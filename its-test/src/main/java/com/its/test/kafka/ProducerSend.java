package com.its.test.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
/**
 * 生产者
 *
 */
public class ProducerSend {

	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		// props.put("acks", "all");
		// props.put("retries", 0);
		// props.put("batch.size", 16384);
		// props.put("linger.ms", 1);
		// props.put("buffer.memory", 33554432);
		props.put("partitioner.class", "com.its.test.kafka.KafkaCustomPartitioner");// 自定义分区
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 5000; i++) {
			Thread.sleep(100);
			String key = "3";
			int index = i % 3;
			if (index == 0) {
				key = "US";
			} else if (index == 1) {
				key = "ZH";
			}
			producer.send(new ProducerRecord<String, String>("mytopic", key, "hello" + i));
		}
		producer.close();
	}
}
