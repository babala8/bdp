package com.zjft.microservice.treasurybrain.productcenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/************************************************************************
 * http://10.34.12.164:3000/#g=1&p=%E4%BA%A7%E5%93%81%E7%AE%A1%E7%90%86 *
 *************************************************************************/
@Api(value = "产品中心：服务基本信息", tags = "产品中心：服务基本信息")
public interface ServiceCenterResource {

	String PREFIX = "${product-center:}/v2/serviceInfo";

	@GetMapping(PREFIX + "/{serviceNo}")
	@ApiOperation(value = "查询服务信息详情", notes = "查询服务信息详情")
	@ApiImplicitParam(name = "serviceNo", value = "服务编号", required = true, paramType = "path")
	ServiceDetailDTO qryProductDetail(@PathVariable("serviceNo") String serviceNo);

//	@GetMapping(PREFIX + "/detail/{serviceNo}")
//	@ApiOperation(value = "查询产品信息详情", notes = "查询产品信息详情")
//	@ApiImplicitParam(name = "serviceNo", value = "产品编号", required = true, paramType = "path")
//	SelfServiceDetailDTO qryProductDetails(@PathVariable("serviceNo") String serviceNo);

	@GetMapping(PREFIX+"/serviceForUse")
	@ApiOperation(value = "查询个性可用服务", notes = "查询个性可用服务")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "curPage", value = "当前页面", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query")
	})
	PageDTO<ServiceDTO> qryProductForUse(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询服务信息", notes = "分页查询服务信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "serviceNo", value = "服务编号", paramType = "query"),
			@ApiImplicitParam(name = "serviceName", value = "服务名称", paramType = "query"),
			@ApiImplicitParam(name = "customerType", value = "服务对象", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<ServiceDTO> qryProductInfoByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

//	@PostMapping(PREFIX)
//	@ApiOperation(value = "增加产品信息", notes = "增加产品信息")
//	DTO addProductInfo(@RequestBody ServiceDTO productDTO);

	@GetMapping(PREFIX + "/customer")
	@ApiOperation(value = "查询服务对象类型", notes = "查询服务对象类型")
	ListDTO<CustomerTypeDTO> qryCustomerType();

	@PostMapping(PREFIX+"/product")
	@ApiOperation(value = "增加服务流转物品信息", notes = "增加服务流转物品信息")
	DTO addProductGoodsInfo(@RequestBody List<ServiceProductDTO> serviceProductDTOS);

	@GetMapping(PREFIX + "/modules")
	@ApiOperation(value = "查询模块信息", notes = "查询模块信息")
	ListDTO<ServiceModuleDTO> qryModules();

	@GetMapping(PREFIX + "/qryOperate")
	@ApiOperation(value = "查询操作信息", notes = "查询操作信息")
	@ApiImplicitParam(name = "operateType", value = "操作类型", paramType = "query")
	ListDTO<ServiceStatusExpandDTO> qryProductStatusExpand(String operateType);

	@GetMapping(PREFIX + "/serviceStatus/{serviceNo}")
	@ApiOperation(value = "查询服务状态", notes = "查询服务状态")
	@ApiImplicitParam(name = "serviceNo", value = "服务编号",example = "1" , required = true, paramType = "path")
	ListDTO<ServiceStatusDTO> qryServiceStatus(@PathVariable("serviceNo") String serviceNo);

	@PostMapping(PREFIX+"/status")
	@ApiOperation(value = "增加服务状态节点信息", notes = "增加服务状态节点信息")
	DTO addProductStatusInfo(@RequestBody List<ServiceStatusDTO> serviceStatusDTOS);

//	@PutMapping(PREFIX+"/status")
//	@ApiOperation(value = "修改产品状态", notes = "修改产品状态信息")
//	DTO modProductStatus(@RequestBody ServiceDTO productDTO);

//	@PostMapping(PREFIX+"/statusConvert")
//	@ApiOperation(value = "增加产品状态节点信息", notes = "增加产品状态节点信息")
//	DTO addProductStatusConverInfo(@RequestBody ServiceStatusConvertDTO productStatusConvertDTO);

}
