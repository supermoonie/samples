package com.github.supermoonie;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author chao.wang
 * @since 2020/4/24
 */
public class JedisPoolTester {

    public static void main(String[] args) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE + 3);
        // 是否开启jmx 监控，如果开启了，可以通过jconsole 或者 jvisualvm 看到关于连接池的统计
        poolConfig.setJmxEnabled(true);
        poolConfig.setMaxWaitMillis(3000);
        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        try (Jedis jedis = jedisPool.getResource()) {
            String ping = jedis.ping();
            System.out.println(ping);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
