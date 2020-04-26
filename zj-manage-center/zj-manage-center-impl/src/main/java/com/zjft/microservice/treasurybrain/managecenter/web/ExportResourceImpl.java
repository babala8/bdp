package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.managecenter.service.CarService;
import com.zjft.microservice.treasurybrain.managecenter.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 崔耀中
 * @since 2020-01-15
 */
@Slf4j
@RestController
public class ExportResourceImpl implements ExportResource {

	@Resource
	private ExportService exportService;

	/**
	 * 批量导入模版下载
	 *
	 * @param
	 * @return 结果
	 */
	@Override
	public DTO exportTemplatesExcel(HttpServletRequest request, HttpServletResponse response, String fileName) {

		try {
			return exportService.exportTemplatesExcel(request, response,fileName);
		} catch (Exception e) {
			log.error("发生导出任务单异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}

	}

}
