package com.zdjizhi;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.Map;

/**
 * @author qidaijie
 * @Package com.zdjizhi
 * @Description:
 * @date 2021/6/3011:46
 */
public class FunctionTest {

    @Test
    public void jsonTest(){
        String logs = "{\"common_schema_type\":\"HTTP\",\"common_sessions\":1,\"http_request_line\":\"POST /portmap HTTP/1.1\",\"http_request_content_type\":\"application/json\",\"http_request_content_length\":21,\"http_user_agent\":\"Jakarta Commons-HttpClient/3.1\",\"http_host\":\"portmap.pro.testin.cn:8888\",\"http_url\":\"portmap.pro.testin.cn:8888/portmap\",\"http_response_line\":\"HTTP/1.1 200 OK\",\"http_response_content_length\":286,\"http_isn\":2504229113,\"http_proxy_flag\":0,\"http_version\":\"http1\",\"http_response_latency_ms\":2,\"http_session_duration_ms\":3,\"http_sequence\":1,\"common_protocol_label\":\"ETHERNET.VLAN.IPv4.TCP\",\"common_c2s_byte_diff\":555,\"common_c2s_pkt_diff\":5,\"common_s2c_byte_diff\":684,\"common_s2c_pkt_diff\":4,\"common_c2s_ipfrag_num\":0,\"common_s2c_ipfrag_num\":0,\"common_first_ttl\":64,\"common_c2s_tcp_unorder_num\":0,\"common_s2c_tcp_unorder_num\":0,\"common_c2s_tcp_lostlen\":0,\"common_s2c_tcp_lostlen\":0,\"common_c2s_pkt_retrans\":0,\"common_s2c_pkt_retrans\":0,\"common_c2s_byte_retrans\":0,\"common_s2c_byte_retrans\":0,\"common_direction\":69,\"common_l7_protocol\":\"HTTP\",\"common_tcp_client_isn\":2504229113,\"common_tcp_server_isn\":468823374,\"common_server_ip\":\"192.168.42.1\",\"common_client_ip\":\"192.168.56.14\",\"common_server_port\":8888,\"common_client_port\":51118,\"common_stream_dir\":3,\"common_address_type\":4,\"common_address_list\":\"51118-8888-192.168.56.14-192.168.42.1\",\"common_start_time\":1652247928,\"common_end_time\":1652247928,\"common_con_duration_ms\":4,\"common_s2c_pkt_num\":4,\"common_s2c_byte_num\":684,\"common_c2s_pkt_num\":5,\"common_c2s_byte_num\":555,\"common_establish_latency_ms\":2,\"http_action_file_size\":0,\"common_tunnels\":[{\"tunnels_schema_type\":\"VLAN\",\"vlan_c2s_direction_id\":[56],\"vlan_s2c_direction_id\":[56]},{\"tunnels_schema_type\":\"MULTIPATH_ETHERNET\",\"c2s_source_mac\":\"00:0e:c6:6f:dd:12\",\"c2s_destination_mac\":\"e8:1c:ba:cc:87:17\",\"s2c_source_mac\":\"e8:1c:ba:cc:87:17\",\"s2c_destination_mac\":\"00:0e:c6:6f:dd:12\"}],\"common_stream_trace_id\":\"869433624695175123\",\"common_l4_protocol\":\"IPv4_TCP\",\"common_sled_ip\":\"192.168.40.81\",\"common_device_id\":\"21426003\",\"common_device_tag\":\"{\\\"tags\\\":[{\\\"tag\\\":\\\"device_group\\\",\\\"value\\\":\\\"group-xxg-three\\\"}]}\",\"common_policy_id\":0,\"common_service\":2,\"common_action\":0,\"common_link_info_c2s\":{\"functional_ip\":\"192.168.40.81\",\"thread_count\":43,\"relative_location\":\"internal\",\"hash_dist\":2,\"hash_algo\":0,\"linkinfo\":[{\"type\":\"ethernet\",\"src\":\"90:00:00:91:40:45\",\"dst\":\"0a:0a:0a:0a:00:0c\"},{\"type\":\"tuple4v4\",\"sip\":\"10.252.21.1\",\"dip\":\"10.10.0.12\",\"sport\":54789,\"dport\":4789},{\"type\":\"vxlan\",\"vlanid\":0,\"dir\":0,\"linkid\":22,\"linktype\":0},{\"type\":\"ethernet\",\"src\":\"00:0e:c6:6f:dd:12\",\"dst\":\"e8:1c:ba:cc:87:17\"},{\"type\":\"vlan\",\"vlan_id_array\":[56]}]},\"common_link_info_s2c\":{\"functional_ip\":\"192.168.40.81\",\"thread_count\":43,\"relative_location\":\"external\",\"hash_dist\":2,\"hash_algo\":0,\"linkinfo\":[{\"type\":\"ethernet\",\"src\":\"90:00:00:91:40:45\",\"dst\":\"0a:0a:0a:0a:00:0c\"},{\"type\":\"tuple4v4\",\"sip\":\"10.252.21.1\",\"dip\":\"10.10.0.12\",\"sport\":54789,\"dport\":4789},{\"type\":\"vxlan\",\"vlanid\":0,\"dir\":1,\"linkid\":22,\"linktype\":0},{\"type\":\"ethernet\",\"src\":\"e8:1c:ba:cc:87:17\",\"dst\":\"00:0e:c6:6f:dd:12\"},{\"type\":\"vlan\",\"vlan_id_array\":[56]}]}}\n";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(logs);
        System.out.println(JsonPath.read(document, "$.name").toString());
        JSONObject jsonObject = JSONObject.parseObject(logs);
        System.out.println(jsonObject.getString("common_schema_type"));

    }
}
