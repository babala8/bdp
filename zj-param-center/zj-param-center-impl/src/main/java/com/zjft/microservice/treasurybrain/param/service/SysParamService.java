package com.zjft.microservice.treasurybrain.param.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;

import java.util.Map;

/**
 * @ClassName SysParamService
 * @Description 第三方系统接入接出业务层
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
public interface SysParamService {

	/**
	*@MethodName: qrySysParamByName
	*@Description: 根据参数名称查询参数对象
	*@Author: zhangjs
	*@Param: paramName
	*@Return: SysParamDTO
	*@Date: 2019/12/11 20:40
	**/
	SysParamDTO qrySysParamByName(String paramName);

	/**
	*@MethodName: qrySysParamByPage
	*@Description: 分页查询参数列表
	*@Author: zhangjs
	*@Param: Map<String, Object> page
	*@Return: PageDTO<SysParamDTO>
	*@Date: 2019/12/11 20:41
	**/
	PageDTO<SysParamDTO> qrySysParamByPage(Map<String, Object> page);

	/**
	*@MethodName: qrySysParamType
	*@Description: 查询所有参数列表
	*@Author: zhangjs
	*@Param: 
	*@Return: ListDTO<SysParamCatalogDTO>
	*@Date: 2019/12/11 20:41
	**/
	ListDTO<SysParamCatalogDTO> qrySysParamType();

	/**
	*@MethodName: addSysParam
	*@Description: 新增参数
	*@Author: zhangjs
	*@Param: SysParamDTO
	*@Return: DTO
	*@Date: 2019/12/11 20:42
	**/
	DTO addSysParam(SysParamDTO dto);

	/**
	*@MethodName: modSysParamById
	*@Description: 修改参数
	*@Author: zhangjs
	*@Param: SysParamDTO
	*@Return: DTO
	*@Date: 2019/12/11 20:43
	**/
	DTO modSysParamById(SysParamDTO dto);

	/**
	*@MethodName: delSysParamById
	*@Description: 删除参数
	*@Author: zhangjs
	*@Param: id
	*@Return: DTO
	*@Date: 2019/12/11 20:43
	**/
	DTO delSysParamById(String id);

}
