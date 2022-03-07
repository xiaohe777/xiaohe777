package com.yjxxt.service;

import com.yjxxt.base.BaseService;
import com.yjxxt.mapper.PermissionMapper;
import com.yjxxt.pojo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {


    @Resource
    private PermissionMapper permissionMapper;


    /**
     * 查询用户所拥有的所有权限码
     * @return 返回list结果集
     */
    public List<String> selectUserHasPermissions(Integer userId){
        return permissionMapper.selectUserHasPermissions(userId);
    }
}
