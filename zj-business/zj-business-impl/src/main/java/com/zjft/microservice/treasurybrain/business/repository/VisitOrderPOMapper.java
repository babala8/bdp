package com.zjft.microservice.treasurybrain.business.repository;


import com.zjft.microservice.treasurybrain.business.domain.VisitOrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VisitOrderPOMapper {
    int deleteByPrimaryKey(@Param("customerNumber")String id,@Param("orderDate") String orderDate);

    int insert(VisitOrderDO record);

    int insertSelective(VisitOrderDO record);

    VisitOrderDO selectByPrimaryKey(@Param("customerNumber")String customerNumber,@Param("orderDate") String orderDate);

    int updateByPrimaryKeySelective(VisitOrderDO record);

    int updateByPrimaryKey(VisitOrderDO record);

	int queryTotalRow(Map<String, Object> param);

	List<VisitOrderDO> qryVisitOrderByPage(Map<String, Object> param);

	List<Map<String, Object>> qryOrderCustomers();
}
