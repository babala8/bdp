package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "仓储中心：实物出库（工作流）",value = "仓储中心：实物出库（工作流）")
public interface GoodOutResource {

	String PREFIX = "${storage:}/v2/goodOut";

	/**
	 * 实物出库
	 *
	 * @param storageEntityTransferDTO 仓储物品流转信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "实物出库（工作流）",notes = "实物出库（工作流）")
	@PostMapping(path = PREFIX + "/out")
	@ZjWorkFlow("GoodOutWorkFlow")
	DTO transferOut(@RequestBody StorageEntityTransferDTO storageEntityTransferDTO);

	/**
	 * 实物出库(解绑笼车)
	 *
	 * @param storageEntityTransferDTO 仓储物品流转信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "实物出库-解绑笼车（工作流）",notes = "实物出库-解绑笼车（工作流）")
	@PostMapping(path = PREFIX + "/outUntied")
	@ZjWorkFlow("GoodOutUntiedWorkFlow")
	DTO transferOutUntied(@RequestBody StorageEntityTransferDTO storageEntityTransferDTO);

	/**
	 * 钞处领现实物出库(解绑笼车)
	 *
	 * @param storageEntityTransferDTO 仓储物品流转信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "钞处领现实物出库-解绑笼车（工作流）",notes = "钞处领现实物出库-解绑笼车（工作流）")
	@PostMapping(path = PREFIX + "/outTransferUntied")
	@ZjWorkFlow("GoodOutTransferUntiedWorkFlow")
	DTO bankNotetransferOutUntied(@RequestBody StorageEntityTransferDTO storageEntityTransferDTO);

}
