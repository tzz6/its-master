package com.its.test.mq.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 创建连接工具类
 * @author tzz
 */
public class ConnectionUtil {

	public static Connection getConnection() throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		// 设置Server地址
		connectionFactory.setHost("10.203.15.50");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("test");
		connectionFactory.setPassword("test");
		connectionFactory.setVirtualHost("/test");
		// 创建一个新连接
		return connectionFactory.newConnection();
	}
}