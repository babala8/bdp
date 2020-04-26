package com.zjft.microservice.treasurybrain.managecenter.web;


import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author 崔耀中
 * @since 2020-01-09
 */
@Api(tags = "综合管理：车辆信息（工作流）",value = "综合管理：车辆信息（工作流）")
public interface CarFlowResource {

	String PREFIX = "${manage-center:}/v2/carFlow";

	/**
	 * 增加车辆
	 *
	 * @param carDTO 车辆信息
	 * @return 响应信息
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "增加车辆",notes = "增加车辆信息")
	@ZjWorkFlow("addCarInfo")
	DTO addCarInfo(@RequestBody @ApiParam(name = "车辆信息基本信息", value = "车辆信息基本信息", required = true) CarDTO carDTO);

	/**
	 * 修改车辆信息
	 *
	 * @param carDTO 车辆信息
	 * @return 响应信息
	 */
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改车辆信息", notes = "修改车辆信息")
	@ZjWorkFlow("modCarInfo")
	DTO modCarInfo(@RequestBody @ApiParam(name = "车辆信息", value = "车辆信息", required = true) CarDTO carDTO);

	/**
	 * 删除车辆信息
	 *
	 * @param carNo 车辆编号
	 * @return 响应信息
	 */
	@DeleteMapping(PREFIX + "/{carNo}")
	@ApiOperation(value = "删除车辆信息", notes = "删除车辆信息")
	@ZjWorkFlow("delCarInfoByNo")
	@ApiImplicitParam(name = "carNo", value = "车辆编号", example = "1001", required = true, paramType = "path")
	DTO delCarInfoByNo(@PathVariable("carNo") String carNo);

}
