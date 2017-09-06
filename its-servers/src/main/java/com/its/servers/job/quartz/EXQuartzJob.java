package com.its.servers.job.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
/**
 * 通过extends QuartzJobBean 的方式
 *
 */
public class EXQuartzJob extends QuartzJobBean {

	protected final transient Logger log = Logger.getLogger(EXQuartzJob.class);

	@SuppressWarnings("unused")
	private int timeout;
	@SuppressWarnings("unused")
	private static int i = 0;

	// 调度工厂实例化后，经过timeout时间开始执行调度
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.info("***Quartz定时任务执行中***通过extends QuartzJobBean 的方式***");
	}

}
