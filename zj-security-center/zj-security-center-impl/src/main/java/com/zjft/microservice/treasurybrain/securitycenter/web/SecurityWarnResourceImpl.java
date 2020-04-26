package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SecurityMessageResponseDTO;
import com.zjft.microservice.treasurybrain.securitycenter.service.SecurityWarnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhangjs
 * @since 2019/8/28 18:51
 */
@Slf4j
@RestController
public class SecurityWarnResourceImpl implements SecurityWarnResource {

	@Resource
	private SecurityWarnService securityWarnService;

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Override
	public PageDTO<SecurityMessageResponseDTO> qrySecurityWarnInfo(Map<String, Object> paramMap) {
		PageDTO<SecurityMessageResponseDTO> dto = new PageDTO<SecurityMessageResponseDTO>();
		try{
			String warnType = StringUtil.parseString(paramMap.get("warnType"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));

			Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));


			if(-1==curPage) {
				curPage = 1;
			}
			if(-1==pageSize) {
				pageSize = 20;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("warnType", warnType);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);
			params.put("clrCenterNo", clrCenterNo);

			Map<String, Object> aMap = securityWarnService.qrySecurityWarnInfo(JSONUtil.createJsonString(params));

			List<SecurityMessageResponseDTO> retList = (List<SecurityMessageResponseDTO>)aMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setRetCode(StringUtil.parseString(aMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);

		}catch (Exception e){
			log.error("查询安防预警信息失败", e);
			dto.setRetException("查询安防预警信息失败");
		}

		return dto;
	}

	@Override
	public DTO sendSecurityMessage(Map<String, Object> paramMap) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"安防预警信息发送失败");
		try{
			log.info(paramMap.toString());

            String warnMessage = StringUtil.parseString(paramMap.get("warnMessage"));
            String warnType = StringUtil.parseString(paramMap.get("warnType"));

            HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("warnMessageInfo", warnMessage);
			params.put("warnType", warnType);
            DTO dto1 = sendInfoInnerResource.sendInfo2All(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(dto1.getRetCode()));
			dto.setRetMsg(StringUtil.parseString(dto1.getRetMsg()));

		}
		catch(Exception e) {
			log.error("安防预警信息发送失败", e);
			dto.setRetException("安防预警信息发送失败!");
		}
		return dto;
	}
}
