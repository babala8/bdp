package com.zjft.microservice.treasurybrain.channelcenter.web;


import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author 崔耀中
 * @since 2020-01-08
 */
@Api(tags = "渠道中心：清分机信息（工作流）",value = "渠道中心：清分机信息（工作流）")
public interface DevFlowResource {

	String PREFIX = "${channel-center:}/v2/devCount";

	/**
	 * 新增清分机信息
	 *
	 * @param devCountDTO 清分机信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "新增清分机信息（工作流）",notes = "新增清分机信息（工作流）")
	@PostMapping(path = PREFIX + "/insert")
	@ZjWorkFlow("addDevCountInfo")
	DTO addDevInfo(@RequestBody DevCountDTO devCountDTO);

	/**
	 * 删除清分机信息
	 *
	 * @param devNo 清分机编号
	 * @return 响应信息
	 */
	@ApiOperation(value = "删除清分机信息（工作流）",notes = "删除清分机信息（工作流）")
	@DeleteMapping(path = PREFIX + "/del")
	@ZjWorkFlow("delDevCountByNo")
	DTO delDevCountByNo(@RequestParam("devNo") String devNo);

	/**
	 * 修改清分机信息
	 *
	 * @param devCountDTO 清分机信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "修改清分机信息（工作流）",notes = "修改清分机信息（工作流）")
	@PutMapping(path = PREFIX + "/mod")
	@ZjWorkFlow("modDevCount")
	DTO modDevCount(@RequestBody DevCountDTO devCountDTO);
}
