package com.yjxxt.controller;

import com.yjxxt.annotations.RequirePermission;
import com.yjxxt.base.BaseController;
import com.yjxxt.base.ResultInfo;
import com.yjxxt.model.UserModel;
import com.yjxxt.pojo.User;
import com.yjxxt.query.UserQuery;
import com.yjxxt.service.UserService;
import com.yjxxt.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;


    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param userPwd  密码
     * @return 结果集
     */
    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd) {

        UserModel model = userService.login(userName, userPwd);
        //实例化结果集
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(model);
        return resultInfo;
    }


    /**
     * 修改用户密码
     *
     * @param req        请求对象
     * @param oldPwd     原始密码
     * @param newPwd     新密码
     * @param confirmPwd 确认密码
     * @return 结果集
     */
    @RequestMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPwd(HttpServletRequest req, String oldPwd, String newPwd, String confirmPwd) {
        //实例化结果集
        ResultInfo resultInfo = new ResultInfo();
        //拿到userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        userService.updateUserPassWord(userId, oldPwd, newPwd, confirmPwd);
        //返回结果集
        return resultInfo;
    }


    /**
     * 跳转
     *
     * @return 跳转修改密码页面
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }


    /**
     * 跳转
     *
     * @return 跳转基本信息页面
     */
    @RequestMapping("toSettingPage")
    public String toSettingPage(HttpServletRequest req) {
        //从cookie中拿数据
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        User user = userService.selectByPrimaryKey(userId);
        //存进req
        req.setAttribute("user",user);
        //转发
        return "user/setting";
    }


    /**
     * 修改用户
     * @param user 用户信息
     * @return 返回结果集
     */
    @RequestMapping("updateUser")
    @ResponseBody
    public ResultInfo updateUser(User user){
        //修改
        userService.updateByPrimaryKeySelective(user);
        //存入model方便更新新的cookie
        User temp = userService.selectByPrimaryKey(user.getId());
        //返回结果集
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(temp);
        resultInfo.setMsg("操作成功");
        return resultInfo;
    }

    /**
     * 条件查询用户
     * @param userQuery 用户条件
     * @return 返回map
     */
    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "60")
    public Map<String,Object> list(UserQuery userQuery){
        return userService.list(userQuery);
    }

    /**
     * 跳转
     *
     * @return 跳转用户列表页面
     */
    @RequestMapping("index")
    public String index() {
        return "user/user";
    }


    /**
     * 用户添加
     * @param user 用户信息
     * @param roleIds 用户所添加的角色id数组
     * @return 结果集
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(User user,Integer[] roleIds){
        userService.save(user,roleIds);
        return success("添加成功");
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 结果集
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(User user,Integer[] roleIds){
        userService.update(user, roleIds);
        return success("修改成功");
    }

    /**
     * 跳转添加或修改用户页面
     * @return 返回页面
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addOrUpdateUserPage(Integer id,HttpServletRequest req){
        //查看id有没有，若能查到用户，则为修改，没有则为添加
        User user = userService.selectByPrimaryKey(id);
        if(user != null) {
            //修改，把用户信息存进req
            req.setAttribute("user",user);
        }
        return "user/add_update";
    }

    /**
     * 批量删除用户
     * @param ids 用户的id数组
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer[] ids){
        //调用UserService
        userService.delete(ids);
        //返回结果集
        return success("删除成功");
    }
}
