package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.bean.Com;

import java.util.List;

public interface BuyMapper extends BaseMapper<Com,Integer> {

    List<Com> selectAllCom();

    Com selectByComName(String comName);
}