package com.zjft.microservice.treasurybrain.accountscenter.web;

import com.zjft.microservice.treasurybrain.accountscenter.dto.BiztxlogDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 常 健
 * @since 2019/10/10
 */
@Api(value = "账务管理：交易记录查询", tags = "账务管理：交易记录查询")
public interface TransactionRecordResource {

	String PREFIX = "/accounts-center/v2/biztxlog";

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询推送信息列表", notes = "分页查询推送信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "termId", value = "终端编号", paramType = "query"),
			@ApiImplicitParam(name = "orgNo", value = "机构号", paramType = "query"),
			@ApiImplicitParam(name = "primaryAccount", value = "交易账号", paramType = "query"),
			@ApiImplicitParam(name = "txType", value = "交易类型", paramType = "query"),
			@ApiImplicitParam(name = "txStatus", value = "交易状态", paramType = "query"),
			@ApiImplicitParam(name = "cardFlag", value = "卡标识", paramType = "query"),
			@ApiImplicitParam(name = "cardMedium", value = "卡介质", paramType = "query"),
			@ApiImplicitParam(name = "dayTradeFlag", value = "频繁交易标志", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<BiztxlogDTO> qryByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);
}
