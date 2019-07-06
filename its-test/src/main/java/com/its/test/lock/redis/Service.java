package com.its.test.lock.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 业务处理类
 */
public class Service {
	private static final Logger logger = LoggerFactory.getLogger(Service.class);

	RedisDistributedLock lock = new RedisDistributedLock();

	/**
	 * 业务方法
	 */
	public void seckill() {
		// 返回锁的value值，供释放锁时候进行判断
		String lockName = "DistributedLock";
		// 加锁
		String indentifier = lock.lockWithTimeout(lockName, 1000 * 30, 1000 * 10);
		// 执行业务处理
		getOrderNum();
		// System.out.println(--n);
		// 解锁（不带事务）
//		lock.releaseLock(lockName, indentifier);
		// 解锁(带事务)
		lock.releaseLockTransaction(lockName, indentifier);
	}

	/**
	 * 使用预分配号(模拟数据库操作)
	 */
	public void getOrderNum() {
	    OrderNumdb.getOrderNumdb();
		String orderNum = OrderNumdb.list.get(0);
		logger.info("ThreadName:" + Thread.currentThread().getName() + "---" + orderNum);
		OrderNumdb.list.remove(0);
	}
}
