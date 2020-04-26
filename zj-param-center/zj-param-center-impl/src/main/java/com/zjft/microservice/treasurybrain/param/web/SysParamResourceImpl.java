package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName SysParamResourceImpl
 * @Description 第三方系统接入接出
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Slf4j
@RestController
public class SysParamResourceImpl implements SysParamResource {

	@Resource
	private SysParamService sysParamService;

	/**
	*@MethodName: qrySysParamByName
	*@Description: 根据参数名称查询系统参数
	*@Author: zhangjs
	*@Param: [paramName]
	*@Return: com.zjft.microservice.treasurybrain.param.dto.SysParamDTO
	*@Date: 2019/12/11 19:17
	**/
	@Override
	public SysParamDTO qrySysParamByName(String paramName) {
		log.info("------------[qrySysParamByName]SysParamResource-------------");
		SysParamDTO dto = new SysParamDTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(paramName)) {
			try {
				dto = sysParamService.qrySysParamByName(paramName);
			} catch (Exception e) {
				log.error("查询系统参数信息失败", e);
				dto.setResult(RetCodeEnum.EXCEPTION);
			}
		}
		return dto;
	}

	/**
	*@MethodName: qrySysParamByPage
	*@Description: 分页查询系统参数列表
	*@Author: zhangjs
	*@Param: [page]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.PageDTO<com.zjft.microservice.treasurybrain.param.dto.SysParamDTO>
	*@Date: 2019/12/11 19:18
	**/
	@Override
	public PageDTO<SysParamDTO> qrySysParamByPage(Map<String, Object> page) {
		log.info("------------[qrySysParamByPage]SysParamResource-------------");
		PageDTO<SysParamDTO> dto = new PageDTO<>(RetCodeEnum.SUCCEED);
		try {
			int curPage = StringUtil.objectToInt(page.get("curPage"));
			int pageSize = StringUtil.objectToInt(page.get("pageSize"));

			if (-1 == curPage) {
				page.put("curPage", 1);
			}
			if (-1 == pageSize) {
				page.put("pageSize", 20);
			}

			dto = sysParamService.qrySysParamByPage(page);
		} catch (Exception e) {
			log.error("分页查询系统参数列表失败 ", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	*@MethodName: qrySysParamType
	*@Description:  查询所有参数类型
	*@Author: zhangjs
	*@Param: []
	*@Return: com.zjft.microservice.treasurybrain.common.dto.ListDTO<com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO>
	*@Date: 2019/12/11 19:19
	**/
	@Override
	public ListDTO<SysParamCatalogDTO> qrySysParamType() {
		log.info("------------[qrySysParamType]SysParamResource-------------");
		ListDTO<SysParamCatalogDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			listDTO = sysParamService.qrySysParamType();
		} catch (Exception e) {
			log.error("查询所有参数类型失败", e);
			listDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return listDTO;
	}

	/**
	*@MethodName: addSysParam
	*@Description: 新增系统参数
	*@Author: zhangjs
	*@Param: [sysParamDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 19:20
	**/
	@Override
	public DTO addSysParam(SysParamDTO sysParamDTO) {
		log.info("------------[addSysParam]SysParamResource-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(sysParamDTO.getParamName()) && sysParamDTO.getCatalog() != null) {
			try {
				dto = sysParamService.addSysParam(sysParamDTO);
			} catch (Exception e) {
				log.error("新增系统参数失败", e);
				dto.setResult(RetCodeEnum.EXCEPTION);
			}
		}
		return dto;
	}

	/**
	*@MethodName: modSysParamById
	*@Description:  修改系统参数
	*@Author: zhangjs
	*@Param: [sysParamDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 19:20
	**/
	@Override
	public DTO modSysParamById(SysParamDTO sysParamDTO) {
		log.info("------------[modSysParamById]SysParamResource-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(sysParamDTO.getLogicId())) {
			try {
				dto = sysParamService.modSysParamById(sysParamDTO);
			} catch (Exception e) {
				log.error("修改系统参数失败", e);
				dto.setResult(RetCodeEnum.EXCEPTION);
			}
		}
		return dto;
	}

	/**
	*@MethodName: delSysParamById
	*@Description:  删除系统参数
	*@Author: zhangjs
	*@Param: [id] 参数id
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 19:21
	**/
	@Override
	public DTO delSysParamById(String id) {
		log.info("------------[delSysParamById]SysParamResource-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(id)) {
			try {
				dto = sysParamService.delSysParamById(id);
			} catch (Exception e) {
				log.error("删除系统参数失败", e);
				dto.setResult(RetCodeEnum.EXCEPTION);
			}
		}
		return dto;
	}
}

