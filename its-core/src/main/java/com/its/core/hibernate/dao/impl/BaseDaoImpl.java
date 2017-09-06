package com.its.core.hibernate.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.its.core.hibernate.dao.BaseDao;

/**
 * 抽象的dao实现,专门用于继承
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	/* 实体类描述符 */
	private Class<T> clazz;

	private SessionFactory sessionFactory;

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	/**
	 * 注入SessionFactory
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("rawtypes")
	public List<T> findEntityByHQL(String hql, Serializable... serializables) {
		Session s = sessionFactory.getCurrentSession();
		Query q = s.createQuery(hql);
		for (int i = 0; i < serializables.length; i++) {
			q.setParameter(i, serializables[i]);
		}
		return q.list();
	}

	/**
	 * 单值检索,确保查询结果有且仅有一条记录
	 */
	@SuppressWarnings("rawtypes")
	public Object uniqueResult(String hql, Serializable... serializables) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		for (int i = 0; i < serializables.length; i++) {
			q.setParameter(i, serializables[i]);
		}
		return q.uniqueResult();
	}

	public T getEntity(Long id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	public T loadEntity(Long id) {
		return (T) sessionFactory.getCurrentSession().load(clazz, id);
	}

	public void saveEntity(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	public void saveOrUpdateEntity(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	public void updateEntity(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	public void deleteEntity(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}

	@SuppressWarnings("rawtypes")
	public void batchHandleByHQL(String hql, Serializable... serializables) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		for (int i = 0; i < serializables.length; i++) {
			q.setParameter(i, serializables[i]);
		}
		q.executeUpdate();
	}

	public List<T> findByIds(Long[] ids) {
		String hql = "FROM " + clazz.getName() + " WHERE id in (:ids)";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameterList("ids", ids).list();
	}

	public List<T> findAll() {
		String hql = "FROM " + clazz.getName();
		return sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true).list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<T> findEntityByPageHQL(String hql, int offset, int pageSize) {
		Session s = sessionFactory.getCurrentSession();
		Query q = s.createQuery(hql);
		q.setFirstResult(offset).setMaxResults(pageSize);
		return q.list();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<T> findEntityByPageHQL(String hql, Map<String, Object> map, int offset, int pageSize) {
		Session s = sessionFactory.getCurrentSession();
		Query q = s.createQuery(hql);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			q.setParameter(entry.getKey(), entry.getValue());
		}
		q.setFirstResult(offset).setMaxResults(pageSize);
		return q.list();
	}

	@Override
	public List<T> criteriaQuery(T t, int offset, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(t.getClass());
		return criteria.setFirstResult(offset).setMaxResults(pageSize).list();
	}
}
