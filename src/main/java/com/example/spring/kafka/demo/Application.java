package com.example.spring.kafka.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * consumer offset -> latest - read from latest last committed offset ex-1,2,3crash,4,5 -> reads from 4
 * 					 earliest -> reads from beginning
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("topic1")
				.partitions(10)
				.replicas(1)
				.build();
	}

	@KafkaListener(id = "myId", topics = "topic1")
//	@KafkaListener(
//			topicPartitions = @TopicPartition(topic = "topicName",
//					partitionOffsets = {
//							@PartitionOffset(partition = "0", initialOffset = "0"),
//							@PartitionOffset(partition = "3", initialOffset = "0")
//					})) consumer config -> key serializer, value serializer, maxpoll records, consumer id, bootstrap server(localhost:9092),max.poll.interval.ms,heartbeat.interval.ms,enable.auto.commit
	/**
	 *
	 *
	 * # Connection settings
	 * bootstrap.servers=192.168.1.50:9092,192.168.1.51:9092
	 * security.protocol=SSL
	 * ssl.truststore.location=/path/to/truststore.jks
	 * ssl.truststore.password=truststore-password
	 * ssl.keystore.location=/path/to/keystore.jks
	 * ssl.keystore.password=keystore-password
	 * ssl.key.password=key-password
	 *
	 * # Consumer settings
	 * enable.auto.commit=true
	 * auto.commit.interval.ms=5000
	 * auto.offset.reset=latest
	 * heartbeat.interval.ms=3000
	 * max.poll.records=100
	 * max.poll.interval.ms=300000
	 * session.timeout.ms=30000
	 *
	 * # Consumer group settings
	 * group.id=my-consumer-group
	 * group.min.session.timeout.ms=6000
	 * group.max.session.timeout.ms=30000
	 *
	 */
	public void listen(String in) {
		System.out.println(in);
	}
	@Bean
	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
		return args -> {
			template.send("topic1", "test");
		};
	}

}
