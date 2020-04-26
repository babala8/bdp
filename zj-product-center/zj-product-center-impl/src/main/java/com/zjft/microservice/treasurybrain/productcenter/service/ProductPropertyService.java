package com.zjft.microservice.treasurybrain.productcenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyValueInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/04
 */
public interface ProductPropertyService {

	DTO addGoodsProperty(ProductPropertyDTO productPropertyDTO);

	ProductPropertyValueInfoDTO qryGoodsPropertyValue(String propertyNo);

	/*DTO delGoodsProperty(String propertyNo);*/

	ListDTO qryPropertyType();

	int insert(Map<String, Object> GoodsPropertyKeyMap);

	int selectCountByGoodsNo(@Param("goodsNo") String goodsNo, @Param("propertyName") String propertyName);

	String selectPropertyNoByGoodsNo(@Param("goodsNo") String goodsNo, @Param("propertyName") String propertyName);

	String selectMaxNo();

	int insert2(Map<String, Object> GoodsPropertyMap);

	int selectByNoAndValue(@Param("propertyNo") String propertyNo, @Param("propertyValue") String propertyValue);
}
