package com.its.core.mongodb.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.its.core.mongodb.dao.MongoBaseDao;


public abstract class MongoBaseDaoImpl<T> implements MongoBaseDao<T> {

	/* 实体类描述符 */
	private Class<T> clazz;

	@Resource
	private MongoTemplate mongoTemplate;

	@SuppressWarnings("unchecked")
	public MongoBaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	/** 创建集合 */
	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}

	/** 删除 collection */
	public void dropCollection(String collectionName) {
		mongoTemplate.dropCollection(collectionName);
	}

	/** 添加 */
	public void insert(T object, String collectionName) {
		mongoTemplate.insert(object, collectionName);
	}

	public void insert(T object) {
		mongoTemplate.insert(object);
	}
	
	public void insertAll(List<T> object) {
		mongoTemplate.insertAll(object);
	}

	/** 根据条件查找一条数据 */
	public T findOne(Query query, String collectionName) {
		return mongoTemplate.findOne(query, clazz, collectionName);
	}

	public T findOne(Query query) {
		return mongoTemplate.findOne(query, clazz);
	}

	/** 根据条件查找 */
	public List<T> findByQuery(Query query, String collectionName) {
		List<T> result = mongoTemplate.find(query, clazz, collectionName);
		return result;
	}

	public List<T> findByQuery(Query query) {
		List<T> result = mongoTemplate.find(query, clazz);
		return result;
	}
	
	/** 查找所有 */
	public List<T> findAll(){
		List<T> result = mongoTemplate.findAll(clazz);
		return result;
	}
	public List<T> findAll(String collectionName){
		List<T> result = mongoTemplate.findAll(clazz, collectionName);
		return result;
	}
	
	public long count(Query query) {
		return mongoTemplate.count(query, clazz);
	}

	/** 修改 */
	public void update(Query query, Update update, String collectionName) {
		mongoTemplate.upsert(query, update, clazz, collectionName);
	}

	public void update(Query query, Update update) {
		mongoTemplate.upsert(query, update, clazz);
	}

	/** 根据条件删除 */
	public void remove(Query query, String collectionName) {
		if (query != null) {
			mongoTemplate.remove(query, clazz, collectionName);
		} else {
			mongoTemplate.remove(clazz, collectionName);
		}
	}

	public void remove(Query query) {
		if (query != null) {
			mongoTemplate.remove(query, clazz);
		} else {
			mongoTemplate.remove(clazz);
		}
	}
}
