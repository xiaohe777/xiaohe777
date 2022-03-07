package com.yjxxt.controller;


import com.yjxxt.base.BaseController;
import com.yjxxt.pojo.User;
import com.yjxxt.service.PermissionService;
import com.yjxxt.service.UserService;
import com.yjxxt.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;


    @RequestMapping("index")
    public String index (){
        //返回视图
        return "index";
    }


    @RequestMapping("main")
    public String main (HttpServletRequest req){
        //从cookie中拿到userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //根据userId查询用户
        User user = userService.selectByPrimaryKey(userId);
        //将查到的用户存进req
        req.setAttribute("user",user);
        //查当前登录用户的权限码
        List<String> permissions = permissionService.selectUserHasPermissions(userId);
        //存入session
        req.getSession().setAttribute("permissions",permissions);
        //跳往目标视图
        return "main";
    }


    @RequestMapping("welcome")
    public String welcome (){
        return "welcome";
    }
}
