package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.AddCallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeListDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjs
 * @since 2019-09-24
 */
@Api(value = "渠道中心：上门客户周期管理", tags = {"渠道中心：上门客户周期管理"})
public interface CallCustomerTimeResource {

	String PREFIX = "${channel-center:}/v2";

	/**
	 * 查询单个上门客户的周期信息
	 * @param customerNo 上门客户编号
	 * @return
	 */
	@GetMapping(PREFIX+"/callCustomerTime"+"/{customerNo}")
	@ApiOperation(value = "查询单个上门客户的周期信息",notes = "查询单个上门客户的周期信息")
	CallCustomerTimeListDTO qryByCustomerNo(@PathVariable(name = "customerNo")  String customerNo);

    @PutMapping(PREFIX + "/modCallCustomerTime")
	@ApiOperation(value = "修改上门客户周期", notes = "修改上门客户周期")
	DTO modCallCustomerTime(@RequestBody CallCustomerTimeListDTO dto);

	/**
	 *
	 * 根据线路编号和星期查询加钞日期
	 *
	 * @param map lineNo 线路编号 weekDay 星期几
	 * @return
	 */
	List<CallCustomerTimeDTO> qryByLineNoAndWeekDate(Map<String,Object> map);

}
