package com.zjft.microservice.treasurybrain.business.service;


import com.zjft.microservice.treasurybrain.business.dto.AddnotesLineDTO;

import java.util.List;
import java.util.Map;


/**
 * @author qfxu
 * 加钞线路管理
 * ,
 */
public interface AddnotesLineService {

	/**
	 * 查询加钞线路映射关系：线路编号、线路名称
	 * @return
	 */
	Map<String, String> getAddnotesLineMap();

}
