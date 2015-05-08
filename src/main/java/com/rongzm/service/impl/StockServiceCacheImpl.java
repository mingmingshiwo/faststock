package com.rongzm.service.impl;

import com.rongzm.entity.Stock;
import com.rongzm.service.StockService;
import com.rongzm.utils.Constants;
import com.rongzm.utils.RedisKeyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rongzhiming on 2015/5/8.
 */
@Service(value = "StockServiceCacheImpl")
public class StockServiceCacheImpl implements StockService {

    private Logger log = LoggerFactory.getLogger(getClass());

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
                if(amount == null){
                    throw new RuntimeException("");
                }
                return Integer.valueOf(amount);
            }
        }else{
            return Integer.valueOf(amount);
        }
    }

    @Override
    public boolean decreaseAmount(int itemId, int step) {
        String key = RedisKeyConstants.AMOUNT.key(itemId);
        redisTemplate.watch(key);



        redisTemplate.multi();
        BoundValueOperations<String,String> operations = redisTemplate.boundValueOps(key);
        operations.increment(-step);
        List<Object> result = redisTemplate.exec();
        log.debug("[exec]" + result);



        return false;
    }

    @Override
    public void add(Stock stock) {

    }

    @Override
    public void increaseAmount(int itemId, int step) {

    }
}
