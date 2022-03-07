package com.yjxxt;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.yjxxt.mapper")
public class CrmProMaxApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CrmProMaxApplication.class,args);
    }


//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(CrmProMaxApplication.class);
//    }
}
