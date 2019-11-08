package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  SysUserRepository extends JpaRepository<SysUser,Integer> {


    SysUser findByName(String username);
}
