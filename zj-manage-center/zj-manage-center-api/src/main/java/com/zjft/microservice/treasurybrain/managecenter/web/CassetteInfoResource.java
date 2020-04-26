package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 钞箱信息接口类
 *
 * @author liuyuan
 * @since 2019/6/11 16:48
 */
@Api(tags = "综合管理：钞箱管理")
public interface CassetteInfoResource {

	String PREFIX = "${manage-center:}/v2/cassette";

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询钞箱信息", notes = "分页查询钞箱信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cassetteNo", value = "钞箱编号", example = "051003281088800001", paramType = "query"),
			@ApiImplicitParam(name = "cassetteNoBank", value = "银行钞箱编号", example = "051003281088800001", paramType = "query"),
			@ApiImplicitParam(name = "cassetteType", value = "钞箱类型", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "cassetteVendor", value = "钞箱品牌", example = "10001", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", example = "2", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<CassetteInfoDTO> qryCassetteInfoByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "新增钞箱信息", notes = "新增钞箱信息")
	DTO insert(@RequestBody @ApiParam(name = "钞箱基本信息", value = "钞箱基本信息", required = true) CassetteInfoDTO cassetteInfoDTO);

	@DeleteMapping(PREFIX + "/{cassetteNo}")
	@ApiOperation(value = "根据钞箱编号删除钞箱信息", notes = "根据钞箱编号删除钞箱信息")
	@ApiImplicitParam(name = "cassetteNo", value = "钞箱编号", example = "051003281088800001", required = true, paramType = "path")
	DTO delByNo(@PathVariable("cassetteNo") String cassetteNo);

	@PutMapping(PREFIX)
	@ApiOperation(value = "根据钞箱编号修改钞箱信息", notes = "根据钞箱编号修改钞箱信息")
	DTO modByNo(@RequestBody @ApiParam(name = "钞箱基本信息", value = "钞箱基本信息", required = true) CassetteInfoDTO cassetteInfoDTO);


}
