package com.its.core.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
/**
  * Description:
  * Company: 顺丰科技有限公司国际业务科技部
  * @Author: 01115486
  * Date: 2019/12/14 17:53
  */
public interface MongoBaseDao<T> {

	/**
	 * 创建集合
	 * @param collectionName 连接名
	 */
	public void createCollection(String collectionName);

	/**
	 * 删除 collection
	 * @param collectionName 连接名
	 */
	public void dropCollection(String collectionName);

	/**
	 * 添加
	 * @param object 对象
	 * @param collectionName 连接名
	 */
	public void insert(T object, String collectionName);
	/**
	 * 添加
	 * @param object 对象
	 */
	public void insert(T object);
	/**
	 * 添加
	 * @param object 对象
	 */
	public void insertAll(List<T> object);

	/**
	 * 根据条件查找一条数据
	 * @param query query
	 * @param collectionName 连接名
	 * @return
	 */
	public T findOne(Query query, String collectionName);
	/**
	 * 根据条件查找一条数据
	 * @param query query
	 * @return
	 */
	public T findOne(Query query);

	/**
	 * 根据条件查找
	 * @param query query
	 * @param collectionName 连接名
	 * @return
	 */
	public List<T> findByQuery(Query query, String collectionName);
	/**
	 * 根据条件查找
	 * @param query query
	 * @return
	 */
	public List<T> findByQuery(Query query);
	
	/**
	 * 查找所有
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 查找所有
	 * @param collectionName 连接名
	 * @return
	 */
	public List<T> findAll(String collectionName);

	/**
	 * count
	 * @param query query
	 * @return
	 */
	public long count(Query query);

	/**
	 * 修改
	 * @param query 条件
	 * @param update 更新
	 * @param collectionName 连接名
	 */
	public void update(Query query, Update update, String collectionName);
	/**
	 * 修改
	 * @param query 条件
	 * @param update 更新
	 */
	public void update(Query query, Update update);

	/**
	 * 根据条件删除
	 * @param query 条件
	 * @param collectionName 连接名
	 */
	public void remove(Query query, String collectionName);
	/**
	 * 根据条件删除
	 * @param query 条件
	 */
	public void remove(Query query);
}
