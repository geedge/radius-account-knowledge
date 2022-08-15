package com.zdjizhi.utils.functions;

import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
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
                JSONObject json = new JSONObject(logs, false, true);
                if (json.containsKey(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE)) {
                    int packetType = json.getInt(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE);
                    if (RadiusKnowledgeConfig.ACCOUNTING_REQUEST == packetType) {
                        result = GetKnowledgeLogs(json);
                    }

                }
            }
        } catch (RuntimeException e) {
            logger.error("Radius log parsing exception,Message is :" + e);
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
        String framedIp = jsonMap.getStr("radius_framed_ip");
        String account = jsonMap.getStr("radius_account");

        if (StringUtil.isNotBlank(framedIp) && StringUtil.isNotBlank(account)) {
            knowledge.set("framed_ip", framedIp);

            knowledge.set("account", account);

            //计费请求报文类型
            knowledge.set("acct_status_type", jsonMap.getStr("radius_acct_status_type"));

            //如果存在时间戳则选择此时间戳没有获取当前时间
            knowledge.set("event_timestamp", jsonMap.getLong("radius_event_timestamp", System.currentTimeMillis() / 1000));

            /*
             * 标识同一个连接：
             * 1.数据若存在acct_multi_session_id属性，取该属性
             * 2.不存在取 acct_session_id
             */
            if (jsonMap.containsKey("radius_acct_multi_session_id")) {
                knowledge.set("acct_session_id", jsonMap.getStr("radius_acct_multi_session_id"));
            } else {
                knowledge.set("acct_session_id", jsonMap.getStr("radius_acct_session_id"));
            }

            //用户的在线时长，以秒为单位,下线用户无此属性，默认为0
            knowledge.set("acct_session_time", jsonMap.getLong("radius_acct_session_time", 0L));

            return knowledge.toString();
        }
        return "";
    }
}
