package com.its.test.redis;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: JedisPool
 */
public class JedisPoolTest {
	private static JedisPool jedisPool;

	/** 初始化jedis连接池 */
	@Before
	public void initJedisPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(1024);
		jedisPoolConfig.setMaxIdle(200);
		jedisPoolConfig.setMaxWaitMillis(1000);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		String host = "vm-01-ip";
		int port = 6379;
		int timeout = 1000;
		String password = "123456";
		// 构造连接池
		jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
	}

	@Test
	public void testSet() {
		Jedis jedis = null;
		try {
			// 从连接池中获取jedis实例
			jedis = jedisPool.getResource();
			String key = "Test_Pool";
			jedis.set(key, "Test_Pool");
			System.out.println(jedis.get("Test_Pool"));
			jedis.del(key);
			System.out.println(jedis.get("Test_Pool"));
		} catch (Exception e) {
		    // 销毁对象
		    jedis.close();
			e.printStackTrace();
		} finally {
		    // 释放对象池
		    jedis.close();
		}
	}
}
