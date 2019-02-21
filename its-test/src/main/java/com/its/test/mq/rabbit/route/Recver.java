package com.its.test.mq.rabbit.route;

import java.io.IOException;

import com.its.test.mq.rabbit.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消费者
 *
 */
public class Recver {

	/** 交接机名 */
	private final static String EXCHANGE_NAME = "testexchangRoute";
	/** 队列名 */
	private final static String QUEUE_NAME = "testroute1";

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		// 声明direct类型转发器  
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 绑定队列到交换机,参数3：路由标记，绑定到交换机时会指定一个标记，只有和它标记一样的消息才会被消费者收到
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "routeKey1");
		// 如果要接收到多个标记，则再绑定一次即可
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "routeKey2");

		// 告诉生产者，在我没有确认当前消息完成之前，不要给我发送新的消息，防止消息处理慢需要等待
		channel.basicQos(1);
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Customer Received 1'" + message + "'");
				// 手动确认，参数2-false:确认收到消息，true：拒绝收到的消息
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		// 参数2-true：自动确认，false：手动确认,代表收到消息后需要手动告诉服务器确认收到消息
		channel.basicConsume(QUEUE_NAME, false, consumer);

	}

}
