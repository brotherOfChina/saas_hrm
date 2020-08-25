package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * com.ihrm.company.dao
 *
 * @author zhaopj
 * 2020/7/28
 */
@Service
public class DepartmentService  extends BaseService<Department> {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门
     */
    public void save(Department department) {
        String id = idWorker.nextId() + "";
        department
                .setId(id);
        departmentDao.save(department);
    }

    /**
     * 更新部门
     */
    public void update(Department department) {
        Department dept = departmentDao.findById(department.getId()).get();
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        departmentDao.save(dept);
    }

    /**
     * 更新id查询部门
     */
    public Department findById(String id) {

        return departmentDao.findById(id).get();
    }

    /**
     * 查询全部部门
     *
     * @param companyId 企业id
     *
     */
    public List<Department> findAll(String companyId) {

        /**
         * root :包含了所有的对象数据
         * CriteriaQuery：一般不用
         * criteriaBuilder：构造查询条件
         */
        Specification<Department> specification
                = (Specification<Department>)
                (root, criteriaQuery, criteriaBuilder)
                        -> criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
        return departmentDao.findAll(getSpec(companyId));
    }

    /**
     * 根据id删除
     */
    public void deleteById(String id) {
        departmentDao.deleteById(id);
    }

}