package com.zjft.microservice.treasurybrain.usercenter.repository;


import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldDO;
import com.zjft.microservice.treasurybrain.usercenter.po.PostScheduleMouldPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostScheduleMouldMapper {

	/**
	 * 删除模板信息
	 */
	int deleteByPrimaryKey(String mouldNo);

	int insert(PostScheduleMouldPO record);

	int insertSelective(PostScheduleMouldPO record);

	PostScheduleMouldPO selectByPrimaryKey(String mouldNo);

	int updateByPrimaryKeySelective(PostScheduleMouldPO record);

	int updateByPrimaryKey(PostScheduleMouldPO record);

	/**
	 * 查找最大编号
	 */
	@Select("select max(MOULD_NO) from POST_SCHEDULE_MOULD_TABLE")
	String qryMaxNo();

	int qryTotalRow(Map<String, Object> paramMap);

	/**
	 * 分页查询
	 */
	List<PostScheduleMouldDO> qryByPage(Map<String, Object> paramMap);

	/*
	 * 通过mouldNo查询模板信息
	 *
	 */
	List<PostScheduleDetailDO> qryInfoByMouldNo(String mouldNo);


	/**
	 * 通过orgNo、postNo查询模板信息
	 */
	List<PostScheduleMouldDO> qryAll(Map<String, Object> map);

	/**
	 * 查询模板类型
	 */
	@Select("select mould_type from POST_SCHEDULE_MOULD_TABLE where mould_no=#{mouldNo,jdbcType=VARCHAR}")
	int qryTypeByNo(String mouldNo);

	/**
	 * 查询模板名称是否存在
	 */
	@Select("select count(1) from POST_SCHEDULE_MOULD_TABLE where mould_name=#{mouldName,jdbcType=VARCHAR}")
	int qryMouldNameExist(String mouldName);
}
