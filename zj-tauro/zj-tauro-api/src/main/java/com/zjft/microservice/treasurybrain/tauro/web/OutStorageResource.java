package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liuyuan
 * @since 2019/8/7 15:44
 */

@Api(value = "物流中心：出库交接",tags = "物流中心：出库交接")
public interface OutStorageResource {

	String PREFIX = "${tauro:}/v2/outStorage";

	/**
	 * 物品出库
	 * 1.更新任务状态
	 * 2.更新物品和笼车的绑定状态
	 * 3.更新物品操作记录
	 *
	 * @param taskTransferDTO
	 * @return
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "物品出库交接", notes = "物品出库交接")
	DTO outStorage(@RequestBody TaskTransferDTO taskTransferDTO);


}
