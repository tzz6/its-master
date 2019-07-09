package com.its.test.mq.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tzz
 * @工号:
 * @date 2019/07/06
 * @Introduce: 自定义分区(DefaultPartitioner 默认分区)
 */
public class KafkaCustomPartitioner implements Partitioner {
    private static Logger logger = LoggerFactory.getLogger(KafkaCustomPartitioner.class);

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 当前主题下的分区数
        Integer count = cluster.partitionCountForTopic(topic);
        logger.info("count:{}", count);
		String keyString = key.toString();
		String us = "US";
		String zh = "ZH";
		if (keyString != null && !"".equals(keyString)) {
			if (us.equals(keyString)) {
				return 0;
			} else if (zh.equals(keyString)) {
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
