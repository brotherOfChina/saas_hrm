package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * com.ihrm.system.controller
 *
 * @author zhaopj
 * 2020/8/17
 */

@CrossOrigin
@RestController
@RequestMapping("/system")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody Map<String, Object> map) throws Exception {
        map.put("id", id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }


    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public Result findAll(@RequestParam Map<String, Object> map) throws Exception {

        return new Result(ResultCode.SUCCESS, permissionService.findALl(map));
    }
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) throws Exception {
        Map<String, Object> map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS, map);
    }
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id") String id) throws Exception {
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
