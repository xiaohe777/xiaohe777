package com.yjxxt;

import com.yjxxt.exceptions.ParamException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("ex", "请求失败");
        // 判断是否是自定义异常
        if (ex instanceof ParamException) {
            ParamException e = (ParamException) ex;
            mv.addObject("ex", e.getMsg());
        }
        return mv;
    }
}
