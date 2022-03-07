package com.yjxxt.controller;

import com.yjxxt.pojo.User;
import com.yjxxt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {


    @Resource
    private UserService userService;




    @RequestMapping("list")
    @ResponseBody
    public List<User> list(){
        return userService.queryAll();
    }


    @RequestMapping("del")
    public String del(){
        return "del";
    }


    @RequestMapping("delUser")
    public String delUser(String userName, Model model){


        userService.delUser(userName);

        model.addAttribute("success","删除成功");
        return "del";
    }

    @RequestMapping("add")
    public String add(){
        return "add";
    }


    @RequestMapping("addUser")
    public String addUser(User user, Model model){

        userService.addUser(user);

        model.addAttribute("success","添加成功");
        return "add";
    }


    @RequestMapping("update")
    public String update(){
        return "update";
    }

    @RequestMapping("updateUser")
    public String updateUser(User user, Model model){

        userService.updateUser(user);

        model.addAttribute("success","修改成功");
        return "update";
    }


}
