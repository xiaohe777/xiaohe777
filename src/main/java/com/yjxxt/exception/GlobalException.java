package com.yjxxt.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {

        ModelAndView modelAndView = new ModelAndView("ex");
        modelAndView.addObject("msg",e.getMessage());

        if(e instanceof ParamException) {
            modelAndView.setViewName("ParamException");
            ParamException ex = (ParamException) e;
            modelAndView.addObject("code",ex.getCode());
            modelAndView.addObject("msg",ex.getMsg());
        }

        return modelAndView;
    }
}
