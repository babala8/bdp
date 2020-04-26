package com.zjft.microservice.treasurybrain.lock.repository;

import com.zjft.microservice.treasurybrain.lock.domain.LockTransTableDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 锁具日志管理
 *
 * @author 韩通
 * @since 2019-06-26
 */
@Mapper
public interface LockTransTableMapper {

	/**
	 * 根据条件查询锁具总数
	 *
	 * @param params
	 * @return
	 */
	int qryTotalRowLock(Map<String, Object> params);

	/**
	 * 分页查询锁具状态
	 *
	 * @param params
	 * @return
	 */
	List<LockTransTableDO> qryLockTransByPage(Map<String, Object> params);
}
