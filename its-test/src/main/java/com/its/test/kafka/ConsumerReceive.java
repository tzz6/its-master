package com.its.test.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.its.test.lock.redis.Service;

/**
 * 消费者
 *
 */
public class ConsumerReceive {

	private Logger logger = Logger.getLogger(ConsumerReceive.class);
	private static KafkaConsumer<String, String> consumer;
	private static Collection<String> topics = Arrays.asList("mytopic", "mytopic2");

	
	@Test
	public void testConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");// 是否自动提交偏移量true:自动提交、false:非自动
		props.put("auto.commit.interval.ms", "1000");// 设置多久一次更新被消费消息的偏移量
		props.put("session.timeout.ms", "30000");// 设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
		props.put("auto.offset.reset", "earliest");

		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(topics);// 设置从那些主题下消费数据 
		// 从对应的主题分区下消费数据--消费mytopic主题下的0分区
		// TopicPartition topicPartition = new TopicPartition("mytopic", 0);
		// consumer.assign(Arrays.asList(topicPartition));

		while (true) {
			// 阻塞时间：从kafka中去取出100ms的数据，一次性可能取出0~N条数据
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				logger.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
						record.topic(), record.partition(), record.offset(), record.key(), record.value()));
			}
		}
	}
	
	//提交与偏移量
	// 已消费的数据对于kafka来说，会将该消费组中的offset值进行修改,offset可设置为自动提交或手动提交（默认情况下自动提交，需要手工设置）
	// 自动提交：提交过程是通过kafka自动将offset移动至下一个message
	// 手动提交：为了保证业务处理成功，在业务处理成功后，再做手工修改offset提交
	@Test
	public void testConsumerCommit() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		// props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(topics);// 设置从那些主题下消费数据
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				logger.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
						record.topic(), record.partition(), record.offset(), record.key(), record.value()));
			}
			// consumer.commitAsync();//异步提交，无需等待broker的响应
			consumer.commitSync();// 同步提交,同步提交有一个不足之处，在broker提交请求作出回应之前，应用程序会一直阻塞
		}
	}
	
	// 同步与异步组合提交
	@Test
	public void testConsumerCommit2() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		// props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(topics);// 设置从那些主题下消费数据
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
					logger.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
							record.topic(), record.partition(), record.offset(), record.key(), record.value()));
				}
				consumer.commitAsync();
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			consumer.commitSync();
			consumer.close();
		}
		// 一般情况下，针对偶尔出现的提交失败，不进行重试不会有太大的问题，因为如果提交失败是因为临时问题导致的，
		// 那么后续的提交总会有成功的，但如果这是发生在关闭消费者或再远行前的最后一次提交，就要确保能够提交成功。
		// 因此，在消费者关闭前一般会组合使用commitAsync()和commitSync()。
	}
	
	//提交特定偏移量
	@Test
	public void testConsumerCommit3() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		// props.put("auto.commit.interval.ms", "1000");
		 props.put("session.timeout.ms", "30000");
		 props.put("auto.offset.reset","earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(topics);// 设置从那些主题下消费数据
		
		Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();// 记录消费的offsets
		int count = 0;
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				logger.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
						record.topic(), record.partition(), record.offset(), record.key(), record.value()));
				currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
						new OffsetAndMetadata(record.offset() + 1, "no metadata"));
			}
			if (count % 1000 == 0) {//处理1000次提交一次偏移量
				consumer.commitAsync(currentOffsets, null);// 将记录的消费的offset偏移
			}
			count++;
		}
	}
	
	
	
	
	// 再均衡监听器
	@Test
	public void testConsumerCommit4() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		// props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();// 记录消费的offsets
		try {
			consumer = new KafkaConsumer<>(props);
			consumer.subscribe(topics, new ConsumerRebalanceListener() {
				@Override
				public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
					System.out.println("Lost partitions in rebalance. Committing current offsets:" + currentOffsets);
					consumer.commitSync(currentOffsets);
				}

				@Override
				public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

				}
			});

			int count = 0;
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
					logger.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
							record.topic(), record.partition(), record.offset(), record.key(), record.value()));
					currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
							new OffsetAndMetadata(record.offset() + 1, "no metadata"));
				}
				if (count % 1000 == 0) {// 处理1000次提交一次偏移量
					consumer.commitAsync(currentOffsets, null);// 将记录的消费的offset偏移
				}
				count++;
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			consumer.commitSync();
			consumer.close();
		}
	}
	
	/**
	 * 加入分布式锁处理
	 */
	@Test
	public void testConsumerDistributedLock() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "false");
		// props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(topics);// 设置从那些主题下消费数据
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				logger.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
						record.topic(), record.partition(), record.offset(), record.key(), record.value()));
				//测试分布式锁
				Service service = new Service();
				service.seckill();
				//测试分布式锁
			}
			// consumer.commitAsync();//异步提交，无需等待broker的响应
			consumer.commitSync();// 同步提交,同步提交有一个不足之处，在broker提交请求作出回应之前，应用程序会一直阻塞
		}
	}

	// Properties配置说明
	// enable.auto.commit 是否自动提交偏移量true:自动提交、false:非自动
	// auto.commit.interval.ms 设置多久一次更新被消费消息的偏移量
	// session.timeout.ms 设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
	// auto.offset.reset 默认latest
	// earliest
	// 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
	// latest
	// 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
	// none
	// topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
}
