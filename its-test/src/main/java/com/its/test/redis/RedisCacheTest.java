package com.its.test.redis;

import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.its.framework.cacheproxy.ICache;
import com.its.framework.cacheproxy.redis.RedisCache;
import com.its.framework.cacheproxy.redis.RedisConfig;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: RedisCacheTest
 */
public class RedisCacheTest {

	@Test
	public void testSpring() {
		ApplicationContext ac = null;
		RedisCache redisCache = null;
		try {
			ac = new ClassPathXmlApplicationContext("classpath:/redis/application-cache.xml");
			redisCache = (RedisCache) ac.getBean("redisCache");
			String key = "Test1";
			redisCache.set(key, "Test01015698你好");
			Object value = redisCache.get(key);
			System.out.println(value);
			redisCache.remove(key);
			value = redisCache.get(key);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		RedisConfig cfg = new RedisConfig();
		cfg.setServers("vm-01-ip:26379,vm-02-ip:26379,vm-03-ip:26379");
		cfg.setMasters("its_sentinel");
		cfg.setPassword("123456");
		ICache c = new RedisCache(cfg);
		Set<String> sets = (Set<String>) c.keys("*");
		System.out.println("key size:" + sets.size());
		for (String str : sets) {
			System.out.println("key:" + str);
		}
		String key = "Test2";
		// set
		c.set(key, "Test010156xx98你好");
		// get
		Object value = c.get(key);
		System.out.println(value);
		// delete
		c.remove(key);
		value = c.get(key);
		System.out.println(value);
	}
}
