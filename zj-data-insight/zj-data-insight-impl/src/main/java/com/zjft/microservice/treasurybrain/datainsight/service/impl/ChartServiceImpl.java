package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefCharts;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefChartsMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.ChartService;
import com.zjft.microservice.treasurybrain.datainsight.service.ChartsDevelopService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChartServiceImpl implements ChartService {

	private final ChartsDevelopService chartsDevelopService;
	private final SelfDefChartsMapper selfDefChartsMapper;
	private final SysUserResource sysUserResource;

	@Autowired
	public ChartServiceImpl(ChartsDevelopService chartsDevelopService, SelfDefChartsMapper selfDefChartsMapper, SysUserResource sysUserResource) {
		this.chartsDevelopService = chartsDevelopService;
		this.selfDefChartsMapper = selfDefChartsMapper;
		this.sysUserResource = sysUserResource;
	}

	@Override
	public Map<String, Object> qryCharts(Map<String, Object> params) {
		log.info("------------[QryCharts]chartService-------------");

		try {
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));

			String chartCreator = StringUtil.parseString(params.get("chartCreator_Query"));
			String chartName = "%" + StringUtil.parseString(params.get("chartName_Query")) + "%";
			int chartSubject = StringUtil.objectToInt(params.get("chartSubject_Query"));

			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String userOrgNo = authUser.getOrgNo();

			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("chartCreator", StringUtil.isNullorEmpty(chartCreator) ? "%%" : chartCreator);
			paramsMap.put("chartName", chartName);
			paramsMap.put("userOrgNo", userOrgNo);
			if (chartSubject == -1) {
				paramsMap.put("chartSubject", " >= 0 ");
			} else {
				paramsMap.put("chartSubject", " =  " + chartSubject);
			}
			paramsMap.put("startRow", pageSize * (curPage - 1));
			paramsMap.put("endRow", pageSize * curPage);
			paramsMap.put("pageSize", pageSize);

			int totalRow = selfDefChartsMapper.qryTotalRowChart(paramsMap);
			int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

			List<SelfDefCharts> list = selfDefChartsMapper.qryCharts(paramsMap);

			List<Map<String, Object>> chartList = new ArrayList<Map<String, Object>>();
			for (SelfDefCharts selfDefCharts : list) {
				Map<String, Object> newChart = new HashMap<String, Object>();
				newChart.put("chartId", selfDefCharts.getChartId());
				newChart.put("name", selfDefCharts.getChartName());
				newChart.put("describe", selfDefCharts.getChartDesc());
				newChart.put("type", selfDefCharts.getChartType());
				newChart.put("icon", selfDefCharts.getChartIcon());
				newChart.put("creator", selfDefCharts.getCreator());
				newChart.put("createTime", CalendarUtil.getSysTimeYMDHMS());
				newChart.put("lastestModUser", selfDefCharts.getLastestModOp());
				newChart.put("lastestModTime", CalendarUtil.getSysTimeYMDHMS());
				newChart.put("subject", selfDefCharts.getChartSubject());
				newChart.put("chartDef", selfDefCharts.getChartDef());
				chartList.add(newChart);
			}
			Map<String, Object> aMap = new HashMap<>();
			aMap.put("chartList", chartList);
			aMap.put("totalRow", totalRow);
			aMap.put("totalPage", totalPage);
			aMap.put("curPage", curPage);
			return aMap;
		} catch (Exception e) {
			log.error("[qryCharts]异常", e);
			return null;
		}
	}

	@Override
	public ListDTO queryChartCreators() {
		log.info("------------[queryChartCreators]chartService-------------");
		ListDTO<String> dto = new ListDTO<>(RetCodeEnum.SUCCEED);
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String userOrgNo = authUser.getOrgNo();

			List<String> retList = selfDefChartsMapper.queryChartCreators(userOrgNo);
			dto.setRetList(retList);
			return dto;
		} catch (Exception e) {
			log.error("[queryChartTypes] error" + e.getMessage(), e);
			dto.setResult(RetCodeEnum.FAIL);
			return dto;
		}
	}

	@Override
	public DTO delChart(String id) {
		log.info("------------[delChart]chartService-------------");

		try {
			SelfDefCharts modelTable = selfDefChartsMapper.selectByPrimaryKey(id);
			if (modelTable == null) {
				return new DTO(RetCodeEnum.FAIL);
			}

			int i = selfDefChartsMapper.deleteByPrimaryKey(id);
			if (i != 1) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return new DTO(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("delChart Fail: ", e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public DTO modChartsModel(Map<String, Object> params) {
		log.info("------------[modChartsModel]chartService-------------");
		try {
			JSONObject jsonObject = new JSONObject(params);
			JSONObject jsonComponentMsg = jsonObject.getJSONObject("componentMsg");
			SelfDefCharts selfDefCharts = new SelfDefCharts();

			selfDefCharts.setChartStatus(0);
			selfDefCharts.setChartId(jsonComponentMsg.getString("chartId"));
			selfDefCharts.setChartName(jsonComponentMsg.getString("name"));
			selfDefCharts.setChartIcon(jsonComponentMsg.getString("icon"));
			selfDefCharts.setChartType(jsonComponentMsg.getString("type"));
			selfDefCharts.setChartSubject(jsonComponentMsg.getIntValue("subject"));
			selfDefCharts.setChartDesc(jsonComponentMsg.getString("describe"));

			UserDTO authUser = sysUserResource.getAuthUserInfo();

			selfDefCharts.setLastestModOp(authUser.getUsername());
			selfDefCharts.setLastestModTime(CalendarUtil.getSysTimeYMDHMS());

			selfDefCharts.setChartDef(jsonObject.getJSONObject("modelMsg").toJSONString());
			selfDefCharts.setChartOption(jsonObject.getString("optionMsg"));
			int i = selfDefChartsMapper.updateByPrimaryKey(selfDefCharts);
			if (i != 1) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return new DTO(RetCodeEnum.SUCCEED);

		} catch (Exception e) {
			log.error("[modChartsModel] Fail:" + e.getMessage(), e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public Map<String, Object> getChartById(String id) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			log.info("------------[getChartById]chartService-------------");
			SelfDefCharts selfDefCharts = selfDefChartsMapper.selectByPrimaryKey(id);
			if (selfDefCharts == null) {
				log.error("[getChartById] qry  null !");
				return null;
			}

			Map<String, Object> componentMsgMap = new HashMap<String, Object>();
			componentMsgMap.put("name", selfDefCharts.getChartName());
			componentMsgMap.put("subject", selfDefCharts.getChartSubject());
			componentMsgMap.put("icon", selfDefCharts.getChartIcon());
			componentMsgMap.put("type", selfDefCharts.getChartType());
			componentMsgMap.put("status", selfDefCharts.getChartStatus());
			componentMsgMap.put("describe", selfDefCharts.getChartDesc());
			componentMsgMap.put("creator", selfDefCharts.getCreator());
			componentMsgMap.put("creatorOrgNo", selfDefCharts.getCreatorOrgno());

			String def = selfDefCharts.getChartDef();
			if (def.length() > 0 && !"{}".equals(def)) {
				JSONObject chartDef = JSONUtil.parseJSONObject(def);
				chartDef.put("chartType", selfDefCharts.getChartType());
				Map<String, Object> dataMsgMap = chartsDevelopService.preview(chartDef.toJSONString());
				retMap.put("dataMsg", dataMsgMap);
			} else {
				retMap.put("dataMsg", def);
			}
			retMap.put("componentMsg", componentMsgMap);
			retMap.put("optionMsg", selfDefCharts.getChartOption());

			return retMap;
		} catch (Exception e) {
			log.error("[getChartById] Fail:", e);
			return null;
		}
	}
}
