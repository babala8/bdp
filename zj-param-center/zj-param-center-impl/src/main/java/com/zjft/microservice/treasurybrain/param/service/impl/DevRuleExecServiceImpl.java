package com.zjft.microservice.treasurybrain.param.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevBaseInfoInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.domain.DevRuleExec;
import com.zjft.microservice.treasurybrain.param.domain.DevRuleExecKey;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;
import com.zjft.microservice.treasurybrain.param.repository.AddnotesRuleMapper;
import com.zjft.microservice.treasurybrain.param.repository.DevRuleExecMapper;
import com.zjft.microservice.treasurybrain.param.service.DevRuleExecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DevRuleExecServiceImpl implements DevRuleExecService {

	@Resource
	private DevRuleExecMapper devRuleExecMapper;

	@Resource
	private AddnotesRuleMapper addnotesRuleMapper;

	@Resource
	private DevBaseInfoInnerResource devBaseInfoInnerResource;

	@Override
	public Map<String, Object> addDevRuleExec(String jsonParam) {
		log.info("------------[addDevRuleExec]DevRuleExecService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			String devNo = StringUtil.parseString(params.get("devNo"));
			String addnotesRuleId = StringUtil.parseString(params.get("addnotesRuleId"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));
			int status = StringUtil.objectToInt(params.get("status"));

			DevRuleExec devRuleExec = new DevRuleExec();
			devRuleExec.setAddnotesRuleId(addnotesRuleId);
			devRuleExec.setDevNo(devNo);
			devRuleExec.setEndDate(endDate);
			devRuleExec.setStartDate(startDate);
			devRuleExec.setStatus(status);

			if (addnotesRuleMapper.selectByPrimaryKey(addnotesRuleId) == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "不存在该加钞规则");
				return retMap;
			}

			DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectByPrimaryKey(devNo);
			if (devBaseInfo == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "不存在该设备");
				return retMap;
			}

			List<DevRuleExec> devList = devRuleExecMapper.isConflict(devRuleExec);
			if (devList != null) {//存在冲突信息
				for (DevRuleExec dev : devList) {
					// 仅返回第一个冲突信息
					retMap.put("retMsg", "存在" + dev.getStartDate() + "至" + dev.getEndDate() + "的特殊规则信息,增加信息失败");
					return retMap;
				}
			}

			devRuleExecMapper.insertSelective(devRuleExec);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "添加特殊加钞规则成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "添加特殊加钞规则异常!");
			log.error("[addDevRuleExec]异常", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryDevRuleExec(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));

			String devNo = StringUtil.parseString(params.get("devNo"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));
			String addnotesRuleId = StringUtil.parseString(params.get("addnotesRuleId"));
			Integer status = StringUtil.objectToInt(params.get("status"));
//			JSONObject orgNoFilter = JSONUtil.parseJSONObject(params.getString("orgNoFilter"));
			String orgNo = StringUtil.parseString(params.get("orgNoFilter"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("devNo", devNo);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("addnotesRuleId", addnotesRuleId);
			paramMap.put("status", status);
			paramMap.put("orgNo", orgNo);

			int totalRow = devRuleExecMapper.qryTotalRowRule(paramMap);
			int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

			List<DevRuleExec> retAddnotesPlans = devRuleExecMapper.qryDevRuleExec(paramMap);
			List<DevRuleExecDTO> retList = new ArrayList<DevRuleExecDTO>();

			String nowDate = CalendarUtil.getSysTimeYMD();
			for (DevRuleExec devRuleExec : retAddnotesPlans) {
				DevRuleExecDTO devRuleExecDTO = new DevRuleExecDTO();
				devRuleExecDTO.setDevNo(devRuleExec.getDevNo());
				devRuleExecDTO.setAddnotesRuleId(devRuleExec.getAddnotesRuleId());
				devRuleExecDTO.setStartDate(devRuleExec.getStartDate());
				devRuleExecDTO.setEndDate(devRuleExec.getEndDate());
				devRuleExecDTO.setStatus(devRuleExec.getStatus());

				//当状态改变时，更新后台状态数据
				//1.判断状态是否发生变化
				Integer nstatus = 0;
				int weight1 = CalendarUtil.getSubDate(nowDate, startDate);
				int weight2 = CalendarUtil.getSubDate(nowDate, endDate);
				if (status == 3) {//取消状态
					nstatus = 3;
				} else if (weight1 * weight2 <= 0) {//endDate<nowDate<startDate,执行中
					nstatus = 1;
				} else if (weight1 < 0) {
					//nowDate<startDate,未执行
					nstatus = 0;
				} else if (weight2 > 0) {//nowDate>endDate,执行过期
					nstatus = 2;
				} else {//状态未发生改变
					nstatus = status;
				}
				//2.状态发生改变了，后台更新状态数据
				if (!nstatus.equals(status)) {
					log.info("更新执行信息状态");
					devRuleExecDTO.setStatus(nstatus);
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("devNo", devNo);
					paramsMap.put("addnotesRuleId", addnotesRuleId);
					paramsMap.put("startDate", startDate);
					paramsMap.put("endDate", endDate);
					paramsMap.put("status", nstatus);
					this.updateDevRuleExecStatus(JSONUtil.createJsonString(paramsMap));
				}

				retList.add(devRuleExecDTO);
			}

			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "特殊加钞规则分页查询成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "特殊加钞规则分页查询异常!");
			log.error("qryDevRuleExec Fail: ", e);
		}
		return retMap;
	}


	@Override
	public Map<String, Object> delSpecialRuleExec(String createJsonString) {
		log.info("------------[delSpecialRuleExec]DevRuleExecService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String devNo = StringUtil.parseString(params.get("devNo"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));

			DevRuleExecKey devRuleExecKey = new DevRuleExecKey();
			devRuleExecKey.setDevNo(devNo);
			devRuleExecKey.setStartDate(startDate);
			devRuleExecKey.setEndDate(endDate);

			DevRuleExecKey obj = devRuleExecMapper.selectByPrimaryKey(devRuleExecKey);

			if (obj == null) {
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			devRuleExecMapper.deleteByPrimaryKey(devRuleExecKey);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "删除执行规则信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "删除执行规则信息异常!");
			log.error("[delDevRuleExec]异常", e);
		}
		return retMap;
	}


	@Override
	public Map<String, Object> updateDevRuleExecStatus(String jsonParam) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);

			DevRuleExec devRuleExec = new DevRuleExec();
			devRuleExec.setAddnotesRuleId(params.getString("addnotesRuleId"));
			devRuleExec.setDevNo(params.getString("devNo"));
			devRuleExec.setEndDate(params.getString("endDate"));
			devRuleExec.setStartDate(params.getString("startDate"));
			devRuleExec.setStatus(params.getInteger("status"));

			devRuleExecMapper.updateByPrimaryKey(devRuleExec);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "更新执行规则信息状态成功");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "更新执行规则状态异常");
			log.error("updateDevRuleExecStatus Fail: ", e);
		}
		return retMap;
	}

	@Override
	public DevRuleExecDTO qryDevRuleExecByKey(DevRuleExecKey devRuleExecKey) {
		log.info("------------[qryDevRuleExecByKey]DevRuleExecService-------------");
		DevRuleExecDTO devRuleExecDTO = new DevRuleExecDTO();
		try {

			DevRuleExec devRuleExec = devRuleExecMapper.selectDevRuleExecByKey(devRuleExecKey);

			if(devRuleExec != null) {
				devRuleExecDTO.setStatus(devRuleExec.getStatus());
				devRuleExecDTO.setAddnotesRuleId(devRuleExec.getAddnotesRuleId());
				devRuleExecDTO.setDevNo(devRuleExec.getDevNo());
				devRuleExecDTO.setStartDate(devRuleExec.getStartDate());
				devRuleExecDTO.setEndDate(devRuleExec.getEndDate());
			}

		} catch (Exception e) {
			log.error("[qryDevRuleExecByKey]异常", e);
		}
		return devRuleExecDTO;
	}
}
