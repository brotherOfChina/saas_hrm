package com.ihrm.company.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.ihrm.company.service
 *
 * @author zhaopj
 * 2020/7/15
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    /**
     * 保存企业
     * 1.配置idWorker
     * 2.在service注入idWorker
     */
    @Autowired
    private IdWorker idWorker;

    public void add(Company company) {
        //基本属性的设置
        String id = idWorker.nextId() + "";
        company.setId(id);
        //设置默认状态 0未审核，1未审核
        company.setAuditState("0");
        //1已激活 0 未激活
        company.setState(0);
        companyDao.save(company);
    }

    /**
     * 更新企业
     * 1. 参数：company
     * 2.先查id，再更新
     * 3.设置修改的属性
     * 4.dao完成更新
     */
    public void update(Company company) {
        //基本属性的设置
        String id = company.getId();
        Company temp = companyDao.findById(id).get();
        //设置默认状态 0未审核，1已审核
        temp.setAuditState("1");
        temp.setCompanyPhone("18404975605");
        //1已激活 0 未激活
        temp.setState(1);
        companyDao.save(temp);
    }

    /**
     * 删除企业
     */
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    /**
     * 根据id查询企业
     */
    public Company findById(String id) {
        return companyDao.findById(id).get();
    }

    /**
     * 查询企业列表
     */
    public List<Company> finaAll() {
        return companyDao.findAll();
    }
}
