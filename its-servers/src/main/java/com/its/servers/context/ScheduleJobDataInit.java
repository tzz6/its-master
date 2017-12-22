package com.its.servers.context;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.its.model.dao.domain.ScheduleJob;
import com.its.servers.job.manager.QuartzManager;
import com.its.service.ScheduleJobService;

/**
 * 初始定时任务配制表（用于管理定时任务：增加、删除、编辑、停止、恢复）
 *
 */
public class ScheduleJobDataInit implements ApplicationListener<ContextRefreshedEvent> {

	private ScheduleJobService scheduleJobService;

	private QuartzManager quartzManager;

	public ScheduleJobService getScheduleJobService() {
		return scheduleJobService;
	}

	public void setScheduleJobService(ScheduleJobService scheduleJobService) {
		this.scheduleJobService = scheduleJobService;
	}

	public QuartzManager getQuartzManager() {
		return quartzManager;
	}

	public void setQuartzManager(QuartzManager quartzManager) {
		this.quartzManager = quartzManager;
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
		// onApplicationEvent方法会被执行两次，为了避免执行两次
		if (event.getApplicationContext().getParent() == null) {
//			scheduleJobService.deleteAllScheduleJob();//删除数据重新初始化
			List<ScheduleJob> lists = scheduleJobService.findAll();
			if (lists == null || lists.size() == 0) {
				System.out.println("**********************init ScheduleJob tab***********************");
				ScheduleJob job1 = new ScheduleJob();
				job1.setJobName("exQuartzJob");
				job1.setTriggerName("exQuartzJobCronTrigger");
				job1.setJobStatus("1");
				job1.setCronExpression("0/10 * * * * ?");
				job1.setClassName("com.its.servers.job.quartz.EXQuartzJob");
				job1.setDescription("通过extends QuartzJobBean 的方式");
				
				ScheduleJob jobA = new ScheduleJob();
				jobA.setJobName("exQuartzAJob");
				jobA.setTriggerName("exQuartzAJobCronTrigger");
				jobA.setJobStatus("1");
				jobA.setCronExpression("0/10 * * * * ?");
				jobA.setClassName("com.its.servers.job.quartz.EXQuartzAJob");
				jobA.setDescription("A--通过extends QuartzJobBean 的方式");
				
				ScheduleJob jobB = new ScheduleJob();
				jobB.setJobName("exQuartzBJob");
				jobB.setTriggerName("exQuartzBJobCronTrigger");
				jobB.setJobStatus("1");
				jobB.setCronExpression("0/10 * * * * ?");
				jobB.setClassName("com.its.servers.job.quartz.EXQuartzBJob");
				jobB.setDescription("B--通过extends QuartzJobBean 的方式");

				ScheduleJob job2 = new ScheduleJob();
				job2.setJobName("quartzJobMethodInvokingJobDetail");
				job2.setTriggerName("quartzJobCronTrigger");
				job2.setJobStatus("3");
				job2.setCronExpression("0 0/10 * * * ?");
				job2.setClassName("com.its.servers.job.quartz.QuartzJob");
				job2.setDescription("简单Quartz定时任务");

				ScheduleJob job3 = new ScheduleJob();
				job3.setJobName("dynamicQuartzJob");
				job3.setTriggerName("dynamicQuartzCronTrigger");
				job3.setJobStatus("1");
				job3.setCronExpression("0 0/10 * * * ?");
				job3.setClassName("com.its.servers.job.quartz.DynamicQuartzJob");
				job3.setDescription("动态添加任务");

				scheduleJobService.saveEntity(job1);
				scheduleJobService.saveEntity(jobA);
				scheduleJobService.saveEntity(jobB);
//				scheduleJobService.saveEntity(job2);
				scheduleJobService.saveEntity(job3);
				// job1、job2为运行中的定时任务
				// 将job3动态添加为定时任务
//				quartzManager.createJob(job3);
			}
		}

	}

}
