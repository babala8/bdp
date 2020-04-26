package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevBaseInfoInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevCountInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.domain.*;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.CallCustomerLineRunMonthConverter;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.LineCallCustomerLineRunDetailConverter;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.LineRunConverter;
import com.zjft.microservice.treasurybrain.linecenter.repository.*;
import com.zjft.microservice.treasurybrain.linecenter.service.DevLineRunService;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;
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
public class DevLineRunServiceImpl implements DevLineRunService {

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private LineNetPointMatrixMapper lineNetPointMatrixMapper;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private DevCountInnerResource devCountInnerResource;

	@Resource
	private LineWorkMapper lineWorkMapper;

	@Resource
	private LineScheduleMapper lineScheduleMapper;

	@Resource
	private LineTableMapper lineTableMapper;

	@Resource
	private DevBaseInfoInnerResource devBaseInfoInnerResource;


	@Override
	public Map<String, Object> qryLineRunMap(String string) {
		log.info("------------[qryAddnotesRule]AddnotesRuleService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String endMonth = StringUtil.parseString(params.get("endMonth"));
			String lineNo = StringUtil.parseString(params.get("lineNo"));
			String startMonth = StringUtil.parseString(params.get("startMonth"));

			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("endMonth", endMonth);
			paramMap.put("lineNo", lineNo);
			paramMap.put("startMonth", startMonth);
			paramMap.put("lineType", "0");

//			int totalRow = lineRunInfoMapper.qryTotalRowForMonth(paramMap);
			int totalRow = lineWorkMapper.qryTotalRowForMonth(paramMap);
			int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;

//			List<LineRunInfo> lineRunInfos = lineRunInfoMapper.qryLineRunMapForMonth(paramMap);
			List<LineWorkDO> lineRunInfos = lineWorkMapper.qryLineRunMapForMonth(paramMap);

			List<LineWorkTableDTO> retList = new ArrayList<LineWorkTableDTO>();
			for (LineWorkDO lineRunInfo : lineRunInfos) {
				LineWorkTableDTO lineWorkTableDTO = LineRunConverter.INSTANCE.domain2dto(lineRunInfo);
				lineWorkTableDTO.setLineName(lineRunInfo.getLineName());
				retList.add(lineWorkTableDTO);
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询失败!");
			return retMap;
		}

	}

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
				paramMap.put("lineType",0);
//				List<LineTableDO> rowSet = lineAddnoteLineMapper.rowSetList(paramMap);
				List<LineTableDO> rowSet = lineTableMapper.rowSetList(paramMap);
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
//				List<LineTableDO> rowSet = lineAddnoteLineMapper.rowSetList1(paramMap);
				List<LineTableDO> rowSet = lineTableMapper.rowSetList1(paramMap);
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
//				lineRunDevDetailMapper.deleteLineDetail(paramMap);
				lineScheduleMapper.deleteLineDetail(paramMap);
//				lineRunInfoMapper.deleteLine(paramMap);
				lineWorkMapper.deleteLine(paramMap);
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
					int sortNo = 1;
					// 这一天
					Calendar cal1 = Calendar.getInstance();
					cal1.set(year, month - 1, day, 0, 0, 0);
					// 服务站顺延开始日期
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(sdFormat.parse(dataString));

					// 线路运行表
					LineWorkDO lineRunInfo = new LineWorkDO();
					lineRunInfo.setLineNo(eachLine.get("lineNo").toString());
					lineRunInfo.setTheYearMonth(theYearMonth);
					lineRunInfo.setTheDay(String.format("%02d", day));
					sb.append(eachLine.get("lineNo")).append(year).append(String.format("%02d", month)).append(String.format("%02d", day)).toString();
					lineRunInfo.setLineWorkId(sb.toString());
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
//					List taskCount = lineAddnoteLineDetailInfoMapper.selectTaskCount(paramMaps);
//					Iterator it = taskCount.iterator();
//					if (it.hasNext()) {
//						lineRunInfo.setTaskCount(StringUtil.objectToInt(taskCount.get(0)));
//					} else {
//						lineRunInfo.setTaskCount(2);
//					}

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
					lineRunInfo.setCustomerCount(devCount);

					// 插入线路运行表
//					lineRunInfoMapper.insertSelective(lineRunInfo);
					lineWorkMapper.insertSelective(lineRunInfo);

					for (Map<String, Object> dct : dctVOList) {
						// 两个时间相差天数mod“设备加钞周期”的余数(时分秒均为0)
						int daysBetween = new BigDecimal(getSubTime(cal1, cal2)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
						int addClrPeriod = Integer.parseInt(String.valueOf(dct.get("addClrPeriod")));
						int modValue = addClrPeriod == 0 ? -1 : Math.abs(daysBetween) % addClrPeriod + 1;

						if (modValue == StringUtil.objectToInt(dct.get("clrDay"))) {
							devCount++;
							LineScheduleDO lineRunDevDetailExpandDO = new LineScheduleDO();
							lineRunDevDetailExpandDO.setCustomerNo(dct.get("devNo").toString());
							sb.append(eachLine.get("lineNo")).append(year).append(String.format("%02d", month)).append(String.format("%02d", day)).toString();
							lineRunDevDetailExpandDO.setLineWorkId(sb.toString());
							sb.setLength(0);
							lineRunDevDetailExpandDO.setCustomerType(6);
							lineRunDevDetailExpandDO.setClrTimeInterval(dct.get("clrTimeInterval").toString());
							lineRunDevDetailExpandDO.setArrivalTime(dct.get("arrivalTime").toString());
							lineRunDevDetailExpandDO.setTheYearMonth(theYearMonth);
							lineRunDevDetailExpandDO.setTheDay(String.format("%02d", day));
							lineRunDevDetailExpandDO.setLineNo(eachLine.get("lineNo").toString());
							lineRunDevDetailExpandDO.setSortNo(sortNo);
							String devNo = dct.get("devNo").toString();
							DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectDetailByPrimaryKey(devNo);
							lineRunDevDetailExpandDO.setLat(devBaseInfo.getY().toString());
							lineRunDevDetailExpandDO.setLng(devBaseInfo.getX().toString());
							lineRunDevDetailExpandDO.setAddress(devBaseInfo.getAddress());
							lineScheduleMapper.insertSelective(lineRunDevDetailExpandDO);
							sortNo++;
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
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> modLineRunMap(LineWorkTableDTO lineWorkTableDTO) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

//		LineRunInfo lineRunInfo1 = lineRunInfoMapper.selectByPrimaryKey(lineWorkTableDTO.getLineWorkId());
		LineWorkDO lineRunInfo1 = lineWorkMapper.selectByPrimaryKey(lineWorkTableDTO.getLineWorkId());
		if (lineRunInfo1 == null) {
			retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			retMap.put("retMsg", "特殊加钞规则修改异常!对象已不存在!");
			return retMap;
		}
		LineWorkDO lineRunInfo = LineRunConverter.INSTANCE.domain2dto(lineWorkTableDTO);
		LineScheduleDO lineRunDevDetailExpandDO = new LineScheduleDO();

		//对应修改中的删除操作
//		lineRunDevDetailMapper.deleteByLineRunNo(lineWorkTableDTO.getLineWorkId());
		lineScheduleMapper.deleteByLineWorkID(lineWorkTableDTO.getLineWorkId());

		for (int i = 0; i < lineWorkTableDTO.getDetailList().size(); i++) {
			lineRunDevDetailExpandDO.setArrivalTime(lineWorkTableDTO.getDetailList().get(i).getArrivalTime());
			lineRunDevDetailExpandDO.setClrTimeInterval(lineWorkTableDTO.getDetailList().get(i).getClrTimeInterval());
//			lineRunDevDetailExpandDO.setOrgNo(lineWorkTableDTO.getDetailList().get(i).getOrgNo());
//			lineRunDevDetailExpandDO.setOrgName(lineWorkTableDTO.getDetailList().get(i).getOrgName());
			lineRunDevDetailExpandDO.setCustomerNo(lineWorkTableDTO.getDetailList().get(i).getCustomerNo());
			lineRunDevDetailExpandDO.setLineWorkId(lineWorkTableDTO.getLineWorkId());
//			lineRunDevDetailMapper.insertSelective(lineRunDevDetailExpandDO);
			lineScheduleMapper.insertSelective(lineRunDevDetailExpandDO);
		}
//		lineRunInfoMapper.updateByPrimaryKeySelective(lineRunInfo);
		lineWorkMapper.updateByPrimaryKeySelective(lineRunInfo);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "修改成功！");
		return retMap;
	}

	@Override
	public Map<String, Object> detailLineRunMap(String string) {
		log.info("------------[qryAddnotesRule]AddnotesRuleService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String lineNo = StringUtil.parseString(params.get("lineNo"));
			String theYearMonth = StringUtil.parseString(params.get("theYearMonth"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lineNo", lineNo);
			paramMap.put("theYearMonth", theYearMonth);

//			List<LineWorkDO> lineRunInfos = lineRunInfoMapper.qryLineRunMapDetail(paramMap);
			List<LineWorkDO> lineRunInfos = lineWorkMapper.qryLineRunMapDetail(paramMap);
			List<LineWorkTableDTO> retList = new ArrayList<LineWorkTableDTO>();
			for (LineWorkDO lineRunInfo : lineRunInfos) {
				LineWorkTableDTO lineWorkTableDTO = LineRunConverter.INSTANCE.domain2dto(lineRunInfo);
				lineWorkTableDTO.setLineName(lineRunInfo.getLineName());
				for(int i=0;i<lineRunInfo.getDetailList().size();i++){
					String no = lineRunInfo.getDetailList().get(i).getCustomerNo();
					DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectDetailByPrimaryKey(no);
					lineWorkTableDTO.getDetailList().get(i).setOrgNo(devBaseInfo.getOrgNo());
					lineWorkTableDTO.getDetailList().get(i).setOrgName(devBaseInfo.getSysOrg().getName());
				}
				retList.add(lineWorkTableDTO);

			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询详情成功！");
			retMap.put("retList", retList);
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.FAIL.getCode());
			log.error("[qryAddnotesRule]异常", e);
			return retMap;
		}
	}

	@Override
	public Map<String, Object> getLineRoute(List<String> devList) throws ParseException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> routeList = new ArrayList<>();
		try {
			if (devList.size() == 0) {
				retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
				retMap.put("retMsg", "设备号为空!");
			} else {
				int tactic = 11;
				String startDevNo = "";
				String endDevNo = "";
				String startOrgName = "";
				String address = "";
				double x = 0.0d;
				double y = 0.0d;
				int distance;
				int time;
				for (int i = 0; i <= devList.size(); i++) {
					//金库到第一台设备
					Map<String, Object> map = new HashMap<String, Object>();
					NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
					if (i == 0) {
						startDevNo = "00080000";
						endDevNo = devList.get(i);
						Map<String, String> startMap = sysOrgInnerResource.qryCenterByNo(startDevNo);
						address = startMap.get("ADDRESS");
						startOrgName = startMap.get("NAME");
						x = Double.valueOf(startMap.get("X"));
						y = Double.valueOf(startMap.get("Y"));
						//Map<String, String> devMap = lineRunInfoMapper.qryDevInfoByNo(endDevNo);
						//endPointNo = devMap.get("ORGNO");

						netPointMatrixKey.setType(1);
						//最后一台设备
					} else if (i == devList.size()) {
						startDevNo = devList.get(i - 1);
						endDevNo = "00080000";
						Map<String, String> devMap = sysOrgInnerResource.qryDevInfoByNo(startDevNo);
						//endPointNo = devMap.get("ORGNO");
						x = Double.valueOf(devMap.get("X"));
						y = Double.valueOf(devMap.get("Y"));
						startOrgName = devMap.get("NAME");
						Map<String, String> startMap = sysOrgInnerResource.qryCenterByNo(endDevNo);
						address = startMap.get("ADDRESS");

						netPointMatrixKey.setType(2);

					} else {
						startDevNo = devList.get(i - 1);
						endDevNo = devList.get(i);
						Map<String, String> devMap = sysOrgInnerResource.qryDevInfoByNo(startDevNo);
						//endPointNo = devMap.get("ORGNO");
						x = Double.valueOf(devMap.get("X"));
						y = Double.valueOf(devMap.get("Y"));
						address = devMap.get("ADDRESS");
						startOrgName = devMap.get("NAME");

						netPointMatrixKey.setType(0);

					}

					netPointMatrixKey.setStartPointNo(startDevNo);
					netPointMatrixKey.setEndPointNo(endDevNo);
					netPointMatrixKey.setTactic(tactic);
					NetPointMatrix netPointMatrix = lineNetPointMatrixMapper.selectByPrimaryKey(netPointMatrixKey);
					if (netPointMatrix == null) {
						retMap.put("retCode", RetCodeEnum.RESULT_EMPTY.getCode());
						retMap.put("retMsg", "设备号不存在!");
					}
					distance = netPointMatrix.getDistance();
					time = netPointMatrix.getTimeCost();

					map.put("startPointX", x);
					map.put("startPointY", y);
					map.put("startDevNo", startDevNo);
					map.put("address", address);
					map.put("startOrgName", startOrgName);
					map.put("endDevNo", endDevNo);
					map.put("distance", distance);
					map.put("time", time);
					routeList.add(map);
				}
				retMap.put("routeList", routeList);
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功!");
		} catch (Exception e) {
			log.error("查询线路失败", e);
			return retMap;
		}
		return retMap;
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
//		return lineRunInfoMapper.insertSelectiveByMap(paramMap);
		return lineWorkMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public int deleteLine(Map<String, Object> paramMap) {
//		return lineRunInfoMapper.deleteLine(paramMap);
		return lineWorkMapper.deleteLine(paramMap);
	}

	@Override
	public List<String> selectTheYearMonthByLineNo(Map<String, Object> paramMap) {
//		return lineRunInfoMapper.selectTheYearMonthByLineNo(paramMap);
		return lineWorkMapper.selectTheYearMonthByLineNo(paramMap);
	}

	@Override
	public int insertDetailSelectiveByMap(Map<String, Object> lineScheduleMap) {
//		return lineRunDevDetailMapper.insertSelectiveByMap(lineRunDevDetailMap);
		return lineScheduleMapper.insertSelectiveByMap(lineScheduleMap);
	}

	@Override
	public int deleteLineDetail(Map<String, Object> paramMap) {
//		return lineRunDevDetailMapper.deleteLineDetail(paramMap);
		return lineScheduleMapper.deleteLineDetail(paramMap);
	}

	@Override
	public LineScheduleDTO selectByMap(Map<String, Object> paramMap) {
		LineScheduleDO lineScheduleDO  = lineScheduleMapper.selectByMap(paramMap);
		LineScheduleDTO lineScheduleDTO = LineCallCustomerLineRunDetailConverter.INSTANCE.po2dto(lineScheduleDO);
		return lineScheduleDTO;
	}

}



