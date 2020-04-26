package com.zjft.microservice.treasurybrain.lock.repository;

import com.zjft.microservice.treasurybrain.lock.po.LockStatusTablePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 锁具状态监控
 *
 * @author 韩通
 * @since 2019-06-26
 */
@Mapper
public interface LockStatusTableMapper {

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
	List<LockStatusTablePO> qryLockStatusByPage(Map<String, Object> params);
}
