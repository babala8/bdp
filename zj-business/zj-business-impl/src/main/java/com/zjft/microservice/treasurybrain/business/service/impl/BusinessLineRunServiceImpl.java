package com.zjft.microservice.treasurybrain.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.business.repository.*;
import com.zjft.microservice.treasurybrain.business.service.BusinessLineRunService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevCountInnerResource;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LinRunInfoResource;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class BusinessLineRunServiceImpl implements BusinessLineRunService {

	@Resource
	private AddnoteLineMapper addnoteLineMapper;

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private AddnoteLineDetailInfoMapper addnoteLineDetailInfoMapper;

	@Resource
	private DevCountInnerResource devCountInnerResource;

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private LinRunInfoResource linRunInfoResource;

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addLineRunMap(String createJsonString) throws ParseException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);
		String lineNo = StringUtil.parseString(params.get("lineNo"));
		String theYearMonth = StringUtil.parseString(params.get("theYearMonth"));
		String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

		int year = Integer.parseInt(theYearMonth.substring(0, 4));
		int month = Integer.parseInt(theYearMonth.substring(5));
		// 如果金库编号为空，生成全部金库线路
		List<String> clrCenterNoList = new ArrayList<>();
		if (clrCenterNo == null || "".equals(clrCenterNo)) {
//			List<String> list = clrCenterTableMapper.clrCenterNoList();
			List<String> list = clrCenterInnerResource.clrCenterNoList();
			if (list.size() == 0) {
				retMap.put("retCode", RetCodeEnum.FAIL.getCode());
				retMap.put("retMsg", "无法获取金库编号！");
				return retMap;
			}
			for (int i = 0; i < list.size(); i++) {
				clrCenterNoList.add(list.get(i));
			}
		} else {
			clrCenterNoList.add(clrCenterNo);
		}


		for (String centerNo : clrCenterNoList) {
			List<Map<String, Object>> routeList = new ArrayList<>();
			// 如果线路号为空字符串，即未选择线路，生成所有线路的运行表
			if (lineNo == null || "".equals(lineNo)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("clrCenterNo", centerNo);
//				List<AddnoteLine> rowSet = addnoteLineMapper.rowSetList(paramMap);
				List<LineTableDTO> rowSet = addnoteLineInnerResource.rowSetList(paramMap);
				if (rowSet.size() == 0) {
					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
					retMap.put("retMsg", "无法获取线路编号！");
					return retMap;
				}

				for (int i = 0; i < rowSet.size(); i++) {
					Map<String, Object> routeInfo = new HashMap<String, Object>();
					routeInfo.put("lineNo", rowSet.get(i).getLineNo());
					routeInfo.put("addClrPeriod", rowSet.get(i).getAddClrPeriod());
					routeList.add(routeInfo);
				}

			} else {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("lineNo", lineNo);
				List<LineTableDTO> rowSet = addnoteLineInnerResource.rowSetList1(paramMap);
				if (rowSet.size() == 0) {
					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
					retMap.put("retMsg", "无法获取线路[" + lineNo + "]的信息");
					return retMap;
				}
				for (int i = 0; i < rowSet.size(); i++) {
					Map<String, Object> routeInfo = new HashMap<String, Object>();
					routeInfo.put("lineNo", rowSet.get(i).getLineNo());
					routeInfo.put("addClrPeriod", rowSet.get(i).getAddClrPeriod());
					routeList.add(routeInfo);
				}
			}


			// 删除所有旧的线路运行行表&&线路运行明细
			for (Map<String, Object> lineNoToDel : routeList) {
				log.debug("删除线路编号：[" + lineNoToDel.get("lineNo") + "]的线路运行信息");
				Map<String, Object> paramMap = new HashMap<String, Object>();

				paramMap.put("lineNo", lineNoToDel.get("lineNo"));
				paramMap.put("theYearMonth", theYearMonth);
				lineScheduleInnerResource.deleteLineDetail(paramMap);
				linRunInfoResource.deleteLine(paramMap);
			}

			// ==========计算本月总天数
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdFormat.parse(theYearMonth + "-01"));
			int totalDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			// 服务站顺延开始日期
			StringBuffer paramName = new StringBuffer();
			paramName.append("OutInSequenceStartDate_").append(centerNo);
//			Object rowSet = sysParamMapper.qryValueByName(paramName.toString());
			// TODO 熔断控制
			SysParamDTO paramDTO = sysParamResource.qrySysParamByName(paramName.toString());
			paramName.setLength(0);
			if (paramDTO == null) {
				retMap.put("retCode", RetCodeEnum.FAIL.getCode());
				retMap.put("retMsg", "请配置编号为[" + centerNo + "]的金库的顺延开始日期");
				return retMap;
			}
			String dataString = paramDTO.getParamValue();

			// 查询机构名称
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clrCenterNo", centerNo);
//			String orgName = clrCenterTableMapper.getOrgNameByClrNo(centerNo);
			String orgName = clrCenterInnerResource.getOrgNameByClrNo(centerNo);
			if (orgName == null) {
				retMap.put("retCode", RetCodeEnum.FAIL.getCode());
				retMap.put("retMsg", "获取编号为[" + centerNo + "]的金库所属机构的名称失败");
				return retMap;
			}

			// 对每一条线路
			for (Map<String, Object> eachLine : routeList) {
				// ===========该线路所有设备(顺延法加钞的)
				List<Map<String, Object>> dctVOList = new ArrayList<Map<String, Object>>();
				paramMap.put("lineNo", eachLine.get("lineNo"));
				List rowSet3 = devCountInnerResource.selectDctVOList(paramMap);
				if (rowSet3.size() == 0) {
					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
					retMap.put("retMsg", "查询" + eachLine.get("lineNo") + "线路顺延法下所有设备的DevClrTimeParam结果为空");
					return retMap;
				}
				for (int i = 0; i < rowSet3.size(); i++) {
					Object obj = rowSet3.get(i);
					Map entry = (Map) obj;
					String addClrPeriod = entry.get("ADD_CLR_PERIOD").toString();
					if (addClrPeriod == null) {
						retMap.put("retCode", RetCodeEnum.FAIL.getCode());
						retMap.put("retMsg", "设备" + entry.get("DEV_NO") + "的加钞周期为空！");
						return retMap;
					}
					Map<String, Object> dctVO = new HashMap<>();
					dctVO.put("addClrPeriod", addClrPeriod);
					dctVO.put("clrTimeInterval", entry.get("CLR_TIME_INTERVAL").toString());
					dctVO.put("devNo", entry.get("DEV_NO").toString());
					dctVO.put("arrivalTime", entry.get("ARRIVAL_TIME").toString());
					dctVO.put("clrDay", entry.get("CLR_DAY").toString());
					dctVO.put("orgNo", entry.get("ORG_NO").toString());
					dctVO.put("orgName", entry.get("NAME").toString());
					dctVOList.add(dctVO);
				}

				StringBuffer sb = new StringBuffer();
				for (int day = 1; day <= totalDay; day++) {
					// 这一天
					Calendar cal1 = Calendar.getInstance();
					cal1.set(year, month - 1, day, 0, 0, 0);
					// 服务站顺延开始日期
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(sdFormat.parse(dataString));

					// 线路运行表
//					LineRunInfo lineRunInfo = new LineRunInfo();
					Map<String,Object> lineRunInfoMap = new HashMap<>();
					lineRunInfoMap.put("lineNo",eachLine.get("lineNo").toString());
					lineRunInfoMap.put("theYearMonth",theYearMonth);
					lineRunInfoMap.put("theDay",String.format("%02d", day));
//					lineRunInfo.setLineNo(eachLine.get("lineNo").toString());
//					lineRunInfo.setTheYearMonth(theYearMonth);
//					lineRunInfo.setTheDay(String.format("%02d", day));
					sb.append(eachLine.get("lineNo")).append(year).append(String.format("%02d", month)).append(String.format("%02d", day)).toString();

					lineRunInfoMap.put("lineRunNo",sb.toString());
					lineRunInfoMap.put("startTimeAm","08:00");
					lineRunInfoMap.put("endTimeAm","11:30");
					lineRunInfoMap.put("startTimePm","13:00");
					lineRunInfoMap.put("endTimePm","16:30");
					lineRunInfoMap.put("returnUnitAm",orgName);
					lineRunInfoMap.put("returnUnitPm",orgName);
//					lineRunInfo.setLineRunNo(sb.toString());
//					lineRunInfo.setStartTimeAm("08:00");
//					lineRunInfo.setEndTimeAm("11:30");
//					lineRunInfo.setStartTimePm("13:00");
//					lineRunInfo.setEndTimePm("16:30");
//					lineRunInfo.setReturnUnitAm(orgName);
//					lineRunInfo.setReturnUnitPm(orgName);
					sb.setLength(0);

					// 查询任务次数
					Map<String, Object> paramMaps = new HashMap<String, Object>();
					paramMaps.put("lineNo", eachLine.get("lineNo").toString());
					paramMaps.put("theDay", String.format("%02d", day));
					List taskCount = addnoteLineDetailInfoMapper.selectTaskCount(paramMaps);
					Iterator it = taskCount.iterator();
					if (it.hasNext()) {
//						lineRunInfo.setTaskCount(StringUtil.objectToInt(taskCount.get(0)));
						lineRunInfoMap.put("taskCount",StringUtil.objectToInt(taskCount.get(0)));
					} else {
//						lineRunInfo.setTaskCount(2);
						lineRunInfoMap.put("taskCount",2);
					}

					int devCount = 0;
					for (Map<String, Object> dct : dctVOList) {
						// 两个时间相差天数mod“设备加钞周期”的余数(时分秒均为0)
						int daysBetween = new BigDecimal(getSubTime(cal1, cal2)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
						int addClrPeriod = Integer.parseInt(String.valueOf(dct.get("addClrPeriod")));
						int modValue = addClrPeriod == 0 ? -1 : Math.abs(daysBetween) % addClrPeriod + 1;
						if (modValue == StringUtil.objectToInt(dct.get("clrDay"))) {
							devCount++;
						}
					}
//					lineRunInfo.setDevCount(devCount);
					lineRunInfoMap.put("devCount",devCount);

					// 插入线路运行表
					linRunInfoResource.insertSelectiveByMap(lineRunInfoMap);

					for (Map<String, Object> dct : dctVOList) {
						// 两个时间相差天数mod“设备加钞周期”的余数(时分秒均为0)
						int daysBetween = new BigDecimal(getSubTime(cal1, cal2)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
						int addClrPeriod = Integer.parseInt(String.valueOf(dct.get("addClrPeriod")));
						int modValue = addClrPeriod == 0 ? -1 : Math.abs(daysBetween) % addClrPeriod + 1;

						if (modValue == StringUtil.objectToInt(dct.get("clrDay"))) {
							devCount++;

//							LineRunDevDetail lineRunDevDetail = new LineRunDevDetail();
							Map<String,Object> lineRunDevDetailMap = new HashMap<>();
							lineRunDevDetailMap.put("devNo",dct.get("devNo").toString());
//							lineRunDevDetail.setDevNo(dct.get("devNo").toString());
							sb.append(eachLine.get("lineNo")).append(year).append(String.format("%02d", month)).append(String.format("%02d", day)).toString();
//							lineRunDevDetail.setLineRunNo(sb.toString());
							lineRunDevDetailMap.put("lineRunNo",sb.toString());
							sb.setLength(0);

//							lineRunDevDetail.setOrgName(dct.get("orgName").toString());
//							lineRunDevDetail.setOrgNo(dct.get("orgNo").toString());
//							lineRunDevDetail.setClrTimeInterval(dct.get("clrTimeInterval").toString());
//							lineRunDevDetail.setArrivalTime(dct.get("arrivalTime").toString());


							lineRunDevDetailMap.put("orgName",dct.get("orgName").toString());
							lineRunDevDetailMap.put("orgNo",dct.get("orgNo").toString());
							lineRunDevDetailMap.put("clrTimeInterval",dct.get("clrTimeInterval").toString());
							lineRunDevDetailMap.put("arrivalTime",dct.get("arrivalTime").toString());

							lineScheduleInnerResource.insertSelectiveByMap(lineRunDevDetailMap);
						}
					}
				}
				log.debug("创建线路" + eachLine.get("lineNo") + "的所有\"线路运行表/线路运行设备明细表\"对象成功");
			}

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "所有线路的线路运行表创建/更新成功,新建" + theYearMonth + "线路运行表成功");
		}

		return retMap;
	}

	/**
	 * 获取两者的时间差
	 *
	 * @return double 单位：天
	 */
	public static Double getSubTime(Calendar cal1, Calendar cal2) {
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		Double retVal = (date1.getTime() - date2.getTime()) * 1.00 / (1000 * 60 * 60 * 24);
		return retVal;
	}


	@Override
	public Map<String, Object> genLineRunMap(String clrCenterNo, List<String> lineNoList) throws ParseException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			String currentYM = CalendarUtil.getSysTimeYM();
			String nextTheYM = CalendarUtil.getNextMonth();

			Map<String, Object> qryParamMap = new HashMap<String, Object>();
			qryParamMap.put("theYearMonth", currentYM);

			for (int i = 0; i < lineNoList.size(); i++) {
				String lineNo = lineNoList.get(i);
				qryParamMap.put("lineNo", lineNo);

				List<String> theYearMonthList = linRunInfoResource.selectTheYearMonthByLineNo(qryParamMap);
				if (theYearMonthList == null) {
					theYearMonthList = new ArrayList<>();
				}
				if (theYearMonthList.size() == 0) {
					theYearMonthList.add(currentYM);
					theYearMonthList.add(nextTheYM);
				}

				for (String theYearMonth : theYearMonthList) {
					Map<String, Object> paramMaps = new HashMap<String, Object>();
					paramMaps.put("clrCenterNo", clrCenterNo);
					paramMaps.put("lineNo", lineNo);
					paramMaps.put("theYearMonth", theYearMonth);
					addLineRunMap(JSONUtil.createJsonString(paramMaps));
				}
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "生成设备线路运行图成功！");
			return retMap;
		} catch (Exception e) {
			log.error("生成设备线路运行图失败", e);
			return retMap;
		}
	}

}



