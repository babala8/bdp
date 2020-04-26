package com.zjft.microservice.treasurybrain.lock.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.lock.dto.LockBaseInfoDTO;

import java.util.Map;

/**
 * @author 常 健
 * @since 2019/06/26
 */
public interface LockBaseInfoService {

	/**
	 * 新增锁具信息
	 */
	DTO addLockBaseInfo(LockBaseInfoDTO lockBaseInfoDTO);

	/**
	 * 分页查询锁具信息
	 */
	PageDTO<LockBaseInfoDTO> qryLockBaseInfoByPage(Map<String, Object> paramMap);

	/**
	 * 修改锁具信息
	 */
	DTO modLockBaseInfo(LockBaseInfoDTO lockBaseInfoDTO);

	/**
	 * 删除锁具信息
	 */
	DTO delLockBaseInfo(String lockCode);

	/**
	 * 查询锁具信息详情
	 */
	LockBaseInfoDTO qryLockBaseInfoDetail(String lockCode);

	/**
	 * 查询锁具信息是否存在
	 */
	int qryExistByLockCode(String lockCode);
}
