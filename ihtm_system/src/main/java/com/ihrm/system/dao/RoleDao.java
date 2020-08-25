package com.ihrm.system.dao;

import com.ihrm.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * com.ihrm.system.dao
 *
 * @author zhaopj
 * 2020/8/17
 */
public interface RoleDao  extends JpaRepository<Role,String> , JpaSpecificationExecutor<Role> {
}
