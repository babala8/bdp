package com.zjft.microservice.treasurybrain.linecenter.repository;

//import com.zjft.microservice.treasurybrain.linecenter.domain.LineCallCustomerLineRunDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.CallCustomerLineRunMonthDO;
import com.zjft.microservice.treasurybrain.linecenter.po.LineWorkPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/23 16:06
 */
@Mapper
public interface LineCallCustomerLineRunMapper {

	int qryMonthTotalRow(Map<String, Object> paramsMap);

	List<CallCustomerLineRunMonthDO> qryMonthByPage(Map<String, Object> paramMap);

//	List<LineCallCustomerLineRunDO> qryDayCordAndDetails(Map<String, Object> map);

	int updateCustomerNumByNo(String lineRunNo);

	/**
	 *
	 *
	 * @param map lineNos 线路编号列表 theYearMonth 年月
	 * @return
	 */
	List<String> getNosWithYearMonthAndLineNos(Map<String, Object> map);

	int deleteByNo(String lineRunNo);

	int insert(LineWorkPO lineCallCustomerLineRunPO);

	String  selectLineRunNo(Map<String, Object> paramMap);

}
