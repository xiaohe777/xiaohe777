package com.yjxxt;


import com.alibaba.fastjson.JSON;
import com.yjxxt.base.ResultInfo;
import com.yjxxt.exceptions.NoAuthenticationException;
import com.yjxxt.exceptions.NoLoginException;
import com.yjxxt.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理类
 */
@Component
public class GlobalException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        //实例化ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //如果是未登录异常，重定向到index
        if(ex instanceof NoLoginException) {
            modelAndView.setViewName("redirect:/index");
            return modelAndView;
        }
        if(ex instanceof NoAuthenticationException) {
            //返回json
            PrintWriter writer = null;
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setCode(400);
            resultInfo.setMsg("无权访问");
            NoAuthenticationException na = (NoAuthenticationException) ex;
            resultInfo.setCode(na.getCode());
            resultInfo.setMsg(na.getMsg());
            resp.setContentType("application/json;charset=utf-8");
            try {
                writer = resp.getWriter();
                writer.write(JSON.toJSONString(resultInfo));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer != null) {
                    writer.flush();
                    writer.close();
                }
            }
            return null;
        }
        //设置默认信息
        modelAndView.setViewName("error");
        modelAndView.addObject("code",400);
        modelAndView.addObject("data","系统异常，请稍后再试");
        //判断是否为handler方法
        if(handler instanceof HandlerMethod) {
            //强转
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody annotation = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            System.out.println(annotation);
            //判断
            if(null == annotation) {
                //返回页面
                if(ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    modelAndView.addObject("code",pe.getCode());
                    modelAndView.addObject("data",pe.getMsg());
                    return modelAndView;
                }
            }else {
                //返回json
                PrintWriter writer = null;
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(400);
                resultInfo.setMsg("系统异常，请稍后再试");
                if(ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                    resp.setContentType("application/json;charset=utf-8");
                }
                try {
                    writer = resp.getWriter();
                    writer.write(JSON.toJSONString(resultInfo));
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(writer != null) {
                        writer.flush();
                        writer.close();
                    }
                }
                return null;
            }
        }
        //返回
        return modelAndView;
    }
}
