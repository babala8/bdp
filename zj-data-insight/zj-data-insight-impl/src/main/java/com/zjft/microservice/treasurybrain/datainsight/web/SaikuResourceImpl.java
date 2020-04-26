package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.datainsight.service.SaikuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨光
 * @since 2019-04-01
 */
@Slf4j
@RestController
public class SaikuResourceImpl implements SaikuResource {

	private final SaikuService saikuService;

	@Autowired
	public SaikuResourceImpl(SaikuService saikuService) {
		this.saikuService = saikuService;
	}


	@Override
	public DTO getSaikuTokenByNo(String no) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL);
		try {
			String token = saikuService.getSaikuTokenByNo(no);
			dto.setElement(token);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("获取失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}
}
