package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 19:41
 */
@Mapper
public interface LineTableMapper {

	/**
	 *
	 *
	 * @param paramsMap lineType 线路类型 orgNo 用户机构
	 * @return
	 */
	List<LineTableDO> getLineByLineType(Map<String,Object> paramsMap);

	List<String> getTodayLine(@Param("status") String status, @Param("planFinishDate") String planFinishDate, @Param("opNo") String opNo);

	@Select("(select LINE_NAME from LINE_TABLE where LINE_NO=#{lineNo,jdbcType=VARCHAR})")
	String getLineName(String lineNo);

	int deleteByPrimaryKey(String lineNo);

	int insertbyMap(Map<String, Object> paramMap);

	LineTableDO selectByPrimaryKey(String lineNo);

	@Select("(select a.CLR_CENTER_NO,c.CENTER_NAME from LINE_TABLE a LEFT JOIN CLR_CENTER_TABLE c on " +
			"a.CLR_CENTER_NO = c.CLR_CENTER_NO where a.LINE_NO=#{lineNo,jdbcType=VARCHAR})")
	@Results({
			@Result(column = "CLR_CENTER_NO", property = "clrCenterNo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CENTER_NAME", property = "centerName", jdbcType = JdbcType.NUMERIC)
	})
	List<Map<String, Object>> getClrCenter(String lineNo);

	List<LineTableDO> getAll();

	int qryTotalRowPlan(Map<String, Object> paramMap);

	List<LineTableDO> rowSetList(Map<String, Object> paramMap);

	List<LineTableDO> rowSetList1(Map<String, Object> paramMap);

	/**
	 *
	 *
	 * @param paramsMap clrCenterNo 金库编号（非必输,为空查全部） lineType 线路类型
	 * @return
	 */
	List<String> getLineNosWithTypeAndClrNo(Map<String,Object> paramsMap);

	int updateByMap(Map<String,Object> map);

	List<LineTableDO> qryLineListByMap(Map<String, Object> paramMap);

	List<LineTableDO> getLineListByDateAndClrNo(Map<String, Object> paramMap);

	List<LineTableDO> qryAddnotesLine(Map<String, Object> paramMap);

	int qryLineNameExist(String lineName);

	String getLineNoMax(String clrCenterNo);

	int insert(LineTableDO record);




}
