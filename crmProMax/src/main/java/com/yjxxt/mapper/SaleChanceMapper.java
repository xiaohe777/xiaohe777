package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.pojo.SaleChance;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

    @MapKey("")
    List<Map<String, Object>> salesList();
}