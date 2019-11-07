package cn.lemonit.lemserver.repository;

import cn.lemonit.lemserver.domian.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  SysUserRepository extends JpaRepository<SysUser,Integer> {


    SysUser findByName(String username);
}
