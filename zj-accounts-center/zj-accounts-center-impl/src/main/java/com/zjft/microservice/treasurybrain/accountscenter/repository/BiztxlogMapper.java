package com.zjft.microservice.treasurybrain.accountscenter.repository;

import com.zjft.microservice.treasurybrain.accountscenter.domain.BiztxlogDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface BiztxlogMapper {

	int qryTotalRow(Map<String, Object> paramMap);

	List<BiztxlogDO> queryInfoByPage(Map<String, Object> paramMap);

	List<Map<String, Object>> qryDevDayAvgAmt(@Param("devNo") String devNo, @Param("tranDate") String tranDate);

	List<Map<String, Object>> getMaxDateOfDev(@Param("devNo") String devNo);


	/*@Select("select sum(AMOUNT_PROCESS_CWD)/count(1) as dayAvgWdr, sum(AMOUNT_PROCESS_CDM)/count(1) as dayAvgDep " +
			"from BIZTXLOG_INIT where TERMID = #{devNo}  and TRANDATE > #{tranDate} ")
	@Results({
			@Result(column = "dayAvgWdr", property = "dayAvgWdr", jdbcType = JdbcType.NUMERIC),
			@Result(column = "dayAvgDep", property = "dayAvgDep", jdbcType = JdbcType.NUMERIC)
	})
	List<Map<String, Object>> qryDevDayAvgAmt(@Param("devNo") String devNo, @Param("tranDate") String tranDate);


	@Select("select max(TRANDATE) as maxDate from BIZTXLOG_INIT where TERMID = #{devNo} ")
	@Results({
			@Result(column = "maxDate", property = "maxDate", jdbcType = JdbcType.NUMERIC)
	})
	List<Map<String, Object>> getMaxDateOfDev(@Param("devNo") String devNo);*/

}
