package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.ihrm.common.controller
 *
 * @author zhaopj
 * 2020/7/28
 */

public class BaseController {
    public HttpServletRequest request;
    public HttpServletResponse response;

    public String companyId;
    public String companyName;

    @ModelAttribute//提前运行
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.companyName = companyName;
        this.response = response;
        //todo 目前使用 写死
        this.companyId = "1";
    }
}
