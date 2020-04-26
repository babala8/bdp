package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevBaseInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevBaseInfoService;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 徐全发
 * @author 杨光
 * @since 2019-03-20
 */
@Slf4j
@RestController
public class DevBaseInfoResourceImpl implements DevBaseInfoResource {

	@Resource
	private DevBaseInfoService devBaseInfoService;

	@Resource
	private SysUserResource sysUserResource;

	@Override
	public ListDTO<DevBaseInfoDTO> qryDevInfoByClrNo(String clrCenterNo) {
		ListDTO<DevBaseInfoDTO> dto = new ListDTO<DevBaseInfoDTO>(RetCodeEnum.FAIL);
		try {
			dto = devBaseInfoService.qryDevInfoByClrNo(clrCenterNo);
		} catch (Exception e) {
			log.error("查询清机中心下的所有设备失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	 * 设备列表查询
	 *
	 * @param page no、address、ip、addnotesLine、orgNo、clrCenterNo、devVendor、devCatalog、devType、devTypeCash、curPage、pageSize
	 * @return PageDTO<DevBaseInfoDTO>
	 */
	@Override
	public PageDTO<DevBaseInfoDTO> queryDevInfoList(Map<String, Object> page) {
		PageDTO<DevBaseInfoDTO> pageDTO;
		try {
			int curPage = StringUtil.objectToInt(page.get("curPage"));
			int pageSize = StringUtil.objectToInt(page.get("pageSize"));

			if (-1 == curPage) {
				page.put("curPage", 1);
			}
			if (-1 == pageSize) {
				page.put("pageSize", 20);
			}
			pageDTO = devBaseInfoService.queryDevInfoList(page);

		} catch (Exception e) {
			pageDTO = new PageDTO<>(RetCodeEnum.EXCEPTION);
			log.info("[queryDevInfoList] :", e);
		}
		return pageDTO;
	}

	@Override
	public DTO addDevInfo(DevBaseInfoDTO devBaseInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(devBaseInfoDTO.getNo())) {
			try {
				dto = devBaseInfoService.addDevInfo(devBaseInfoDTO);

			} catch (Exception e) {
				log.error("添加设备信息失败", e);
				dto.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto;
	}


	@Override
	public DTO modDevInfo(DevBaseInfoDTO devBaseInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(devBaseInfoDTO.getNo())) {
			try {
				dto = devBaseInfoService.modDevInfo(devBaseInfoDTO);

			} catch (Exception e) {
				log.error("修改设备信息失败", e);
				dto.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto;
	}

	@Override
	public DTO delDevInfoByNo(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(no)) {
			try {
				dto = devBaseInfoService.delDevInfoByNo(no);
			} catch (Exception e) {
				log.error("删除设备失败", e);
				dto.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto;
	}

	@Override
	public ListDTO<DevCatalogDTO> qryDevCatalog() {
		ListDTO<DevCatalogDTO> dto;
		try {
			dto = devBaseInfoService.qryDevCatalog();

		} catch (Exception e) {
			log.error("查询清机中心下的所有设备失败", e);
			dto = new ListDTO<>(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ObjectDTO getDevBaseInfoByNo(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询设备信息失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			String devNo = StringUtil.parseString(paramMap.get("devNo"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("devNo", devNo);
			params.put("orgNo", orgNo);

			Map<String, Object> retMap = devBaseInfoService.getDevBaseInfoByNo(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询设备信息失败:", e);
			dto.setRetException("查询设备信息失败!");
		}
		return dto;
	}

	@Override
	public DevClrTimeParamListDTO qryByDevNo(String devNo) {
		try{
			return devBaseInfoService.qryByDevNo(devNo);
		}catch (Exception e){
			log.error("查询单台设备的清机周期信息发生异常：",e);
			return new DevClrTimeParamListDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<List> qryDevsInfoByOrgNo(String atmCode) {
		ListDTO<List> dto = new ListDTO<List>(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			String jsonString =
					"[{\"devNo\":\"000001\",\"address\":\"丁兰街道办\",   \"devCatalogName\":\"CRS\",\"devTypeName\":\"HT2845V\",\"devVendor\":\"10001\",\"caseCatalogList\":[{\"catalogName\":\"钞空\",\"caseTotal\":\"5\"},{\"catalogName\":\"钞箱故障\",\"caseTotal\":\"6\"}],\"transHisList\":[{\"transDate\":\"2018-01-21\",\"transTotal\":\"10\"},{\"transDate\":\"2018-01-22\",\"transTotal\":\"12\"}]}," +
							"{\"devNo\":\"000002\",\"address\":\"丁桥勤丰路自助\",\"devCatalogName\":\"CRS\",\"devTypeName\":\"HT2845V\",\"devVendor\":\"10001\",\"caseCatalogList\":[{\"catalogName\":\"钞空\",\"caseTotal\":\"5\"},{\"catalogName\":\"钞箱故障\",\"caseTotal\":\"6\"}],\"transHisList\":[{\"transDate\":\"2018-01-21\",\"transTotal\":\"10\"},{\"transDate\":\"2018-01-22\",\"transTotal\":\"12\"}]}," +
							"{\"devNo\":\"000003\",\"address\":\"丁桥支行\",    \"devCatalogName\":\"ATM\",\"devTypeName\":\"6625\",\"devVendor\":\"10002\",\"caseCatalogList\":[{\"catalogName\":\"钞空\",\"caseTotal\":\"5\"},{\"catalogName\":\"钞箱故障\",   \"caseTotal\":\"6\"}],\"transHisList\":[{\"transDate\":\"2018-01-21\",\"transTotal\":\"10\"},{\"transDate\":\"2018-01-22\",\"transTotal\":\"12\"}]}]";

			dto.setRetMsg("成功");
			dto.setRetCode("00");
			dto.setRetList((List) JSONUtil.parseJSONArray(jsonString));
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}


}

