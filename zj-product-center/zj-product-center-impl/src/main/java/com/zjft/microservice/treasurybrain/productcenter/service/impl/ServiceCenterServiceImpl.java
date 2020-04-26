package com.zjft.microservice.treasurybrain.productcenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.productcenter.domain.*;
import com.zjft.microservice.treasurybrain.productcenter.dto.*;
import com.zjft.microservice.treasurybrain.productcenter.mapstruct.ServiceConverter;
import com.zjft.microservice.treasurybrain.productcenter.mapstruct.ServiceDetailConverter;
import com.zjft.microservice.treasurybrain.productcenter.po.*;
import com.zjft.microservice.treasurybrain.productcenter.repository.*;
import com.zjft.microservice.treasurybrain.productcenter.service.ServiceCenterService;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class ServiceCenterServiceImpl implements ServiceCenterService {

	@Resource
	private ServiceMapper serviceMapper;

	@Resource
	private ServiceStatusMapper serviceStatusMapper;

	@Resource
	private ServiceStatusConvertsMapper serviceStatusConvertsMapper;

	@Resource
	private ServiceProductMapper serviceProductMapper;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private ServiceProductPropertyMapper serviceProductPropertyMapper;

	@Resource
	private ProductPropertyValueMapper productPropertyValueMapper;

	@Resource
	private ProductTableMapper productTableMapper;

	@Resource
	private ProductPropertyKeyMapper productPropertyKeyMapper;

	@Override
	public PageDTO<ServiceDTO> qryProductInfoByPage(Map<String, Object> paramMap) {
		PageDTO<ServiceDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = serviceMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<ServiceDO> doList = serviceMapper.qryProductInfoByPage(paramMap);
		List<ServiceDTO> dtoList = ServiceConverter.INSTANCE.do2dto(doList);

		pageDTO.setRetList(dtoList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public ServiceDetailDTO qryProductDetail(String serviceNo) {
		ServiceDO serviceDO = serviceMapper.qryProductInfo(serviceNo);
		ServiceDetailDTO productDetailDTO = ServiceDetailConverter.INSTANCE.do2detaildto(serviceDO);
		productDetailDTO.setResult(RetCodeEnum.FAIL);
		List<ServiceStatusDTO> statusDTOList = serviceMapper.qryProductServiceStatus(serviceNo);
		List<ServiceStatusConvertDTO> statusConvertDTOList = serviceMapper.qryProductStatusConvert(serviceNo);

		//todo 该服务的产品信息
		List<String> productNoList = serviceProductPropertyMapper.qyrProductNo(serviceNo);
		List<ServiceProductDTO> serviceProductDTOList = new ArrayList<>();
		ServiceProductDTO serviceProductDTO = null;
		//遍历该服务下的所有产品
		for (String productNo : productNoList) {
			List<ServiceProductPropertyPO> serviceProductPropertyPOS = serviceProductPropertyMapper.qryByServiceNo(serviceNo,productNo);

			serviceProductDTO = new ServiceProductDTO();
			serviceProductDTO.setServiceNo(Integer.parseInt(serviceNo));
			serviceProductDTO.setProductNo(productNo);
			serviceProductDTO.setDirection(serviceProductPropertyPOS.get(0).getDirection());
			String productName = productTableMapper.selectGoodsNameByGoodsNo(productNo);
			serviceProductDTO.setProductName(productName);
//			serviceProductDTO.setPropertyValueID(propertyValueID);
//			List<SelfServiceProductArgumentDTO> selfServiceProductArgumentDTOS = new ArrayList<>();
			List<ProductPropertyValueDetailDTO> productPropertyValueDetailDTOS = new ArrayList<>();
			ProductPropertyValueDetailDTO productPropertyValueDetailDTO = null;
			for (ServiceProductPropertyPO serviceProductPropertyPO : serviceProductPropertyPOS) {
				String propertyValueID = serviceProductPropertyPO.getPropertyValueID();
				//根据propertyValueID查询value表
				ProductPropertyValuePO productPropertyValuePO = productPropertyValueMapper.selectByPrimaryKey(propertyValueID);
				//根据value表里的prepertyNo查询prepertyName
				String propertyName = productPropertyKeyMapper.selectPropertyNameByPropertyNo(productPropertyValuePO.getPropertyNo());

				productPropertyValueDetailDTO = new ProductPropertyValueDetailDTO();

				productPropertyValueDetailDTO.setId(propertyValueID);
				productPropertyValueDetailDTO.setPropertyNo(productPropertyValuePO.getPropertyNo());
				productPropertyValueDetailDTO.setPropertyName(propertyName);
				productPropertyValueDetailDTO.setPropertyValue(productPropertyValuePO.getPropertyValue());
//				selfServiceProductArgumentDTO.setId(propertyValueID);
//				selfServiceProductArgumentDTO.setParameter(productPropertyValuePO.getPropertyNo());
//				selfServiceProductArgumentDTO.setParameterType(propertyName);
//				selfServiceProductArgumentDTO.setParameterName(productPropertyValuePO.getPropertyValue());

				productPropertyValueDetailDTOS.add(productPropertyValueDetailDTO);

			}
			serviceProductDTO.setPropertyValueDetailList(productPropertyValueDetailDTOS);
			serviceProductDTOList.add(serviceProductDTO);
		}


//		List<ServiceProductDTO> goodsDTOList = serviceMapper.qryProductServiceGoods(serviceNo);
//
//		if (goodsDTOList != null && goodsDTOList.size() > 0) {
//			for (ServiceProductDTO serviceProductDTO : goodsDTOList) {
//				String productNo = serviceProductDTO.getProductNo();
//				List<SelfServiceProductArgumentDTO> selfServiceProductArgumentDTOS = serviceMapper.qryPGoodsArgument(productNo);
//				serviceProductDTO.setSelfServiceProductArgumentDTOList(selfServiceProductArgumentDTOS);
//			}
//		}

		List<CallCustomerDTO> customerDTOSList = serviceMapper.qryProductCustomer(serviceDO.getCustomerType());
		productDetailDTO.setStatusList(statusDTOList);
		productDetailDTO.setCustomerList(customerDTOSList);
		productDetailDTO.setStatusConvertList(statusConvertDTOList);
		productDetailDTO.setProductList(serviceProductDTOList);
		productDetailDTO.setResult(RetCodeEnum.SUCCEED);
		return productDetailDTO;
	}

//	@Override
//	public SelfServiceDetailDTO qryProductDetails(String serviceNo) {
//		ServiceDO productDO = serviceMapper.qryProductInfo(serviceNo);
//		SelfServiceDetailDTO productDetailDTO = ServiceDetailConverter.INSTANCE.do2detaildtos(productDO);
//		productDetailDTO.setResult(RetCodeEnum.FAIL);
//		List<SelfServiceProductDTO> goodsDTOList = serviceMapper.qryProductServiceGoods(serviceNo);
//		if (goodsDTOList != null && goodsDTOList.size() > 0) {
//			for (SelfServiceProductDTO selfProductGoodsDTO : goodsDTOList) {
//				String goodsNo = selfProductGoodsDTO.getGoodsNo();
//				List<SelfServiceProductArgumentDTO> selfProductGoodsArgumentDTOS = selfProductMapper.qryPGoodsArgument(goodsNo);
//				selfProductGoodsDTO.setSelfServiceProductArgumentDTOList(selfProductGoodsArgumentDTOS);
//			}
//		}
//		List<CallCustomerDTO> customerDTOSList = selfProductMapper.qryProductCustomer(productDO.getCustomerType());
//
//		productDetailDTO.setCustomerList(customerDTOSList);
//		productDetailDTO.setGoodsList(goodsDTOList);
//		productDetailDTO.setResult(RetCodeEnum.SUCCEED);
//		return productDetailDTO;
//	}

	@Override
	public ListDTO<CustomerTypeDTO> qryCustomerType() {
		ListDTO listDTO = new ListDTO(RetCodeEnum.SUCCEED);
		List<CustomerTypeDTO> customerTypeDTOList = serviceMapper.qryCustomerType();
		listDTO.addAll(customerTypeDTOList);
		return listDTO;
	}

	@Override
	public ListDTO<ServiceModuleDTO> qryModules() {
		ListDTO listDTO = new ListDTO(RetCodeEnum.SUCCEED);
		List<ServiceModuleDTO> serviceModuleDTOList = serviceMapper.qryModules();
		listDTO.addAll(serviceModuleDTOList);
		return listDTO;
	}

	@Override
	public DTO modProductStatus(ServiceDTO serviceDTO) {

		ServiceTablePO serviceTablePO = new ServiceTablePO();
		serviceTablePO.setServiceNo(serviceDTO.getServiceNo());
		serviceTablePO.setStatus(serviceDTO.getStatus());
		serviceTablePO.setUpdateTime(CalendarUtil.getSysTimeYMDHMS());

		int x = serviceMapper.updateProductStatus(serviceTablePO);
		if (x != 1) {
			log.error("更新产品状态信息失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO addProductInfo(ServiceDTO serviceDTO) {

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
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO addProductStatusInfo(List<ServiceStatusDTO> serviceStatusDTOS) {
		if (serviceStatusDTOS.size() > 0) {
			ServiceStatusDTO dto = serviceStatusDTOS.get(0);
			if (dto.getServiceNo() != null) {
				serviceStatusMapper.deleteByProductNo(dto.getServiceNo());
			}
		}

		for (ServiceStatusDTO serviceStatusDTO : serviceStatusDTOS) {
			if (serviceStatusDTO == null || serviceStatusDTO.getServiceNo() == null) {
				log.error("添加产品状态节点失败！产品编号为空！");
				return new DTO(RetCodeEnum.FAIL);
			}
			ServiceStatusPO serviceStatusPO = new ServiceStatusPO();
			serviceStatusPO.setServiceNo(serviceStatusDTO.getServiceNo());
			serviceStatusPO.setStatus(serviceStatusDTO.getStatus());
			serviceStatusPO.setName(serviceStatusDTO.getName());
			serviceStatusPO.setNote(serviceStatusDTO.getNote());

			int x = serviceStatusMapper.insert(serviceStatusPO);
			if (x != 1) {
				log.error("添加产品状态节点失败！");
				return new DTO(RetCodeEnum.FAIL);
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO addProductStatusConverInfo(ServiceStatusConvertDTO serviceStatusConvertDTO1) {
		List<ServiceStatusDTO> serviceStatusDTOS = serviceStatusConvertDTO1.getServiceStatusDTOS();
		List<ServiceStatusConvertDTO> serviceStatusConvertDTOS = serviceStatusConvertDTO1.getServiceStatusConvertDTOS();

		if (serviceStatusDTOS.size() > 0) {
			ServiceStatusDTO dto = serviceStatusDTOS.get(0);
			if (dto.getServiceNo() != null) {
				serviceStatusMapper.deleteByProductNo(dto.getServiceNo());
			}
		}

		for (ServiceStatusDTO serviceStatusDTO : serviceStatusDTOS) {
			if (serviceStatusDTO == null || serviceStatusDTO.getServiceNo() == null) {
				log.error("添加产品状态节点失败！产品编号为空！");
				return new DTO(RetCodeEnum.FAIL);
			}
			ServiceStatusPO serviceStatusPO = new ServiceStatusPO();
			serviceStatusPO.setServiceNo(serviceStatusDTO.getServiceNo());
			serviceStatusPO.setStatus(serviceStatusDTO.getStatus());
			serviceStatusPO.setName(serviceStatusDTO.getName());
			serviceStatusPO.setNote(serviceStatusDTO.getNote());

			int x = serviceStatusMapper.insert(serviceStatusPO);
			if (x != 1) {
				log.error("添加产品状态节点失败！");
				return new DTO(RetCodeEnum.FAIL);
			}
		}

		if (serviceStatusConvertDTOS.size() > 0) {
			ServiceStatusConvertDTO dto = serviceStatusConvertDTOS.get(0);
			if (dto.getServiceNo() != null) {
				serviceStatusConvertsMapper.deleteByProductNo(dto.getServiceNo());
			}
		}

		int maxId = 1;
		for (ServiceStatusConvertDTO serviceStatusConvertDTO : serviceStatusConvertDTOS) {
			if (serviceStatusConvertDTO == null || serviceStatusConvertDTO.getServiceNo() == null) {
				log.error("添加产品状态节点顺序失败！产品编号为空！");
				return new DTO(RetCodeEnum.FAIL);
			}
			ServiceStatusConvertPO serviceStatusConvertPO = new ServiceStatusConvertPO();
			int id = serviceStatusConvertDTO.getServiceNo();
			serviceStatusConvertPO.setId(String.valueOf(id * 100 + maxId));
			serviceStatusConvertPO.setServiceNo(serviceStatusConvertDTO.getServiceNo());
			serviceStatusConvertPO.setCurStatus(serviceStatusConvertDTO.getCurStatus());
			serviceStatusConvertPO.setOperateType(serviceStatusConvertDTO.getOperateType());
			//serviceStatusConvertPO.setNextStatus(serviceStatusConvertDTO.getNextStatus());
			serviceStatusConvertPO.setDescription(serviceStatusConvertDTO.getDescription());
			serviceStatusConvertPO.setModuleNo(serviceStatusConvertDTO.getModuleNo());

			int x = serviceStatusConvertsMapper.insert(serviceStatusConvertPO);
			if (x != 1) {
				log.error("添加产品状态节点顺序失败！");
				return new DTO(RetCodeEnum.FAIL);
			}
			maxId++;
		}

		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO addProductGoodsInfo(List<ServiceProductDTO> serviceProductDTOS) {
		if (serviceProductDTOS.size() > 0) {
			ServiceProductDTO dto = serviceProductDTOS.get(0);
			if (dto.getProductNo() != null) {
				serviceProductPropertyMapper.deleteByserviceNo(dto.getServiceNo());
				serviceProductMapper.deleteByProductNo(dto.getServiceNo());
			}
		}

		for (ServiceProductDTO serviceProductDTO : serviceProductDTOS) {
			if (serviceProductDTO == null || serviceProductDTO.getProductNo() == null) {
				log.error("添加产品流转物品失败！产品编号为空！");
				return new DTO(RetCodeEnum.FAIL);
			}
			ServiceProductPO serviceProductPO = new ServiceProductPO();
			serviceProductPO.setServiceNo(serviceProductDTO.getServiceNo());
			serviceProductPO.setProductNo(serviceProductDTO.getProductNo());
//			serviceProductPO.setPropertyList(serviceProductDTO.getPropertyList());
			serviceProductPO.setDirection(serviceProductDTO.getDirection());

			int x = serviceProductMapper.insert(serviceProductPO);
			if (x != 1) {
				log.error("添加产品流转物品失败失败！");
				return new DTO(RetCodeEnum.FAIL);
			}
			//todo
			List<String> propertyList = serviceProductDTO.getPropertyList();
			for (String ID : propertyList) {
				ServiceProductPropertyPO serviceProductPropertyPO = new ServiceProductPropertyPO();
				serviceProductPropertyPO.setServiceNo(serviceProductDTO.getServiceNo());
				serviceProductPropertyPO.setProductNo(serviceProductDTO.getProductNo());
				serviceProductPropertyPO.setDirection(serviceProductDTO.getDirection());
				serviceProductPropertyPO.setPropertyValueID(ID);
				serviceProductPropertyMapper.insert(serviceProductPropertyPO);
			}

		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public ListDTO<ServiceStatusExpandDTO> qryProductStatusExpand(String operateType) {
		ListDTO listDTO = new ListDTO(RetCodeEnum.SUCCEED);
		List<ServiceStatusExpandDTO> serviceStatusExpandDTOList = serviceMapper.qryProductStatusExpand(operateType);
		listDTO.addAll(serviceStatusExpandDTOList);
		return listDTO;
	}

	@Override
	public ListDTO<ServiceStatusDTO> qryServiceStatus(String serviceNo) {
		ListDTO listDTO = new ListDTO(RetCodeEnum.SUCCEED);
		List<ServiceStatusDTO> statusDTOList = serviceMapper.qryProductServiceStatus(serviceNo);
		listDTO.addAll(statusDTOList);
		return listDTO;
	}

	/**
	 * 查询个性可用产品
	 */
	@Override
	public PageDTO<ServiceDTO> qryProductForUse(Map<String, Object> paramMap) {
		PageDTO<ServiceDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = serviceMapper.qryProductForUseTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		List<ServiceDO> doList = serviceMapper.qryProductForUseByPage(paramMap);

		List<ServiceDTO> dtoList = ServiceConverter.INSTANCE.do2dto(doList);

		pageDTO.setRetList(dtoList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public Integer qryNextByOperateType(String operateType) {
		return serviceMapper.qryNextByOperateType(operateType);
	}

	@Override
	public String qryNameByOperateType(String operateType) {
		return serviceMapper.qryNameByOperateType(operateType);
	}

	@Override
	public int qryExist(Map<String, Object> map) {
		return serviceMapper.qryExist(map);
	}

	@Override
	public Integer isStatusConvertMatch(Integer taskType, Integer curStatus, String operateType) {
		return serviceStatusConvertsMapper.isStatusConvertMatch(taskType, curStatus, operateType);
	}

	@Override
	public String mayStatusConvert(Integer nextStatus, Integer serviceNo) {
		return serviceStatusConvertsMapper.mayStatusConvert(nextStatus, serviceNo);
	}

	@Override
	public List<String> mayStatusConverts(String operateType, Integer serviceNo) {
		return serviceStatusConvertsMapper.mayStatusConverts(operateType, serviceNo);
	}

	@Override
	public String getJSONTemplate(int serviceNo, String operateType) {
		return serviceStatusConvertsMapper.getJSONTemplate(serviceNo, operateType);
	}

	@Override
	public List<String> selectCurStatus(Integer taskType, String operateType) {
		return serviceStatusConvertsMapper.selectCurStatus(taskType, operateType);
	}

	@Override
	public Boolean isConvert(String taskNo, String operateType, Set set) {
		Boolean b = false;
		//查询出所需参数
		TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
		int taskType = taskTableDTO.getTaskType();
		int curstatus = taskTableDTO.getStatus();
		Integer nextStatus = serviceMapper.qryNextByOperateType(operateType);

		//构造有向图
		//所需节点参数
		List<ServiceStatusPO> serviceStatusPOList = serviceStatusMapper.qryByServiceNo(taskType);
		//所需边参数
		List<ServiceStatusConvert> serviceStatusConvertList = serviceStatusConvertsMapper.qryByServiceNo(taskType);

		int vertex_num = serviceStatusPOList.size();
		int edge_num = serviceStatusConvertList.size();

		//构建有向图
		Graph graph = new Graph();
		graph.setVertex_num(vertex_num);
		graph.setEdge_num(edge_num);
		creatGraph(graph, serviceStatusPOList, serviceStatusConvertList);

		//遍历两节点之间是否可以到达，包含跳过弱节点
		//中间变量
		Set<Object> tempSet = new HashSet<Object>();
		//跳过的节点
		if (set == null) {
			set = new LinkedHashSet();
		}
		b = search(graph, curstatus, operateType,set,tempSet);
		return b;
	}

//	@Override
//	public int isStatusConvert(String taskNo, String operateType) {
//		//查询出所需参数
//		TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
//		int taskType = taskTableDTO.getTaskType();
//		int curStatus = taskTableDTO.getStatus();
//
//		Integer statusConvertMatch = serviceStatusConvertsMapper.isStatusConvertMatch(taskType, curStatus, operateType);
//		if(statusConvertMatch > 0 ){
//			//可直接转换，没有弱节点
//			return 0;
//		}
//
//		//所有转换关系
//		List<ServiceStatusConvert> serviceStatusConvertList = serviceStatusConvertsMapper.qryByServiceNo(taskType);
//		//中间变量
//		Set<String>	set = new LinkedHashSet<String>();
//		Boolean b = searchStatus(serviceStatusConvertList,curStatus,operateType,set);
//		if (b) {
//			//中间存在弱节点，并可跳过执行操作
//			return 1;
//		}else {
//			//不可操作
//			return  -1;
//		}
//	}
//
//	private static boolean searchStatus(List<ServiceStatusConvert> serviceStatusConvertList, int status, String operateType , Set<String> set) {
//
//		for (int i = 0; i < serviceStatusConvertList.size(); i++) {
//			//取出一条数据
//			ServiceStatusConvert ServiceStatusConvert = serviceStatusConvertList.get(i);
//			if (status == ServiceStatusConvert.getCurStatus() && operateType.equals(ServiceStatusConvert.getOperateType())){
//				return true;
//			}else if(1 == ServiceStatusConvert.getWeakNode()){
//				//集合中不存在该状态才进行递归
//				if(! set.contains(ServiceStatusConvert.getOperateType())){
//					set.add(ServiceStatusConvert.getOperateType());
//					boolean b = searchStatus(serviceStatusConvertList, ServiceStatusConvert.getNextStatus(), operateType,set);
//					if (b == true){
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}

	private static void creatGraph(Graph graph, List<ServiceStatusPO> serviceStatusPOList, List<ServiceStatusConvert> serviceStatusConvertList) {
		//记录节点信息
		Node[] nodeList = new Node[serviceStatusPOList.size()];
		Node node = null;
		for (int i = 0; i < serviceStatusPOList.size(); i++) {
			ServiceStatusPO serviceStatusPO = serviceStatusPOList.get(i);
			node = new Node();
			node.setNodeName(serviceStatusPO.getStatus());
			node.setOperateType(serviceStatusPO.getOperateType());
			node.setFirst_edge(null);
			nodeList[i] = node;
		}
		graph.setNodeList(nodeList);

		//记录边信息
		for (int i = 0; i < serviceStatusConvertList.size(); i++) {
			ServiceStatusConvert ServiceStatusConvert = serviceStatusConvertList.get(i);
			Integer curStatus = ServiceStatusConvert.getCurStatus();
			Integer nextStatus = ServiceStatusConvert.getNextStatus();

			EdgeNode p = new EdgeNode();
			int v1 = locateVex(graph, curStatus);
			int v2 = locateVex(graph, nextStatus);

			//当前状态的那个节点
			Node n = graph.getNodeList()[v1];

			//新构造的一条边
			p.setIndex(v2);
			p.setWeakNode(ServiceStatusConvert.getWeakNode());
			p.setOperateType(ServiceStatusConvert.getOperateType());
			EdgeNode edgeNode = n.getFirst_edge();

			p.setEdgeNode(edgeNode);
			n.setFirst_edge(p);
		}

	}

	private static int locateVex(Graph graph, Integer s) {
		for (int i = 0; i < graph.getVertex_num(); i++) {
			Node[] nodeList = graph.getNodeList();
			if(nodeList[i].getNodeName().equals(s)){
				return i;
			}
		}
		return -1;
	}

	private static Boolean search(Graph graph, int num1, String operateType,Set<Object> set,Set<Object> tempSet) {

		EdgeNode p = new EdgeNode();
		for (int i = 0; i < graph.getVertex_num(); i++) {

			Node[] nodeList = graph.getNodeList();
			//取出N开始位置相同的节点
			Integer nodeName = nodeList[i].getNodeName();
			if(nodeName == num1){

				//取出开始节点下的第一个节点P
				p = nodeList[i].getFirst_edge();

				//只要P不为空
				while (p != null) {

					//取出节点的操作类型是否为目标操作
					Node node = nodeList[p.getIndex()];
					String operateType1 = node.getOperateType();

					//如果目标操作相同
					if (operateType.equals(operateType1)){
						return true;
						//如果是弱操作
					} else if(p.getWeakNode() == 1){

						//递归
						if(! tempSet.contains(node.getOperateType())){
							tempSet.add(node.getOperateType());
							Boolean aBoolean = search(graph,node.getNodeName(),operateType,set,tempSet);
							if(aBoolean){
								set.add(node.getOperateType());
								return true;
							}
						}

					}
					//取出下一个节点
					p = p.getEdgeNode();
				}
			}
		}
		return false;
	}

}



