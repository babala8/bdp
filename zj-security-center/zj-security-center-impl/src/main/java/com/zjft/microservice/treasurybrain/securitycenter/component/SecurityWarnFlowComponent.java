package com.zjft.microservice.treasurybrain.securitycenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.securitycenter.domain.SampleMessageInfo;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SecurityMessageResponseDTO;
import com.zjft.microservice.treasurybrain.securitycenter.mapstruct.SampleMessageConverter;
import com.zjft.microservice.treasurybrain.securitycenter.repository.SampleMessageMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author 韩通
 * @since 2020-01-09
 */
@Slf4j
@ZjComponentResource(group = "securityWarn")
public class SecurityWarnFlowComponent {

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private SampleMessageMapper sampleMessageMapper;

	/**
	 * 查询安防预警信息参数校验
	 */
	@ZjComponentMapping("qrySecurityWarnInfoCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qrySecurityWarnInfoCheck(HashMap requestHashMap, PageDTO<SecurityMessageResponseDTO> returnPageDTO, HashMap<String, Object> paramMap) {
		try{
			String warnType = StringUtil.parseString(requestHashMap.get("warnType"));
			String startDate = StringUtil.parseString(requestHashMap.get("startDate"));
			String endDate = StringUtil.parseString(requestHashMap.get("endDate"));
			String clrCenterNo = StringUtil.parseString(requestHashMap.get("clrCenterNo"));

			Integer curPage = StringUtil.objectToInt(requestHashMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(requestHashMap.get("pageSize"));

			paramMap.put("warnType", warnType);
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("curPage", curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("clrCenterNo", clrCenterNo);
		}catch (Exception e){
			log.error("查询安防预警信息失败", e);
			returnPageDTO.setRetException("查询安防预警信息失败");
			return "fail";
		}
		return "ok";
	}

	/**
	 * 查询安防预警信息
	 */
	@ZjComponentMapping("qrySecurityWarnInfo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qrySecurityWarnInfo(HashMap requestHashMap, PageDTO<SecurityMessageResponseDTO> returnPageDTO,  HashMap<String, Object> paramMap) {
		try {
			int pageSize = PageUtil.transParam2Page(paramMap, returnPageDTO);
			int totalRow = sampleMessageMapper.qryTotalRowForMonth(paramMap);
			int totalPage = PageUtil.computeTotalPage(pageSize,totalRow);

			List<SampleMessageInfo> sampleMessageInfos = sampleMessageMapper.qrySecurityWarnInfoForMonth(paramMap);
			List<SecurityMessageResponseDTO> securityMessageResponseDTOS = SampleMessageConverter.INSTANCE.domain2dto(sampleMessageInfos);

			returnPageDTO.setTotalRow(totalRow);
			returnPageDTO.setTotalPage(totalPage);
			returnPageDTO.setRetList(securityMessageResponseDTOS);
			returnPageDTO.setRetCode(RetCodeEnum.SUCCEED.getCode());
			returnPageDTO.setRetMsg("查询安防预警信息成功！");
			return "ok";
		} catch (Exception e) {
			returnPageDTO.setRetCode(RetCodeEnum.EXCEPTION.getCode());
			returnPageDTO.setRetMsg("查询安防预警信息失败!");
			return "fail";
		}
	}

	/**
	 * 发送安防预警信息
	 */
	@ZjComponentMapping("sendSecurityMessage")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sendSecurityMessage(HashMap requestHashMap, DTO returnDTO, HashMap<String, Object> paramMap) {
		try{
			log.info(requestHashMap.toString());

			String warnMessage = StringUtil.parseString(requestHashMap.get("warnMessage"));
			String warnType = StringUtil.parseString(requestHashMap.get("warnType"));

			paramMap.put("warnMessageInfo", warnMessage);
			paramMap.put("warnType", warnType);
			DTO dto = sendInfoInnerResource.sendInfo2All(JSONUtil.createJsonString(paramMap));

			returnDTO.setRetCode(dto.getRetCode());
			returnDTO.setRetMsg(dto.getRetMsg());
			return "ok";
		}
		catch(Exception e) {
			log.error("安防预警信息发送失败", e);
			returnDTO.setRetException("安防预警信息发送失败!");
			return "fail";
		}
	}
}
