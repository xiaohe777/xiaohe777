package com.yjxxt.controller;

import com.yjxxt.exception.ParamException;
import com.yjxxt.pojo.User;
import com.yjxxt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("list")
    @ResponseBody
    public List<User> list() {
        if (true) {
            throw new ParamException(400,"参数错啦");
//            System.out.println(3/0);
        }

        return userService.queryAll();
    }
}
