package com.zjft.microservice.treasurybrain.business.aop;

import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanMapper;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 加钞计划推送切面类
 * @author 张思雨
 * @since 2019-7-2
 */
@Slf4j
@Aspect
@Component
public class AddnotesPlanPushAop {

	@Resource
	private AddnotesPlanMapper addnotesPlanMapper;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	/**
	 * 切点位置：添加加钞计划审核addAddnotesAudit
	 */
	@Pointcut("execution(* com.zjft.microservice.treasurybrain.business.service.impl.AddnotesPlanServiceImpl.addAddnotesAudit*(..))")
	public void AddAddnotesAuditPointcut(){}

	/**
	 * 添加加钞计划审核完成后，推送当前待审核的计划数
	 * @param joinPoint 连接点
	 */
	@AfterReturning("AddAddnotesAuditPointcut()")
	public void afterAddAddnotesAudit(JoinPoint joinPoint){
		log.debug("@AfterReturning() - AddAddnotesAuditPointcut() - afterAddAddnotesAudit()");
		int status = StatusEnum.AddnotesPlanStatus.NEED_AUDIT.getId();
		try {
			int count = addnotesPlanMapper.selectWaitVerifyCount(status);
			String message = "[当前待审核计划个数为]：" + count;
			log.info(message);

			Map needAuditCount = new HashMap();
			needAuditCount.put("need_audit_count",count);

			String role = "ROLE_10001";
			String jsonNeedAuditCount = JSONUtil.createJsonString(needAuditCount);
			DTO sendInfo2All = sendInfoInnerResource.sendInfo2All(jsonNeedAuditCount);
			log.info("推送给所有人是否成功："+sendInfo2All.getRetMsg());
			DTO sendInfo2Roles = sendInfoInnerResource.sendInfo2Roles(role,jsonNeedAuditCount);
			log.info("推送给某角色是否成功："+sendInfo2Roles.getRetMsg());
		}catch (Exception e){
			log.error("[afterAddAddnotesAudit exception]: ",e);
		}
	}
}
