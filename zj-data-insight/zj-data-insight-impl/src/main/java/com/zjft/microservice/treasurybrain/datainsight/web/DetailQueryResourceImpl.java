package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.dto.DetailQueryDTO;
import com.zjft.microservice.treasurybrain.datainsight.service.ChartsDevelopService;
import com.zjft.microservice.treasurybrain.datainsight.service.DetailQueryService;
import com.zjft.microservice.treasurybrain.datainsight.util.ReportExport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DetailQueryResourceImpl implements DetailQueryResource {

	private final DetailQueryService detailQueryService;

	private final ChartsDevelopService chartsDevelopService;

	@Autowired
	public DetailQueryResourceImpl(DetailQueryService detailQueryService, ChartsDevelopService chartsDevelopService) {
		this.detailQueryService = detailQueryService;
		this.chartsDevelopService = chartsDevelopService;
	}

	@Override
	public ObjectDTO qry(Map<String, Object> params) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL);
		try {
			String serviceURL = StringUtil.parseString(params.get("serviceURL"));
			String serviceMethod = StringUtil.parseString(params.get("serviceMethod"));

			Map<String, String> reqMap = new HashMap<String, String>();
			reqMap.put("serviceMethod", serviceMethod);
			reqMap.put("serviceURL", serviceURL);
			dto = detailQueryService.invokeService(reqMap);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	@Override
	public String export(HttpServletRequest request, HttpServletResponse response, Map<String, Object> params) {
		String retMsg = "";
		try {
			List<Map<String, Object>> returnParam = (List<Map<String, Object>>) params.get("returnParam");

			String id = StringUtil.parseString(params.get("id"));
			ObjectDTO dto = detailQueryService.qrySelfDefDetailById(id);
			if (!RetCodeEnum.SUCCEED.getCode().equalsIgnoreCase(dto.getRetCode())) {
				return "导出查询失败!";
			}
			Map<String, Object> element = (Map<String, Object>) dto.getElement();
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) element.get("data");

			List<String> firstRowList = new ArrayList<String>();
			List<String> colRowList = new ArrayList<String>();
			for (Map<String, Object> param : returnParam) {
				String desc = StringUtil.parseString(param.get("desc"));
				String name = StringUtil.parseString(param.get("name"));
				firstRowList.add(!"".equals(desc) ? desc : name);
				colRowList.add(name);
			}

			//初始化生成报表所需要的参数；
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sheet", "sheet1");
			parameters.put("firstRowList", firstRowList);
			parameters.put("colRowList", colRowList);

			DetailQueryDTO detailQueryDTO = ReportExport.fixReportMap(dataList, parameters);
			if (!RetCodeEnum.SUCCEED.getCode().equalsIgnoreCase(detailQueryDTO.getRetCode())) {
				return "导出文件生成失败！";
			}

			String fileSrc = detailQueryDTO.getXlsFile();
			// 下载后保存的文件名
			String fileName = request.getParameter("fileName") == null ? id+".xls" : request.getParameter("fileName").trim();

			FileUtil.downLoadResponseFile(request, response, fileName, fileSrc, true, false);
			log.info("=====================downloadFile==========================");
		} catch (Exception e) {
			log.error("导出失败", e);
			retMsg = "文件下载异常!";
		}
		return retMsg;
	}


	@Override
	public DTO saveReport(Map<String, Object> params) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		try {
			detailQueryService.addDetailQueryModel(params);
		} catch (Exception e) {
			log.error("保存失败", e);
			dto.setRetException("保存失败");
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ObjectDTO qrySelfDefDetail(String id) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "保存失败！");
		try {
			dto = detailQueryService.qrySelfDefDetailById(id);
		} catch (Exception e) {
			log.error("查询自定义明细查询模板失败", e);
			dto.setRetException("查询自定义明细查询模板失败");
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO getDefDetails() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			dto = detailQueryService.getDetails();

		} catch (Exception e) {
			log.error("查询模板失败:", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO getGroups() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			dto = detailQueryService.queryGroups();
		} catch (Exception e) {
			log.error("查询模板失败:", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO qryServiceList() {
		ListDTO<Object> dto = new ListDTO<>(RetCodeEnum.SUCCEED);
		try {
			List<Object> retList = chartsDevelopService.qryServiceList();
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("保存失败", e);
			dto.setRetException("保存失败");
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}
}
