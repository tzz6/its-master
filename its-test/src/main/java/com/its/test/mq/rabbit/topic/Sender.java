package com.its.test.mq.rabbit.topic;

import com.its.test.mq.rabbit.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者-topic模式
 * @author tzz
 */
public class Sender {

	// 简单模式（hello）：一个生产者，一个消费者
	// work模式：一个生产者，多个消费者，每个消费者获取到的消息唯一。
	// 订阅模式（Publish/Subscribe）：一个生产者发送的消息会被多个消费者获取。
	// 路由模式（Routing）：发送消息到交换机并且要指定路由key ，消费者将队列绑定到交换机时需要指定路由key
	// topic模式：将路由键和某模式进行匹配，“#”匹配一个词或多个词，“*”只匹配一个词,其他的内容与routing路由模式一致

	/** 交接机名 */
	private final static String EXCHANGE_NAME = "testTopic";
	/** 交接机类型-topic */
	private final static String EXCHANGE_TYPE = "topic";

	public static void main(String[] args) throws Exception {
		// 获取连接
		Connection connection = ConnectionUtil.getConnection();
		// 创建通道
		Channel channel = connection.createChannel();
		// 声明交换机，参数1：交换机名称，参数2:类型topic，也就是topic模式
		channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
		String routeKey = "key.1";
//		String routeKey = "key1.1";
//		String routeKey = "key1.1.2";
//		String routeKey = "abc1.1";
//		String routeKey = "abc1.1.2";
//		String routeKey = "key2.1";
//		String routeKey = "key2.1.2";
//		String routeKey = "abc2.1";
//		String routeKey = "abc2.1.2";
		String message = "Topic模式消息" + routeKey;
		channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());
		// 关闭连接
		channel.close();
		connection.close();
	}

}
