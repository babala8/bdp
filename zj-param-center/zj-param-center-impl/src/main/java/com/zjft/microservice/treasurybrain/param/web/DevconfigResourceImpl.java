package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.dto.EstDTO;
import com.zjft.microservice.treasurybrain.param.dto.KeyDTO;
import com.zjft.microservice.treasurybrain.param.service.DevconfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DevconfigResourceImpl implements DevconfigResource {

	@Resource
	private DevconfigService devconfigService;

	@Override
	public ObjectDTO getDevconfig(String clrCenterNo){
		log.info("------------[getDevconfig]DevconfigWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		Map<String,Object> element=new HashMap<String,Object>();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);

			Map<String, Object> retMap = devconfigService.getDevconfigByClrNo(JSONUtil.createJsonString(params));
			List<KeyDTO> keyDTOs = (List<KeyDTO>)retMap.get("keyList");
			List<EstDTO> estDTOs = (List<EstDTO>)retMap.get("estList");
			List<KeyDTO> keyDTOs1 = (List<KeyDTO>)retMap.get("auxList");
			element.put("keyList",keyDTOs);
			element.put("estList",estDTOs);
			element.put("auxList",keyDTOs1);
			dto.setElement(element);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询设备参数失败:",e);
			dto.setRetException("查询设备参数失败");
			return dto;
		}
		return dto;
	}

	@Override
	public DTO devConfig(Map<String,Object> paramMap){
		log.info("------------[devConfig]DevconfigWebService-------------");
		DTO dto=new DTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String keyList = JSONUtil.createJsonString(paramMap.get("keyList"));
			String estList = JSONUtil.createJsonString(paramMap.get("estList"));
			String auxList = JSONUtil.createJsonString(paramMap.get("auxList"));
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("keyList", keyList);
			params.put("estList", estList);
			params.put("auxList", auxList);

			Map<String,Object> retMap=devconfigService.devConfig(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("修改设备参数失败:",e);
			dto.setRetException("修改设备参数失败");
			return dto;
		}
		return dto;
	}

	@Override
	public ObjectDTO qryIsValidCountsByClrNo(String clrCenterNo) {
		log.info("------------[qryIsValidCountsByClrNo]DevconfigWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		Map<String,Object> element=new HashMap<String,Object>();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);

			Map<String, Object> retMap = devconfigService.getIsValidCountsByClrNo(JSONUtil.createJsonString(params));

			dto.setElement(retMap.get("count"));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询设备参数数量失败:",e);
			dto.setRetException("查询设备参数数量失败");
			return dto;
		}
		return dto;
	}

}
