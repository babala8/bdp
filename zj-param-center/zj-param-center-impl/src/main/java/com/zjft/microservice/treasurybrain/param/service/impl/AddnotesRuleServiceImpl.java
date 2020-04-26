package com.zjft.microservice.treasurybrain.param.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.domain.AddnotesRule;
import com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO;
import com.zjft.microservice.treasurybrain.param.mapstruct.AddnotesRuleDTOConverter;
import com.zjft.microservice.treasurybrain.param.repository.AddnotesRuleMapper;
import com.zjft.microservice.treasurybrain.param.service.AddnotesRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴朋
 * @author 谢菊花
 */
@Service
@Slf4j
public class AddnotesRuleServiceImpl implements AddnotesRuleService {

	@Resource
	private AddnotesRuleMapper addnotesRuleMapper;

	@Override
	public Map<String, Object> qryAddnotesRule(String string) {
		log.info("------------[qryAddnotesRule]AddnotesRuleService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {

			JSONObject params = JSONUtil.parseJSONObject(string);
			String ruleId = StringUtil.parseString(params.get("ruleId"));
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String ruleGenOpName = StringUtil.parseString(params.get("ruleGenOpName"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));

			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 20;
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("ruleId", ruleId);
			paramMap.put("ruleGenOpName", ruleGenOpName);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("curPage", curPage);
			paramMap.put("pageSize", pageSize);

			int totalRow = addnotesRuleMapper.qryTotalRowRule(paramMap);
			int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
			List<AddnotesRule> retAddnotesRules = addnotesRuleMapper.qryAddnotesRule(paramMap);

			List<AddnotesRuleDTO> retList = new ArrayList<AddnotesRuleDTO>();

			for (AddnotesRule addnotesRule : retAddnotesRules) {
				AddnotesRuleDTO addnotesRuleDTO = AddnotesRuleDTOConverter.INSTANCE.domain2dto(addnotesRule);
				addnotesRuleDTO.setRuleGenOpName(addnotesRule.getRuleGenOpName());
				retList.add(addnotesRuleDTO);
			}

			retMap.put("addnotesRuleList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询特殊规则成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询特殊规则异常!");
			log.error("[qryAddnotesRule]异常", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> addAddnotesRule(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			Double addnotesCoeff = StringUtil.objectToDouble(params.get("addnotesCoeff"));
			Double addnotesPeriod = StringUtil.objectToDouble(params.get("addnotesPeriod"));
			Double quotaRatio = StringUtil.objectToDouble(params.get("quotaRatio"));
			String ruleDesp = StringUtil.parseString(params.get("ruleDesp"));
			String ruleGenOpNo = StringUtil.parseString(params.get("ruleGenOpNo"));
			String sysdate = CalendarUtil.getSysTimeYMD(); // 计划生成日期YYYY-MM-DD
			String ruleGenTime = CalendarUtil.getSysTimeHMS(); // 计划生成时间hh:mm:ss
			String ruleGenDate = sysdate.replace("-", "");
			String ruleId = "";
			String maxRuleId = addnotesRuleMapper.selectMaxRuleId(clrCenterNo, ruleGenDate);
			if (maxRuleId == null || "".equals(maxRuleId)) {
				ruleId = clrCenterNo + ruleGenDate + "001";
			} else {
				int maxSeqNo = Integer.parseInt(maxRuleId.substring(maxRuleId.length() - 3));
				if (maxSeqNo >= 999) {
					retMap.put("retMsg", "添加异常!当日规则超过999个!");
					return retMap;
				}
				ruleId = clrCenterNo + ruleGenDate + String.format("%03d", maxSeqNo + 1);
			}

			AddnotesRule addnotesRule = new AddnotesRule();
			addnotesRule.setRuleId(ruleId);
			addnotesRule.setAddnotesCoeff(addnotesCoeff);
			addnotesRule.setAddnotesPeriod(addnotesPeriod);
			addnotesRule.setQuotaRatio(quotaRatio);
			addnotesRule.setRuleDesp(ruleDesp);
			addnotesRule.setRuleGenDate(sysdate);
			addnotesRule.setRuleGenTime(ruleGenTime);
			addnotesRule.setRuleGenOp(ruleGenOpNo);
			addnotesRule.setClrCenterNo(clrCenterNo);
			addnotesRuleMapper.insertSelective(addnotesRule);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "添加特殊规则成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "添加特殊规则异常!");
			log.error("[addAddnotesPlan]异常", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> modAddnotesRule(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String ruleId = StringUtil.parseString(params.get("ruleId"));
			Double addnotesCoeff = StringUtil.objectToDouble(params.get("addnotesCoeff"));
			Double addnotesPeriod = StringUtil.objectToDouble(params.get("addnotesPeriod"));
			Double quotaRatio = StringUtil.objectToDouble(params.get("quotaRatio"));
			String ruleDesp = StringUtil.parseString(params.get("ruleDesp"));

			AddnotesRule addnotesRule = addnotesRuleMapper.selectByPrimaryKey(ruleId);
			if (addnotesRule == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "特殊加钞规则修改异常!对象已不存在!");
				return retMap;
			}

			addnotesRule.setAddnotesCoeff(addnotesCoeff);
			addnotesRule.setAddnotesPeriod(addnotesPeriod);
			addnotesRule.setQuotaRatio(quotaRatio);
			addnotesRule.setRuleDesp(ruleDesp);

			addnotesRuleMapper.updateByPrimaryKey(addnotesRule);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "修改特殊规则成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "修改特殊规则异常!");
			log.error("[modAddnotesRule]异常", e);
		}
		return retMap;
	}

	@Override
	public AddnotesRuleDTO detailAddnotesRule(String jsonParam) {
		log.info("------------[detailAddnotesRule]AddnotesRuleService-------------");
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			String ruleId = StringUtil.parseString(params.get("ruleId"));

			AddnotesRule addnotesRule = addnotesRuleMapper.selectDetailByPrimaryKey(ruleId);
			if (addnotesRule == null) {
				return new AddnotesRuleDTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			AddnotesRuleDTO addnotesRuleDTO = AddnotesRuleDTOConverter.INSTANCE.domain2dto(addnotesRule);
			addnotesRuleDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
			addnotesRuleDTO.setRetMsg("查询加钞规则详情成功！");
			return addnotesRuleDTO;
		} catch (Exception e) {
			AddnotesRuleDTO addnotesRuleDTO = new AddnotesRuleDTO(RetCodeEnum.EXCEPTION);
			addnotesRuleDTO.setRetMsg("查询加钞规则详情异常!");
			log.error("[detailAddnotesRule]异常", e);
			return addnotesRuleDTO;
		}
	}

	@Override
	public Map<String, Object> delAddnotesRule(String jsonParam) {
		log.info("------------[modAddnotesRule]AddnotesRuleService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			String ruleId = StringUtil.parseString(params.get("ruleId"));

			AddnotesRule addnotesRule = addnotesRuleMapper.selectDetailByPrimaryKey(ruleId);
			if (addnotesRule == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "特殊加钞规则删除异常!对象已不存在!");
				return retMap;
			}

			addnotesRuleMapper.deleteByPrimaryKey(ruleId);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "特殊加钞规则删除成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "特殊加钞规则删除异常!");
			log.error("[delAddnotesRule]异常", e);
		}
		return retMap;
	}
}
