package com.rongzm.math.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rongzhiming on 2015/5/6.
 */
public class HouseRobber {

    Map<String,Integer> cache = new HashMap<String,Integer>();

    public int rob(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }else if(nums.length == 1){
            return nums[0];
        }
        if(nums.length == 2){
            if(nums[0] >= nums[1]){
                return nums[0];
            }else{
                return nums[1];
            }
        }

        int r1 = nums[0] + get(subarray(nums, 2, nums.length));
        int r2 = get(subarray(nums, 1, nums.length));
        if(r1 >= r2){
            return r1;
        }else{
            return r2;
        }
    }

    Integer get(int[] nums){
        StringBuilder sb = new StringBuilder();
        for(int num: nums){
            sb.append(num);
        }
        String key = sb.toString();
        Integer result = cache.get(key);
        if(result == null){
            result = rob(nums);
            cache.put(key,result);
        }
        return result;
    }

    static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if(array == null) {
            return null;
        } else {
            if(startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if(endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if(newSize <= 0) {
                return new int[0];
            } else {
                int[] subarray = new int[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }
}
