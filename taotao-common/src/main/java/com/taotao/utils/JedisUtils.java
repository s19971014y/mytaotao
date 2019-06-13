package com.taotao.utils;

import redis.clients.jedis.Jedis;

public class JedisUtils{

    private static Jedis jedis = JedisGetUtils.getJedis();

    public static String set(String key, String value){
        String result = jedis.set(key,value);
        return result;
    }
    public  static  String get(String key){
        String result = jedis.get(key);
        return result;
    }
    public static Boolean exists(String key){
        Boolean result = jedis.exists(key);
        return result;
    }
    public static Long expire(String key, int seconds){
        Long result = jedis.expire(key,seconds);
        return result;
    }
    public  static Long ttl(String key){
        Long result = jedis.ttl(key);
        return result;
    }
    public  static  Long incr(String key){
        Long result = jedis.incr(key);
        return result;
    }
    public  static Long hset(String key, String field, String value){

        Long result = jedis.hset(key,field,value);
        return result;
    }
    public  static String hget(String key, String field){
        String result = jedis.hget(key, field);
        return result;
    }
    public  static Long hdel(String key, String... field){
        Long result = jedis.hdel(key, field);
        return result;

    }
    public static Long del(String key){
        Long result = jedis.del(key);
        return result;
    };

}
