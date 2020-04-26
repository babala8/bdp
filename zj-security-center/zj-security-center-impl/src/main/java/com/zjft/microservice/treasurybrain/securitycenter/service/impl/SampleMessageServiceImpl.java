package com.zjft.microservice.treasurybrain.securitycenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.securitycenter.domain.SampleMessageInfo;
import com.zjft.microservice.treasurybrain.securitycenter.repository.SampleMessageMapper;
import com.zjft.microservice.treasurybrain.securitycenter.service.SampleMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SampleMessageServiceImpl implements SampleMessageService {

	@Resource
	private SampleMessageMapper sampleMessageMapper;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addSampleMessageMap(String createJsonString) throws ParseException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);

		String warnMessageId = StringUtil.parseString(params.get("warnMessageId"));
		String warnMessageType = StringUtil.parseString(params.get("warnMessageType"));
		String warnMessageInfo = StringUtil.parseString(params.get("warnMessageInfo"));
		String warnMessageDate = StringUtil.parseString(params.get("warnMessageDate"));
		String warnMessageTime = StringUtil.parseString(params.get("warnMessageTime"));
		String warnMessageDetailInfo = StringUtil.parseString(params.get("warnMessageDetailInfo"));
		String warnMessageToUserNo = StringUtil.parseString(params.get("warnMessageToUserNo"));
		String warnMessageToRoleNo = StringUtil.parseString(params.get("warnMessageToRoleNo"));
		String warnMessageHandleStatus = StringUtil.parseString(params.get("warnMessageHandleStatus"));
		String warnMessageHandleUserNo = StringUtil.parseString(params.get("warnMessageHandleUserNo"));
		String warnMessageHandleUserName = StringUtil.parseString(params.get("warnMessageHandleUserName"));
		String warnMessageHandleDate = StringUtil.parseString(params.get("warnMessageHandleDate"));
		String warnMessageHandleResult = StringUtil.parseString(params.get("warnMessageHandleResult"));
		String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

		SampleMessageInfo sampleMessageInfo = new SampleMessageInfo();
		sampleMessageInfo.setWarnMessageId(warnMessageId);
		sampleMessageInfo.setWarnMessageDate(warnMessageDate);
		sampleMessageInfo.setWarnMessageDetailInfo(warnMessageDetailInfo);
		sampleMessageInfo.setWarnMessageHandleDate(warnMessageHandleDate);
		sampleMessageInfo.setWarnMessageHandleResult(warnMessageHandleResult);
		sampleMessageInfo.setWarnMessageHandleStatus(warnMessageHandleStatus);
		sampleMessageInfo.setWarnMessageHandleUserName(warnMessageHandleUserName);
		sampleMessageInfo.setWarnMessageHandleUserNo(warnMessageHandleUserNo);
		sampleMessageInfo.setWarnMessageInfo(warnMessageInfo);
		sampleMessageInfo.setWarnMessageTime(warnMessageTime);
		sampleMessageInfo.setWarnMessageToRoleNo(warnMessageToRoleNo);
		sampleMessageInfo.setWarnMessageToUserNo(warnMessageToUserNo);
		sampleMessageInfo.setWarnMessageType(warnMessageType);
		sampleMessageInfo.setClrCenterNo(clrCenterNo);

		int warnMessageAddFalg = Integer.parseInt(StringUtil.parseString(params.get("warnMessageAddFlag")));

		if(warnMessageAddFalg == 1) {
			sampleMessageMapper.insert(sampleMessageInfo);
		}

		if(warnMessageToUserNo != null && !"".equals(warnMessageToUserNo)) {
			DTO dto = sendInfoInnerResource.sendInfo2User(warnMessageToUserNo, createJsonString);
		}
        if(warnMessageToRoleNo != null && !"".equals(warnMessageToRoleNo)) {
			sendInfoInnerResource.sendInfo2Roles(warnMessageToRoleNo, createJsonString);
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "安防预警信息创建成功,发送到" + warnMessageToUserNo + "成功");

		return retMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> parkingGuidePushMessage(String createJsonString) throws ParseException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		DTO dto = sendInfoInnerResource.sendInfo2All(createJsonString);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "泊车引导信息创建成功,发送到成功");

		return retMap;
	}

}



