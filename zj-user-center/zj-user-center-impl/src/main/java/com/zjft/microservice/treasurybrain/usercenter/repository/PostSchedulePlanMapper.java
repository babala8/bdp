package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleMouldOpDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostSchedulePlanDO;
import com.zjft.microservice.treasurybrain.usercenter.po.PostSchedulePlanPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostSchedulePlanMapper {

	/*
	 * 删除某月排班安排
	 *
	 */
	int deleteByPlanNo(String planNo);

	int insert(PostSchedulePlanPO record);

	int insertSelective(PostSchedulePlanPO record);

	PostSchedulePlanPO selectByPrimaryKey(PostSchedulePlanPO key);

	int updateByPlanNo(PostSchedulePlanPO postSchedulePlanPO);

	int updateByPrimaryKey(PostSchedulePlanPO record);

	/*
	 * 删除某月排班安排
	 *
	 */
	@Delete("delete from POST_SCHEDULE_PLAN_TABLE where schedule_month=#{scheduleMonth,jdbcType=VARCHAR}")
	void deleteByMonth(String scheduleMonth);

	/*
	 * 通过planNo查询信息
	 *
	 */
	List<PostSchedulePlanDO> qryPlanInfoByNo(String planNo);

	/**
	 * 通过classNo、planDate查询返回人员编号+名称List
	 */
	List<PostScheduleMouldOpDO> qryOpDetail(Map<String, Object> map);

	@Select("select count(1) from POST_SCHEDULE_PLAN_TABLE where plan_no =#{planNo,jdbcType=VARCHAR}")
	int qryCountByPlanNo(String planNo);
}
