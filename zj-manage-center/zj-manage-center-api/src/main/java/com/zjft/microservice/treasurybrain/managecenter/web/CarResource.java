package com.zjft.microservice.treasurybrain.managecenter.web;


import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019/9/21
 */
@Api(tags = {"综合管理：车辆管理"})
public interface CarResource {

	String PREFIX = "${manage-center:}/v2/car";

	@GetMapping(PREFIX)
	@ApiOperation(value = "车辆查询列表", notes = "车辆查询列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type",value = "车辆类型",example = "1",paramType = "query"),
			@ApiImplicitParam(name = "carNumber",value = "车牌号",example = "沪A00001",paramType = "query"),
			@ApiImplicitParam(name = "status",value = "车辆状态",example = "1",paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")

	})
	PageDTO<CarDTO> queryCarList(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加车辆",notes = "增加车辆信息")
	DTO addCarInfo(@RequestBody @ApiParam(name = "车辆信息基本信息", value = "车辆信息基本信息", required = true) CarDTO carDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改车辆信息", notes = "修改车辆信息")
	DTO modCarInfo(@RequestBody @ApiParam(name = "车辆信息", value = "车辆信息", required = true) CarDTO carDTO);

	@DeleteMapping(PREFIX + "/{carNo}")
	@ApiOperation(value = "删除车辆信息", notes = "删除车辆信息")
	@ApiImplicitParam(name = "carNo", value = "车辆编号", example = "1001", required = true, paramType = "path")
	DTO delCarInfoByNo(@PathVariable("carNo") String carNo);

	@PostMapping(PREFIX+ "/import")
	@ApiOperation(value = "批量导入车辆信息", notes = "批量导入车辆信息")
	DTO importCar(@RequestParam(value = "file") MultipartFile file);

	List<CarDTO> qryAllCar();
}
