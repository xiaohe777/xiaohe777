package com.yjxxt.aop;


import com.yjxxt.annotations.RequirePermission;
import com.yjxxt.exceptions.NoAuthenticationException;
import com.yjxxt.exceptions.NoLoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;


    @Around(value = "@annotation(com.yjxxt.annotations.RequirePermission)")
    public Object around(ProceedingJoinPoint pjp){
        //看看是否登录
        //先拿权限码
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if(permissions == null || permissions.size() == 0) {
            throw new NoLoginException("用户未登录");
        }
        //看看是否包含访问的目标方法头上注解里的权限码
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        RequirePermission requirePermission = signature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        if(!permissions.contains(requirePermission.code())) {
            throw new NoAuthenticationException("无权访问");
        }
        //返回结果
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
