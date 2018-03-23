package com.its.test.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisPoolShardedTest {
	private static ShardedJedisPool shardedJedisPool;

	/** 初始化jedis连接池 */
	@Before
	public void initJedisPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(1024);
		jedisPoolConfig.setMaxIdle(200);
		jedisPoolConfig.setMaxWaitMillis(1000);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		String host1 = "vm-01-ip";
		String host2 = "vm-02-ip";
		String host3 = "vm-03-ip";
		int port = 6379;
		int timeout = 1000;
//		String auth = "123456";
		// 构造连接池
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo(host1, port, timeout);
//		jedisShardInfo1.setPassword(auth);
		JedisShardInfo jedisShardInfo2 = new JedisShardInfo(host2, port, timeout);
//		jedisShardInfo1.setPassword(auth);
		JedisShardInfo jedisShardInfo3 = new JedisShardInfo(host3, port, timeout);
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(jedisShardInfo1);
		shards.add(jedisShardInfo2);
		shards.add(jedisShardInfo3);
		shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		ShardedJedis jedis = null;
		try {
			// 从连接池中获取jedis实例
			jedis = shardedJedisPool.getResource();
			String key = "JedisPoolShardedTest_key";
			jedis.set(key, "JedisPoolShardedTest_value");
			System.out.println(jedis.get("JedisPoolShardedTest_key"));
//			jedis.del(key);
			System.out.println(jedis.get("JedisPoolShardedTest_key"));
		} catch (Exception e) {
			shardedJedisPool.returnBrokenResource(jedis);// 销毁对象
			e.printStackTrace();
		} finally {
			shardedJedisPool.returnResource(jedis);// 释放对象池
		}
	}

}
