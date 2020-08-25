package com.ihrm.system.dao;

import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * com.ihrm.system.dao
 *
 * @author zhaopj
 * 2020/8/17
 */
public interface PermissionApiDao extends JpaRepository<PermissionApi, String>, JpaSpecificationExecutor<PermissionApi> {
}
