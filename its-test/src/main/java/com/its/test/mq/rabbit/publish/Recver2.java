package com.its.test.mq.rabbit.publish;

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
public class Recver2 {

	/** 交接机名 */
	private final static String EXCHANGE_NAME = "testexchang";
	/** 队列名 */
	private final static String QUEUE_NAME = "testpubqueue2";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 绑定队列到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		// 告诉生产者，在我没有确认当前消息完成之前，不要给我发送新的消息，防止消息处理慢需要等待
		channel.basicQos(1);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Customer Received 2'" + message + "'");
				// 手动确认，参数2-false:确认收到消息，true：拒绝收到的消息
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		// 参数2-true：自动确认，false：手动确认,代表收到消息后需要手动告诉服务器确认收到消息
		channel.basicConsume(QUEUE_NAME, false, consumer);

	}

}