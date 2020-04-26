package com.zjft.microservice.treasurybrain.lock.web;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockStatusTableDTO;
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
@Api(value = "电子密码锁模块：锁具状态监控", tags = "电子密码锁模块：锁具状态监控")
public interface LockStatusTableResource {

	String PREFIX = "${lock:}/v2/lockStatus";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询锁具状态", notes = "查询锁具状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lockCode", value = "锁具序列号", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "监控状态", paramType = "query"),
			@ApiImplicitParam(name = "updateTime", value = "更新时间", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<LockStatusTableDTO> qryLockStatusByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

}
