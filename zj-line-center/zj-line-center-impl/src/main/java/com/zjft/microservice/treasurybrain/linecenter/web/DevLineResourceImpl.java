package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.AddnotesLineDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.DevLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/12/10
 */
@Slf4j
@RestController
public class DevLineResourceImpl implements DevLineResource {


	@Resource
	private DevLineService devLineService;

	@Override
	public DTO directionRoute(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "规划两点间线路失败");
		try {
			Map<String, Object> retMap = devLineService.directionRoute(JSONUtil.createJsonString(paramMap));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap.get("routes"));
		} catch (Exception e) {
			log.error("规划两点间线路失败: ", e);
			dto.setRetException("规划两点间线路失败");
		}
		return dto;
	}

	@Override
	public ObjectDTO qryPlanGroupRoute(Map<String, Object> paramMap) {
		log.info("------------[getPlanGroupRoute]AddnotesPlanWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(paramMap.get("groupNo"));
			int tactic = StringUtil.objectToInt(paramMap.get("tactic"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("groupNo", groupNo);
			params.put("tactic", tactic);

			Map<String, Object> retMap = devLineService.qryPlanGroupRoute(JSONUtil.createJsonString(params));

			dto.setElement(retMap);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询分组下的网点信息失败:", e);
			dto.setRetException("查询分组下的网点信息失败:");
			return dto;
		}
		return dto;
	}

	@Override
	public DTO autoGroupTsp(Map<String, Object> paramMap) {
		log.info("------------[autoGroupTsp]AddnotesPlanWebService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			int tspFlag = StringUtil.objectToInt(paramMap.get("tspFlag"));
			int tactic = StringUtil.objectToInt(paramMap.get("tactic"));
			int groupNum = StringUtil.objectToInt(paramMap.get("groupNum"));
			int maxNetpointNumOfGroup = StringUtil.objectToInt(paramMap.get("maxNetpointNumOfGroup"));
			String earliestStartTime = StringUtil.parseString(paramMap.get("earliestStartTime"));
			String lastestArrivalTime = StringUtil.parseString(paramMap.get("lastestArrivalTime"));
			String groupType = StringUtil.parseString(paramMap.get("groupType"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("tspFlag", tspFlag);
			params.put("tactic", tactic);
			params.put("groupNum", (groupNum <= 1 ? 1 : groupNum));
			params.put("maxNetpointNumOfGroup", maxNetpointNumOfGroup);
			params.put("earliestStartTime", earliestStartTime);
			params.put("lastestArrivalTime", lastestArrivalTime);
			params.put("groupType", groupType);

			Map<String, Object> retMap = devLineService.autoGroupTsp(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("设备分组线路规划失败:", e);
			dto.setRetException("设备分组线路规划失败");
			return dto;
		}
		return dto;
	}

	@Override
	public DTO modGroupTsp(Map<String, Object> paramMap) {
		log.info("------------[modGroupTsp]AddnotesPlanWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "修改失败");
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(paramMap.get("groupNo"));
			String netPointsGroup = JSONUtil.createJsonString(paramMap.get("netPointsGroup"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("groupNo", groupNo);
			params.put("netPointsGroup", netPointsGroup);

			Map<String, Object> retMap = devLineService.modGroupTsp(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("分组线路信息修改失败:", e);
			dto.setRetException("分组线路信息修改失败");
			return dto;
		}
		return dto;
	}


	@Override
	public PageDTO<LineTableDTO> qryAddnotesLineByPage(Map<String, Object> paramMap) {
		log.info("------------[qryAddnotesLine]AddnotesLineWebService-------------");
		PageDTO<LineTableDTO> dto = new PageDTO<LineTableDTO>();
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String lineName = StringUtil.parseString(paramMap.get("lineName"));
			Integer lineType = StringUtil.objectToInt(paramMap.get("lineType"));
			int curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			int pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 20;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("lineName", lineName);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);
			params.put("lineType", lineType);

			Map<String, Object> retMap = devLineService.qryAddnotesLineByPage(JSONUtil.createJsonString(params));

			List<LineTableDTO> retList = (List<LineTableDTO>) retMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(retMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(retMap.get("totalPage"))));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询加钞线路失败", e);
			dto.setRetException("查询加钞线路失败！");
		}
		return dto;
	}

	@Override
	public DTO addAddnotesLine(LineTableDTO lineTableDTO) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "添加失败");
		try {
			String clrCenterNo = StringUtil.parseString(lineTableDTO.getClrCenterNo());
			String lineName = StringUtil.parseString(lineTableDTO.getLineName());
			String note = StringUtil.parseString(lineTableDTO.getNote());
			Integer addClrPeriod = StringUtil.objectToInt(lineTableDTO.getAddClrPeriod());
			Integer lineType = StringUtil.objectToInt(lineTableDTO.getLineType());

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("lineName", lineName);
			params.put("addClrPeriod", addClrPeriod);
			params.put("note", note);
			params.put("lineType", lineType);

			Map<String, Object> retMap = devLineService.addAddnotesLine(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("添加加钞线路失败:", e);
			dto.setRetException("添加加钞线路失败!");
		}
		return dto;
	}

	@Override
	public DTO modAddnotesLine(LineTableDTO lineTableDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), "修改失败");
		try {
			String lineNo = StringUtil.parseString(lineTableDTO.getLineNo());
			String lineName = StringUtil.parseString(lineTableDTO.getLineName());
			String note = StringUtil.parseString(lineTableDTO.getNote());
			Integer addClrPeriod = StringUtil.objectToInt(lineTableDTO.getAddClrPeriod());
			Integer lineType = StringUtil.objectToInt(lineTableDTO.getLineType());

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("lineNo", lineNo);
			params.put("lineName", lineName);
			params.put("addClrPeriod", addClrPeriod);
			params.put("note", note);
			params.put("lineType", lineType);

			Map<String, Object> retMap = devLineService.modAddnotesLine(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("修改加钞线路失败:", e);
			dto.setRetException("修改加钞线路失败!");
		}
		return dto;
	}

	@Override
	public ObjectDTO qrydetailAddnotesLine(String lineNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询详情失败");
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("lineNo", lineNo);

			Map<String, Object> retMap = devLineService.qryAddnotesLineDetail(JSONUtil.createJsonString(params));

			dto.setElement(retMap.get("addnotesLineDTO"));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询加钞线路详情异常:", e);
			dto.setRetException("查询加钞线路详情异常!");
		}
		return dto;
	}

	@Override
	public DTO delAddnotesLine(String lineNo) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), "删除失败");
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("lineNo", lineNo);

			Map<String, Object> retMap = devLineService.delAddnotesLine(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("删除加钞线路失败:", e);
			dto.setRetException("删除加钞线路失败!");
		}
		return dto;
	}


	@Override
	public ListDTO<LineTableDTO> qryLineListByDateAndClrNo(Map<String, Object> paramMap) {
		ListDTO<LineTableDTO> dto = new ListDTO<LineTableDTO>(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			Map<String, Object> retMap = devLineService.qryLineListByDateAndClrNo(JSONUtil.createJsonString(paramMap));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			List<LineTableDTO> clrCenterDTOs = (List<LineTableDTO>) retMap.get("retList");
			dto.setRetList(clrCenterDTOs);
		} catch (Exception e) {
			log.error("查询失败:", e);
		}
		return dto;
	}

}
