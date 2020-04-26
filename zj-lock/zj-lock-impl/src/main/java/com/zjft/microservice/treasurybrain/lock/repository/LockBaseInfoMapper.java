package com.zjft.microservice.treasurybrain.lock.repository;


import com.zjft.microservice.treasurybrain.lock.domain.LockBaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/06/26
 */
@Mapper
public interface LockBaseInfoMapper {

	/**
	 * 分页查询
	 */
	List<LockBaseInfoDO> qryCheckTaskByPage(Map<String, Object> paramsMap);

	/**
	 * 增加
	 */
	int insert(LockBaseInfoDO lockBaseInfoDO);

	/**
	 * 修改
	 */
	int update(LockBaseInfoDO lockBaseInfoDO);

	/**
	 * 通过锁具序列号删除
	 */
	int deleteByLockCode(String lockCode);

	/**
	 * 查询符合条件的记录总条数
	 */
	int qryTotalRow(Map<String, Object> paramsMap);

	/**
	 * 查询锁具信息详情
	 */
	LockBaseInfoDO qryLockBaseInfoDetail(String lockCode);

	/**
	 * 查询锁具是否存在
	 */
	int  qryExistByLockCode(@Param("lockCode") String lockCode);
}
