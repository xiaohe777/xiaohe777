package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.bean.Oca;

import java.util.List;

public interface OcaMapper extends BaseMapper<Oca,Integer> {

    List<Oca> selectComByOrder(Integer ingredientsId);
}