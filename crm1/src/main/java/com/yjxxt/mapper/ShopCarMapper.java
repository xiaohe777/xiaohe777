package com.yjxxt.mapper;

import com.yjxxt.base.BaseMapper;
import com.yjxxt.bean.ShopCar;

import java.util.List;

public interface ShopCarMapper extends BaseMapper<ShopCar,Integer> {


    List<ShopCar> selectAllInShopCar(Integer userId);

    Integer  deleteBycomNameAndSumPrice(Integer markOrderNum);

    Integer setMarkNumById(Integer id, Integer markId);

    List<ShopCar> selectShopCarByMarkOrderNum(Integer markOrderNum);

    List<String> selectAsyIdFromShopCarByMarkOrderNum(Integer markOrderNum);

    Integer deleteByMarkOrderNum(Integer markOrderNum);

}