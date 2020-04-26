package com.zjft.microservice.treasurybrain.storage.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.storage.domain.*;
import com.zjft.microservice.treasurybrain.storage.dto.TransferTaskDetailDTO;
import com.zjft.microservice.treasurybrain.storage.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.storage.dto.*;
import com.zjft.microservice.treasurybrain.storage.mapstruct.*;
import com.zjft.microservice.treasurybrain.storage.po.StorageCheckPO;
import com.zjft.microservice.treasurybrain.storage.po.StorageEntityPropertyPO;
import com.zjft.microservice.treasurybrain.storage.repository.GoodManageMapper;
import com.zjft.microservice.treasurybrain.storage.repository.StorageEntityPropertyMapper;
import com.zjft.microservice.treasurybrain.storage.repository.StorageEntityTableMapper;
import com.zjft.microservice.treasurybrain.storage.service.GoodManageService;
import com.zjft.microservice.treasurybrain.storage.web.ShelfResource;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityDetailInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskEntityInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
@Slf4j
@Service
public class GoodManageServiceImpl implements GoodManageService {

	@Resource
	private GoodManageMapper goodManageMapper;


	@Resource
	private StorageEntityTableMapper storageEntityTableMapper;


	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private ShelfResource shelfResource;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskEntityDetailInnerResource taskEntityDetailInnerResource;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Resource
	private StorageEntityPropertyMapper storageEntityPropertyMapper;

	@Override
	public PageDTO<StorageEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap) {
		PageDTO<StorageEntityDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = taskInnerResource.qryTotalRowStorage(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<StorageUseEntityDTO> list = taskInnerResource.qryStorageEntityByPage(paramMap);
		List<StorageEntityDTO> storageList = GoodManageConverter.INSTANCE.dto2dto(list);

		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setRetList(storageList);
		return pageDTO;
	}

	@Override
	public StorageEntityDetailDTO qryStorageEntityDetail(String taskNo) {
		StorageEntityDetailDTO dto = new StorageEntityDetailDTO(RetCodeEnum.SUCCEED);
		//查询库存基本信息
		StorageUseEntityDetailDTO storageUseEntityDetailDTO = taskInnerResource.getStorageInfo(taskNo);
		StorageEntityDetailDO storageEntityDetailDO = GoodManageConverter.INSTANCE.dto2do(storageUseEntityDetailDTO);

		//查询对应的设备信息
		String devNo = taskInnerResource.getDev(taskNo);
		if (StringUtil.isNullorEmpty(devNo)) {
			return dto;
		}
		//设备对象（包含钞袋信息）
		StorageDevDO storageDevDO = new StorageDevDO();
		storageDevDO.setDevNo(devNo);
		//查询设备下的钞袋信息
		List<StorageUseCassetteBagDTO> dtoList = taskEntityInnerResource.getCassetteBagList(taskNo, devNo);
		List<StorageCassetteBagDO> cassetteBagList = StorageCassetteBagConverter.INSTANCE.dto2do(dtoList);
		BigDecimal amountDev = new BigDecimal(0);
		if (cassetteBagList.size() == 0 || cassetteBagList == null) {
			return dto;
		}
		//遍历钞袋
		for (StorageCassetteBagDO storageCassetteBagDO : cassetteBagList) {
			//通过钞袋寻找钞箱
			String cassetteBagNo = storageCassetteBagDO.getCassetteBagNo();
			List<StorageUseCassetteDTO> cassetteList = taskEntityInnerResource.getCassetteList(taskNo, cassetteBagNo);
			List<StorageCassetteDO> storageCassetteDOS = StorageCassetteBagConverter.INSTANCE.dto2todo(cassetteList);
			BigDecimal amountBag = new BigDecimal(0);
			//遍历钞箱并查找钞箱详情
			for (StorageCassetteDO storageCassetteDO : storageCassetteDOS) {
				String id = storageCassetteDO.getId();
				amountBag = amountBag.add(storageCassetteDO.getAmount());
				List<StorageUseCassetteBagDetailDTO> cassetteDeatil = taskEntityDetailInnerResource.getCassetteDetail(id);
				List<StorageCassetteBagDetailDO> cassetteBagDetailDOList = StorageCassetteBagConverter.INSTANCE.detailDTO2DO(cassetteDeatil);
				storageCassetteDO.setCassetteBagDetailDOList(cassetteBagDetailDOList);
			}
			storageCassetteBagDO.setCassetteNoList(storageCassetteDOS);
			storageCassetteBagDO.setAmount(amountBag);
			amountDev = amountDev.add(storageCassetteBagDO.getAmount());
		}
		storageDevDO.setAmount(amountDev);
		storageDevDO.setCassetteBagList(cassetteBagList);
		storageEntityDetailDO.setDevs(storageDevDO);

		dto = GoodManageConverter.INSTANCE.domain2dto(storageEntityDetailDO);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 分页查询
	 * 1.分页查询中的物品信息
	 * 2.查询物品对应的现金信息
	 *
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageDTO<StorageSortedEntityDTO> qryStorageInnerEntityByPage(Map<String, Object> paramMap) throws Exception {
		PageDTO<StorageSortedEntityDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		paramMap.put("inOutFlag", StatusEnum.StorageSortedEntityLoaction.IN.getLocationType());
		int totalRow = storageEntityTableMapper.qryTotalRowForPage(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		/**
		 * 1.分页查询中的物品信息
		 */
		List<StorageEntityTableDO> storageEntityTableDOS = storageEntityTableMapper.qryByPage(paramMap);
		List<StorageSortedEntityDTO> storageSortedEntityDTOS = new ArrayList<>();
		/**
		 * 2.查询物品对应的现金信息
		 */
		for (StorageEntityTableDO storageEntityTableDO : storageEntityTableDOS) {
			String id = storageEntityTableDO.getId();
			StorageSortedEntityDTO storageSortedEntityDTO = StorageSortedEntityConverter.INSTANCE.domain2dto(storageEntityTableDO);
			List<StorageEntityPropertyPO> entityDetail = storageEntityPropertyMapper.qryEntityDetailById(id);
			List<StorageEntityPropertyDTO> entityDetailDTO = StorageEntityPropertyConverter.INSTANCE.po2dto(entityDetail);
			storageSortedEntityDTO.setEntityDetail(entityDetailDTO);
			storageSortedEntityDTOS.add(storageSortedEntityDTO);
		}
		pageDTO.setRetList(storageSortedEntityDTOS);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		return pageDTO;
	}

	@Override
	public StorageEntityTransferDTO qryStorageEntityByShelfNo(String shelfNo) {
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
	 * 根据线路编号查询笼车明细（出库）
	 */
	@Override
	public Map<String, Object> qryShelfDetailByLineNo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		String lineNoList = StringUtil.parseString(paramMap.get("lineNoList"));
		String[] lineNoLists = lineNoList.split(",");
		int taskType = StringUtil.objectToInt(paramMap.get("taskType"));
		String productNo = StringUtil.parseString(paramMap.get("productNo"));
		try {
			int status = 304;
			for (int i = 0; i < lineNoLists.length; i++) {
				Map<String, Object> paramsMap = new HashMap<>();
				String lineNo = StringUtil.parseString(lineNoLists[i]);
				if (taskType == 1) {
					paramsMap.put("status", status);
					paramsMap.put("taskType", taskType);
					paramsMap.put("lineNo", lineNo);
				} else if (taskType == 2) {
					paramsMap.put("status", status);
					paramsMap.put("taskType", taskType);
					paramsMap.put("lineNo", lineNo);
					paramsMap.put("productNo", productNo);
				}
				List<Map<String, Object>> shelfNoList = taskInnerResource.qryShelfNoByLineNo(paramsMap);
				for (Map<String, Object> resultMap : shelfNoList) {
					Map<String, Object> parasMap = new HashMap<>();
					Map<String, Object> entity = new HashMap<String, Object>();
					entity.put("shelfNo", StringUtil.parseString(resultMap.get("shelfNo")));
					entity.put("lineNo", lineNo);
					String shelfNo = StringUtil.parseString(resultMap.get("shelfNo"));
					if (taskType == 1) {
						parasMap.put("status", status);
						parasMap.put("taskType", taskType);
						parasMap.put("shelfNo", shelfNo);
					} else if (taskType == 2) {
						parasMap.put("status", status);
						parasMap.put("taskType", taskType);
						parasMap.put("shelfNo", shelfNo);
						parasMap.put("productNo", productNo);
					}
					List<String> containerNoList = taskInnerResource.qryShelfDetailByShelfNo(parasMap);
					entity.put("containerNoList", containerNoList);
					retList.add(entity);
				}
			}
			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "加钞任务查询成功！");
		} catch (Exception e) {
			log.error("查询笼车明细失败", e);
		}
		return retMap;
	}

	@Override
	public PageDTO<StorageCheckDTO> qryCheckInfoByPage(Map<String, Object> paramMap) {
		PageDTO<StorageCheckDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

		int totalRow = goodManageMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<StorageCheckDO> doList = goodManageMapper.qryCheckInfoByPage(paramMap);
		List<StorageCheckDTO> dtoList = StorageCheckConverter.INSTANCE.domain2dto(doList);

		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public DTO check(CheckInventoryDTO checkInventoryDTO) {
		DTO dto = new DTO();
		String clrCenterNo = checkInventoryDTO.getClrCenterNo();
		List<CheckInventoryDetailDTO> list = checkInventoryDTO.getCheckInventoryDetailDTOList();
		BigDecimal totalMoney = new BigDecimal("0");
		for (CheckInventoryDetailDTO checkInventoryDetailDTO : list) {
			//BigDecimal denomination = checkInventoryDetailDTO.getDenomination();
			BigDecimal amount = checkInventoryDetailDTO.getAmount();
			//BigDecimal money = denomination.multiply(amount);
			totalMoney = totalMoney.add(amount);
		}

		//数据库总金额
		BigDecimal totalAmount = goodManageMapper.selectTotalMoneyByClrCenterNo(clrCenterNo);
		StorageCheckPO storageCheckPO = new StorageCheckPO();
		if (totalAmount != null) {
			if (totalAmount.compareTo(totalMoney) == 0) {
				storageCheckPO.setFlag(1);
				dto.setRetMsg("核库结果一致");
				dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			} else {
				storageCheckPO.setFlag(0);
				dto.setRetMsg("核库结果不一致");
				dto.setRetCode(RetCodeEnum.FAIL.getCode());
			}
			storageCheckPO.setNo(java.util.UUID.randomUUID().toString().replace("-", ""));
			storageCheckPO.setDatabaseRecordMoney(totalAmount);
			storageCheckPO.setStorageCheckMoney(totalMoney);
			storageCheckPO.setTime(CalendarUtil.getSysTimeYMDHMS());
			storageCheckPO.setClrCenterNo(clrCenterNo);
			int x = goodManageMapper.insertCheckInfo(storageCheckPO);
			if (x != 1) {
				log.error("生成核库记录失败！");
				return new DTO(RetCodeEnum.FAIL);
			}
			return dto;
		}
		dto.setRetMsg("核库失败");
		dto.setRetCode(RetCodeEnum.FAIL.getCode());
		return dto;
	}

	/**
	 * 钞处领现金库确认
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO affirmOutNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String taskNo = transferTaskInfoDTO.getTaskNo();
		String customerNo = transferTaskInfoDTO.getCustomerNo();
		String shelfNo = transferTaskInfoDTO.getShelfNo();
		//根据任务编号查询任务单
		TaskTableDTO transferTaskInfo = taskInnerResource.getByPrimaryKey(taskNo);
		if (transferTaskInfo == null) {
			dto.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			dto.setRetMsg(RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return dto;
		}

		//查询当前任务单状态
		Integer curStatus = taskInnerResource.qryCurStatus(transferTaskInfoDTO.getTaskNo());
		String operateType = transferTaskInfoDTO.getOperateType();
		Integer nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		int taskType = transferTaskInfoDTO.getTaskType();

		int x = serviceInnerResource.isStatusConvertMatch(taskType, curStatus, operateType);
		if (x < 1) {
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			dto.setRetMsg("操作状态不存在");
			return dto;
		}

		//判断笼车是否存在
		int flag = shelfResource.qryExist(shelfNo);
		if (flag < 1) {
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			dto.setRetMsg("笼车【" + shelfNo + "】不存在！");
			return dto;
		}

		//查询笼车状态
		int shelfStatus = shelfResource.qryShelfStatus(shelfNo);
		if (StatusEnum.ShelfTableStatus.STATUS_ALREADY_ENABLE.getStatus() != shelfStatus) {
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			dto.setRetMsg("笼车" + shelfNo + "不是空闲状态！");
			return dto;
		}
		//修改笼车状态为占用
		int y = shelfResource.updateStatusByNo(shelfNo, StatusEnum.ShelfTableStatus.STATUS_USED.getStatus());
		if (y != 1) {
			throw new RuntimeException("修改笼车" + shelfNo + "状态失败！");
		}

		//修改task_table
		String modeOp = ServletRequestUtil.getUsername();
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		paraMap.put("modeOp", modeOp);
		paraMap.put("modeTime", CalendarUtil.getSysTimeYMDHMS());
		//确认出库后的状态变为出库确认207
		paraMap.put("status", nextStatus);
		paraMap.put("shelfNo", shelfNo);

		int a = taskInnerResource.updateByPrimaryKeyMap(paraMap);
		if (a == -1) {
			throw new RuntimeException("修改任务单失败！");
		}

		List<TransferTaskDetailDTO> transferTaskDetailList = transferTaskInfoDTO.getTransferTaskDetailDTO();
		String curTime = CalendarUtil.getSysTimeYMD();
		//插入task_entity_detail
		for (TransferTaskDetailDTO transferTaskDetailDTO : transferTaskDetailList) {
			//首先取到容器编号，根据容器编号进行在仓储表中查找相应数据（本身以及下属实物），再更新到相应的task_entity表中
			String entityNo = transferTaskDetailDTO.getEntityNo();
			String depositType = StringUtil.parseString(transferTaskDetailDTO.getDepositType());
			List<StorageEntityTableDO> doList = storageEntityTableMapper.qryByEntityNo(entityNo);
			if (doList.size() > 0) {
				for (StorageEntityTableDO storageEntityTableDO : doList) {
					String id = storageEntityTableDO.getId();
					List<StorageEntityPropertyPO> poList = storageEntityPropertyMapper.qryEntityDetailById(id);
					storageEntityTableDO.setEntityDetail(poList);
				}
			} else {
				dto.setRetMsg("此容器" + entityNo + "不存在！");
				return dto;
			}
			for (StorageEntityTableDO storageEntityTableDO1 : doList) {
				String storageId = storageEntityTableDO1.getId();
				//插入task_entity_table
				HashMap<String, Object> paracMap = new HashMap<String, Object>();
				String id = java.util.UUID.randomUUID().toString().replace("-", "");
				String entityNo1 = storageEntityTableDO1.getEntityNo();
				paracMap.put("id", id);
				paracMap.put("taskNo", taskNo);
				paracMap.put("entityNo", entityNo1);
				paracMap.put("productNo", storageEntityTableDO1.getProductNo());
				paracMap.put("amount", storageEntityTableDO1.getAmount());
				paracMap.put("parentEntity", storageEntityTableDO1.getParentEntity());
				paracMap.put("direction", StatusEnum.StorageSortedTransferDirection.OUT.getDirection()); //1：出库  2：入库
				paracMap.put("leafFlag", StatusEnum.ContainerLeafFlag.FLAG_TRUE.getFlag());//调拨任务单中的容器默认为最下级容器1
				paracMap.put("note", curTime);
				paracMap.put("depositType", depositType);
				int f = taskEntityInnerResource.insertSelectiveByMap(paracMap);
				if (f == -1) {
					throw new RuntimeException("失败");
				}
				//现金调出，申请金额和实际调出金额可能会不一致，之后的清点等业务，需要以实际调出金额为准
				List<StorageEntityPropertyPO> detailList = storageEntityTableDO1.getEntityDetail();
				if (detailList.size() > 0) {
					//插入task_entity_detail
					for (StorageEntityPropertyPO storageEntityPropertyPO : detailList) {
						if (storageEntityPropertyPO != null) {
							HashMap<String, Object> paradMap = new HashMap<String, Object>();
							paradMap.put("id", id);
							paradMap.put("key", storageEntityPropertyPO.getKey());
							paradMap.put("name", storageEntityPropertyPO.getName());
							paradMap.put("value", storageEntityPropertyPO.getValue());
							paradMap.put("taskNo", taskNo);
							int e = taskEntityDetailInnerResource.insertSelectiveByMap(paradMap);
							if (e == -1) {
								throw new RuntimeException("失败");
							}
						}
					}
				}
				storageEntityPropertyMapper.deleteById(storageId);
				storageEntityTableMapper.deleteById(storageId);
			}
			//如果箱子类型是解现箱，那么寄库类型不区分，默认为null  3:解现箱  4：寄存箱
			/*if (containerType == 3) {
				depositType = null;
			}*/
		}
		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("金库确认成功！");

		//推送给所有人信息
		Map<String, Object> outStorage = new HashMap<>();
		outStorage.put("taskNo", taskNo);
		outStorage.put("customerNo", customerNo);
		outStorage.put("shelfNo", shelfNo);
		outStorage.put("type", "MenJinShenHeChuKu");
		String jsonStorageInfo = JSONUtil.createJsonString(outStorage);
		sendInfoInnerResource.sendInfo2All(jsonStorageInfo);

		return dto;
	}
}
