package com.zdjizhi.utils.functions;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson2.JSONObject;
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
                JSONObject radiusLog = JSONObject.parse(logs);
                if (radiusLog.containsKey(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE)) {
                    int packetType = radiusLog.getIntValue(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE);
                    if (RadiusKnowledgeConfig.ACCOUNTING_REQUEST == packetType) {
                        result = GetKnowledgeLogs(radiusLog);
                    }

                }
            }
        } catch (RuntimeException e) {
            logger.error("Radius log parsing exception,Detailed info:" + e);
        }
        return result;
    }

    /**
     * 获取RadiusOnOff日志
     *
     * @param radiusLog 原始日志
     * @return OnOff日志
     */
    private static String GetKnowledgeLogs(JSONObject radiusLog) {
        JSONObject knowledge = new JSONObject();

        String framedIp = radiusLog.getString("radius_framed_ip");
        String account = radiusLog.getString("radius_account");

        if (StringUtil.isNotBlank(framedIp) && StringUtil.isNotBlank(account)) {
            knowledge.put("framed_ip", framedIp);

            knowledge.put("account", account);

            //所属vsys,若不填写默认为1
            knowledge.put("vsys_id", radiusLog.getIntValue("common_vsys_id", 1));

            //计费请求报文类型
            knowledge.put("acct_status_type", radiusLog.getString("radius_acct_status_type"));

            //如果存在时间戳则选择此时间戳没有获取当前时间
            knowledge.put("event_timestamp", radiusLog.getLongValue("radius_event_timestamp", System.currentTimeMillis() / 1000));

            /*
             * 标识同一个连接：
             * 1.数据若存在acct_multi_session_id属性，取该属性
             * 2.不存在取 acct_session_id
             */
            if (radiusLog.containsKey("radius_acct_multi_session_id")) {
                knowledge.put("acct_session_id", radiusLog.getString("radius_acct_multi_session_id"));
            } else {
                knowledge.put("acct_session_id", radiusLog.getString("radius_acct_session_id"));
            }

            //用户的在线时长，以秒为单位,下线用户无此属性，默认为0
            knowledge.put("acct_session_time", radiusLog.getLongValue("radius_acct_session_time", 0L));

            return knowledge.toString();
        }
        return "";
    }
}
