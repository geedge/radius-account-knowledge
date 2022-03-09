package com.zdjizhi.topology;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zdjizhi.utils.functions.FilterNullFunction;
import com.zdjizhi.utils.functions.MapCompletedFunction;
import com.zdjizhi.utils.kafka.KafkaConsumer;
import com.zdjizhi.utils.kafka.KafkaProducer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author qidaijie
 * @Package com.zdjizhi.topology
 * @Description:
 * @date 2021/5/2016:42
 */
public class RadiusKnowledgeTopology {
    private static final Log logger = LogFactory.get();

    public static void main(String[] args) {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> streamSource = environment.addSource(KafkaConsumer.getKafkaConsumer());

        DataStream<String> result = streamSource.map(new MapCompletedFunction()).name("RadiusOnOffMap")
                .filter(new FilterNullFunction()).name("FilterAbnormalData");

        result.addSink(KafkaProducer.getKafkaProducer()).name("LogSink");

        try {
            environment.execute("RADIUS-ON-OFF");
        } catch (Exception e) {
            logger.error("This Flink task start ERROR! Exception information is :" + e);
        }

    }


}
