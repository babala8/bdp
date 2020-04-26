package com.zjft.microservice.treasurybrain.channelcenter.web_inner;


import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-06
 */
public interface CallCustomerInnerResource {

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	String selectOutOrgLineNo(@RequestParam("customerNo") String customerNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	Map<String,Object> selectlineNo (@RequestParam("id") String id);

	//@GetMapping(PREFIX + "/inner/{customerNo}")
	@ApiOperation(value = "根据编号查询上门客户", notes = "根据编号查询上门客户（内部服务）")
	//@ApiImplicitParam(name = "customerNo", value = "客户编号", required = true, paramType = "path")
	CallCustomerDTO selectByPrimaryKey(@PathVariable("customerNo") String customerNo);

	//@PostMapping("/inner/customer")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	void updateByPrimaryKeySelective(@RequestBody CallCustomerDTO callCustomerDTO);

}
