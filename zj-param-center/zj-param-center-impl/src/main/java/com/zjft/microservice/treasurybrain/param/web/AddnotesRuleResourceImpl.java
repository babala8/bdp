package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO;
import com.zjft.microservice.treasurybrain.param.service.AddnotesRuleService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴朋、谢菊花
 */

@Slf4j
@RestController
public class AddnotesRuleResourceImpl implements  AddnotesRuleResource {

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private AddnotesRuleService addnotesRuleService;

	@Override
	public PageDTO<AddnotesRuleDTO> qryAddnotesRule(Map<String, Object> paramMap) {
		PageDTO<AddnotesRuleDTO> dto = new PageDTO<AddnotesRuleDTO>();
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String ruleId = StringUtil.parseString(paramMap.get("ruleId"));
			String ruleGenOpName = StringUtil.parseString(paramMap.get("ruleGenOpName"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));

			Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

			if(-1==curPage) {
				curPage = 1;
			}
			if(-1==pageSize) {
				pageSize = 20;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("ruleId", ruleId);
			params.put("ruleGenOpName", ruleGenOpName);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> retMap = addnotesRuleService.qryAddnotesRule(JSONUtil.createJsonString(params));

			List<AddnotesRuleDTO> retList = (List<AddnotesRuleDTO>)retMap.get("addnotesRuleList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(retMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(retMap.get("totalPage"))));
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询特殊规则失败", e);
			dto.setRetException("查询特殊规则失败");
		}
		return dto;
	}


	@Override
	public DTO addAddnotesRule(AddnotesRuleDTO addnotesRuleDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"添加失败");
		try {
			String clrCenterNo = StringUtil.parseString(addnotesRuleDTO.getClrCenterNo());
			Double addnotesCoeff = StringUtil.objectToDouble(addnotesRuleDTO.getAddnotesCoeff());
			Double addnotesPeriod = StringUtil.objectToDouble(addnotesRuleDTO.getAddnotesPeriod());
			Double quotaRatio = StringUtil.objectToDouble(addnotesRuleDTO.getQuotaRatio());
			String ruleDesp = StringUtil.parseString(addnotesRuleDTO.getRuleDesp());

			UserDTO authUserDTO = sysUserResource.getAuthUserInfo();
			String username = authUserDTO.getUsername();

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("addnotesCoeff", addnotesCoeff);
			params.put("addnotesPeriod", addnotesPeriod);
			params.put("quotaRatio", quotaRatio);
			params.put("ruleDesp", ruleDesp);
			params.put("ruleGenOpNo", username);

			Map<String,Object> retMap = addnotesRuleService.addAddnotesRule(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("添加特殊规则失败", e);
			dto.setRetException("添加特殊规则失败!");
		}
		return dto;
	}

	@Override
	public DTO modAddnotesRule(AddnotesRuleDTO addnotesRuleDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"修改失败");
		try {
			String ruleId = StringUtil.parseString(addnotesRuleDTO.getRuleId());
			Double addnotesCoeff = StringUtil.objectToDouble(addnotesRuleDTO.getAddnotesCoeff());
			Double addnotesPeriod = StringUtil.objectToDouble(addnotesRuleDTO.getAddnotesPeriod());
			Double quotaRatio = StringUtil.objectToDouble(addnotesRuleDTO.getQuotaRatio());
			String ruleDesp = StringUtil.parseString(addnotesRuleDTO.getRuleDesp());

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("ruleId", ruleId);
			params.put("addnotesCoeff", addnotesCoeff);
			params.put("addnotesPeriod", addnotesPeriod);
			params.put("quotaRatio", quotaRatio);
			params.put("ruleDesp", ruleDesp);

			Map<String, Object> retMap =  addnotesRuleService.modAddnotesRule(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("修改特殊规则失败", e);
			dto.setRetException("修改特殊规则失败!");
		}
		return dto;
	}


	@Override
	public ObjectDTO detailAddnotesRule(String ruleId) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(),"查询失败");
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("ruleId", ruleId);

			AddnotesRuleDTO addnotesRuleDTO = addnotesRuleService.detailAddnotesRule(JSONUtil.createJsonString(params));
			dto.setElement(addnotesRuleDTO);
			dto.setRetCode(addnotesRuleDTO.getRetCode());
			dto.setRetMsg(addnotesRuleDTO.getRetMsg());
		} catch (Exception e) {
			log.error("查询加钞规则详情失败", e);
			dto.setRetException("查询加钞规则详情失败!");
		}
		return dto;
	}

	@Override
	public DTO delAddnotesRule(String ruleId) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"删除失败");
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("ruleId", ruleId);

			Map<String,Object> retMap=addnotesRuleService.delAddnotesRule(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("特殊加钞规则删除失败", e);
			dto.setRetException("特殊加钞规则删除失败!");
		}
		return dto;
	}
}
