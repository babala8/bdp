package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;


import java.util.Map;

public interface SelfProductService {

	Map<String, Object> applyForSelfProduct(TransferTaskInfoDTO transferTaskInfoDTO);
}
