package com.zdjizhi.utils.kafka;

import com.zdjizhi.common.RadiusKnowledgeConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Optional;
import java.util.Properties;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.kafka
 * @Description:
 * @date 2021/6/814:04
 */
public class Producer {

    private static Properties createProducerConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", RadiusKnowledgeConfig.OUTPUT_KAFKA_SERVERS);
        properties.put("acks", RadiusKnowledgeConfig.PRODUCER_ACK);
        properties.put("retries", RadiusKnowledgeConfig.RETRIES);
        properties.put("linger.ms", RadiusKnowledgeConfig.LINGER_MS);
        properties.put("request.timeout.ms", RadiusKnowledgeConfig.REQUEST_TIMEOUT_MS);
        properties.put("batch.size", RadiusKnowledgeConfig.BATCH_SIZE);
        properties.put("buffer.memory", RadiusKnowledgeConfig.BUFFER_MEMORY);
        properties.put("max.request.size", RadiusKnowledgeConfig.MAX_REQUEST_SIZE);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, RadiusKnowledgeConfig.PRODUCER_KAFKA_COMPRESSION_TYPE);

        CertUtils.chooseCert(RadiusKnowledgeConfig.KAFKA_SINK_PROTOCOL, properties);

        return properties;
    }


    public static FlinkKafkaProducer<String> getKafkaProducer() {
        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<String>(
                RadiusKnowledgeConfig.OUTPUT_KAFKA_TOPIC,
                new SimpleStringSchema(),
                createProducerConfig(), Optional.empty());

        kafkaProducer.setLogFailuresOnly(false);
//        kafkaProducer.setWriteTimestampToKafka(true);

        return kafkaProducer;
    }
}
