package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.JwtUtil;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * com.ihrm.system.controller
 *
 * @author zhaopj
 * 2020/8/24
 */
@RestController
@CrossOrigin
@RequestMapping("/frame")
public class LoginController {
    @Autowired
    private UserService userService;


}
