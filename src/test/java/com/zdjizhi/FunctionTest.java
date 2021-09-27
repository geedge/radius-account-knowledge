package com.zdjizhi;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2021/6/3011:46
 */
public class FunctionTest {

    @Test
    public void jsonTest(){
        String logs = "{\"name\":\"test\"}";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(logs);
        System.out.println(JsonPath.read(document, "$.name").toString());
        JSONObject jsonObject = JSONObject.parseObject(logs);
        System.out.println(jsonObject.getLong("age"));
    }
}
