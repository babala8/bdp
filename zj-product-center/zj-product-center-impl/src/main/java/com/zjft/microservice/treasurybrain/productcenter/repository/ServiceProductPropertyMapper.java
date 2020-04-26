package com.zjft.microservice.treasurybrain.productcenter.repository;

import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyValuePO;
import com.zjft.microservice.treasurybrain.productcenter.po.ServiceProductPO;
import com.zjft.microservice.treasurybrain.productcenter.po.ServiceProductPropertyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceProductPropertyMapper {

	int insert(ServiceProductPropertyPO serviceProductPropertyPO);

	List<String> qyrProductNo(String serviceNo);

	List<ServiceProductPropertyPO> qryByServiceNo(@Param("serviceNo")String serviceNo, @Param("productNo")String productNo);

	int deleteByserviceNo(Integer serviceNo);
}
