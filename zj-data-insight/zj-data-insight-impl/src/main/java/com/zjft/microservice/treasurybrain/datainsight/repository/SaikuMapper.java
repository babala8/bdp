package com.zjft.microservice.treasurybrain.datainsight.repository;


import com.zjft.microservice.treasurybrain.datainsight.domain.ChartType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 杨光
 * @since 2019-04-01
 */
@Mapper
@Repository
public interface SaikuMapper {

	@Select("select token from saiku_user_group where GROUP_ID = #{userNo,jdbcType=VARCHAR} ")
	String getSaikuTokenByNo(String userNo);

}