package com.rongzm.utils;

/**
 * Created by rongzhiming on 2015/5/8.
 */
public class RedisKeyConstants {
    private static final String PREFIX = "fs.";

    public static final KeyBuilder AMOUNT = new KeyBuilder() {
        @Override
        public String key(Object... args) {
            return String.format(PREFIX + "amount_%s",args);
        }
    };


    public static abstract class KeyBuilder{
        public abstract String key(Object... args);
    }
}
