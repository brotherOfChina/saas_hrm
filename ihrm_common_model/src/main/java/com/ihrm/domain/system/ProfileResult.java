package com.ihrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * com.ihrm.domain.system
 *
 * @author zhaopj
 * 2020/8/24
 */

@Setter
@Getter
public class ProfileResult {
    private String mobile;
    private String username;

    private String company;
    private Map<String ,Object> roles;
    public ProfileResult(User user) {
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();
        //角色数据
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        Map<String,Object> rolesMap = new HashMap<>();
        for (Role role : user.getRoles()) {
            for (Permission perm : role.getPermissions()) {
                String code = perm.getCode();
                if(perm.getType() == 1) {
                    menus.add(code);
                }else if(perm.getType() == 2) {
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }
        rolesMap.put("menus",menus);
        rolesMap.put("points",points);
        rolesMap.put("apis",points);
        this.roles = rolesMap;
    }

}
