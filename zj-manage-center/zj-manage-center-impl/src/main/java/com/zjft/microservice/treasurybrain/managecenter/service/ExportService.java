package com.zjft.microservice.treasurybrain.managecenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 崔耀中
 * @since 2020-01-15
 */
public interface ExportService {

	/**
	 * @Description 导入车辆信息模版下载
	 * @Param carNo
	 */
	DTO exportTemplatesExcel(HttpServletRequest request, HttpServletResponse response, String fileName);

}
