package com.zdjizhi.utils.functions;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.zdjizhi.common.RadiusKnowledgeConfig;
import com.zdjizhi.utils.StringUtil;
import org.apache.flink.api.common.functions.FilterFunction;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.functions
 * @Description:
 * @date 2021/5/2715:01
 */
public class FilterLogFunction implements FilterFunction<String> {
    private static final Log logger = LogFactory.get();

    @Override
    public boolean filter(String message) {
        boolean legitimate = false;
        try {
            if (StringUtil.isNotBlank(message)) {
                JSONObject jsonObject = JSONObject.parseObject(message);
                if (jsonObject.containsKey(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE)) {
                    int packetType = jsonObject.getInteger(RadiusKnowledgeConfig.RADIUS_PACKET_TYPE);
                    if (RadiusKnowledgeConfig.ACCOUNTING_REQUEST == packetType) {
                        int statusType = jsonObject.getInteger(RadiusKnowledgeConfig.RADIUS_ACCT_STATUS_TYPE);
                        if (RadiusKnowledgeConfig.START_BILLING == statusType || RadiusKnowledgeConfig.STOP_BILLING == statusType) {
                            legitimate = true;
                        }
                    }
                }
            }
        } catch (RuntimeException re) {
            logger.error("数据解析异常,异常信息:" + re);
        }
        return legitimate;
    }
}
