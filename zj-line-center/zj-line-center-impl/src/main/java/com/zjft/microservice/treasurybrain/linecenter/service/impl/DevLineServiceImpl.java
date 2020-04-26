package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanGroupDTO;
import com.zjft.microservice.treasurybrain.business.web_inner.AddnotesPlanDetailInnerResource;
import com.zjft.microservice.treasurybrain.business.web_inner.AddnotesPlanGroupInnerResource;
import com.zjft.microservice.treasurybrain.business.web_inner.AddnotesPlanInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web.SysOrgResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.Matrix;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrix;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrixKey;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.AddnotesLineConverter;
import com.zjft.microservice.treasurybrain.linecenter.repository.*;
import com.zjft.microservice.treasurybrain.linecenter.service.DevLineService;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 常 健
 * @since 2019/12/10
 */
@Slf4j
@Service
public class DevLineServiceImpl implements DevLineService {


	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Resource
	private SysOrgResource sysOrgResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private LineNetPointMatrixMapper lineNetPointMatrixMapper;

	@Resource
	RestTemplate restTemplate;

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private LineAddnoteLineMapper lineAddnoteLineMapper;

	@Resource
	private AddnotesPlanDetailInnerResource addnotesPlanDetailInnerResource;

	@Resource
	private AddnotesPlanGroupInnerResource addnotesPlanGroupInnerResource;

	@Resource
	private AddnotesPlanInnerResource addnotesPlanInnerResource;

	@Resource
	private LineTableMapper lineTableMapper;

	/**
	 * 规划两点间线路
	 *
	 * @param jsonParam clrOrgNo:清机中心机构编号,originX:起点经度,originY:起点纬度,destinationX:终点经度
	 *                  destinationY:终点纬度,mode:导航模式,tactic:导航策略
	 * @return retMap:routes
	 */
	@Override
	public Map<String, Object> directionRoute(String jsonParam) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			String originX = StringUtil.parseString(params.get("originX"));
			String originY = StringUtil.parseString(params.get("originY"));
			String destinationX = StringUtil.parseString(params.get("destinationX"));
			String destinationY = StringUtil.parseString(params.get("destinationY"));
			String mode = StringUtil.parseString(params.get("mode"));
			String clrOrgNo = StringUtil.parseString(params.get("clrOrgNo"));
			String tactics = StringUtil.parseString(params.get("tactic"));

			String origin = originY + "," + originX;
			String destination = destinationY + "," + destinationX;
//			String region = orgService.qryOrgRegion(clrOrgNo);
			String region = null;
			try {
//				List<String> regionList = sysOrgMapper.qryOrgRegion(clrOrgNo);
				List<String> regionList = sysOrgInnerResource.qryOrgRegion(clrOrgNo);
				if (regionList != null) {
					region = StringUtil.parseString(regionList.get(0));
				}
			} catch (Exception e) {
				log.error("Exception in qryClrCenterRegion: ", e);
			}
			String ak = StringUtil.parseString(CfgProperty.getProperty("mapApiAk"));
			String routes = StringUtil.parseString(
					HttpClientUtil.direction(origin, destination, mode, region, region, region, tactics, ak));
			if ("".equals(routes)) {
				return retMap;
			}
			retMap.put("routes", routes);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "规划两点间线路成功!");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "规划两点间线路异常!");
			log.error("directionRoute Exception: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryPlanGroupRoute(String createJsonString) {
		log.info("------------[getPlanGroupRoute]GroupService-------------");

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(params.get("groupNo"));
			int tactic = StringUtil.objectToInt(params.get("tactic"));

			Integer clrTimeInterval = 0; //分组编号变为0,1,2.。后此处修改为全天
			// 计划分组
			Map<String, Object> map1 = new HashMap<>();
			map1.put("addnotesPlanNo", addnotesPlanNo);
			map1.put("groupNo", groupNo);
			map1.put("clrTimeInterval", clrTimeInterval);

			AddnotesPlanGroupDTO addnotesPlanGroupDTO = addnotesPlanGroupInnerResource.selectByPrimaryKeyMap(map1);
			if (addnotesPlanGroupDTO == null) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
				log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]不存在");
				return retMap;
			}

			// 总路程&&总耗时
			Integer totalDistance = addnotesPlanGroupDTO.getPlanDistance();
			Integer totalTime = addnotesPlanGroupDTO.getPlanTimeCost();
			if (totalDistance == null || totalTime == null) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划未排序");
				log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]未排序");
				return retMap;
			}
			retMap.put("planDistance", totalDistance);
			retMap.put("planTimeCost", totalTime);

			AddnotesPlanDTO addnotesPlanDTO = addnotesPlanInnerResource.selectDetailByPrimaryKey(addnotesPlanNo);
			String clrCenterTable = addnotesPlanDTO.getClrCenterName();
			if (clrCenterTable == null || "".equals(clrCenterTable)) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划找不到清分中心");
				log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]找不到清机中心");
				return retMap;
			}

			String clrCenterNo = addnotesPlanDTO.getClrCenterNo();
			String clrCenterOrgNo, clrName = null;
			String clrAddress = null;
			double clrx = -1;
			double clry = -1;
//			List<Map<String, Object>> clrList = clrCenterTableMapper.getClrCenterByClrNo(clrCenterNo);
			List<Map<String, Object>> clrList = clrCenterInnerResource.getClrCenterByClrNo(clrCenterNo);
			if (clrList != null && clrList.size() > 0) {
				clrCenterOrgNo = StringUtil.parseString(clrList.get(0).get("bankOrgNo"));
				SysOrgDTO sysOrgDTO = sysOrgResource.qrySysOrgDetailByNo(clrCenterOrgNo);
				clrAddress = sysOrgDTO.getAddress();
				clrName = StringUtil.parseString(clrList.get(0).get("centerName"));
				clrx = StringUtil.ch2Double(clrList.get(0).get("x").toString());
				clry = StringUtil.ch2Double(clrList.get(0).get("y").toString());
			} else {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划找不到清分中心");
				log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]找不到清机中心");
				return retMap;
			}

			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("addnotesPlanNo", addnotesPlanNo);
			paramsMap.put("groupNo", groupNo);
			paramsMap.put("tactic", tactic);
			List<Map<String, Object>> netpoints = addnotesPlanDetailInnerResource.getPlanGroupNetpoints(paramsMap);
			if (netpoints == null || netpoints.size() == 0) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划找不到网点信息");
				log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]找不到网点信息");
				return retMap;
			}

			List<Map<String, Object>> routeList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < netpoints.size(); i++) {
				int sortNo = StringUtil.objectToInt(netpoints.get(i).get("sortNo"));
				String orgNo = StringUtil.parseString(netpoints.get(i).get("orgNo"));
				String orgName = StringUtil.parseString(netpoints.get(i).get("orgName"));
				String devNo = StringUtil.parseString(netpoints.get(i).get("devNo"));
				SysOrgDTO sysOrgDTO = sysOrgResource.qrySysOrgDetailByNo(orgNo);
				String orgAddress = sysOrgDTO.getAddress();
				double x = StringUtil.ch2Double(netpoints.get(i).get("x").toString());
				double y = StringUtil.ch2Double(netpoints.get(i).get("y").toString());

				// 清分中心->网点
				if (sortNo == 1) {
					Map<String, Object> route = new HashMap<String, Object>();
					NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
					netPointMatrixKey.setStartPointNo(clrCenterOrgNo);
					netPointMatrixKey.setEndPointNo(devNo);
					netPointMatrixKey.setType(1);
					netPointMatrixKey.setTactic(tactic);

					NetPointMatrix netPointMatrix = lineNetPointMatrixMapper.selectByPrimaryKey(netPointMatrixKey);
					if (netPointMatrix == null) {
						retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划找不到点[" + clrCenterOrgNo + "]至[" + devNo + "]关联信息");
						log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]找不到点[" + clrCenterOrgNo + "]至[" + devNo + "]关联信息");
						return retMap;
					}
					int distance = netPointMatrix.getDistance();
					int time = netPointMatrix.getTimeCost();

					route.put("startPointX", clrx);
					route.put("startPointY", clry);
					route.put("startOrgNo", clrCenterOrgNo);
					route.put("startOrgName", clrName);
					route.put("startOrgAddress", clrAddress);

					route.put("endPointX", x);
					route.put("endPointY", y);
					route.put("endOrgNo", devNo);
					route.put("endOrgName", devNo);
					route.put("endOrgAddress", orgAddress);

					route.put("distance", distance);
					route.put("time", time);
					route.put("sortNo", 0);
					routeList.add(route);
				}
				// 网点->清分中心
				if (sortNo == netpoints.size()) {
					Map<String, Object> route = new HashMap<String, Object>();
					NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
					netPointMatrixKey.setStartPointNo(devNo);
					netPointMatrixKey.setEndPointNo(clrCenterOrgNo);
					netPointMatrixKey.setType(2);
					netPointMatrixKey.setTactic(tactic);

					NetPointMatrix netPointMatrix = lineNetPointMatrixMapper.selectByPrimaryKey(netPointMatrixKey);
					if (netPointMatrix == null) {
						retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划找不到点[" + devNo + "]至[" + clrCenterOrgNo + "]关联信息");
						log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]找不到点[" + devNo + "]至[" + clrCenterOrgNo + "]关联信息");
						return retMap;
					}
					int distance = netPointMatrix.getDistance();
					int time = netPointMatrix.getTimeCost();

					route.put("startPointX", x);
					route.put("startPointY", y);
					route.put("startOrgNo", devNo);
					route.put("startOrgName", devNo);
					route.put("startOrgAddress", orgAddress);

					route.put("endPointX", clrx);
					route.put("endPointY", clry);
					route.put("endOrgNo", clrCenterOrgNo);
					route.put("endOrgName", clrName);
					route.put("endOrgAddress", clrAddress);

					route.put("distance", distance);
					route.put("time", time);
					route.put("sortNo", sortNo);
					routeList.add(route);
				}
				// 网点->网点
				if (i < netpoints.size() - 1) {
					Map<String, Object> route = new HashMap<String, Object>();
					String endOrgNo = StringUtil.parseString(netpoints.get(i + 1).get("devNo"));
					String endOrgName = StringUtil.parseString(netpoints.get(i + 1).get("devNo"));
					SysOrgDTO sysOrgDTO1 = sysOrgResource.qrySysOrgDetailByNo(endOrgNo);
					String endAddress = sysOrgDTO1.getAddress();
					double endx = StringUtil.ch2Double(netpoints.get(i + 1).get("x").toString());
					double endy = StringUtil.ch2Double(netpoints.get(i + 1).get("y").toString());
					NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
					netPointMatrixKey.setStartPointNo(devNo);
					netPointMatrixKey.setEndPointNo(endOrgNo);
					netPointMatrixKey.setType(0);
					netPointMatrixKey.setTactic(tactic);

					NetPointMatrix netPointMatrix = lineNetPointMatrixMapper.selectByPrimaryKey(netPointMatrixKey);
					if (netPointMatrix == null) {
						retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划找不到点[" + devNo + "]至[" + endOrgNo + "]关联信息");
						log.error("[getPlanGroupRoute] 计划[" + addnotesPlanNo + "]分组[" + groupNo + "]找不到点[" + devNo + "]至[" + endOrgNo + "]关联信息");
						return retMap;
					}
					int distance = netPointMatrix.getDistance();
					int time = netPointMatrix.getTimeCost();

					route.put("startPointX", x);
					route.put("startPointY", y);
					route.put("startOrgNo", orgNo);
					route.put("startOrgName", orgName);
					route.put("startOrgAddress", orgAddress);

					route.put("endPointX", endx);
					route.put("endPointY", endy);
					route.put("endOrgNo", endOrgNo);
					route.put("endOrgName", endOrgName);
					route.put("endOrgAddress", endAddress);

					route.put("distance", distance);
					route.put("time", time);
					route.put("sortNo", sortNo);
					routeList.add(route);
				}
			}
			retMap.put("routeList", routeList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "分组下的网点信息查询成功!");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "分组下的网点信息查询异常!");
			log.error("getPlanGroupRoute Fail: ", e);
		}
		return retMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> autoGroupTsp(String jsonParam) {
		log.info("------------[autoGroupTsp]GroupService-------------");

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		JSONObject params = JSONUtil.parseJSONObject(jsonParam);
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
		boolean tspFlag = StringUtil.objectToInt(params.get("tspFlag")) == 1 ? true : false;
		int tactic = StringUtil.objectToInt(params.get("tactic"));
		int groupNum = StringUtil.objectToInt(params.get("groupNum"));
		String earliestStartTime = StringUtil.parseString(params.get("earliestStartTime"));
		String lastestArrivalTime = StringUtil.parseString(params.get("lastestArrivalTime"));
		//1-自动分组	2-线路分组
		String groupType = StringUtil.parseString(params.get("groupType"));

		int dataType = 1; //设备关联

		//开始分组
		//查询加钞计划
		AddnotesPlanDTO addnotesPlanDTO = addnotesPlanInnerResource.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlanDTO == null) {
			retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
			log.error("[autoGroupTsp]编号为" + addnotesPlanNo + "的加钞计划不存在");
			return retMap;
		}

		// 删除加钞计划下的分组信息
		//更新加钞计划明细表
		addnotesPlanDetailInnerResource.deleteGroup(addnotesPlanNo);
		//删除计划分组
		addnotesPlanGroupInnerResource.deleteByAddnotesPlanNo(addnotesPlanNo);

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addnotesPlanNo", addnotesPlanNo);
		if ("2".equals(groupType)) {//线路分组
			List<Map<String, Object>> lineMsgList = groupDevRegularly(addnotesPlanNo);
			DTO dto = updateGroupInfo(lineMsgList, addnotesPlanNo);
			if (!RetCodeEnum.SUCCEED.getCode().equals(dto.getRetCode())) {
				return retMap;
			}
		} else { //自动分组
			List<Map<String, Object>> lineMsgList = groupDevAuto(addnotesPlanNo, tactic, groupNum);
			DTO dto = updateGroupInfo(lineMsgList, addnotesPlanNo);
			if (!RetCodeEnum.SUCCEED.getCode().equals(dto.getRetCode())) {
				return retMap;
			}

		}
		// change status of addnotesPlan
		Map<String, Object> map2 = new HashMap<>();
		map2.put("addnotesPlanNo", addnotesPlanNo);
		map2.put("clrCenterNo", addnotesPlanDTO.getClrCenterNo());
		map2.put("clrCenterName", addnotesPlanDTO.getClrCenterName());
		map2.put("planAddnotesDate", addnotesPlanDTO.getPlanAddnotesDate());
		map2.put("planStartTime", earliestStartTime);
		map2.put("lastestEndTime", lastestArrivalTime);
		map2.put("planDevCount", addnotesPlanDTO.getPlanDevCount());
		map2.put("planAddnotesAmt", addnotesPlanDTO.getPlanAddnotesAmt());
		map2.put("planGenMode", addnotesPlanDTO.getPlanGenMode());
		map2.put("planGenOpNo", addnotesPlanDTO.getPlanGenOpNo());
		map2.put("planGenOpName", addnotesPlanDTO.getPlanGenOpName());
		map2.put("planGenDate", addnotesPlanDTO.getPlanGenDate());
		map2.put("planGenTime", addnotesPlanDTO.getPlanGenTime());
		map2.put("status", StatusEnum.AddnotesPlanStatus.GROUPED.getId());
		map2.put("submitOpNo", addnotesPlanDTO.getSubmitOpNo());
		map2.put("submitDate", addnotesPlanDTO.getSubmitDate());
		map2.put("submitTime", addnotesPlanDTO.getSubmitTime());
		map2.put("lineMode", addnotesPlanDTO.getLineMode());
		map2.put("lineNo", addnotesPlanDTO.getLineNo());
		map2.put("lineList", addnotesPlanDTO.getLineList());
		map2.put("modOpNo", addnotesPlanDTO.getModOpNo());
		map2.put("modDate", addnotesPlanDTO.getModDate());
		map2.put("modTime", addnotesPlanDTO.getModTime());
		map2.put("note", addnotesPlanDTO.getNote());
		map2.put("auditOpNo", addnotesPlanDTO.getAuditOpNo());
		map2.put("auditOpName", addnotesPlanDTO.getAuditOpName());
		map2.put("auditDate", addnotesPlanDTO.getAuditDate());
		map2.put("auditTime", addnotesPlanDTO.getAuditTime());
		map2.put("refuseSuggestion", addnotesPlanDTO.getRefuseSuggestion());
		map2.put("isUrgency", addnotesPlanDTO.getIsUrgency());
		addnotesPlanInnerResource.updateByPrimaryKeyByMap(map2);

		//规划线路
		if (tspFlag) {
			// 加钞计划下的所有分组编号
			List<String> groupNos = addnotesPlanGroupInnerResource.getGroupNoListByNo(addnotesPlanNo);
			List<Object> sortGroupRetList = new ArrayList<Object>();
			Integer sortGroupRetCode;
			for (String groupNo : groupNos) {
				sortGroupRetList = this.sortPlanGroup(addnotesPlanNo, groupNo, tactic, dataType);
				sortGroupRetCode = (Integer) sortGroupRetList.get(0);
				if (sortGroupRetCode != 1) {
					log.error("[autoGroupTsp]加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组规划路线失败");
				}
			}
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "自动分组成功!");

		return retMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> modGroupTsp(String jsonParam) {
		log.info("------------[modGroupTsp]GroupService-------------");

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try{
		JSONObject params = JSONUtil.parseJSONObject(jsonParam);
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
		String groupNo = StringUtil.parseString(params.get("groupNo"));
		String jsonNetPointsGroup = StringUtil.parseString(params.get("netPointsGroup"));
		Integer clrTimeInterval = 0;

		HashMap<String, Integer> netpointNoSortNoMap = new HashMap<String, Integer>();
		//组内网点编号、顺序号
		if (!"".equals(jsonNetPointsGroup)) {

			JSONArray jsonArray = JSONUtil.parseJSONArray(jsonNetPointsGroup);
			for (int j = 0; j < jsonArray.size(); j++) {
				JSONObject jsonObject = jsonArray.getJSONObject(j);
				String orgNo = StringUtil.parseString(jsonObject.get("orgNo"));
				int sortNo = StringUtil.ch2Int(jsonObject.getString("sortNo"));
				netpointNoSortNoMap.put(orgNo, sortNo);
			}
		}

		//开始分组
		//查询加钞计划
		AddnotesPlanDTO addnotesPlanDTO = addnotesPlanInnerResource.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlanDTO == null) {
			retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
			log.error("[qryGroupsTsp]编号为" + addnotesPlanNo + "的加钞计划不存在");
			return retMap;
		}


		// 删除加钞计划某组的排序信息
		Map<String, Object> map3 = new HashMap<>();
		map3.put("addnotesPlanNo", addnotesPlanNo);
		map3.put("groupNo", groupNo);
		map3.put("clrTimeInterval", clrTimeInterval);
		AddnotesPlanGroupDTO addnotesPlanGroupDTO = addnotesPlanGroupInnerResource.selectByPrimaryKeyMap(map3);

		if (addnotesPlanGroupDTO == null) {
			retMap.put("retMsg", "加钞计划[" + addnotesPlanNo + "]下的某组[" + groupNo + "]不存在");
			log.error("[clearGroup] " + "加钞计划[" + addnotesPlanNo + "]下的某组[" + groupNo + "]不存在");
			return retMap;
		}

		Map<String, Object> map4 = new HashMap<>();
		map4.put("planDevCnt", addnotesPlanGroupDTO.getPlanDevCnt());
		map4.put("planNetpntCnt", addnotesPlanGroupDTO.getPlanNetpntCnt());
		map4.put("planDistance", 0);
		map4.put("planTimeCost", 0);
		map4.put("addnotesPlanNo", addnotesPlanGroupDTO.getAddnotesPlanNo());
		map4.put("groupNo", addnotesPlanGroupDTO.getGroupNo());
		map4.put("clrTimeInterval", addnotesPlanGroupDTO.getClrTimeInterval());
		addnotesPlanGroupInnerResource.updateByPrimaryKeyMap(map4);


		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("addnotesPlanNo", addnotesPlanNo);
		paraMap.put("lineNo", groupNo);
		addnotesPlanDetailInnerResource.updateSortByGroupNo(paraMap);

		// loop to set new sortNo
		Iterator<Map.Entry<String, Integer>> iterator = netpointNoSortNoMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			paraMap.put("pointNo", entry.getKey());
			paraMap.put("sortNo", entry.getValue());
			addnotesPlanDetailInnerResource.updateSortNoByNetPoint(paraMap);
		}

		// 判断分组下是否所有的网点都已经排序，并按此排序组织netpointNosSorted
		List<Map<String, Object>> nsList = addnotesPlanDetailInnerResource.getNetPointsOrderBySortNo(paraMap);

		boolean tspFlag = true;
		List<String> netpointNosSorted = new ArrayList<String>();// 有序的网点编号列表;
		Map<String, Object> hMap;
		for (int i = 0; i < nsList.size(); i++) {
			hMap = nsList.get(i);
			Integer sortNo = StringUtil.objectToInt(hMap.get("sortNo"));
			if (sortNo == -1) {
				tspFlag = false;
				break;
			}
			netpointNosSorted.add(StringUtil.parseString(hMap.get("orgNo")));
		}

		int tactic = StatusEnum.MatrixTactic.TIME_SHORTEST.getValue();//默认采用1（时间最短）
		if (tspFlag) {
			// 清机中心编号
			String clrCenterNo = addnotesPlanDTO.getClrCenterNo();
			// 获取代价
			HashMap<String, Integer> costMap = this.getRouteCost(clrCenterNo, netpointNosSorted, tactic);
			if (costMap == null) {
				retMap.put("retMsg", "获取最优路线代价（路程+耗时）异常!");
				return retMap;
			}
			int distance = costMap.get("distance");
			int time = costMap.get("time");
			// 加钞计划分组
			AddnotesPlanGroupDTO addnotesPlanGroupN = addnotesPlanGroupInnerResource.selectByPrimaryKeyMap(map3);
			if (addnotesPlanGroupN == null) {
				log.error("[modGroupTsp] 加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组不存在");
				retMap.put("retMsg", "加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组不存在");
				return retMap;
			}
			Map<String, Object> map5 = new HashMap<>();
			map5.put("planDevCnt", addnotesPlanGroupN.getPlanDevCnt());
			map5.put("planNetpntCnt", addnotesPlanGroupN.getPlanNetpntCnt());
			map5.put("planDistance", distance);
			map5.put("planTimeCost", time);
			map5.put("addnotesPlanNo", addnotesPlanGroupN.getAddnotesPlanNo());
			map5.put("groupNo", addnotesPlanGroupN.getGroupNo());
			map5.put("clrTimeInterval", addnotesPlanGroupN.getClrTimeInterval());
			addnotesPlanGroupInnerResource.updateByPrimaryKeyMap(map5);
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "修改分组线路信息成功!");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "修改分组线路信息异常!");
//			log.error("modGroupTsp Fail: ", e);
//		}
		return retMap;
	}

	@Override
	/**
	 * 规划加钞计划分组的线路
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @param groupNo 分组编号
	 * @param tactic 矩阵策列
	 * @param dataType 关联类型
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public List<Object> sortPlanGroup(String addnotesPlanNo, String groupNo, int tactic, int dataType) {
		List<Object> retList = new ArrayList<Object>();
		retList.add(0);
		// 清机中心编号
		AddnotesPlanDTO addnotesPlanDTO = addnotesPlanInnerResource.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlanDTO == null || !StatusEnum.AddnotesPlanStatus.canSort(addnotesPlanDTO.getStatus())) {
			log.error("[sortPlanGroup] 加钞计划[" + addnotesPlanNo + "]不存在或状态异常");
			retList.add("加钞计划[" + addnotesPlanNo + "]不存在或状态异常");
			return retList;
		}
		String clrCenterNo = addnotesPlanDTO.getClrCenterNo();

		// 删除加钞计划某组的排序信息
		//查询分组信息并更新
		Map<String, Object> map1 = new HashMap<>();
		map1.put("addnotesPlanNo", addnotesPlanNo);
		map1.put("groupNo", groupNo);
		map1.put("clrTimeInterval", 0);
		AddnotesPlanGroupDTO addnotesPlanGroup = addnotesPlanGroupInnerResource.selectByPrimaryKeyMap(map1);
		if (addnotesPlanGroup == null) {
			log.error("加钞计划[" + addnotesPlanNo + "]下第[" + groupNo + "]组不存在");
			retList.add("加钞计划[" + addnotesPlanNo + "]下第[" + groupNo + "]组不存在");
			return retList;
		}
		Map<String, Object> map2 = new HashMap<>();
		map2.put("planDevCnt", addnotesPlanGroup.getPlanDevCnt());
		map2.put("planNetpntCnt", addnotesPlanGroup.getPlanNetpntCnt());
		map2.put("planDistance", 0);
		map2.put("planTimeCost", 0);
		map2.put("addnotesPlanNo", addnotesPlanGroup.getAddnotesPlanNo());
		map2.put("groupNo", addnotesPlanGroup.getGroupNo());
		map2.put("clrTimeInterval", addnotesPlanGroup.getClrTimeInterval());
		addnotesPlanGroupInnerResource.updateByPrimaryKeyMap(map2);

		// 更新加钞计划明细
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addnotesPlanNo", addnotesPlanNo);
		paramMap.put("lineNo", groupNo);
		addnotesPlanDetailInnerResource.updateSortNoNull(paramMap);


		// 网点编号
//			List<String> pointNos = addnotesPlanDetailMapper.getNetpointList(paramMap);
//			if (pointNos == null) {
//				retList.add("获取加钞计划[" + addnotesPlanNo + "]第[" + groupNo + "]组的网点编号列表失败!");
//				return retList;
//			}
		List<String> pointNos = addnotesPlanDetailInnerResource.getDevPointList(paramMap);
		if (pointNos == null || pointNos.size() == 0) {
			retList.add("获取加钞计划[" + addnotesPlanNo + "]第[" + groupNo + "]组的设备编号列表失败!");
			return retList;
		}

		// 代价矩阵：索引=0:清机中心;其它索引-1:网点在netpointNos中的索引
		Matrix matrixOfCosts = this.formMatrixOfCosts(clrCenterNo, pointNos, tactic, dataType);
		if (matrixOfCosts == null) {
			retList.add("组织代价矩阵失败!");
			return retList;
		}

		//TODO rmi中调用的方法不存在
		log.info("------------[sortPlanGroup]zjml invoke -------------");
		Map<String, Object> map = new HashMap<>();
		map.put("transData", matrixOfCosts.getValue());
		String zjmlIP = CfgProperty.getProperty("zjmlIP");
		String zjmlPort = CfgProperty.getProperty("zjmlPort");
		String jsonString = restTemplate.postForObject("http://" + zjmlIP + ":" + zjmlPort + "/coffers/addNotePath/", JSONUtil.createJsonString(map), String.class);
		JSONObject jsonObject = JSONUtil.parseJSONObject(jsonString);
		//最优路线编号
		List<Integer> acoBestRoute = (List<Integer>) jsonObject.get("pathbest");
		//最优路线距离
		Integer lengthbest = StringUtil.objectToInt(jsonObject.get("lengthbest"));

		// calculate distance and time
		HashMap<String, Integer> costMap = this.getRouteCost(clrCenterNo, pointNos, tactic, acoBestRoute);
		if (costMap == null) {
			retList.add("获取路线代价失败!");
			return retList;
		}
		int distance = costMap.get("distance");
		int time = costMap.get("time");

		// note result of sort
		// -- 加钞计划明细-排序字段
		String netpointNoSort;
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("addnotesPlanNo", addnotesPlanNo);
		paraMap.put("lineNo", groupNo);
		for (int sortNo = 1; sortNo < acoBestRoute.size() - 1; sortNo++) {
			netpointNoSort = pointNos.get(acoBestRoute.get(sortNo) - 1);
			paraMap.put("sortNo", sortNo);
			paraMap.put("pointNo", netpointNoSort);
			addnotesPlanDetailInnerResource.updateSortNoByDevNo(paraMap);
		}

		// -- 加钞计划分组
		AddnotesPlanGroupDTO addnotesPlanGroupN = addnotesPlanGroupInnerResource.selectByPrimaryKeyMap(map1);
		if (addnotesPlanGroupN == null) {
			log.error("[sortPlanGroup] 加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组不存在");
			retList.add("加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组不存在");
			return retList;
		}
		Map<String, Object> map3 = new HashMap<>();
		map3.put("planDevCnt", addnotesPlanGroupN.getPlanDevCnt());
		map3.put("planNetpntCnt", addnotesPlanGroupN.getPlanNetpntCnt());
		map3.put("planDistance", distance);
		map3.put("planTimeCost", time);
		map3.put("addnotesPlanNo", addnotesPlanGroupN.getAddnotesPlanNo());
		map3.put("groupNo", addnotesPlanGroupN.getGroupNo());
		map3.put("clrTimeInterval", addnotesPlanGroupN.getClrTimeInterval());
		addnotesPlanGroupInnerResource.updateByPrimaryKeyMap(map3);

		// succeed to return
		retList.clear();
		retList.add(1);
		retList.add(distance);
		retList.add(time);
		return retList;
	}

	/**
	 * 获取路线代价（路程+耗时）
	 *
	 * @param clrCenterNo       清机中心编号：起点&&终点
	 * @param netpointNosSorted 有序的网点编号：按路线先后访问的顺序排列
	 * @param tactic            策略
	 * @return
	 */
	private HashMap<String, Integer> getRouteCost(String clrCenterNo, List<String> netpointNosSorted, int tactic) {
		try {
			// 基本代价
			HashMap<String, Integer> baseCostMap = this.getRouteBaseCost(clrCenterNo, netpointNosSorted, tactic);
			if (baseCostMap == null) {
				return null;
			}
			int baseDistance = baseCostMap.get("distance");
			int baseTime = baseCostMap.get("time");

			// 权值
			double weighting = -1;

//			SysParam sysParam = sysParamMapper.selectByParamName("routeTimeWeighting"); TODO 熔断
			SysParamDTO sysParam = sysParamResource.qrySysParamByName("routeTimeWeighting");

			if (sysParam != null) {
				weighting = StringUtil.ch2Double(sysParam.getParamValue());
			}
			if (weighting < 0) {
				log.warn("[getRouteCost] 耗时权值异常,默认取1.2");
				weighting = 1.2;
			}

			// 成功返回
			HashMap<String, Integer> retMap = new HashMap<String, Integer>();
			retMap.put("distance", baseDistance);
			retMap.put("time", (int) Math.ceil(baseTime * weighting));
			return retMap;
		} catch (Exception e) {
			log.error("[getRouteCost] 获取最优路线代价（路程+耗时）异常: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取路线代价（路程+耗时）
	 *
	 * @param clrCenterNo 金库中心编号;在bestRoute中的值为0
	 *                    网点编号列表;每个网点在bestRoute中的值为其在网点编号列表中的索引加1
	 * @param tactic      策略
	 * @param route       路径：值为0:金库中心;其它值-1:网点在netpointNos中的索引
	 * @return {distance:distance,time:time}
	 */
	private HashMap<String, Integer> getRouteCost(String clrCenterNo, List<String> netpointNosSorted, int tactic, List<Integer> route) {
		try {
			// 基本代价
			HashMap<String, Integer> baseCostMap = this.getRouteBaseCost(clrCenterNo, netpointNosSorted, tactic);
			if (baseCostMap == null) {
				return null;
			}
			int baseDistance = baseCostMap.get("distance");
			int baseTime = baseCostMap.get("time");

			// 权值
			double weighting = -1;

//			String paramValue = sysParamMapper.selectByParamName("routeTimeWeighting").getParamValue(); TODO 熔断控制
			String paramValue = sysParamResource.qrySysParamByName("routeTimeWeighting").getParamValue();

			if (paramValue != null) {
				weighting = Double.parseDouble(paramValue);
			}
			if (weighting < 0) {
				log.warn("[getRouteCost] 耗时权值异常,默认取1.2");
				weighting = 1.2;
			}
			// 成功返回
			HashMap<String, Integer> retMap = new HashMap<String, Integer>();
			retMap.put("distance", baseDistance);
			retMap.put("time", (int) Math.ceil(baseTime * weighting));
			return retMap;
		} catch (Exception e) {
			// 获取最优路线代价（路程+耗时）出错");
			log.error("[getRouteCost] 获取最优路线代价（路程+耗时）异常: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 组织代价矩阵
	 *
	 * @param clrCenterNo 清机中心编号;在代价矩阵中的索引为0
	 * @param netpointNos 网点编号列表;每个网点在代价矩阵中的索引为其在编号列表中的索引加1
	 * @param tactic      策略
	 * @return 代价矩阵：索引=0:清机中心;其它索引-1:网点在netpointNos中的索引
	 */
	private Matrix formMatrixOfCosts(String clrCenterNo, List<String> netpointNos, int tactic, int dataType) {
		try {
			// 网点数
			int netpointNum = netpointNos.size();

			// 网点到网点的代价列表
			HashMap<String, Object> paraMapN2N = new HashMap<String, Object>();
			paraMapN2N.put("startPointNos", netpointNos);
			paraMapN2N.put("endPointNos", netpointNos);
			paraMapN2N.put("type", StatusEnum.MatrixType.N_TO_N.getValue());
			paraMapN2N.put("tactic", tactic);
			paraMapN2N.put("dataType", dataType);
			List<NetPointMatrix> costsN2N = lineNetPointMatrixMapper.selectNetpointMatrix(paraMapN2N);

			if (costsN2N.size() != (netpointNum - 1) * netpointNum) {
				log.error("[formMatrixOfCosts] 网点未全部关联");
				return null;
			}

			// 清机中心对应的银行机构编号列表
//			List<String> clrCenterOrgNoList = clrCenterTableMapper.getClrCenterOrgNo(clrCenterNo);
			List<String> clrCenterOrgNoList = clrCenterInnerResource.getClrCenterOrgNo(clrCenterNo);
			if (clrCenterOrgNoList == null || clrCenterOrgNoList.size() == 0) {
				log.error("清机中心对应的银行机构不存在");
				return null;
			}
			String clrCenterOrgNo = clrCenterOrgNoList.get(0);

			// 清机中心到网点的代价列表
			HashMap<String, Object> paraMapC2N = new HashMap<String, Object>();
			paraMapC2N.put("startPointNos", clrCenterOrgNoList);
			paraMapC2N.put("endPointNos", netpointNos);
			paraMapC2N.put("type", StatusEnum.MatrixType.C_TO_N.getValue());
			paraMapC2N.put("tactic", tactic);
			paraMapC2N.put("dataType", dataType);
			List<NetPointMatrix> costsC2N = lineNetPointMatrixMapper.selectNetpointMatrix(paraMapC2N);
			if (costsC2N.size() != netpointNum) {
				log.error("[formMatrixOfCosts] 清机中心未关联全部网点");
				return null;
			}

			// 网点到清机中心的代价列表
			HashMap<String, Object> paraMapN2C = new HashMap<String, Object>();
			paraMapN2C.put("startPointNos", netpointNos);
			paraMapN2C.put("endPointNos", clrCenterOrgNoList);
			paraMapN2C.put("type", StatusEnum.MatrixType.N_TO_C.getValue());
			paraMapN2C.put("tactic", tactic);
			paraMapN2C.put("dataType", dataType);
			List<NetPointMatrix> costsN2C = lineNetPointMatrixMapper.selectNetpointMatrix(paraMapN2C);
			if (costsN2C.size() != netpointNum) {
				log.error("[formMatrixOfCosts] 存在网点未关联清机中心");
				return null;
			}

			// 点到点的代价矩阵
			Matrix matrixOfCosts = new Matrix(netpointNum + 1, netpointNum + 1);

			String iPointNo, jPointNo;
			List<NetPointMatrix> costs;

			NetPointMatrixKey kCostObjectId;
			int i2jCostObjectIndex;
			NetPointMatrix i2jCostObject;
			double i2jCost;

			for (int i = 0; i < netpointNum + 1; i++) {
				for (int j = 0; j < netpointNum + 1; j++) {

					// one → itself
					if (i == j) {
						matrixOfCosts.setValueItem(i, j, 0);
						continue;
					}

					// iPointNo && jPointNo && costs

					if (i == 0) {
						// 清机中心 → 网点
						iPointNo = clrCenterOrgNo;
						jPointNo = netpointNos.get(j - 1);
						costs = costsC2N;
					} else if (j == 0) {
						// 网点 → 清机中心
						iPointNo = netpointNos.get(i - 1);
						jPointNo = clrCenterOrgNo;
						costs = costsN2C;
					} else {
						// 网点 → 网点
						iPointNo = netpointNos.get(i - 1);
						jPointNo = netpointNos.get(j - 1);
						costs = costsN2N;
					}

					// i2jCost
					i2jCostObjectIndex = -1;
					for (int k = 0; k < costs.size(); k++) {
						kCostObjectId = costs.get(k);
						if (kCostObjectId.getStartPointNo().equals(iPointNo) && kCostObjectId.getEndPointNo().equals(jPointNo)) {
							i2jCostObjectIndex = k;
							break;
						}
					}
					if (i2jCostObjectIndex < 0) {
						log.error("[formMatrixOfCosts] 清机中心与网点未全部关联");
						return null;
					}
					i2jCostObject = costs.remove(i2jCostObjectIndex);
					i2jCost = this.getCost(i2jCostObject);

					// set value of matrixOfCosts at (i,j)
					matrixOfCosts.setValueItem(i, j, i2jCost);
				}
			}

			// return
			return matrixOfCosts;
		} catch (Exception e) {
			log.error("[formMatrixOfCosts] 组织代价矩阵异常: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取路线基本(不考虑金库时间)代价（路程+耗时）
	 *
	 * @param clrCenterNo       金库中心编号：起点&&终点
	 * @param netpointNosSorted 有序的网点编号：按路线先后访问的顺序排列
	 * @param tactic            策略
	 * @return {distance:distance,time:time}
	 */
	private HashMap<String, Integer> getRouteBaseCost(String clrCenterNo, List<String> netpointNosSorted, int tactic) {
		try {
			// 总代价
			int totalDistance = 0, totalTime = 0;

			String startPointNo, endPointNo;
			int startPointType, endPointType;
			NetPointMatrix costObject;
			Integer distance, time;

			// 金库中心对应的银行机构编号列表
//			List<String> clrCenterOrgNoList = clrCenterTableMapper.getClrCenterOrgNo(clrCenterNo);
			List<String> clrCenterOrgNoList = clrCenterInnerResource.getClrCenterOrgNo(clrCenterNo);
			if (clrCenterOrgNoList == null || clrCenterOrgNoList.size() == 0) {
				log.error("金库中心对应的银行机构不存在");
				return null;
			}
			String clrCenterOrgNo = clrCenterOrgNoList.get(0);

			// 金库中心→第一个网点
			if (netpointNosSorted.size() > 0) {
				startPointNo = clrCenterOrgNo;
				startPointType = StatusEnum.MatrixPointType.CENTER.getValue();// MatrixPointType.CENTER.getValue();
				endPointNo = netpointNosSorted.get(0);
				endPointType = StatusEnum.MatrixPointType.NETPOINT.getValue();// MatrixPointType.NETPOINT.getValue();
				costObject = this.getNetPointMatrix(startPointNo, startPointType, endPointNo, endPointType, tactic);
				distance = null;
				time = null;
				if (costObject != null) {
					distance = costObject.getDistance();
					time = costObject.getTimeCost();
				}
				if (distance == null || distance < 0 || time == null || time < 0) {
					log.error("[getRouteBaseCost] 金库中心[" + startPointNo + "]到网点[" + endPointNo + "]未关联");
				}
				// plus
				totalDistance += distance;
				totalTime += time;
			}

			// 网点到网点
			for (int i = 0; i < netpointNosSorted.size() - 1; i++) {
				startPointNo = netpointNosSorted.get(i);
				startPointType = StatusEnum.MatrixPointType.NETPOINT.getValue();
				endPointNo = netpointNosSorted.get(i + 1);
				endPointType = StatusEnum.MatrixPointType.NETPOINT.getValue();
				costObject = this.getNetPointMatrix(startPointNo, startPointType, endPointNo, endPointType, tactic);
				distance = null;
				time = null;
				if (costObject != null) {
					distance = costObject.getDistance();
					time = costObject.getTimeCost();
				}
				if (distance == null || distance < 0 || time == null || time < 0) {
					log.error("[getRouteBaseCost] 网点[" + startPointNo + "]到网点[" + endPointNo + "]未关联");
				}
				// plus
				totalDistance += distance;
				totalTime += time;
			}

			// 最后一个网点→金库中心
			if (netpointNosSorted.size() > 0) {
				startPointNo = netpointNosSorted.get(netpointNosSorted.size() - 1);
				startPointType = StatusEnum.MatrixPointType.NETPOINT.getValue();
				endPointNo = clrCenterOrgNo;
				endPointType = StatusEnum.MatrixPointType.CENTER.getValue();
				costObject = this.getNetPointMatrix(startPointNo, startPointType, endPointNo, endPointType, tactic);
				distance = null;
				time = null;
				if (costObject != null) {
					distance = costObject.getDistance();
					time = costObject.getTimeCost();
				}
				if (distance == null || distance < 0 || time == null || time < 0) {
					log.error("[getRouteBaseCost] 网点[" + startPointNo + "]到金库中心[" + endPointNo + "]未关联");
				}
				// plus
				totalDistance += distance;
				totalTime += time;
			}

			// 成功返回
			HashMap<String, Integer> retMap = new HashMap<String, Integer>();
			retMap.put("distance", totalDistance);
			retMap.put("time", totalTime);
			return retMap;
		} catch (Exception e) {
			log.error("[getRouteBaseCost] 获取路线基本(不考虑金库时间)代价（路程+耗时）异常: " + e.getMessage());
			return null;
		}
	}

	public NetPointMatrix getNetPointMatrix(String startPointNo, int startPointType, String endPointNo, int endPointType, int tactic) {
		// check and initialize type
		int type;
		if (startPointType == StatusEnum.MatrixPointType.NETPOINT.getValue() && endPointType == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
			type = StatusEnum.MatrixType.N_TO_N.getValue();
		} else if (startPointType == StatusEnum.MatrixPointType.CENTER.getValue() && endPointType == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
			type = StatusEnum.MatrixType.C_TO_N.getValue();
		} else if (startPointType == StatusEnum.MatrixPointType.NETPOINT.getValue() && endPointType == StatusEnum.MatrixPointType.CENTER.getValue()) {
			type = StatusEnum.MatrixType.N_TO_C.getValue();
		} else {
			log.error("[getObject] startPointType[" + startPointType + "] or endPointType[" + endPointType + "] error");
			return null;
		}
		NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
		netPointMatrixKey.setStartPointNo(startPointNo);
		netPointMatrixKey.setEndPointNo(endPointNo);
		netPointMatrixKey.setType(type);
		netPointMatrixKey.setTactic(tactic);
		return lineNetPointMatrixMapper.selectByPrimaryKey(netPointMatrixKey);
	}

	//TODO 新框架作为服务使用

	/**
	 * 固定线路分组:根据设备详情表的所属线路钞进行设备分组
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 设备分组信息 分组编号 设备编号
	 */
	public List<Map<String, Object>> groupDevRegularly(String addnotesPlanNo) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		//查询加钞计划的设备及设备所属线路和所属机构
		Map<String, Object> paramMap = new HashMap<>(1);
		paramMap.put("addnotesPlanNo", addnotesPlanNo);
		List<Map<String, Object>> lineMsgList = addnotesPlanDetailInnerResource.getLineMsgList(paramMap);
		return lineMsgList;
	}

	//TODO 新框架作为服务使用

	/**
	 * 更新设备分组
	 * 1。更新加钞计划详情的设备加钞线路即分组
	 * 2。更新加钞计划分组表中的设备数量和设备对应的网点数量
	 *
	 * @param lineMsgList    线路编号 设备编号
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 更新结果
	 */
	public DTO updateGroupInfo(List<Map<String, Object>> lineMsgList, String addnotesPlanNo) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		for (Map<String, Object> lineMsg : lineMsgList) {
			//更新加钞计划详情设备加钞线路为所属线路
			String lineNo = StringUtil.parseString(lineMsg.get("lineNo"));
			String devNo = StringUtil.parseString(lineMsg.get("devNo"));
			Map<String, Object> map1 = new HashMap<>();
			map1.put("addnotesPlanNo", addnotesPlanNo);
			map1.put("devNo", devNo);
			map1.put("lineNo", lineNo);
			addnotesPlanDetailInnerResource.updateByMapSelective(map1);
		}

//		List<AddnotesPlanDetail>  as  = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);
		List<Map<String, Object>> groupNoAndDevNumList = addnotesPlanDetailInnerResource.getDevCountEachGroup(addnotesPlanNo);
		for (Map<String, Object> groupNoAndDevNum : groupNoAndDevNumList) {
			String lineNo = StringUtil.parseString(groupNoAndDevNum.get("LINENO"));
			//线路上设备数
			int devCnt = Integer.parseInt(String.valueOf(groupNoAndDevNum.get("DEVNUM")));
			//线路上网点数
			Map<String, Object> map2 = new HashMap<>();
			map2.put("addnotesPlanNo", addnotesPlanNo);
			map2.put("lineNo", lineNo);
			int netpointNosSize = addnotesPlanDetailInnerResource.getNetCountInGroupByMap(map2);


			//0 : 该组不包含"顺延法"筛选出的设备
			Map<String, Object> map3 = new HashMap<>();
			map3.put("addnotesPlanNo", addnotesPlanNo);
			map3.put("groupNo", lineNo);
			map3.put("clrTimeInterval", StatusEnum.DevClrTimeInterval.NOPLAN.getType());
			map3.put("planDevCnt", devCnt);
			map3.put("planNetpntCnt", netpointNosSize);
			addnotesPlanGroupInnerResource.insertSelectiveByMap(map3);
		}
		return dto;
	}

	//TODO 新框架作为服务使用

	/**
	 * 设备自动分组
	 * 1.查询待分组设备
	 * 2.组成矩阵发送给zjml
	 * 3.解析zjml返回数据
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @param tactic         分组策略
	 * @param groupNum       分组数
	 * @return 分组结果 分组编号 设备编号
	 */
	public List<Map<String, Object>> groupDevAuto(String addnotesPlanNo, int tactic, int groupNum) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("addnotesPlanNo", addnotesPlanNo);
		int dataType = 1;
		//查询待分组设备
		List<String> pointNosList = addnotesPlanDetailInnerResource.getDevPointList(paramMap);
		//组成矩阵
		Matrix matrixOfCostsN2N = this.formMatrixOfCostsN2N(pointNosList, tactic, dataType);
		if (matrixOfCostsN2N == null) {
			return null;
		}

		//发送给zjml
		log.info("------------[autoGroupTsp]zjml invoke -------------");
		Map<Integer, List<Integer>> groups = new HashMap<>();

		Map<String, Object> map = new HashMap<>();
		map.put("transData", matrixOfCostsN2N.getValue());
		map.put("groupNum", groupNum);
		String zjmlIP = CfgProperty.getProperty("zjmlIP");
		String zjmlPort = CfgProperty.getProperty("zjmlPort");
		String jsonString = restTemplate.postForObject("http://" + zjmlIP + ":" + zjmlPort + "/coffers/devGroup/", JSONUtil.createJsonString(map), String.class);

		//解析zjml返回数据
		JSONObject jsonObject = JSONUtil.parseJSONObject(jsonString);
		List<Integer> devGroupList = (List<Integer>) jsonObject.get("devGroup");
		List<Map<String, Object>> lineMsgList = new ArrayList<>();
		if (devGroupList != null) {
			for (int devNum = 0; devNum < devGroupList.size(); devNum++) {
				Map<String, Object> devGroupInfo = new HashMap<>();
				devGroupInfo.put("lineNo", devGroupList.get(devNum));
				devGroupInfo.put("devNo", pointNosList.get(devNum));
				lineMsgList.add(devGroupInfo);
			}
		}
		return lineMsgList;
	}

	/**
	 * 组织网点到网点的代价矩阵
	 *
	 * @param netpointNoList 网点编号列表
	 * @param tactic         策略
	 * @return
	 */
	private Matrix formMatrixOfCostsN2N(List<String> netpointNoList, int tactic, int dataType) {
		try {
			// 网点数
			int netpointNum = netpointNoList.size();

			// 网点到网点的代价列表
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startPointNos", netpointNoList);
			paramMap.put("endPointNos", netpointNoList);
			paramMap.put("type", StatusEnum.MatrixType.N_TO_N.getValue());
			paramMap.put("tactic", tactic);
			paramMap.put("dataType", dataType);
			List<NetPointMatrix> costsN2N = lineNetPointMatrixMapper.selectNetpointMatrix(paramMap);

			if (costsN2N.size() != (netpointNum - 1) * netpointNum) {
				log.error("[formMatrixOfCostsN2N] 网点未全部关联");
				return null;
			}

			// 网点到网点的代价矩阵
			Matrix matrixOfCostsN2N = new Matrix(netpointNum, netpointNum);

			String iNetpointNo, jNetpointNo;

			NetPointMatrixKey kCostObjectId;
			int i2jCostObjectIndex;
			NetPointMatrix i2jCostObject;
			double i2jCost;

			for (int i = 0; i < netpointNum; i++) {
				for (int j = 0; j < netpointNum; j++) {

					// one → itself
					if (i == j) {
						matrixOfCostsN2N.setValueItem(i, j, 0);
						continue;
					}

					// iNetpointNo && jNetpointNo
					iNetpointNo = netpointNoList.get(i);
					jNetpointNo = netpointNoList.get(j);

					// i→j: cost
					i2jCostObjectIndex = -1;
					for (int k = 0; k < costsN2N.size(); k++) {
						kCostObjectId = costsN2N.get(k);
						if (kCostObjectId.getStartPointNo().equals(iNetpointNo) && kCostObjectId.getEndPointNo().equals(jNetpointNo)) {
							i2jCostObjectIndex = k;
							break;
						}
					}
					if (i2jCostObjectIndex < 0) {
						log.error("[formMatrixOfCostsN2N] 网点未全部关联");
						return null;
					}
					i2jCostObject = costsN2N.remove(i2jCostObjectIndex);
					i2jCost = this.getCost(i2jCostObject);

					// set value of matrixOfCostsN2N at (i,j)
					matrixOfCostsN2N.setValueItem(i, j, i2jCost);
				}
			}
			// return
			return matrixOfCostsN2N;
		} catch (Exception e) {
			log.error("[formMatrixOfCostsN2N] " + "组织网点到网点的代价矩阵异常: " + e.getMessage());
			return null;
		}
	}

	public double getCost(NetPointMatrix object) {
		int tactic = object.getTactic();
		if (tactic == StatusEnum.MatrixTactic.TIME_SHORTEST.getValue()) {
			// 时间最短：取耗时
			return object.getTimeCost();
		} else if (tactic == StatusEnum.MatrixTactic.DISTANCE_SHORTEST.getValue()) {
			// 路程最短：取路程
			return object.getDistance();
		} else {
			// 不走高速：取耗时
			return object.getTimeCost();
		}
	}

	@Override
	public Map<String, Object> qryAddnotesLineByPage(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
		retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String lineName = StringUtil.parseString(params.get("lineName"));
			Integer lineType = StringUtil.objectToInt(params.get("lineType"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("lineName", lineName);
			paramMap.put("lineType", lineType);

//			int totalRow = lineAddnoteLineMapper.qryTotalRowPlan(paramMap);
			int totalRow = lineTableMapper.qryTotalRowPlan(paramMap);
			int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;

//			List<LineTableDO> lineTableDOS = lineAddnoteLineMapper.qryAddnotesLine(paramMap);
			List<LineTableDO> lineTableDOS = lineTableMapper.qryAddnotesLine(paramMap);
			List<LineTableDTO> retList = AddnotesLineConverter.INSTANCE.domain2dto(lineTableDOS);

			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询加钞线路成功");
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞线路异常!");
			log.error("qryAddnotesLine Fail: ", e);
			return retMap;
		}
	}

	@Override
	public Map<String, Object> delAddnotesLine(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String lineNo = StringUtil.parseString(params.get("lineNo"));

//			LineTableDO addnoteLine = lineAddnoteLineMapper.selectByPrimaryKey(lineNo);
			LineTableDO addnoteLine = lineTableMapper.selectByPrimaryKey(lineNo);
			if (addnoteLine == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "加钞路线删除异常!对象已不存在!");
				return retMap;
			}

//			lineAddnoteLineMapper.deleteByPrimaryKey(lineNo);
			lineTableMapper.deleteByPrimaryKey(lineNo);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "删除加钞线路成功！");
			retMap.put("lineNo", lineNo);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "删除加钞线路异常!");
			log.error("delAddnotesLine Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> addAddnotesLine(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String lineName = StringUtil.parseString(params.get("lineName"));
			Integer addClrPeriod = StringUtil.objectToInt(params.get("addClrPeriod"));
			String note = StringUtil.parseString(params.get("note"));
			Integer lineType = StringUtil.objectToInt(params.get("lineType"));

			if (lineTableMapper.qryLineNameExist(lineName) > 0) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_EXIST.getCode());
				retMap.put("retMsg", "线路名称已存在，请修改名称再重新提交!");
				return retMap;
			}

			//获取最大顺序号
//			String lineNoMax = lineAddnoteLineMapper.getLineNoMax(clrCenterNo);
			String lineNoMax = lineTableMapper.getLineNoMax(clrCenterNo);
			String lineNo = null;
			if (lineNoMax == null || "".equals(lineNoMax)) {
				lineNo = clrCenterNo + "001";
			} else {
				String a = lineNoMax.substring(6, 9);
				int tmp = StringUtil.objectToInt(a) + 1;
				String lineNo_ = StringUtil.parseString(tmp);
				int length = lineNo_.length();
				while (length < 3) {
					StringBuffer sb = new StringBuffer();
					sb.append("0").append(lineNo_);
					lineNo_ = StringUtil.parseString(sb);
					length += 1;
				}
				lineNo = clrCenterNo + StringUtil.parseString(lineNo_);
			}

			LineTableDO lineTableDO = new LineTableDO();
			lineTableDO.setClrCenterNo(clrCenterNo);
			lineTableDO.setLineNo(lineNo);
			lineTableDO.setLineName(lineName);
			lineTableDO.setAddClrPeriod(addClrPeriod);
			lineTableDO.setNote(note);
			lineTableDO.setLineType(lineType);
//			lineAddnoteLineMapper.insert(addnoteLine);
			lineTableMapper.insert(lineTableDO);


			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "添加加钞线路成功！");
			retMap.put("lineNo", lineNo);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "添加加钞线路异常!");
			log.error("addAddnotesLine Fail: ", e);
		}
		return retMap;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> modAddnotesLine(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String lineNo = StringUtil.parseString(params.get("lineNo"));
			String lineName = StringUtil.parseString(params.get("lineName"));
			Integer addClrPeriod = StringUtil.objectToInt(params.get("addClrPeriod"));
			String note = StringUtil.parseString(params.get("note"));
			Integer lineType = StringUtil.objectToInt(params.get("lineType"));

			/*AddnoteLineDO addnoteLine = lineAddnoteLineMapper.selectByPrimaryKey(lineNo);
			if (addnoteLine == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "加钞路线修改异常!对象已不存在!");
				return retMap;
			}*/

//			LineTableDO addnoteLine = lineAddnoteLineMapper.selectByPrimaryKey(lineNo);
			LineTableDO addnoteLine = lineTableMapper.selectByPrimaryKey(lineNo);
//			lineAddnoteLineMapper.deleteByPrimaryKey(lineNo);
			lineTableMapper.deleteByPrimaryKey(lineNo);
			if (lineTableMapper.qryLineNameExist(lineName) > 0) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_EXIST.getCode());
				retMap.put("retMsg", "线路名称已存在，请修改名称再重新提交!");
				return retMap;
			}
			addnoteLine.setLineNo(lineNo);
			addnoteLine.setLineName(lineName);
			addnoteLine.setAddClrPeriod(addClrPeriod);
			addnoteLine.setNote(note);
			addnoteLine.setLineType(lineType);
//			lineAddnoteLineMapper.insert(addnoteLine);
			lineTableMapper.insert(addnoteLine);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "修改加钞线路成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "修改加钞线路异常!");
			log.error("modAddnotesLine Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryAddnotesLineDetail(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			String lineNo = StringUtil.parseString(params.get("lineNo"));

//			LineTableDO addnoteLine = lineAddnoteLineMapper.selectByPrimaryKey(lineNo);
			LineTableDO addnoteLine = lineTableMapper.selectByPrimaryKey(lineNo);
			if (addnoteLine == null) {
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", "查询加钞线路详情异常!对象已不存在!");
				return retMap;
			}

			LineTableDTO addnotesLineTableDTO = AddnotesLineConverter.INSTANCE.domain2dto(addnoteLine);
			retMap.put("addnotesLineDTO", addnotesLineTableDTO);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询加钞线路详情成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞线路详情异常!");
			log.error("qryAddnotesLineDetail Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryLineListByDateAndClrNo(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String lineDate = StringUtil.parseString(params.get("lineDate"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("lineDate", lineDate);

//			List<LineTableDO> addnoteLines = lineAddnoteLineMapper.getLineListByDateAndClrNo(paramMap);
			List<LineTableDO> addnoteLines = lineTableMapper.getLineListByDateAndClrNo(paramMap);

			List<LineTableDTO> retList = AddnotesLineConverter.INSTANCE.domain2dto(addnoteLines);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
			retMap.put("retList", retList);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "加钞线路信息查询异常!");
			log.error("getLineListByClrNo Fail: ", e);
		}
		return retMap;
	}

}
