package com.zdjizhi.utils.functions;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.common.RadiusKnowledgeConfig;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/5/2715:01
 */
public class MapCompletedFunction implements MapFunction<String, String> {
    private static final Log logger = LogFactory.get();

    @Override
    public String map(String logs) {
        String result = null;
        try {
            if (StringUtil.isNotBlank(logs)) {
                JSONObject jsonMap = JSONObject.parseObject(logs);
                if (jsonMap.containsKey(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE)) {
                    int packetType = jsonMap.getInteger(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE);
                    if (RadiusKnowledgeConfig.ACCOUNTING_REQUEST == packetType) {
                        result = GetKnowledgeLogs(jsonMap);
                    }

                }
            }
        } catch (RuntimeException e) {
            logger.error("数据解析异常,异常信息:" + e);
        }
        return result;
    }

    /**
     * 获取RadiusOnOff日志
     *
     * @param jsonMap 原始日志
     * @return OnOff日志
     */
    private static String GetKnowledgeLogs(JSONObject jsonMap) {
        JSONObject knowledge = new JSONObject();

        knowledge.put("framed_ip", jsonMap.getString("radius_framed_ip"));

        knowledge.put("account", jsonMap.getString("radius_account"));

        knowledge.put("acct_status_type", jsonMap.getString("radius_acct_status_type"));

        /*
        如果存在时间戳则选择此时间戳没有获取当前时间
        */
        if (jsonMap.containsKey(RadiusKnowledgeConfig.RADIUS_EVENT_TIMESTAMP)) {
            knowledge.put("event_timestamp", jsonMap.getString("radius_event_timestamp"));
        } else {
            knowledge.put("event_timestamp", (System.currentTimeMillis() / 1000));
        }
        /*
         * 标识同一个连接：
         * 1.数据若存在acct_multi_session_id属性，取该属性
         * 2.不存在取 acct_session_id
         */
        if (jsonMap.containsKey(RadiusKnowledgeConfig.RADIUS_MULTI_SESSION_ID)) {
            knowledge.put("acct_session_id", jsonMap.getString("radius_acct_multi_session_id"));
        } else {
            knowledge.put("acct_session_id", jsonMap.getString("radius_acct_multi_session_id"));
        }
         /*
           用户的在线时长，以秒为单位,下线用户无此属性，默认为0
         */
        if (jsonMap.containsKey(RadiusKnowledgeConfig.RADIUS_SESSION_TIME)) {
            knowledge.put("acct_session_time", jsonMap.getString("radius_acct_session_time"));
        } else {
            knowledge.put("acct_session_time", 0);
        }

        return knowledge.toJSONString();
    }
}
