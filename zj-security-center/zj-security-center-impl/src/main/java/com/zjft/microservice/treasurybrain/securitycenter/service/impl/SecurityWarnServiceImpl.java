package com.zjft.microservice.treasurybrain.securitycenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.securitycenter.domain.SampleMessageInfo;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SecurityMessageResponseDTO;
import com.zjft.microservice.treasurybrain.securitycenter.mapstruct.SampleMessageConverter;
import com.zjft.microservice.treasurybrain.securitycenter.repository.SampleMessageMapper;
import com.zjft.microservice.treasurybrain.securitycenter.service.SecurityWarnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class SecurityWarnServiceImpl implements SecurityWarnService {

	@Resource
	private SampleMessageMapper sampleMessageMapper;

	@Override
	public Map<String, Object> qrySecurityWarnInfo(String string) {
		log.info("------------[qrySecurityWarnInfo]SecurityWarnService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String warnType = StringUtil.parseString(params.get("warnType"));
			String startDate = StringUtil.parseString(params.get("startDate"));
			String endDate = StringUtil.parseString(params.get("endDate"));
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));

			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("warnType", warnType);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("clrCenterNo",clrCenterNo);

			int totalRow = sampleMessageMapper.qryTotalRowForMonth(paramMap);
			int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;

			List<SampleMessageInfo> sampleMessageInfos = sampleMessageMapper.qrySecurityWarnInfoForMonth(paramMap);

			List<SecurityMessageResponseDTO> retList = new ArrayList<SecurityMessageResponseDTO>();
			for (SampleMessageInfo sampleMessageInfo : sampleMessageInfos) {
				SecurityMessageResponseDTO securityMessageResponseDTO = SampleMessageConverter.INSTANCE.domain2dto(sampleMessageInfo);
				retList.add(securityMessageResponseDTO);
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询安防预警信息成功！");
			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询安防预警信息失败!");
			return retMap;
		}

	}

}



