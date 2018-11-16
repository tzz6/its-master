package com.its.test.lock.redis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Service {
	private static final Logger logger = Logger.getLogger(Service.class);
	private static JedisPool pool = null;
	private static List<String> list = new ArrayList<>();// 号码池

	static {
		// 初始化号码池
		for (int i = 10000; i < 99999; i++) {
			list.add("SF" + i);
		}
		list.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
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
		pool = new JedisPool(config, host, port, timeout, password);// 构造连接池
	}

	RedisDistributedLock lock = new RedisDistributedLock(pool);

	/**
	 * 业务方法
	 */
	public void seckill() {
		// 返回锁的value值，供释放锁时候进行判断
		String lockName = "DistributedLock";
		String indentifier = lock.lockWithTimeout(lockName, 1000 * 30, 1000 * 10);// 加锁
		getOrderNum();// 执行业务处理
		// System.out.println(--n);
//		lock.releaseLock(lockName, indentifier);// 解锁（不带事务）
		lock.releaseLockTransaction(lockName, indentifier);// 解锁(带事务)
	}

	/**
	 * 使用预分配号(模拟数据库操作)
	 */
	public void getOrderNum() {
		String orderNum = list.get(0);
		logger.info("ThreadName:" + Thread.currentThread().getName() + "---" + orderNum);
		list.remove(0);
	}

}
