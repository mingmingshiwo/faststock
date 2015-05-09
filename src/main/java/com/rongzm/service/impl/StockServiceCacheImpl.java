package com.rongzm.service.impl;

import com.rongzm.entity.Stock;
import com.rongzm.pojo.AmountResult;
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

    private final static Long AMOUNT_NOT_ENOUGH = -999l;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StockService stockService;

    @Override
    public Integer queryAmount(int itemId) {
        AmountResult amountResult = queryAmount_(itemId);
        return amountResult.getAmount();
    }

    public AmountResult queryAmount_ (int itemId){
        String key = RedisKeyConstants.AMOUNT.key(itemId);
        BoundValueOperations<String,String> operations = redisTemplate.boundValueOps(key);
        String amount = operations.get();
        if(amount == null){
            Integer amountDb = stockService.queryAmount(itemId);
            if(amountDb == null){
                throw new RuntimeException("stock not exist");
            }

            Boolean noConcurrent = operations.setIfAbsent(String.valueOf(amountDb));//并发
            if(noConcurrent){
                //how to return
                operations.expire(Constants.REDIS_TIME_OUT, TimeUnit.MINUTES);
                return new AmountResult(amountDb,true);
            }else{
                amount = operations.get();
                if(amount == null){
                    throw new RuntimeException("Value not exist. But expect exist!");
                }
                return new AmountResult(Integer.valueOf(amount), false);
            }
        }else{
            return new AmountResult(Integer.valueOf(amount), false);
        }
    }

    @Override
    public boolean decreaseAmount(final int itemId, final int step) {
        final String key = RedisKeyConstants.AMOUNT.key(itemId);

        int retry = 0;
        while(retry <= Constants.REDIS_RETRY){
            Long txResult = redisTemplate.execute(new SessionCallback<Long>() {
                @Override
                @SuppressWarnings("unchecked")
                public Long execute(RedisOperations operations) throws DataAccessException {
                    operations.watch(key);
                    AmountResult amountResult = queryAmount_(itemId);
                    if (amountResult.isInitCache()) {
                        operations.watch(key);
                        amountResult = queryAmount_(itemId);
                    }
                    Integer amount = amountResult.getAmount();
                    if (amount == null || amount < step) {
                        operations.unwatch();
                        return AMOUNT_NOT_ENOUGH;
                    }

                    operations.multi();
                    operations.opsForValue().increment(key, -step);
                    List<Object> list = operations.exec();
                    if (CollectionUtils.isEmpty(list)) {
                        return null;
                    } else {
                        return (Long) list.get(0);
                    }
                }
            });

            if(txResult == null){//transaction fail
                retry++;
            }else if(AMOUNT_NOT_ENOUGH.equals(txResult)){
                break;
            }else{
                log.info("suc [oldAmount]{} [retry]{}",txResult, retry);
                return true;
            }
        }

        log.info("fail [retry]{}", retry);
        return false;
    }

    @Override
    public void add(Stock stock) {

    }

    @Override
    public void increaseAmount(int itemId, int step) {

    }
}
