package com.zjft.microservice.treasurybrain.param.service.impl;

import com.zjft.microservice.treasurybrain.common.domain.SysParam;
import com.zjft.microservice.treasurybrain.common.domain.SysParamCatalogDO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.mapstruct.SysParamCatalogConverter;
import com.zjft.microservice.treasurybrain.param.mapstruct.SysParamConverter;
import com.zjft.microservice.treasurybrain.param.repository.SysParamMapper;
import com.zjft.microservice.treasurybrain.param.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName SysParamService
 * @Description 第三方系统接入接出业务层
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Slf4j
@Service
public class SysParamServiceImpl implements SysParamService {

	@Resource(name = "SysParamMapperExtend")
	private SysParamMapper sysParamMapper;

	/**
	*@MethodName: qrySysParamByName
	*@Description: 根据参数名查询参数对象
	*@Author: zhangjs
	*@Param: [paramName]
	*@Return: com.zjft.microservice.treasurybrain.param.dto.SysParamDTO
	*@Date: 2019/12/11 21:38
	**/
	@Override
	public SysParamDTO qrySysParamByName(String paramName) {
		log.info("------------[qrySysParamByName]SysParamService-------------");
		SysParamDTO dto = new SysParamDTO(RetCodeEnum.FAIL);
		SysParam sysParam = sysParamMapper.selectByParamName(paramName);
		if (sysParam == null) {
			dto.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
			return dto;
		}
		dto = SysParamConverter.INSTANCE.domain2dto(sysParam);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	*@MethodName: qrySysParamByPage
	*@Description: 分页查询系统参数列表
	*@Author: zhangjs
	*@Param: [page]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.PageDTO<com.zjft.microservice.treasurybrain.param.dto.SysParamDTO>
	*@Date: 2019/12/11 21:50
	**/
	@Override
	public PageDTO<SysParamDTO> qrySysParamByPage(Map<String, Object> page) {
		log.info("------------[qrySysParamByPage]SysParamService-------------");
		PageDTO<SysParamDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int curPage = StringUtil.objectToInt(page.get("curPage"));
		int pageSize = StringUtil.objectToInt(page.get("pageSize"));
		page.put("startRow", pageSize * (curPage - 1));
		page.put("endRow", pageSize * curPage);

		int totalRow = sysParamMapper.qryTotalRow(page);
		int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

		List<SysParam> list = sysParamMapper.queryParamByPage(page);
		List<SysParamDTO> listDTO = SysParamConverter.INSTANCE.domain2dto(list);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setCurPage(curPage);
		pageDTO.setPageSize(pageSize);
		pageDTO.setRetList(listDTO);
		return pageDTO;
	}

	/**
	*@MethodName: qrySysParamType
	*@Description: 查询所有参数类型
	*@Author: zhangjs
	*@Param: []
	*@Return: com.zjft.microservice.treasurybrain.common.dto.ListDTO<com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO>
	*@Date: 2019/12/11 21:53
	**/
	@Override
	public ListDTO<SysParamCatalogDTO> qrySysParamType() {
		log.info("------------[qrySysParamType]SysParamService-------------");
		ListDTO<SysParamCatalogDTO> sysParamCatalogDTO = new ListDTO<>(RetCodeEnum.FAIL);
		List<SysParamCatalogDO> sysParamCatalogDO = sysParamMapper.qrySysParamCatalogList();
		List<SysParamCatalogDTO> sysParamCatalogDTOList = SysParamCatalogConverter.INSTANCE.domain2dto(sysParamCatalogDO);
		sysParamCatalogDTO.setResult(RetCodeEnum.SUCCEED);
		sysParamCatalogDTO.setRetList(sysParamCatalogDTOList);
		return sysParamCatalogDTO;
	}

	/**
	*@MethodName: addSysParam
	*@Description: 增加系统对象
	*@Author: zhangjs
	*@Param: [sysParamDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 21:54
	**/
	@Override
	public DTO addSysParam(SysParamDTO sysParamDTO) {
		log.info("------------[addSysParam]SysParamService-------------");
		DTO dto1 = new DTO(RetCodeEnum.FAIL);
		SysParam sysParam = SysParamConverter.INSTANCE.dto2do(sysParamDTO);
		sysParam.setLogicId(UUID.randomUUID().toString().replaceAll("-", ""));
		int x = sysParamMapper.insert(sysParam);
		if (x == 1) {
			dto1.setResult(RetCodeEnum.SUCCEED);
		}
		return dto1;
	}

	/**
	*@MethodName: modSysParamById
	*@Description: 修改参数
	*@Author: zhangjs
	*@Param: [sysParamDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 21:54
	**/
	@Override
	public DTO modSysParamById(SysParamDTO sysParamDTO) {
		log.info("------------[modSysParamById]SysParamService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		SysParam sysParam = SysParamConverter.INSTANCE.dto2do(sysParamDTO);
		int x = sysParamMapper.updateByPrimaryKey(sysParam);
		if (x == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	/**
	*@MethodName: delSysParamById
	*@Description: 依据逻辑主键删除参数
	*@Author: zhangjs
	*@Param: [id]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 21:59
	**/
	@Override
	public DTO delSysParamById(String id) {
		log.info("------------[delSysParamById]SysParamService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		int x = sysParamMapper.deleteByPrimaryKey(id);
		if (x == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}
}
