package com.zjft.microservice.treasurybrain.business.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author chenshen
 * @date 2019/1/19
 */
@Mapper
@Repository
public interface AddnotesAmountMapper {

	/**
	 * 查询该设备最近一年平均加钞额
	 * @param devNo
	 * @return
	 */
	@Select("SELECT NVL(TRUNC(SUM(ADDNOTES_DEV_AMOUNT)/COUNT(1)/10000), 0) * 10000 ADDNOTES_DEV_AMOUNT FROM ADDNOTES_DATA\n" +
			"WHERE DEV_NO = #{devNo} AND ADDNOTES_DEV_TIME > TO_CHAR(SYSDATE - INTERVAL '1' YEAR,'yyyy-mm-dd hh24:mi:ss');")
	int getAvgAddCashAmountHistory(String devNo);

	/**
	 * 获取正常加钞值
	 * @param devNo
	 * @return
	 */
	@Select("select t.normal_addnotes_amount from normal_addnotes_amount_info t where t.dev_no = #{devNo} order by t.mod_date desc")
	List<Map<String, Integer>> getNormalAmount(String devNo);

	/**
	 * 根据参数值获取参数名称
	 * @param paramName
	 * @return
	 */
	@Select("select param_value from sys_param where param_name = #{paramName}")
	String getSysParamValue(String paramName);

	/**
	 * 查询加钞额
	 * @param devNo
	 * @param startTime
	 * @return
	 */
	@Select("select t.dev_no,t.addnotes_plan_amount,t.cash_out_time from addnotes_data t where t.dev_no = #{devNo} " +
			"and t.cash_out_time >= #{startTime} and t.addnotes_plan_amount > 0 and t.dev_back_amount > 0 order by t.cash_out_time")
	List<Map<String, String>> getAddnotesAmount(String devNo, String startTime);


	/**
	 * 查询加钞历史
	 * @param devNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("select t.dev_no,t.addnotes_plan_amount,t.cash_out_time from addnotes_data t where t.dev_no = #{devNo} " +
			"and t.cash_out_time >= #{startTime} and t.cash_out_time < #{endTime} and t.addnotes_plan_amount > 0 " +
			"and t.dev_back_amount > 0 order by t.cash_out_time")
	List<Map<String, String>> getAddnotesAmountB(String devNo, String startTime, String endTime);

}
