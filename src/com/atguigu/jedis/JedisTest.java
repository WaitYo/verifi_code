package com.atguigu.jedis;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
     Jedis jedis = new Jedis("192.168.200.129", 6379);
     System.out.println(jedis.ping());
     jedis.set("k100", "v100");
     System.out.println(jedis.get("k100"));
     Set<String> keys = jedis.keys("*");
     for (String key : keys) {
    	 System.out.println(key);
	}
     System.out.println("111111111"+jedis.exists("j1"));
     jedis.close();
	}

}
