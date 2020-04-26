package com.zjft.microservice.treasurybrain.linecenter.service.impl;


import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrix;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.NetPointMatrixConverter;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineNetPointMatrixMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.NetPointMatrixService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class NetPointMatrixServiceImpl implements NetPointMatrixService {

	@Resource
	private LineNetPointMatrixMapper lineNetPointMatrixMapper;

	@Override
	public NetpointMatrixDTO selectByPrimaryKeyMap(Map<String, Object> map) {
		NetPointMatrix netPointMatrix = lineNetPointMatrixMapper.selectByPrimaryKeyMap(map);
		return NetPointMatrixConverter.INSTANCE.domain2dto(netPointMatrix);
	}

	@Override
	public List<NetpointMatrixDTO> selectNetpointMatrix(Map<String, Object> paramMap) {
		List<NetPointMatrix> doList = lineNetPointMatrixMapper.selectNetpointMatrix(paramMap);
		return NetPointMatrixConverter.INSTANCE.domain2dto(doList);
	}
}
