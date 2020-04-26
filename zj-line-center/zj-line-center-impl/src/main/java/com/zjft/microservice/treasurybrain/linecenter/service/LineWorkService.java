package com.zjft.microservice.treasurybrain.linecenter.service;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/27
 */
public interface LineWorkService {

	List<String> qryTaskNoByLineNo(String lineNo);

	String qryLineWorkId(Map<String, Object> paramMap);


}
