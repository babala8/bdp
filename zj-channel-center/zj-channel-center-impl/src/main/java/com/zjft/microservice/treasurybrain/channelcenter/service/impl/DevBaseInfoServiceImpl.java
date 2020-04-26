package com.zjft.microservice.treasurybrain.channelcenter.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.domain.DevClrTimeParamDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevBaseInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevClrTimeParamDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevBaseInfoConverter;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevCatalogConverter;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevClrTimeParamDTOConverter;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevBaseInfoMapper;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevCatalogTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevBaseInfoService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevBaseInfoInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.domain.DevCatalogTable;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备基础信息
 *
 * @author 徐全发
 * @author 杨光
 * @since 2019-03-20
 */
@Slf4j
@Service
public class DevBaseInfoServiceImpl implements DevBaseInfoService {

	@Resource(name = "devBaseInfoMapperExtend")
	private DevBaseInfoMapper devBaseInfoMapper;

	@Resource
	private DevCatalogTableMapper devCatalogTableMapper;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private DevBaseInfoInnerResource devBaseInfoInnerResource;


	@Override
	public ListDTO<DevBaseInfoDTO> qryDevInfoByClrNo(String paramName) {
		ListDTO<DevBaseInfoDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
//			List<DevBaseInfo> devList = devBaseInfoMapper.qryDevInfoByClrNo(paramName);
		List<Map<String, Object>> devList = devBaseInfoMapper.qryDevInfoByClrNo(paramName);
		if (devList == null || devList.size() <= 0) {
			dto.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
			return dto;
		}
//			List<DevBaseInfoDTO> retList = DevBaseInfoConverter.INSTANCE.domain2dto(devList);
		List<DevBaseInfoDTO> retList = new ArrayList<>();
		for (Map dev : devList) {
			DevBaseInfoDTO devBaseInfoDTO = new DevBaseInfoDTO();
			devBaseInfoDTO.setNo(StringUtil.parseString(dev.get("no")));
			devBaseInfoDTO.setOrgNo(StringUtil.parseString(dev.get("orgNo")));
			devBaseInfoDTO.setOrgName(StringUtil.parseString(dev.get("orgName")));
			devBaseInfoDTO.setAddnotesLine(StringUtil.parseString(dev.get("addnotesLine")));
			devBaseInfoDTO.setAddnotesLineName(StringUtil.parseString(dev.get("addnotesLineName")));
			devBaseInfoDTO.setStatus(StringUtil.parseString(dev.get("status")));
			devBaseInfoDTO.setAddress(StringUtil.parseString(dev.get("address")));
			devBaseInfoDTO.setDevType(StringUtil.parseString(dev.get("devType")));
			devBaseInfoDTO.setDevTypeName(StringUtil.parseString(dev.get("devTypeName")));
			devBaseInfoDTO.setAwayFlag(StringUtil.objectToInteger(dev.get("awayFlag")));
			devBaseInfoDTO.setX(StringUtil.objectToDouble(dev.get("x")));
			devBaseInfoDTO.setY(StringUtil.objectToDouble(dev.get("y")));

			retList.add(devBaseInfoDTO);
		}

		dto.setRetList(retList);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 设备列表查询
	 *
	 * @param params no、address、ip、addnotesLine、orgNo、clrCenterNo、devVendor、devCatalog、devType、devTypeCash、curPage、pageSize
	 * @return PageDTO<DevBaseInfoDTO>
	 */
	@Override
	public PageDTO<DevBaseInfoDTO> queryDevInfoList(Map<String, Object> params) {
		PageDTO<DevBaseInfoDTO> dto = new PageDTO<>(RetCodeEnum.FAIL);
		UserDTO authUser = sysUserResource.getAuthUserInfo();

		int curPage = StringUtil.objectToInt(params.get("curPage"));
		int pageSize = StringUtil.objectToInt(params.get("pageSize"));
		int startRow = pageSize * (curPage - 1);
		int endRow = pageSize * curPage;
		params.put("startRow", startRow);
		params.put("endRow", endRow);
		params.put("authOrgNo", authUser.getOrgNo());

		int totalRow = devBaseInfoMapper.qryTotalRow(params);
		int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

		List<DevBaseInfo> baseInfoList = devBaseInfoMapper.selectDevListByParams(params);
		List<DevBaseInfoDTO> dtoList = DevBaseInfoConverter.INSTANCE.domain2dto(baseInfoList);

		dto.setPageSize(pageSize);
		dto.setRetList(dtoList);
		dto.setTotalRow(totalRow);
		dto.setTotalPage(totalPage);
		dto.setCurPage(curPage);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	@Override
	public DTO addDevInfo(DevBaseInfoDTO dto) {
		if (checkPermissionByOrgNo(dto.getOrgNo())) {
			DevBaseInfo devBaseInfo = DevBaseInfoConverter.INSTANCE.dto2do(dto);
			int i = devBaseInfoMapper.insertSelective(devBaseInfo);
			if (i == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modDevInfo(DevBaseInfoDTO dto) {
		if (checkPermissionByDevNo(dto.getNo())) {
			DevBaseInfo devBaseInfo = DevBaseInfoConverter.INSTANCE.dto2do(dto);
			int i = devBaseInfoMapper.updateByPrimaryKeySelective(devBaseInfo);
			if (i == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO delDevInfoByNo(String no) {
		if (checkPermissionByDevNo(no)) {
			int i = devBaseInfoMapper.deleteByPrimaryKey(no);
			if (i == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public ListDTO<DevCatalogDTO> qryDevCatalog() {
		ListDTO<DevCatalogDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		List<DevCatalogTable> devCatalogTables = devCatalogTableMapper.queryDevCatalogList();

		List<DevCatalogDTO> retList = DevCatalogConverter.INSTANCE.domain2dto(devCatalogTables);
		dto.setRetList(retList);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	private boolean checkPermissionByDevNo(String devNo) {
		UserDTO authUser = sysUserResource.getAuthUserInfo();
		return devBaseInfoMapper.checkPermissionByDevNo(devNo, authUser.getOrgNo()) > 0;
	}


	private boolean checkPermissionByOrgNo(String orgNo) {
		UserDTO authUser = sysUserResource.getAuthUserInfo();
		return devBaseInfoMapper.checkPermissionByOrgNo(orgNo, authUser.getOrgNo()) > 0;
	}

	/**
	 * 查询设备信息及使用信息
	 *
	 * @param string devNo：设备编号，orgNo：所属机构
	 * @return entity：设备编号，地址，组织机构名，剩余可用钞量，日均取款量，剩余可用天数，距离上次加钞天数，查询最近两个月清机周期中的日均取款量
	 */
	@Override
	public Map<String, Object> getDevBaseInfoByNo(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String devNo = StringUtil.parseString(params.get("devNo"));
			String orgNo = StringUtil.parseString(params.get("orgNo"));

//			DevBaseInfo devBaseInfo = devBaseInfoMapper.qryDevByNoOrgNo(devNo, orgNo);
			DevBaseInfo devBaseInfo = devBaseInfoInnerResource.qryDevByNoOrgNo(devNo, orgNo);
			if (devBaseInfo == null) {
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("devNo", devNo);

			try {
				entity.put("devCatalogName", devBaseInfo.getDevTypeTable().getDevCatalogTable().getName());
			} catch (Exception e) {
				entity.put("devCatalogName", "");
			}
			entity.put("address", devBaseInfo.getAddress());
			entity.put("orgName", devBaseInfo.getSysOrg() == null ? "" : devBaseInfo.getSysOrg().getName());

			int availableAmt = 0; //剩余可用钞量
			int dayAvgAmt = 0; //日均取款量
			int availableDays = 0; //剩余可用天数
			int notAddCashDays = -1;//距离上次加钞天数
			String lastAddnoteDate = null;
			//查询最近两个月清机周期中的日均取款量
			String endDate = CalendarUtil.getSysTimeYMD();
			String startDate = CalendarUtil.getFixedMonth(endDate, -2);
			dayAvgAmt = devBaseInfoMapper.getDevDayAvgAmt(devNo, startDate, endDate) == null ? 0 : devBaseInfoMapper.getDevDayAvgAmt(devNo, startDate, endDate);

			if (devBaseInfo.getDevStatusTable() != null) {
				availableAmt = devBaseInfo.getDevStatusTable().getAvailableAmt();
				if (devBaseInfo.getDevStatusTable().getLastAddnoteDate() != null && !"".equals(devBaseInfo.getDevStatusTable().getLastAddnoteDate())) {
					notAddCashDays = CalendarUtil.getSubDate(CalendarUtil.getSysTimeYMD(), devBaseInfo.getDevStatusTable().getLastAddnoteDate());
					lastAddnoteDate = devBaseInfo.getDevStatusTable().getLastAddnoteDate();
				}
			}
			if (dayAvgAmt == 0 || dayAvgAmt == -1) {
				if (availableAmt == 0) {
					availableDays = 0;//如果剩余可用钞量为0，日均取款量也为0，则剩余可用天数默认取0
				} else {
					availableDays = 7;//如果剩余可用钞量不为0，日均取款量为0，则剩余可用天数默认取7
				}
			} else {
				availableDays = availableAmt / dayAvgAmt;//可用天数下取整
			}
			entity.put("availableAmt", availableAmt);
			entity.put("dayAvgAmt", dayAvgAmt);
			entity.put("availableDays", availableDays);
			entity.put("notAddCashDays", notAddCashDays);
			entity.put("lastAddnoteDate", lastAddnoteDate);
			entity.put("lineName", devBaseInfo.getAddnotesLineName());

			retMap.put("entity", entity);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询设备信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询设备信息异常!");
			log.error("getDevBaseInfoByNo Fail: ", e);
		}
		return retMap;
	}

	@Override
	public DevClrTimeParamListDTO qryByDevNo(String devNo) {

		DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectByPrimaryKey(devNo);
		DevClrTimeParamListDTO devClrTimeParamListDTO = new DevClrTimeParamListDTO();
		if(null!=devBaseInfo && !StringUtil.isNullorEmpty(devBaseInfo.getNo())){
			//查询设备信息
			devClrTimeParamListDTO.setDevNo(devNo);
			devClrTimeParamListDTO.setAddClrPeriod(devBaseInfo.getAddClrPeriod());
			devClrTimeParamListDTO.setMaxAddClrPeriod(devBaseInfo.getMaxAddClrPeriod());
			//查询设备加钞时间段信息
			List<DevClrTimeParamDO> devClrTimeParamDOS = devBaseInfoMapper.qryByDevNo(devNo);
			List<DevClrTimeParamDTO> devClrTimeParamDTOS =  DevClrTimeParamDTOConverter.INSTANCE.do2dto(devClrTimeParamDOS);
			devClrTimeParamListDTO.setTimeList(devClrTimeParamDTOS);
			devClrTimeParamListDTO.setResult(RetCodeEnum.SUCCEED);

		}else{
			devClrTimeParamListDTO.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}
		return devClrTimeParamListDTO;
	}


	// -----------------------------------------------
	//                   内部服务
	// -----------------------------------------------

	@Override
	public DevBaseInfo selectByPrimaryKey(String no) {
		return devBaseInfoMapper.selectByPrimaryKey(no);
	}

	@Override
	public DevBaseInfo qryDevByNoOrgNo(String devNo, String orgNo) {
		return devBaseInfoMapper.qryDevByNoOrgNo(devNo, orgNo);
	}

	@Override
	public DevBaseInfo selectDetailByPrimaryKey(String devNo) {
		return devBaseInfoMapper.selectDetailByPrimaryKey(devNo);
	}


	@Override
	public void updateByPrimaryKeySelective(DevBaseInfo devBaseInfo) {
		devBaseInfoMapper.updateByPrimaryKeySelective(devBaseInfo);
	}

	@Override
	public List<Map<String, Object>> getKeyEventDevice(Map<String, Object> params) {
		return devBaseInfoMapper.getKeyEventDevice(params);
	}

	@Override
	public List<Map<String, Object>> getKeyEventDeviceForfault(Map<String, Object> params) {
		return devBaseInfoMapper.getKeyEventDeviceForfault(params);
	}

	@Override
	public List<Map<String, Object>> getKeyEventDeviceForLineRun(Map<String, Object> params) {
		return devBaseInfoMapper.getKeyEventDeviceForLineRun(params);
	}

	@Override
	public List<Map<String, Object>> getAvaileAmtAndTimeInterval(Map<String, Object> params) {
		return devBaseInfoMapper.getAvaileAmtAndTimeInterval(params);
	}


}
