package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevTypeDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

/**
 * @author 杨光
 * @since 2019-04-02
 */
public interface DevTypeService {

	PageDTO<DevTypeDTO> queryDevTypeList(String name);


	DTO addDevType(DevTypeDTO dto);


	DTO modDevType(DevTypeDTO dto);


	DTO delDevTypeByNo(String no);
}
