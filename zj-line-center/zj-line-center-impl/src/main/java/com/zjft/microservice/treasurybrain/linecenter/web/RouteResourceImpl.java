package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class RouteResourceImpl implements RouteResource {

	@Resource
	private RouteService routeService;

	@Override
	public PageDTO<NetpointMatrixDTO> qryNetMatrixByPage(Map<String, Object> paramMap) {
		PageDTO<NetpointMatrixDTO> dto = new PageDTO<NetpointMatrixDTO>();
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			int type = StringUtil.objectToInt(paramMap.get("type"));
			int tactic = StringUtil.objectToInt(paramMap.get("tactic"));
			int dataType = StringUtil.objectToInt(paramMap.get("dataType"));
			String startPointNo = StringUtil.parseString(paramMap.get("startPointNo"));
			String endPointNo = StringUtil.parseString(paramMap.get("endPointNo"));
			Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

			if (dataType == -1) {
				dataType = 1;
			}

			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 10;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("type", type);
			params.put("tactic", tactic);
			params.put("dataType", dataType);
			params.put("startPointNo", startPointNo);
			params.put("endPointNo", endPointNo);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> aMap = routeService.qryNetMatrixByPage(JSONUtil.createJsonString(params));

			List<NetpointMatrixDTO> retList = (List<NetpointMatrixDTO>) aMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	@Override
	public ListDTO qryPathLinked(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "获取失败");
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			int dataType = StringUtil.objectToInt(paramMap.get("dataType"));
			if (dataType == -1) {
				dataType = 1;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("dataType", dataType);

			Map<String, Object> retMap = routeService.qryPathLinked(params);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

			List retList = (List) retMap.get("retList");
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("关联失败", e);
			dto.setRetException("获取失败!");
		}
		return dto;
	}

	@Override
	public DTO linkPath(Map<String, Object> paramMap) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), "关联失败");
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			int cover = StringUtil.objectToInt(paramMap.get("cover"));
			int tactic = StringUtil.objectToInt(paramMap.get("tactic"));
			int dataType = StringUtil.objectToInt(paramMap.get("dataType"));
			if (dataType == -1) {
				dataType = 1;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("cover", cover);
			params.put("tactic", tactic);
			params.put("dataType", dataType);

			Map<String, Object> retMap = routeService.linkPath(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("请求关联失败", e);
			dto.setRetException("请求关联失败!");
		}
		return dto;
	}

	@Override
	public ObjectDTO qryClrNetPointMatrixStatus(String clrCenterNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "关联失败");
		try {
			Map<String, Object> retMap = routeService.qryClrNetPointMatrixStatus(clrCenterNo);

			dto.setElement(retMap);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询清分中心的是否正在关联失败", e);
			dto.setRetException("查询清分中心的是否正在关联失败!");
		}
		return dto;
	}

}
