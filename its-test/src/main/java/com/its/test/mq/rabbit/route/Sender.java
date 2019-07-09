package com.its.test.mq.rabbit.route;

import com.its.test.mq.rabbit.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者-路由模式
 * @author tzz
 */
public class Sender {

	// 简单模式（hello）：一个生产者，一个消费者
	// work模式：一个生产者，多个消费者，每个消费者获取到的消息唯一。
	// 订阅模式（Publish/Subscribe）：一个生产者发送的消息会被多个消费者获取。
	// 路由模式（Routing）：发送消息到交换机并且要指定路由key ，消费者将队列绑定到交换机时需要指定路由key
	// topic模式：将路由键和某模式进行匹配，“#”匹配一个词或多个词，“*”只匹配一个词,其他的内容与routing路由模式一致

	/** 交接机名 */
	private final static String EXCHANGE_NAME = "testexchangRoute";
	/** 交接机类型-direct */
	private final static String EXCHANGE_TYPE = "direct";
	/** 路由key */
	private final static String ROUTE_KEY = "routeKey";

	public static void main(String[] args) throws Exception {
		// 获取连接
		Connection connection = ConnectionUtil.getConnection();
		// 创建通道
		Channel channel = connection.createChannel();
		// 声明交换机，参数1：交换机名称，参数2:类型direct，也就是路由模式
		channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
		int end = 100;
		for (int i = 0; i < end; i++) {
			// 生成routekey
			int keyIndex = i % 3 + 1;
			String message = "路由模式消息" + keyIndex + "--" + i;
			channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY + keyIndex, null, message.getBytes());
			Thread.sleep(20);
		}
		// 关闭连接
		channel.close();
		connection.close();
	}

}
