package com.its.servers.job.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.its.model.dao.domain.ScheduleJob;


/**
 * 用于测试定时任务管理（增加、删除、编辑、停止、恢复）
 *
 */
public class DynamicQuartzJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("动态添加定时任务----任务成功运行----任务名称----[" + scheduleJob.getJobName() + "]");
    }

}

