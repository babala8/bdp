package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import com.zjft.microservice.treasurybrain.datainsight.domain.InsightUserConfigDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 杨光
 * @author 常健
 */
@Mapper
@Repository
public interface InsightUserConfigMapper {


	@Update("update INSIGHT_USER_CONFIG u " +
			"set u.USER_DEFAULT_VIEW = #{usrDefaultView,jdbcType=VARCHAR}" +
			" where u.username= #{username,jdbcType=VARCHAR} and  subject_id =  #{subjectId,jdbcType=INTEGER}")
	int updateUsrDefaultViewByUsername(@Param("username")String username, @Param("subjectId")int subjectId, @Param("usrDefaultView")String usrDefaultView);


	@Select("select username,user_default_view,subject_id from INSIGHT_USER_CONFIG " +
			"where username= #{username,jdbcType=VARCHAR} and  subject_id =  #{subjectId,jdbcType=INTEGER}")
	@Results({
			@Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "user_default_view", property = "userDefaultView", jdbcType = JdbcType.VARCHAR),
			@Result(column = "subject_id", property = "subjectId", jdbcType = JdbcType.INTEGER)
	})
	InsightUserConfigDO selectUserConfigBySubjectId(@Param("username")String username,@Param("subjectId")int subjectId);


	@Insert("insert into INSIGHT_USER_CONFIG (username, user_default_view, subject_id) " +
			"values (#{username,jdbcType=VARCHAR}, empty_clob(), #{subjectId,jdbcType=INTEGER})")
	int insertUserConfigByUsername(@Param("username")String username,@Param("subjectId")int subjectId);


	@Select("select count(1) from INSIGHT_USER_CONFIG where username= #{username,jdbcType=VARCHAR} and  subject_id =  #{subjectId,jdbcType=INTEGER}")
	int queryRowByUsername(@Param("username")String username,@Param("subjectId")int subjectId);


	@Select("select subject_id,subject_name,subject_desc from CHART_SUBJECT " +
			"where subject_id in (select subject_id from  INSIGHT_USER_CONFIG where  username= #{username,jdbcType=VARCHAR})")
	@Results({
			@Result(column = "subject_id", property = "subjectId", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "subject_name", property = "subjectName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "subject_desc", property = "subjectDesc", jdbcType = JdbcType.VARCHAR)
	})
	List<ChartSubject>  getUserSubjectsByUsername(@Param("username")String username);

}
