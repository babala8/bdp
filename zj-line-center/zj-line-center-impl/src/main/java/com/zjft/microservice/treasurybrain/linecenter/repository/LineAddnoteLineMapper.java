package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

@Mapper
public interface LineAddnoteLineMapper {
	int deleteByPrimaryKey(String lineNo);

	int insert(LineTableDO record);

	int insertbyMap(Map<String, Object> paramMap);

	int insertSelective(LineTableDO record);

	LineTableDO selectByPrimaryKey(String lineNo);

	int updateByPrimaryKeySelective(LineTableDO record);

	int updateByPrimaryKey(LineTableDO record);

	List<LineTableDO> getLineListByClrNo(Map<String, Object> paramMap);

	List<LineTableDO> getLineListByDateAndClrNo(Map<String, Object> paramMap);

	List<LineTableDO> getLineListByType(Map<String, Object> paramMap);

	List<LineTableDO> getAll();

	int qryTotalRowPlan(Map<String, Object> paramMap);

	List<LineTableDO> qryAddnotesLine(Map<String, Object> paramMap);

	String getLineNoMax(String clrCenterNo);

	List<LineTableDO> rowSetList(Map<String, Object> paramMap);

	List<LineTableDO> rowSetList1(Map<String, Object> paramMap);

	int qryLineNameExist(String lineName);

	@Select("(select a.CLR_CENTER_NO,c.CENTER_NAME from LINE_TABLE a LEFT JOIN CLR_CENTER_TABLE c on " +
			"a.CLR_CENTER_NO = c.CLR_CENTER_NO where a.LINE_NO=#{lineNo,jdbcType=VARCHAR})")
	@Results({
			@Result(column = "CLR_CENTER_NO", property = "clrCenterNo", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CENTER_NAME", property = "centerName", jdbcType = JdbcType.NUMERIC)
	})
	List<Map<String, Object>> getClrCenter(String lineNo);

	int updateByMap(Map<String,Object> map);

	List<LineTableDO> qryLineListByMap(Map<String, Object> paramMap);

	/**
	 *
	 *
	 * @param paramsMap clrCenterNo 金库编号（非必输,为空查全部） lineType 线路类型
	 * @return
	 */
	List<String> getLineNosWithTypeAndClrNo(Map<String,Object> paramsMap);

}
