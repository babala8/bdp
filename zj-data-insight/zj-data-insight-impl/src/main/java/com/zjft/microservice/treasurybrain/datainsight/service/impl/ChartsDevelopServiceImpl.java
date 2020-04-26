package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefCharts;
import com.zjft.microservice.treasurybrain.datainsight.repository.ChartSubjectMapper;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefChartsMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.ChartsDevelopService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ChartsDevelopServiceImpl implements ChartsDevelopService {

	private final SelfDefChartsMapper selfDefChartsMapper;
	private final ChartSubjectMapper chartSubjectMapper;
	private final SysUserResource sysUserResource;


	@Autowired
	public ChartsDevelopServiceImpl(SelfDefChartsMapper selfDefChartsMapper, ChartSubjectMapper chartSubjectMapper, SysUserResource sysUserResource) {
		this.selfDefChartsMapper = selfDefChartsMapper;
		this.chartSubjectMapper = chartSubjectMapper;
		this.sysUserResource = sysUserResource;
	}

	@Override
	public Map<String, Object> preview(String jsonParams) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			log.info("------------[ChartsDevelpService]preview-------------");
			JSONObject params = JSONUtil.parseJSONObject(jsonParams);

			String chartType = StringUtil.parseString(params.get("chartType"));
			String serviceURL = StringUtil.parseString(params.get("serviceURL"));
			String serviceMethod = StringUtil.parseString(params.get("serviceMethod"));

			String requestParam = StringUtil.parseString(params.get("requestParam"));
			Map<String,Object> reqParams = new HashMap<String,Object>();
			if(!requestParam.isEmpty()){
				JSONArray reqJsonArray = JSONUtil.parseJSONArray(requestParam);
				for(int i=0 ; i < reqJsonArray.size();i++){
					JSONObject reqJsObject = reqJsonArray.getJSONObject(i);
					String name = reqJsObject.getString("name");
					String value = reqJsObject.getString("value");
					//动态参数转换
					if("oneMonth".equals(value)){
						value=CalendarUtil.getFixedDateByOld(-31);
					}else if("threeMonth".equals(value)){
						value=CalendarUtil.getFixedDateByOld(-31*3);
					}else if("oneYear".equals(value)){
						value=CalendarUtil.getFixedDateByOld(-365);
					}
					reqParams.put(name,value);
				}
			}

			String jsonString = "";
			if ("GET".equalsIgnoreCase(serviceMethod)) {
				jsonString = HttpRequestUtil.restfulGet(serviceURL, reqParams);
			} else if ("POST".equalsIgnoreCase(serviceMethod)) {
				jsonString = HttpRequestUtil.restfulPost(serviceURL, reqParams);
			}

			JSONObject jsonObject = JSONUtil.parseJSONObject(jsonString);
			String retCode = jsonObject.getString("retCode");
			String retMsg = jsonObject.getString("retMsg");

			if (!RetCodeEnum.SUCCEED.getCode().equals(retCode)) {
				return retMap;
			}

			if (ChartTypeEnum.RANK_VIEW.getChartType().equals(chartType) ||
					ChartTypeEnum.INFO_VIEW.getChartType().equals(chartType) ||
					ChartTypeEnum.DETAIL_VIEW.getChartType().equals(chartType)) {
				retMap.put("data", jsonObject.getJSONArray("data"));
			} else {
				JSONArray measureRows = params.getJSONArray("measureRows");
				JSONArray dimensionRows = params.getJSONArray("dimensionRows");

				if (dimensionRows.size() < 1 && measureRows.size() < 1) {
					return retMap;
				}

				List<Map<String, Object>> measureList = new ArrayList<>();
				List<Map<String, Object>> dimensionList = new ArrayList<>();

				List<List<Object>> dimensionData = new ArrayList<>();
				for (int i = 0; i < dimensionRows.size(); i++) {
					dimensionData.add(new ArrayList<>());
				}

				List<List<Object>> measureData = new ArrayList<>();
				for (int i = 0; i < measureRows.size(); i++) {
					measureData.add(new ArrayList<>());
				}

				JSONArray dataArray = jsonObject.getJSONArray("data");

				if (dataArray != null) {
					// 遍历数据集的每一行数据
					for (int i = 0; i < dataArray.size(); i++) {
						JSONObject dataObject = dataArray.getJSONObject(i);

						// 组装维度数据 dimensionData
						for (int j = 0; j < dimensionRows.size(); j++) {
							String data = StringUtil.parseString(dataObject.get(dimensionRows.get(j)));
							dimensionData.get(j).add(data);
						}
						// 组装度量数据 measureData
						for (int j = 0; j < measureRows.size(); j++) {
							String data = StringUtil.parseString(dataObject.get(measureRows.get(j)));
							measureData.get(j).add(data);
						}
					}
				}

				// 组装 dimensionList
				for (int j = 0; j < dimensionRows.size(); j++) {
					Map<String, Object> element = new HashMap<>();
					element.put("name", StringUtil.parseString(dimensionRows.get(j)));
					element.put("data", dimensionData.get(j));
					dimensionList.add(element);
				}

				// 组装 measureList
				for (int j = 0; j < measureRows.size(); j++) {
					Map<String, Object> element = new HashMap<>();
					element.put("name", StringUtil.parseString(measureRows.get(j)));
					element.put("data", measureData.get(j));
					measureList.add(element);
				}
				retMap.put("legend", measureRows);
				retMap.put("measureList", measureList);
				retMap.put("dimensionList", dimensionList);
			}

		} catch (Exception e) {
			log.error("[preview] Fail:" + e.getMessage(), e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> queryChartSubjects() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			log.info("------------[ChartsDevelpService]queryChartSubjects-------------");
			List<ChartSubject> list = chartSubjectMapper.getChartSubjectsByOrgNo();
			retMap.put("retList", list);
		} catch (Exception e) {
			log.error("[queryChartSubjects] Fail:" + e.getMessage(), e);
		}
		return retMap;
	}

	@Override
	public void addChartsModel(Map<String, Object> params) {
		log.info("------------[addChartsModel]ChartsDevelpService-------------");
		try {
			JSONObject jsonObject = new JSONObject(params);
			JSONObject jsonComponentMsg = jsonObject.getJSONObject("componentMsg");
			SelfDefCharts selfDefCharts = new SelfDefCharts();
			selfDefCharts.setChartId(UUID.randomUUID().toString().replace("-",""));
			selfDefCharts.setChartDef(jsonObject.getJSONObject("modelMsg").toJSONString());
			selfDefCharts.setChartOption(jsonObject.getString("optionMsg"));

			selfDefCharts.setChartStatus(0);
			selfDefCharts.setChartName(jsonComponentMsg.getString("name"));
			selfDefCharts.setChartIcon(jsonComponentMsg.getString("icon"));
			selfDefCharts.setChartType(jsonComponentMsg.getString("type"));
			selfDefCharts.setChartDesc(jsonComponentMsg.getString("describe"));
			selfDefCharts.setChartSubject(jsonComponentMsg.getIntValue("subject"));

			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String username = authUser.getUsername();
			String orgNo = authUser.getOrgNo();

			selfDefCharts.setCreator(username);
			selfDefCharts.setCreatorOrgno(orgNo);
			selfDefCharts.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
			selfDefCharts.setLastestModOp(username);
			selfDefCharts.setLastestModTime(CalendarUtil.getSysTimeYMDHMS());

			selfDefChartsMapper.insert(selfDefCharts);

		} catch (Exception e) {
			log.error("[addChartsModel] Fail:", e);
		}
	}

	/**
	 * 查询当前用户权限下的图表
	 * @return Map
	 */
	@Override
	public Map<String, Object> getSelfDefCharts() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			log.info("------------[ChartsDevelpService]getSelfDefCharts-------------");
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String userOrgNo = authUser.getOrgNo();

			List<SelfDefCharts> retListTmp = selfDefChartsMapper.getSelfDefCharts(userOrgNo);
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			for (SelfDefCharts chart: retListTmp) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("id", chart.getChartId());
				tmp.put("name", chart.getChartName());
				tmp.put("chartDesc", chart.getChartDesc());
				tmp.put("icon", chart.getChartIcon());
				tmp.put("type", chart.getChartType());
				tmp.put("chart_option", chart.getChartOption());
				retList.add(tmp);
			}
			retMap.put("retList", retList);
		} catch (Exception e) {
			log.error("[getSelfDefCharts] Fail:" + e.getMessage(), e);
		}
		return retMap;
	}


	@Override
	public List<Object> qryServiceList() {
		List<Object> retList = new ArrayList<Object>();
		try {
			log.info("------------[ChartsDevelpService]restfulRequest-------------");
			String responseJson = HttpRequestUtil.restfulGet(CfgProperty.getProperty("bdpServiceListApi"), null);
			log.info("{}", responseJson);
			JSONObject jsonObj = JSONUtil.parseJSONObject(responseJson);
			String retCode = StringUtil.parseString(jsonObj.get("retCode"));
			String retMsg = StringUtil.parseString(jsonObj.get("retMsg"));

			retList = jsonObj.getJSONArray("retList");
			log.info("[queryMeasureRows]返回码=[" + retCode + "] 返回信息=[" + retMsg + "]");

		} catch (Exception e) {
			log.error("[qryServiceList] Fail:" + e);
		}
		return retList;
	}
}
