package com.yjxxt.controller;

import com.yjxxt.base.BaseController;
import com.yjxxt.base.ResultInfo;
import com.yjxxt.pojo.Role;
import com.yjxxt.query.RoleQuery;
import com.yjxxt.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {


    @Resource
    private RoleService roleService;

    /**
     * 查用户所有角色
     * @return 返回List<Map>
     */
    @RequestMapping("allRoles")
    @ResponseBody
    public List<Map<String,Object>> selectAllRoles(Integer userId){
        return roleService.selectAllRoles(userId);
    }

    /**
     * 跳转
     * @return 返回试图
     */
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }


    /**
     * 角色列表查询
     * @param roleQuery 查询条件
     * @return 返回map
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery roleQuery){
        return roleService.list(roleQuery);
    }


    /**
     * 添加角色
     * @param role 角色信息
     * @return 返回结果集
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo roleSave(Role role){
        roleService.roleSave(role);
        return success("添加成功");
    }

    /**
     * 修改角色
     * @param role 角色信息
     * @return 返回结果集
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Role role){
        roleService.update(role);
        return success("修改成功");
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return 返回结果集
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer roleId){
        roleService.delete(roleId);
        return success("删除成功");
    }


    /**
     * 跳转到添加修改页面
     * @param id 角色id
     * @param req 请求
     * @return 返回试图
     */
    @RequestMapping("addOrUpdateRolePage")
    public String addOrUpdateRolePage(Integer id, HttpServletRequest req){
        //查id
        Role role = roleService.selectByPrimaryKey(id);
        //判断是否存在
        if(role != null) {
            req.setAttribute("role",role);
        }
        return "role/add_update";
    }

    /**
     * 跳转到授权页面
     * @param roleId 授权的角色id
     * @param req 请求
     * @return 返回试图
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId,HttpServletRequest req){
        req.setAttribute("roleId",roleId);
        return "role/grant";
    }


    /**
     * 给角色添加资源信息
     * @param roleId 角色id
     * @param mIds 资源信息
     * @return 返回结果集
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId, String[] mIds){
        roleService.addGrant(roleId,mIds);
        return success("操作成功");
    }

}
