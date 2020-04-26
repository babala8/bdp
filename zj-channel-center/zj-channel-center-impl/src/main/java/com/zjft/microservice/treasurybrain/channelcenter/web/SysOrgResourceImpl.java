package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.OrgBusinessTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.SysOrgService;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张笑
 */
@Slf4j
@RestController
public class SysOrgResourceImpl implements SysOrgResource {
	@Resource
	private SysOrgService sysOrgService;

	/**
	 * 分页查询系统参数列表
	 *
	 * @param page Map对象  里面存放fuzzyOrgNo、orgNo、orgName、orgGrade、curPage和pageSize
	 * @return PageDTO对象
	 */
	@Override
	public PageDTO<SysOrgDTO> qrySysOrgByPage(Map<String, Object> page) {
		PageDTO<SysOrgDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			// 当前页面
			int curPage = StringUtil.objectToInt(page.get("curPage"));
			// 页面大小
			int pageSize = StringUtil.objectToInt(page.get("pageSize"));
			if (-1 == curPage) {
				page.put("curPage", 1);
			}
			if (-1 == pageSize) {
				page.put("pageSize", 20);
			}
			pageDTO = sysOrgService.qrySysOrgByPage(page);
		} catch (Exception e) {
			log.error("分页查询机构信息列表失败", e);
			pageDTO.setResult(RetCodeEnum.FAIL);
		}
		return pageDTO;
	}

	/**
	 * 查询所有机构的类型
	 *
	 * @return ListDTO
	 */
	@Override
	public ListDTO<SysOrgGradeDTO> qrySysOrgGrade() {
		ListDTO<SysOrgGradeDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			listDTO = sysOrgService.qrySysOrgGrade();
			listDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("查询所有机构类型失败", e);
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}

	/**
	 * 添加机构信息
	 *
	 * @param sysOrgDTO 机构
	 * @return DTO
	 */
	@Override
	public DTO addSysOrg(SysOrgDTO sysOrgDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto = sysOrgService.addSysOrg(sysOrgDTO);

		} catch (Exception e) {
			log.error("添加机构失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	 * 修改机构信息
	 *
	 * @param sysOrgDTO 机构
	 * @return DTO
	 */
	@Override
	public DTO modSysOrgById(SysOrgDTO sysOrgDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto = sysOrgService.modSysOrgById(sysOrgDTO);

		} catch (Exception e) {
			log.error("修改机构失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	 * 删除机构
	 *
	 * @param no 机构号
	 * @return DTO
	 */
	@Override
	public DTO delSysOrgById(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(no)) {
			try {
				dto = sysOrgService.delSysOrgById(no);

			} catch (Exception e) {
				log.error("删除机构失败", e);
				dto.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto;
	}

	/**
	 * 查询父机构的下一级机构
	 *
	 * @param parentOrgNo 父机构
	 * @return ListDTO
	 */
	@Override
	public ListDTO qryChildrenOrgByAuth(String parentOrgNo) {
		ListDTO<SysOrgDTO> listDTO;
		try {

			listDTO = sysOrgService.qryChildrenOrgByAuth(parentOrgNo);
		} catch (Exception e) {
			log.error("查询子机构失败", e);
			listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		}

		return listDTO;
	}

	/**
	 * 查询机构详情
	 *
	 * @param no 机构编号
	 * @return SysOrgDTO
	 */
	@Override
	public SysOrgDTO qrySysOrgDetailByNo(String no) {
		SysOrgDTO sysOrgDTO = new SysOrgDTO(RetCodeEnum.FAIL);
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return sysOrgDTO;
			}
			sysOrgDTO = sysOrgService.qrySysOrgDetailByNo(no);
		} catch (Exception e) {
			log.error("查询机构详情失败", e);
			sysOrgDTO = new SysOrgDTO(RetCodeEnum.FAIL);
		}
		return sysOrgDTO;
	}

	/**
	 * 模糊查询机构列表
	 *
	 * @param orgName 机构名称
	 * @return SysOrgDTO
	 */
	@Override
	public ListDTO qryOrgFuzzyByAuth(String orgName) {
		ListDTO<SysOrgDTO> listDTO;
		try {
			listDTO = sysOrgService.qryOrgFuzzyByAuth(orgName);
		} catch (Exception e) {
			log.error("模糊查询机构列表失败", e);
			listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		}
		return listDTO;
	}

	/**
	 * @Description
	 * @Param [clrOrgNo]
	 */
	@Override
	public ListDTO<OrgBusinessTimeDTO> qryOrgBusinessTime(String orgNo){
		ListDTO<OrgBusinessTimeDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			listDTO = sysOrgService.qryOrgBusinessTime(orgNo);
			listDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("查询所有机构类型失败", e);
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}

	/**
	 * 加钞周期调整，需要同步生成线路运行图失败
	 */
	@Override
	public DTO modOrgBusinessTime(OrgBusinessTimeDTO dto) {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "调整失败");
		try {
			dto1 = sysOrgService.modOrgBusinessTime(dto);
		} catch (Exception e) {
			dto1.setRetException("网点营业时间调整失败！");
			log.error("网点营业时间调整", e);
		}
		return dto1;
	}

	@Override
	public ListDTO<SysOrgDTO> getNetpointsByClrCenterNo(String clrCenterNo) {

		ListDTO<SysOrgDTO> dto = new ListDTO<SysOrgDTO>(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> retMap = sysOrgService.getNetpointsByClrCenterNo(clrCenterNo);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

			List<SysOrgDTO> LineList = (List<SysOrgDTO>) retMap.get("netPointList");
			dto.setRetList(LineList);
			return dto;
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	@Override
	public ListDTO qryPointInMap() {
		// 需求：在地图中获取网点
		// 思路：调用service层的qryPointInMap()方法，获取包含网点信息的列表
		// 输出：ListDTO对象 输出从数据库中查询到的网点信息
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			//调用service层方法获取地图中网点
			dto = sysOrgService.qryPointInMap();
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO getNetpointsWithDevsOfGroup(Map<String, Object> paramMap) {
		log.info("------------[getNetpointsWithDevsOfGroup]AddnotesPlanWebService-------------");
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(paramMap.get("groupNo"));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("groupNo", groupNo);
			Map<String, Object> retMap = sysOrgService.getNetpointsWithDevsOfGroup(JSONUtil.createJsonString(params));

			List retList = (List) retMap.get("retList");
			dto.setRetList(retList);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询分组下的网点信息失败:", e);
			dto.setRetException("查询分组下的网点信息失败:");
			return dto;
		}
		return dto;
	}

	@Override
	public ListDTO qryPointsByCity(String cityName) {
		// 需求：根据城市名称查询网点列表
		// 思路：调用service层的qryOrgByCity()方法，根据城市名称获取该城市中的网点列表
		// 输入：城市名称，不能为空
		// 输出：ListDTO对象 输出从数据库中查询到的该城市下的网点信息
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			//判断城市名称是否为空或者为空格或者包含空格
			if ("".equals(cityName) || cityName.contains(" ")) {
				return dto;
			}
			//调用service层方法，返回查询结果
			ListDTO listDTO = sysOrgService.qryPointsByCityName(cityName);

			//如果查询结果为空
			if (listDTO.getRetList().isEmpty()) {
				dto.setRetCode(RetCodeEnum.RESULT_EMPTY.getCode());
				dto.setRetMsg(RetCodeEnum.RESULT_EMPTY.getTip());
				return dto;
			}
			dto.setRetList(listDTO.getRetList());
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

}
