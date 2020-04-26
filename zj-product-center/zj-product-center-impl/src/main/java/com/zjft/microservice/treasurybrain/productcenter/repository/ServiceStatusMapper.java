package com.zjft.microservice.treasurybrain.productcenter.repository;

import com.zjft.microservice.treasurybrain.productcenter.po.ServiceStatusPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServiceStatusMapper {

	int insert(ServiceStatusPO serviceStatusPO);

	int deleteByProductNo(Integer serviceNo);

	List<ServiceStatusPO> qryByServiceNo(Integer serviceNo);

}
