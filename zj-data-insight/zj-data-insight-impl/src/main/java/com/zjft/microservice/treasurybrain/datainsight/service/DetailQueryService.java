package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;

import java.util.Map;


public interface DetailQueryService {

	ObjectDTO invokeService(Map<String, String> params);

	void addDetailQueryModel(Map<String, Object> params);

	ObjectDTO qrySelfDefDetailById(String id);

	ListDTO getDetails();

	ListDTO queryGroups();

}
