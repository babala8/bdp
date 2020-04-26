package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;
import com.zjft.microservice.treasurybrain.pushserver.service.PushServerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/26
 */
@RestController
@Slf4j
public class PushServerInfoResourceImpl implements PushServerInfoResource {

	@Resource
	private PushServerInfoService pushServerInfoService;

	@Override
	public PageDTO<PushSeverInfoDTO> qryByPage(Map<String, Object> paramMap) {
		try {
			return pushServerInfoService.qryByPage(paramMap);
		} catch (Exception e) {
			log.error("分页查询推送信息失败！", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO updateStatus(String no) {
		try {
			return pushServerInfoService.updateStatus(no);
		} catch (Exception e) {
			log.error("更新状态失败！");
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public PushSeverInfoDTO qryMessageByNo(String no) {
		return pushServerInfoService.qryMessageByNo(no);
	}
}
