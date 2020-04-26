package com.zjft.microservice.treasurybrain.param.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.domain.DevChsEstParam;
import com.zjft.microservice.treasurybrain.param.domain.DevChsKeyEvent;
import com.zjft.microservice.treasurybrain.param.dto.EstDTO;
import com.zjft.microservice.treasurybrain.param.dto.KeyDTO;
import com.zjft.microservice.treasurybrain.param.repository.DevChsEstParamMapper;
import com.zjft.microservice.treasurybrain.param.repository.DevChsKeyEventMapper;
import com.zjft.microservice.treasurybrain.param.service.DevconfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FIXME 驼峰命名
 */
@Service
@Slf4j
public class DevconfigServiceImpl implements DevconfigService {

	@Resource
	private DevChsKeyEventMapper devChsKeyEventMapper;

	@Resource
	private DevChsEstParamMapper devChsEstParamMapper;

	@Override
	public Map<String, Object> getDevconfigByClrNo(String createJsonString) {
		log.info("------------[getDevconfigByClrNo]DevconfigServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try{
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

//			关键型参数
			List<DevChsKeyEvent> devChsKeyEvents=devChsKeyEventMapper.getKeyEventByClrNo(clrCenterNo);
			List<KeyDTO> keyList = new ArrayList<KeyDTO>();
			List<KeyDTO> auxList = new ArrayList<KeyDTO>();
			for(DevChsKeyEvent devChsKeyEvent:devChsKeyEvents){
				if(Integer.parseInt(devChsKeyEvent.getEventNo()) <= 3){
					KeyDTO keyDTO =new KeyDTO();
					keyDTO.setClrCenterNo(devChsKeyEvent.getClrCenterNo());
					keyDTO.setEventNo(devChsKeyEvent.getEventNo());
					keyDTO.setEventName(devChsKeyEvent.getEventName());
					keyDTO.setEventDesp(devChsKeyEvent.getEventDesp());
					keyDTO.setIsValid(devChsKeyEvent.getIsValid().intValue());
					keyList.add(keyDTO);
				}else{
					KeyDTO keyDTO =new KeyDTO();
					keyDTO.setClrCenterNo(devChsKeyEvent.getClrCenterNo());
					keyDTO.setEventNo(devChsKeyEvent.getEventNo());
					keyDTO.setEventName(devChsKeyEvent.getEventName());
					keyDTO.setEventDesp(devChsKeyEvent.getEventDesp());
					keyDTO.setIsValid(devChsKeyEvent.getIsValid().intValue());
					auxList.add(keyDTO);
				}

			}

//			预测型参数
			List<DevChsEstParam> devChsEstParams=devChsEstParamMapper.getEstParamByClrNo(clrCenterNo);
			List<EstDTO> estList = new ArrayList<EstDTO>();
			for(DevChsEstParam devChsEstParam:devChsEstParams){
				EstDTO estDTO=new EstDTO();
				estDTO.setClrCenterNo(devChsEstParam.getClrCenterNo());
				estDTO.setEventNo(devChsEstParam.getEstParamNo());
				estDTO.setEventName(devChsEstParam.getEstParamName());
				estDTO.setEventDesp(devChsEstParam.getEstParamDesp());
				estDTO.setIsValid(devChsEstParam.getIsValid().intValue());
				estDTO.setWeight(devChsEstParam.getWeight().doubleValue());
				estList.add(estDTO);
			}

			retMap.put("keyList", keyList);
			retMap.put("estList", estList);
			retMap.put("auxList", auxList);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "参数配置查询成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "设备配置参数查询异常!");
			log.error("getDevconfigByClrNo Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String,Object> devConfig(String createJsonString){
		log.info("------------[devConfig]DevconfigServiceImpl-------------");
		Map<String,Object> retMap=new HashMap<String,Object>();
		retMap.put("retCode",RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg",RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

			//关键型参数
			String keyList = (String) params.get("keyList");
			JSONArray keyObjs=JSONUtil.parseJSONArray(keyList);
			String auxList = (String) params.get("auxList");
			JSONArray auxObjs=JSONUtil.parseJSONArray(auxList);
			keyObjs.addAll(auxObjs);
			for(int i=0;i<keyObjs.size();i++){
				DevChsKeyEvent keyEvent=new DevChsKeyEvent();
				keyEvent.setClrCenterNo(clrCenterNo);
				JSONObject jsonObj=keyObjs.getJSONObject(i);
				keyEvent.setEventDesp(jsonObj.getString("eventDesp"));
				keyEvent.setEventName(jsonObj.getString("eventName"));
				keyEvent.setEventNo(jsonObj.getString("eventNo"));
				keyEvent.setIsValid(jsonObj.getByte("isValid"));
				devChsKeyEventMapper.updateByPrimaryKeySelective(keyEvent);
			}

			//预测型参数
			String estList = (String) params.get("estList");
			JSONArray estObjs=JSONUtil.parseJSONArray(estList);
			for(int i=0;i<estObjs.size();i++){
				DevChsEstParam estParam=new DevChsEstParam();
				JSONObject estObj=estObjs.getJSONObject(i);
				estParam.setClrCenterNo(clrCenterNo);
				estParam.setEstParamDesp(estObj.getString("eventDesp"));
				estParam.setEstParamName(estObj.getString("eventName"));
				estParam.setEstParamNo(estObj.getString("eventNo"));
				estParam.setIsValid(estObj.getByte("isValid"));
//				仅在有效isVaild==1时修改权重,否则使用只是用数据库中的权重
				if(estObj.getByte("isValid")==1){
					estParam.setWeight(estObj.getBigDecimal("weight"));
				}
				devChsEstParamMapper.updateByPrimaryKeySelective(estParam);
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "修改设备参数成功！");
	    } catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "修改设备参数异常!");
		    log.error("devConfig Fail: ", e);
	    }
		return retMap;
	}

	@Override
	public Map<String, Object> getIsValidCountsByClrNo(String createJsonString) {
		log.info("------------[getIsValidCountsByClrNo]DevconfigServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try{
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

//			关键型参数
			Integer count = devChsEstParamMapper.selectIsValidCountsByClrNo(clrCenterNo);

			retMap.put("count", count);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "参数配置查询成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "设备配置参数查询异常!");
			log.error("getDevconfigByClrNo Fail: ", e);
		}
		return retMap;
	}
}
