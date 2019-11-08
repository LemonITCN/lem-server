package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.ResponseUserToken;
import cn.lemonit.lemserver.domian.SysUser;
import cn.lemonit.lemserver.domian.ThreadUser;
import cn.lemonit.lemserver.exception.CustomException;
import cn.lemonit.lemserver.dao.SysUserRepository;
import cn.lemonit.lemserver.utils.JwtUtils;
import cn.lemonit.lemserver.utils.ResultCode;
import cn.lemonit.lemserver.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: JoeTao
 * createAt: 2018/9/17
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtTokenUtil;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, @Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService, JwtUtils jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public SysUser register(SysUser userDetail) {
        String username = userDetail.getName();
        if(sysUserRepository.findByName(username)!=null) {
            throw new CustomException(ResultJson.failure(ResultCode.BAD_REQUEST, "用户已存在"));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userDetail.getPassword();
        userDetail.setPassword(encoder.encode(rawPassword));
        sysUserRepository.save(userDetail);
        return userDetail;
    }

    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final ThreadUser userDetail = (ThreadUser) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateAccessToken(userDetail);
        //存储token
        jwtTokenUtil.putToken(username, token);
        return new ResponseUserToken(token, userDetail);

    }

    @Override
    public void logout(String token) {
        token = token.substring(tokenHead.length());
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(userName);
    }

    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new CustomException(ResultJson.failure(ResultCode.LOGIN_ERROR, e.getMessage()));
        }
    }
}
