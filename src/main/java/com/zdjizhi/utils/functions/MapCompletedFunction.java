package com.zdjizhi.utils.functions;

import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.bean.RadiusKnowledge;
import com.zdjizhi.common.RadiusKnowledgeConfig;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/5/2715:01
 */
public class MapCompletedFunction implements MapFunction<String, String> {

    @Override
    public String map(String logs) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(logs);
            RadiusKnowledge knowledge = new RadiusKnowledge();
            knowledge.setFramed_ip(jsonObject.getString("radius_framed_ip"));
            knowledge.setAccount(jsonObject.getString("radius_account"));
            knowledge.setAcct_status_type(jsonObject.getInteger("radius_acct_status_type"));
            /*
            如果存在时间戳则选择此时间戳没有获取当前时间
            */
            if (jsonObject.containsKey(RadiusKnowledgeConfig.RADIUS_EVENT_TIMESTAMP)) {
                knowledge.setEvent_timestamp(jsonObject.getLong("radius_event_timestamp"));
            } else {
                knowledge.setEvent_timestamp(System.currentTimeMillis() / 1000);
            }
            /*
             * 标识同一个连接：
             * 1.数据若存在acct_multi_session_id属性，取该属性
             * 2.不存在取 acct_session_id
             */
            if (jsonObject.containsKey(RadiusKnowledgeConfig.RADIUS_MULTI_SESSION_ID)) {
                knowledge.setAcct_session_id(jsonObject.getString("radius_acct_multi_session_id"));
            } else {
                knowledge.setAcct_session_id(jsonObject.getString("radius_acct_session_id"));
            }
            /*
             用户的在线时长，以秒为单位,下线用户无此属性，默认为0
             */
            if (jsonObject.containsKey(RadiusKnowledgeConfig.RADIUS_SESSION_TIME)) {
                knowledge.setAcct_session_time(jsonObject.getInteger("radius_acct_session_time"));
            } else {
                knowledge.setAcct_session_time(0);
            }
            return JSONObject.toJSONString(knowledge);
        } catch (RuntimeException e) {
            return "";
        }
    }
}
