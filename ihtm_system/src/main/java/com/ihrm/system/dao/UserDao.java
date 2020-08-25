package com.ihrm.system.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * com.ihrm.system.dao
 *
 * @author zhaopj
 * 2020/8/15
 */
public interface UserDao extends JpaRepository<User, String> , JpaSpecificationExecutor<User> {
    public User findByMobile(String mobile);
}
