package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityDTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/10
 */
@Api(tags = {"综合管理：款箱基础信息管理"})
public interface EntityBaseInfoResource {
	String PREFIX = "${manage-center:}/v2/entity";


	/**
	 * 根据网点查询款箱号
	 *
	 * @return
	 */
	@GetMapping(PREFIX+"/qryContainerNoList/{customerNo}")
	@ApiOperation(value = "根据网点查询款箱号",notes = "根据网点查询款箱号")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customerNo", value = "网点编号" , required = true, paramType = "path")
	})
	ObjectDTO qryContainerNoList(@PathVariable("customerNo") String customerNo);

	/**
	 * 分页查询款箱基础信息
	 *
	 * @param paramMap 查询参数
	 * @return 款箱基础信息列表
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "查询款箱基础信息列表", notes = "查询款箱基础信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "entityNo", value = "物品编号", example = "01001", paramType = "query"),
			@ApiImplicitParam(name = "goodsNo", value = "物品类型编号", example = "X0001", paramType = "query"),
			@ApiImplicitParam(name = "customerNo", value = "所属对象编号", example = "1001", paramType = "query"),
			@ApiImplicitParam(name = "customerType", value = "所属对象类型", example = "1", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<BaseEntityInfoDTO> queryEntityByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	 * 根据不同类型的款箱添加页面新增款箱基础信息
	 *
	 * @param baseEntityDTO
	 * @return
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "增加款箱基础信息", notes = "增加款箱基础信息")
	DTO addEntityInfo(@RequestBody @ApiParam(name = "款箱基本信息", value = "款箱基本信息", required = true) BaseEntityDTO baseEntityDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改款箱基础信息", notes = "修改款箱基础信息")
	DTO modEntityInfo(@RequestBody @ApiParam(name = "款箱基础信息", value = "款箱基础信息", required = true) BaseEntityDTO baseEntityDTO);

	@DeleteMapping(PREFIX + "/{entityNo}")
	@ApiOperation(value = "删除款箱基础信息", notes = "删除款箱基础信息")
	@ApiImplicitParam(name = "entityNo", value = "物品编号", example = "01001", required = true, paramType = "path")
	DTO delEntityInfoByEntityNo(@PathVariable("entityNo") String entityNo);

	/**
	 * 批量导入款箱信息
	 */
	@PostMapping(PREFIX+ "/import")
	@ApiOperation(value = "批量导入款箱信息", notes = "批量导入款箱信息")
	DTO importEntity(@RequestParam(value = "file") MultipartFile file);
}
