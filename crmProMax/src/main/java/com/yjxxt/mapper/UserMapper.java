package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.pojo.User;


public interface UserMapper extends BaseMapper<User,Integer> {

    User selectUserByUserName(String userName);

}