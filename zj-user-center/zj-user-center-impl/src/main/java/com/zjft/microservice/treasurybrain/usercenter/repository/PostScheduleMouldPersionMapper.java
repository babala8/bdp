package com.zjft.microservice.treasurybrain.usercenter.repository;


import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleClassDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldOpDO;
import com.zjft.microservice.treasurybrain.usercenter.po.PostScheduleMouldPersionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostScheduleMouldPersionMapper {

	/**
	 * 删除模板人员信息
	 */
	int deleteByPrimaryKey(String mouldNo);

	/**
	 * 插入模板人员信息
	 */
	int insert(PostScheduleMouldPersionPO record);

	int insertSelective(PostScheduleMouldPersionPO record);

	/**
	 * 查询模板班次详情信息
	 */
	List<PostScheduleMouldDetailDO> qryDetailByMouldNo(String mouldNo);

	/**
	 * 查询某模板的人员详情记录条数
	 */
	@Select("select count(1) from POST_SCHEDULE_MOULD_PERSION where MOULD_NO = #{mouldNo,jdbcType=VARCHAR}")
	int qryCountByMouldNo(String mouldNo);


	/**
	 * 通过classesNo、countNo查询返回编号+名称List
	 */
	List<PostScheduleMouldOpDO> qryOpDetailByNos(Map<String, Object> map);

	/**
	 * 通过classesNo、countNo查询返回编号list
	 */
	@Select("select op_no from POST_SCHEDULE_MOULD_PERSION where classes_no=#{classesNo,jdbcType=VARCHAR} and count_no=#{countNo,jdbcType=INTEGER} " +
			"and mould_no=#{mouldNo,jdbcType=VARCHAR}")
	List<String> qryOpNos(Map<String, Object> map);

	/**
	 * 通过countNo查询返回班次list
	 */
	List<PostScheduleClassDetailDO> qryClassesNos(Map<String, Object> map);
}
