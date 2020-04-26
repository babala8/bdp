package com.zjft.microservice.treasurybrain.tauro.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.NetPointMatrixInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.ContainerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTauroDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskDetailInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.tauro.domain.TagReaderCoordsInfoDO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderCoordsDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDetailDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TauroCustomerInfoDTO;
import com.zjft.microservice.treasurybrain.tauro.mapstruct.TagReaderCoordsConverter;
import com.zjft.microservice.treasurybrain.tauro.mapstruct.TaskProcessConverter;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderCoordsInfoPO;
import com.zjft.microservice.treasurybrain.tauro.repository.TagReaderCoordsMapper;
import com.zjft.microservice.treasurybrain.tauro.service.TaskProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TaskProcessServiceImpl implements TaskProcessService {

	@Resource
	private TagReaderCoordsMapper tagReaderCoordsMapper;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private NetPointMatrixInnerResource netPointMatrixInnerResource;

	@Override
	public PageDTO<TaskProcessDTO> queryDispatchByPage(Map<String, Object> paramMap) {
		log.info("------------[queryDispatchByPage]TaskProcessService-------------");
		PageDTO<TaskProcessDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

//		int totalRow = taskProcessMapper.qryTotalRow(paramMap);
		int totalRow = taskInnerResource.qryTotalRowTauro(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<TaskTauroDTO> doList = taskInnerResource.qryDispatchByPage(paramMap);
		List<TaskProcessDTO> dtoList = TaskProcessConverter.INSTANCE.do2dto(doList);

		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public TaskProcessDetailDTO queryDispatchDetail(String taskNo) {
		log.info("------------[queryDispatchDetail]TaskProcessService-------------");
		TaskProcessDetailDTO taskProcessDetailDTO = new TaskProcessDetailDTO();
		//先查出每一个taskNo下对应的 Customer（ATM机）的信息
		List<CustomerInfoDTO> customerInfoDTOS = taskDetailInfoInnerResource.qryCustomerInfo(taskNo);
		//再查出每一个 Customer 下对应的 Container （钞箱钞袋）的信息
		//用来记录上一台加钞设备的编号
		String upperCustomerNo = "";
		for (CustomerInfoDTO customerInfoDTO : customerInfoDTOS) {
			//使用Optional类进行空判断
			String customerNo = Optional.ofNullable(customerInfoDTO).map(CustomerInfoDTO::getCustomerNo).orElse("");
			List<ContainerInfoDTO> containerInfoDTOS = taskEntityInnerResource.qryContainerInfo(taskNo, customerNo);
			if (customerInfoDTO != null) {
				customerInfoDTO.setContainerInfoDTOList(containerInfoDTOS);
				//如果是第一台设备
				if (Optional.ofNullable(customerInfoDTO).map(CustomerInfoDTO::getSortNo).orElse("").equals("1")) {
					customerInfoDTO.setTimeCost(String.valueOf(10));
					upperCustomerNo = customerNo;
				} else {
//					NetPointMatrix netPointMatrix=new NetPointMatrix();
//					netPointMatrix.setStartPointNo(upperCustomerNo);
//					netPointMatrix.setEndPointNo(customerNo);
//					netPointMatrix.setType(0);
//					netPointMatrix.setTactic(11);
//					NetPointMatrix result = taskNetPointMatrixMapper.selectByPrimaryKey(netPointMatrix);
					Map<String, Object> netPointMatrixMap = new HashMap<>();
					netPointMatrixMap.put("startPointNo", upperCustomerNo);
					netPointMatrixMap.put("endPointNo", customerNo);
					netPointMatrixMap.put("type", 0);
					netPointMatrixMap.put("tactic", 11);
					NetpointMatrixDTO netPointMatrixDTO = netPointMatrixInnerResource.selectByPrimaryKeyMap(netPointMatrixMap);
					customerInfoDTO.setTimeCost(StringUtil.parseString(netPointMatrixDTO.getTimeCost()));
					upperCustomerNo = customerNo;
				}
			}
		}
		List<TauroCustomerInfoDTO> tauroCustomerInfoDTOList = TaskProcessConverter.INSTANCE.do2dto2(customerInfoDTOS);
		taskProcessDetailDTO.setTauroCustomerInfoDTOList(tauroCustomerInfoDTOList);
		taskProcessDetailDTO.setResult(RetCodeEnum.SUCCEED);
		return taskProcessDetailDTO;
	}

	@Override
	public DTO addTagReaderCoordsInfo(TagReaderCoordsDTO tagReaderCoordsDTO) {
		log.info("------------[addTagReaderCoordsInfo]TaskProcessService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		//使用人员、日期、时间设置为当前操作用户及操作日期、时间
		tagReaderCoordsDTO.setUserNo(ServletRequestUtil.getUsername());
		tagReaderCoordsDTO.setRdDate(CalendarUtil.getSysTimeYMD());
		tagReaderCoordsDTO.setRdTime(CalendarUtil.getSysTimeHMS());
		TagReaderCoordsInfoPO tagReaderCoordsInfoPO = TagReaderCoordsConverter.INSTANCE.dto2po(tagReaderCoordsDTO);
		//新增类型0-定时上送位置信息
		if (null == tagReaderCoordsInfoPO.getTaskType()) {
			tagReaderCoordsInfoPO.setTaskType(0);
		}

		int insert = tagReaderCoordsMapper.addTagReaderCoordsInfo(tagReaderCoordsInfoPO);

		if (insert == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	@Override
	public ListDTO<TagReaderCoordsDTO> queryCoordsByTaskNo(String taskNo) {
		log.info("------------[queryCoordsByTaskNo]TaskProcessService-------------");
		ListDTO listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		List<TagReaderCoordsInfoDO> doList = tagReaderCoordsMapper.queryCoordsByTaskNo(taskNo);
		List<TagReaderCoordsDTO> dtoList = TagReaderCoordsConverter.INSTANCE.domain2dto(doList);
		listDTO.setRetList(dtoList);
		return listDTO;
	}
}
