package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "仓储中心：实物入库（工作流）",value = "仓储中心：实物入库（工作流）")
public interface GoodInResource {

	String PREFIX = "${storage:}/v2/goodIn";

	/**
	 * 实物入库
	 *
	 * @param storageEntityTransferDTO 仓储物品流转信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "实物入库（工作流）",notes = "实物入库（工作流）")
	@PostMapping(path = PREFIX + "/in")
	@ZjWorkFlow("GoodInWorkFlow")
	DTO transferIn(@RequestBody StorageEntityTransferDTO storageEntityTransferDTO);

	/**
	 * 实物入库
	 *
	 * @param storageEntityTransferDTO 仓储物品流转信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "实物入库--带车（工作流）",notes = "实物入库--带车（工作流）")
	@PostMapping(path = PREFIX + "/inWithShelf")
	@ZjWorkFlow("GoodInWithShelfWorkFlow")
	DTO transferInWithShelf(@RequestBody StorageEntityTransferDTO storageEntityTransferDTO);

	/**
	 * 钞处解现实物入库
	 *
	 * @param storageEntityTransferDTO 仓储物品流转信息
	 * @return 响应信息
	 */
	@ApiOperation(value = "钞处解现实物入库--带车（工作流）",notes = "钞处解现实物入库--带车（工作流）")
	@PostMapping(path = PREFIX + "/bankNoteInWithShelf")
	@ZjWorkFlow("GoodInBankNoteWithShelfWorkFlow")
	DTO bankNoteInWithShelf(@RequestBody StorageEntityTransferDTO storageEntityTransferDTO);
}
