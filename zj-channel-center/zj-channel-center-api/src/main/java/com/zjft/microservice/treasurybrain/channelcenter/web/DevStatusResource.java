package com.zjft.microservice.treasurybrain.channelcenter.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "渠道中心：设备状态", tags = {"渠道中心：设备状态"})
public interface DevStatusResource {

	String PREFIX = "${channel-center:}/v2/dev/status";

	/**FIXME
	 * 此resource唯一功能为从dev_status_table表根据devNo字符串查询出所有不在此字符串中的devNo和对应的预测金额availableAmt
	 * 经过优化后，目前没有任何方法调用该resource
	 * @param devNosSb
	 * @return
	 */
	@GetMapping(PREFIX + "/inner/list")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> queryForList(@RequestParam("devNosSb") String devNosSb);


}
