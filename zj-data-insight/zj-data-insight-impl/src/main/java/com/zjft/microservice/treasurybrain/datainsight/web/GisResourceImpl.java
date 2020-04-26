package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.dto.GisPointInfoDTO;
import com.zjft.microservice.treasurybrain.datainsight.service.GisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author 杨光
 * @author 常健
 * @since 2019-03-18
 */
@Slf4j
@RestController
public class GisResourceImpl implements GisResource {

	private final GisService gisService;

	@Autowired
	public GisResourceImpl(GisService gisService) {
		this.gisService = gisService;
	}

	@Override
	public DTO addTemplate(Map<String, Object> params) {
		log.info("------------[addTemplate]GisResource-------------");
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		try {
			String orgNo = StringUtil.parseString(params.get("orgNo"));
			if (StringUtil.isNullorEmpty(orgNo)) {
				return new DTO(RetCodeEnum.FAIL);
			}
			gisService.addGisPointInfo(params);
		} catch (Exception e) {
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public GisPointInfoDTO qryTemplate(String orgNo) {
		log.info("------------[qryTemplate]GisResource-------------");
		GisPointInfoDTO gisPointInfoDTO;
		try {
			gisPointInfoDTO = gisService.qryGisPointInfo(orgNo);
		} catch (Exception e) {
			log.error("查找失败", e);
			gisPointInfoDTO = new GisPointInfoDTO(RetCodeEnum.FAIL);
		}
		return gisPointInfoDTO;
	}
}
