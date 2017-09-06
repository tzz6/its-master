package com.its.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.its.core.hibernate.dao.BaseDao;
import com.its.model.dao.domain.ScheduleJob;
import com.its.service.ScheduleJobService;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJob> implements ScheduleJobService {

	@Override
	@Resource(name = "scheduleJobDao")
	public void setBaseDao(BaseDao<ScheduleJob> baseDao) {
		super.setBaseDao(baseDao);
	}

	/** 单值检索 */
	public Object uniqueResult(String hql, Serializable... serializables) {
		return uniqueResult(hql, serializables);
	}

	public void deleteAllScheduleJob() {
		for (ScheduleJob scheduleJob : findAll()) {
			super.deleteEntity(scheduleJob);
		}

	}
}
