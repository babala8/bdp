package com.zjft.microservice.treasurybrain.pushserver.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import com.zjft.microservice.treasurybrain.pushserver.repository.PushServerInfoMapper;
import com.zjft.microservice.treasurybrain.pushserver.service.SendMailInfoService;
import com.zjft.microservice.treasurybrain.usercenter.web_inner.SysUserInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 韩 通
 * @since 2020-02-24
 */
@Service
@Slf4j
public class SendMailInfoServiceImpl implements SendMailInfoService {

	@Resource
	private PushServerInfoMapper pushServerInfoMapper;

	@Value("${spring.mail.username}")
	private String sender;

	@Autowired
	private JavaMailSender javaMailSender;

	@Resource
	private SysUserInnerResource sysUserInnerResource;

	@Override
	public DTO sendInfo2User(Map<String, Object> map) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String message = StringUtil.parseString(map.get("message"));
		String userName = StringUtil.parseString(map.get("userName"));
		String noticeTitle = StringUtil.parseString(map.get("noticeTitle"));
		String noticeCategory = StringUtil.parseString(map.get("noticeCategory"));
		String noticeCategoryDescription = StringUtil.parseString(map.get("noticeCategoryDescription"));
		String time = StringUtil.parseString(map.get("time"));
		//根据用户编号查询
		String email = sysUserInnerResource.getUserEmail(userName);
		if (StringUtil.isNullorEmpty(email)) {
			dto.setRetMsg("该用户没有邮箱");
			return dto;
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(noticeTitle);
		simpleMailMessage.setText(message);
		PushServerInfoPO pushServerInfoPO = new PushServerInfoPO();
		pushServerInfoPO.setNo(java.util.UUID.randomUUID().toString().replace("-", ""));
		pushServerInfoPO.setMessage(message);
		pushServerInfoPO.setName(userName);
		pushServerInfoPO.setNoticeTitle(noticeTitle);
		pushServerInfoPO.setNoticeAddress(email);
		pushServerInfoPO.setNoticeCategory(noticeCategory);
		pushServerInfoPO.setNoticeFlag(0);//未发送成功
		pushServerInfoPO.setNoticeWay(3);//邮件方式
		if (StringUtil.isNullorEmpty(time)){
			pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
		}else {
			pushServerInfoPO.setTime(time);
		}
		pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
		pushServerInfoPO.setNoticeCategoryDescription(noticeCategoryDescription);
		pushServerInfoMapper.insert(pushServerInfoPO);
		try {
			javaMailSender.send(simpleMailMessage);
			//更新为已发送
			pushServerInfoMapper.updateNoticeFlag(pushServerInfoPO.getNo());
			log.info("邮件已经发送。");
		} catch (Exception e) {
			log.error("发送邮件时发生异常！", e);
			dto.setRetMsg("邮件发送给用户异常！");
			return dto;
		}
		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("邮件发送给角色成功！");
		return dto;
	}

	@Override
	public DTO sendInfo2Roles(Map<String, Object> map) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String message = StringUtil.parseString(map.get("message"));
		String roles = StringUtil.parseString(map.get("roles"));
		String noticeTitle = StringUtil.parseString(map.get("noticeTitle"));
		String noticeCategory = StringUtil.parseString(map.get("noticeCategory"));
		String noticeCategoryDescription = StringUtil.parseString(map.get("noticeCategoryDescription"));
		//根据角色编号查询邮箱
		List<UserDTO> userDTOS = sysUserInnerResource.qryRolesEmail(roles);
		if (userDTOS.size() == 0) {
			dto.setRetMsg("该角色下没有用户或用户邮箱！");
			return dto;
		}

		String[] emails = new String[userDTOS.size()];
		for(int i=0;i<userDTOS.size();i++){
			emails[i] = userDTOS.get(i).getEmail();
		}

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setTo(emails);
		simpleMailMessage.setSubject(noticeTitle);
		simpleMailMessage.setText(message);

		//将该角色的所有用户插入库中
		//保存no
		List<String> list = new ArrayList<>();
		List<PushServerInfoPO> pushServerInfoPOList = new ArrayList<>();
		PushServerInfoPO pushServerInfoPO = null;
		for (UserDTO userDTO1 : userDTOS) {
			String name = userDTO1.getName();
			String email = userDTO1.getEmail();

			pushServerInfoPO = new PushServerInfoPO();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			list.add(uuid);
			pushServerInfoPO.setNo(uuid);
			pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
			pushServerInfoPO.setName(name);
			pushServerInfoPO.setMessage(message);
			pushServerInfoPO.setNoticeWay(3);//通知方式2：邮件
			pushServerInfoPO.setNoticeCategory(noticeCategory);
			pushServerInfoPO.setNoticeCategoryDescription(noticeCategoryDescription);
			pushServerInfoPO.setNoticeTitle(noticeTitle);
			pushServerInfoPO.setNoticeAddress(email);//通知地址：通知方式为邮件则地址为邮箱地址
			pushServerInfoPO.setNoticeFlag(0);//默认发送为0
			pushServerInfoPOList.add(pushServerInfoPO);
		}
		pushServerInfoMapper.insertBatch(pushServerInfoPOList);
		try {
			javaMailSender.send(simpleMailMessage);
			//更新为已发送
			pushServerInfoMapper.updateNoticeFlagBatch(list);
			log.info("邮件已经发送。");
		} catch (Exception e) {
			log.error("发送邮件时发生异常！", e);
			dto.setRetMsg("邮件发送给角色异常！");
			return dto;
		}
		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("邮件发送给角色成功！");
		return dto;
	}

	@Override
	public DTO sendInfo2All(Map<String, Object> map) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String message = StringUtil.parseString(map.get("message"));
		String noticeTitle = StringUtil.parseString(map.get("noticeTitle"));
		String noticeCategory = StringUtil.parseString(map.get("noticeCategory"));
		String noticeCategoryDescription = StringUtil.parseString(map.get("noticeCategoryDescription"));
		//根据角色编号查询邮箱
		List<UserDTO> userDTOS = sysUserInnerResource.getAllUserInfo();
		if (userDTOS.size() == 0) {
			dto.setRetMsg("该角色下没有用户或用户邮箱！");
			return dto;
		}

		String[] emails = new String[userDTOS.size()];
		for(int i=0;i<userDTOS.size();i++){
			emails[i] = userDTOS.get(i).getEmail();
		}

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setTo(emails);
		simpleMailMessage.setSubject(noticeTitle);
		simpleMailMessage.setText(message);

		//将该角色的所有用户插入库中
		//保存no
		List<String> list = new ArrayList<>();
		List<PushServerInfoPO> pushServerInfoPOList = new ArrayList<>();
		PushServerInfoPO pushServerInfoPO = null;
		for (UserDTO userDTO1 : userDTOS) {
			String name = userDTO1.getName();
			String email = userDTO1.getEmail();

			pushServerInfoPO = new PushServerInfoPO();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			list.add(uuid);
			pushServerInfoPO.setNo(uuid);
			pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
			pushServerInfoPO.setName(name);
			pushServerInfoPO.setMessage(message);
			pushServerInfoPO.setNoticeWay(3);//通知方式2：邮件
			pushServerInfoPO.setNoticeCategory(noticeCategory);
			pushServerInfoPO.setNoticeCategoryDescription(noticeCategoryDescription);
			pushServerInfoPO.setNoticeTitle(noticeTitle);
			pushServerInfoPO.setNoticeAddress(email);//通知地址：通知方式为邮件则地址为邮箱地址
			pushServerInfoPO.setNoticeFlag(0);//默认发送为0
			pushServerInfoPOList.add(pushServerInfoPO);
		}
		pushServerInfoMapper.insertBatch(pushServerInfoPOList);
		try {
			javaMailSender.send(simpleMailMessage);
			//更新为已发送
			pushServerInfoMapper.updateNoticeFlagBatch(list);
			log.info("简单邮件已经发送。");
		} catch (Exception e) {
			log.error("发送简单邮件时发生异常！", e);
			dto.setRetMsg("邮件发送给用户异常！");
			return dto;
		}
		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg("邮件发送给用户成功！");
		return dto;
	}
}
