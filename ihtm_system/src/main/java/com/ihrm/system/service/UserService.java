package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * com.ihrm.system.service
 *
 * @author zhaopj
 * 2020/8/15
 */

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoleDao roleDao;

    /**
     * 保存用户
     */
    public void save(User user) {
        String id = idWorker.nextId() + "";
        user
                .setId(id);
        user.setPassword("123456");
        user.setEnableState(1);
        userDao.save(user);
    }

    /**
     * 更新部门
     */
    public void update(User user) {
        User user1 = userDao.findById(user.getId()).get();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setDepartmentId(user.getDepartmentId());
        user1.setDepartmentName(user.getDepartmentName());
        userDao.save(user1);
    }

    /**
     * 更新id查询部门
     */
    public User findById(String id) {

        return userDao.findById(id).get();
    }

    /**
     * 更新id查询部门
     */
    public void deleteById(String id) {

        userDao.deleteById(id);
    }

    /**
     * 查询全部用户
     *
     * @param map 集合
     *            companyId
     *            departmentId
     */
    public Page<User> findAll(Map<String, Object> map, int page, int size) {
        //1.需要查询条件
        Specification<User> userSpecification = (Specification<User>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(map.get("companyId"))) {
                list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), map.get("companyId")));
            }
            if (!StringUtils.isEmpty(map.get("departmentId"))) {
                list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), map.get("departmentId")));
            }
            if (!StringUtils.isEmpty(map.get("hasDept"))) {
                if ("0".equals(map.get("hasDept"))) {
                    list.add(criteriaBuilder.isNull(root.get("departmentId")));
                } else {
                    list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                }
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        //2.分页
        /**
         * root :包含了所有的对象数据
         * CriteriaQuery：一般不用
         * criteriaBuilder：构造查询条件
         */
        return userDao.findAll(userSpecification, PageRequest.of(page - 1, size));
    }


    public void assignRoles(String userId, List<String> roleIds) {
        //1.根据id查询用户
        User user = userDao.getOne(userId);
        //2.设置用的的角色列表
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            roles.add(roleDao.getOne(roleId));
        }
        //3.设置用户和角色集合的关系
        user.setRoles(roles);
        //4.更新用户
        userDao.save(user);

    }

    public User findByMobile(String mobile) {

        return userDao.findByMobile(mobile);

    }
}
