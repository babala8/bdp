package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 19:16
 */

@Api(value = "线路中心：网点线路管理" ,tags = "线路中心：网点线路管理")
public interface NetworkLineResource {

	String PREFIX = "${line-center:}/v2/networkLine";

	@GetMapping(PREFIX+"/lineType")
	@ApiOperation(value = "查询某类型所有线路",notes = "查询某类型所有线路")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lineType",value = "线路类型 0-ATM加钞路线 1-网点加钞路线 2-上门收款 3-紧急加钞路线"),
			@ApiImplicitParam(name = "clrCenterNo",value = "金库编号"),
	})
	ListDTO<LineTableDTO> qryAllLineByType(@RequestParam @ApiIgnore Map<String,Object> paramsMap);

	/**
	 * 查询自己当天可执行的线路
	 */
	@GetMapping(path = PREFIX + "/qryTodayLine")
	@ApiOperation(value = "查询自己当天可执行的线路", notes = "查询自己当天可执行的线路")
	@ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "query")
	List<String> qryTodayLine(@RequestParam("status") String status);

	/**
	 * 查询可执行线路
	 *
	 * 1.输入容器类型（3），任务单类型，任务单状态（304） 查询可出库线路
	 * 2.输入任务单类型，查询使用中的此任务单类型对应线路
	 * @return
	 */
	@GetMapping(PREFIX+"/qryLineNoList")
	@ApiOperation(value = "查询可执行线路",notes = "查询可执行线路")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskType", value = "任务单类型" , paramType = "query"),
			//@ApiImplicitParam(name = "status", value = "任务单状态" , paramType = "query"),
			@ApiImplicitParam(name = "operateType", value = "操作类型" , paramType = "query"),
			@ApiImplicitParam(name = "productNo", value = "容器类型" ,paramType = "query"),
			@ApiImplicitParam(name = "planFinishDate", value = "计划完成日期" ,paramType = "query")
	})
	ListDTO  qryLineNoList(@ApiIgnore @RequestParam Map<String, Object> paramMap);


}
