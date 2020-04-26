package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.domain.DevRuleExecKey;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;
import com.zjft.microservice.treasurybrain.param.service.DevRuleExecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DevRuleExecResourceImpl implements  DevRuleExecResource {

	@Resource
	private DevRuleExecService devRuleExecService;

	@Override
	public PageDTO<DevRuleExecDTO> qryDevRuleExec(Map<String, Object> paramMap) {
		PageDTO<DevRuleExecDTO> dto = new PageDTO<DevRuleExecDTO>();
		dto.setRetCode(RetCodeEnum.FAIL.getCode());
		dto.setRetMsg("查询设备信息失败！");
		try {
			String devNo = StringUtil.parseString(paramMap.get("devNo"));
			String addnotesRuleId = StringUtil.parseString(paramMap.get("addnotesRuleId"));
			String orgNoFilter = StringUtil.parseString(paramMap.get("orgNoFilter"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));
			int status = StringUtil.objectToInt(paramMap.get("status"));
			Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

			if(-1==curPage) {
				curPage = 1;
			}
			if(-1==pageSize) {
				pageSize = 20;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("devNo", devNo);
			params.put("addnotesRuleId", addnotesRuleId);
			params.put("orgNoFilter", orgNoFilter);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> retMap = devRuleExecService.qryDevRuleExec(JSONUtil.createJsonString(params));

			List<DevRuleExecDTO> retList = (List<DevRuleExecDTO>)retMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(retMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(retMap.get("totalPage"))));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("特殊加钞规则分页查询失败", e);
			dto.setRetException("特殊加钞规则分页查询失败");
		}
		return dto;
	}

	@Override
	public DTO addDevRuleExec(Map<String, Object> paramMap) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"添加失败");
		try {
			String devNo = StringUtil.parseString(paramMap.get("devNo"));
			String addnotesRuleId = StringUtil.parseString(paramMap.get("addnotesRuleId"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));

			String nowDate= CalendarUtil.getSysTimeYMD();

			Integer status=0;//默认未执行
			//根据执行时间更新状态
			int weight1 = CalendarUtil.getSubDate(nowDate, startDate);
			int weight2=CalendarUtil.getSubDate(nowDate, endDate);
			if(weight1<weight2){
				dto.setRetMsg("开始时间大于结束时间！");
				dto.setRetCode(RetCodeEnum.FAIL.getCode());
				return dto;
			}
			if(weight1 * weight2 <= 0){
				status=1;
			}else if(weight1<0){
				//nowDate<startDate,未执行
				status=0;
			}else{
				status=2;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("devNo", devNo);
			params.put("addnotesRuleId", addnotesRuleId);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);

			Map<String,Object> retMap=devRuleExecService.addDevRuleExec(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("添加特殊加钞规则失败", e);
			dto.setRetException("添加特殊加钞规则失败!");
		}
		return dto;
	}

	@Override
	public DTO delSpecialRuleExec(Map<String,Object> paramMap){
		DTO dto = new DTO();
		dto.setRetCode(RetCodeEnum.FAIL.getCode());
		dto.setRetMsg("删除执行规则失败");
		try {
			String devNo=StringUtil.parseString(paramMap.get("devNo"));
			String startDate=StringUtil.parseString(paramMap.get("startDate"));
			String endDate=StringUtil.parseString(paramMap.get("endDate"));
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("devNo", devNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);

			Map<String,Object> retMap=devRuleExecService.delSpecialRuleExec(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("删除执行规则信息失败", e);
			dto.setRetException("删除执行规则失败");
		}
		return dto;
	}

	@Override
	public DevRuleExecDTO qryDevRuleExecByKey(Map<String, Object> paramMap) {
		DevRuleExecDTO devRuleExecDTO = new DevRuleExecDTO();
		try {
			String devNo = StringUtil.parseString(paramMap.get("devNo"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));

			DevRuleExecKey devRuleExecKey = new DevRuleExecKey();
			devRuleExecKey.setDevNo(devNo);
			devRuleExecKey.setEndDate(endDate);
			devRuleExecKey.setStartDate(startDate);

			devRuleExecDTO = devRuleExecService.qryDevRuleExecByKey(devRuleExecKey);

		} catch (Exception e) {
			log.error("查询加钞特殊规则失败", e);
			return devRuleExecDTO;
		}
		return devRuleExecDTO;
	}

	@Override
	public DTO modDevRuleExecByKey(Map<String, Object> paramMap) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"修改失败");
		try {
			String devNo = StringUtil.parseString(paramMap.get("devNo"));
			String addnotesRuleId = StringUtil.parseString(paramMap.get("addnotesRuleId"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));
			String status = StringUtil.parseString(paramMap.get("status"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("devNo", devNo);
			params.put("addnotesRuleId", addnotesRuleId);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);

			Map<String,Object> retMap=devRuleExecService.addDevRuleExec(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("添加特殊加钞规则失败", e);
			dto.setRetException("添加特殊加钞规则失败!");
		}
		return dto;
	}
}
