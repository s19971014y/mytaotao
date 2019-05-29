package com.taotao.utils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisGetUtils {
    static{
        //单机版
        jedisPool = new JedisPool("47.101.212.18",6379);
        //集群版
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("47.101.212.18",7001));
        set.add(new HostAndPort("47.101.212.18",7002));
        set.add(new HostAndPort("47.101.212.18",7003));
        set.add(new HostAndPort("47.101.212.18",7004));
        set.add(new HostAndPort("47.101.212.18",7005));
        set.add(new HostAndPort("47.101.212.18",7006));
        jedisCluster = new JedisCluster(set);
    }

    //集群版Jedis
    private static JedisCluster jedisCluster;
    //单机版的Jedis池
    private static JedisPool jedisPool;
    //单机的Jedis
    private static Jedis jedis = jedisPool.getResource();

    public static Jedis getJedis() {
        return jedis;
    }
    public static JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public static JedisPool getJedisPool() {
        return jedisPool;
    }
}
