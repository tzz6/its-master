package com.its.test.lock.redis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 测试TRedis分布式锁
 */
public class Test {
    public static void main(String[] args) {
		Service service = new Service();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss").format(new Date()));
		int end = 200;
		for (int i = 0; i < end; i++) {
//			try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			Thread thread = new Thread() {
			    @Override
				public void run() {
					service.seckill();
				}
			};
			thread.start();
		}
    }
}