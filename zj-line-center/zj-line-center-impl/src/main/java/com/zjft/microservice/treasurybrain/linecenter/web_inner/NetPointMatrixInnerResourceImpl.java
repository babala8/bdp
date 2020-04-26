package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.NetPointMatrixService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class NetPointMatrixInnerResourceImpl implements NetPointMatrixInnerResource {

	@Resource
	private NetPointMatrixService netPointMatrixService;

	@Override
	public NetpointMatrixDTO selectByPrimaryKeyMap(Map<String, Object> map) {
		return netPointMatrixService.selectByPrimaryKeyMap(map);
	}

	@Override
	public List<NetpointMatrixDTO> selectNetpointMatrix(Map<String, Object> paramMap) {
		return netPointMatrixService.selectNetpointMatrix(paramMap);
	}
}
