package com.its.test.lock.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: Redis分布式锁-Redisson框架实现
 */
public class RedissonDistributedLock {
	private static final Logger logger = LoggerFactory.getLogger(RedissonDistributedLock.class);
	private static RedissonClient redissonClient = null;
    static {
        //redisson配置
        Config config = new Config();
        // 设置看门狗，默认30S
        // config.setLockWatchdogTimeout(5*100);
        config.useSentinelServers().addSentinelAddress("redis://vm-01-ip:26379", "redis://vm-02-ip:26379", "redis://vm-03-ip:26379")
            .setMasterName("its_sentinel").setPassword("123456");
        //单机
//        config.useSingleServer().setAddress("redis://vm-01-ip:6379").setPassword("123456");
        // 初始化Redisson
        redissonClient = Redisson.create(config);
    }

	/**
	 * 加锁
	 * 
	 * @param lockKey 锁的key
	 * @param timeout redis锁的超时时间，超时自动解锁
	 * @return 锁标识
	 */
    public boolean lockWithTimeout(String lockKey, long timeout) {
        boolean flag = false;
        try {
            // 获取锁对象
            RLock rLock = redissonClient.getLock(lockKey);
            // 加锁，并且设置锁过期时间，单位毫秒，防止死锁的产生
            rLock.lock(timeout, TimeUnit.MILLISECONDS);
            logger.info("ThreadName:" + Thread.currentThread().getName() + "---获得锁");
            // 加锁成功
            flag = true;
        } catch (Exception e) {
            logger.error("lock" + e.getMessage(), e);
        }
        return flag;
    }
	
    /**
          *  释放锁
     * @param lockKey 锁的key
     * @return
     */
    public boolean unlock(String lockKey) {
        boolean flag = false;
        try {
            // 获取锁对象
            RLock rLock = redissonClient.getLock(lockKey);
            // 检查这个锁是否被任何线程锁定,是则调用unlock释放锁
            if (rLock.isLocked()) {
                // 释放锁
                rLock.unlock();
                logger.info("ThreadName:" + Thread.currentThread().getName() + "---主动释放锁");
            } else {
                logger.info("ThreadName:" + Thread.currentThread().getName() + "---锁已被释放");
            }
            // 释放锁成功
            flag = true;
        } catch (Exception e) {
//            logger.error("****unlock****" + e.getMessage(), e);
            logger.error("****unlock**Exception**");
        }
        return flag;
    }
}
