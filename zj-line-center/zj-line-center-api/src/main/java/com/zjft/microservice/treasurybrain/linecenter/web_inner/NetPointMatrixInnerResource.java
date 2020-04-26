package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;

import java.util.List;
import java.util.Map;

public interface NetPointMatrixInnerResource {

	NetpointMatrixDTO selectByPrimaryKeyMap(Map<String, Object> map);

	List<NetpointMatrixDTO> selectNetpointMatrix(Map<String, Object> paramMap);
}
