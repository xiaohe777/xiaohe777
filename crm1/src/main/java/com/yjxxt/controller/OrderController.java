package com.yjxxt.controller;


import com.yjxxt.base.BaseController;
import com.yjxxt.base.ResultInfo;
import com.yjxxt.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController {


    @Resource
    private OrderService orderService;


    @Resource
    private HttpSession session;





    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(HttpServletRequest req){
//        return orderService.list(LoginUserUtil.releaseUserIdFromCookie(req));
        return orderService.list(101);
    }


    @RequestMapping("selectComByOrder")
    @ResponseBody
    public Map<String,Object> selectComByOrder(){
        return orderService.selectComByOrder((Integer) session.getAttribute("id"));
    }


    @RequestMapping("selectIngredientsByOrderComId")
    @ResponseBody
    public Map<String,Object> selectIngredientsByOrderComId(){
        return orderService.selectIngredientsByOrderComId((Integer) session.getAttribute("ingredientsId"));
    }


    @RequestMapping("openOrderIncludeCom")
    public String openOrderIncludeCom(Integer id, HttpSession session){
        session.setAttribute("id",id);
        return "buy/openOrderIncludeCom";
    }


    @RequestMapping("openOrderIncludeIngredients")
    public String openOrderIncludeIngredients(Integer id, HttpSession session){
        session.setAttribute("ingredientsId",id);
        return "buy/openOrderIncludeIngredients";
    }


    @RequestMapping("deleteOrder")
    @ResponseBody
    public ResultInfo deleteOrder(Integer id){
        orderService.deleteOrder(id);

        return success("退单成功");
    }

}
