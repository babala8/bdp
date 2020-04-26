package com.zjft.microservice.treasurybrain.productcenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceStatusConvertDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceStatusDTO;
import com.zjft.microservice.treasurybrain.productcenter.po.ServiceStatusConvertPO;
import com.zjft.microservice.treasurybrain.productcenter.po.ServiceStatusPO;
import com.zjft.microservice.treasurybrain.productcenter.po.ServiceTablePO;
import com.zjft.microservice.treasurybrain.productcenter.repository.ServiceMapper;
import com.zjft.microservice.treasurybrain.productcenter.repository.ServiceStatusConvertsMapper;
import com.zjft.microservice.treasurybrain.productcenter.repository.ServiceStatusMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@ZjComponentResource(group = "product")
public class ServiceComponent {

	@Resource
	private ServiceMapper serviceMapper;

	@Resource
	private ServiceStatusMapper serviceStatusMapper;

	@Resource
	private ServiceStatusConvertsMapper serviceStatusConvertsMapper;


	/**
	 * 增加产品信息
	 */
	@ZjComponentMapping("addProductInfo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addProductInfo(ServiceDTO serviceDTO, ObjectDTO returnDTO, List<String> list) {
		String serviceName = serviceDTO.getServiceName();
		if (StringUtil.isNullorEmpty(serviceName)) {
//			throw new RuntimeException("失败");
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			return "fail";
		}
		ServiceTablePO serviceTablePO = new ServiceTablePO();
		Integer serviceMaxNo = serviceMapper.qryMaxServiceNo();
		serviceTablePO.setServiceNo(serviceMaxNo != null ? serviceMaxNo + 1 : 1);

		serviceTablePO.setServiceName(serviceDTO.getServiceName());
		serviceTablePO.setCustomerType(serviceDTO.getCustomerType());
		serviceTablePO.setNote(serviceDTO.getNote());
		serviceTablePO.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
		serviceTablePO.setUpdateTime(CalendarUtil.getSysTimeYMDHMS());
		serviceTablePO.setStatus(1);
		serviceTablePO.setType(1);

		int x = serviceMapper.insert(serviceTablePO);
		if (x != 1) {
			log.error("添加产品基本信息失败！");
//			throw new RuntimeException("失败");
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			return "fail";
		}
		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		return "ok";
	}

	/**
	 * 修改产品信息
	 */
	@ZjComponentMapping("modProductInfo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String modProductInfo(ServiceDTO serviceDTO, ObjectDTO returnDTO, List<String> list) {
		Integer serviceNo = serviceDTO.getServiceNo();
		if (serviceNo == null) {
			returnDTO.setRetCode("FF");
			return "fail";
		}
		ServiceTablePO serviceTablePO = new ServiceTablePO();
		serviceTablePO.setServiceNo(serviceDTO.getServiceNo());
		serviceTablePO.setStatus(serviceDTO.getStatus());
		serviceTablePO.setUpdateTime(CalendarUtil.getSysTimeYMDHMS());

		int x = serviceMapper.updateProductStatus(serviceTablePO);
		if (x != 1) {
			log.error("更新产品状态信息失败！");
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
		}
		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		return "ok";
	}

	/**
	 * 失败组件
	 */
	@ZjComponentMapping("fail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String applyForSelfProductFail(ServiceDTO serviceDTO, ObjectDTO returnDTO, List<String> list) {
		returnDTO.setRetCode("FF");
		return "fail";
	}

	/**
	 * 增加产品状态节点信息参数校验
	 */
	@ZjComponentMapping("addProductStatusConverCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addProductStatusConverCheck(ServiceStatusConvertDTO serviceStatusConvertDTO, ObjectDTO returnDTO, List<String> list) {

		List<ServiceStatusDTO> serviceStatusDTOS = serviceStatusConvertDTO.getServiceStatusDTOS();
		List<ServiceStatusConvertDTO> serviceStatusConvertDTOS = serviceStatusConvertDTO.getServiceStatusConvertDTOS();
		if (serviceStatusConvertDTOS.size() < 0 && serviceStatusDTOS.size() < 0) {
			returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
			returnDTO.setRetMsg(RetCodeEnum.PARAM_ERROR.getTip());
			return "fail";
		}

		return "ok";
	}

	/**
	 * 增加产品状态节点信息
	 */
	@ZjComponentMapping("addProductStatusConver")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addProductStatusConver(ServiceStatusConvertDTO serviceStatusConvertDTO, ObjectDTO returnDTO, List<String> list) {
		List<ServiceStatusDTO> serviceStatusDTOS = serviceStatusConvertDTO.getServiceStatusDTOS();
		List<ServiceStatusConvertDTO> serviceStatusConvertDTOS = serviceStatusConvertDTO.getServiceStatusConvertDTOS();

		if (serviceStatusDTOS.size() > 0) {
			ServiceStatusDTO dto = serviceStatusDTOS.get(0);
			if (dto.getServiceNo() != null) {
				serviceStatusMapper.deleteByProductNo(dto.getServiceNo());
			}
		}

		for (ServiceStatusDTO serviceStatusDTO : serviceStatusDTOS) {
			if (serviceStatusDTO == null || serviceStatusDTO.getServiceNo() == null) {
				log.error("添加产品状态节点失败！产品编号为空！");
				returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
				returnDTO.setRetMsg("添加产品状态节点失败！产品编号为空！");
				return "fail";
			}
			ServiceStatusPO serviceStatusPO = new ServiceStatusPO();
			serviceStatusPO.setServiceNo(serviceStatusDTO.getServiceNo());
			serviceStatusPO.setStatus(serviceStatusDTO.getStatus());
			serviceStatusPO.setName(serviceStatusDTO.getName());
			serviceStatusPO.setNote(serviceStatusDTO.getNote());

			int x = serviceStatusMapper.insert(serviceStatusPO);
			if (x != 1) {
				log.error("添加产品状态节点失败！");
				returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
				returnDTO.setRetMsg("添加产品状态节点失败");
				return "fail";
			}
		}

		if (serviceStatusConvertDTOS.size() > 0) {
			ServiceStatusConvertDTO dto = serviceStatusConvertDTOS.get(0);
			if (dto.getServiceNo() != null) {
				serviceStatusConvertsMapper.deleteByProductNo(dto.getServiceNo());
			}
		}

		int maxId = 1;
		for (ServiceStatusConvertDTO serviceStatusConvertDTO1 : serviceStatusConvertDTOS) {
			if (serviceStatusConvertDTO1 == null || serviceStatusConvertDTO1.getServiceNo() == null) {
				log.error("添加产品状态节点顺序失败！产品编号为空！");
				returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
				returnDTO.setRetMsg("添加产品状态节点顺序失败！产品编号为空");
				return "fail";
			}
			ServiceStatusConvertPO serviceStatusConvertPO = new ServiceStatusConvertPO();
			int id = serviceStatusConvertDTO1.getServiceNo();
			serviceStatusConvertPO.setId(String.valueOf(id * 100 + maxId));
			serviceStatusConvertPO.setServiceNo(serviceStatusConvertDTO1.getServiceNo());
			serviceStatusConvertPO.setCurStatus(serviceStatusConvertDTO1.getCurStatus());
			serviceStatusConvertPO.setOperateType(serviceStatusConvertDTO1.getOperateType());
			//serviceStatusConvertPO.setNextStatus(serviceStatusConvertDTO.getNextStatus());
			serviceStatusConvertPO.setDescription(serviceStatusConvertDTO1.getDescription());
			serviceStatusConvertPO.setModuleNo(serviceStatusConvertDTO1.getModuleNo());
			serviceStatusConvertPO.setWeakNode(serviceStatusConvertDTO1.getWeakNode());

			int x = serviceStatusConvertsMapper.insert(serviceStatusConvertPO);
			if (x != 1) {
				log.error("添加产品状态节点顺序失败！");
				returnDTO.setRetCode(RetCodeEnum.FAIL.getCode());
				returnDTO.setRetMsg("添加产品状态节点顺序失败");
				return "fail";
			}
			maxId++;
		}

		returnDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
		return "ok";

	}

	/**
	 * 失败组件
	 */
	@ZjComponentMapping("fail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addProductStatusConverFail(ServiceStatusConvertDTO serviceStatusConvertDTO, ObjectDTO returnDTO, List<String> list) {
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
