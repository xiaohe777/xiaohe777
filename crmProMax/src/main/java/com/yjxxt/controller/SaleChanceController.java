package com.yjxxt.controller;


import com.yjxxt.base.BaseController;
import com.yjxxt.base.ResultInfo;
import com.yjxxt.pojo.SaleChance;
import com.yjxxt.pojo.User;
import com.yjxxt.query.SaleChanceQuery;
import com.yjxxt.service.SaleChanceService;
import com.yjxxt.service.UserService;
import com.yjxxt.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {


    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;


    /**
     * 按条件查询
     * @param saleChanceQuery 查询条件
     * @return 返回map
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(SaleChanceQuery saleChanceQuery){
        return saleChanceService.list(saleChanceQuery);
    }


    /**
     * 跳转页面
     * @return 转发
     */
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    /**
     * 添加营销机会
     * @param saleChance 营销机会
     * @return 返回结果集
     */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(SaleChance saleChance, HttpServletRequest req){
        //从cookie找出当前创建人
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        User user = userService.selectByPrimaryKey(userId);
        //设置创建人
        saleChance.setCreateMan(user.getUserName());
        //调用方法添加
        saleChanceService.save(saleChance);
        //返回结果
        return success("添加成功");
    }


    /**
     * 修改营销机会
     * @param saleChance 营销机会
     * @return 返回结果集
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(SaleChance saleChance){
        //调用方法
        saleChanceService.update(saleChance);
        //返回
        return success("修改成功");
    }

    /**
     * 跳转到增加或修改页面
     * @param id 前台传来的编号，若有则为修改，没有则为添加
     * @param req 请求头
     * @return 返回页面
     */
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id,HttpServletRequest req){
        //查用户
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        //存
        req.setAttribute("saleChance",saleChance);
        //返回
        return "saleChance/add_update";
    }

    /**
     * 返回所有销售人员
     * @return 返回包含销售人员的map
     */
    @RequestMapping("sales")
    @ResponseBody
    public List<Map<String,Object>> salesList(){
        return saleChanceService.salesList();
    }


    /**
     * 删除营销机会
     * @param ids id数组
     * @return 结果集
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer[] ids){
        saleChanceService.delete(ids);
        return success("删除成功");
    }
}
