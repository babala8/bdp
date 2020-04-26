package com.zjft.microservice.treasurybrain.productcenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.dto.*;
import com.zjft.microservice.treasurybrain.productcenter.dto.CustomerTypeDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceDetailDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceStatusExpandDTO;
import com.zjft.microservice.treasurybrain.productcenter.service.ServiceCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ServiceCenterResourceImpl implements ServiceCenterResource {

	@Resource
	private ServiceCenterService productService;

	@Override
	public PageDTO<ServiceDTO> qryProductInfoByPage(Map<String, Object> paramMap) {
		try {
			return productService.qryProductInfoByPage(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ServiceDetailDTO qryProductDetail(String serviceNo) {
		if (StringUtil.isNullorEmpty(serviceNo)) {
			return new ServiceDetailDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return productService.qryProductDetail(serviceNo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceDetailDTO(RetCodeEnum.EXCEPTION);
		}
	}

//	@Override
//	public SelfServiceDetailDTO qryProductDetails(String serviceNo) {
//		if (StringUtil.isNullorEmpty(serviceNo)) {
//			return new SelfServiceDetailDTO(RetCodeEnum.PARAM_LACK);
//		}
//		try {
//			return productService.qryProductDetails(serviceNo);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new SelfServiceDetailDTO(RetCodeEnum.EXCEPTION);
//		}
//	}

	@Override
	public ListDTO<CustomerTypeDTO> qryCustomerType() {
		try {
			return productService.qryCustomerType();
		} catch (Exception e) {
			e.printStackTrace();
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<ServiceModuleDTO> qryModules() {
		try {
			return productService.qryModules();
		} catch (Exception e) {
			e.printStackTrace();
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}
//	@Override
//	public DTO modProductStatus(ServiceDTO productDTO) {
//		try {
//			Integer productNo = productDTO.getProductNo();
//			if (productNo ==null) {
//				return new DTO(RetCodeEnum.FAIL);
//			}
//			return productService.modProductStatus(productDTO);
//		} catch (Exception e) {
//			log.error("更新产品状态失败", e);
//			return new DTO(RetCodeEnum.EXCEPTION);
//		}
//	}
//	@Override
//	public DTO addProductInfo(ServiceDTO productDTO) {
//		try {
//			String productName = productDTO.getProductName();
//			if (StringUtil.isNullorEmpty(productName)) {
//				return new DTO(RetCodeEnum.FAIL);
//			}
//			return productService.addProductInfo(productDTO);
//		} catch (Exception e) {
//			log.error("新增产品信息失败", e);
//			return new DTO(RetCodeEnum.EXCEPTION);
//		}
//	}

	@Override
	public DTO addProductStatusInfo(List<ServiceStatusDTO> serviceStatusDTOS) {
		try {
			if (serviceStatusDTOS.size()<0) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return productService.addProductStatusInfo(serviceStatusDTOS);
		} catch (Exception e) {
			log.error("增加产品状态节点信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

//	@Override
//	public DTO addProductStatusConverInfo(ServiceStatusConvertDTO productStatusConvertDTO) {
//		List<ServiceStatusDTO> serviceStatusDTOS = productStatusConvertDTO.getServiceStatusDTOS();
//		List<ServiceStatusConvertDTO> serviceStatusConvertDTOS = productStatusConvertDTO.getServiceStatusConvertDTOS();
//		try {
//			if (serviceStatusConvertDTOS.size()<0 && serviceStatusDTOS.size()<0) {
//				return new DTO(RetCodeEnum.FAIL);
//			}
//			ServiceStatusConvertDTO productStatusConvertDTO1=productStatusConvertDTO;
//			return productService.addProductStatusConverInfo(productStatusConvertDTO1);
//		} catch (Exception e) {
//			log.error("增加产品状态节点信息失败", e);
//			return new DTO(RetCodeEnum.EXCEPTION);
//		}
//	}




	@Override
	public DTO addProductGoodsInfo(List<ServiceProductDTO> serviceProductDTOS) {
		try {

			return productService.addProductGoodsInfo(serviceProductDTOS);
		} catch (Exception e) {
			log.error("增加产品流转物品信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}


	@Override
	public ListDTO<ServiceStatusExpandDTO> qryProductStatusExpand(String operateType){
		try {
			return productService.qryProductStatusExpand(operateType);
		} catch (Exception e) {
			e.printStackTrace();
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<ServiceStatusDTO> qryServiceStatus(String serviceNo){
		try {
			return productService.qryServiceStatus(serviceNo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 查询个性可用产品
	 */
	@Override
	public PageDTO<ServiceDTO> qryProductForUse(Map<String, Object> paramMap) {
		return productService.qryProductForUse(paramMap);
	}

}
