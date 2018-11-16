package com.its.test.lock.redis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试TRedis分布式锁
 *
 */
public class Test {
    public static void main(String[] args) {
		Service service = new Service();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss").format(new Date()));
		for (int i = 0; i < 900; i++) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread thread = new Thread() {
				public void run() {
					service.seckill();
				}
			};
			thread.start();
		}
//    	int i = 0;
//    	while(true){
//    		i++;
//    		System.out.println(i);
//    		if(i>0&&i<100){
//    			continue;
//    		}
//    		System.out.println(i);
//    		break;
//    	}
    }
}