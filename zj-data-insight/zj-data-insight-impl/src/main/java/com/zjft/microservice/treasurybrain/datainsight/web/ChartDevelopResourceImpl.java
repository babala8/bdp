package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.datainsight.service.ChartsDevelopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author 杨光
 */
@Slf4j
@RestController
public class ChartDevelopResourceImpl implements ChartDevelopResource {

	private final ChartsDevelopService chartsDevelopService;

	@Autowired
	public ChartDevelopResourceImpl(ChartsDevelopService chartsDevelopService) {
		this.chartsDevelopService = chartsDevelopService;
	}

	@Override
	public ObjectDTO preview(String jsonParams) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL);
		try {
			Map<String, Object> retMap = chartsDevelopService.preview(jsonParams);
			dto.setResult(RetCodeEnum.SUCCEED);
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}


	@Override
	public DTO addChartsModel(Map<String, Object> params) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			chartsDevelopService.addChartsModel(params);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("保存失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}


	@Override
	public DTO qryChartsModel() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			Map<String, Object> retMap = chartsDevelopService.getSelfDefCharts();
			dto.setResult(RetCodeEnum.SUCCEED);
			List retList = (List) retMap.get("retList");
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public DTO qryChartSubjects() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			Map<String, Object> retMap = chartsDevelopService.queryChartSubjects();
			dto.setResult(RetCodeEnum.SUCCEED);
			dto.setRetList((List) retMap.get("retList"));
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public DTO qryServiceList() {
		ListDTO dto = new ListDTO(RetCodeEnum.SUCCEED);
		try {
			List<Object> retList = chartsDevelopService.qryServiceList();
			dto.setResult(RetCodeEnum.SUCCEED);
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("服务列表查询失败", e);
			return new DTO(RetCodeEnum.FAIL);
		}
		return dto;
	}
}