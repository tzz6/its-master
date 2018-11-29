package com.its.test.lock.redis;

import org.apache.log4j.Logger;

public class Service {
	private static final Logger logger = Logger.getLogger(Service.class);

	RedisDistributedLock lock = new RedisDistributedLock();

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
		OrderNumDB.getOrderNumDB();
		String orderNum = OrderNumDB.list.get(0);
		logger.info("ThreadName:" + Thread.currentThread().getName() + "---" + orderNum);
		OrderNumDB.list.remove(0);
	}
}
