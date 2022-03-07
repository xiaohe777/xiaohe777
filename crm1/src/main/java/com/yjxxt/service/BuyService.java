package com.yjxxt.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.base.BaseService;
import com.yjxxt.bean.Com;
import com.yjxxt.mapper.BuyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class BuyService extends BaseService<Com,Integer> {


    @Resource
    private BuyMapper buyMapper;

    public Map<String, Object> selectAllCom() {

        //实例化分页
        PageHelper.startPage(1,10);

        PageInfo<Com> pageInfo = new PageInfo<>(buyMapper.selectAllCom());

        Map<String,Object> map = new HashMap<>();

        map.put("code",0);
        map.put("msg","查询成功");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());

        return map;
    }
}
