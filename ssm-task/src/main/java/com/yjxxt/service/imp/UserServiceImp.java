package com.yjxxt.service.imp;

import com.yjxxt.mapper.UserMapper;
import com.yjxxt.pojo.User;
import com.yjxxt.service.UserService;
import com.yjxxt.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public User selectUserByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public void delUser(String userName) {
        //判断待删除的用户是否存在
        AssertUtils.isTrue(userMapper.selectByName(userName) == null,"用户不存在");
        //删除
        AssertUtils.isTrue(userMapper.delUser(userName) < 1,"删除失败");
    }

    @Override
    public void addUser(User user) {

        AssertUtils.isTrue(StringUtils.isBlank(user.getPassWord()),"密码为空");
        //查询用户是否存在
        User temp = selectUserByName(user.getUserName());
        AssertUtils.isTrue(null != temp,"用户名已存在");
        //添加
        AssertUtils.isTrue(userMapper.addUser(user) < 1,"添加失败");
    }

    @Override
    public void updateUser(User user) {
        User temp = selectUserByName(user.getUserName());
        //用户名是否为空
        AssertUtils.isTrue(user.getUserName() == null,"用户名为空");
        //密码是否为空
        AssertUtils.isTrue(StringUtils.isBlank(user.getPassWord()),"密码为空");
        //用户名是否已存在
        AssertUtils.isTrue(temp != null && !user.getUserId().equals(temp.getUserId()),"用户已存在");
        //修改
        AssertUtils.isTrue(userMapper.updateUser(user) < 1,"修改失败");
    }
}
