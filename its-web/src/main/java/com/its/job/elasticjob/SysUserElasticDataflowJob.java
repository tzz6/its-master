package com.its.job.elasticjob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.its.model.mybatis.dao.domain.JobManager;
import com.its.service.mybatis.JobManagerService;
import com.its.web.util.IpUtil;

/**
 * 当当ElasticJob
 * 
 * 流式处理
 * 
 * 可通过DataflowJobConfiguration配置是否流式处理。
 * 
 * 流式处理数据只有fetchData方法的返回值为null或集合长度为空时，作业才停止抓取，否则作业将一直运行下去；
 * 非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业。
 * 
 * 如果采用流式作业处理方式，建议processData处理数据后更新其状态，避免fetchData再次抓取到，从而使得作业永不停止。
 * 流式数据处理参照TbSchedule设计，适用于不间歇的数据处理。
 */
public class SysUserElasticDataflowJob implements DataflowJob<JobManager> {
	
	private static final Log log = LogFactory.getLog(SysUserElasticDataflowJob.class);

	private JobManagerService jobManagerService = null; 
	
	public JobManagerService getJobManagerService() {
		return jobManagerService;
	}

	public void setJobManagerService(JobManagerService jobManagerService) {
		this.jobManagerService = jobManagerService;
	}

	@Override
	public List<JobManager> fetchData(ShardingContext shardingContext) {
		long threadID = Thread.currentThread().getId();
		int shardingTotalCount = shardingContext.getShardingTotalCount();
		int shardingItem = shardingContext.getShardingItem();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "0");
		map.put("serviceType", "sys_user");
		map.put("ip", IpUtil.getLocalIp());
		map.put("startNum", 0);
		map.put("rows", 200);
		map.put("shardingTotalCount", shardingTotalCount);
		map.put("shardingItem", shardingItem);
		log.info(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s", threadID, shardingTotalCount, shardingItem));
		List<JobManager> list = jobManagerService.getJobManagerList(map);
//		List<JobManager> list = new ArrayList<JobManager>();
//		JobManager jobManager = new JobManager();
//		jobManager.setJobId(3L);
//		jobManager.setStatus("0");
//		list.add(jobManager);
		return list;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<JobManager> data) {
		long threadID = Thread.currentThread().getId();
		int shardingTotalCount = shardingContext.getShardingTotalCount();
		int shardingItem = shardingContext.getShardingItem();
		log.info(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s", threadID, shardingTotalCount, shardingItem));
		for (JobManager jobManager : data) {
			jobManager.setStatus("1");
			jobManager.setJobIp(IpUtil.getLocalIp() + "--" + shardingItem);
			jobManagerService.updateJobManager(jobManager);
			log.info(String.format("------JOB ID: %s, IP: %s, JOB IP: %s", jobManager.getJobId(),
					jobManager.getIp(), jobManager.getJobIp()));
		}

	}
}