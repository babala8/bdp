package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.AddCallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.CallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.CallCustomerLineRunMonthDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @author liuyuan
 * @since 2019/9/23 11:22
 */
@Api(tags = "线路中心：上门收款线路管理")
public interface OnSiteCollectionResource {

	String PREFIX = "${line-center:}/v2/onSiteCollection";

	/**
	 * 分页查询上门收款线路安排
	 * 按照线路和月份展示信息，按照月份线路号排序
	 *
	 * @param paramMap 清机中心编号 线路编号 开始月份 结束月份 分页参数
	 * @return
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询上门收款线路安排", notes = "分页查询上门收款线路安排")
	@ZjWorkFlow("qryOnSiteCCollection")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "lineNo", value = "线路编号", paramType = "query"),
			@ApiImplicitParam(name = "startMonth", value = "开始月份[yyyy-mm]", paramType = "query"),
			@ApiImplicitParam(name = "endMonth", value = "结束月份[yyyy-mm]", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<LineWorkTableDTO> qryByPage(@RequestParam @ApiIgnore HashMap<String, Object> paramMap);

	/**
	 * 按线路号，年月查询线路安排及详情
	 *
	 * @param paramMap 线路编号 年月
	 * @return
	 */
	@GetMapping(PREFIX + "/details")
	@ApiOperation(value = "查询上门收款线路安排详情", notes = "查询上门收款线路安排详情")
	@ZjWorkFlow("qryDetailByLineNoAndMonth")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lineNo", value = "线路编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "theYearMonth", value = "年月[yyyy-mm]", required = true, paramType = "query")
	})
	ListDTO<LineWorkTableDTO> qryDetailByLineNoAndMonth(@RequestParam @ApiIgnore HashMap<String, Object> paramMap);

	/**
	 * 修改线路运行图
	 *
	 * @param callCustomerLineRunDTO 修改某线路某日的结果
	 * @return
	 */
	@PutMapping(PREFIX + "/lineRun")
	@ApiOperation(value = "修改线路运行图", notes = "修改线路运行图")
	@ZjWorkFlow("modCallCustomerLineRun")
	DTO modLineRun(@RequestBody LineWorkTableDTO callCustomerLineRunDTO);

	/**
	 * 覆盖生成线路运行图
	 * 先删除后生成
	 * 没有线路编号和
	 *
	 * @param addCallCustomerLineRunDTO 金库编号 年月 线路编号列表
	 * @return
	 */
	@PostMapping(PREFIX + "/lineRun")
	@ApiOperation(value = "覆盖生成线路运行图", notes = "覆盖生成线路运行图")
	@ZjWorkFlow("addAndCoverCallCustomerLineRun")
	DTO addAndCoverLineRun(@RequestBody AddCallCustomerLineRunDTO addCallCustomerLineRunDTO);

}
