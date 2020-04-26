package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/27
 */
public interface LineWorkInnerResource {

	List<String> qryTaskNoByLineNo(String lineNo);

	/**
	 *根据日期、线路编号查询lineWorkId
	 */
	String qryLineWorkId(Map<String, Object> paramMap);
}
