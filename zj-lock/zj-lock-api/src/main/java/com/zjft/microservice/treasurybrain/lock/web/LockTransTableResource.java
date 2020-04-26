package com.zjft.microservice.treasurybrain.lock.web;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockTransTableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 韩通
 * @since 2019-06-26
 *
 */
@Api(value = "电子密码锁模块：锁具日志管理", tags = "电子密码锁模块：锁具日志管理")
public interface LockTransTableResource {

	String PREFIX = "${lock:}/v2/lockTrans";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询锁具日志", notes = "查询锁具日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serialNo", value = "交易流水号", paramType = "query"),
			@ApiImplicitParam(name = "devNo", value = "设备编号", paramType = "query"),
			@ApiImplicitParam(name = "lockCode", value = "锁具序列号", paramType = "query"),
			@ApiImplicitParam(name = "tranStartDate", value = "交易开始日期", paramType = "query"),
			@ApiImplicitParam(name = "tranEndDate", value = "交易结束日期", paramType = "query"),
			//@ApiImplicitParam(name = "tranTime", value = "交易时间", paramType = "query"),
			@ApiImplicitParam(name = "tranType", value = "交易类型", paramType = "query"),
			@ApiImplicitParam(name = "encryptCode", value = "密码服务器返回码", paramType = "query"),
			@ApiImplicitParam(name = "esbCode", value = "ESB服务器返回码", paramType = "query"),
			@ApiImplicitParam(name = "retCode", value = "返回码", paramType = "query"),
			@ApiImplicitParam(name = "retMsg", value = "返回信息", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<LockTransTableDTO> qryLockTransByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

}
