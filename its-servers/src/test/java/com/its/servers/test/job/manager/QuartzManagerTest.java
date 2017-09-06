package com.its.servers.test.job.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.its.model.dao.domain.ScheduleJob;
import com.its.servers.job.manager.QuartzManager;
import com.its.servers.job.quartz.DynamicQuartzJob;

@SuppressWarnings("unused")
public class QuartzManagerTest {

	private static String JOB_NAME = "WD_JOB_NAME";
	private static String JOB_GROUP_NAME = "WD_JOBGROUP_NAME";
	private static String TRIGGER_NAME = "WD_TRIGGER_NAME";
	private static String TRIGGER_GROUP_NAME = "WD_TRIGGERGROUP_NAME";

	public static void main(String[] args) {

		ApplicationContext ac = null;
		try {
			ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			QuartzManager quartzManagerService = (QuartzManager) ac.getBean("quartzManager");
			
			ScheduleJob job = new ScheduleJob();
			job.setJobName(JOB_NAME);
			// job.setJobGroup(JOB_GROUP_NAME);
			job.setTriggerName(TRIGGER_NAME);
			// job.setTriggerGroup(TRIGGER_GROUP_NAME);
			job.setJobStatus("1");
			job.setCronExpression("0/5 * * * * ?");
			System.out.println(DynamicQuartzJob.class.getName());
			job.setClassName(DynamicQuartzJob.class.getName());
			job.setDescription("动态任务调度");
			System.out.println("************测试添加************");
			quartzManagerService.createJob(job);
			job.setCronExpression("0/1 * * * * ?");
			quartzManagerService.createJob(job);
			System.out.println("************添加成功************");

			Thread.sleep(10000);
			System.out.println("************测试修改************");
			job.setCronExpression("0/5 * * * * ?");
			quartzManagerService.updateJob(job);
			System.out.println("************修改成功************");
			
			Thread.sleep(6000);
			System.out.println("************测试暂停************");
			quartzManagerService.stopJob(job);
			System.out.println("************暂停成功************");
			
			Thread.sleep(6000);
			System.out.println("************测试恢复************");
			quartzManagerService.recoverJob(job);
			System.out.println("************恢复成功************");
			
			Thread.sleep(10000);
			System.out.println("************测试删除************");
			quartzManagerService.removeJob(job);
			System.out.println("************删除成功************");
			
			Thread.sleep(3000);
			quartzManagerService.createJob(job);
			
			Thread.sleep(10000);
			System.out.println("************停止所有定时任务************");
			quartzManagerService.shutdownAllJob();
			System.out.println("************停止************");
			
			Thread.sleep(20000);
			System.out.println("************启动所有定时任务************");
			quartzManagerService.startAllJob();
			System.out.println("************启动************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
