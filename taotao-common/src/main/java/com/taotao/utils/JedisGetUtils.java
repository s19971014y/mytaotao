package com.taotao.utils;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class JedisGetUtils {
    static{
        //单机版
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(100000);
        jedisPool = new JedisPool(config,"47.101.212.18",7000);
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
