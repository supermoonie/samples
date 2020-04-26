package com.github.supermoonie;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * @author supermoonie
 * @since 2020/4/26
 */
public class JedisPiplineTester {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 10; i ++) {
            pipeline.set("key-" + i, "value-" + i);
        }
        List<Object> objects = pipeline.syncAndReturnAll();
        objects.forEach(System.out::println);
        for (int i = 0; i < 10; i ++) {
            pipeline.del("key-" + i);
        }
        pipeline.sync();
    }
}
