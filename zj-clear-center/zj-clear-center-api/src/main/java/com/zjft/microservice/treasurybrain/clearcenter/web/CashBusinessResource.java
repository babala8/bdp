package com.zjft.microservice.treasurybrain.clearcenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.clearcenter.dto.LoadNoteDTO;
import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;



/**
 * @author liuyuan
 * @since 2019/8/7 14:38
 */
@Api(value = "钞处中心：现金业务管理",tags = "钞处中心：现金业务管理")
public interface CashBusinessResource {

	String PREFIX = "${clear-center:}/v2/cashBusiness";

	/**
	 * 任务配钞
	 *
	 * @param loadNoteDTO
	 * @return
	 */
	@PostMapping(PREFIX+"/loadNote")
	@ApiOperation(value = "配钞", notes = "根据任务单信息配钞至现金容器")
	@ZjWorkFlow("loadNote")
	DTO loadNote(@RequestBody LoadNoteDTO loadNoteDTO);

	@PostMapping(PREFIX+"/inventory")
	@ApiOperation(value = "清点", notes = "清点现金容器中的金额，并插入任务容器表")
	@ZjWorkFlow("inventory")
	DTO inventory(@RequestBody TaskCheckDTO taskCheckDTO);

	@PostMapping(PREFIX+"/cashIn")
	@ApiOperation(value = "现金入库", notes = "现金入库")
	@ZjWorkFlow("cashInStorage")
	DTO cashInStorage(@RequestBody ArrayList<String> taskNos);

	@PostMapping(PREFIX+"/cashDistribution")
	@ApiOperation(value = "配款领现", notes = "配款领现")
	@ZjWorkFlow("cashDistribution")
	DTO cashDistribution(@RequestBody ArrayList<String> taskNos);
}
