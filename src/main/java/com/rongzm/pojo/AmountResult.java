package com.rongzm.pojo;

/**
 * Created by rongzhiming on 2015/5/9.
 */
public class AmountResult {
    Integer amount;
    boolean initCache;

    public AmountResult(Integer amount, boolean initCache) {
        this.amount = amount;
        this.initCache = initCache;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isInitCache() {
        return initCache;
    }

    public void setInitCache(boolean initCache) {
        this.initCache = initCache;
    }
}
