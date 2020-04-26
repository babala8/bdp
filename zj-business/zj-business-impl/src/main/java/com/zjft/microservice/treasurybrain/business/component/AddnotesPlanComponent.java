package com.zjft.microservice.treasurybrain.business.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.business.service.GroupService;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ZjComponentResource(group = "addnotesPlan")
public class AddnotesPlanComponent {
	@Resource
	private GroupService groupService;

	/**
	 * 计划分组信息查询
	 */
	@ZjComponentMapping("qryGroupTsp(计划分组信息查询)")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qryGroupTsp(String addnotesPlanNo, ObjectDTO returnDTO, List<String> taskNos) {
		Map<String, Object> element = new HashMap<String, Object>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("addnotesPlanNo", addnotesPlanNo);
		try {
			Map<String, Object> aMap = groupService.qryGroupsTsp(JSONUtil.createJsonString(params));

			List<HashMap<String, Object>> group = (List<HashMap<String, Object>>) aMap.get("groupList");
			List<HashMap<String, Object>> netPointsNotGroup = (List<HashMap<String, Object>>) aMap.get("netPointsNotGroupList");
			element.put("planAddnotesDate", aMap.get("planAddnotesDate"));
			element.put("netPointNum", aMap.get("netPointNum"));
			element.put("planDevCount", aMap.get("planDevCount"));
			element.put("groupNum", aMap.get("groupNum"));
			element.put("clrCenterNo", aMap.get("clrCenterNo"));
			element.put("maxNetpointNumOfGroup", aMap.get("maxNetpointNumOfGroup"));
			element.put("earliestStartTime", aMap.get("earliestStartTime"));
			element.put("lastestArrivalTime", aMap.get("lastestArrivalTime"));
			element.put("cashTruckNum", aMap.get("cashTruckNum"));
			element.put("lineMode", aMap.get("lineMode"));
			element.put("group", group);
			element.put("netPointsNotGroup", netPointsNotGroup);

			returnDTO.setElement(element);
			returnDTO.setRetCode(StringUtil.parseString(aMap.get("retCode")));
			returnDTO.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
		}catch (Exception e){
			log.error("查询计划分组信息失败:", e);
			returnDTO.setRetException("查询失败");
			return "fail";
		}
		return "ok";
	}


	/**
	 * 计划分组信息查询失败
	 */
	@ZjComponentMapping("qryGroupTspFail(计划分组信息查询失败)")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String qryGroupTspFail(String addnotesPlanNo, ObjectDTO returnDTO, List<String> taskNos) {
		returnDTO.setRetCode("FF");
		return "fail";
	}


}
