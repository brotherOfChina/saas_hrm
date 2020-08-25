package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.ihrm.company.controller
 *
 * @author zhaopj
 * 2020/7/28
 */

//1.解决跨域
@CrossOrigin
//2. 声明restController
@RestController
//3.设置父路径
@RequestMapping("/company")
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        // 1.设置保存的企业id
        String companyId = "1";
        department.setCompanyId(companyId);
        //2.调用service完成保存
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public Result findAll() {

        //指定企业id
        List<Department> departments = departmentService.findAll(companyId);
        Company company = companyService.findById(companyId);
        return new Result(ResultCode.SUCCESS, new DeptListResult(company, departments));
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {

        //指定企业id
        Department department = departmentService.findById(id);

        return new Result(ResultCode.SUCCESS, department);
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody Department department) {
        // 1.设置保存的企业id
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id") String id) {

        //指定企业id
       departmentService.deleteById(id);

        return new Result(ResultCode.SUCCESS);
    }

}
