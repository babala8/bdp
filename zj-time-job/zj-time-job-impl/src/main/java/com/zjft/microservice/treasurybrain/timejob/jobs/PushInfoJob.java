package com.zjft.microservice.treasurybrain.timejob.jobs;

import com.zjft.microservice.quartz.common.ResultEnum;
import com.zjft.microservice.quartz.jobs.BaseZjJob;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushServerRequestDTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.PushServerInfoInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendMailInfoInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendSmsInfoInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/2/25
 */
@Slf4j
public class PushInfoJob extends BaseZjJob {

	@Resource
	private PushServerInfoInnerResource pushServerInfoInnerResource;

	@Resource
	private SendMailInfoInnerResource sendMailInfoInnerResource;

	@Resource
	private SendSmsInfoInnerResource sendSmsInfoInnerResource;

	@Override
	protected ResultEnum taskPerform(JobExecutionContext jobExecutionContext) throws Exception {
		//查询近48小时内没发送成功的短信/邮件重新发送
		List<PushSeverInfoDTO> dtoList = pushServerInfoInnerResource.qryPushInfo();
		List<PushSeverInfoDTO> smsList = new ArrayList<>();
		List<PushSeverInfoDTO> mailList = new ArrayList<>();
		for (PushSeverInfoDTO pushSeverInfoDTO : dtoList) {
			int notice_way = pushSeverInfoDTO.getNoticeWay();
			if (notice_way == 2) {
				smsList.add(pushSeverInfoDTO);
			} else if (notice_way == 3) {
				mailList.add(pushSeverInfoDTO);
			}
		}

		PushServerRequestDTO pushServerRequestDTO;
		if (smsList.size() > 0) {
			for (PushSeverInfoDTO pushSeverInfoDTO : smsList) {
				pushServerInfoInnerResource.deletePushServerInfoById(pushSeverInfoDTO.getNo());
				pushServerRequestDTO = new PushServerRequestDTO();
				pushServerRequestDTO.setTime(pushSeverInfoDTO.getTime());
				pushServerRequestDTO.setTimeJobName(pushSeverInfoDTO.getName());
				pushServerRequestDTO.setMessage(pushSeverInfoDTO.getMessage());
				pushServerRequestDTO.setNoticeCategory(pushSeverInfoDTO.getNoticeCategory());
				pushServerRequestDTO.setNoticeCategoryDescription(pushSeverInfoDTO.getNoticeCategoryDescription());
				pushServerRequestDTO.setNoticeTitle(pushSeverInfoDTO.getNoticeTitle());
				pushServerRequestDTO.setTimeJobNoticeAddress(pushSeverInfoDTO.getNoticeAddress());
				DTO dto = sendSmsInfoInnerResource.sendInfo2User(pushServerRequestDTO);
			}
		}

		HashMap<String, Object> map;
		if (mailList.size() > 0) {
			for (PushSeverInfoDTO pushSeverInfoDTO : mailList) {
				pushServerInfoInnerResource.deletePushServerInfoById(pushSeverInfoDTO.getNo());
				map = new HashMap<>();
				map.put("message", pushSeverInfoDTO.getMessage());
				map.put("timeJobName", pushSeverInfoDTO.getTime());
				map.put("userName", pushSeverInfoDTO.getName());
				map.put("noticeTitle", pushSeverInfoDTO.getNoticeTitle());
				map.put("timeJobNoticeAddress",pushSeverInfoDTO.getNoticeAddress());
				map.put("noticeCategory", pushSeverInfoDTO.getNoticeCategory());
				map.put("noticeCategoryDescription", pushSeverInfoDTO.getNoticeCategoryDescription());
				DTO dto = sendMailInfoInnerResource.sendInfo2User(map);
			}
		}
		return ResultEnum.SUCCEED;
	}
}
