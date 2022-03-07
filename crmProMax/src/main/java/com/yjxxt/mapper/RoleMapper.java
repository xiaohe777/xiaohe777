package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.pojo.Role;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    @MapKey("")
    List<Map<String,Object>> selectAllRoles(Integer userId);

    Role selectRolesByRoleName(String roleName);

    Integer deleteRole(Integer roleId);
}