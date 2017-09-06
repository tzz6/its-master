package com.its.core.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BaseDao接口
 */
public interface BaseDao<T> {
	/* 写操作 */
	public void saveEntity(T t);

	public void updateEntity(T t);

	public void saveOrUpdateEntity(T t);

	public void deleteEntity(T t);

	public void batchHandleByHQL(String hql, Serializable... serializables);

	public T loadEntity(Long id);

	public T getEntity(Long id);

	public List<T> findEntityByHQL(String hql, Serializable... serializables);
	
	/* 单值检索 */
	public Object uniqueResult(String hql, Serializable... serializables);

	public List<T> findByIds(Long[] ids);
	
	public List<T> findAll();

	public List<T> findEntityByPageHQL(String hql, int offset, int pageSize);
	
	public List<T> findEntityByPageHQL(String hql, Map<String, Object> map, int offset, int pageSize);
	
	public List<T> criteriaQuery(T t, int offset, int pageSize);
}