package com.its.test.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.its.common.redis.ShardedJedisSentinelPool;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class ShardedJedisSentinelPoolTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testSpring() {
		ApplicationContext ac = null;
		try {
			ac = new ClassPathXmlApplicationContext("classpath:/redis/applicationContext_redis.xml");
			ShardedJedisSentinelPool pool = (ShardedJedisSentinelPool) ac.getBean("shardedJedisPool");
			ShardedJedis jedis = null;
			jedis = pool.getResource();
			for (int i = 0; i < 10; i++) {
				try {
					jedis = pool.getResource();
					String key = "key" + i;
					jedis.set(key, i + "");
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

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		ShardedJedisSentinelPool pool = null;
		try {
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			List<String> masters = new ArrayList<String>();
			masters.add("its_sentinel");
			Set<String> sentinels = new HashSet<String>();
			sentinels.add("vm-01-ip:26379");
			sentinels.add("vm-02-ip:26379");
			sentinels.add("vm-03-ip:26379");
			String auth = "123456";
			pool = new ShardedJedisSentinelPool(masters, sentinels, config, 60000, auth);
			ShardedJedis j = null;
			for (int i = 0; i < 10; i++) {
				try {
					j = pool.getResource();
					j.set("KEY: " + i, "" + i);
					System.out.print(i);
					System.out.print(" ");
					Thread.sleep(500);
					pool.returnResource(j);
				} catch (JedisConnectionException e) {
					System.out.print(e);
					i--;
					Thread.sleep(1000);
				}
			}

			System.out.println("----------------------------");

			for (int i = 0; i < 10; i++) {
				try {
					j = pool.getResource();
					System.out.print(j.get("KEY: " + i));
					Thread.sleep(500);
					pool.returnResource(j);
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
