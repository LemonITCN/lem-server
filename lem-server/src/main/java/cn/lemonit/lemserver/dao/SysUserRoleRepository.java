package cn.lemonit.lemserver.dao;

import cn.lemonit.lemserver.domian.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Integer> {


    List<SysUserRole> findByUserId(Integer id);
}
