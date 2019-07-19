package com.its.test.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.its.test.lock.redis.RedisDistributedLock;
import com.its.test.lock.redisson.RedissonDistributedLock;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 业务处理测试类-Redis
 */
public class Service {
	private static final Logger logger = LoggerFactory.getLogger(Service.class);

    /** Redis分布式锁-Jedis实现 */
    RedisDistributedLock redislock = new RedisDistributedLock();
    
    /** Redis分布式锁-Redisson框架实现 */
	RedissonDistributedLock redissonlock = new RedissonDistributedLock();


	/**
	 * 业务方法-Jedis实现 
	 */
	public void seckillJedis() {
	    // Redis锁key名称
        String lockName = "DistributedLock";
        // 返回锁的value值，供释放锁时候进行判断
        String indentifier = null;
        try {
            // 加锁
            indentifier = redislock.lockWithTimeout(lockName, 30 * 1000, 10 * 1000);
            // 执行业务处理
            getOrderNum();
        } catch (Exception e) {
            logger.error("****seckillJedis****" + e.getMessage(), e);
        } finally {
            // 解锁（不带事务）
            // lock.releaseLock(lockName, indentifier);
            // 解锁(带事务)
            redislock.releaseLockTransaction(lockName, indentifier);
        }
	}
	
	/**
         *  业务方法-Redisson框架实现
     */
    public void seckillRedisson() {
        // Redis锁key名称
        String lockName = "RedissonDistributedLock";
        try {
            // 加锁
            redissonlock.lockWithTimeout(lockName, 3 * 1000);
            // 执行业务处理
            getOrderNum();
        } catch (Exception e) {
            logger.error("****seckillRedisson****" + e.getMessage(), e);
        } finally {
            // 解锁
            redissonlock.unlock(lockName);
        }
    }

	/**
	 * 使用预分配号(模拟数据库操作)
	 */
    public void getOrderNum() {
        OrderNumdb.getOrderNumdb();
        String orderNum = OrderNumdb.list.get(0);
        try {
            // 用线程等待的方式模拟业务代码处理时长
            Thread.sleep(1* 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("ThreadName:" + Thread.currentThread().getName() + "---" + orderNum);
        OrderNumdb.list.remove(0);
    }
}
