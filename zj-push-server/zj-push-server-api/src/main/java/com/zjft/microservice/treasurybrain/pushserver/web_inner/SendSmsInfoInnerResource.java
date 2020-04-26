package com.zjft.microservice.treasurybrain.pushserver.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushServerRequestDTO;

/**
 * @author 常 健
 * @since 2020/2/25
 */
public interface SendSmsInfoInnerResource {

		DTO sendInfo2User(PushServerRequestDTO pushServerRequestDTO);

		DTO sendInfo2Roles(PushServerRequestDTO pushServerRequestDTO);

		DTO sendInfo2All(PushServerRequestDTO pushServerRequestDTO);
}
