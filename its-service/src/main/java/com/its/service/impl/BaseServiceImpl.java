package com.its.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.its.core.hibernate.dao.BaseDao;
import com.its.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> baseDao;

	/** 注入BaseDao */
	@Resource
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public void saveEntity(T t) {
		baseDao.saveEntity(t);
	}

	public void updateEntity(T t) {
		baseDao.updateEntity(t);
	}

	public void saveOrUpdateEntity(T t) {
		baseDao.saveOrUpdateEntity(t);
	}

	public void deleteEntity(T t) {
		baseDao.deleteEntity(t);
	}

	public void batchHandleByHQL(String hql, Serializable... serializables) {
		baseDao.batchHandleByHQL(hql, serializables);
	}

	public T loadEntity(Long id) {
		return baseDao.loadEntity(id);
	}

	public T getEntity(Long id) {
		return baseDao.getEntity(id);
	}

	public List<T> findEntityByHQL(String hql, Serializable... serializables) {
		return baseDao.findEntityByHQL(hql, serializables);
	}
	
	@Override
	public List<T> findEntityByPageHQL(String hql, int offset, int pageSize) {
		return baseDao.findEntityByPageHQL(hql, offset, pageSize);
	}
	
	@Override
	public List<T> findEntityByPageHQL(String hql, Map<String, Object> map, int offset, int pageSize) {
		return baseDao.findEntityByPageHQL(hql, map, offset, pageSize);
	}

	public List<T> findByIds(Long[] ids) {
		return baseDao.findByIds(ids);
	}
	
	public List<T> findAll() {
		return baseDao.findAll();
	}
}
