package com.yjxxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.base.BaseService;
import com.yjxxt.mapper.ModuleMapper;
import com.yjxxt.mapper.PermissionMapper;
import com.yjxxt.mapper.RoleMapper;
import com.yjxxt.pojo.Permission;
import com.yjxxt.pojo.Role;
import com.yjxxt.query.RoleQuery;
import com.yjxxt.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleService extends BaseService<Role,Integer> {


    @Resource
    private RoleMapper roleMapper;


    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;


    /**
     * 查询用户所拥有的角色
     * @return 返回List<Map>
     */
    public List<Map<String,Object>> selectAllRoles(Integer userId){
        return roleMapper.selectAllRoles(userId);
    }


    /**
     * 角色列表查询
     * @param roleQuery 查询条件
     * @return 返回map
     */
    public Map<String,Object>list(RoleQuery roleQuery){
        //实例化分页单位
        PageHelper.startPage(roleQuery.getPage(), roleQuery.getLimit());
        //查询
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleMapper.selectByParams(roleQuery));
        //实例化map
        HashMap<String, Object> map = new HashMap<>();
        //赋值
        map.put("code",0);
        map.put("msg","操作成功");
        map.put("count",rolePageInfo.getTotal());
        map.put("data",rolePageInfo.getList());
        //返回map
        return map;
    }


    /**
     * 添加角色
     * @param role 角色
     */
    public void roleSave(Role role){
        //判断角色名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空");
        //角色是否已经存在
        Role temp = roleMapper.selectRolesByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp,"角色名已存在");
        //设置默认信息
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        //插入
        AssertUtil.isTrue(roleMapper.insertSelective(role) < 1,"添加角色失败");
    }


    /**
     * 修改角色
     * @param role 角色信息
     */
    public void update(Role role) {
        //判断角色名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空");
        //角色是否已经存在
        Role temp = roleMapper.selectRolesByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp && !temp.getId().equals(role.getId()),"角色名已存在");
        //设置修改信息
        role.setUpdateDate(new Date());
        //修改
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1,"修改角色失败");
    }


    /**
     * 删除角色
     * @param roleId 角色id
     */
    public void delete(Integer roleId) {
        //判断角色记录是否为空
        AssertUtil.isTrue(roleId == null || roleMapper.selectByPrimaryKey(roleId) == null,"请选择数据");
        //删除
        AssertUtil.isTrue(roleMapper.deleteRole(roleId) < 1,"删除角色失败");
    }

    /**
     * 给角色添加资源信息
     * @param roleId 角色id
     * @param mIds 资源信息
     */
    public void addGrant(Integer roleId, String[] mIds) {
        //判断角色是否有权限
        Integer count = permissionMapper.selectByRoleId(roleId);
        if (count > 0) {
            //删除角色拥有的所有资源
            AssertUtil.isTrue(permissionMapper.deleteModulesByRoleId(roleId) < count, "操作失败");
        }
        //实例化集合
        List<Permission> list = new ArrayList<>();
        //遍历mIds
        for (String mId : mIds) {
            //分配新的资源
            Permission permission = new Permission();
            permission.setRoleId(roleId);
            permission.setModuleId(Integer.parseInt(mId));
            permission.setAclValue(moduleMapper.selectByPrimaryKey(Integer.parseInt(mId)).getOptValue());
            permission.setCreateDate(new Date());
            permission.setUpdateDate(new Date());
            //存进list
            list.add(permission);
        }
        //批量添加
        AssertUtil.isTrue(permissionMapper.insertBatch(list) < 0,"添加失败");
    }
}
