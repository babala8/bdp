package com.zjft.microservice.treasurybrain.business.repository;


import com.zjft.microservice.treasurybrain.business.domain.TaskInOut;
import com.zjft.microservice.treasurybrain.business.dto.CurrencyTypeListDTO;
import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;
import com.zjft.microservice.treasurybrain.business.po.TransferTaskInOutPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Mapper
public interface TaskInOutMapper {

	int deleteByPrimaryKey(BigDecimal id);

	int deleteByTaskNo(String taskNo);

	int deleteByTaskNoAndCustomerNo(@Param("taskNo")String taskNo,@Param("customerNo")String customerNo);

	int insert(TransferTaskInOutPo record);

	int insertSelective(TransferTaskInOutPo record);

	int insertSelectiveByMap(Map<String, Object> paramMap);

	List<TransferTaskInOutPo> qryTaskInOutList(String taskNo);

	int updateByPrimaryKeyMap(Map<String, Object> paramMap);

	TransferTaskInOutPo selectByPrimaryKey(TransferTaskInOutPo id);

	int updateByPrimaryKeySelective(TransferTaskInOutPo record);

	int updateByTaskNo(TransferTaskInOutPo record);

	int updateByPrimaryKey(TransferTaskInOutPo record);

	String selectSumAmount(Map<String, Object> paramMap);

	List<CurrencyTypeListDTO>  selectCurrencyTypeList(Map<String, Object> paramMap);

	List<TransferTaskInOutPo> selectByTaskNo(String taskNo);

}
