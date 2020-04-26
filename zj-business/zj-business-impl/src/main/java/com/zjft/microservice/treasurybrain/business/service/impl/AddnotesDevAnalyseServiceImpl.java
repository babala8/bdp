package com.zjft.microservice.treasurybrain.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.accountscenter.web_inner.BiztxlogInitInnerResource;
import com.zjft.microservice.treasurybrain.business.service.AddnotesDevAnalyseService;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class AddnotesDevAnalyseServiceImpl implements AddnotesDevAnalyseService {
	
	private final static String DEV_BASE_INFO = "devBaseInfo";
	private final static String DEV_HISTORY_BIZTXLOG_AVG7 = "devHistoryBiztxlogAvg7";
	private final static String DEV_HISTORY_BIZTXLOG_AVG30 = "devHistoryBiztxlogAvg30";
	private final static String DEV_HISTORY_BIZTXLOG_AVG90 = "devHistoryBiztxlogAvg90";
	private final static String FOLD_DEV_HISTORY_BIZTXLOG = "foldDevHistoryBiztxlog";
	private final static String ADDNOTES_DATAS_SIXDAYS = "addnotesDatasSixDays";
	private final static String QUADRANT_DEV_FLOWAVGS = "quadrantDevFlowAvgs";
	private final static String QUADRANT_TABLES = "quadrantTables";

	@Resource
	private BiztxlogInitInnerResource biztxlogInitInnerResource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getAddnotesDevAnalyse(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg",  RetCodeEnum.FAIL.getTip());

		try {
			String devNo = StringUtil.parseString(paramMap.get("devNo"));
			String addnotesDate = StringUtil.parseString(paramMap.get("addnotesDate"));
			int useDays = StringUtil.objectToInt(paramMap.get("useDays"));
			int addnotesAmount = StringUtil.objectToInt(paramMap.get("addnotesAmount"));

			String maxDate = CalendarUtil.getSysTimeYMD();
			List<Map<String, Object>> list = biztxlogInitInnerResource.getMaxDateOfDev(devNo);
			if (list != null && list.size() > 0 && list.get(0) != null) {
				maxDate = StringUtil.parseString(list.get(0).get("MAXDATE"));
			}
			String maxDate1 = maxDate.replaceAll("-", "");
			int maxDate2 = Integer.parseInt(maxDate1);
			
			String addnotesDate1 = addnotesDate.replaceAll("-", "");
			int addnotesDate2 = Integer.parseInt(addnotesDate1);
			
			String currentDate = maxDate;
			if(maxDate2 > addnotesDate2) {
				currentDate = addnotesDate;
			}
			
			//加钞设备基本信息
			JSONObject devBaseInfo = new JSONObject();
			devBaseInfo.put("devNo", devNo);
			devBaseInfo.put("addnotesDate", addnotesDate);
			devBaseInfo.put("useDays", useDays);
			devBaseInfo.put("addnotesAmount", addnotesAmount);
            
            //历史数据参考信息
			JSONObject devHistoryBiztxlogAvg7 = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedDate(currentDate, -7), currentDate);//7天数据
            JSONObject devHistoryBiztxlogAvg30 = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedDate(currentDate, -30), currentDate);//30天数据
            JSONObject devHistoryBiztxlogAvg90 = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedDate(currentDate, -90), currentDate);//90天数据
            
            //折线图显示
            JSONArray devHistoryBiztxlogTwoYear = getHistoryBiztxlogInfo(devNo, CalendarUtil.getFixedDate(CalendarUtil.getFixedMonth(currentDate, -24), -20), CalendarUtil.getFixedDate(CalendarUtil.getFixedMonth(currentDate, -24), 10));//1年同期20天
            JSONArray devHistoryBiztxlogOneYear = getHistoryBiztxlogInfo(devNo, CalendarUtil.getFixedDate(CalendarUtil.getFixedMonth(currentDate, -12), -20), CalendarUtil.getFixedDate(CalendarUtil.getFixedMonth(currentDate, -12), 10));//1年同期20天
            JSONArray devHistoryBiztxlog = getHistoryBiztxlogInfo(devNo, CalendarUtil.getFixedDate(currentDate, -20), CalendarUtil.getFixedDate(currentDate, 10));//同期20天
            JSONArray foldDevHistoryBiztxlog = new JSONArray();
            
            for(int i = 0;i <= 30; i ++) {
            	JSONArray jsonArray = new JSONArray();
            	String historyDate = CalendarUtil.getFixedDate(CalendarUtil.getFixedDate(currentDate, -20), i).substring(5, 10);
            	jsonArray.add(historyDate);
            	boolean flag1 = false;
            	for(int j = 0;j < devHistoryBiztxlogTwoYear.size();j++) {
            		if(devHistoryBiztxlogTwoYear.getJSONObject(j).getString("trandate").substring(5, 10).equals(historyDate)) {
            			jsonArray.add(devHistoryBiztxlogTwoYear.getJSONObject(j).getDouble("flow"));
            			flag1 = true;
            		}
            	}
            	if(!flag1) {
            		jsonArray.add(null);
            	}
            	
            	boolean flag2 = false;
            	for(int k = 0;k < devHistoryBiztxlogOneYear.size();k++) {
            		if(devHistoryBiztxlogOneYear.getJSONObject(k).getString("trandate").substring(5, 10).equals(historyDate)) {
            			jsonArray.add(devHistoryBiztxlogOneYear.getJSONObject(k).getDouble("flow"));
            			flag2 = true;
            		}
            	}
            	if(!flag2) {
            		jsonArray.add(null);
            	}
            	
            	boolean flag3 = false;
            	for(int e = 0;e < devHistoryBiztxlog.size();e++) {
            		if(devHistoryBiztxlog.getJSONObject(e).getString("trandate").substring(5, 10).equals(historyDate)) {
            			jsonArray.add(devHistoryBiztxlog.getJSONObject(e).getDouble("flow"));
            			flag3 = true;
            		}
            	}
            	if(!flag3) {
            		jsonArray.add(null);
            	}
            	foldDevHistoryBiztxlog.add(jsonArray);
            }
            
            //象限图示
            JSONArray quadrantDevFlowAvgs = new JSONArray();
            JSONObject devForecastFlowAvgFourDay = getForecastFlow(devNo, addnotesDate, CalendarUtil.getFixedDate(addnotesDate, 3));//4天本周期预测
            JSONObject devForecastFlowAvgSevenDay = getForecastFlow(devNo, addnotesDate, CalendarUtil.getFixedDate(addnotesDate, 6));//7天本周期预测
            JSONObject devForecastFlowAvgNineDay = getForecastFlow(devNo, addnotesDate, CalendarUtil.getFixedDate(addnotesDate, 9));//10天本周期预测
            
            JSONObject devFlowAvgFourDayLast = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedDate(currentDate, -4), CalendarUtil.getFixedDate(currentDate, -1));//4天上周期实际
            JSONObject devFlowAvgSevenDayLast = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedDate(currentDate, -7), CalendarUtil.getFixedDate(currentDate, -1));//7天上周期实际
            JSONObject devFlowAvgNineDayLast = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedDate(currentDate, -10), CalendarUtil.getFixedDate(currentDate, -1));//10天上周期实际
            
            JSONObject devFlowHistoryAvgFourDay = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedMonth(addnotesDate, -12), CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(addnotesDate, 3), -12));//4天历史同周期实际
            JSONObject devFlowHistoryAvgSevenDay = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedMonth(addnotesDate, -12), CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(addnotesDate, 6), -12));//7天历史同周期实际
            JSONObject devFlowHistoryAvgNineDay = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedMonth(addnotesDate, -12), CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(addnotesDate, 9), -12));//10天历史同周期实际
            
            JSONObject devFlowHistoryAvgFourDayLast = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(currentDate, -4), -12), CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(currentDate, -1), -12));//4天历史上周期实际
            JSONObject devFlowHistoryAvgSevenDayLast = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(currentDate, -7), -12), CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(currentDate, -1), -12));//7天历史上周期实际
            JSONObject devFlowHistoryAvgNineDayLast = getHistoryBiztxlogAvg(devNo, CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(currentDate, -10), -12), CalendarUtil.getFixedMonth(CalendarUtil.getFixedDate(currentDate, -1), -12));//10天历史上周期实际
            
            JSONArray quadrantTables = new JSONArray();
            
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("describe", "4天日均取款-存款");
            jsonObject1.put("firstQuadrant", devForecastFlowAvgFourDay.getDouble("forecastFlow"));
            jsonObject1.put("secondQuadrant", devFlowAvgFourDayLast.getDouble("flowAvg"));
            jsonObject1.put("fouthQuadrant", devFlowHistoryAvgFourDay.getDouble("flowAvg"));
            jsonObject1.put("thirdQuadrant", devFlowHistoryAvgFourDayLast.getDouble("flowAvg"));
            quadrantTables.add(jsonObject1);
            
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("describe", "7天日均取款-存款");
            jsonObject2.put("firstQuadrant", devForecastFlowAvgSevenDay.getDouble("forecastFlow"));
            jsonObject2.put("secondQuadrant", devFlowAvgSevenDayLast.getDouble("flowAvg"));
            jsonObject2.put("fouthQuadrant", devFlowHistoryAvgSevenDay.getDouble("flowAvg"));
            jsonObject2.put("thirdQuadrant", devFlowHistoryAvgSevenDayLast.getDouble("flowAvg"));
            quadrantTables.add(jsonObject2);
            
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("describe", "10天日均取款-存款");
            jsonObject3.put("firstQuadrant", devForecastFlowAvgNineDay.getDouble("forecastFlow"));
            jsonObject3.put("secondQuadrant", devFlowAvgNineDayLast.getDouble("flowAvg"));
            jsonObject3.put("fouthQuadrant", devFlowHistoryAvgNineDay.getDouble("flowAvg"));
            jsonObject3.put("thirdQuadrant", devFlowHistoryAvgNineDayLast.getDouble("flowAvg"));
            quadrantTables.add(jsonObject3);
            
            quadrantDevFlowAvgs.add(devForecastFlowAvgFourDay.getDouble("forecastFlow"));
            quadrantDevFlowAvgs.add(devFlowAvgFourDayLast.getDouble("flowAvg"));
            quadrantDevFlowAvgs.add(devFlowHistoryAvgFourDay.getDouble("flowAvg"));
            quadrantDevFlowAvgs.add(devFlowHistoryAvgFourDayLast.getDouble("flowAvg"));
            
            quadrantDevFlowAvgs.add(devForecastFlowAvgSevenDay.getDouble("forecastFlow"));
            quadrantDevFlowAvgs.add(devFlowAvgSevenDayLast.getDouble("flowAvg"));
            quadrantDevFlowAvgs.add(devFlowHistoryAvgSevenDay.getDouble("flowAvg"));
            quadrantDevFlowAvgs.add(devFlowHistoryAvgSevenDayLast.getDouble("flowAvg"));
            
            quadrantDevFlowAvgs.add(devForecastFlowAvgNineDay.getDouble("forecastFlow"));
            quadrantDevFlowAvgs.add(devFlowAvgNineDayLast.getDouble("flowAvg"));
            quadrantDevFlowAvgs.add(devFlowHistoryAvgNineDay.getDouble("flowAvg"));
            quadrantDevFlowAvgs.add(devFlowHistoryAvgNineDayLast.getDouble("flowAvg"));
            
            JSONArray addnotesDatasSixDays = getHistoryAddnotesInfo(devNo, addnotesDate);
            JSONArray addnotesDatasSixDays1 = new JSONArray();
            for(int f = 0;f < addnotesDatasSixDays.size();f++) {
            	JSONObject jsonObject = new JSONObject();
            	jsonObject.put("addnotesDate", addnotesDatasSixDays.getJSONObject(f).getString("addnotesDate"));
	        	jsonObject.put("addnotesPlanAmount", addnotesDatasSixDays.getJSONObject(f).getIntValue("addnotesPlanAmount"));
	        	if(f == 0) {
	        		jsonObject.put("devBackAmount", 0);
	        	}else {
	        		jsonObject.put("devBackAmount", addnotesDatasSixDays.getJSONObject(f-1).getIntValue("devBackAmount"));
	        	}
	        	addnotesDatasSixDays1.add(jsonObject);
            }

			retMap.put(DEV_BASE_INFO, devBaseInfo);
			retMap.put(DEV_HISTORY_BIZTXLOG_AVG7, devHistoryBiztxlogAvg7);
			retMap.put(DEV_HISTORY_BIZTXLOG_AVG30, devHistoryBiztxlogAvg30);
			retMap.put(DEV_HISTORY_BIZTXLOG_AVG90, devHistoryBiztxlogAvg90);
			retMap.put(FOLD_DEV_HISTORY_BIZTXLOG, foldDevHistoryBiztxlog);
			retMap.put(ADDNOTES_DATAS_SIXDAYS, addnotesDatasSixDays1);
			retMap.put(QUADRANT_DEV_FLOWAVGS, quadrantDevFlowAvgs);
			retMap.put(QUADRANT_TABLES, quadrantTables);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", RetCodeEnum.SUCCEED.getTip());
		} catch (Exception e) {
			log.error("分析异常", e);
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "加钞设备信息分析异常!");
		}
		return retMap;
	}
	
	public JSONObject getForecastFlow(String devNo, String startDate, String endDate) {
		String selectForecastFlowSql = "select avg(t.draw_amount)/10000, avg(t.deposit_amount)/10000,avg(t.draw_amount-t.deposit_amount)/10000 forecastFlow " +
				"from forecast_cash_amount_info t where t.dev_no = ? and t.forecast_date >= ? and t.forecast_date <= ?";
        JSONObject JSONObject = new JSONObject();
		try {
			List<Map<String, Object>>  list = jdbcTemplate.queryForList(selectForecastFlowSql, new Object[]{devNo, startDate, endDate});
			if(list != null && list.size() > 0 && list.get(0) != null) {
				JSONObject.put("devNo", devNo);
				JSONObject.put("forecastFlow", Math.round(StringUtil.objectToDouble(list.get(0).get("forecastFlow"))));
	        }
		} catch (Exception e) {
			log.error("得到预测净流出异常", e);
			return JSONObject;
		}
        return JSONObject;
	}
	
	public JSONArray getHistoryBiztxlogInfo(String devNo, String startDate, String endDate) {
		String selectHistoryBiztxlogSql = "select t.termid,t.trandate,t.amount_cwd/10000,t.amount_cdm/10000,(t.amount_cwd-t.amount_cdm)/10000 flow " +
				"from biztxlog_init t where t.termid = ? and t.trandate >= ? and t.trandate <= ? order by t.trandate";
		JSONArray jsonArray = new JSONArray();
		try {
			List<Map<String, Object>>  list = jdbcTemplate.queryForList(selectHistoryBiztxlogSql, new Object[]{devNo, startDate, endDate});
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map tempMap = list.get(0);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("trandate", StringUtil.parseString(tempMap.get("trandate")));
					jsonObject.put("flow", StringUtil.objectToDouble(tempMap.get("flow")));
					jsonArray.add(jsonObject);
				}
	        }
		} catch (Exception e) {
			log.error("得到历史实际净流出异常", e);
			return jsonArray;
		}
		return jsonArray;
	}
	
	public JSONArray getHistoryBiztxlogInfoByDate(String devNo, String date) {
		String selectHistoryBiztxlogSql = "select t.termid,t.trandate,t.amount_cwd/10000,t.amount_cdm/10000,(t.amount_cwd-t.amount_cdm)/10000 flow from biztxlog_init t where t.termid = ? and t.trandate = ?";
		JSONArray jsonArray = new JSONArray();
		try {
			List<Map<String, Object>>  list = jdbcTemplate.queryForList(selectHistoryBiztxlogSql, new Object[]{devNo, date});
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map tempMap = list.get(0);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("trandate", StringUtil.parseString(tempMap.get("trandate")));
					jsonObject.put("flow", StringUtil.objectToDouble(tempMap.get("flow")));
					jsonArray.add(jsonObject);
				}
	        }
		} catch (Exception e) {
			log.error("得到历史实际净流出异常", e);
			return jsonArray;
		}
		return jsonArray;
	}
	
	public JSONObject getHistoryBiztxlogAvg(String devNo, String startDate, String endDate) {
		String selectHistoryBiztxlogAvgSql = "select avg(t.amount_cwd)/10000 cwdAvg,avg(t.amount_cdm)/10000 cdmAvg,avg(t.amount_cwd-t.amount_cdm)/10000 flowAvg from biztxlog_init t where t.termid = ? and t.trandate >= ? and t.trandate <= ?";
		JSONObject jsonObject = new JSONObject();
		DecimalFormat df = new DecimalFormat("0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);

		try {
			List<Map<String, Object>>  list = jdbcTemplate.queryForList(selectHistoryBiztxlogAvgSql, new Object[]{devNo, startDate, endDate});
			if(list != null && list.size() > 0 && list.get(0) != null) {
				Map tempMap = list.get(0);
				double cwdAvg = StringUtil.objectToDouble(tempMap.get("cwdAvg"));
				double cdmAvg = StringUtil.objectToDouble(tempMap.get("cdmAvg"));
				double flowAvg = StringUtil.objectToDouble(tempMap.get("flowAvg"));
				jsonObject.put("cwdAvg", df.format(cwdAvg));
				jsonObject.put("cdmAvg", df.format(cdmAvg));
				jsonObject.put("flowAvg", df.format(flowAvg));
			}
		} catch (Exception e) {
			log.error("得到历史实际日均净流出异常", e);
			return jsonObject;
		}
		return jsonObject;
	}
	
	public JSONArray getHistoryAddnotesInfo(String devNo, String currentDate) {
		String selectHistoryAddnotesSql = "select t.dev_no,t.cash_out_time,t.addnotes_plan_amount,t.dev_back_amount from addnotes_data t where t.dev_no = ? and t.cash_out_time <= ? order by t.cash_out_time desc";
		JSONArray jsonArray = new JSONArray();
		try {
			List<Map<String, Object>>  list = jdbcTemplate.queryForList(selectHistoryAddnotesSql, new Object[]{devNo, currentDate});
			if(list != null && list.size() > 0) {
				int count = 0;
				for (int i = 0; i < list.size(); i++) {
					count++;
					Map tempMap = list.get(0);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("trandate", StringUtil.parseString(tempMap.get("trandate")));
					jsonObject.put("flow", StringUtil.objectToDouble(tempMap.get("flow")));

					jsonObject.put("addnotesDate", StringUtil.parseString(tempMap.get("cash_out_time")).substring(0, 10));
					jsonObject.put("addnotesPlanAmount", StringUtil.objectToInt(tempMap.get("addnotes_plan_amount")));
					jsonObject.put("devBackAmount", StringUtil.objectToInt(tempMap.get("dev_back_amount")));
					jsonArray.add(jsonObject);

					if (count == 10) {
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("得到历史加钞数据异常", e);
			return jsonArray;
		}
		return jsonArray;
	}

}
