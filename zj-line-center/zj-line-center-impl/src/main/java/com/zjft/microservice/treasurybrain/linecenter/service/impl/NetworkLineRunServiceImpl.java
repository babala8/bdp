package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.web.ClrCenterResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.domain.*;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetworkLineRunInfoDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.NetworkLineRunConverter;
import com.zjft.microservice.treasurybrain.linecenter.repository.*;
import com.zjft.microservice.treasurybrain.linecenter.service.NetworkLineRunService;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class NetworkLineRunServiceImpl implements NetworkLineRunService {

	@Resource
	private LineNetworkLineRunInfoMapper lineNetworkLineRunInfoMapper;

	@Resource
	private LineNetworkLineRunDevDetailMapper lineNetworkLineRunDevDetailMapper;

	@Resource
	private LineNetworkLineMapper lineNetworkLineMapper;

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private LineWorkMapper lineWorkMapper;

	@Resource
	private LineTableMapper lineTableMapper;

	@Resource
	private LineScheduleMapper lineScheduleMapper;

	@Override
	public Map<String, Object> qryNetworkLineRunMap(String string) {
		log.info("------------[qryNetworkLineRunMap]NetworkLineRunService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String endMonth = StringUtil.parseString(params.get("endMonth"));
			String networkLineNo = StringUtil.parseString(params.get("networkLineNo"));
			String startMonth = StringUtil.parseString(params.get("startMonth"));

			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("endMonth", endMonth);
			paramMap.put("networkLineNo", networkLineNo);
			paramMap.put("startMonth", startMonth);
			paramMap.put("lineType", "1");

//			int totalRow = lineNetworkLineRunInfoMapper.qryTotalRowForMonth(paramMap);
			int totalRow = lineWorkMapper.qryTotalRowForMonth(paramMap);
			int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;

//			List<NetworkLineRunInfo> networkLineRunInfos = lineNetworkLineRunInfoMapper.qryNetworkLineRunMapForMonth(paramMap);
			List<LineWorkDO> networkLineRunInfos = lineWorkMapper.qryLineRunMapForMonth(paramMap);

			List<LineWorkTableDTO> retList = new ArrayList<LineWorkTableDTO>();
			for (LineWorkDO networkLineRunInfo : networkLineRunInfos) {
				String lineNo = networkLineRunInfo.getLineNo();
//				List<String> netAcountList = lineNetworkLineRunInfoMapper.selectNetAcountList(lineNo);
				List<String> netAcountList = lineWorkMapper.selectNetAcountList(lineNo);
				Integer netAcount = StringUtil.objectToInt(netAcountList.get(0));
				LineWorkTableDTO networkLineRunInfoDTO = NetworkLineRunConverter.INSTANCE.domain2dto(networkLineRunInfo);
				networkLineRunInfoDTO.setLineName(networkLineRunInfo.getLineName());
				networkLineRunInfoDTO.setCustomerCount(netAcount);
				retList.add(networkLineRunInfoDTO);
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询网点线路运行图成功！");
			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询网点线路运行图失败!");
			return retMap;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addNetworkLineRunMap(String createJsonString) throws ParseException {
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
				paramMap.put("lineType", 1);
//				List<NetworkLine> rowSet = lineNetworkLineMapper.rowSetList(paramMap);
				List<LineTableDO> rowSet = lineTableMapper.rowSetList(paramMap);
				if (rowSet.size() == 0) {
					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
					retMap.put("retMsg", "无法获取网点线路编号！");
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
//				List<NetworkLine> rowSet = lineNetworkLineMapper.rowSetList1(paramMap);
				List<LineTableDO> rowSet = lineTableMapper.rowSetList1(paramMap);
				if (rowSet.size() == 0) {
					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
					retMap.put("retMsg", "无法获取网点线路[" + lineNo + "]的信息");
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
			for (Map<String, Object> networkLineNoToDel : routeList) {
				log.debug("删除线路编号：[" + networkLineNoToDel.get("networkLineNo") + "]的线路运行信息");
				Map<String, Object> paramMap = new HashMap<String, Object>();

				paramMap.put("lineNo", networkLineNoToDel.get("lineNo"));
				paramMap.put("theYearMonth", theYearMonth);
//				lineNetworkLineRunDevDetailMapper.deleteNetworkLineDetail(paramMap);
				lineScheduleMapper.deleteNetworkLineDetail(paramMap);
//				lineNetworkLineRunInfoMapper.deleteNetworkLine(paramMap);
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
				// ===========该线路所有网点(顺延法加钞的)
				paramMap.put("lineNo", eachLine.get("lineNo"));

				int networkCount = 0;
				//此处对于网点营运时间不做要求，所有的网点每天都会进行加钞
				List<SysOrgDTO> sysOrgList = sysOrgInnerResource.qryNetworksByNetworkLineNo(clrCenterNo, eachLine.get("lineNo").toString());
				if (sysOrgList.size() == 0) {
//					throw new RuntimeException("查询" + eachLine.get("networkLineNo") + "线路顺延法下所有网点的结果为空");
//					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
//					retMap.put("retMsg", "查询" + eachLine.get("networkLineNo") + "线路顺延法下所有网点的结果为空");
//					return retMap;
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
					retMap.put("retCode", RetCodeEnum.FAIL.getCode());
					retMap.put("retMsg", "查询" + eachLine.get("networkLineNo") + "线路顺延法下所有网点的结果为空");
					return retMap;
				}
				for (SysOrgDTO sysOrg : sysOrgList) {
					networkCount++;
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
//					NetworkLineRunInfo networkLineRunInfo = new NetworkLineRunInfo();
					LineWorkDO networkLineRunInfo = new LineWorkDO();
					networkLineRunInfo.setLineNo(eachLine.get("lineNo").toString());
					networkLineRunInfo.setTheYearMonth(theYearMonth);
					networkLineRunInfo.setTheDay(String.format("%02d", day));
					sb.append(eachLine.get("lineNo")).append(year).append(String.format("%02d", month)).append(String.format("%02d", day)).toString();
					networkLineRunInfo.setLineWorkId(sb.toString());
					sb.setLength(0);

					networkLineRunInfo.setCustomerCount(networkCount);

					// 插入线路运行表
//					lineNetworkLineRunInfoMapper.insertSelective(networkLineRunInfo);
					lineWorkMapper.insertSelective(networkLineRunInfo);


					for (SysOrgDTO sysOrg : sysOrgList) {
//						NetworkLineRunOrgDetail networkLineRunOrgDetail = new NetworkLineRunOrgDetail();
						LineScheduleDO networkLineRunOrgDetail = new LineScheduleDO();
//						networkLineRunOrgDetail.setNetworkName(sysOrg.getName());
						networkLineRunOrgDetail.setLineWorkId(networkLineRunInfo.getLineWorkId());
						networkLineRunOrgDetail.setCustomerNo(sysOrg.getNo());
						networkLineRunOrgDetail.setCustomerName(sysOrg.getName());
						networkLineRunOrgDetail.setAddress(sysOrg.getAddress());
						networkLineRunOrgDetail.setTheYearMonth(theYearMonth);
						networkLineRunOrgDetail.setTheDay(String.format("%02d", day));
						//顺序号未进行判断
						networkLineRunOrgDetail.setSortNo(sortNo);
						networkLineRunOrgDetail.setCustomerType(3);//默认网点
						networkLineRunOrgDetail.setLineNo(eachLine.get("lineNo").toString());
//						lineNetworkLineRunDevDetailMapper.insertSelective(networkLineRunOrgDetail);
						lineScheduleMapper.insertSelective(networkLineRunOrgDetail);
						sortNo++;
					}

				}
				log.debug("创建线路" + eachLine.get("lineNo") + "的所有\"线路运行表/线路运行网点明细表\"对象成功");
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
	public Map<String, Object> modNetworkLineRunMap(LineWorkTableDTO lineWorkTableDTO) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

//		NetworkLineRunInfo networkLineRunInfo1 = lineNetworkLineRunInfoMapper.selectByPrimaryKey(lineWorkTableDTO.getNetworkLineRunNo());
		LineWorkDO networkLineRunInfo1 = lineWorkMapper.selectByPrimaryKey(lineWorkTableDTO.getLineWorkId());
		if (networkLineRunInfo1 == null) {
			retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			retMap.put("retMsg", "网点线路运行图修改异常!对象已不存在!");
			return retMap;
		}
//		NetworkLineRunInfo networkLineRunInfo = NetworkLineRunConverter.INSTANCE.domain2dto(lineWorkTableDTO);
		LineWorkDO networkLineRunInfo = NetworkLineRunConverter.INSTANCE.domain2dto(lineWorkTableDTO);
		LineScheduleDO lineScheduleDO = new LineScheduleDO();

		//对应修改中的删除操作
//		lineNetworkLineRunDevDetailMapper.deleteByNetworkLineRunNo(lineWorkTableDTO.getNetworkLineRunNo());
		lineScheduleMapper.deleteByLineWorkID(lineWorkTableDTO.getLineWorkId());

		for (int i = 0; i < lineWorkTableDTO.getDetailList().size(); i++) {
			lineScheduleDO.setSortNo(lineWorkTableDTO.getDetailList().get(i).getSortNo());
//			lineScheduleDO.setNetworkName(lineWorkTableDTO.getDetailList().get(i).getNetworkName());
			lineScheduleDO.setCustomerNo(lineWorkTableDTO.getDetailList().get(i).getCustomerNo());
			lineScheduleDO.setCustomerName(lineWorkTableDTO.getDetailList().get(i).getCustomerName());
			lineScheduleDO.setLineWorkId(lineWorkTableDTO.getLineWorkId());
			lineScheduleDO.setAddress(lineWorkTableDTO.getDetailList().get(i).getAddress());
			lineScheduleDO.setLineNo(lineWorkTableDTO.getLineNo());
			lineScheduleDO.setCustomerType(lineWorkTableDTO.getDetailList().get(i).getCustomerType());
//			lineNetworkLineRunDevDetailMapper.insertSelective(networkLineRunDetail);
			lineScheduleMapper.insertSelective(lineScheduleDO);
		}
//		lineNetworkLineRunInfoMapper.updateByPrimaryKeySelective(networkLineRunInfo);
		lineWorkMapper.updateByPrimaryKeySelective(networkLineRunInfo);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "修改网点线路运行图成功！");
		return retMap;
	}

	@Override
	public Map<String, Object> qrydetailNetworkLineRunMap(String string) {
		log.info("------------[detailNetworkLineRunMap]NetworkLineRunService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String networkLineNo = StringUtil.parseString(params.get("networkLineNo"));
			String theYearMonth = StringUtil.parseString(params.get("theYearMonth"));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("networkLineNo", networkLineNo);
			paramMap.put("theYearMonth", theYearMonth);

//			List<NetworkLineRunInfo> networkLineRunInfos = lineNetworkLineRunInfoMapper.qryNetworkLineRunMapDetail(paramMap);
			List<LineWorkDO> networkLineRunInfos = lineWorkMapper.qryNetworkLineRunMapDetail(paramMap);
			List<LineWorkTableDTO> retList = new ArrayList<LineWorkTableDTO>();
			for (LineWorkDO networkLineRunInfo : networkLineRunInfos) {
				LineWorkTableDTO networkLineRunInfoDTO = NetworkLineRunConverter.INSTANCE.domain2dto(networkLineRunInfo);
				networkLineRunInfoDTO.setLineName(networkLineRunInfo.getLineName());
				retList.add(networkLineRunInfoDTO);

			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询网点线路运行图详情成功！");
			retMap.put("retList", retList);
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.FAIL.getCode());
			log.error("[detailNetworkLineRunMap]异常", e);
			return retMap;
		}
	}
}



