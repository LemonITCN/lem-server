package cn.lemonit.lemserver.service;

import cn.lemonit.lemserver.domian.SysRole;
import cn.lemonit.lemserver.domian.SysUser;
import cn.lemonit.lemserver.domian.SysUserRole;
import cn.lemonit.lemserver.domian.ThreadUser;
import cn.lemonit.lemserver.repository.SysRoleRepository;
import cn.lemonit.lemserver.repository.SysUserRepository;
import cn.lemonit.lemserver.repository.SysUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆身份认证
 * @author: JoeTao
 * createAt: 2018/9/14
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final SysUserRepository authMapper;

    @Autowired
    SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    SysRoleRepository sysRoleRepository;

    public CustomUserDetailsServiceImpl(SysUserRepository authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public ThreadUser loadUserByUsername(String name) throws UsernameNotFoundException {
        ThreadUser user = new ThreadUser();
        SysUser userDetail = authMapper.findByName(name);
        if (userDetail == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
        }
        user.setUserId(userDetail.getId());
        user.setUsername(userDetail.getName());
        user.setPassword(userDetail.getPassword());

//        List<GrantedAuthority> a = AuthorityUtils.commaSeparatedStringToAuthorityList("123,222");
        List<SysUserRole> role = sysUserRoleRepository.findByUserId(userDetail.getId());
        List<SysRole> roles = new ArrayList<>();
        for (SysUserRole ur : role) {
            roles.add(sysRoleRepository.getOne(ur.getRoleId()));
        }
//        user.setRoleName();
        user.setRoles(roles);
        return user;
    }
}
