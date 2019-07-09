package com.its.test.thread;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author tzz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MultiThreadConfig.class })
public class MultiThreadTest {

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private MultiThreadProcessService multiThreadProcessService;

	@Test
	public void test() {
		// execute(Runable)方法执行过程
		// 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
		// 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
		// 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maxPoolSize，建新的线程来处理被添加的任务。
		// 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maxPoolSize，那么通过handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程
		// maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
		// 当线程池中的线程数量大于corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
		int n = 10000;
		for (int i = 0; i < n; i++) {
			taskExecutor.setThreadNamePrefix("Spring");
			multiThreadProcessService.setIndex(i + "");
			taskExecutor.execute(new MultiThreadDemo(multiThreadProcessService));
			System.out.println("Thread-" + i + ", now threadpool active threads totalnum is " + taskExecutor.getActiveCount());
		}

		try {
			System.in.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}