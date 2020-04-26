package com.zjft.microservice.treasurybrain.productcenter.web_inner;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ProductPropertyInnerResource {
	int insert(Map<String, Object> GoodsPropertyMap);

	int selectByNoAndValue(@Param("propertyNo") String propertyNo, @Param("propertyValue") String propertyValue);
}
