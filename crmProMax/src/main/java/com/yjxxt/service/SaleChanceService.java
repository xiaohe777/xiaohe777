package com.yjxxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.base.BaseService;
import com.yjxxt.mapper.SaleChanceMapper;
import com.yjxxt.pojo.SaleChance;
import com.yjxxt.query.SaleChanceQuery;
import com.yjxxt.utils.AssertUtil;
import com.yjxxt.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {


    @Resource
    private SaleChanceMapper saleChanceMapper;


    /**
     * 按照条件查询
     * @param saleChanceQuery 查询条件
     * @return 返回map
     */
    public Map<String,Object> list(SaleChanceQuery saleChanceQuery){
        //实例化分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        //查询
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
        //实例化map
        Map<String,Object> map = new HashMap<>();
        //存值
        map.put("code",0);
        map.put("msg","操作成功");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        //返回map
        return map;
    }


    /**
     * 营销机会添加
     * @param saleChance 营销机会
     */
    public void save(SaleChance saleChance) {
        //参数校验
        checkSaleChanceParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        //设置默认信息   状态->未分配  开发结果->未开发
        saleChance.setState(0);
        saleChance.setDevResult(0);
        //判断指派人是否为空
        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
            //状态->已分配  开发结果->开发中  创建时间->当前时间
            saleChance.setState(1);
            saleChance.setDevResult(1);
            saleChance.setAssignTime(new Date());
        }
        //设置其他信息
        saleChance.setIsValid(1);
        saleChance.setUpdateDate(new Date());
        saleChance.setCreateDate(new Date());
        //添加
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) < 1, "添加失败");
    }

    /**
     * 营销机会修改
     * @param saleChance 营销机会
     */
    public void update(SaleChance saleChance){
        //查询用户
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp == null ,"待修改用户不存在");
        //校验参数
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //修改参数
        //未分配
        if(StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setDevResult(1);
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        //已经分配
        if(StringUtils.isBlank(saleChance.getAssignMan()) && StringUtils.isNotBlank(temp.getAssignMan())) {
            saleChance.setState(0);
            saleChance.setDevResult(0);
            saleChance.setAssignTime(null);
            saleChance.setAssignMan("");
        }
        //设置其他信息
        saleChance.setUpdateDate(new Date());
        //修改
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) < 1,"修改失败");
    }

    /**
     * 参数校验
     * @param customerName 客户名
     * @param linkMan 联系人
     * @param linkPhone 联系电话
     */
    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        //判断非空
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空");
        //手机号校验
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"请输入合法手机号");
    }

    /**
     * 查询所有销售人员
     * @return 返回包含销售人员的map
     */
    public List<Map<String,Object>> salesList(){
        return saleChanceMapper.salesList();
    }


    /**
     * 删除营销机会
     * @param ids id数组
     */
    public void delete(Integer[] ids) {
        //校验
        AssertUtil.isTrue(ids.length == 0 || ids == null,"请选择数据");
        //删除
        AssertUtil.isTrue(deleteBatch(ids) < ids.length,"删除失败");
    }
}
