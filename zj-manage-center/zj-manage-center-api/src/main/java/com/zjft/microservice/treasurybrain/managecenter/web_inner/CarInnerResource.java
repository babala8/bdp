package com.zjft.microservice.treasurybrain.managecenter.web_inner;


import io.swagger.annotations.ApiOperation;

/**
 * @author 崔耀中
 * @since 2019/12/31
 */
public interface CarInnerResource {

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	String qryCarNumberByNo(Integer carNo);

}
