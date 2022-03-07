package com.yjxxt.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.base.BaseService;
import com.yjxxt.mapper.UserMapper;
import com.yjxxt.mapper.UserRoleMapper;
import com.yjxxt.model.UserModel;
import com.yjxxt.pojo.User;
import com.yjxxt.pojo.UserRole;
import com.yjxxt.query.UserQuery;
import com.yjxxt.utils.AssertUtil;
import com.yjxxt.utils.Md5Util;
import com.yjxxt.utils.PhoneUtil;
import com.yjxxt.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends BaseService<User,Integer> {


    @Resource
    private UserMapper userMapper;


    @Resource
    private UserRoleMapper userRoleMapper;


    /**
     * 用户登录
     * @param userName 用户名
     * @param UserPwd 用户密码
     * @return 用户模型
     */
    public UserModel login(String userName, String UserPwd){

        //校验用户名和密码
        checkUserParams(userName,UserPwd);
        //查询是否存在此用户
        User temp = userMapper.selectUserByUserName(userName);
        AssertUtil.isTrue(null == temp,"用户不存在或已注销");
        //校验密码
        checkUserPwd(UserPwd,temp.getUserPwd());
        //返回结果
        return buildUserModel(temp);

    }

    /**
     * 构建返回的用户模型
     * @param temp 用户
     * @return 用户模型
     */
    private UserModel buildUserModel(User temp) {
        //实例化model
        UserModel model = new UserModel();
        //设置信息(加密)
        model.setUserIdStr(UserIDBase64.encoderUserID(temp.getId()));
        model.setUserName(temp.getUserName());
        model.setTrueName(temp.getTrueName());
        //返回结果
        return model;

    }

    /**
     * 校验密码
     * @param userPwd 用户密码
     * @param tempPwd 数据库查到的用户密码
     */
    private void checkUserPwd(String userPwd, String tempPwd) {
        //校验密码
        AssertUtil.isTrue(!tempPwd.equals(Md5Util.encode(userPwd)),"密码错误");
    }

    /**
     * 校验用户名和密码是否为空
     * @param userName 用户名
     * @param userPwd  用户密码
     */
    private void checkUserParams(String userName, String userPwd) {
        //校验用户名
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        //校验密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空");
    }


    /**
     * 修改用户密码
     * @param userId 用户id
     * @param oldPwd 原始密码
     * @param newPwd 新密码
     * @param confirmPwd 确认密码
     */
    public void updateUserPassWord(Integer userId,String oldPwd,String newPwd,String confirmPwd){
        //根据userId查询用户，校验用户是否存在或登录
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == user,"用户未登录或不存在");
        //校验用户密码
        checkUserPwdParams(user,oldPwd,newPwd,confirmPwd);
        //修改密码
        user.setUserPwd(Md5Util.encode(newPwd));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1,"修改失败");
    }

    /**
     * 校验密码
     * @param user 用户
     * @param oldPwd 原始密码
     * @param newPwd 新密码
     * @param confirmPwd 确认密码
     */
    private void checkUserPwdParams(User user,String oldPwd, String newPwd, String confirmPwd) {
        //非空判断
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPwd),"确认密码不能为空");
        //原始密码不正确
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)),"原始密码不正确");
        //新密码与原始密码不一致
        AssertUtil.isTrue(oldPwd.equals(newPwd),"新密码与原始密码相同");
        //确认密码与新密码一致
        AssertUtil.isTrue(!newPwd.equals(confirmPwd),"确认密码与新密码不一致");
    }


    /**
     * 查询所有用户列表
     * @param userQuery 参数查询
     * @return 返回map
     */
    public Map<String ,Object> list(UserQuery userQuery){
        //实例化分页
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        //查询
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(userQuery));
        //实例化map
        HashMap<String, Object> map = new HashMap<>();
        //赋值
        map.put("code",0);
        map.put("msg","查询成功");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        //返回
        return map;
    }


    /**
     * 添加用户
     * 1. 参数校验
     * 用户名 非空 唯一性
     * 邮箱 非空
     * 手机号 非空 格式合法
     * 2. 设置默认参数
     * isValid 1
     * creteDate 当前时间
     * updateDate 当前时间
     * userPwd 123456 -> md5加密
     * 3. 执行添加，判断结果
     */
    public void save(User user,Integer[] roleIds){
        //参数校验
        checkUserParams(user.getUserName(),user.getEmail(),user.getPhone());
        //用户名唯一
        User temp = userMapper.selectUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp,"用户名已存在");
        //设置默认参数
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        //设默认密码123
        user.setUserPwd(Md5Util.encode("123"));
        //添加
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1,"添加失败");
        //关联角色表，插入角色
        bindUserRoles(user.getId(),roleIds);
    }

    /**
     * 新增和修改用户时插入以及修改用户角色
     * @param userId 用户id
     * @param roleIds 用户的角色id
     */
    private void bindUserRoles(Integer userId, Integer[] roleIds) {
        /**
         * 查询用户拥有的角色，若有，则全部删除，新增roleIds数组里的id值
         * 若没有，直接添加roleIds数组里的id值
         */
        Integer count = userRoleMapper.countUserRoles(userId);
        if(count > 0) {
            //把角色全部删除
            AssertUtil.isTrue(!userRoleMapper.deleteUserRoles(userId).equals(count),"用户角色关联失败");
        }
        //添加角色
        if(roleIds.length != 0) {
            UserRole userRole = new UserRole();
            for (Integer id:roleIds) {
                userRole.setUpdateDate(new Date());
                userRole.setCreateDate(new Date());
                userRole.setUserId(userId);
                userRole.setRoleId(id);
                AssertUtil.isTrue(userRoleMapper.insertSelective(userRole) < 1,"用户角色关联失败");
            }
        }

    }

    /**
     * 更新用户
     * 1. 参数校验
     * id 非空 记录必须存在
     * 用户名 非空 唯一性
     * email 非空
     * 手机号 非空 格式合法
     * 2. 设置默认参数
     * updateDate
     * 3. 执行更新，判断结果
     * @param user 用户信息
     */
    public void update(User user,Integer[] roleIds){
        //校验
        checkUserParams(user.getUserName(),user.getEmail(),user.getPhone());
        //id非空  必须存在
        AssertUtil.isTrue(user.getId() == null || userMapper.selectByPrimaryKey(user.getId()) == null,"用户记录不存在");
        //用户名唯一
        User temp = userMapper.selectUserByUserName(user.getUserName());
        AssertUtil.isTrue(temp != null && !user.getId().equals(temp.getId()),"用户名已存在");
        //设置默认参数
        user.setUpdateDate(new Date());
        //修改
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1,"修改失败");
        //关联角色表，插入角色
        bindUserRoles(user.getId(),roleIds);
    }

    /**
     * 校验参数
     * @param userName 用户名
     * @param email 邮箱
     * @param phone 手机
     */
    private void checkUserParams(String userName, String email, String phone) {
        //非空
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(email),"邮箱为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号为空");
        //合法手机号
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号不合法");
    }

    /**
     * 批量删除用户
     * @param ids 用户的id数组
     */
    public void delete(Integer[] ids){
        //校验数组是否有值
        AssertUtil.isTrue(ids == null || ids.length == 0,"请选择数据");
        //删除
        AssertUtil.isTrue(userMapper.deleteBatch(ids) < ids.length,"删除失败");
        //删除用户的同时把角色表里的信息也删除
        AssertUtil.isTrue(userRoleMapper.deleteBatch(ids) < 0,"角色关联失败");
    }

}

