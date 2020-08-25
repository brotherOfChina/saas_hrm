package com.ihrm.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * com.ihrm.common.utils
 *
 * @author zhaopj
 * 2020/8/24
 */

@Getter
@Setter
@ConfigurationProperties("jwt.config")
public class JwtUtil {
    private String key;
    private long ttl;


    public String createJWT(String id, String subject, Map<String, Object> map) {
        long now = System.currentTimeMillis();
        long exp = now + ttl;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)//设置id
                .setSubject(subject)//设置简述
                .setIssuedAt(new Date())//设置签分日期
                .signWith(SignatureAlgorithm.HS256, key);//设置加密方式及密钥
        for(Map.Entry<String,Object> entry:map.entrySet()){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        if (ttl >0){
            jwtBuilder.setExpiration(new Date(exp));
        }

        return jwtBuilder.compact();

    }
    public Claims parseJWT(String token){
        Claims claims=null;
        try {
            claims=Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        }catch (Exception r){
            r.printStackTrace();
        }
        return claims;
    }
}
