package com.its.core.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * 为RedisMybatisCache静态注入jedisConnectionFactory中间类
 */
public class RedisCacheTransfer {
	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisMybatisCache.setJedisConnectionFactory(jedisConnectionFactory);
	}

}