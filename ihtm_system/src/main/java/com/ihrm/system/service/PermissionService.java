package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import com.ihrm.system.utils.PermissionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * com.ihrm.system.service
 *
 * @author zhaopj
 * 2020/8/17
 */

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private PermissionApiDao permissionApiDao;
    @Autowired
    private IdWorker idWorker;


    public void save(Map<String, Object> map) throws Exception {
        //设置主键的值
        String id = idWorker.nextId() + "";
        //1.通过map构造permission对象
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        perm.setId(id);
        //2.根据类型构造不同的资源对象（菜单，按钮，api）
        int type = perm.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //保存
        permissionDao.save(perm);

    }

    public void update(Map<String, Object> map) throws Exception {
        //设置主键的值
        //1.通过map构造permission对象
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        //根据传递的权限id查询权限
        if (permissionDao.findById(perm.getId()).isPresent()) {
            Permission permission = permissionDao.findById(perm.getId()).get();
            permission.setName(perm.getName());
            permission.setCode(perm.getCode());
            permission.setDescription(perm.getDescription());
            permission.setEnVisible(perm.getEnVisible());
        }

        //2.根据类型构造不同的资源对象（菜单，按钮，api）
        int type = perm.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(perm.getId());
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(perm.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(perm.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //保存
        permissionDao.save(perm);

    }

    public Map<String, Object> findById(String id) throws Exception {
        //1.查询权限
        Permission permission = permissionDao.getOne(id);
        //2.根据权限的类型查询资源
        int type = permission.getType();
        Object object = null;

        switch (type) {
            case PermissionConstants.PY_MENU:
                object = permissionMenuDao.getOne(id);
                break;
            case PermissionConstants.PY_POINT:
                object = permissionPointDao.getOne(id);
                break;
            case PermissionConstants.PY_API:
                object = permissionApiDao.getOne(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //构造map集合
        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name", permission.getName());
        map.put("type", permission.getType());
        map.put("code", permission.getCode());
        map.put("description", permission.getDescription());
        map.put("pid", permission.getPid());
        map.put("enVisible", permission.getEnVisible());
        return map;
    }

    /**
     * 查询全部权限
     * type ：
     * 0 菜单+按钮
     * 1. 菜单
     * 3. API接口
     *
     * @param map 请求的权限
     * @return 符号条件的权限列表
     */
    public List<Permission> findALl(Map<String, Object> map) {
        //1.查询条件
        Specification<Permission> permissionSpecification = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //动态拼接查询条件
                List<Predicate> list = new ArrayList<>();
                //根据pid查询
                if (!StringUtils.isEmpty(map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class), map.get("pid")));
                }
                if (!StringUtils.isEmpty(map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class), map.get("enVisible")));
                }
                if (!StringUtils.isEmpty(map.get("type"))) {
                    String type1 = (String) map.get("type");
                    CriteriaBuilder.In<Object> typeIn = criteriaBuilder.in(root.get("type"));
                    if ("0".equals(type1)) {
                        typeIn.value(1).value(2);
                    } else {
                        typeIn.value(Integer.parseInt(type1));
                    }
                    list.add(typeIn);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        return permissionDao.findAll(permissionSpecification);
    }
    public void deleteById(String id) throws Exception {
        Permission permission = permissionDao.getOne(id);
        permissionDao.deleteById(id);

        int type=permission.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }

}

