package com.taotao.utils;

import redis.clients.jedis.Jedis;

public class JedisUtils{

    private static Jedis jedis = JedisGetUtils.getJedis();

    public static String set(String key, String value){
        return jedis.set(key,value);
    }
    public  static  String get(String key){
        return jedis.get(key);
    }
    public static Boolean exists(String key){
        return jedis.exists(key);
    }
    public static Long expire(String key, int seconds){
        return jedis.expire(key,seconds);
    }
    public  static Long ttl(String key){
        return jedis.ttl(key);
    }
    public  static  Long incr(String key){
        return jedis.incr(key);
    }
    public  static Long hset(String key, String field, String value){
        return jedis.hset(key, field, value);
    }
    public  static String hget(String key, String field){
        return jedis.hget(key, field);
    }
    public  static Long hdel(String key, String... field){
        return jedis.hdel(key, field);

    }
    public static Long del(String key){
        return jedis.del(key);
    };

}
