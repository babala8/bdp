package com.zjft.microservice.treasurybrain.param.repository;

import com.zjft.microservice.treasurybrain.common.domain.SysParam;
import com.zjft.microservice.treasurybrain.common.domain.SysParamCatalogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysParamMapper
 * @Description 第三方系统接入接出数据层
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Mapper
@Repository("SysParamMapperExtend")
public interface SysParamMapper {

	/**
	*@MethodName: deleteByPrimaryKey
	*@Description: 依据逻辑主键删除
	*@Author: zhangjs
	*@Param: logicId 逻辑主键
	*@Return: int 变动数
	*@Date: 2019/12/11 21:13
	**/
	int deleteByPrimaryKey(String logicId);

	/**
	*@MethodName: insert
	*@Description: 插入单条数据
	*@Author: zhangjs
	*@Param: record 参数对象
	*@Return: int 变动数
	*@Date: 2019/12/11 21:17
	**/
	int insert(SysParam record);

	/**
	*@MethodName: insertSelective
	*@Description: 插入单条数据，支持null
	*@Author: zhangjs
	*@Param: record 参数对象
	*@Return: int 变动数
	*@Date: 2019/12/11 21:18
	**/
	int insertSelective(SysParam record);

	/**
	*@MethodName: selectByPrimaryKey
	*@Description: 依据逻辑主键查询对应数据
	*@Author: zhangjs
	*@Param: logicId 逻辑主键
	*@Return: SysParam 参数对象
	*@Date: 2019/12/11 21:19
	**/
	SysParam selectByPrimaryKey(String logicId);

	/**
	*@MethodName: updateByPrimaryKeySelective
	*@Description: 修改参数，支持null
	*@Author: zhangjs
	*@Param: record 参数对象
	*@Return: int 变动数
	*@Date: 2019/12/11 21:20
	**/
	int updateByPrimaryKeySelective(SysParam record);

	/**
	*@MethodName: updateByPrimaryKey
	*@Description: 修改参数
	*@Author: zhangjs
	*@Param: record 参数对象
	*@Return: int 变动数
	*@Date: 2019/12/11 21:21
	**/
	int updateByPrimaryKey(SysParam record);

	/**
	*@MethodName: qrySysParamCatalogList
	*@Description: 查询所有的参数类型
	*@Author: zhangjs
	*@Param: []
	*@Return: java.util.List<com.zjft.microservice.treasurybrain.common.domain.SysParamCatalogDO> 参数类型列表
	*@Date: 2019/12/11 21:21
	**/
	@Select("select CATALOG,CATALOG_NAME,DESCRIPTION from SYS_PARAM_CATALOG ")
	@Results({
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.NUMERIC, id = true),
			@Result(column = "CATALOG_NAME", property = "catalogName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPTION", property = "description", jdbcType = JdbcType.VARCHAR),
	})
	List<SysParamCatalogDO> qrySysParamCatalogList();

	/**
	*@MethodName: getAllList
	*@Description: 查询出所有参数列表
	*@Author: zhangjs
	*@Param: 
	*@Return: List<SysParam> 参数列表
	*@Date: 2019/12/11 21:23
	**/
	List<SysParam> getAllList();

	/**
	*@MethodName: selectByParamName
	*@Description: 依据参数名称查询出参数对象
	*@Author: zhangjs
	*@Param: paramName 参数名称
	*@Return: SysParam 参数对象
	*@Date: 2019/12/11 21:25
	**/
	SysParam selectByParamName(String paramName);

	/**
	*@MethodName: qrySysRunParam
	*@Description: 查询运行的参数列表
	*@Author: zhangjs
	*@Param: 
	*@Return: List<SysParam> 参数列表
	*@Date: 2019/12/11 21:25
	**/
	List<SysParam> qrySysRunParam();

	/**
	*@MethodName: queryParamByPage
	*@Description: 依据查询项查询出对象列表
	*@Author: zhangjs
	*@Param: Map<String, Object> map 依据查询项查询出对象列表
	*@Return: List<SysParam> 参数列表
	*@Date: 2019/12/11 21:25
	**/
	List<SysParam> queryParamByPage(Map<String, Object> map);

	/**
	*@MethodName: qryValueByName
	*@Description: 依据参数名称查询参数值
	*@Author: zhangjs
	*@Param: paramName 参数名称
	*@Return: String 参数值
	*@Date: 2019/12/11 21:25
	**/
	String qryValueByName(String paramName);

	/**
	*@MethodName: qryTotalRow
	*@Description: 查询数据量
	*@Author: zhangjs
	*@Param: Map<String, Object> map 查询条件
	*@Return: int 数据量
	*@Date: 2019/12/11 21:25
	**/
	int qryTotalRow(Map<String, Object> map);

}
