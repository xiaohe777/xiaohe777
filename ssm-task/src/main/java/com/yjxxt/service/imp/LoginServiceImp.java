package com.yjxxt.service.imp;

import com.yjxxt.pojo.User;
import com.yjxxt.service.LoginService;
import com.yjxxt.service.UserService;
import com.yjxxt.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImp implements LoginService {

    @Resource
    private UserService userService;

    @Override
    public User login(User user) {

        //用户名是否为空
        AssertUtils.isTrue(StringUtils.isBlank(user.getUserName()),"用户名为空");
        //密码是否为空
        AssertUtils.isTrue(StringUtils.isBlank(user.getPassWord()),"密码为空");
        //查询用户是否存在
        User temp = userService.selectUserByName(user.getUserName());
        AssertUtils.isTrue(null == temp,"用户不存在");
        //查询密码是否正确
        AssertUtils.isTrue(!user.getPassWord().equals(temp.getPassWord()),"密码不正确");

        return user;
    }
}
