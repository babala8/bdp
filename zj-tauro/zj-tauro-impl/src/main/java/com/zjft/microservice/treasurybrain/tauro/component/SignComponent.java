package com.zjft.microservice.treasurybrain.tauro.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.*;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderCoordsInfoPO;
import com.zjft.microservice.treasurybrain.tauro.repository.TagReaderCoordsMapper;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@ZjComponentResource(group = "sign")
public class SignComponent {
	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private TaskEntityInnerResource taskEntityInnerResource;

	@Resource
	private TaskPerRecorderInnerResource taskPerRecorderInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private TagReaderCoordsMapper tagReaderCoordsMapper;

	@Resource
	private ServiceInnerResource serviceInnerResource;
	/**
	 * 虚拟签收
	 */
	@ZjComponentMapping("sign(虚拟签收)")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sign(TaskTransferDTO taskTransferDTO, ObjectDTO returnDTO, List<String> lists) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		String lineNo = taskTransferDTO.getLineNo();
		int taskType = taskTransferDTO.getTaskType();
		String customerNo = taskTransferDTO.getCustomerNo();
		int nextStatus=0;
		String operateType = taskTransferDTO.getOperateType();
		if(operateType!=null){
			//nextStatus = taskTransferDTO.getStatus();
			nextStatus = serviceInnerResource.qryNextByOperateType(operateType);
		}
		List<String> entityNoList = taskTransferDTO.getEntityNos();
		List<String> taskContainerList = new ArrayList<String>();
		List<String> taskNoList = new ArrayList<String>();
		List<String> statusString = serviceInnerResource.mayStatusConverts(operateType,taskType);
		if(statusString.size()==0){
			Map<String,Object> lineMap=new HashMap<String,Object>();
			lineMap.put("lineNo",lineNo);
			lineMap.put("planFinishDate", CalendarUtil.getSysTimeYMD());
//			List<String> list = tauroTaskTableMapper.qryTaskNoByLineNo(lineMap);
			List<String> list = taskInnerResource.qryTaskNoByLineNo(lineMap);
			taskNoList.addAll(list);
		}else{
			for(String mayStatus:statusString){
				Map<String,Object> lineMap=new HashMap<String,Object>();
				lineMap.put("lineNo",lineNo);
				lineMap.put("status",mayStatus);
				lineMap.put("planFinishDate",CalendarUtil.getSysTimeYMD());
//				List<String> list = tauroTaskTableMapper.qryTaskNoByLineNo(lineMap);
				List<String> list = taskInnerResource.qryTaskNoByLineNo(lineMap);
				taskNoList.addAll(list);
			}
		}
		if(taskNoList.size()==0){
			throw new RuntimeException("当前线路下无[可签收]任务单");
		}
		for(String taskNo:taskNoList){
			List<String> containerList = taskEntityInnerResource.selectContainerNoByTaskNo(taskNo);
			taskContainerList.addAll(containerList);
		}
		for(String taskNo:taskNoList) {
			int taskStatus = taskInnerResource.getByPrimaryKey(taskNo).getStatus();

			boolean flag = false;
			for (String entityNo : entityNoList) {
				Map map = new HashMap();
				map.put("entityNo", entityNo);
				map.put("taskNo", taskNo);
//				TauroTaskEntityTablePO tauroTaskEntityTablePO = tauroTaskEntityTableMapper.qryByTaskNoAndContainerNo(map);
				TaskEntityDTO taskEntityDTO = taskEntityInnerResource.qryByTaskNoAndContainerNo(map);
				if (taskEntityDTO == null) {
					log.error("该容器[" + entityNo + "]编号输入错误");
					//throw new RuntimeException("该容器[" + containerNo + "]编号输入错误");
					flag = true;
					break;
				}
				//验证任务状态是否为已配钞入库且是否为当日任务
				String planFinishDate = taskInnerResource.getByPrimaryKey(taskNo).getPlanFinishDate();
				if ((serviceInnerResource.isStatusConvertMatch(taskType, taskStatus, operateType) == 0 && nextStatus != 0) || !planFinishDate.equals(CalendarUtil.getSysTimeYMD())) {
					//throw new RuntimeException("当前任务[" + taskNo + "]未完成配钞或不是今日任务单");
				}
				List<String> cassetteNos = taskEntityInnerResource.selectCassetteNos(taskNo, entityNo);
				for (String cassetteNo : cassetteNos) {
					if (!entityNoList.contains(cassetteNo)) {
						throw new RuntimeException("任务单[" + taskNo + "]中存在未匹配的容器，无法进行签收操作");
					}
				}
			}

			if(flag) {
				continue;
			}

			for (String containerNo : entityNoList) {
				//新增任务执行记录表
//				TauroTaskEntityDetailPO tauroTaskEntityDetailPO = tauroTaskEntityDetailMapper.selectByNo(taskNo, containerNo);
//				TauroTaskPerRecorderPO tauroTaskPerRecorderPO = new TauroTaskPerRecorderPO();
//				tauroTaskPerRecorderPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
//				tauroTaskPerRecorderPO.setTaskNo(taskNo);
//				tauroTaskPerRecorderPO.setContainerNo(containerNo);
//				tauroTaskPerRecorderPO.setPerformTime(CalendarUtil.getSysTimeYMDHMS());
//				tauroTaskPerRecorderPO.setPerformType(StatusEnum.TaskPerRecorderTypeTauro.TYPE_VIRTUAL_SIGN.getType());
//				tauroTaskPerRecorderPO.setOpNo1(sysUserResource.getAuthUserInfo().getUsername());
//				if(tauroTaskEntityDetailPO != null) {
//					tauroTaskPerRecorderPO.setContainerType(tauroTaskEntityDetailPO.getContainerType());
//				}else {
//					tauroTaskPerRecorderPO.setContainerType(1);
//				}
				TaskEntityDTO taskEntityDTO = taskEntityInnerResource.selectOneByTaskNo(taskNo,containerNo);
				Map<String,Object> taskPerRecorderMap = new HashMap<>();
				taskPerRecorderMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				taskPerRecorderMap.put("taskNo",taskNo);
				taskPerRecorderMap.put("containerNo",containerNo);
				taskPerRecorderMap.put("performTime",CalendarUtil.getSysTimeYMDHMS());
				taskPerRecorderMap.put("performType", StatusEnum.TaskPerRecorderTypeTauro.TYPE_TAILBOX_RECYCLE.getType());
				taskPerRecorderMap.put("opNo1",sysUserResource.getAuthUserInfo().getUsername());
				if(taskEntityDTO != null) {
					taskPerRecorderMap.put("containerType", taskEntityDTO.getProductNo());
				}else {
					taskPerRecorderMap.put("containerType",1);
				}
				taskPerRecorderMap.put("containerType", taskEntityDTO.getProductNo());


				int x = taskPerRecorderInnerResource.insertByMap(taskPerRecorderMap);
				if (x != 1) {
					log.error("生成容器[" + containerNo + "]任务执行记录表失败");
					throw new RuntimeException("生成容器[" + containerNo + "]任务执行记录表失败");
				}
			}

			//更新当前设备状态
//			TauroTaskDetailTablePO tauroTaskDetailTablePO=new TauroTaskDetailTablePO();
//			tauroTaskDetailTablePO.setTaskNo(taskNo);
//			tauroTaskDetailTablePO.setCustomerNo(customerNo);
//			tauroTaskDetailTablePO.setStatus(StatusEnum.DevStatus.STATUS_SIGNED.getStatus());

//			Map<String,Object> taskDetailMap = new HashMap<>();
//			taskDetailMap.put("taskNo",taskNo);
//			taskDetailMap.put("customerNo",customerNo);
//			taskDetailMap.put("status",StatusEnum.DevStatus.STATUS_RECOVERY.getStatus());
//
////		int updateFlag = tauroTaskDetailTableMapper.updateStatusByNo(tauroTaskDetailTablePO);
//			int updateFlag = taskDetailInfoInnerResource.updateByPrimaryKeyMap(taskDetailMap);
//			if (updateFlag != 1) {
//				log.error("更新表 [TASK_DETAIL_TABLE] 状态失败");
//				throw new RuntimeException("更新设备编号[" + customerNo + "]状态失败");
//			}

			if (nextStatus != 0) {
				//更新当前任务单状态
//				TauroTaskTablePO tauroTaskTablePO = new TauroTaskTablePO();
//				tauroTaskTablePO.setTaskNo(taskNo);
//				tauroTaskTablePO.setStatus(nextStatus);

				Map<String,Object> TaskTableMap = new HashMap<>();
				TaskTableMap.put("taskNo",taskNo);
				TaskTableMap.put("status",nextStatus);
				int updateFlag1 = taskInnerResource.updateByPrimaryKeyMap(TaskTableMap);
				if (updateFlag1 != 1) {
					log.error("更新表 [TASK_TABLE] 状态失败");
					throw new RuntimeException("更新任务表编号[" + taskNo + "]状态失败");
				}
			}

			//更新TAG_READER_COORDS_INFO表的数据（暂时）
			if(taskType==1){
				List<String> list=new ArrayList<String>();
				if(customerNo.equals("44021515")){
					list.add("104.073181,30.616502");
					list.add("104.073347,30.639405");
					list.add("104.107591,30.66283");
					list.add("104.130886,30.678478");
					//list.add("104.156418,30.691808");
					//list.add("104.162239,30.69746");
				}else if(customerNo.equals("44021426")){
					list.add("104.139768,30.676642");
					list.add("104.145103,30.679557");
//					list.add("104.097992,30.64913");
//					list.add("104.1307091,30.6780513");
//					list.add("104.1459727,30.68035569");
				}
				int count=0;
				for(String s:list){
					TagReaderCoordsInfoPO tagReaderCoordsInfoPO=new TagReaderCoordsInfoPO();
					String[] xy = s.split(",");
					long currentTime = System.currentTimeMillis() + count * 10 * 1000;
					count++;
					Date date = new Date(currentTime);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String nowTime=df.format(date);
					String[] time=nowTime.split(" ");
					tagReaderCoordsInfoPO.setTagReaderNo("4dbe488997123a06");
					tagReaderCoordsInfoPO.setRdDate(time[0]);
					tagReaderCoordsInfoPO.setRdTime(time[1]);
					tagReaderCoordsInfoPO.setUserNo(ServletRequestUtil.getUsername());
					tagReaderCoordsInfoPO.setX(new BigDecimal(xy[1]));
					tagReaderCoordsInfoPO.setY(new BigDecimal(xy[0]));
					tagReaderCoordsInfoPO.setCoordsSrc(1);
					tagReaderCoordsInfoPO.setTaskType(taskType);
					tagReaderCoordsInfoPO.setTaskNo(taskNo);
					tagReaderCoordsMapper.addTagReaderCoordsInfo(tagReaderCoordsInfoPO);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		return "ok";
	}
}
