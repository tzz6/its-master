package com.its.test.mq.rabbit.work;

import com.its.test.mq.rabbit.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者-work模式
 *
 */
public class Sender {
	
	// 简单模式（hello）：一个生产者，一个消费者
	// work模式：一个生产者，多个消费者，每个消费者获取到的消息唯一。
	// 订阅模式（Publish/Subscribe）：一个生产者发送的消息会被多个消费者获取。
	// 路由模式（Routing）：发送消息到交换机并且要指定路由key ，消费者将队列绑定到交换机时需要指定路由key
	// topic模式：将路由键和某模式进行匹配，“#”匹配一个词或多个词，“*”只匹配一个词,其他的内容与routing路由模式一致

	/** 队列名 */
	private final static String QUEUE_NAME = "testwork";

	public static void main(String[] args) throws Exception {
		// 获取连接
		Connection connection = ConnectionUtil.getConnection();
		// 创建通道
		Channel channel = connection.createChannel();
		// 声明队列，队列不存在是才创建，如果已存在则什么都不做
		// 参数1：队列名称
		// 参数2：是否持久化，false指内存中，如果Rabbitmq重启则会丢失，true则会保存到erlang自带的数据库
		// 参数3：是否排外，有两个作用，作用1当我们连接关闭后是否会自动删除队列，作用2，是否私有当天前队列，如果私有，其他通道不可访问，设置为true,一般是一个队列只有一个消费者的时候
		// 参数4：是否自动删除
		// 参数5：一些其他参数
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 发送内容
		for (int i = 0; i < 100; i++) {
			String message = "生产者--发送消息" + i;
			Thread.sleep(10);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		}

		// 关闭连接
		channel.close();
		connection.close();
	}

}
