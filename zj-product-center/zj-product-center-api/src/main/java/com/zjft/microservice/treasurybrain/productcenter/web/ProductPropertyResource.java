package com.zjft.microservice.treasurybrain.productcenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyValueInfoDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/02
 */
@Api(value = "产品中心：产品属性信息", tags = "产品中心：产品属性信息")
public interface ProductPropertyResource {

	String PREFIX = "${product-center:}/v2/productProperty";

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加产品属性信息", notes = "增加产品属性信息")
	DTO addGoodsProperty(@RequestBody ProductPropertyDTO productPropertyDTO);

	/*@DeleteMapping(PREFIX + "/{propertyNo}")
	@ApiOperation(value = "删除商品属性信息", notes = "删除商品属性信息")
	@ApiImplicitParam(name = "propertyNo", value = "属性编号", required = true, paramType = "path")
	DTO delGoodsProperty(@PathVariable("propertyNo") String propertyNo);*/

	@GetMapping(PREFIX + "/propertyValue")
	@ApiOperation(value = "产品属性值查询", notes = "产品属性值查询")
	@ApiImplicitParam(name = "propertyNo", value = "属性编号", required = true, paramType = "query")
	ProductPropertyValueInfoDTO qryGoodsPropertyValue(@RequestParam("propertyNo") String propertyNo);

	@GetMapping(PREFIX + "/propertyTypeList")
	@ApiOperation(value = "产品属性类型查询", notes = "产品属性类型查询")
	ListDTO qryPropertyType();

	@GetMapping(PREFIX+"/product")
	@ApiOperation(value = "查询产品基础信息", notes = "查询产品基础信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productNo", value = "商品编号", paramType = "query"),
			@ApiImplicitParam(name = "productType", value = "所属分类", paramType = "query"),
			@ApiImplicitParam(name = "productName", value = "商品名称", paramType = "query")
	})
	ListDTO qryGoodsBaseByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX+"/product")
	@ApiOperation(value = "增加产品信息", notes = "增加产品信息")
	@ApiOperationSupport(ignoreParameters = {"productPropertyDetailDTO.productPropertyKeyValueDTOList.propertyNo"})
	DTO addGoodsBase(@RequestBody ProductPropertyDetailDTO productPropertyDetailDTO);

	@PutMapping(PREFIX+"/product")
	@ApiOperation(value = "修改产品信息", notes = "修改产品信息")
	DTO modGoodsBase(@RequestBody ProductPropertyDetailDTO productPropertyDetailDTO);

	@DeleteMapping(PREFIX + "/{productNo}")
	@ApiOperation(value = "删除产品信息", notes = "删除产品信息")
	@ApiImplicitParam(name = "productNo", value = "商品编号", required = true, paramType = "path")
	DTO delGoodsBase(@PathVariable("productNo") String productNo);

	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "产品基础信息详情查询", notes = "产品基础信息详情查询")
	@ApiImplicitParam(name = "productNo", value = "商品编号", required = true, paramType = "query")
	ProductDetailDTO qryGoodsBaseDetail(@RequestParam("productNo") String productNo);

	@GetMapping(PREFIX + "/upperInfoList")
	@ApiOperation(value = "产品所属信息查询", notes = "产品所属信息查询")
	ListDTO qryGoodsUpperInfo();
}
