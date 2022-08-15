package com.zdjizhi.common;


import com.zdjizhi.utils.system.RadiusKnowledgeConfigurations;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author Administrator
 */
public class RadiusKnowledgeConfig {

    private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    static {
        encryptor.setPassword("galaxy");
    }

    /**
     * 4- Accounting-Request(账户授权)
     */
    public static final int ACCOUNTING_REQUEST = 4;
    /**
     * 1、开始计费
     */
    public static final int START_BILLING = 1;

    /**
     * 3、过渡计费
     */
    public static final int UPDATE_BILLING = 3;
    /**
     * 2、停止计费
     */
    public static final int STOP_BILLING = 2;

    /**
     * 报文类型
     */
    public static final String RADIUS_PACKET_TYPE = "radius_packet_type";



    /**
     * kafka
     */
    public static final String SOURCE_KAFKA_SERVERS = RadiusKnowledgeConfigurations.getStringProperty(0, "source.kafka.servers");
    public static final String SOURCE_KAFKA_TOPIC = RadiusKnowledgeConfigurations.getStringProperty(0, "source.kafka.topic");
    public static final String SINK_KAFKA_SERVERS = RadiusKnowledgeConfigurations.getStringProperty(0, "sink.kafka.servers");
    public static final String SINK_KAFKA_TOPIC = RadiusKnowledgeConfigurations.getStringProperty(0, "sink.kafka.topic");
    public static final String GROUP_ID = RadiusKnowledgeConfigurations.getStringProperty(0, "group.id");
    public static final String PRODUCER_ACK = RadiusKnowledgeConfigurations.getStringProperty(1, "producer.ack");
    public static final String PRODUCER_KAFKA_COMPRESSION_TYPE = RadiusKnowledgeConfigurations.getStringProperty(1, "producer.kafka.compression.type");

    /**
     * default config
     */
    public static final String RETRIES = RadiusKnowledgeConfigurations.getStringProperty(1, "retries");
    public static final String LINGER_MS = RadiusKnowledgeConfigurations.getStringProperty(1, "linger.ms");
    public static final Integer REQUEST_TIMEOUT_MS = RadiusKnowledgeConfigurations.getIntProperty(1, "request.timeout.ms");
    public static final Integer BATCH_SIZE = RadiusKnowledgeConfigurations.getIntProperty(1, "batch.size");
    public static final Integer BUFFER_MEMORY = RadiusKnowledgeConfigurations.getIntProperty(1, "buffer.memory");
    public static final Integer MAX_REQUEST_SIZE = RadiusKnowledgeConfigurations.getIntProperty(1, "max.request.size");
    public static final String TOOLS_LIBRARY = RadiusKnowledgeConfigurations.getStringProperty(0, "tools.library");
    public static final String KAFKA_SASL_JAAS_USER = encryptor.decrypt(RadiusKnowledgeConfigurations.getStringProperty(1, "kafka.user"));
    public static final String KAFKA_SASL_JAAS_PIN = encryptor.decrypt(RadiusKnowledgeConfigurations.getStringProperty(1, "kafka.pin"));

    /**
     * kafka source config
     */
    public static final String SESSION_TIMEOUT_MS = RadiusKnowledgeConfigurations.getStringProperty(1, "session.timeout.ms");
    public static final String MAX_POLL_RECORDS = RadiusKnowledgeConfigurations.getStringProperty(1, "max.poll.records");
    public static final String MAX_PARTITION_FETCH_BYTES = RadiusKnowledgeConfigurations.getStringProperty(1, "max.partition.fetch.bytes");

}