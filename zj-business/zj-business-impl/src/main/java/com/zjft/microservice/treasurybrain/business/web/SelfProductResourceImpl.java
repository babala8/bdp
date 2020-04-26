package com.zjft.microservice.treasurybrain.business.web;


import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.business.service.SelfProductService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 吴朋
 * @since 2019/9/21
 */

@Slf4j
@RestController
public class SelfProductResourceImpl implements SelfProductResource {
	@Resource
	private SelfProductService selfProductService;

	/**
	 * 添加加钞审核计划
	 * 查询加钞计划，有则提交审核，审核完成后推送当前剩余审核计划数（aop）
	 *
	 */
//	@Override
//	public DTO applyForSelfProduct(TransferTaskInfoDTO transferTaskInfoDTO) {
//		log.info("------------SelfProductResourceImpl[applyForSelfProduct]-------------");
//		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
//		try {
//			//参数校验：金库编号，服务对象编号不为空
//			if (StringUtil.isNullorEmpty(transferTaskInfoDTO.getClrCenterNo()) || StringUtil.isNullorEmpty(transferTaskInfoDTO.getCustomerNo()) ) {
//				return new DTO(RetCodeEnum.PARAM_LACK);
//			}
//
//			Map<String, Object> retMap = selfProductService.applyForSelfProduct(transferTaskInfoDTO);
//			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
//			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
//		}catch (Exception e) {
//			log.error("产品业务申请生成失败：" + e.getMessage());
//			return new DTO(RetCodeEnum.EXCEPTION);
//		}
//		return dto;
//	}

}
