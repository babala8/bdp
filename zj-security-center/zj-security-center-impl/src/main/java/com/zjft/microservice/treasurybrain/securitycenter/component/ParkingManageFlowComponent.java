package com.zjft.microservice.treasurybrain.securitycenter.component;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author 韩通
 * @since 2020-01-09
 */
@Slf4j
@ZjComponentResource(group = "parkingManage")
public class ParkingManageFlowComponent {

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;
	/**
	 * 泊车引导检查类型
	 */
	@ZjComponentMapping("parkingGuideTypeCheck")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String parkingGuideTypeCheck(HashMap requestHashMap, DTO returnDTO, HashMap<String, Object> paramMap) {
		try{
			String parkingGuideType = StringUtil.parseString(requestHashMap.get("parkingGuideType"));

			paramMap.put("type",parkingGuideType);
			if("callOutType".equals(parkingGuideType)) {
				return  "parkingCall";
			}else if("callEnterType".equals(parkingGuideType)) {
				return  "enterType";
			}else if ("callLeaveType".equals(parkingGuideType)){
				return  "leaveType";
			}
		}
		catch(Exception e) {
			log.error("泊车引导失败", e);
			returnDTO.setRetException("泊车引导失败!");
		}
		return "fail";
	}

	/**
	 * 泊车引导检查类型
	 */
	@ZjComponentMapping("parkingCall")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String parkingCall(HashMap requestHashMap, DTO returnDTO, HashMap<String, Object> paramMap) {
		try{
			ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>)requestHashMap.get("parkingGuideList");

			for(int i= 0;i < list.size();i++) {
				LinkedHashMap parkingGuide = (LinkedHashMap)list.get(i);

				String parkingCarNo =  StringUtil.parseString(parkingGuide.get("parkingCarNo"));
				String parkingCarType =  StringUtil.parseString(parkingGuide.get("parkingCarType"));
				String parkingCarLeaveDate =  StringUtil.parseString(parkingGuide.get("parkingCarLeaveDate"));
				String parkingCarEnterDate =  StringUtil.parseString(parkingGuide.get("parkingCarEnterDate"));
				String lineName = StringUtil.parseString(parkingGuide.get("lineName"));
				int location = Integer.parseInt(StringUtil.parseString(parkingGuide.get("location")));

				paramMap.put("parkingCarNo", parkingCarNo);
				paramMap.put("parkingCarType", parkingCarType);
				paramMap.put("parkingCarLeaveDate", parkingCarLeaveDate);
				paramMap.put("parkingCarEnterDate", parkingCarEnterDate);
				paramMap.put("lineName", lineName);
				paramMap.put("location", location);

				String message = JSONObject.toJSONString(paramMap);
				DTO dto = sendInfoInnerResource.sendInfo2All(message);

				returnDTO.setRetCode(dto.getRetCode());
				returnDTO.setRetMsg("泊车引导信息创建成功,发送到成功");

			}
			return "ok";
		}
		catch(Exception e) {
			log.error("泊车引导失败", e);
			returnDTO.setRetException("泊车引导失败!");
		}
		return "fail";
	}

	/**
	 * 泊车引导检查类型
	 */
	@ZjComponentMapping("enterType")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String enterType(HashMap requestHashMap, DTO returnDTO, HashMap<String, Object> paramMap) {
		try{
			ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>)requestHashMap.get("ParkingGuideList");

			for(int i= 0;i < list.size();i++) {
				LinkedHashMap parkingGuide = (LinkedHashMap)list.get(i);

				String parkingCarNo =  StringUtil.parseString(parkingGuide.get("parkingCarNo"));
				String parkingCarType =  StringUtil.parseString(parkingGuide.get("parkingCarType"));
				String parkingCarLeaveDate =  StringUtil.parseString(parkingGuide.get("parkingCarLeaveDate"));
				String parkingCarEnterDate =  StringUtil.parseString(parkingGuide.get("parkingCarEnterDate"));
				String lineName = StringUtil.parseString(parkingGuide.get("lineName"));
				int location = Integer.parseInt(StringUtil.parseString(parkingGuide.get("location")));

				paramMap.put("parkingCarNo", parkingCarNo);
				paramMap.put("parkingCarType", parkingCarType);
				paramMap.put("parkingCarLeaveDate", parkingCarLeaveDate);
				paramMap.put("parkingCarEnterDate", parkingCarEnterDate);
				paramMap.put("lineName", lineName);
				paramMap.put("location", location);

				String message = JSONObject.toJSONString(paramMap);
				DTO dto = sendInfoInnerResource.sendInfo2All(message);

				returnDTO.setRetCode(dto.getRetCode());
				returnDTO.setRetMsg("泊车引导信息创建成功,发送到成功");

			}
			return "ok";
		}
		catch(Exception e) {
			log.error("泊车引导失败", e);
			returnDTO.setRetException("泊车引导失败!");
		}
		return "fail";
	}

	/**
	 * 泊车引导检查类型
	 */
	@ZjComponentMapping("leaveType")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String leaveType(HashMap requestHashMap, DTO returnDTO, HashMap<String, Object> paramMap) {
		try{
			ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>)requestHashMap.get("ParkingGuideList");

			for(int i= 0;i < list.size();i++) {
				LinkedHashMap parkingGuide = (LinkedHashMap)list.get(i);

				String parkingCarNo =  StringUtil.parseString(parkingGuide.get("parkingCarNo"));
				String parkingCarType =  StringUtil.parseString(parkingGuide.get("parkingCarType"));
				String parkingCarLeaveDate =  StringUtil.parseString(parkingGuide.get("parkingCarLeaveDate"));
				String parkingCarEnterDate =  StringUtil.parseString(parkingGuide.get("parkingCarEnterDate"));
				String lineName = StringUtil.parseString(parkingGuide.get("lineName"));
				int location = Integer.parseInt(StringUtil.parseString(parkingGuide.get("location")));

				paramMap.put("parkingCarNo", parkingCarNo);
				paramMap.put("parkingCarType", parkingCarType);
				paramMap.put("parkingCarLeaveDate", parkingCarLeaveDate);
				paramMap.put("parkingCarEnterDate", parkingCarEnterDate);
				paramMap.put("lineName", lineName);
				paramMap.put("location", location);

				String message = JSONObject.toJSONString(paramMap);
				DTO dto = sendInfoInnerResource.sendInfo2All(message);

				returnDTO.setRetCode(dto.getRetCode());
				returnDTO.setRetMsg("泊车引导信息创建成功,发送到成功");

			}
			return "ok";
		}
		catch(Exception e) {
			log.error("泊车引导失败", e);
			returnDTO.setRetException("泊车引导失败!");
		}
		return "fail";
	}
}
