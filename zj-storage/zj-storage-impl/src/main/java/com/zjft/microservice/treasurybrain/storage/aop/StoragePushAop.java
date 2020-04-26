package com.zjft.microservice.treasurybrain.storage.aop;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityTransferDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库房出入库信息推送
 *
 * @author 葛瑞莲
 * @since 2019/9/9
 */
@Slf4j
@Aspect
@Component
public class StoragePushAop {

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;


	/**
	 * AOP切点位置：实物出库
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.storage.component.GoodOutComponent.goodOut(..))")
	public void StorageOutPointcut() {
	}

	/**
	 * AOP切点位置：实物入库
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.storage.component.GoodInComponent.goodIn(..))")
	public void StorageInPointcut() {
	}

	/**
	 * 仓储出库操作后，推送出库容器信息给所有人
	 *
	 * @param joinPoint 连接点-用于获取切入方法的参数
	 */
	@AfterReturning("StorageOutPointcut()")
	public void AfterStorageOut(JoinPoint joinPoint) {
		log.debug("@AfterReturning() - StorageOutPointcut() - AfterStorageOut()");

		try {
			String nowTime = CalendarUtil.getSysTimeYMDHMS();
			Object[] a = joinPoint.getArgs();
			StorageEntityTransferDTO storageEntityTransferDTO = (StorageEntityTransferDTO) a[0];
			String lineName = storageEntityTransferDTO.getLineName();
			String lineNo = storageEntityTransferDTO.getLineNo();
			String clrCenterNo = "";
			String centerName = "";
			List<Map<String, Object>> centerMap = addnoteLineInnerResource.qryClrCenter(lineNo);
			if (centerMap != null && centerMap.size() > 0 && centerMap.get(0) != null) {
				clrCenterNo = StringUtil.parseString(centerMap.get(0).get("clrCenterNo"));
				centerName = StringUtil.parseString(centerMap.get(0).get("centerName"));
			}
//			Integer containerCount = goodManageMapper.qryContainerCount(taskNoList);
			Integer containerCount = storageEntityTransferDTO.getEntityNos().size();
			//flag为出入库标志位，1：入库，2：出库
			Integer flag = 2;
			//websocket推送消息类型标识
			String type = "InOut";
			String message = nowTime + " " + lineName + "线路 出库 " + containerCount + "个款箱";
			log.info(message);

			Map outStorage = new HashMap();
			outStorage.put("nowTime", nowTime);
			outStorage.put("lineName", lineName);
			outStorage.put("containerCount", containerCount);
			outStorage.put("flag", flag);
			outStorage.put("type", type);
			outStorage.put("clrCenterNo", clrCenterNo);
			outStorage.put("centerName", centerName);

			String jsonStorageInfo = JSONUtil.createJsonString(outStorage);
			DTO sendInfo2All = sendInfoInnerResource.sendInfo2All(jsonStorageInfo);
			log.info("推送给所有人是否成功：" + sendInfo2All.getRetMsg());
		} catch (Exception e) {
			log.error("[AfterStorageOut exception]: ", e);
		}
	}

	/**
	 * 仓储入库操作后，推送出库容器信息给所有人
	 *
	 * @param joinPoint 连接点-用于获取切入方法的参数
	 */
	@AfterReturning("StorageInPointcut()")
	public void AfterStorageIn(JoinPoint joinPoint) {
		log.debug("@AfterReturning() - StorageInPointcut() - AfterStorageIn()");

		try {
			String nowTime = CalendarUtil.getSysTimeYMDHMS();
			Object[] a = joinPoint.getArgs();
			StorageEntityTransferDTO storageEntityTransferDTO = (StorageEntityTransferDTO) a[0];
			String lineNo = storageEntityTransferDTO.getLineNo();
			String lineName = addnoteLineInnerResource.qryLineNameByLineNo(lineNo);
			String clrCenterNo = "";
			String centerName = "";
			List<Map<String, Object>> centerMap = addnoteLineInnerResource.qryClrCenter(lineNo);
			if (centerMap != null && centerMap.size() > 0 && centerMap.get(0) != null) {
				clrCenterNo = StringUtil.parseString(centerMap.get(0).get("clrCenterNo"));
				centerName = StringUtil.parseString(centerMap.get(0).get("centerName"));
			}
			Integer containerCount = storageEntityTransferDTO.getEntityNos().size();
			//flag为出入库标志位，1：入库，2：出库
			Integer flag = 1;
			//websocket推送消息类型标识
			String type = "InOut";
			String message = nowTime + " " + lineName + "线路 入库 " + containerCount + "个款箱";
			log.info(message);

			Map inStorage = new HashMap();
			inStorage.put("nowTime", nowTime);
			inStorage.put("lineName", lineName);
			inStorage.put("containerCount", containerCount);
			inStorage.put("flag", flag);
			inStorage.put("type", type);
			inStorage.put("centerName", centerName);
			inStorage.put("clrCenterNo", clrCenterNo);


			String jsonStorageInfo = JSONUtil.createJsonString(inStorage);
			DTO sendInfo2All = sendInfoInnerResource.sendInfo2All(jsonStorageInfo);
			log.info("推送给所有人是否成功：" + sendInfo2All.getRetMsg());
		} catch (Exception e) {
			log.error("[AfterStorageIn exception]: ", e);
		}
	}


}
