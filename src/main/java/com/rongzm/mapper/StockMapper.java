package com.rongzm.mapper;

import com.rongzm.entity.Stock;

public interface StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    int selectAmountByItemId(Integer itemId);

    int decreaseAmount(Integer itemId);

    int decreaseAmountStep(Integer itemId, Integer step);
}