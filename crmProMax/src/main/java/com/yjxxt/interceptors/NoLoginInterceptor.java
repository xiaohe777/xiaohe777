package com.yjxxt.interceptors;

import com.yjxxt.exceptions.NoLoginException;
import com.yjxxt.service.UserService;
import com.yjxxt.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class NoLoginInterceptor extends HandlerInterceptorAdapter {


    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果未登录，抛未登录异常
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if(null == userId || null == userService.selectByPrimaryKey(userId)) {
            throw new NoLoginException();
        }
        //否则放行
        return true;
    }
}
