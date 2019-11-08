package cn.lemonit.lemserver.utils;

import cn.lemonit.lemserver.domian.SysRole;
import cn.lemonit.lemserver.domian.SysUserRole;
import cn.lemonit.lemserver.domian.ThreadUser;
import cn.lemonit.lemserver.dao.SysRoleRepository;
import cn.lemonit.lemserver.dao.SysUserRoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: JoeTao
 * createAt: 2018/9/14
 */
@Component
public class JwtUtils {
    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";

    private Map<String, String> tokenMap = new ConcurrentHashMap<>(32);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long access_token_expiration;

    @Value("${jwt.expiration}")
    private Long refresh_token_expiration;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;



    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    // 验证在不在map里面
    public boolean containToken(String userName, String token) {
        if (userName != null && tokenMap.containsKey(userName) && tokenMap.get(userName).equals(token)) {
            return true;
        }
        return false;
    }


    //生成token===================================开始=======================

    private List authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }

    public String generateAccessToken(ThreadUser userDetail) {
        Map<String, Object> claims = generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, authoritiesToArray(userDetail.getAuthorities()));
        return generateAccessToken(userDetail.getUsername(), claims);
    }

    private Map<String, Object> generateClaims(ThreadUser userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USER_ID, userDetail.getUserId());
        return claims;
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, access_token_expiration);
    }

    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    //生成token===================================结束=======================


    //存token 和 userName
    public void putToken(String userName, String token) {
        tokenMap.put(userName, token);
    }



    //通过token 获取对象

    public ThreadUser getUserFromToken(String token) {
        ThreadUser userDetail;
        try {
            final Claims claims = getClaimsFromToken(token);
            Integer userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            List<GrantedAuthority> roleName = (List<GrantedAuthority>) claims.get(CLAIM_KEY_AUTHORITIES);


            List<SysUserRole> role = sysUserRoleRepository.findByUserId(userId);
            List<SysRole> roles = new ArrayList<>();
            for (SysUserRole ur : role) {
                roles.add(sysRoleRepository.getOne(ur.getRoleId()));
            }
//            SysRole s = new SysRole();
//            SysRole s1 = new SysRole();
//            s.setId(1);
//            s.setName("ROLE_USER");
//            s1.setId(2);
//            s1.setName("ROLE_ADMIN");
//            roles.add(s);
//            roles.add(s1);
//            for (SysUserRole ur : role) {
//                roles.add(sysRoleRepository.getOne(ur.getRoleId()));
//            }
//
//            for (SysUserRole ur : role) {
//                roles.add(sysRoleRepository.getOne(ur.getRoleId()));
//            }

            userDetail = new ThreadUser();
            userDetail.setPassword("");
            userDetail.setUserId(userId);
            userDetail.setUsername(username);
//            userDetail.setAuthoritie(roleName);
            userDetail.setRoles(roles);
        } catch (Exception e) {
            userDetail = null;
        }
        return userDetail;
    }


    public Integer getUserIdFromToken(String token) {
        Integer userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Integer.valueOf(String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
        } catch (Exception e) {
            userId = 0;
        }
        return userId;
    }


    public Boolean validateToken(String token, ThreadUser userDetails) {
        ThreadUser userDetail = (ThreadUser) userDetails;
        final long userId = getUserIdFromToken(token);
        final String username = getUsernameFromToken(token);
//        final Date created = getCreatedDateFromToken(token);
        return (userId == userDetail.getUserId()
                && username.equals(userDetail.getUsername())
                && !isTokenExpired(token)
//                && !isCreatedBeforeLastPasswordReset(created, userDetail.getLastPasswordResetDate())
        );
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }


    public void deleteToken(String userName) {
        tokenMap.remove(userName);
    }


}
