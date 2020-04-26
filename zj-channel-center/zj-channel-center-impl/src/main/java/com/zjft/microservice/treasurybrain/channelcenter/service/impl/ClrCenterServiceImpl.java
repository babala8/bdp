package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.repository.ClrCenterTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.ClrCenterService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.mapstruct.ClrCenterConverter;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 常健
 */
@Slf4j
@Service("baseClrCenterService")
public class ClrCenterServiceImpl implements ClrCenterService {

	@Resource
	private ClrCenterTableMapper clrCenterTableMapper;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Override
	public ListDTO<ClrCenterDTO> qryClrCenterListByAuth(Map<String, Object> paramMap) {
		log.info("----------[qryClrCenterListByAuth]ClrCenterService----------------");
		ListDTO<ClrCenterDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String userOrgNo = authUser.getOrgNo();
			/*String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String clrCenterName = StringUtil.parseString(paramMap.get("clrCenterName"));
			Integer clrCenterType = StringUtil.objectToInt(paramMap.get("clrCenterType"));*/
			paramMap.put("userOrgNo",userOrgNo);
			List<ClrCenterTable> clrCenterTableList = clrCenterTableMapper.qryClrCenterListByAuth(paramMap);
			List<ClrCenterDTO> clrCenterDTOList = ClrCenterConverter.INSTANCE.domain2dto(clrCenterTableList);
			listDTO.setRetList(clrCenterDTOList);
			listDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[qryClrCenterListByAuth]Fail", e);
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}

	/**
	 * 金库参数调整
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO updateCenterNum(ClrCenterDTO clrCenterDTO) {

		ClrCenterTable domain = ClrCenterConverter.INSTANCE.dto2do(clrCenterDTO);
		clrCenterTableMapper.updateByPrimaryKeySelective(domain);

		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 根据金库编号判断其是否为自动化库
	 *
	 * @param clrCenterNo 金库编号
	 * @return true：自动化库/false：非自动化库
	 */
	@Override
	public Boolean qryClrCenterIsAuto(String clrCenterNo) {

		return clrCenterTableMapper.qryClrCenterIsAuto(clrCenterNo) > 0;

	}


	// -----------------------------------------------
	//                   内部服务
	// -----------------------------------------------


	@Override
	public List<ClrCenterTable> getClrCenterListByOrgNo(String orgNo) {
		return clrCenterTableMapper.getClrCenterListByOrgNo(orgNo);
	}

	@Override
	public List<Map<String, Object>> getClrCenterByClrNo(String clrCenterNo) {
		return clrCenterTableMapper.getClrCenterByClrNo(clrCenterNo);
	}

	@Override
	public ClrCenterTable selectByPrimaryKey(String clrCenterNo) {
		return clrCenterTableMapper.selectByPrimaryKey(clrCenterNo);
	}

	@Override
	public List<String> getClrCenterOrgNo(String clrCenterNo) {
		return clrCenterTableMapper.getClrCenterOrgNo(clrCenterNo);
	}

	@Override
	public List<String> clrCenterNoList() {
		return clrCenterTableMapper.clrCenterNoList();

	}

	@Override
	public String getOrgNameByClrNo(String centerNo) {
		return clrCenterTableMapper.getOrgNameByClrNo(centerNo);

	}

	@Override
	public void updateByPrimaryKeySelective(ClrCenterTable clrCenterTable) {
		clrCenterTableMapper.updateByPrimaryKeySelective(clrCenterTable);

	}


	@Override
	public Map<String, Object> getClrCenterByClrCenterNo(String jsonParam) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

//			List<Map<String, Object>> clrCenterList = clrCenterTableMapper.getClrCenterByClrNo(clrCenterNo);
			List<Map<String, Object>> clrCenterList = clrCenterInnerResource.getClrCenterByClrNo(clrCenterNo);

			if (clrCenterList != null && clrCenterList.size() > 0) {
				Map<String, Object> tmpMap = clrCenterList.get(0);
				retMap.put("centerName", tmpMap.get("centerName"));
				retMap.put("bankOrgNo", tmpMap.get("bankOrgNo"));
				retMap.put("bankOrgName", tmpMap.get("bankOrgName"));
				retMap.put("bankRegion", tmpMap.get("bankRegion"));
				retMap.put("bankCity", tmpMap.get("bankCity"));
				retMap.put("x", tmpMap.get("x"));
				retMap.put("y", tmpMap.get("y"));
				retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
				retMap.put("retMsg", "查询成功！");
			}
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "清分中心查询异常!");
			log.error("getClrCenterByClrNo Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> getNetpointsByClrNo(String clrCenterNo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			if ("".equals(clrCenterNo)) {
				retMap.put("retMsg", "清分中心号为空!");
				return retMap;
			}
//			List<Map<String, Object>> retOrgTables = sysOrgMapper.getNetpointsByClrNo(clrCenterNo);
			List<Map<String, Object>> retOrgTables = sysOrgInnerResource.getNetpointsByClrNo(clrCenterNo);
			if (retOrgTables == null || retOrgTables.size() == 0) {
				retMap.put("retMsg", "无查询结果！");
			} else {
				List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> orgMap : retOrgTables) {
					Map<String, Object> entity = new HashMap<String, Object>();
					entity.put("no", orgMap.get("no"));
					entity.put("name", orgMap.get("name"));
					entity.put("x", orgMap.get("x"));
					entity.put("y", orgMap.get("y"));
					retList.add(entity);
				}
				retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
				retMap.put("retMsg", "机构查询成功！");
				retMap.put("retList", retList);
			}
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "机构查询异常!");
			log.error("qryOrgByNo Fail: ", e);
		}
		return retMap;
	}
}
