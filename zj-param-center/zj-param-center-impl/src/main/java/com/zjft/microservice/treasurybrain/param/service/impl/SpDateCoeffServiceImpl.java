package com.zjft.microservice.treasurybrain.param.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.domain.SpdateCoeff;
import com.zjft.microservice.treasurybrain.param.domain.SpdateCoeffKey;
import com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO;
import com.zjft.microservice.treasurybrain.param.repository.SpdateCoeffMapper;
import com.zjft.microservice.treasurybrain.param.service.SpDateCoeffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴朋
 */
@Service
@Slf4j
public class SpDateCoeffServiceImpl implements SpDateCoeffService {

	@Resource
	private SpdateCoeffMapper spdateCoeffMapper;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Override
	public Map<String, Object> qrySpDateCoeff(String createJsonString) {
		log.info("------------[qrySpDateCoeff]SpDateCoeffServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			String specialDateStart = StringUtil.parseString(params.get("specialDateStart"));
			String specialDateEnd = StringUtil.parseString(params.get("specialDateEnd"));
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));


			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("specialDateStart", specialDateStart);
			paramMap.put("specialDateEnd", specialDateEnd);
			paramMap.put("clrCenterNo", clrCenterNo);
			//查询所有记录
			int totalRow = spdateCoeffMapper.qryTotalRowRule(paramMap);
			int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

			List<Map<String, Object>> retSpdateCoeff = spdateCoeffMapper.qrySpdateCoeff(paramMap);

			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> aMap : retSpdateCoeff) {
				Map<String, Object> entity = new HashMap<String, Object>();

				entity.put("endDate", aMap.get("endDate"));
				entity.put("startDate", aMap.get("startDate"));
				entity.put("addnotesCoeff", aMap.get("addnotesCoeff"));
				entity.put("clrCenterNo", aMap.get("clrCenterNo"));
				//根据clrCenterNo
				ClrCenterTable clrCenter = clrCenterInnerResource.selectByPrimaryKey(aMap.get("clrCenterNo").toString());
				String centerName="";
				if(clrCenter!=null){
					centerName= clrCenter.getCenterName();
				}
				entity.put("clrCenterName", centerName);
				retList.add(entity);
			}

			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询金库特殊日期配钞成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询金库特殊日期配钞异常!");
			log.error("[qrySpDateCoeff]异常", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> addSpDateCoeff(String createJsonString) {
		log.info("------------[addSpDateCoeff]spDateCoeffServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));
			BigDecimal addnotesCoeff = params.getBigDecimal("addnotesCoeff");

			SpdateCoeff spdateCoeff = new SpdateCoeff();
			spdateCoeff.setClrCenterNo(clrCenterNo);
			spdateCoeff.setStartDate(startDate);
			spdateCoeff.setEndDate(endDate);
			spdateCoeff.setAddnotesCoeff(addnotesCoeff);
			if (spdateCoeffMapper.selectByPrimaryKey(spdateCoeff) != null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_EXIST.getCode());
				retMap.put("retMsg", "已存在该特殊日期执行信息");
				return retMap;
			}
			SpdateCoeff cspdateCoeff = spdateCoeffMapper.isConflict(spdateCoeff);
			if (cspdateCoeff != null) {
				//返回冲突信息
				String sDate = cspdateCoeff.getStartDate();
				String eDate = cspdateCoeff.getEndDate();
				retMap.put("retMsg", "已经存在" + sDate + "至" + eDate + "之间的特殊日期配钞信息，请重新输入");
				return retMap;
			}

			spdateCoeffMapper.insertSelective(spdateCoeff);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "增加金库特殊日期配钞设置成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "增加金库特殊日期配钞设置失败!");
			log.error("[addSpDateCoeff]异常", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> modSpDateCoeff(String createJsonString) {
		log.info("------------[modSpDateCoeff]spDateCoeffServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			BigDecimal addnotesCoeff = params.getBigDecimal("addnotesCoeff");
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));

			SpdateCoeff spdateCoeff = new SpdateCoeff();

			spdateCoeff.setAddnotesCoeff(addnotesCoeff);
			spdateCoeff.setClrCenterNo(clrCenterNo);
			spdateCoeff.setEndDate(endDate);
			spdateCoeff.setStartDate(startDate);

			spdateCoeffMapper.updateByPrimaryKeySelective(spdateCoeff);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "修改金库特殊日期配钞信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "修改金库特殊日期配钞信息异常!");
			log.error("modifySpDateCoeff Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> delSpDateCoeff(String createJsonString) {
		log.info("------------[delSpDateCoeff]spDateCoeffServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));
			SpdateCoeff spdateCoeff = new SpdateCoeff();
			spdateCoeff.setClrCenterNo(clrCenterNo);
			spdateCoeff.setEndDate(endDate);
			spdateCoeff.setStartDate(startDate);

			if (spdateCoeffMapper.selectByPrimaryKey(spdateCoeff) == null) {
				retMap.put("retMsg", "对象已不存在,可能已经删除！");
				return retMap;
			}
			spdateCoeffMapper.deleteByPrimaryKey(spdateCoeff);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "删除特殊日期配钞信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "删除特殊日期配钞信息异常!");
			log.error("[delSpDateCoeff]异常", e);
		}
		return retMap;
	}

	@Override
	public SpDateCoeffDTO qrySpDateCoeffByKey(SpdateCoeffKey spdateCoeffKey) {
		log.info("------------[qrySpDateCoeffByKey]SpDateCoeffServiceImpl-------------");
		SpDateCoeffDTO spateCoeffDTO = new SpDateCoeffDTO();
		try {

			SpdateCoeff spateCoeff = spdateCoeffMapper.selectSpdateCoeffByKey(spdateCoeffKey);

			if(spateCoeff != null) {
				spateCoeffDTO.setAddnotesCoeff(Double.parseDouble(spateCoeff.getAddnotesCoeff().toString()));
				spateCoeffDTO.setClrCenterNo(spateCoeff.getClrCenterNo());
				spateCoeffDTO.setStartDate(spateCoeff.getStartDate());
				spateCoeffDTO.setEndDate(spateCoeff.getEndDate());
			}

		} catch (Exception e) {
			log.error("[qrySpDateCoeff]异常", e);
		}
		return spateCoeffDTO;
	}
}
