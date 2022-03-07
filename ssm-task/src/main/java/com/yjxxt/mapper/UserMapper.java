package com.yjxxt.mapper;

import com.yjxxt.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> queryAll();

    User selectByName(String name);

    Integer delUser(String userName);

    Integer addUser(User user);

    Integer updateUser(User user);

}
