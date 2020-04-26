package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.domain.VisitOrderDO;
import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
import com.zjft.microservice.treasurybrain.business.mapstruct.VisitOrderConverter;
import com.zjft.microservice.treasurybrain.business.repository.VisitOrderPOMapper;
import com.zjft.microservice.treasurybrain.business.service.VisitOrderService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.CallCustomerInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.CallCustomerLineRunDetailDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.CallCustomerLineRunDetailInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴朋
 * @since 2019/9/21
 */

@Slf4j
@Service
public class VisitOrderServiceImpl implements VisitOrderService {
	@Resource
	VisitOrderPOMapper visitOrderPOMapper;

	@Resource
	private CallCustomerInnerResource callCustomerInnerResource;

	@Resource
	private CallCustomerLineRunDetailInnerResource callCustomerLineRunDetailInnerResource;

	/**
	 * 上门预约管理分页查询
	 */
	@Override
	public PageDTO<VisitOrderDTO> qryVisitOrder(Map<String, Object> paramMap) {
		PageDTO<VisitOrderDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = visitOrderPOMapper.queryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		List<VisitOrderDO> doList = visitOrderPOMapper.qryVisitOrderByPage(paramMap);
		List<VisitOrderDTO> dtoList = VisitOrderConverter.INSTANCE.do2dto(doList);
		pageDTO.setRetList(dtoList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	/**
	 * 上门预约管理新增
	 */
	@Override
	public DTO addVisitOrder(VisitOrderDTO visitOrderDTO) {
		VisitOrderDO visitOrderDO = VisitOrderConverter.INSTANCE.do2dto(visitOrderDTO);
		visitOrderDO.setStatus(0);
		String customerNumber = visitOrderDTO.getCustomerNumber();
		String orderDate = visitOrderDTO.getOrderDate();
		VisitOrderDO visitOrderDOS = visitOrderPOMapper.selectByPrimaryKey(customerNumber, orderDate);
		if (visitOrderDOS != null) {
			return new DTO(RetCodeEnum.AUDIT_OBJECT_EXIST);
		}
		int x = visitOrderPOMapper.insertSelective(visitOrderDO);
		if (x == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 上门预约管理删除
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO deleteVisitOrder(String customerNumber, String orderDate) {
//		String theYearMonth = orderDate.substring(0,7);
//		String theDay = orderDate.substring(8);
//		//根据客户编号在上门客户信息表（CALL_CUSTOMER_TABLE）里找到上门线路编号
//		String lineNo = callCustomerLineRunDetailsMapper.selectlineNo(customerNumber);
//
//		//根据上门线路编号 日期 在上门收款线路排班表（CALL_CUSTOMER_LINE_RUM）里找到编号（LINE_RUN_NO）
//		Map<String,Object> paramMap = new HashMap<>();
//		paramMap.put("theYearMonth",theYearMonth);
//		paramMap.put("theDay",theDay);
//		paramMap.put("lineNo",lineNo);
//		String lineRunNo = callCustomerLineRunDetailsMapper.selectLineRunNo(paramMap);
//
//		//删除上门收款线路排班详情表的预约
//		callCustomerLineRunDetailsMapper.deleteByLineRunNo(lineRunNo);
//
//		//更新上门收款线路排班表客户数量
//		callCustomerLineRunDetailsMapper.updateCustomerNumByNo(lineRunNo);

		int x = visitOrderPOMapper.deleteByPrimaryKey(customerNumber, orderDate);
		if (x != 1) {
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 上门预约管理修改
	 */
	@Override
	public DTO updateVisitOrder(VisitOrderDTO visitOrderDTO) {
		VisitOrderDO visitOrderDO = VisitOrderConverter.INSTANCE.do2dto(visitOrderDTO);
		visitOrderDO.setStatus(0);
		int x = visitOrderPOMapper.updateByPrimaryKeySelective(visitOrderDO);
		if (x == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 查询上门客户信息列表
	 */
	@Override
	public Map<String, Object> qryOrderCustomers() {
		List<Map<String, Object>> orderCustomerList = new ArrayList<>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			orderCustomerList = visitOrderPOMapper.qryOrderCustomers();
			retMap.put("orderCustomerList", orderCustomerList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
		} catch (Exception e) {
			log.error("查询线路失败", e);
			retMap.put("retCode", RetCodeEnum.FAIL.getCode());
			retMap.put("retMsg", "查询失败！");
		}
		return retMap;
	}

	/**
	 * 上门预约管理审核
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO auditVisitOrder(VisitOrderDTO visitOrderDTO) {
		if (visitOrderDTO.getStatus() == 2) {
			VisitOrderDO visitOrderDO = VisitOrderConverter.INSTANCE.do2dto(visitOrderDTO);
			int y = visitOrderPOMapper.updateByPrimaryKey(visitOrderDO);
			if (y == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
			return new DTO(RetCodeEnum.FAIL);
		}
		String customerNumber = visitOrderDTO.getCustomerNumber();
		String orderDate = visitOrderDTO.getOrderDate();
		String theYearMonth = orderDate.substring(0, 7);
		String theDay = orderDate.substring(8);
		//根据客户号在上门客户信息表（CALL_CUSTOMER_TABLE）里找到上门线路编号,客户编号
//		CallCustomerLineRunDetailDO callCustomerLineRunDetailDOs = callCustomerLineRunDetailsMapper.selectlineNo(customerNumber);
		Map<String,Object> selectlineNoMap = callCustomerInnerResource.selectlineNo(customerNumber);
		String lineNo = StringUtil.parseString(selectlineNoMap.get("lineNo"));
		String customerNo = StringUtil.parseString(selectlineNoMap.get("customerNo"));
//		String lineNo = callCustomerLineRunDetailDOs.getLineNo();
//		String customerNo = callCustomerLineRunDetailDOs.getCustomerNo();

		//根据上门线路编号 日期 在上门收款线路排班表（CALL_CUSTOMER_LINE_RUM）里找到编号（LINE_RUN_NO）
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("theYearMonth", theYearMonth);
		paramMap.put("theDay", theDay);
		paramMap.put("lineNo", lineNo);
//		String lineRunNo = callCustomerLineRunDetailsMapper.selectLineRunNo(paramMap);
		String lineRunNo = callCustomerLineRunDetailInnerResource.qryLineRunNo(paramMap);

		if (lineRunNo == null) {
			return new DTO(RetCodeEnum.FAIL.getCode(), "预约日期的线路尚未生成，请先生成");
		}

		//根据 customerNumber  和 lineRunNo 在上门收款线路排班详情表(CALL_CUSTOMER_LINE_RUN_DETAIL)里查是否有此数据，如果有则更新，没有则新增
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("lineRunNo", lineRunNo);
		paraMap.put("customerNo", customerNo);
//		CallCustomerLineRunDetailDO callCustomerLineRunDetailDO = callCustomerLineRunDetailsMapper.qryCallCustomerLineRunDetailDO(paraMap);
		LineScheduleDTO callCustomerLineRunDetailDO = callCustomerLineRunDetailInnerResource.qryCallCustomerLineRunDetail(paraMap);
		int x = 0;
//		CallCustomerLineRunDetailPO callCustomerLineRunDetailPO1 = new CallCustomerLineRunDetailPO();
//		callCustomerLineRunDetailPO1.setLineRunNo(lineRunNo);
//		callCustomerLineRunDetailPO1.setArrivalTime(visitOrderDTO.getOrderTime());
//		callCustomerLineRunDetailPO1.setCustomerNo(customerNo);
//		callCustomerLineRunDetailPO1.setClrTimeInterval(visitOrderDTO.getOrderTimePeriod());
		Map<String,Object> map = new HashMap<>();
		map.put("lineRunNo",lineRunNo);
		map.put("arrivalTime",visitOrderDTO.getOrderTime());
		map.put("customerNo",customerNo);
		map.put("clrTimeInterval",visitOrderDTO.getOrderTimePeriod());
		if (callCustomerLineRunDetailDO == null) {
//			callCustomerLineRunDetailPO1.setClrTimeInterval(visitOrderDTO.getOrderTimePeriod());
			map.put("clrTimeInterval",visitOrderDTO.getOrderTimePeriod());
//			x = callCustomerLineRunDetailsMapper.insert(callCustomerLineRunDetailPO1);
			x = callCustomerLineRunDetailInnerResource.insertByMap(map);
		} else {
//			x = callCustomerLineRunDetailsMapper.updateByPrimaryKey(callCustomerLineRunDetailPO1);
			x = callCustomerLineRunDetailInnerResource.insertByMap(map);
		}
		//更新上门收款线路排班表客户数量
		int z = callCustomerLineRunDetailInnerResource.updateCustomerNumByNo(lineRunNo);

		//更新上门客户预约表状态
		VisitOrderDO visitOrderDO = VisitOrderConverter.INSTANCE.do2dto(visitOrderDTO);
		int y = visitOrderPOMapper.updateByPrimaryKey(visitOrderDO);
		if (x != 1 || y != 1 || z != 1) {
			throw new RuntimeException("失败");
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}
}
