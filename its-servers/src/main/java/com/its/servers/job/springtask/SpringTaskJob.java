package com.its.servers.job.springtask;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.its.servers.job.quartz.QuartzJob;


/**
 * 注解方式
 *
 */
@Service
public class SpringTaskJob {

	protected final transient Logger log = Logger.getLogger(QuartzJob.class);
	
	@Scheduled(cron="0/10 * * * * ?")
	public void push(){
		log.info("***SpringTask定时任务执行中***Annotation方式***");
	}
}
