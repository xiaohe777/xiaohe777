package com.yjxxt.service;

import com.yjxxt.pojo.User;

import java.util.List;

public interface UserService {

    List<User> queryAll();

    User selectUserByName(String name);

    void delUser(String userName);

    void addUser(User user);

    void updateUser(User user);
}
