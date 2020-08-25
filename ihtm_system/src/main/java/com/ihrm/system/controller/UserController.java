package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;


import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtil;
import com.ihrm.domain.system.ProfileResult;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/system")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);

        if (user == null || !user.getPassword().equals(password)) {
            return new Result(ResultCode.MOBILE_ERROR_OR_PASSWORD_ERROR);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getCompanyName());
            String token = jwtUtil.createJWT(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);

        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST )
    public Result profile(HttpServletRequest httpRequest) throws Exception {
        //1.获取请求头信息
        //2.替换token
        String authorization=httpRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        String token=authorization.replace("Bearer ","");
        Claims claims=jwtUtil.parseJWT(token);
        String id=claims.getId();
        User user = userService.findById(id);

        return new Result(ResultCode.SUCCESS, new ProfileResult(user));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        // 1.设置保存的企业id
        String companyId = "1";
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        //2.调用service完成保存
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam() Map<String, Object> map) {
        map.put("companyId", companyId);
        //指定企业id
        Page<User> all = userService.findAll(map, page, size);
        PageResult<User> userPageResult = new PageResult<>(all.getTotalElements(), all.getContent());
        return new Result(ResultCode.SUCCESS, userPageResult);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {

        //指定企业id
        User user = userService.findById(id);

        return new Result(ResultCode.SUCCESS, user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody User user) {
        // 1.设置保存的企业id
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id") String id) {
        //指定企业id
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String, Object> map) {
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        if (map.containsKey("roleIds")) {
            List<String> roleIds = (List<String>) map.get("roleIds");
            userService.assignRoles(userId, roleIds);
            return new Result(ResultCode.SUCCESS);
        } else {
            return new Result(ResultCode.FAIL);
        }
        //3.调用色绿i完成角色分配
    }
}
