package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.CassetteBagInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 钞袋信息DTO
 *
 * @author liuyuan
 * @since 2019/6/12 19:07
 */

@Api(tags = "综合管理：钞袋管理")
public interface CassetteBagInfoResource {

	String PREFIX = "${manage-center:}/v2/cassettebag";

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询钞袋信息", notes = "分页查询钞袋信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bagNo", value = "钞箱袋管理编号", example = "051003281998800001", paramType = "query"),
			@ApiImplicitParam(name = "bagNoBank", value = "钞箱袋银行编号", example = "051003281998800001", paramType = "query"),
			@ApiImplicitParam(name = "bagVendor", value = "钞箱袋品牌", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")

	})
	PageDTO<CassetteBagInfoDTO> qryCassetteBagInfoByPage(@ApiIgnore @RequestParam Map<String, Object> paramsMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "添加钞袋", notes = "添加钞袋")
	DTO insert(@RequestBody @ApiParam(name = "钞袋基本信息", value = "钞袋基本信息", required = true) CassetteBagInfoDTO cassetteBagInfoDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改钞袋", notes = "修改钞袋")
	DTO modByNo(@RequestBody @ApiParam(name = "钞袋基本信息", value = "钞袋基本信息", required = true) CassetteBagInfoDTO cassetteBagInfoDTO);

	@DeleteMapping(PREFIX + "/{bagNo}")
	@ApiOperation(value = "删除钞袋", notes = "删除钞袋")
	@ApiImplicitParam(name = "bagNo", value = "钞箱袋管理编号", example = "051003281998800001", required = true, paramType = "path")
	DTO delByNo(@PathVariable("bagNo") String bagNo);

}
