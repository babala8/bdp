package com.zjft.microservice.treasurybrain.lock.service;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockStatusTableDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockTransTableDTO;

import java.util.Map;

/**
 * 钥匙日志管理
 *
 * @author 韩通
 * @since 2019-06-26
 */
public interface LockTransTableService {

	/**
	 * 分页查询锁具日志
	 *
	 * @param paramMap
	 * @return
	 */
	PageDTO<LockTransTableDTO> qryLockTransByPage(Map<String, Object> paramMap);
}
