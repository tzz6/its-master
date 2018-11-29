package com.its.test.lock.redis;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Redis分布式锁
 *
 */
public class RedisDistributedLock {
	private static final Logger logger = Logger.getLogger(RedisDistributedLock.class);
	private static JedisPool jedisPool = null;
//	private static JedisPool pool = null;
//
//	public RedisDistributedLock(JedisPool jedisPool) {
//		this.jedisPool = jedisPool;
//	}
	
	static {
		// 初始化Redis
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000);// 设置最大连接数（redis服务端maxclients设置的是1000，可更改设置）
		config.setMaxIdle(8);// 设置最大空闲数
		config.setMaxWaitMillis(1000 * 100);// 设置最大等待时间
		config.setTestOnBorrow(true);// 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
		String host = "vm-02-ip";
		int port = 6379;
		int timeout = 3000;
		String password = "123456";
		jedisPool = new JedisPool(config, host, port, timeout, password);// 构造连接池
	}


	/**
	 * 加锁
	 * 
	 * @param lockKey 锁的key
	 * @param acquireTimeout 获取超时时间
	 * @param timeout redis锁的超时时间
	 * @return 锁标识
	 */
	public String lockWithTimeout(String lockKey, long acquireTimeout, long timeout) {
		Jedis conn = null;
		String retIdentifier = null;
		try {
//			logger.info("ThreadName:" + Thread.currentThread().getName() + "---ilockWithTimeout--1");
			conn = jedisPool.getResource();// 获取连接
			String identifier = UUID.randomUUID().toString();// 随机生成一个value
			int lockExpire = (int) (timeout / 1000);// 超时时间，上锁后超过此时间则自动释放锁
			long end = System.currentTimeMillis() + acquireTimeout;// 获取锁的超时时间，超过这个时间则放弃获取锁
			while (System.currentTimeMillis() < end) {
				// SETNX key val  当且仅当key不存在时，set一个key为val的字符串，返回1；若key存在，则什么都不做，返回0。
				long setNx = conn.setnx(lockKey, identifier);
				String threadName = Thread.currentThread().getName();
				if (setNx == 1) {
					logger.info("ThreadName:" + threadName + "---identifier:" + identifier + "---setNx:" + setNx);
					// expire key timeout 为key设置一个超时时间，单位为second，超过这个时间锁会自动释放，避免死锁。
					long expire = conn.expire(lockKey, lockExpire);
					retIdentifier = identifier;
					logger.info("ThreadName:" + threadName + "---" + conn.get(lockKey) + "---" + expire + "---获得锁---1");
					return retIdentifier;
				}else{
//					logger.info("ThreadName:" + threadName + "---identifier:" + identifier + "---setNx:" + setNx);
				}
				// 返回-1代表key没有设置超时时间，为key设置一个超时时间
				if (conn.ttl(lockKey) == -1) {
					conn.expire(lockKey, lockExpire);
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}
		} catch (JedisException e) {
			logger.info("Exception", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		logger.info("ThreadName:" + Thread.currentThread().getName() + "---获得锁---2");
		return retIdentifier;
	}

	/**
	 * 释放锁（带事务）
	 * 
	 * @param lockKey 锁的key
	 * @param identifier 释放锁的标识
	 * @return
	 */
	public boolean releaseLockTransaction(String lockKey, String identifier) {
		Jedis jedisConn = null;
		boolean retFlag = false;
		try {
			jedisConn = jedisPool.getResource();
			while (true) {
//		logger.info("ThreadName:" + Thread.currentThread().getName() + "---releaseLock--1");
				// 监视lock，准备开始事务
				String watch = jedisConn.watch(lockKey);
				String value = jedisConn.get(lockKey);
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				logger.info("ThreadName:" + Thread.currentThread().getName() + "---identifier:" + identifier
						+ "---watch" + watch + "---value:" + value);
				if (identifier != null && identifier.equals(value)) {
					Transaction transaction = jedisConn.multi();
					transaction.del(lockKey);
					List<Object> results = transaction.exec();
					if (results == null || results.size() == 0) {
						logger.info("ThreadName:" + Thread.currentThread().getName() + "---释放锁---del fail"+"---"+results);
						continue;
					}else{
						logger.info("ThreadName:" + Thread.currentThread().getName() + "---释放锁---del success"+"---"+results);
					}
					retFlag = true;
				}else{
					logger.info("ThreadName:" + Thread.currentThread().getName() + "---释放锁---无identifier");
				}
				jedisConn.unwatch();
				break;
			}
		} catch (JedisException e) {
			logger.info("Exception", e);
		} finally {
			if (jedisConn != null) {
				jedisConn.close();
			}
		}
		return retFlag;
	}
	
	/**
	 * 释放锁（不带事务）
	 * 
	 * @param lockKey 锁的key
	 * @param identifier 释放锁的标识
	 * @return
	 */
	public boolean releaseLock(String lockKey, String identifier) {
		Jedis jedisConn = null;
		boolean retFlag = false;
		try {
			jedisConn = jedisPool.getResource();
			while (true) {
//		logger.info("ThreadName:" + Thread.currentThread().getName() + "---releaseLock--1");
				// 监视lock，准备开始事务
				String value = jedisConn.get(lockKey);
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				logger.info("ThreadName:" + Thread.currentThread().getName() + "---identifier:" + identifier
						+ "---value:" + value);
				if (identifier != null && identifier.equals(value)) {
					jedisConn.del(lockKey);
					logger.info("ThreadName:" + Thread.currentThread().getName() + "---释放锁---del success");
					retFlag = true;
				}else{
					logger.info("ThreadName:" + Thread.currentThread().getName() + "---释放锁---无identifier");
				}
				break;
			}
		} catch (JedisException e) {
			logger.info("Exception", e);
		} finally {
			if (jedisConn != null) {
				jedisConn.close();
			}
		}
		return retFlag;
	}
}
