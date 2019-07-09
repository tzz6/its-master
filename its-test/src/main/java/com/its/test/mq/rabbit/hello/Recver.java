package com.its.test.mq.rabbit.hello;

import java.io.IOException;

import com.its.test.mq.rabbit.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消费者
 * 
 * @author tzz
 */
public class Recver {

	/** 队列名 */
	private final static String QUEUE_NAME = "testhello";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//官网消费代码
//		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//		    String message = new String(delivery.getBody(), "UTF-8");
//		    System.out.println(" [x] Received '" + message + "'");
//		};
//		channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		
		// DefaultConsumer类实现了Consumer接口，通过传入一个频道，
		// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Customer Received '" + message + "'");
			}
		};
		// 自动回复队列应答 -- RabbitMQ中的消息确认机制
		channel.basicConsume(QUEUE_NAME, true, consumer);

	}

}
