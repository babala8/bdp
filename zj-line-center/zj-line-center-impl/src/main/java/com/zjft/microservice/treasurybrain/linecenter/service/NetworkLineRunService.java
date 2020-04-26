package com.zjft.microservice.treasurybrain.linecenter.service;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetworkLineRunInfoDTO;

import java.text.ParseException;
import java.util.Map;


/**
 * @author zhangjs
 * 网点线路运行图管理
 * @since 2019/8/28 18:51
 */
public interface NetworkLineRunService {

	Map<String, Object> qryNetworkLineRunMap(String createJsonString);

	Map<String, Object> addNetworkLineRunMap(String createJsonString) throws ParseException;

	Map<String, Object> modNetworkLineRunMap(LineWorkTableDTO dto);

	Map<String, Object> qrydetailNetworkLineRunMap(String createJsonString);

}
