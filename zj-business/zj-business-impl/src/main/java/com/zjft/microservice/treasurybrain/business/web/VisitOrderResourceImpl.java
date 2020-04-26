package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
import com.zjft.microservice.treasurybrain.business.service.VisitOrderService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴朋
 * @since 2019/9/21
 */

@Slf4j
@RestController
public class VisitOrderResourceImpl implements VisitOrderResource {
	@Resource
	private VisitOrderService visitOrderService;
	/**
	 * 上门预约管理分页查询
	 */
	@Override
	public PageDTO<VisitOrderDTO> qryVisitOrder(Map<String, Object> paramMap) {
			return visitOrderService.qryVisitOrder(paramMap);
	}

//	/**
//	 * 上门预约管理添加
//	 */
//	@Override
//	public DTO addVisitOrder(VisitOrderDTO visitOrderDTO) {
//			return visitOrderService.addVisitOrder(visitOrderDTO);
//	}

	/**
	 * 上门预约管理删除
	 */
	@Override
	public DTO deleteVisitOrder(String customerNumber,String orderDate) {
		if (customerNumber == null || "".equals(customerNumber)) {
			return new DTO(RetCodeEnum.FAIL);
		}
		return visitOrderService.deleteVisitOrder(customerNumber,orderDate);
	}
	/**
	 * 上门预约管理修改
	 */
	@Override
	public DTO updateVisitOrder(VisitOrderDTO visitOrderDTO) {
		return visitOrderService.updateVisitOrder(visitOrderDTO);
	}

	/**
	 * 审核
	 */
	@Override
	public DTO auditVisitOrder(VisitOrderDTO visitOrderDTO) {
		return visitOrderService.auditVisitOrder(visitOrderDTO);
	}

	/**
	 * 查询上门客户信息列表
	 */
	@Override
	public ListDTO qryOrderCustomers() {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> orderCustomerList = visitOrderService.qryOrderCustomers();
			List retList = (List) orderCustomerList.get("orderCustomerList");
			dto.setRetList(retList);
			dto.setRetCode(StringUtil.parseString(orderCustomerList.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(orderCustomerList.get("retMsg")));
		} catch (Exception e) {
			log.error("查询上门预约客户列表失败", e);
			dto.setRetException("查询上门预约客户列表失败");
		}
		return dto;
	}
}
