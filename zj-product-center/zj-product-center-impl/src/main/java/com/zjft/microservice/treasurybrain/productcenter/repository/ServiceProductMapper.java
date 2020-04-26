package com.zjft.microservice.treasurybrain.productcenter.repository;

import com.zjft.microservice.treasurybrain.productcenter.po.ServiceProductPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServiceProductMapper {

	int insert(ServiceProductPO serviceProductPO);

	int deleteByProductNo(Integer serviceNo);
}
