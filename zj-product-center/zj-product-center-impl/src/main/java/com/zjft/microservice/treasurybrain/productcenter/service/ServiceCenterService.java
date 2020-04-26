package com.zjft.microservice.treasurybrain.productcenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ServiceCenterService {

	PageDTO<ServiceDTO> qryProductInfoByPage(Map<String, Object> paramMap);

	ServiceDetailDTO qryProductDetail(String serviceNo);

//	SelfServiceDetailDTO qryProductDetails(String serviceNo);

	ListDTO<CustomerTypeDTO> qryCustomerType();

	ListDTO<ServiceModuleDTO> qryModules();

	DTO modProductStatus(ServiceDTO serviceDTO);

	DTO addProductInfo(ServiceDTO serviceDTO);

	DTO addProductStatusInfo(List<ServiceStatusDTO> serviceStatusDTOS);

	DTO addProductStatusConverInfo(ServiceStatusConvertDTO serviceStatusConvertDTO1);

	DTO addProductGoodsInfo(List<ServiceProductDTO> serviceProductDTOS);

	ListDTO<ServiceStatusExpandDTO> qryProductStatusExpand(String operateType);

	ListDTO<ServiceStatusDTO> qryServiceStatus(String serviceNo);

	PageDTO<ServiceDTO> qryProductForUse(Map<String, Object> paramMap);

	Integer qryNextByOperateType(String operateType);

	String qryNameByOperateType(String operateType);

	int qryExist(Map<String, Object> Map);

	Integer isStatusConvertMatch(Integer taskType,Integer curStatus, String operateType);

	/**
	 *当你确定上一个转换状态只有一个的时候用这个
	 */
	String mayStatusConvert(Integer nextStatus, Integer serviceNo);

	/**
	 *否则就用这个
	 */
	List<String> mayStatusConverts(String operateType, Integer serviceNo);

	String getJSONTemplate(int serviceNo,String operateType);

	List<String> selectCurStatus(Integer taskType,String operateType);

	/** 先使用这个
	 * 判断任务单是否可以进行operateType操作
	 * @param taskNo 任务单编号
	 * @param operateType 操作类型
	 * @param set 跳过的操作
	 * @return
	 */
	Boolean isConvert(String taskNo, String operateType, Set set);

//	/**
//	 * 判断任务单是否可以进行operateType操作
//	 * @param taskNo 任务单编号
//	 * @param operateType 操作类型
//	 * @return
//	 */
//	int isStatusConvert(String taskNo, String operateType);

}
