package com.its.job.elasticjob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.its.model.mybatis.dao.domain.JobManager;
import com.its.service.mybatis.JobManagerService;
import com.its.web.util.IpUtil;
import com.its.web.util.SpringContextUtils;
/**
 * 当当ElasticJob
 *
 */
public class SysUserElasticJob implements SimpleJob {
	
	private static final Logger log = Logger.getLogger("elasticjob");

	@Override
	public void execute(ShardingContext shardingContext) {
		long threadID = Thread.currentThread().getId();
		int shardingTotalCount = shardingContext.getShardingTotalCount();
		int shardingItem = shardingContext.getShardingItem();
		log.info(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s", threadID, shardingTotalCount, shardingItem));
		JobManagerService jobManagerService = (JobManagerService) SpringContextUtils.getBeanById("jobManagerService");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "0");
		map.put("serviceType", "sys_user");
		map.put("ip", IpUtil.getLocalIp());
		map.put("startNum", 0);
		map.put("rows", 200);
		map.put("shardingTotalCount", shardingTotalCount);
		map.put("shardingItem", shardingItem);
		/** 
		 * ElasticJob实际开发中，有了任务总片数和当前分片项，就可以对任务进行分片执行了 
		 * 比如 SELECT * FROM user WHERE status = 0 AND MOD(id, shardingTotalCount) = shardingItem 
		 * 传统Spring+Quartz分布式集群
		 * 方案一：抢占式：通过数据库配置，每次只会有一个服务器执行定时任务
		 * 方案二：均衡式：数据分片，通过数据分片方案实现定时任务分布式(业务数据insert时通过指定IP的方式实现业务数据分片，服务器通过IP查询需要处理的业务数据)
		 */  
		List<JobManager> list = jobManagerService.getJobManagerList(map);
//		List<JobManager> list = new ArrayList<JobManager>();
//		JobManager jm = new JobManager();
//		jm.setJobId(3L);
//		jm.setStatus("0");
//		list.add(jm);
		for (JobManager jobManager : list) {
			jobManager.setStatus("1");
			jobManager.setJobIp(IpUtil.getLocalIp() + "--" + shardingItem);
			jobManagerService.updateJobManager(jobManager);
			log.info(String.format("------JOB ID: %s, IP: %s, JOB IP: %s", jobManager.getJobId(), jobManager.getIp(), jobManager.getJobIp()));
		}
    }  

}
