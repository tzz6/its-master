package com.its.test.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

/**
 * 自定义分区 
 * DefaultPartitioner 默认分区
 */
public class KafkaCustomPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		@SuppressWarnings("unused")
		Integer count = cluster.partitionCountForTopic(topic);// 当前主题下的分区数
		String keyString = key.toString();
		if (keyString != null && !"".equals(keyString)) {
			if ("US".equals(keyString)) {
				return 0;
			} else if ("ZH".equals(keyString)) {
				return 1;
			} else {
				return 2;
			}
		}
		return 0;
	}

	@Override
	public void close() {

	}

}
