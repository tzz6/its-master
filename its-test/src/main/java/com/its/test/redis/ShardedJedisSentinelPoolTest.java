package com.its.test.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class ShardedJedisSentinelPoolTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		ApplicationContext ac = null;
		try {
			ac = new ClassPathXmlApplicationContext("classpath:/redis/applicationContext_redis.xml");
			ShardedJedisSentinelPool pool = (ShardedJedisSentinelPool) ac.getBean("shardedJedisPool");
			ShardedJedis jedis = null;
			System.out.println("---------------------");
			for (int i = 0; i < 100; i++) {
				try {
					jedis = pool.getResource();
					String key = "key" + i;
					jedis.set(key, i+"");
					System.out.println(jedis.get(key));
					jedis.del(key);
					System.out.println(jedis.get(key));
					Thread.sleep(100);
					pool.returnResource(jedis);
				} catch (JedisConnectionException e) {
					System.out.print("x");
					i--;
					Thread.sleep(1000);
				}
			}
			pool.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
