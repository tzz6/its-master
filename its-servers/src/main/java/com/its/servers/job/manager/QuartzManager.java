package com.its.servers.job.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import com.its.model.dao.domain.ScheduleJob;


/**
 * 定时任务管理器（增加、删除、编辑、停止、恢复）
 *
 */
public class QuartzManager {

	private Scheduler schedulerFactory;

	public Scheduler getSchedulerFactory() {
		return schedulerFactory;
	}

	public void setSchedulerFactory(Scheduler schedulerFactory) {
		this.schedulerFactory = schedulerFactory;
	}

	/** 添加 */
	@SuppressWarnings("unchecked")
	public void createJob(ScheduleJob job) {
		JobDetail jobDetail = null;
		CronTrigger trigger = null;
		CronScheduleBuilder scheduleBuilder = null;
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
			// 获取任务触发器
			trigger = (CronTrigger) schedulerFactory.getTrigger(triggerKey);
			if (null == trigger) {// 不存在--创建
				jobDetail = JobBuilder.newJob(job.getClazz()).withIdentity(job.getJobName(), job.getJobGroup()).build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
				scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());// 表达式调度构建器
				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getTriggerName(), job.getTriggerGroup())
						.withSchedule(scheduleBuilder).build();
				schedulerFactory.scheduleJob(jobDetail, trigger);
			} else {// 存在--更新相应的定时设置
				scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());// 表达式调度构建器
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				schedulerFactory.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 修改--任务将立即按新的时间表达式执行 */
	public void updateJob(ScheduleJob job) {
		CronTrigger trigger = null;
		CronScheduleBuilder scheduleBuilder = null;
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
			trigger = (CronTrigger) schedulerFactory.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			String time = job.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				scheduleBuilder = CronScheduleBuilder.cronSchedule(time);// 表达式调度构建器
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				schedulerFactory.rescheduleJob(triggerKey, trigger);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除任务后，所对应的trigger也将被删除
	 */
	public void removeJob(ScheduleJob job) {
		try {
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			schedulerFactory.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暂停任务
	 */
	public void stopJob(ScheduleJob job) {
		try {
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			schedulerFactory.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复任务,和暂停任务相对
	 */
	public void recoverJob(ScheduleJob job) {
		try {
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			schedulerFactory.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行任务--立即运行（只会运行一次）
	 */
	public void runJob(ScheduleJob job) {
		try {
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			schedulerFactory.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/** 启动所有定时任务 */
	public void startAllJob() {
		try {
			if (schedulerFactory.isShutdown()) {
				schedulerFactory.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/** 关闭所有定时任务 */
	public void shutdownAllJob() {
		try {
			if (!schedulerFactory.isShutdown()) {
				schedulerFactory.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计划中的任务
	 */
	public List<ScheduleJob> planJob(ScheduleJob scheduleJob) throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.groupContains(scheduleJob.getTriggerGroup());
		Set<JobKey> jobKeys = schedulerFactory.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = schedulerFactory.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = schedulerFactory.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 运行中的任务
	 */
	public List<ScheduleJob> runningJob() throws SchedulerException {
		List<JobExecutionContext> executingJobs = schedulerFactory.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = schedulerFactory.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}
}