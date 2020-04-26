package com.zjft.microservice.treasurybrain.clearcenter.repository;


import com.zjft.microservice.treasurybrain.clearcenter.po.TaskCheckPO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BanknoteTaskCheckMapper {
    int deleteByPrimaryKey(String id);

    int deleteByTaskNoAndCustomerNo(@Param("taskNo") String taskNo, @Param("customerNo") String customerNo);

    int insert(TaskCheckPO record);

    int insertSelective(TaskCheckPO record);

	int insertByMap(Map<String,Object> hashMap);

    TaskCheckPO selectByPrimaryKey(String id);

	List<TaskCheckPO> selectByTaskNoAndCustomerNo(@Param("taskNo") String taskNo, @Param("customerNo") String customerNo);

	List<TaskCheckPO> selectByTaskNo(String taskNo);

	double selectAmountByTaskNoAndCustomerNo(@Param("taskNo") String taskNo, @Param("customerNo") String customerNo);

	TaskCheckPO selectByMap(Map<String,Object> hashMap);

    int updateByPrimaryKeySelective(TaskCheckPO record);

	int updateByID(Map<String,Object> hashMap);

    int updateByPrimaryKey(TaskCheckPO record);

    BigDecimal selectCheckAmount(String taskNo);
}
