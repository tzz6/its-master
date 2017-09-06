package com.its.servers.job.quartz;

import org.apache.log4j.Logger;

public class QuartzJob{

	protected final transient Logger log = Logger.getLogger(QuartzJob.class);
	
//	@Autowired
//	private UserService userService;

	public void push() {
//		userService.findAllUser();
		log.info("***Quartz定时任务执行中***QuartzJob.push***");
	}
}
