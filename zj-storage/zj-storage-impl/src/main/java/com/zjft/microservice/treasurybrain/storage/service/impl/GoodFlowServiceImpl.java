package com.zjft.microservice.treasurybrain.storage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferDetailDO;
import com.zjft.microservice.treasurybrain.storage.domain.StorageSortedTransferTableDO;
import com.zjft.microservice.treasurybrain.storage.dto.*;
import com.zjft.microservice.treasurybrain.storage.mapstruct.StorageEntityPropertyConverter;
import com.zjft.microservice.treasurybrain.storage.mapstruct.StorageSortedTransferConverter;
import com.zjft.microservice.treasurybrain.storage.mapstruct.StorageSortedTransferEntityConverter;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityPropertyPO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityTablePO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTransferEntityPO;
import com.zjft.microservice.treasurybrain.storage.po.StorageTransferTablePO;
import com.zjft.microservice.treasurybrain.storage.repository.*;
import com.zjft.microservice.treasurybrain.storage.service.GoodFlowService;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityTransferDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
@Slf4j
@Service
public class GoodFlowServiceImpl implements GoodFlowService {

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private StorageTransferTableMapper storageTransferTableMapper;

	@Resource
	private StorageTransferEntityMapper storageTransferEntityMapper;

	@Resource
	StorageEntityTableMapper storageEntityTableMapper;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private GoodManageMapper goodManageMapper;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private StorageEntityPropertyMapper storageEntityPropertyMapper;


	@Override
	public List<String> getGoodsByTaskNo(String taskNo) {
		return taskEntityInnerResource.getGoodsByTaskNo(taskNo);
	}

	@Override
	public StorageEntityTransferDTO getStorageEntityByShelfNo(String shelfNo) {
		StorageEntityTransferDTO dto = new StorageEntityTransferDTO(RetCodeEnum.SUCCEED);
		//根据笼车编号查询对应的任务单编号，线路编号
		List<StorageUseEntityTransferDTO> storageEntityTransferDOS = taskInnerResource.getLine(shelfNo);
		List<String> taskList = taskInnerResource.getTaskListByShelfNo(shelfNo);
		if (storageEntityTransferDOS == null || storageEntityTransferDOS.size() < 1 || taskList.size() < 1) {
			dto.setResult(RetCodeEnum.FAIL);
			dto.setRetMsg("未匹配到该笼车编号下的待出库任务单");
			return dto;
		}
		if (storageEntityTransferDOS.size() > 1) {
			dto.setResult(RetCodeEnum.FAIL);
			dto.setRetMsg("该笼车编号下对应有多条线路");
			return dto;
		}
		//根据任务单编号找到对应的实物列表
		List<String> goods = new ArrayList<>();
		for (String taskNo : taskList) {
			if (StringUtil.isNullorEmpty(taskNo)) {
				dto.setResult(RetCodeEnum.FAIL);
				dto.setRetMsg("该笼车编号下的待出库任务单编号为空");
				return dto;
			}
			List<String> goodList = taskEntityInnerResource.getGoodsByTaskNo(taskNo);
			goods.addAll(goodList);
		}

		dto.setShelfNo(shelfNo);
		dto.setLineNo(storageEntityTransferDOS.get(0).getLineNo());
		dto.setLineName(storageEntityTransferDOS.get(0).getLineName());
		dto.setEntityNos(goods);
		return dto;
	}

	/**
	 * 库房调入
	 * 1.校验参数和笼车信息
	 * 2.插入库房调拨记录
	 * 3.删除库房物品信息
	 *
	 * @param storageSortedTransferDTO T
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO StorageInnerIn(StorageSortedTransferDTO storageSortedTransferDTO) throws Exception {
		log.debug("-----------[GoodFlowServiceImpl:StorageInnerIn]start------------");
		/**
		 * 新增库房出入库总记录
		 */
		StorageTransferTablePO storageTransferTablePO =
				StorageSortedTransferConverter.INSTANCE.dto2po(storageSortedTransferDTO);
		storageTransferTablePO.setRecordNo(java.util.UUID.randomUUID().toString().replace("-", ""));
		storageTransferTablePO.setTransferDate(CalendarUtil.getSysTimeYMD());
		storageTransferTablePO.setTransferTime(CalendarUtil.getSysTimeHMS());
		storageTransferTablePO.setDirection(StatusEnum.StorageSortedTransferDirection.IN.getDirection());

		int insertRecordFlag = storageTransferTableMapper.insert(storageTransferTablePO);
		if (0 == insertRecordFlag) {
			log.error("插入新的库房入库记录失败");
			return new DTO(RetCodeEnum.FAIL.getCode(), "新增库房入库记录失败");
		}

		for (StorageSortedTransferEntityDTO storageSortedTransferEntityDTO : storageSortedTransferDTO.getStorageEntities()) {
			/**
			 * 新增调拨详情记录
			 */
			StorageTransferEntityPO storageTransferEntityPO =
					StorageSortedTransferEntityConverter.INSTANCE.dto2po(storageSortedTransferEntityDTO);
			storageTransferEntityPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageTransferEntityPO.setRecordNo(storageTransferTablePO.getRecordNo());
			/*storageTransferEntityPO.setCurrencyType(storageSortedEntityMoneyDTO.getCurrencyType());
			storageTransferEntityPO.setCurrencyCode(storageSortedEntityMoneyDTO.getCurrencyCode());
			storageTransferEntityPO.setDenomination(storageSortedEntityMoneyDTO.getDenomination());*/
			storageTransferEntityPO.setAmount(storageSortedTransferEntityDTO.getAmount());
			storageTransferEntityMapper.insert(storageTransferEntityPO);

			/*插入物品信息:1.清空物品金额信息 2.插入或更新新的金额信息*/
			StorageEntityTablePO storageEntityTablePO =
					StorageSortedTransferEntityConverter.INSTANCE.transferEntityPo2EntityPo(storageTransferEntityPO);
			storageEntityTablePO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageEntityTablePO.setClrCenterNo(storageTransferTablePO.getClrCenterNo());
			storageEntityTablePO.setTime(CalendarUtil.getSysTimeYMDHMS());
			storageEntityTablePO.setInOutFlag(StatusEnum.StorageSortedEntityLoaction.IN.getLocationType());

			/**
			 * 1.清空物品金额信息
			 */
			storageEntityTableMapper.inClear(storageEntityTablePO);
			/**
			 * 2.插入或更新新的金额信息
			 */
			storageEntityTableMapper.insertOrUpdate(storageEntityTablePO);

			for (StorageEntityPropertyDTO storageEntityPropertyDTO : storageSortedTransferEntityDTO.getPropertyInfos()) {

				//插入属性表
				StorageEntityPropertyPO storageEntityPropertyPO = new StorageEntityPropertyPO();
				int propertyType = storageEntityPropertyDTO.getPropertyType();
				//区分库房和调拨的属性
				if (propertyType==1){
					storageEntityPropertyPO.setId(storageTransferEntityPO.getId());
				}else {
					storageEntityPropertyPO.setId(storageEntityTablePO.getId());
				}
				storageEntityPropertyPO.setPropertyType(propertyType);
				storageEntityPropertyPO.setKey(storageEntityPropertyDTO.getKey());
				storageEntityPropertyPO.setName(storageEntityPropertyDTO.getName());
				storageEntityPropertyPO.setValue(storageEntityPropertyDTO.getValue());
				storageEntityPropertyMapper.insert(storageEntityPropertyPO);

			}
		}
		log.debug("-----------[GoodFlowServiceImpl:StorageInnerIn]end------------");
		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 库房调出
	 * 1.插入库房调拨记录
	 * 2.更新库房物品信息出库
	 *
	 * @param storageSortedTransferDTO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO StorageInnerOut(StorageSortedTransferDTO storageSortedTransferDTO) throws Exception {
		log.debug("-----------[GoodFlowServiceImpl:StorageInnerOut]start------------");
		/*新增库房出入库总记录*/
		StorageTransferTablePO storageTransferTablePO = StorageSortedTransferConverter.INSTANCE.dto2po(storageSortedTransferDTO);
		storageTransferTablePO.setRecordNo(java.util.UUID.randomUUID().toString().replace("-", ""));
		storageTransferTablePO.setTransferDate(CalendarUtil.getSysTimeYMD());
		storageTransferTablePO.setTransferTime(CalendarUtil.getSysTimeHMS());
		storageTransferTablePO.setDirection(StatusEnum.StorageSortedTransferDirection.OUT.getDirection());

		int insertRecordFlag = storageTransferTableMapper.insert(storageTransferTablePO);
		if (0 == insertRecordFlag) {
			log.error("插入新的库房出库记录失败");
			return new DTO(RetCodeEnum.FAIL.getCode(), "新增库房出库记录失败");
		}
		for (StorageSortedTransferEntityDTO storageSortedTransferEntityDTO : storageSortedTransferDTO.getStorageEntities()) {
			/**
			 * 新增调拨详情记录
			 */
			StorageTransferEntityPO storageTransferEntityPO =
					StorageSortedTransferEntityConverter.INSTANCE.dto2po(storageSortedTransferEntityDTO);
			storageTransferEntityPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageTransferEntityPO.setRecordNo(storageTransferTablePO.getRecordNo());
				/*storageTransferEntityPO.setCurrencyType(storageSortedEntityMoneyDTO.getCurrencyType());
				storageTransferEntityPO.setCurrencyCode(storageSortedEntityMoneyDTO.getCurrencyCode());
				storageTransferEntityPO.setDenomination(storageSortedEntityMoneyDTO.getDenomination());*/
			storageTransferEntityPO.setAmount(storageSortedTransferEntityDTO.getAmount());
			storageTransferEntityMapper.insert(storageTransferEntityPO);

			/*删除库房物品*/
			StorageEntityTablePO storageEntityTablePO =
					StorageSortedTransferEntityConverter.INSTANCE.transferEntityPo2EntityPo(storageTransferEntityPO);
			storageEntityTablePO.setClrCenterNo(storageTransferTablePO.getClrCenterNo());
			storageEntityTablePO.setTime(CalendarUtil.getSysTimeYMDHMS());
			storageEntityTablePO.setInOutFlag(StatusEnum.StorageSortedEntityLoaction.OUT.getLocationType());
			storageEntityTableMapper.outClear(storageEntityTablePO);

			for (StorageEntityPropertyDTO storageEntityPropertyDTO : storageSortedTransferEntityDTO.getPropertyInfos()) {

				//插入属性表
				StorageEntityPropertyPO storageEntityPropertyPO = new StorageEntityPropertyPO();
				int propertyType = storageEntityPropertyDTO.getPropertyType();
				//区分库房和调拨的属性
				if (propertyType==1){
					storageEntityPropertyPO.setId(storageTransferEntityPO.getId());
				}else {
					storageEntityPropertyPO.setId(storageEntityTablePO.getId());
				}
				storageEntityPropertyPO.setPropertyType(propertyType);
				storageEntityPropertyPO.setKey(storageEntityPropertyDTO.getKey());
				storageEntityPropertyPO.setName(storageEntityPropertyDTO.getName());
				storageEntityPropertyPO.setValue(storageEntityPropertyDTO.getValue());
				storageEntityPropertyMapper.insert(storageEntityPropertyPO);

			}

		}
		log.debug("-----------[GoodFlowServiceImpl:StorageInnerOut]end------------");

		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 分页查询调入调出记录
	 *
	 * @param paramMap
	 * @return
	 */
	@Override
	public PageDTO<StorageSortedTransferDTO> qryInnerTransferByPage(Map<String, Object> paramMap) {

		PageDTO<StorageSortedTransferDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = storageTransferTableMapper.qryTotalRowForPage(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<StorageSortedTransferTableDO> storageSortedTransferDOS = storageTransferTableMapper.qryByPage(paramMap);
		List<StorageSortedTransferDTO> storageSortedTransferDTOS =
				StorageSortedTransferConverter.INSTANCE.dos2dtos(storageSortedTransferDOS);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setRetList(storageSortedTransferDTOS);

		return pageDTO;
	}

	/**
	 * 查询调入调出详情
	 * 1.查询流转中的容器信息
	 * 2.根据容器信息查询流转中容器的现金信息
	 *
	 * @param recordNo
	 * @return
	 */
	@Override
	public ListDTO<StorageSortedTransferEntityDTO> qryInnerTransferDetail(String recordNo) {

		/**
		 * 查询任务流转中的容器
		 */
		List<StorageSortedTransferDetailDO> storageSortedTransferDetailDOS = storageTransferEntityMapper.qryContainerNosInRecord(recordNo);
		List<StorageSortedTransferEntityDTO> storageSortedTransferEntityDTOS = new ArrayList<>();

		/**
		 * 遍历容器
		 */
		for (StorageSortedTransferDetailDO storageSortedTransferDetailDO : storageSortedTransferDetailDOS) {
			StorageTransferEntityPO storageTransferEntityPO = new StorageTransferEntityPO();
			storageTransferEntityPO.setRecordNo(recordNo);
			storageTransferEntityPO.setContainerNo(storageSortedTransferDetailDO.getContainerNo());

			/**
			 * 查询现金信息
			 */
			List<StorageSortedTransferDetailDO> storageSortedTransferDetailDOS1 =
					storageTransferEntityMapper.qryByRecordNoAndContainerNo(storageTransferEntityPO);
			/**
			 * 组成响应信息
			 */
			StorageSortedTransferEntityDTO storageSortedTransferEntityDTO =
					StorageSortedTransferEntityConverter.INSTANCE.do2dto(storageSortedTransferDetailDO);
			/*List<StorageSortedEntityMoneyDTO> moneyInfos = new ArrayList<>();
			*//**
			 * 遍历容器现金信息
			 *//*
			for (StorageSortedTransferDetailDO storageSortedTransferDetailDO1 : storageSortedTransferDetailDOS1) {
				String id = storageSortedTransferDetailDO1.getId();


				StorageSortedEntityMoneyDTO storageSortedEntityMoneyDTO = new StorageSortedEntityMoneyDTO();
				storageSortedEntityMoneyDTO.setCurrencyType(storageSortedTransferDetailDO1.getCurrencyType());
				storageSortedEntityMoneyDTO.setCurrencyCode(storageSortedTransferDetailDO1.getCurrencyCode());
				storageSortedEntityMoneyDTO.setDenomination(storageSortedTransferDetailDO1.getDenomination());
				storageSortedEntityMoneyDTO.setAmount(storageSortedTransferDetailDO1.getAmount());
				moneyInfos.add(storageSortedEntityMoneyDTO);
			}
			storageSortedTransferEntityDTO.setMoneyInfos(moneyInfos);*/

			List<StorageEntityPropertyDTO> propertyInfos = new ArrayList<>();
			for (StorageSortedTransferDetailDO storageSortedTransferDetailDO1 : storageSortedTransferDetailDOS1) {
				String id = storageSortedTransferDetailDO1.getId();
				List<StorageEntityPropertyPO> propertyInfo = storageEntityPropertyMapper.qryEntityDetailById(id);
				propertyInfos = StorageEntityPropertyConverter.INSTANCE.po2dto(propertyInfo);
			}
			storageSortedTransferEntityDTO.setPropertyInfos(propertyInfos);
			storageSortedTransferEntityDTOS.add(storageSortedTransferEntityDTO);
		}

		ListDTO<StorageSortedTransferEntityDTO> retList = new ListDTO<>(RetCodeEnum.SUCCEED);
		retList.setRetList(storageSortedTransferEntityDTOS);
		return retList;
	}

	@Override
	public DTO accessCheck(StorageEntityCheckDTO storageEntityCheckDTO) {
		String string = StringUtil.parseString(JSONObject.toJSON(storageEntityCheckDTO));
		log.debug("推送消息：" + string);
		return sendInfoInnerResource.sendInfo2All(string);
	}

}
