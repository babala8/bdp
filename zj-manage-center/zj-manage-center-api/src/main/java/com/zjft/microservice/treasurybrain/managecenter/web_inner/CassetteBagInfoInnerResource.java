package com.zjft.microservice.treasurybrain.managecenter.web_inner;


import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-06
 */
public interface CassetteBagInfoInnerResource {

	@ApiOperation(value = "内部接口", notes = "内部接口")
	int updateStatusByNo(Map<String,Object> map);

}
