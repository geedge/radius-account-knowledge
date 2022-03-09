package com.zdjizhi.utils.kafka;

import com.zdjizhi.common.RadiusKnowledgeConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.kafka
 * @Description:
 * @date 2021/6/813:54
 */
public class KafkaConsumer {
    private static Properties createConsumerConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", RadiusKnowledgeConfig.SOURCE_KAFKA_SERVERS);
        properties.put("group.id", RadiusKnowledgeConfig.GROUP_ID);
        properties.put("session.timeout.ms", RadiusKnowledgeConfig.SESSION_TIMEOUT_MS);
        properties.put("max.poll.records", RadiusKnowledgeConfig.MAX_POLL_RECORDS);
        properties.put("max.partition.fetch.bytes", RadiusKnowledgeConfig.MAX_PARTITION_FETCH_BYTES);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        CertUtils.chooseCert(RadiusKnowledgeConfig.SOURCE_KAFKA_SERVERS, properties);

        return properties;
    }

    public static FlinkKafkaConsumer<String> getKafkaConsumer() {
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(
                RadiusKnowledgeConfig.SOURCE_KAFKA_TOPIC,
                new SimpleStringSchema(),
                createConsumerConfig());

        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);
        kafkaConsumer.setStartFromGroupOffsets();

        return kafkaConsumer;
    }
}
