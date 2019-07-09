package com.its.test.mq.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author tzz
 * @工号: 
 * @date 2019/07/06
 * @Introduce: 生产者
 */
public class ProducerSend {

    private static Logger logger = LoggerFactory.getLogger(ProducerSend.class);
    private static String BOOTSTRAP_SERVERS = "10.203.15.50:9092,10.202.107.207:9092,10.202.107.115:9092";
	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
		// props.put("acks", "all");
		// props.put("retries", 0);
		// props.put("batch.size", 16384);
		// props.put("linger.ms", 1);
		// props.put("buffer.memory", 33554432);
		props.put("partitioner.class", "com.its.test.mq.kafka.KafkaCustomPartitioner");
		// 自定义分区
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);
		logger.info("ProducerSend Start");
		int messageNum = 500;
		for (int i = 0; i < messageNum; i++) {
			Thread.sleep(20);
			String key = "3";
			int index = i % 3;
			if (index == 0) {
				key = "US";
			} else if (index == 1) {
				key = "ZH";
			}
			producer.send(new ProducerRecord<String, String>("mytopic20190705", key, "hello" + i));
		}
		producer.close();
		logger.info("ProducerSend End");
	}
}
