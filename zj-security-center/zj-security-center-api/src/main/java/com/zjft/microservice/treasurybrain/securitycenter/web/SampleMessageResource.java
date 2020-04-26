package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.handler.annotation.ZjMessageMapping;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SampleMessageResponseDTO;

/**
 *
 * @author zhangjs
 */
public interface SampleMessageResource {

	@ZjMessageMapping("securityWarnNoAdd")
	SampleMessageResponseDTO  SampleMessageNoAdd(SampleMessageResponseDTO testRequestDTO);

	@ZjMessageMapping("securityWarnAdd")
	SampleMessageResponseDTO SampleMessageAdd(SampleMessageResponseDTO testRequestDTO);

}
