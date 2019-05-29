package com.taotao.utils;

import redis.clients.jedis.JedisCluster;

public class JedisClusterUtils{

    private static JedisCluster jedisCluster = JedisGetUtils.getJedisCluster();

    public static String set(String key, String value){
        return jedisCluster.set(key,value);
    }
    public  static  String get(String key){
        return jedisCluster.get(key);
    }
    public static Boolean exists(String key){
        return jedisCluster.exists(key);
    }
    public static Long expire(String key, int seconds){
        return jedisCluster.expire(key,seconds);
    }
    public  static Long ttl(String key){
        return jedisCluster.ttl(key);
    }
    public  static  Long incr(String key){
        return jedisCluster.incr(key);
    }
    public  static Long hset(String key, String field, String value){
        return jedisCluster.hset(key, field, value);
    }
    public  static String hget(String key, String field){
        return jedisCluster.hget(key, field);
    }
    public  static Long hdel(String key, String... field){
        return jedisCluster.hdel(key, field);
    }
}
