package com.its.test.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 测试Redis分布式锁
 */
public class LockTest {
    
    public static void main(String[] args) {
        // 测试Jedis实现-Redis分布式锁
//         testJedis();
        // 测试Redisson框架实现-Redis分布式锁
         testRedisson();
    }
    
    /**
          *   测试Jedis实现 -Redis分布式锁
     */
    public static void testJedis() {
        Service service = new Service();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss").format(new Date()));
        int end = 200;
        for (int i = 0; i < end; i++) {
            // try {
            // Thread.sleep(2);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            Thread thread = new Thread() {
                @Override
                public void run() {
                    service.seckillJedis();
                }
            };
            thread.start();
        }
    }
    
    /**
          *   测试Redisson框架实现-Redis分布式锁
     */
    public static void testRedisson() {
        Service service = new Service();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss").format(new Date()));
        int end = 200;
        for (int i = 0; i < end; i++) {
            // try {
            // Thread.sleep(2);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            Thread thread = new Thread() {
                @Override
                public void run() {
                    service.seckillRedisson();
                }
            };
            thread.start();
        }
    }
}