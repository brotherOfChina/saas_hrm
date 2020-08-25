package com.ihrm.system.service;

import com.ihrm.common.entity.PageResult;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.utils.PermissionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * com.ihrm.system.service
 *
 * @author zhaopj
 * 2020/8/17
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PermissionDao permissionDao;
    /**
     * 添加角色
     *
     * @param role 角色实例
     */
    public void save(Role role) {
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    /**
     * 更新角色
     *
     * @param role 角色实例
     */
    public void update(Role role) {
        Role target = roleDao.getOne(role.getId());
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.save(target);
    }

    /**
     * 根据id查询角色
     *
     * @param id 角色id
     * @return 角色实例
     */
    public Role findById(String id) {
        if (roleDao.findById(id).isPresent()) {
            return roleDao.findById(id).get();
        } else {
            return null;
        }

    }

    /**
     * 根据id删除角色
     * @param id 角色id
     */
    public void delete(String id){
        roleDao.deleteById(id);
    }

    /**
     * 查询公司下所有角色
     * @param companyId  公司id
     * @param page 页码
     * @param size 页数
     * @return 角色的page对象
     */
    public Page<Role> findAll(String companyId,int page,int size){
        Specification<Role> roleSpecification= (Specification<Role>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("companyId").as(String.class),companyId);
        return  roleDao.findAll(roleSpecification, PageRequest.of(page-1,size));
    }

    /**
     * 分配权限
     * @param roleId
     * @param permIds
     */
    public void assignPerms(String roleId, List<String> permIds) {
        //1.获取分配的角色对象
        if (!roleDao.findById(roleId).isPresent()){
            Role role=roleDao.findById(roleId).get();
            //2.构造角色的权限集合
            Set<Permission> perms =new HashSet<>();
            for (String permId : permIds) {
                Permission permission = permissionDao.findById(permId).get();
                //需要根据父id和类型查询API权限列表
                List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
                perms.addAll(apiList);
                perms.add(permission);
            }
            System.out.println(perms.size());
            //3.设置角色和权限的关系
            role.setPermissions(perms);
            roleDao.save(role);
        }





    }
}
