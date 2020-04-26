package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liuyuan
 * @since 2019/8/14 09:02
 */
@Api(value = "物流中心：回收",tags = "物流中心：回收")
public interface RecoverResource {


	String PREFIX = "${tauro:}/v2/recover";

	/**
	 *
	 * 1.根据人员编号查询任务单任务单编号
	 * 2.更新任务单消息
	 * @param taskTransferDTO
	 * @return
	 */
	@ApiOperation(value = "物流中心：回收",notes = "物流中心：回收")
	@PostMapping(path = PREFIX )
	DTO recover(@RequestBody TaskTransferDTO taskTransferDTO);


}
