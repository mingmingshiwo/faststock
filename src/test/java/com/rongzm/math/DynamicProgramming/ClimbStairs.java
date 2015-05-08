package com.rongzm.math.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rongzhiming on 2015/5/6.
 */
public class ClimbStairs {
    Map<Integer,Integer> cache = new HashMap<Integer,Integer>();

    public int climbStairs(int n) {
        if(n == 1){
            return 1;
        }else if(n == 2){
            return 2;
        }

        int r1 = get(n - 1);
        int r2 = get(n-2);
        return r1 + r2;
    }

    private int get(int n){
        Integer result = cache.get(n);
        if(result == null){
            result = climbStairs(n);
            cache.put(n, result);
        }
        return result;
    }

    public static void main(String[] args) {
        ClimbStairs dp = new ClimbStairs();
        System.out.println(dp.climbStairs(44));
    }
}
