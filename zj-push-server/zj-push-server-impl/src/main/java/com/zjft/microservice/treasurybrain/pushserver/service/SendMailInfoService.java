package com.zjft.microservice.treasurybrain.pushserver.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;

import java.util.Map;

/**
 * @author 韩 通
 * @since 2020-02-24
 */
public interface SendMailInfoService {

	DTO sendInfo2User(Map<String,Object> map);

	DTO sendInfo2Roles(Map<String,Object> map);

	DTO sendInfo2All(Map<String,Object> map);
}
