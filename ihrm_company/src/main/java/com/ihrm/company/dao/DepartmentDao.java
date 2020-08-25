package com.ihrm.company.dao;

import com.ihrm.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * com.ihrm.company.dao
 *
 * @author zhaopj
 * 2020/7/28
 */
public interface DepartmentDao extends JpaRepository<Department,String>, JpaSpecificationExecutor<Department> {
}