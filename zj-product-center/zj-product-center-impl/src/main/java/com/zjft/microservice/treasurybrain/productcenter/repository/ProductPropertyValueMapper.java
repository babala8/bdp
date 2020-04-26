package com.zjft.microservice.treasurybrain.productcenter.repository;


import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyValuePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ProductPropertyValueMapper {
	int deleteByPrimaryKey(String id);

	int insert(Map<String, Object> GoodsPropertyMap);

	int insertSelective(ProductPropertyValuePO record);

	ProductPropertyValuePO selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProductPropertyValuePO record);

	int updateByPrimaryKey(ProductPropertyValuePO record);

	int deleteInfoByPropertyNo(String propertyNo);

	int deleteInfoByProductNo(String productNo);

	/**
	 * 通过propertyNo查找ID,propertyValue
	 */
	List<ProductPropertyValuePO> selectPropertyValue(String propertyNo);

	/**
	 * 通过propertyNo查找记录条数
	 */
	int selectCountBypropertyNo(String propertyNo);

	/**
	 * 通过propertyNo查找属性值
	 */
	@Select("select PROPERTY_VALUE from PRODUCT_PROPERTY_VALUE where PROPERTY_NO=#{propertyNo,jdbcType=VARCHAR}")
	List<String> qryPropertyValueByNo(String propertyNo);

	/**
	 * 查找属性类型
	 */
	@Select("select PROPERTY_TYPE from PRODUCT_PROPERTY_KEY")
	List<Integer> qryPropertyType();

	int selectByNoAndValue(@Param("propertyNo") String propertyNo, @Param("propertyValue") String propertyValue);
}
