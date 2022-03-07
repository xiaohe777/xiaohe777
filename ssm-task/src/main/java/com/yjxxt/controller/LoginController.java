package com.yjxxt.controller;


import com.yjxxt.pojo.User;
import com.yjxxt.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private LoginService loginService;

    @RequestMapping("login")
    public String login(User user, HttpSession session){

        User logger = loginService.login(user);

        session.setAttribute("msg","欢迎" + logger.getUserName()+",登陆成功");

        session.setAttribute("user",logger);

        return "main";
    }



}
