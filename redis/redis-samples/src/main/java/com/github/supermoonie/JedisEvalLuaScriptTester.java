package com.github.supermoonie;

import redis.clients.jedis.Jedis;

/**
 * @author supermoonie
 * @since 2020/4/26
 */
public class JedisEvalLuaScriptTester {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String script = "return redis.call('get', KEYS[1])";
        Object value = jedis.eval(script, 1, "hello");
        System.out.println(value);
        String scriptSha = jedis.scriptLoad(script);
        value = jedis.evalsha(scriptSha, 1, "hello");
        System.out.println(value);
    }
}
