package com.zjft.microservice.treasurybrain.linecenter.repository;


import com.zjft.microservice.treasurybrain.linecenter.domain.LineWorkDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LineWorkMapper {

    int deleteByPrimaryKey(String lineWorkId);

    int insert(LineWorkDO record);

    int insertSelective(LineWorkDO record);

	LineWorkDO selectByPrimaryKey(String lineWorkId);

    int updateByPrimaryKeySelective(LineWorkDO record);

    int updateByPrimaryKey(LineWorkDO record);

	List<String> qryLineHasMapWithDate(Map<String, Object> map);

	int updateCustomerNumByNo(String lineRunNo);

	List<LineWorkDO> qryDayCordAndDetails(Map<String, Object> map);

	int insertSelectiveByMap(Map<String, Object> paramMap);

	int deleteLine(Map<String, Object> paramMap);

	List<String>  selectTheYearMonthByLineNo(Map<String, Object> paramMap);

	int qryTotalRowForMonth(Map<String, Object> paramMap);

	List<LineWorkDO> qryLineRunMapForMonth(Map<String, Object> paramMap);

	List<LineWorkDO> qryLineRunMapDetail(Map<String, Object> paramMap);

	List<String> selectNetAcountList(String lineNo);

	List<LineWorkDO> qryNetworkLineRunMapDetail(Map<String, Object> paramMap);

	int qryMonthTotalRow(Map<String, Object> paramsMap);

	List<LineWorkDO> qryMonthByPage(Map<String, Object> paramMap);

	/**
	 *
	 *
	 * @param map lineNos 线路编号列表 theYearMonth 年月
	 * @return
	 */
	List<String> getNosWithYearMonthAndLineNos(Map<String, Object> map);



	List<String> qryTaskNoByLineNo(List<String> lineWorkIdList);

	@Select("select LINE_WORK_ID from LINE_WORK_TABLE where LINE_NO = #{lineNo,jdbcType=VARCHAR}")
	List<String> qryLineWorkIdByLineNo(String lineNo);

	String qryLineWorkId(Map<String, Object> paramMap);
}
