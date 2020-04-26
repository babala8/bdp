package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushServerRequestDTO;
import com.zjft.microservice.treasurybrain.pushserver.service.SendSmsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 常 健
 * @since 2020/2/24
 */
@RestController
@Slf4j
public class SendSmsInfoResourceImpl implements SendSmsInfoResorce {

	@Resource
	private SendSmsInfoService sendSmsInfoService;

	@Override
	public DTO sendInfo2User(PushServerRequestDTO pushServerRequestDTO) {
		try {
			String userName = pushServerRequestDTO.getUserName();
			String message = pushServerRequestDTO.getMessage();
			String noticeCategory = pushServerRequestDTO.getNoticeCategory();
			String noticeCategoryDescription = pushServerRequestDTO.getNoticeCategoryDescription();
			String noticeTitle = pushServerRequestDTO.getNoticeTitle();
			if (StringUtil.isNullorEmpty(userName) || StringUtil.isNullorEmpty(message) || StringUtil.isNullorEmpty(noticeCategory)
					|| StringUtil.isNullorEmpty(noticeCategoryDescription) || StringUtil.isNullorEmpty(noticeTitle)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return sendSmsInfoService.sendInfo2User(pushServerRequestDTO);
		} catch (Exception e) {
			log.error("发送短信至用户失败！", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO sendInfo2Roles(PushServerRequestDTO pushServerRequestDTO) {
		try {
			String roles = pushServerRequestDTO.getRoles();
			String message = pushServerRequestDTO.getMessage();
			String noticeCategory = pushServerRequestDTO.getNoticeCategory();
			String noticeCategoryDescription = pushServerRequestDTO.getNoticeCategoryDescription();
			String noticeTitle = pushServerRequestDTO.getNoticeTitle();
			if (StringUtil.isNullorEmpty(roles) || StringUtil.isNullorEmpty(message) || StringUtil.isNullorEmpty(noticeCategory)
					|| StringUtil.isNullorEmpty(noticeCategoryDescription) || StringUtil.isNullorEmpty(noticeTitle)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return sendSmsInfoService.sendInfo2Roles(pushServerRequestDTO);
		} catch (Exception e) {
			log.error("发送短信至角色失败！", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO sendInfo2All(PushServerRequestDTO pushServerRequestDTO) {
		try {
			String message = pushServerRequestDTO.getMessage();
			String noticeCategory = pushServerRequestDTO.getNoticeCategory();
			String noticeCategoryDescription = pushServerRequestDTO.getNoticeCategoryDescription();
			String noticeTitle = pushServerRequestDTO.getNoticeTitle();
			if (StringUtil.isNullorEmpty(message) || StringUtil.isNullorEmpty(noticeCategory)
					|| StringUtil.isNullorEmpty(noticeCategoryDescription) || StringUtil.isNullorEmpty(noticeTitle)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return sendSmsInfoService.sendInfo2All(pushServerRequestDTO);
		} catch (Exception e) {
			log.error("发送短信至所有人失败！", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}


}
