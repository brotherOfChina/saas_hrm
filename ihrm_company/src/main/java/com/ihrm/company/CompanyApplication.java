package com.ihrm.company;

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * com.ihrm.company
 *
 * @author zhaopj
 * 2020/7/15
 */

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm.company")
//2.jps注解
@EntityScan(value = "com.ihrm.domain.company")
public class CompanyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }

}
