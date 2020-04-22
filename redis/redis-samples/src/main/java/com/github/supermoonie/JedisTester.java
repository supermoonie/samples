package com.github.supermoonie;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 */
public class JedisTester {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("hello", "world");
        System.out.println(jedis.get("hello"));
        jedis.incr("counter");
        System.out.println(jedis.get("counter"));
        jedis.hset("myHash", "f1", "v1");
        jedis.hset("myHash", "f2", "v2");
        System.out.println(jedis.hgetAll("myHash"));
        jedis.rpush("myList", "1");
        jedis.rpush("myList", "2");
        jedis.rpush("myList, ", "3");
        System.out.println(jedis.lrange("myList", 0, -1));
        jedis.sadd("mySet", "a");
        jedis.sadd("mySet", "b");
        jedis.sadd("mySet", "c");
        System.out.println(jedis.smembers("mySet"));
        jedis.zadd("myZset", 1, "a");
        jedis.zadd("myZset", 2, "b");
        jedis.zadd("myZset", 3, "c");
        System.out.println(jedis.zrangeWithScores("myZset", 0, -1));
    }
}
