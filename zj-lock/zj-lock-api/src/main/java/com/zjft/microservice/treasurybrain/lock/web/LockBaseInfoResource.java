package com.zjft.microservice.treasurybrain.lock.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockBaseInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 韩通
 * @since 2019-06-25
 */
@Api(value = "电子密码锁模块：锁具信息管理", tags = "电子密码锁模块：锁具信息管理")
public interface LockBaseInfoResource {

	String PREFIX = "${lock:}/v2/lock";

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加锁具信息", notes = "增加锁具信息")
	DTO addLockBaseInfo(@RequestBody LockBaseInfoDTO lockBaseInfoDTO);

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询锁具信息", notes = "查询锁具信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lockCode", value = "锁具序列号", paramType = "query"),
			@ApiImplicitParam(name = "devNo", value = "设备编号", paramType = "query"),
			@ApiImplicitParam(name = "version", value = "锁具版本", paramType = "query"),
			@ApiImplicitParam(name = "cversion", value = "C端程序版本", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "是否激活", paramType = "query"),
			@ApiImplicitParam(name = "madeDate", value = "生产日期", paramType = "query"),
			@ApiImplicitParam(name = "installDate", value = "安装日期", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "备注", paramType = "query"),
			@ApiImplicitParam(name = "blockNum", value = "闭锁码", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<LockBaseInfoDTO> qryLockBaseInfoByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改锁具信息", notes = "修改锁具信息")
	DTO modLockBaseInfo(@RequestBody LockBaseInfoDTO lockBaseInfoDTO);

	@DeleteMapping(PREFIX + "/{lockCode}")
	@ApiOperation(value = "删除锁具信息", notes = "删除锁具信息")
	@ApiImplicitParam(name = "lockCode", value = "锁具序列号", required = true, paramType = "path")
	DTO delLockBaseInfo(@PathVariable("lockCode") String lockCode);

	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "锁具信息详情查询", notes = "锁具信息详情查询")
	@ApiImplicitParam(name = "lockCode", value = "锁具序列号", required = true, paramType = "query")
	LockBaseInfoDTO qryLockBaseInfoDetail(@RequestParam("lockCode") String lockCode);
}
