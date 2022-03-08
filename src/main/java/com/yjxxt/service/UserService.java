package com.yjxxt.service;

import com.yjxxt.mapper.UserMapper;
import com.yjxxt.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> queryAll(){
        System.out.println("哈哈哈");
        return userMapper.queryAll();
    }
}
