package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import com.zjft.microservice.treasurybrain.datainsight.repository.provider.ChartSubjectSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 杨光
 */
@Mapper
@Repository
public interface ChartSubjectMapper {
	@Delete("delete from chart_subject where SUBJECT_ID = #{subjectId,jdbcType=INTEGER}")
	int deleteByPrimaryKey(Integer subjectId);


	@Insert("insert into chart_subject (SUBJECT_ID, SUBJECT_NAME, SUBJECT_DESC) " +
			"values (#{subjectId,jdbcType=INTEGER}, #{subjectName,jdbcType=VARCHAR}, #{subjectDesc,jdbcType=VARCHAR})")
	int insert(ChartSubject record);


	@InsertProvider(type = ChartSubjectSqlProvider.class, method = "insertSelective")
	int insertSelective(ChartSubject record);


	@Select("select SUBJECT_ID, SUBJECT_NAME, SUBJECT_DESC from chart_subject where SUBJECT_ID = #{subjectId,jdbcType=INTEGER}")
	ChartSubject selectByPrimaryKey(Integer subjectId);


	@UpdateProvider(type = ChartSubjectSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ChartSubject record);


	@Update("update chart_subject " +
			"set SUBJECT_NAME = #{subjectName,jdbcType=VARCHAR},SUBJECT_DESC = #{subjectDesc,jdbcType=VARCHAR} " +
			"where SUBJECT_ID = #{subjectId,jdbcType=INTEGER}")
	int updateByPrimaryKey(ChartSubject record);


	@Select("select SUBJECT_ID, SUBJECT_NAME, SUBJECT_DESC from chart_subject")
	@Results({
			@Result(column = "SUBJECT_ID", property = "subjectId", jdbcType = JdbcType.NUMERIC, id = true),
			@Result(column = "SUBJECT_NAME", property = "subjectName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SUBJECT_DESC", property = "subjectDesc", jdbcType = JdbcType.VARCHAR)
	})
	List<ChartSubject> getChartSubjectsByOrgNo();

}