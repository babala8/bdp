package com.zjft.microservice.treasurybrain.linecenter.service;

import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;

import java.util.List;
import java.util.Map;

public interface NetPointMatrixService {
	NetpointMatrixDTO selectByPrimaryKeyMap(Map<String, Object> map);

	List<NetpointMatrixDTO> selectNetpointMatrix(Map<String, Object> paramMap);
}
