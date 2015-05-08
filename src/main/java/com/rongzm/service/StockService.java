package com.rongzm.service;

import com.rongzm.entity.Stock;

/**
 * Created by rongzhiming on 2015/5/8.
 */
public interface StockService {
    Integer queryAmount(int itemId);

    boolean decreaseAmount(int itemId, int step);

    void add(Stock stock);

    void increaseAmount(int itemId, int step);
}
