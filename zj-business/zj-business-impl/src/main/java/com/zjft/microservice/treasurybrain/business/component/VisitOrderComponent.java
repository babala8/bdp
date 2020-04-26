package com.zjft.microservice.treasurybrain.business.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.business.domain.VisitOrderDO;
import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
import com.zjft.microservice.treasurybrain.business.mapstruct.VisitOrderConverter;
import com.zjft.microservice.treasurybrain.business.repository.VisitOrderPOMapper;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@ZjComponentResource(group = "visitOrder")
public class VisitOrderComponent {
	@Resource
	private VisitOrderPOMapper visitOrderPOMapper;
	/**
	 * 计划分组信息查询
	 */
	@ZjComponentMapping("addVisitOrder(添加预约)")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addVisitOrder(VisitOrderDTO visitOrderDTO, DTO returnDTO, List<String> taskNos) {
		VisitOrderDO visitOrderDO = VisitOrderConverter.INSTANCE.do2dto(visitOrderDTO);
		visitOrderDO.setStatus(0);
		String customerNumber = visitOrderDTO.getCustomerNumber();
		String orderDate = visitOrderDTO.getOrderDate();
		VisitOrderDO visitOrderDOS = visitOrderPOMapper.selectByPrimaryKey(customerNumber, orderDate);
		if (visitOrderDOS != null) {
			returnDTO.setRetMsg("对象已存在,请勿重复添加!");
			return "fail";
		}
		int x = visitOrderPOMapper.insertSelective(visitOrderDO);
		if (x == 1) {
			return "ok";
		}
		return "fail";
	}

	/**
	 * 添加预约失败
	 */
	@ZjComponentMapping("addVisitOrderFail(添加预约失败)")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addVisitOrderFail(VisitOrderDTO visitOrderDTO, DTO returnDTO, List<String> taskNos) {
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
