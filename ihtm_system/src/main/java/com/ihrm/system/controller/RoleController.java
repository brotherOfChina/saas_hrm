package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * com.ihrm.system.controller
 *
 * @author zhaopj
 * 2020/8/17
 */

@RestController
@CrossOrigin
@RequestMapping("/system")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "/role/assignPrem",method = RequestMethod.PUT)
    public Result assignPrem(@RequestBody Map<String, Object> map) {
        //1.获取被分配的角色的id
        String roleId = (String) map.get("roleId");
        //2.获取到权限的id列表
        List<String> permIds = (List<String>) map.get("ids");
        //3.调用service完成权限分配
        roleService.assignPerms(roleId, permIds);
        return new Result(ResultCode.SUCCESS);


    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Result save(@RequestBody Role role) {
        role.setCompanyId(companyId);
        roleService.save(role);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Role role) {
        role.setId(id);
        roleService.update(
                role
        );
        return Result.SUCCESS();
    }


    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) {
        roleService.delete(
                id
        );
        return Result.SUCCESS();
    }


    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) {
        Role role = roleService.findById(
                id
        );
        return new Result(ResultCode.SUCCESS, role);
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result findAllByPage(int page, int size) {
        Page<Role> roles = roleService.findAll(companyId, page, size);
        PageResult<Role> pageResult = new PageResult<>(roles.getTotalElements(), roles.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

}
