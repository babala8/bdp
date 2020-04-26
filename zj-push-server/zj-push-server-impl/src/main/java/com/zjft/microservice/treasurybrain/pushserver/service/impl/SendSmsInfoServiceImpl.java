package com.zjft.microservice.treasurybrain.pushserver.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushServerRequestDTO;
import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import com.zjft.microservice.treasurybrain.pushserver.repository.PushServerInfoMapper;
import com.zjft.microservice.treasurybrain.pushserver.service.SendSmsInfoService;
import com.zjft.microservice.treasurybrain.pushserver.web.MessageResource;
import com.zjft.microservice.treasurybrain.usercenter.web_inner.SysUserInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/24
 */
@Service
@Slf4j
public class SendSmsInfoServiceImpl implements SendSmsInfoService {

	@Resource
	private SysUserInnerResource sysUserInnerResource;

	@Resource
	private MessageResource messageResource;

	@Resource
	private PushServerInfoMapper pushServerInfoMapper;

	@Override
	public DTO sendInfo2User(PushServerRequestDTO pushServerRequestDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String userName = pushServerRequestDTO.getUserName();
		String message = pushServerRequestDTO.getMessage();
		String noticeCategory = pushServerRequestDTO.getNoticeCategory();
		String noticeCategoryDescription = pushServerRequestDTO.getNoticeCategoryDescription();
		String noticeTitle = pushServerRequestDTO.getNoticeTitle();
		String newMessage = noticeTitle + '：' + message;
		String time = pushServerRequestDTO.getTime();
		String timeJobName = pushServerRequestDTO.getTimeJobName();
		String timeJobNoticeAddress = pushServerRequestDTO.getTimeJobNoticeAddress();
		String phoneNumber;
		String name;
		//判断操作是否为定时任务所做，再传入name,noticeAddress,time
		if (StringUtil.isNullorEmpty(timeJobName)) {
			name = sysUserInnerResource.getNameByUserName(userName);
		} else {
			name = timeJobName;
		}
		if (StringUtil.isNullorEmpty(timeJobNoticeAddress)) {
			phoneNumber = sysUserInnerResource.getUserPhoneNumber(userName);
		} else {
			phoneNumber = timeJobNoticeAddress;
		}

		PushServerInfoPO pushServerInfoPO = new PushServerInfoPO();
		pushServerInfoPO.setNo(java.util.UUID.randomUUID().toString().replace("-", ""));
		if (StringUtil.isNullorEmpty(time)) {
			pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
		} else {
			pushServerInfoPO.setTime(time);
		}
		pushServerInfoPO.setName(name);
		pushServerInfoPO.setMessage(newMessage);
		pushServerInfoPO.setNoticeWay(2);//通知方式2：短信
		pushServerInfoPO.setNoticeCategory(noticeCategory);
		pushServerInfoPO.setNoticeCategoryDescription(noticeCategoryDescription);
		pushServerInfoPO.setNoticeTitle(noticeTitle);
		pushServerInfoPO.setNoticeAddress(phoneNumber);//通知地址：通知方式为短信则地址为手机号
		pushServerInfoPO.setNoticeFlag(0);//默认发送为0
		pushServerInfoMapper.insert(pushServerInfoPO);

		//第三方调用
		Map<String, Object> map = messageResource.sendSmsInfo("上海银行金库", phoneNumber, newMessage);
		if (map.get("retCode").equals("ok")) {
			//接收发送成功则更新状态
			log.info("短信已发送，发送内容：【" + newMessage + "】，接收人：" + name);
			log.info("回执ID：" + map.get("requestId"));
			pushServerInfoMapper.updateNoticeFlag(pushServerInfoPO.getNo());
		} else {
			log.error("短信发送失败！");
			log.info("回执ID：" + map.get("requestId"));
			return dto;
		}
		dto.setRetCode("00");
		dto.setRetMsg("短信发送成功！");
		return dto;
	}

	@Override
	public DTO sendInfo2Roles(PushServerRequestDTO pushServerRequestDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String roles = pushServerRequestDTO.getRoles();
		String message = pushServerRequestDTO.getMessage();
		String noticeCategory = pushServerRequestDTO.getNoticeCategory();
		String noticeCategoryDescription = pushServerRequestDTO.getNoticeCategoryDescription();
		String noticeTitle = pushServerRequestDTO.getNoticeTitle();
		String newMessage = noticeTitle + '：' + message;
		List<UserDTO> userList = sysUserInnerResource.getUserAndPhoneNumber(roles);

		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		for (UserDTO user : userList) {
			String phoneNumber = user.getMobile();
			String name = user.getName();
			sb.append(phoneNumber).append(",");
			sb1.append(name).append(",");
		}
		String batchPhoneNumber = sb.toString();
		String batchName = sb1.toString();

		//待有具体第三方，则根据具体接口参数进行调整，此处模拟群发短信要么群发都成功要么都失败
		List<String> list = new ArrayList<>();
		List<PushServerInfoPO> pushServerInfoPOList = new ArrayList<>();
		PushServerInfoPO pushServerInfoPO = null;
		for (UserDTO userDTO1 : userList) {
			String name = userDTO1.getName();
			String phoneNumber = userDTO1.getMobile();

			pushServerInfoPO = new PushServerInfoPO();
			String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
			pushServerInfoPO.setNo(uuid);
			pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
			pushServerInfoPO.setName(name);
			pushServerInfoPO.setMessage(newMessage);
			pushServerInfoPO.setNoticeWay(2);//通知方式2：短信
			pushServerInfoPO.setNoticeCategory(noticeCategory);
			pushServerInfoPO.setNoticeCategoryDescription(noticeCategoryDescription);
			pushServerInfoPO.setNoticeTitle(noticeTitle);
			pushServerInfoPO.setNoticeAddress(phoneNumber);//通知地址：通知方式为短信则地址为手机号
			pushServerInfoPO.setNoticeFlag(0);//默认发送为0
			list.add(uuid);
			pushServerInfoPOList.add(pushServerInfoPO);
		}
		pushServerInfoMapper.insertBatch(pushServerInfoPOList);
		//第三方调用
		Map<String, Object> map = messageResource.sendBatchSmsInfo("上海银行金库", batchPhoneNumber, newMessage);
		if (map.get("retCode").equals("ok")) {
			//接收发送成功则更新状态
			log.info("短信已发送，发送内容：【" + newMessage + "】，接收人：" + batchName);
			log.info("回执ID：" + map.get("requestId"));
			pushServerInfoMapper.updateNoticeFlagBatch(list);
		} else {
			log.error("短信发送失败！");
			log.info("回执ID：" + map.get("requestId"));
			return dto;
		}
		dto.setRetCode("00");
		dto.setRetMsg("短信发送成功！");
		return dto;
	}

	@Override
	public DTO sendInfo2All(PushServerRequestDTO pushServerRequestDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String roles = pushServerRequestDTO.getRoles();
		String message = pushServerRequestDTO.getMessage();
		String noticeCategory = pushServerRequestDTO.getNoticeCategory();
		String noticeCategoryDescription = pushServerRequestDTO.getNoticeCategoryDescription();
		String noticeTitle = pushServerRequestDTO.getNoticeTitle();
		String newMessage = noticeTitle + '：' + message;
		List<UserDTO> userDTOList = sysUserInnerResource.getAllUserInfo();

		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		for (UserDTO user : userDTOList) {
			String phoneNumber = user.getMobile();
			String name = user.getName();
			sb.append(phoneNumber).append(",");
			sb1.append(name).append(",");
		}
		String batchPhoneNumber = sb.toString();
		String batchName = sb1.toString();

		List<String> list = new ArrayList<>();
		List<PushServerInfoPO> pushServerInfoPOList = new ArrayList<>();
		PushServerInfoPO pushServerInfoPO = null;
		for (UserDTO userDTO1 : userDTOList) {
			String name = userDTO1.getName();
			String phoneNumber = userDTO1.getMobile();

			pushServerInfoPO = new PushServerInfoPO();
			String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
			pushServerInfoPO.setNo(uuid);
			pushServerInfoPO.setTime(CalendarUtil.getSysTimeYMDHMS());
			pushServerInfoPO.setName(name);
			pushServerInfoPO.setMessage(newMessage);
			pushServerInfoPO.setNoticeWay(2);//通知方式2：短信
			pushServerInfoPO.setNoticeCategory(noticeCategory);
			pushServerInfoPO.setNoticeCategoryDescription(noticeCategoryDescription);
			pushServerInfoPO.setNoticeTitle(noticeTitle);
			pushServerInfoPO.setNoticeAddress(phoneNumber);//通知地址：通知方式为短信则地址为手机号
			pushServerInfoPO.setNoticeFlag(0);//默认发送为0
			list.add(uuid);
			pushServerInfoPOList.add(pushServerInfoPO);
		}
		pushServerInfoMapper.insertBatch(pushServerInfoPOList);
		//第三方调用
		Map<String, Object> map = messageResource.sendBatchSmsInfo("上海银行金库", batchPhoneNumber, newMessage);
		if (map.get("retCode").equals("ok")) {
			//接收发送成功则更新状态
			log.info("短信已发送，发送内容：【" + newMessage + "】，接收人：" + batchName);
			log.info("回执ID：" + map.get("requestId"));
			pushServerInfoMapper.updateNoticeFlagBatch(list);
		} else {
			log.error("短信发送失败！");
			log.info("回执ID：" + map.get("requestId"));
			return dto;
		}
		dto.setRetCode("00");
		dto.setRetMsg("短信发送成功！");
		return dto;
	}
}
