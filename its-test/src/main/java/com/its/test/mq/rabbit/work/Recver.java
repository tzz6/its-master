package com.its.test.mq.rabbit.work;

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
 */
public class Recver {

	/** 队列名 */
	private final static String QUEUE_NAME = "testwork";

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
		
		// 告诉生产者，在我没有确认当前消息完成之前，不要给我发送新的消息，防止消息处理慢需要等待
		channel.basicQos(1);
		// DefaultConsumer类实现了Consumer接口，通过传入一个频道，
		// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Customer Received '" + message + "'");
				//手动确认，参数2-false:确认收到消息，true：拒绝收到的消息
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		// 参数2-true：自动确认，false：手动确认,代表收到消息后需要手动告诉服务器确认收到消息
		channel.basicConsume(QUEUE_NAME, false, consumer);

	}

}
