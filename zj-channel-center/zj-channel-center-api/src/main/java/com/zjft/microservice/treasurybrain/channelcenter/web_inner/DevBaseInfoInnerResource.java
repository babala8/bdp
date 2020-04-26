package com.zjft.microservice.treasurybrain.channelcenter.web_inner;


import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
public interface DevBaseInfoInnerResource {

	//@GetMapping(PREFIX + "/inner/{no}")
	@ApiOperation(value = "根据编号查询设备", notes = "根据编号查询设备（内部服务）")
	//@ApiImplicitParam(name = "no", value = "设备编号", required = true, paramType = "path")
	DevBaseInfo selectByPrimaryKey(@PathVariable("no") String no);


	//@GetMapping(PREFIX + "/inner/dev")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	DevBaseInfo qryDevByNoOrgNo(@RequestParam("devNo") String devNo, @RequestParam("orgNo") String orgNo);


	//@GetMapping(PREFIX + "/inner/devDetail")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	DevBaseInfo selectDetailByPrimaryKey(@RequestParam("devNo") String devNo);


	//@PostMapping("/inner/dev")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	void updateByPrimaryKeySelective(@RequestBody DevBaseInfo devBaseInfo);


	//@GetMapping(PREFIX + "/inner/keyEventDevice")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> getKeyEventDevice(@RequestParam Map<String, Object> params);

	//@GetMapping(PREFIX + "/inner/keyEventDeviceForfault")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> getKeyEventDeviceForfault(@RequestParam Map<String, Object> params);


	//@GetMapping(PREFIX + "/inner/getKeyEventDeviceForLineRun")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> getKeyEventDeviceForLineRun(@RequestParam Map<String, Object> params);


	//@GetMapping(PREFIX + "/inner/availeAmtAndTimeInterval")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> getAvaileAmtAndTimeInterval(@RequestParam Map<String, Object> params);

}
