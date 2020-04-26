package com.zjft.microservice.treasurybrain.linecenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.web.CallCustomerTimeResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.domain.CallCustomerLineRunMonthDO;
//import com.zjft.microservice.treasurybrain.linecenter.domain.LineCallCustomerLineRunDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineScheduleDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.AddCallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.CallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.CallCustomerLineRunMonthDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.CallCustomerLineRunConverter;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.CallCustomerLineRunMonthConverter;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.LineCallCustomerLineRunDetailConverter;
//import com.zjft.microservice.treasurybrain.linecenter.po.LineCallCustomerLineRunDetailPO;
import com.zjft.microservice.treasurybrain.linecenter.po.LineSchedulePO;
import com.zjft.microservice.treasurybrain.linecenter.po.LineWorkPO;
import com.zjft.microservice.treasurybrain.linecenter.repository.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 常 健
 * @since 2020/1/9
 */
@Slf4j
@ZjComponentResource(group = "onSiteCollection")
public class OnSiteCollectionComponent {

	@Resource
	private LineCallCustomerLineRunMapper lineCallCustomerLineRunMapper;

	@Resource
	private LineCallCustomerLineRunDetailMapper lineCallCustomerLineRunDetailMapper;

	@Resource
	private CallCustomerTimeResource callCustomerTimeResource;

	@Resource
	private LineNetworkLineRunInfoMapper lineNetworkLineRunInfoMapper;

	@Resource
	private LineWorkMapper lineWorkMapper;

	@Resource
	private LineScheduleMapper lineScheduleMapper;

	@Resource
	private LineTableMapper lineTableMapper;

 	/**
	 * 分页查询上门收款线路安排
	 */
	@ZjComponentMapping("qryOnSiteCollectionByPage")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryOnSiteCollectionByPage(HashMap<String, Object> paramMap, PageDTO<LineWorkTableDTO> returnDTO, String str) {
		//上门收款类型的线路
		paramMap.put("lineType", StatusEnum.LineType.CALL_CUSTOMER_LINE.getType());
		//分页参数
		int pageSize = PageUtil.transParam2Page(paramMap, returnDTO);
//		int totalRow = lineCallCustomerLineRunMapper.qryMonthTotalRow(paramMap);
		int totalRow = lineWorkMapper.qryMonthTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		//分页结果
//		List<LineWorkDO> callCustomerLineRunMonthDOS = lineCallCustomerLineRunMapper.qryMonthByPage(paramMap);
		List<LineWorkDO> callCustomerLineRunMonthDOS = lineWorkMapper.qryMonthByPage(paramMap);
		List<LineWorkTableDTO> callCustomerLineRunMonthDTOS = CallCustomerLineRunMonthConverter.INSTANCE.do2dto(callCustomerLineRunMonthDOS);
		//查询结果
		returnDTO.setTotalRow(totalRow);
		returnDTO.setPageSize(pageSize);
		returnDTO.setTotalPage(totalPage);
		returnDTO.setRetList(callCustomerLineRunMonthDTOS);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}


	/**
	 * 查询上门收款线路安排及详情
	 */
	@ZjComponentMapping("qryOnSiteCollectionDetail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String qryOnSiteCollectionDetail(HashMap<String, Object> paramMap, ListDTO<LineWorkTableDTO> returnDTO, String str) {
		//上门收款类型的线路
		paramMap.put("lineType", StatusEnum.LineType.CALL_CUSTOMER_LINE.getType());
//		List<LineCallCustomerLineRunDO> callCustomerLineRunDOS = lineCallCustomerLineRunMapper.qryDayCordAndDetails(paramMap);
		List<LineWorkDO> callCustomerLineRunDOS = lineWorkMapper.qryDayCordAndDetails(paramMap);
		List<LineWorkTableDTO> callCustomerLineRunDTOS = CallCustomerLineRunConverter.INSTANCE.do2dto(callCustomerLineRunDOS);
		returnDTO.setRetList(callCustomerLineRunDTOS);
		returnDTO.setRetCode("00");
		returnDTO.setRetMsg("查询成功！");
		return "ok";
	}


	/**
	 * 覆盖生成上门收款线路运行图参数校验
	 */
	@ZjComponentMapping("addAndCoverCheck")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String addAndCoverCheck(AddCallCustomerLineRunDTO requestDTO, DTO returnDTO, HashMap<String, Object> map) {
		String theYearMonth = requestDTO.getTheYearMonth();
		//年月
		int year = Integer.parseInt(theYearMonth.substring(0, 4));
		int month = Integer.parseInt(theYearMonth.substring(5));
		map.put("year", year);
		map.put("month", month);
		//生成月份不能再现在之前
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		if (year < today.get(Calendar.YEAR) || month < (today.get(Calendar.MONTH) + 1)) {
			log.error("不能生成本月之前的上门收款线路排班表");
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg("不能生成本月之前的上门收款线路排班表");
			return "fail";
		}
		return "ok";
	}


	/**
	 * 覆盖生成上门收款线路运行图
	 */
	@ZjComponentMapping("addAndCover")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String addAndCover(AddCallCustomerLineRunDTO requestDTO, DTO returnDTO, HashMap<String, Object> map1){
		String clrCenterNo = requestDTO.getClrCenterNo();
		String theYearMonth = requestDTO.getTheYearMonth();
		List<String> lineNos = requestDTO.getLineNos();
		int year = StringUtil.objectToInt(map1.get("year"));
		int month = StringUtil.objectToInt(map1.get("month"));


		//线路列表为空查询金库下所有上门收款线路，金库编号也为空查询全部上门收款线路
		if (0 == lineNos.size()) {
			Map<String, Object> map = new HashMap<>(2);
			map.put("lineType", StatusEnum.LineType.CALL_CUSTOMER_LINE.getType());
			if (!StringUtil.isNullorEmpty(clrCenterNo)) {
				map.put("clrCenterNo", clrCenterNo);
			}
//			lineNos = lineNetworkLineRunInfoMapper.getLineNosWithTypeAndClrNo(map);
			lineNos = lineTableMapper.getLineNosWithTypeAndClrNo(map);
		}

		// 计算这个月总天数
		int totalDay;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdFormat.parse(theYearMonth + "-01"));
			 totalDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}catch (Exception e){
			return "fail";
		}


		//删除旧的线路运行图记录
		{
			Map<String, Object> map = new HashMap<>(2);
			map.put("theYearMonth", theYearMonth);
			map.put("lineNos", lineNos);
			//查询全部符合条件的线路运行图编号
//			List<String> oldLineRunNos = lineCallCustomerLineRunMapper.getNosWithYearMonthAndLineNos(map);
			List<String> oldLineRunNos = lineWorkMapper.getNosWithYearMonthAndLineNos(map);
			//删除详情和总记录
			for (String lineRunNo : oldLineRunNos) {
//				lineCallCustomerLineRunDetailMapper.deleteByLineRunNo(lineRunNo);
				lineScheduleMapper.deleteByLineWorkID(lineRunNo);
//				lineCallCustomerLineRunMapper.deleteByNo(lineRunNo);
				lineWorkMapper.deleteByPrimaryKey(lineRunNo);
			}
		}


		//遍历线路，按照日期生成记录
		for (String lineNo : lineNos) {
			for (int day = 1; day <= totalDay; day++) {
				int sortNo = 1;
				//生成编号:线路编号+yyyyMMdd
				String lineWorkId = lineNo + theYearMonth.substring(0, 4) + theYearMonth.substring(5) + String.format("%02d", day);

				//当天日期
				Calendar cal1 = Calendar.getInstance();
				cal1.set(year, month - 1, day, 0, 0, 0);
				//计算星期几
				int weekday = cal1.get(Calendar.DAY_OF_WEEK) + 1;

				LineWorkDO lineCallCustomerLineRunDO = new LineWorkDO();
				lineCallCustomerLineRunDO.setLineWorkId(lineWorkId);
				lineCallCustomerLineRunDO.setLineNo(lineNo);
				lineCallCustomerLineRunDO.setTheDay(String.format("%02d", day));
				lineCallCustomerLineRunDO.setTheYearMonth(theYearMonth);

				//查询当日客户
				Map<String, Object> lineNoAndWordDay = new HashMap<>();
				lineNoAndWordDay.put("lineNo", lineNo);
				lineNoAndWordDay.put("weekDay", weekday);
				List<CallCustomerTimeDTO> callCustomerTimeDTOS = callCustomerTimeResource.qryByLineNoAndWeekDate(lineNoAndWordDay);

				//根据客户信息生成详情
				List<LineScheduleDO> lineCallCustomerLineRunDetailDOS = new ArrayList<>();
				for (CallCustomerTimeDTO callCustomerTimeDTO : callCustomerTimeDTOS) {

					LineScheduleDO lineCallCustomerLineRunDetailDO = new LineScheduleDO();
					lineCallCustomerLineRunDetailDO.setLineWorkId(lineWorkId);
					lineCallCustomerLineRunDetailDO.setCustomerNo(callCustomerTimeDTO.getCustomerNo());
					lineCallCustomerLineRunDetailDO.setArrivalTime(callCustomerTimeDTO.getArrivalTime());
					lineCallCustomerLineRunDetailDO.setClrTimeInterval(String.valueOf(callCustomerTimeDTO.getClrTimeInterval()));
					lineCallCustomerLineRunDetailDO.setAddress(callCustomerTimeDTO.getAddress());
					lineCallCustomerLineRunDetailDO.setCustomerName(callCustomerTimeDTO.getCustomerName());
					lineCallCustomerLineRunDetailDOS.add(lineCallCustomerLineRunDetailDO);
				}
				//统计客户数
				lineCallCustomerLineRunDO.setCustomerCount(lineCallCustomerLineRunDetailDOS.size());
				//插入排班信息
//				int x = lineCallCustomerLineRunMapper.insert(lineCallCustomerLineRunPO);
				int x = lineWorkMapper.insert(lineCallCustomerLineRunDO);
				if (x != 1) {
					log.error("生成失败！");
					returnDTO.setRetCode("FF");
					returnDTO.setRetMsg("生成失败！");
					throw new RuntimeException();
				}
				for (LineScheduleDO lineCallCustomerLineRunDetailDO : lineCallCustomerLineRunDetailDOS) {
//					int y = lineCallCustomerLineRunDetailMapper.insert(lineCallCustomerLineRunDetailPO);
					lineCallCustomerLineRunDetailDO.setCustomerType(5);
					lineCallCustomerLineRunDetailDO.setTheDay(String.format("%02d", day));
					lineCallCustomerLineRunDetailDO.setTheYearMonth(theYearMonth);
					lineCallCustomerLineRunDetailDO.setLineNo(lineNo);
					int y = lineScheduleMapper.insert(lineCallCustomerLineRunDetailDO);
					if (y != 1) {
						log.error("生成失败！");
						returnDTO.setRetCode("FF");
						returnDTO.setRetMsg("生成失败！");
						throw new RuntimeException();
					}
					sortNo++;
				}
			}
		}
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}


	/**
	 * 修改上门收款线路运行图
	 */
	@ZjComponentMapping("modOnSiteCollection")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")}
	)
	public String modOnSiteCollection(LineWorkTableDTO requestDTO, DTO returnDTO, String str) {
		String lineWorkId = requestDTO.getLineWorkId();
		//删除原详情
//		lineCallCustomerLineRunDetailMapper.deleteByLineRunNo(lineWorkId);
		lineScheduleMapper.deleteByLineWorkID(lineWorkId);
		//新增新详情
		List<LineScheduleDO> lineCallCustomerLineRunDetailPOS =
				LineCallCustomerLineRunDetailConverter.INSTANCE.dto2po(requestDTO.getDetailList());
		for (LineScheduleDO detail : lineCallCustomerLineRunDetailPOS) {
			detail.setLineWorkId(lineWorkId);
//			int x = lineCallCustomerLineRunDetailMapper.insert(detail);
			int x = lineScheduleMapper.insert(detail);
			if (x != 1) {
				log.error("修改失败！");
				returnDTO.setRetMsg("修改失败！");
				returnDTO.setRetCode("FF");
				throw new RuntimeException();
			}
		}
		//更新客户数
//		int y = lineCallCustomerLineRunMapper.updateCustomerNumByNo(lineWorkId);
		int y = lineWorkMapper.updateCustomerNumByNo(lineWorkId);
		if (y != 1) {
			log.error("修改失败！");
			returnDTO.setRetMsg("修改失败！");
			returnDTO.setRetCode("FF");
			throw new RuntimeException();
		}
		returnDTO.setRetCode("00");
		returnDTO.setRetMsg("修改成功！");
		return "ok";
	}

}
