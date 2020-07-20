package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.ihrm.company.controller
 *
 * @author zhaopj
 * 2020/7/15
 */
@RequestMapping("/company")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    //保存企业
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Result save(@RequestBody Company company) {
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }

    //更具id更新企业
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public @ResponseBody
    Result update(@PathVariable(value = "id") String id, @RequestBody Company company) {
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }

    //根据id删除企业
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Result delete(@PathVariable(value = "id") String id) {
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //根据id查询企业
    @RequestMapping(method = RequestMethod.GET, value = "/  {id}")
    public Result findById(@PathVariable(value = "id") String id) {
        Company company = companyService.findById(id);
        return new Result(ResultCode.SUCCESS, company);
    }
    //查询全部企业
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Company> companies = companyService.finaAll();
        return new Result(ResultCode.SUCCESS, companies);
    }
}
