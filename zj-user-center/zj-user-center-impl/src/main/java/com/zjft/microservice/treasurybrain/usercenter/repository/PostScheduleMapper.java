package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleDO;
import com.zjft.microservice.treasurybrain.usercenter.domain.PostScheduleDetailDO;
import com.zjft.microservice.treasurybrain.usercenter.po.PostSchedulePO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/11/19
 */
@Mapper
public interface PostScheduleMapper {

	int insert(PostSchedulePO postSchedulePO);

	/*
	 * 通过mouldNo、countNo查询模板信息
	 *
	 */
	@Select("select a.*,b.OP_NO,b.CLASSES_NO,b.COUNT_NO from POST_SCHEDULE_MOULD_TABLE a left join POST_SCHEDULE_MOULD_PERSION b on a.MOULD_NO=b.MOULD_NO\n" +
			"where a.MOULD_NO=#{mouldNo,jdbcType=VARCHAR} and b.countNo=#{countNo,jdbcType=INTEGER}")
	PostScheduleDetailDO qryInfoByNos(@Param("mouldNo") String mouldNo, @Param("countNo") Integer countNo);

	/*
	 * 删除某月排班安排
	 *
	 */
	@Delete("delete from POST_SCHEDULE_TABLE where schedule_month=#{scheduleMonth,jdbcType=VARCHAR}")
	void deleteByMonth(String scheduleMonth);

	int qryTotalRow(Map<String, Object> paramMap);

	List<PostScheduleDO> qryByPage(Map<String, Object> paramMap);

	@Delete("delete from POST_SCHEDULE_TABLE where plan_no=#{planNo,jdbcType=VARCHAR}")
	int delete(String planNo);
}
