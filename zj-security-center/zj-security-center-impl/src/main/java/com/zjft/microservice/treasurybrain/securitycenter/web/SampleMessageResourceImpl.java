package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.handler.annotation.ZjMessageResource;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SampleMessageResponseDTO;
import com.zjft.microservice.treasurybrain.securitycenter.service.SampleMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.UUID;

/**
 * 安防推送信息获取
 *
 * @author zhangjs
 * @since 2019-09-09
 */
@ZjMessageResource
@RestController
@Slf4j
public class SampleMessageResourceImpl implements SampleMessageResource {

	@Resource
	private SampleMessageService sampleMessageService;

	@Override
	public SampleMessageResponseDTO  SampleMessageNoAdd(SampleMessageResponseDTO sampleMessageResponseDTO) {
        log.info(sampleMessageResponseDTO.toString());
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("warnMessageId", UUID.randomUUID().toString().substring(0,8));

        params.put("warnMessageAddFlag", 0);

        params.put("warnMessageType", sampleMessageResponseDTO.getWarnMessageType());
        params.put("warnMessageInfo", sampleMessageResponseDTO.getWarnMessageInfo());
        params.put("warnMessageDate", CalendarUtil.getSysTimeYMD());
        params.put("warnMessageTime", CalendarUtil.getSysTimeHMS());
        params.put("warnMessageDetailInfo", sampleMessageResponseDTO.getWarnMessageInfo());
        params.put("warnMessageToUserNo", sampleMessageResponseDTO.getWarnMessageToUserNo());
        params.put("warnMessageToRoleNo", sampleMessageResponseDTO.getWarnMessageToRoleNo());
        params.put("warnMessageHandleStatus", "");
        params.put("warnMessageHandleUserNo", "");
        params.put("warnMessageHandleUserName", "");
        params.put("warnMessageHandleDate", "");
        params.put("warnMessageHandleResult", "");

        try{
        	sampleMessageService.addSampleMessageMap(JSONUtil.createJsonString(params));
		}catch (ParseException e) {
        	log.error(e.getMessage());
		}

		return sampleMessageResponseDTO;
	}

	@Override
	public SampleMessageResponseDTO SampleMessageAdd(SampleMessageResponseDTO sampleMessageResponseDTO) {
        log.info(sampleMessageResponseDTO.toString());
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("warnMessageId", UUID.randomUUID().toString().substring(0,8));

        params.put("warnMessageAddFlag", 1);

        params.put("warnMessageType", sampleMessageResponseDTO.getWarnMessageType());
        params.put("warnMessageInfo", sampleMessageResponseDTO.getWarnMessageInfo());
        params.put("warnMessageDate", CalendarUtil.getSysTimeYMD());
        params.put("warnMessageTime", CalendarUtil.getSysTimeHMS());
        params.put("warnMessageDetailInfo", sampleMessageResponseDTO.getWarnMessageInfo());
        params.put("warnMessageToUserNo", sampleMessageResponseDTO.getWarnMessageToUserNo());
        params.put("warnMessageToRoleNo", sampleMessageResponseDTO.getWarnMessageToRoleNo());
		params.put("clrCenterNo", sampleMessageResponseDTO.getClrCenterNo());
        params.put("warnMessageHandleStatus", "");
        params.put("warnMessageHandleUserNo", "");
        params.put("warnMessageHandleUserName", "");
        params.put("warnMessageHandleDate", "");
        params.put("warnMessageHandleResult", "");

        try{
        	sampleMessageService.addSampleMessageMap(JSONUtil.createJsonString(params));
		}catch (ParseException e) {
        	log.error(e.getMessage());
		}

		return sampleMessageResponseDTO;
	}


}
