package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface VisitOrderService {
	PageDTO<VisitOrderDTO> qryVisitOrder(Map<String, Object> paramMap);

	DTO addVisitOrder(VisitOrderDTO visitOrderDTO);

	DTO deleteVisitOrder( String id,String orderDate);

	DTO updateVisitOrder(VisitOrderDTO visitOrderDTO);

	/**
	 * 查询上门预约客户列表
	 * @return 结果
	 */
	Map<String,Object> qryOrderCustomers();

	DTO auditVisitOrder (VisitOrderDTO visitOrderDTO);
}
