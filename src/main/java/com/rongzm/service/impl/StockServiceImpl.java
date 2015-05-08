package com.rongzm.service.impl;

import com.rongzm.entity.Stock;
import com.rongzm.mapper.StockMapper;
import com.rongzm.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rongzhiming on 2015/5/8.
 */
@Service("StockServiceImpl")
public class StockServiceImpl implements StockService{
    @Autowired
    private StockMapper stockMapper;

    @Override
    public Integer queryAmount(int itemId) {
        return stockMapper.selectAmountByItemId(itemId);
    }

    @Override
    public boolean decreaseAmount(int itemId, int num) {
        int result;
        if(num == 1){
            result = stockMapper.decreaseAmount(itemId);
        }else{
            result = stockMapper.decreaseAmountStep(itemId,num);
        }
        if(result > 0){
            return true;
        }
        return false;
    }

    @Override
    public void add(Stock stock) {
        stockMapper.insertSelective(stock);
    }

    @Override
    public void increaseAmount(int itemId, int num) {

    }
}
