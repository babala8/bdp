package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.dto.ChartDTO;
import com.zjft.microservice.treasurybrain.datainsight.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 杨光
 */
@Slf4j
@RestController
public class ChartResourceImpl implements ChartResource {

	private final ChartService chartService;

	@Autowired
	public ChartResourceImpl(ChartService chartService) {
		this.chartService = chartService;
	}


	@Override
	public PageDTO<ChartDTO> qryChart(Map<String, Object> params) {
		PageDTO<ChartDTO> dto = new PageDTO<>();
		try {
			String modelTypeQuery = StringUtil.parseString(params.get("chartType_Query"));
			String modelCreatorQuery = StringUtil.parseString(params.get("chartCreator_Query"));
			String modelNameQuery = StringUtil.parseString(params.get("chartName_Query"));
			Integer chartSubjectQuery = StringUtil.objectToInt(params.get("chartSubject_Query"));
			int curPage = StringUtil.objectToInt(params.get("curPage"));
			int pageSize = StringUtil.objectToInt(params.get("pageSize"));
			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 20;
			}

			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("chartType_Query", modelTypeQuery);
			paramsMap.put("chartCreator_Query", modelCreatorQuery);
			paramsMap.put("chartName_Query", modelNameQuery);
			paramsMap.put("chartSubject_Query", chartSubjectQuery);
			paramsMap.put("pageSize", pageSize);
			paramsMap.put("curPage", curPage);

			Map<String, Object> aMap = chartService.qryCharts(paramsMap);

			@SuppressWarnings("unchecked")
			List<ChartDTO> retList = (List<ChartDTO>) aMap.get("chartList");
			dto.setCurPage(StringUtil.ch2Int(String.valueOf(aMap.get("curPage"))));
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
			dto.setPageSize(pageSize);
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询组件信息失败 ", e);
			dto.setRetException("查询组件信息失败");
		}
		return dto;
	}

	@Override
	public ListDTO queryModelCreators() {
		ListDTO dto = new ListDTO(RetCodeEnum.SUCCEED);
		try {
			dto = chartService.queryChartCreators();
		} catch (Exception e) {
			log.error("查询组件失败", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}


	@Override
	public DTO delChart(String id) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			chartService.delChart(id);
			dto.setResult(RetCodeEnum.SUCCEED);

		} catch (Exception e) {
			log.error("删除组件失败 ", e);
			dto.setRetException("删除" + id + "失败");
		}
		return dto;
	}

	@Override
	public DTO modModel(Map<String, Object> params) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto = chartService.modChartsModel(params);
		} catch (Exception e) {
			log.error("保存失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ObjectDTO getSelfDataById(String id) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			Map<String, Object> retMap = chartService.getChartById(id);

			dto.setResult(RetCodeEnum.SUCCEED);
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}
}
