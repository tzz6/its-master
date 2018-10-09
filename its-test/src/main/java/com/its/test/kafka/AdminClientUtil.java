package com.its.test.kafka;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.config.ConfigResource;

/**
 * 很多用户都有直接使用程序API操作Kafka集群的需求。在0.11版本之前，kafka的服务器端代码(即添加kafka_2.**依赖)提供了AdminClient和AdminUtils可以提供部分的集群管理操作，
 * 但社区官网主页并没有给出这两个类的使用文档。用户只能自行查看源代码和测试用例才能了解具体的使用方法。倘若使用客户端API的话(即添加kafka_clients依赖)，
 * 用户必须构造特定的请求并自觉编写代码向指定broker创建Socket连接并发送请求，同样是十分繁琐。
 * 故Kafka0.11版本引入了客户端的AdminClient工具。注意，虽然和原先服务器端的AdminClient类同名，
 * 但这个工具是属于客户端的，因此需要在程序中添加kafka_clients依赖，比如Gradle的话则增加 compile group:
 * ‘org.apache.kafka‘, name: ‘kafka-clients‘, version: ‘0.11.0.0‘
 * 
 * 该工具提供的所有功能包括：
 * 
 * 创建topic 
 * 查询所有topic 
 * 查询单个topic详情 
 * 删除topic 
 * 修改config（包括BROKER和TOPIC资源的config）
 * 查询资源config详情 
 * 创建ACL 
 * 查询ACL详情 
 * 删除ACL 
 * 查询整个集群详情 
 * 用户使用该类的方式与Java clients的使用方式一致，不用连接Zookeeper，而是直接给定集群中的broker列表。
 * 另外该类是线程安全的，因此可以放心地在多个线程中使用该类的实例。
 * AdminClient的实现机制与《JavaAPI方式调用Kafka各种协议》一文中的方式完全一样：
 * 都是在后台自行构建Kafka的各种请求然后发送，只不过所有的细节AdminClient都帮用户实现了，用户不再自己编写底层的各种功能代码了。
 * 
 * 下面给出一个该类的测试实例，列出了除ACL操作之外的所有操作样例代码，如下所示
 */

public class AdminClientUtil {

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "10.202.107.208:9092,10.202.107.207:9092,10.202.107.115:9092");
		String topicName = "testCreateTopic";
//			List<String> topicNames =  Arrays.asList(topicName, "__consumer_offsets");
		List<String> topicNames =  Arrays.asList(topicName);

		try (AdminClient client = AdminClient.create(props)) {
			//集群详情
//			describeCluster(client);
			//所有topics
//			Set<String> topicNames= listAllTopics(client);
//			System.out.println("Current topics in this cluster: " + topicNames);
			// 配置详情
			describeConfig(client, topicName);
			// 创建Topics
//			createTopics(client, topicName, 3, (short) 3);
			// 修改Topics
//			alterConfigs(client, topicName);
			// 删除Topics
//			deleteTopics(client, topicNames);
			// Topics详情
//			topicsMap.forEach((k, v) -> System.out.println(k + " ===> " + for(v.partitions());
			Map<String, TopicDescription> topicsMap = describeTopics(client, topicNames);
			for (Map.Entry<String, TopicDescription> entry : topicsMap.entrySet()) {
				System.out.println("topicName:" + entry.getKey());
				List<TopicPartitionInfo> topicPartitionInfos = entry.getValue().partitions();
				for (TopicPartitionInfo topicPartitionInfo : topicPartitionInfos) {
					System.out.println(topicPartitionInfo);
				}
			}
		}
	}
	
	 /**
     * describe the cluster
     * @param client
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void describeCluster(AdminClient client) throws ExecutionException, InterruptedException {
        DescribeClusterResult ret = client.describeCluster();
        System.out.println(String.format("Cluster id: %s, controller: %s", ret.clusterId().get(), ret.controller().get()));
        System.out.println("Current cluster nodes info: ");
        for (Node node : ret.nodes().get()) {
            System.out.println(node);
        }
    }
	

   /**
    * print all topics in the cluster
    * @param client
    * @return
    * @throws ExecutionException
    * @throws InterruptedException
    */
    public static Set<String> listAllTopics(AdminClient client) throws ExecutionException, InterruptedException {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true); // includes internal topics such as __consumer_offsets
        ListTopicsResult topics = client.listTopics(options);
        Set<String> topicNames = topics.names().get();
        return topicNames;
    }
   

    /**
     * describe topic‘s config
     * @param client
     * @param topicName 主题名称
     */
    public static void describeConfig(AdminClient client, String topicName) throws ExecutionException, InterruptedException {
        DescribeConfigsResult ret = client.describeConfigs(Collections.singleton(new ConfigResource(ConfigResource.Type.TOPIC, topicName)));
        Map<ConfigResource, Config> configs = ret.all().get();
        for (Map.Entry<ConfigResource, Config> entry : configs.entrySet()) {
            ConfigResource key = entry.getKey();
            Config value = entry.getValue();
            System.out.println(String.format("Resource type: %s, resource name: %s", key.type(), key.name()));
            Collection<ConfigEntry> configEntries = value.entries();
            for (ConfigEntry each : configEntries) {
                System.out.println(each.name() + " = " + each.value());
            }
        }

    }
    
	/**
	 * create multiple sample topics
	 * 
	 * @param client
	 * @param topicName 主题名称
	 * @param numPartitions 分区数
	 * @param replicationFactor 复制数
	 */
	public static void createTopics(AdminClient client, String topicName, int numPartitions, short replicationFactor) throws ExecutionException, InterruptedException {
		NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
//		newTopic.configs(configs);
		CreateTopicsResult ret = client.createTopics(Arrays.asList(newTopic));
		ret.all().get();
	}


    /**
     * alter config for topics
     * @param client
     * @param topicName 主题名称
     */
	public static void alterConfigs(AdminClient client, String topicName) throws ExecutionException, InterruptedException {
        Config topicConfig = new Config(Arrays.asList(new ConfigEntry("cleanup.policy", "compact")));
        client.alterConfigs(Collections.singletonMap(
                new ConfigResource(ConfigResource.Type.TOPIC, topicName), topicConfig)).all().get();
    }

    /**
     * delete the given topics
     * @param client
     * @param topicNames 主题名称
     */
	public static void deleteTopics(AdminClient client, Collection<String> topicNames)throws ExecutionException, InterruptedException {
		KafkaFuture<Void> futures = client.deleteTopics(topicNames).all();
		futures.get();
	}

  /**
   * describe the given topics
   * @param client
   * @param topicNames 主题名称
   * @return
   * @throws ExecutionException
   * @throws InterruptedException
   */
    public static Map<String, TopicDescription> describeTopics(AdminClient client, Collection<String> topicNames) throws ExecutionException, InterruptedException {
        DescribeTopicsResult ret = client.describeTopics(topicNames);
        Map<String, TopicDescription> topics = ret.all().get();
        return topics;
    }

}
