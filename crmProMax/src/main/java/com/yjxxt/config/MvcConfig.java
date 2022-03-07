package com.yjxxt.config;


import com.yjxxt.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


    //把未登录拦截器放进ioc容器
    @Bean
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加未登录拦截器
        registry.addInterceptor(noLoginInterceptor())
                //添加拦截路径为所有
                .addPathPatterns("/**")
                //添加放行路径
                .excludePathPatterns("/index","/user/login","/css/**","/images/**","/js/**","/lib/**");
    }
}
