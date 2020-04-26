package com.zjft.microservice.treasurybrain.lock.service;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockStatusTableDTO;

import java.util.Map;

/**
 * 钥匙状态监控
 *
 * @author 韩通
 * @since 2019-06-26
 */
public interface LockStatusTableService {

	/**
	 * 分页查询钥匙状态
	 *
	 * @param paramMap
	 * @return
	 */
	PageDTO<LockStatusTableDTO> qryLockStatusByPage(Map<String, Object> paramMap);
}
