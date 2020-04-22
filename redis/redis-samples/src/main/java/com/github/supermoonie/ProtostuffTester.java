package com.github.supermoonie;

import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;

/**
 * @author supermoonie
 * @since 2020/4/20
 */
public class ProtostuffTester {

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setName("tom");
        user.setAge(20);
        user.setGender(1);
        System.out.println(user);
        ProtostuffSerializer protostuffSerializer = new ProtostuffSerializer();
        byte[] userBytes = protostuffSerializer.serialize(user, User.SCHEMA);
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        byte[] key = "user:1".getBytes(StandardCharsets.UTF_8);
        jedis.set(key, userBytes);
        byte[] bytes = jedis.get(key);
        User resultUser = protostuffSerializer.deserializer(bytes, User.SCHEMA);
        System.out.println(resultUser);
        System.out.println(user.equals(resultUser));
        System.out.println(user.hashCode());
        System.out.println(resultUser.hashCode());
    }
}
