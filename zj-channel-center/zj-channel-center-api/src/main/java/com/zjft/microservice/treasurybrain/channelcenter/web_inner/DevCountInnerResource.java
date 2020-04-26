package com.zjft.microservice.treasurybrain.channelcenter.web_inner;


import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
public interface DevCountInnerResource {

	@ApiOperation(value = "内部服务",notes = "内部服务")
	int deleteByDevNo(String devNo);

	@ApiOperation(value = "内部服务",notes = "内部服务")
	int insert(Map<String,Object> record);

	@ApiOperation(value = "内部服务",notes = "内部服务")
	List<String> selectlineNoList(String devNo);

	@ApiOperation(value = "内部服务",notes = "内部服务")
	List selectDctVOList(Map<String, Object> paramMap);


}
