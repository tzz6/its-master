package com.its.test.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MultiThreadProcessService {

	public static final Logger logger = Logger.getLogger(MultiThreadProcessService.class);

	private String index;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * 默认处理流程耗时1000ms
	 */
	public void processSomething() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
		System.out.println("ProcessSomething" + Thread.currentThread() + "...start" + getIndex() + "...date"
				+ format.format(new Date()));
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("ProcessSomething" + Thread.currentThread() + "...end" + getIndex() + "...date"
				+ format.format(new Date()));
	}
}