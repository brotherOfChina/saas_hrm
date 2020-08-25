package com.ihrm.system.dao;

import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * com.ihrm.system.dao
 *
 * @author zhaopj
 * 2020/8/17
 */
public interface PermissionPointDao extends JpaRepository<PermissionPoint, String>, JpaSpecificationExecutor<PermissionPoint> {
}
