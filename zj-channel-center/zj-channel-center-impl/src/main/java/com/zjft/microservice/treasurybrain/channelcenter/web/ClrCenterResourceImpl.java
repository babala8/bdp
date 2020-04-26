package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.service.ClrCenterService;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 常健
 * @since 2019-04-02
 */

@Slf4j
@RestController
public class ClrCenterResourceImpl implements ClrCenterResource {

	@Resource(name = "baseClrCenterService")
	private ClrCenterService clrCenterService;

	@Override
	public ListDTO<ClrCenterDTO> qryClrCenterListByAuth(Map<String, Object> paramMap) {
		log.info("----------[qryClrCenterListByAuth]ClrCenterResource----------------");
		ListDTO<ClrCenterDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			listDTO = clrCenterService.qryClrCenterListByAuth(paramMap);
		} catch (Exception e) {
			log.error("根据用户权限查询金库列表失败", e);
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}

	@Override
	public ObjectDTO getClrCenterByNo(String clrCenterNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);

			Map<String, Object> retMap = clrCenterService.getClrCenterByClrCenterNo(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	@Override
	public DTO updateCenterNum(ClrCenterDTO clrCenterDTO) {
		try {
			return clrCenterService.updateCenterNum(clrCenterDTO);
		} catch (Exception e) {
			log.error("[金库参数调整异常]: ", e);
			return new UserDTO(RetCodeEnum.EXCEPTION);
		}
	}
}
