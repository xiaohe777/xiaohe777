package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {


    Integer selectByRoleId(Integer roleId);

    Integer deleteModulesByRoleId(Integer roleId);

    List<Integer> selectModuleIdByRoleId(Integer roleId);

    List<String> selectUserHasPermissions(Integer userId);
}