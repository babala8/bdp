package com.zjft.microservice.treasurybrain.productcenter.repository;


import com.zjft.microservice.treasurybrain.productcenter.po.ProductBaseTablePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductTableMapper {
	int deleteByPrimaryKey(String productNo);

	int insert(ProductBaseTablePO record);

	int insertSelective(ProductBaseTablePO record);

	ProductBaseTablePO selectByPrimaryKey(String productNo);

	int updateByPrimaryKeySelective(ProductBaseTablePO record);

	int updateByPrimaryKey(ProductBaseTablePO record);

	/**
	 * 查询符合条件的记录总条数
	 */
	int qryTotalRow(Map<String, Object> paramsMap);

	List<ProductBaseTablePO> qryGoodsBase(Map<String, Object> paramsMap);

	/**
	 * 查询编号是否已经存在
	 */
	int qryExist(String goodsNo);

	/**
	 * 通过商品名称查找上级商品编号
	 */
	String selectUpperNoByName(String propertyName);

	/**
	 * 通过商品编号查找所属分类
	 */
	String selectUpperName(String goodsNo);

	/**
	 * 通过商品编号查找商品名称
	 */
	String selectGoodsNameByGoodsNo(String goodsNo);

	/**
	 * 查询商品所属信息
	 */
	List<ProductBaseTablePO> qryGoodsInfo();

	/**
	 * 查询属性编号
	 */
	@Select("select PROPERTY_NO from PRODUCT_PROPERTY_KEY where PRODUCT_NO=#{productNo,jdbcType=VARCHAR}")
	List<String> qryPropertyNo(String productNo);

	/**
	 * 查询名称是否已经存在
	 */
	int qryNameExist(String goodsName);

}
