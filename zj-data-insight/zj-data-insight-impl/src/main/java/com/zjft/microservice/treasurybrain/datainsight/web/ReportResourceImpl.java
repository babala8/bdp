package com.zjft.microservice.treasurybrain.datainsight.web;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.Base64Util;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.Const;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterDefinitionEntry;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.*;

@Slf4j
@RestController
public class ReportResourceImpl extends HttpServlet implements ReportResource {
	private final ReportService reportService;

	@Autowired
	public ReportResourceImpl(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	@PostConstruct
	public void init() {
		String path = FileUtil.getBasePath() + "/simple-jndi";
		Const.JNDI_DIRECTORY = path;
		System.setProperty("java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory");
		System.setProperty("org.osjava.sj.root", path);
		System.setProperty("org.osjava.sj.delimiter", "/");
		ClassicEngineBoot.getInstance().start();
	}

	@Override
	public ListDTO getReports() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			dto = reportService.queryReports();
		} catch (Exception e) {
			log.error("查询模板失败:", e);
		}
		return dto;
	}


	@Override
	public String qry(HttpServletRequest request, HttpServletResponse response, Map<String, Object> params) {
		try {
			String reportName = params.get("reportName").toString();
			log.info("[reportName:]" + reportName);

			String reportPath = "file:" + FileUtil.getBasePath() + "/reports/" + reportName;
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();


			List<Map<String, String>> reqList = (List) params.get("fieldList");
			if (reqList != null) {
				for (int i = 0; i < reqList.size(); i++) {
					String fieldName = reqList.get(i).get("fieldName");
					Object value = reqList.get(i).get("value");
					String dataType = reqList.get(i).get("dataType");
					if ("orgTree".equalsIgnoreCase(dataType)) {
						Map<String, Object> map = (Map<String, Object>) value;
						report.getParameterValues().put(fieldName, map.get("no"));
					} else {
						report.getParameterValues().put(fieldName, value);
					}
				}
			}

			// determine the output format and render accordingly
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			HtmlReportUtil.createStreamHTML(report, response.getOutputStream());
			return "查询成功！";
		} catch (Exception e) {
			log.error("查询报表失败:", e);
			return "查询报表失败！error:" + e.getMessage();
		}

	}

	@Override
	public ListDTO getProperties(Map<String, Object> params) {
		ListDTO<Map<String, Object>> dto = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			String reportName = params.get("reportName").toString();
			log.info("[reportName:]" + reportName);

			String reportPath = "file:" + FileUtil.getBasePath() + "/reports/" + reportName;
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();
			ParameterDefinitionEntry[] paramDef = report.getParameterDefinition().getParameterDefinitions();

			List<Map<String, Object>> retList = new ArrayList<>();
			for (ParameterDefinitionEntry pdEntry : paramDef) {
				String label = pdEntry.getParameterAttribute(
						"http://reporting.pentaho.org/namespaces/engine/parameter-attributes/core",
						"label", null);
				log.info("label:" + label);
				log.info("dataType:" + pdEntry.getValueType().toString().substring(pdEntry.getValueType().toString().lastIndexOf(".") + 1));
				log.info("fieldName:" + pdEntry.getName());

				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("label", label);
				retMap.put("dataType", pdEntry.getValueType().toString().substring(pdEntry.getValueType().toString().lastIndexOf(".") + 1));
				retMap.put("fieldName", pdEntry.getName());
				retMap.put("isRequire", pdEntry.isMandatory());
				retList.add(retMap);
			}
			dto.setRetList(retList);
			dto.setResult(RetCodeEnum.SUCCEED);

		} catch (Exception e) {
			log.error("查询配置失败:", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
			dto.setRetException("查询配置失败!");
		}
		return dto;
	}

	@Override
	public String export(HttpServletRequest request, HttpServletResponse response, Map<String, Object> params) {
		try {
			String reportName = params.get("reportName").toString();
			log.info("[reportName:]" + reportName);

			String reportPath = "file:" + FileUtil.getBasePath() + "/reports/" + reportName;
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();

			List<Map<String, String>> reqList = (List) params.get("fieldList");
			if (reqList != null) {
				for (int i = 0; i < reqList.size(); i++) {
					String fieldName = reqList.get(i).get("fieldName");
					Object value = reqList.get(i).get("value");
					String dataType = reqList.get(i).get("dataType");
					if ("orgTree".equalsIgnoreCase(dataType)) {
						Map<String, Object> map = (Map<String, Object>) value;
						report.getParameterValues().put(fieldName, map.get("no"));
					} else {
						report.getParameterValues().put(fieldName, value);
					}
				}
			}

			// determine the output format and render accordingly
			String outputFormat = StringUtil.parseString(params.get("outputFormat"));
			if ("html".equals(outputFormat)) {
				response.setContentType("text/html");
				response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
				response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".html");
				response.setHeader("Content-Transfer-Encoding", "binary");
				HtmlReportUtil.createStreamHTML(report, response.getOutputStream());
			} else if ("xls".equals(outputFormat)) {
				response.setContentType("application/x-xls");
				response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
				response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".xlsx");
				response.setHeader("Content-Transfer-Encoding", "binary");
				ExcelReportUtil.createXLSX(report, response.getOutputStream());
			} else if ("pdf".equals(outputFormat)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/pdf;charset=UTF-8");
				response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
				response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".pdf");
				response.setHeader("Content-Transfer-Encoding", "binary");
				PdfReportUtil.createPDF(report, response.getOutputStream());
			}
			return "生成报表成功！";
		} catch (Exception e) {
			log.error("生成报表失败:", e);
			return "生成报表失败！error:" + e.getMessage();
		}
	}

	@Override
	public PageDTO queryReportsByPage(Map<String, Object> params) {
		PageDTO dto = new PageDTO(RetCodeEnum.FAIL);
		try {
			String reportName = StringUtil.parseString(params.get("reportName"));

			int curPage = StringUtil.objectToInt(params.get("curPage"));
			int pageSize = StringUtil.objectToInt(params.get("pageSize"));

			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 20;
			}

			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("reportName", reportName);
			paramsMap.put("pageSize", pageSize);
			paramsMap.put("curPage", curPage);

			dto = reportService.queryReportsByPage(paramsMap);

		} catch (Exception e) {
			log.error("查询报表列表失败 ", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public DTO delReport(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto = reportService.delReport(no);
		} catch (Exception e) {
			log.error("删除报表失败 ", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public DTO addReport(Map<String, Object> params) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			JSONObject jsonObject = new JSONObject(params);
			String reportName = jsonObject.getString("reportName");
			String groupName = jsonObject.getString("groupName");

			List parameters = jsonObject.getJSONArray("parameters");
			//交互式报表 参数为null
			if (parameters != null && parameters.size() == 0) {
				parameters = null;
			} else if (((JSONObject) parameters.get(0)).isEmpty()) {
				parameters = null;
			}

			JSONObject reportFile = jsonObject.getJSONObject("reportFile");

			String fileName = reportFile.getString("name");
			// "data:;base64,"
			String fileDataString = reportFile.getString("data");
			int index = StringUtil.parseString(fileDataString).indexOf("base64,");
			String fileData = fileDataString.substring(index + 7);


			//上传模板文件
			byte[] connet = Base64Util.decode(fileData);
			Map<String, String> retMap = FileUtil.upLoadFile(connet, fileName, FileUtil.getBasePath() + "/reports/", fileName);

			if (!"00".equals(retMap.get("retCode"))) {
				dto.setRetCode(retMap.get("retCode"));
				dto.setRetMsg(retMap.get("retMsg"));
				return dto;
			}
			Map<String, Object> reqParams = new HashMap<>(5);
			reqParams.put("id", UUID.randomUUID().toString());
			reqParams.put("reportName", reportName);
			reqParams.put("groupName", groupName);
			reqParams.put("fileName", fileName);
			reqParams.put("parameters", parameters);

			reportService.addReport(reqParams);
			dto.setResult(RetCodeEnum.SUCCEED);

		} catch (Exception e) {
			log.error("添加报表失败 ", e);
			dto.setRetException("添加失败");
		}
		return dto;
	}

	@Override
	public ObjectDTO getFileDetailByFileName(Map<String, Object> params) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL);
		try {
			String reportName = params.get("reportName").toString();
			log.info("[reportName:]" + reportName);

			String reportPath = "file:" + FileUtil.getBasePath() + "/reports/" + reportName;
			ResourceManager manager = new ResourceManager();
			manager.registerDefaults();
			Resource res = manager.createDirectly(new URL(reportPath), MasterReport.class);
			MasterReport report = (MasterReport) res.getResource();

			DataFactory dataFactory = report.getDataFactory();
			String[] queryNames = dataFactory.getQueryNames();
			int sqlNum = queryNames.length;

			List<Map<String, Object>> sqlList = new ArrayList<>();
			for (String queryName : queryNames) {
				Map<String, Object> tmpMap = new HashMap<>(2);
				tmpMap.put("queryName", queryName);
				tmpMap.put("querySql", dataFactory.getQuery(queryName));
				sqlList.add(tmpMap);
			}

			Map<String, Object> retMap = new HashMap<>(2);
			retMap.put("sqlNum", sqlNum);
			retMap.put("sqlList", sqlList);

			dto.setElement(retMap);
			dto.setResult(RetCodeEnum.SUCCEED);

		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public ListDTO getGroups() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			dto = reportService.queryGroups();
		} catch (Exception e) {
			log.error("查询模板失败:", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}
}
