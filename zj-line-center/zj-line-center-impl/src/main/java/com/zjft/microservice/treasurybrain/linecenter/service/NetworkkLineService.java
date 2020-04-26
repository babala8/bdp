package com.zjft.microservice.treasurybrain.linecenter.service;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 19:32
 */

public interface NetworkkLineService {

	/**
	 *
	 * 根据线路类型查询全部线路信息
	 *
	 * @param paramsMap lineType 线路类型  clrCenterNo 金库编号
	 * @return
	 */
	ListDTO<LineTableDTO> getAllLineByType(Map<String,Object> paramsMap);

	List<String> getTodayLine(String status);

	/**
	 * 查询可执行线路
	 * @return 结果
	 */
	Map<String,Object> qryLineNoList(Map<String, Object> paramMap);
}
