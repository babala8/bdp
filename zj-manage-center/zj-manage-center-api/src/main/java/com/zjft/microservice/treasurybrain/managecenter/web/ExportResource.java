package com.zjft.microservice.treasurybrain.managecenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 崔耀中
 * @since 2020/01/15
 */
@Api(tags = {"综合管理：导出管理"})
public interface ExportResource {

	String PREFIX = "${manage-center:}/v2/export";

	/**
	 * @Description 批量导入模版下载
	 * @Param carNo
	 */
	@PostMapping(PREFIX + "/exportTemplatesExcel")
	@ApiOperation(value = "批量导入模版下载", notes = "批量导入模版下载")
	DTO exportTemplatesExcel(@ApiIgnore HttpServletRequest request,
							 @ApiIgnore HttpServletResponse response,
							 @RequestParam(value = "filename") String filename);


}
