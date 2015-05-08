package com.rongzm.math.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.

 If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.
 * Created by rongzhiming on 2015/5/7.
 */
public class SellStock {
    int prices[];
    int lengthMin1;
    int sum = 0;

    Map<Integer,Integer> cache = new HashMap<Integer,Integer>();

    public int maxProfit(int[] prices) {
        this.prices = prices;
        this.lengthMin1 = prices.length - 1;

        for(int i=lengthMin1;i>=0;i--){
            caculate(i);
        }
        return sum;
    }

    int caculate(int point){
        int now = this.prices[point];
        if(point == lengthMin1){
            return now;
        }
        Integer key = point+1;
        Integer last = cache.get(key);
        if(last == null){
            last = caculate(key);
            cache.put(key,last);
        }

        if(now > last){
            return now;
        }else{
            int sum_ = last - now;
            if(sum_ > sum){
                sum = sum_;
            }
            return last;
        }
    }
}
