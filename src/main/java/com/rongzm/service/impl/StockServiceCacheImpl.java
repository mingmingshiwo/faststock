package com.rongzm.service.impl;

import com.rongzm.entity.Stock;
import com.rongzm.service.StockService;
import com.rongzm.utils.Constants;
import com.rongzm.utils.RedisKeyConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
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
    public boolean decreaseAmount(int itemId, final int step) {
        String key = RedisKeyConstants.AMOUNT.key(itemId);
        final BoundValueOperations<String,String> operations = redisTemplate.boundValueOps(key);

        int retry = 0;
        while(retry <= Constants.REDIS_RETRY){
            redisTemplate.watch(key);
            int amount = queryAmount(itemId);
            if(amount < step){
                break;
            }
            List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations oper) throws DataAccessException {
                    redisTemplate.multi();
                    operations.increment(-step);
                    return redisTemplate.exec();
                }
            });
            log.info("[exec]" + txResults);
            if(CollectionUtils.isNotEmpty(txResults) && txResults.get(0) != null){
                log.info("[suc]" + retry);
                return true;
            }else{
                retry++;
            }
        }

        log.info("[fail]" + retry);
        redisTemplate.unwatch();
        return false;
    }

    @Override
    public void add(Stock stock) {

    }

    @Override
    public void increaseAmount(int itemId, int step) {

    }
}
