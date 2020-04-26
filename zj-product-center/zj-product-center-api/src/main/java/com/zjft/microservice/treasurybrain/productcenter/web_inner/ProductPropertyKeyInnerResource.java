package com.zjft.microservice.treasurybrain.productcenter.web_inner;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ProductPropertyKeyInnerResource {
	int insert(Map<String, Object> GoodsPropertyKeyMap);

	int selectCountByGoodsNo(@Param("goodsNo") String goodsNo, @Param("propertyName") String propertyName);

	String selectPropertyNoByGoodsNo(@Param("goodsNo") String goodsNo, @Param("propertyName") String propertyName);

	String selectMaxNo();
}
