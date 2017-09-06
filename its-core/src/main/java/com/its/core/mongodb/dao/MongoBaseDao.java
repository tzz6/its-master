package com.its.core.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface MongoBaseDao<T> {

	/** 创建集合 */
	public void createCollection(String collectionName);

	/** 删除 collection */
	public void dropCollection(String collectionName);

	/** 添加 */
	public void insert(T object, String collectionName);
	public void insert(T object);

	/** 根据条件查找一条数据 */
	public T findOne(Query query, String collectionName);
	public T findOne(Query query);

	/** 根据条件查找 */
	public List<T> findByQuery(Query query, String collectionName);
	public List<T> findByQuery(Query query);
	
	/** 查找所有 */
	public List<T> findAll();
	public List<T> findAll(String collectionName);

	/** 修改 */
	public void update(Query query, Update update, String collectionName);
	public void update(Query query, Update update);

	/** 根据条件删除 */
	public void remove(Query query, String collectionName);
	public void remove(Query query);
}
