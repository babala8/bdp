package com.zjft.microservice.treasurybrain.productcenter.repository;


import com.zjft.microservice.treasurybrain.productcenter.po.ProductPropertyKeyPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ProductPropertyKeyMapper {

	/**
	 * 删除物品属性
	 */
	int deleteByPrimaryKey(List<String> propertyNoList);

	int deleteInfoByProductNo(String productNo);

	int insert(Map<String, Object> GoodsPropertyKeyMap);

	int insertSelective(ProductPropertyKeyPO record);

	ProductPropertyKeyPO selectByPrimaryKey(String propertyNo);

	int updateByPrimaryKeySelective(ProductPropertyKeyPO record);

	int updateByPrimaryKey(ProductPropertyKeyPO record);

	/**
	 * 通过goodsNo查找propertyNo
	 */
	List<String> selectPropertyNo(String goodsNo);

	/**
	 * 通过属性编号查找属性名称
	 */
	String selectPropertyNameByPropertyNo(String propertyNo);

	/**
	 * 通过属性编号查找属性类型
	 */
	@Select("select PROPERTY_TYPE from PRODUCT_PROPERTY_KEY where PROPERTY_NO=#{propertyNo,jdbcType=VARCHAR}")
	Integer selectFlagByPropertyNo(String propertyNo);

	/**
	 * 查找属性编号是否存在
	 */
	int qryExist(String propertyNo);

	/**
	 * 查找最大属性编号
	 */
	@Select("select max(PROPERTY_NO) from PRODUCT_PROPERTY_KEY")
	String selectMaxNo();

	int selectCountByGoodsNo(@Param("productNo") String goodsNo, @Param("propertyName") String propertyName);

	String selectPropertyNoByGoodsNo(@Param("productNo") String goodsNo, @Param("propertyName") String propertyName);



}
