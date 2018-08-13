package com.its.core.cache.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Mybatis使用Redis实现cache
 *
 */
@SuppressWarnings("deprecation")
public class RedisMybatisCache implements Cache {
	private static final Logger logger = LoggerFactory.getLogger(RedisMybatisCache.class);

	private static JedisConnectionFactory jedisConnectionFactory;

	private final String id;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public RedisMybatisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug("MybatisRedisCache:id=" + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		readWriteLock.writeLock().lock();
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
//			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			connection.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
			logger.info("添加mybaits二级缓存key=" + key + ",value=" + value);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public Object getObject(Object key) {
		// 先从缓存中去取数据,先加上读锁
		readWriteLock.readLock().lock();
		Object result = null;
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = serializer.deserialize(connection.get(serializer.serialize(key)));
			logger.info("命中mybaits二级缓存,value=" + result);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			readWriteLock.readLock().unlock();
		}
		return result;
	}

	@Override
	public Object removeObject(Object key) {
		readWriteLock.writeLock().lock();
		JedisConnection connection = null;
		Object result = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = connection.expire(serializer.serialize(key), 0);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			readWriteLock.writeLock().unlock();
		}
		return result;
	}

	/** 清空所有缓存 */
	@Override
	public void clear() {
		readWriteLock.readLock().lock();
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			connection.flushDb();
			connection.flushAll();
			logger.info("----清空所有缓存----");
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			readWriteLock.readLock().unlock();
		}
	}

	/** 获取缓存总数量 */
	@Override
	public int getSize() {
		int result = 0;
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			result = Integer.valueOf(connection.dbSize().toString());
			logger.info("添加mybaits二级缓存数量：" + result);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisMybatisCache.jedisConnectionFactory = jedisConnectionFactory;
	}
}