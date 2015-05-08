package com.rongzm.service.impl;

import com.rongzm.entity.Stock;
import com.rongzm.service.StockService;
import com.rongzm.utils.Constants;
import com.rongzm.utils.RedisKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by rongzhiming on 2015/5/8.
 */
@Service("StockServiceCacheImpl")
public class StockServiceCacheImpl implements StockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StockService stockService;

    @Override
    public Integer queryAmount(int itemId) {
        String key = RedisKeyConstants.AMOUNT.key(itemId);
        BoundValueOperations<String,String> operations = redisTemplate.boundValueOps(key);
        String amount = operations.get();
        if(amount == null){
            Integer amountDb = stockService.queryAmount(itemId);
            if(amountDb == null){
                return null;
            }

            Boolean noConcurrent = operations.setIfAbsent(String.valueOf(amountDb));//并发
            if(noConcurrent){
                operations.expire(Constants.REDIS_TIME_OUT, TimeUnit.MINUTES);
                return amountDb;
            }else{
                amount = operations.get();
                if(amount != null){
                    return Integer.valueOf(amount);
                }
            }
        }else{
            return Integer.valueOf(amount);
        }

        return null;
    }

    @Override
    public boolean decreaseAmount(int itemId, int step) {



        return false;
    }

    @Override
    public void add(Stock stock) {

    }

    @Override
    public void increaseAmount(int itemId, int step) {

    }
}
