package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Mapper
public interface OutsourcingMapper {
	/**
	 * 查询符合查询条件的总记录数
	 *
	 * @param paramMap 查询条件
	 * @return 记录数
	 */
	Integer qryTotalRowOutsourcing(Map<String, Object> paramMap);

	/**
	 * 分页查询外包人员信息
	 *
	 * @param paramMap 查询参数
	 * @return 符合条件的外包人员列表
	 */
	List<OutsourcingPO> queryOutsourcingByPage(Map<String, Object> paramMap);

	/**
	 * 查询该编号信息是否存在
	 *
	 * @param no 人员编号
	 * @return 数据库中该人员编号对应的记录数
	 */
	Integer queryByNo(@Param("no") String no);

	/**
	 * 新增外包人员基础信息
	 *
	 * @param outsourcingPO
	 * @return
	 */
	Integer insertSelective(OutsourcingPO outsourcingPO);

	/**
	 * 修改外包人员基础信息
	 *
	 * @param outsourcingPO
	 * @return
	 */
	Integer updateByPrimaryKeySelective(OutsourcingPO outsourcingPO);

	/**
	 * 根据人员编号删除外包人员信息
	 *
	 * @param no 人员编号
	 * @return
	 */
	Integer deleteByPrimaryKey(@Param("no") String no);

	/**
	 * 没有就插入，已有就更新
	 *
	 * @param outsourcingPO
	 * @return
	 */
	Integer insertOrUpdate(OutsourcingPO outsourcingPO);

	List<String> qryByPost(int post);
}
