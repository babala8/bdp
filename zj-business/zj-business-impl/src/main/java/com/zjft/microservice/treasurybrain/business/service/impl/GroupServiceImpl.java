package com.zjft.microservice.treasurybrain.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.business.domain.*;
import com.zjft.microservice.treasurybrain.business.mapstruct.NetPointMatrixDTOConverter;
import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanDetailMapper;
import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanGroupMapper;
import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanMapper;
import com.zjft.microservice.treasurybrain.business.service.GroupService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.MatrixPointType;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.NetPointMatrixInnerResource;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;


@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

	@Resource
	private AddnotesPlanMapper addnotesPlanMapper;

	@Resource
	private AddnotesPlanDetailMapper addnotesPlanDetailMapper;

	@Resource
	private AddnotesPlanGroupMapper addnotesPlanGroupMapper;

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private NetPointMatrixInnerResource netPointMatrixInnerResource;

	@Resource
	RestTemplate restTemplate;


	@Override
	public Map<String, Object> qryGroupsTsp(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));

			//查询计划设备数
			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
			if (addnotesPlan == null) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
				log.error("[qryGroupsTsp]编号为" + addnotesPlanNo + "的加钞计划不存在");
				return retMap;
			}

			String clrCenterNo = addnotesPlan.getClrCenterNo();
//			ClrCenterTable clrCenterTable = clrCenterTableMapper.selectByPrimaryKey(clrCenterNo);
			ClrCenterTable clrCenterTable = clrCenterInnerResource.selectByPrimaryKey(clrCenterNo);

			if (clrCenterTable == null) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的清机中心不存在");
				log.error("[qryGroupsTsp]编号为" + addnotesPlanNo + "的清机中心不存在");
				return retMap;
			}
			int cashTruckNum = clrCenterTable.getCashtruckNum();

			String lineMode = clrCenterTable.getNote();

			String planAddnotesDate = addnotesPlan.getPlanAddnotesDate();

			int planDevCount = addnotesPlan.getPlanDevCount();

			//最早出发时间
			String earliestStartTime = addnotesPlan.getPlanStartTime();

			//最晚到达时间
			String lastestArrivalTime = addnotesPlan.getLastestEndTime();

			//查询计划网点数
			int netPointNum = addnotesPlanDetailMapper.getNetpointNum(addnotesPlanNo);

			//查询已分组信息（只考虑自动分组）
			List<AddnotesPlanGroup> retAddnotesPlanGroup = addnotesPlanGroupMapper.getGroupsByNo(addnotesPlanNo);

			//组数
			int groupNum = retAddnotesPlanGroup.size();

			//已分组网点
			List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
			for (AddnotesPlanGroup addnotesPlanGroup : retAddnotesPlanGroup) {
				int min = addnotesPlanGroup.getPlanTimeCost() == null ? 0 : addnotesPlanGroup.getPlanTimeCost();
				Map<String, Object> entity = new HashMap<String, Object>();
				entity.put("groupNo", addnotesPlanGroup.getGroupNo());
				entity.put("lineName",addnotesPlanGroup.getLineName());
				entity.put("planDevCnt", addnotesPlanGroup.getPlanDevCnt());
				entity.put("planNetPntCnt", addnotesPlanGroup.getPlanNetpntCnt());
				entity.put("planDistance", addnotesPlanGroup.getPlanDistance());
				entity.put("planTimeCost", addnotesPlanGroup.getPlanTimeCost());

				//计算预计最早到达时间
				String predictArrivalTime = "";
				if (!"".equals(earliestStartTime)) {
					predictArrivalTime = CalendarUtil.getFixedTimeHMS(earliestStartTime, 0, min, 0);
				}
				entity.put("predictArrivalTime", predictArrivalTime);
				groupList.add(entity);
			}

			//查询未分组网点
			List<Map<String, Object>> tempList = addnotesPlanDetailMapper.getNetPointsNotGroup(addnotesPlanNo);
			List<Map<String, Object>> netPointsNotGroupList = this.formNetpointsWithDevs(tempList, false, false);

			int maxNetpointNumOfGroup;
			if (groupNum > 0) {//已分组
				maxNetpointNumOfGroup = netPointNum - groupNum + 1;
			} else {//尚未分组
				// 每组最大网点数，默认10  TODO 熔断控制
//				maxNetpointNumOfGroup = StringUtil.objectToInt(sysParamMapper.qryValueByName("maxNetpointNumOfGroup"));
				maxNetpointNumOfGroup = StringUtil.ch2Int(sysParamResource.qrySysParamByName("maxNetpointNumOfGroup").getParamValue());
				if (maxNetpointNumOfGroup == -1) {
					maxNetpointNumOfGroup = 10;
				} else {
					// 最小样本容量 TODO 熔断控制
//					maxNetpointNumOfGroup = StringUtil.objectToInt(sysParamMapper.qryValueByName("MIN_SAMPLE_VOLUME"));
					maxNetpointNumOfGroup = StringUtil.ch2Int(sysParamResource.qrySysParamByName("MIN_SAMPLE_VOLUME").getParamValue());

					if (maxNetpointNumOfGroup == -1) {
						maxNetpointNumOfGroup = 10;
					}
				}

				if (maxNetpointNumOfGroup > netPointNum) {
					maxNetpointNumOfGroup = netPointNum;
				}
				groupNum = netPointNum / maxNetpointNumOfGroup + (netPointNum % maxNetpointNumOfGroup == 0 ? 0 : 1);
			}

			retMap.put("planAddnotesDate", planAddnotesDate);
			retMap.put("netPointNum", netPointNum);
			retMap.put("planDevCount", planDevCount);
			retMap.put("groupNum", groupNum);
			retMap.put("maxNetpointNumOfGroup", maxNetpointNumOfGroup);
			retMap.put("earliestStartTime", earliestStartTime);
			retMap.put("lastestArrivalTime", lastestArrivalTime);
			retMap.put("cashTruckNum", cashTruckNum);
			retMap.put("lineMode", lineMode);
			retMap.put("clrCenterNo", clrCenterNo);

			retMap.put("groupList", groupList);
			retMap.put("netPointsNotGroupList", netPointsNotGroupList);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "计划分组信息查询成功!");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "计划分组信息查询查询异常!");
			log.error("qryGroupsTsp Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryGroupByNo(String createJsonString) {
		log.info("------------[]AddnotesPlanWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, Object> aMap = this.qryAutoGroupTspDetail(createJsonString);
			Integer planNetPntCnt = StringUtil.objectToInt(aMap.get("planNetPntCnt"));
			Integer planDevCnt = StringUtil.objectToInt(aMap.get("planDevCnt"));
			Integer planDistance = StringUtil.objectToInt(aMap.get("planDistance"));
			Integer planTimeCost = StringUtil.objectToInt(aMap.get("planTimeCost"));
			String jsonNetPointsGroup = JSONUtil.createJsonString(aMap.get("netPointsGroup"));
			String jsonNetPointsNotGroup = JSONUtil.createJsonString(aMap.get("netPointsNotGroup"));

			List<HashMap<String, Object>> netPointsGroupList = new ArrayList<HashMap<String, Object>>();
			if (!"".equals(jsonNetPointsGroup)) {
				JSONArray jsonArray = JSONUtil.parseJSONArray(jsonNetPointsGroup);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonEntity = jsonArray.getJSONObject(i);
					HashMap<String, Object> netPointsGroup = new HashMap<String, Object>();
					netPointsGroup.put("orgNo", jsonEntity.getString("orgNo"));
					netPointsGroup.put("orgName", jsonEntity.getString("orgName"));
					netPointsGroup.put("address", jsonEntity.getString("address"));
					netPointsGroup.put("x", jsonEntity.getDouble("x"));
					netPointsGroup.put("y", jsonEntity.getDouble("y"));
					netPointsGroup.put("sortNo", jsonEntity.getString("sortNo"));
					netPointsGroup.put("devNo", jsonEntity.getString("devNo"));
					netPointsGroup.put("keyEventDetail", jsonEntity.getString("keyEventDetail"));
//					String jsonDevsNos = StringUtil.parseString(jsonEntity.get("devs"));
//					List<Map<String, Object>> devs = new ArrayList<Map<String, Object>>();
//					if (!"".equals(jsonDevsNos)) {
//						JSONArray jsonArrayDev = JSONUtil.parseJSONArray(jsonDevsNos);
//						for (int j = 0; j < jsonArrayDev.size(); j++) {
//							JSONObject jsonDev = jsonArrayDev.getJSONObject(j);
//							Map<String, Object> devMap = new HashMap<String, Object>();
//							devMap.put("no", jsonDev.getString("no"));
//							devMap.put("keyEventDetail", jsonDev.getString("keyEventDetail"));
//							devs.add(devMap);
//						}
//					}
//					netPointsGroup.put("devs", devs);

					netPointsGroupList.add(netPointsGroup);
				}
			}

			List<HashMap<String, Object>> netPointsNotGroupList = new ArrayList<HashMap<String, Object>>();
			if (!"".equals(jsonNetPointsNotGroup)) {
				JSONArray jsonArray = JSONUtil.parseJSONArray(jsonNetPointsNotGroup);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonEntity = jsonArray.getJSONObject(i);
					HashMap<String, Object> netPointsNotGroup = new HashMap<String, Object>();
					netPointsNotGroup.put("orgNo", jsonEntity.getString("orgNo"));
					netPointsNotGroup.put("orgName", jsonEntity.getString("orgName"));
					netPointsNotGroup.put("address", jsonEntity.getString("address"));
					netPointsNotGroup.put("x", jsonEntity.getDouble("x"));
					netPointsNotGroup.put("y", jsonEntity.getDouble("y"));
					netPointsNotGroup.put("devNo", jsonEntity.getString("devNo"));
					netPointsNotGroup.put("keyEventDetail", jsonEntity.getString("keyEventDetail"));
//					String jsonDevsNos = StringUtil.parseString(jsonEntity.get("devs"));
//					List<Map<String, Object>> devs = new ArrayList<Map<String, Object>>();
//					if (!"".equals(jsonDevsNos)) {
//						JSONArray jsonArrayDev = JSONUtil.parseJSONArray(jsonDevsNos);
//						for (int j = 0; j < jsonArrayDev.size(); j++) {
//							JSONObject jsonDev = jsonArrayDev.getJSONObject(j);
//							Map<String, Object> devMap = new HashMap<String, Object>();
//							devMap.put("no", jsonDev.getString("no"));
//							devMap.put("keyEventDetail", jsonDev.getString("keyEventDetail"));
//							devs.add(devMap);
//						}
//					}
//					netPointsNotGroup.put("devs", devs);
					netPointsNotGroupList.add(netPointsNotGroup);
				}
			}

			retMap.put("planNetPntCnt", planNetPntCnt);
			retMap.put("planDevCnt", planDevCnt);
			retMap.put("planDistance", planDistance);
			retMap.put("planTimeCost", planTimeCost);
			retMap.put("netPointsGroupList", netPointsGroupList);
			retMap.put("netPointsNotGroupList", netPointsNotGroupList);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "设备分组详细信息查询成功!");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "设备分组详细信息查询失败!");
			log.error("qryGroupByNo Fail: ", e);
		}

		return retMap;
	}


	public Map<String, Object> qryAutoGroupTspDetail(String jsonParam) {
		log.info("------------[qryAutoGroupTspDetail]GroupService-------------");

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(params.get("groupNo"));

			//查询计划设备数
			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
			if (addnotesPlan == null) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
				log.error("[qryGroupsTsp]编号为" + addnotesPlanNo + "的加钞计划不存在");
				return retMap;
			}

			int clrTimeInterval = 0;
			AddnotesPlanGroupKey addnotesPlanGroupKey = new AddnotesPlanGroupKey();
			addnotesPlanGroupKey.setAddnotesPlanNo(addnotesPlanNo);
			addnotesPlanGroupKey.setGroupNo(groupNo);
			addnotesPlanGroupKey.setClrTimeInterval(clrTimeInterval);
			AddnotesPlanGroup addnotesPlanGroup = addnotesPlanGroupMapper.selectByPrimaryKey(addnotesPlanGroupKey);

			//网点数
			int planNetPntCnt = addnotesPlanGroup.getPlanNetpntCnt();
			//设备数
			int planDevCnt = addnotesPlanGroup.getPlanDevCnt();
			//总路程
			int planDistance = addnotesPlanGroup.getPlanDistance();
			//总耗时
			int planTimeCost = addnotesPlanGroup.getPlanTimeCost();

			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("addnotesPlanNo", addnotesPlanNo);
			paramMap.put("lineNo", groupNo);
			List<Map<String, Object>> netpointsOfGroupDev = addnotesPlanDetailMapper.getNetPointsWithGroup(paramMap);
			List<Map<String, Object>> netPointsGroup = this.formNetpointsWithDevs(netpointsOfGroupDev, true, false);

			//查询未分组网点
			List<Map<String, Object>> netpointsNotGroupDev = addnotesPlanDetailMapper.getNetPointsNotGroup(addnotesPlanNo);
			List<Map<String, Object>> netPointsNotGroup = this.formNetpointsWithDevs(netpointsNotGroupDev, false, false);

			retMap.put("planNetPntCnt", planNetPntCnt);
			retMap.put("planDevCnt", planDevCnt);
			retMap.put("planDistance", planDistance);
			retMap.put("planTimeCost", planTimeCost);
			retMap.put("netPointsGroup", netPointsGroup);
			retMap.put("netPointsNotGroup", netPointsNotGroup);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "计划分组详细信息查询成功!");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "计划分组详细信息查询查询异常!");
			log.error("qryAutoGroupTspDetail Fail: ", e);
		}
		return retMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> modGroup(String jsonParam) {
		log.info("------------[modGroup]GroupService-------------");

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try{
		JSONObject params = JSONUtil.parseJSONObject(jsonParam);
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
		String groupNo = StringUtil.parseString(params.get("groupNo"));
		String jsonNetPointsGroup = (String) params.get("netPointsGroup");
		String jsonNetPointsNotGroup = (String) params.get("netPointsNotGroup");
		Integer clrTimeInterval = 0;

		List<String> netPointsList = new ArrayList<String>();
		//添加组内网点编号
		if (!"".equals(jsonNetPointsGroup)) {
			JSONArray jsonArray = JSONUtil.parseJSONArray(jsonNetPointsGroup);
			for (int j = 0; j < jsonArray.size(); j++) {
				netPointsList.add(jsonArray.getString(j));
			}
		}
		//添加组外网点编号（需要加入该组）
//			List<String> netPointsNotList=new ArrayList<String>();
//			if(!"".equals(jsonNetPointsNotGroup)){
//				JSONArray jsonArray = JSONUtil.parseJSONArray(jsonNetPointsNotGroup);
//				for(int j = 0; j < jsonArray.size(); j++){
//					netPointsNotList.add(jsonArray.getString(j));
////					netPointsList.add(jsonArray.getString(j));
//				}
//			}

		//开始分组
		//查询加钞计划，校验，若加钞计划不存在则返回错误信息
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
			log.error("[qryGroupsTsp]编号为" + addnotesPlanNo + "的加钞计划不存在");
			return retMap;
		}
		AddnotesPlanGroupKey addnotesPlanGroupKey = new AddnotesPlanGroupKey();
		addnotesPlanGroupKey.setAddnotesPlanNo(addnotesPlanNo);
		addnotesPlanGroupKey.setGroupNo(groupNo);
		addnotesPlanGroupKey.setClrTimeInterval(clrTimeInterval);
		AddnotesPlanGroup addnotesPlanGroup = addnotesPlanGroupMapper.selectByPrimaryKey(addnotesPlanGroupKey);

		//校验加钞计划分组是否存在，若不存在则返回失败信息
		if (addnotesPlanGroup == null) {
			retMap.put("retMsg", "加钞计划[" + addnotesPlanNo + "]下的某组[" + groupNo + "]不存在");
			log.error("[clearGroup] " + "加钞计划[" + addnotesPlanNo + "]下的某组[" + groupNo + "]不存在");
			return retMap;
		}
		addnotesPlanGroup.setPlanDevCnt(0);
		addnotesPlanGroup.setPlanNetpntCnt(0);
		addnotesPlanGroup.setPlanDistance(0);
		addnotesPlanGroup.setPlanTimeCost(0);
		addnotesPlanGroupMapper.updateByPrimaryKey(addnotesPlanGroup);


		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("addnotesPlanNo", addnotesPlanNo);
		paraMap.put("lineNo", groupNo);
		addnotesPlanDetailMapper.updateByGroupNo(paraMap);

		//查询分组detail信息
		List<AddnotesPlanDetail> details;
		if (netPointsList.size() > 0) {
			paraMap.put("netpointNos", netPointsList);
			details = addnotesPlanDetailMapper.qryDetailByDevPointGroup(paraMap);
		} else {
			details = new ArrayList<AddnotesPlanDetail>();
		}
		// 更新details的组别和排序字段
		for (AddnotesPlanDetail addnotesPlanDetail : details) {
			addnotesPlanDetail.setLineNo(String.valueOf(groupNo));
			addnotesPlanDetail.setSortNo(null);
			addnotesPlanDetailMapper.updateByPrimaryKey(addnotesPlanDetail);
		}
		// 更新计划分组的设备数和网点数
		addnotesPlanGroup.setPlanDevCnt(details.size());
		addnotesPlanGroup.setPlanNetpntCnt(netPointsList.size());
		addnotesPlanGroupMapper.updateByPrimaryKey(addnotesPlanGroup);

		//默认进行规划线路【注释--抽出单独做为一个组件】
		int tactic = StatusEnum.MatrixTactic.TIME_SHORTEST.getValue();//默认采用11
		int dataType = 1; //设备关联
		List<Object> sortGroupRetList = this.sortPlanGroup(addnotesPlanNo, groupNo, tactic, dataType);
		Integer sortGroupRetCode = (Integer) sortGroupRetList.get(0);
		if (sortGroupRetCode != 1) {
			log.error("[modGroup]加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组规划路线失败");
		}

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "修改分组信息成功!");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "修改分组信息异常!");
//			log.error("modGroup Fail: ", e);
//		}
		return retMap;
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

	/**
	 * 添加分组
	 *
	 * @param addnotesPlanNo
	 * @param groupNo
	 * @param netpointNos
	 * @return
	 */
	private boolean addGroup(String addnotesPlanNo, String groupNo, List<String> netpointNos) {
		try {
			int devCnt = 0;
			int netpointNosSize = netpointNos.size();

			if (netpointNosSize > 0) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("addnotesPlanNo", addnotesPlanNo);
				paramMap.put("netpointNos", netpointNos);
				List<AddnotesPlanDetail> details = addnotesPlanDetailMapper.qryDetailByDevPoint(paramMap);
				for (AddnotesPlanDetail addnotesPlanDetail : details) {
					addnotesPlanDetail.setLineNo(groupNo);
					addnotesPlanDetailMapper.updateByPrimaryKeySelective(addnotesPlanDetail);
				}
				devCnt = details.size();
			}

			AddnotesPlanGroup addnotesPlanGroup = new AddnotesPlanGroup();
			addnotesPlanGroup.setAddnotesPlanNo(addnotesPlanNo);
			addnotesPlanGroup.setGroupNo(groupNo);
			addnotesPlanGroup.setClrTimeInterval(0);//0 : 该组不包含"顺延法"筛选出的设备
			addnotesPlanGroup.setPlanDevCnt(devCnt);
			addnotesPlanGroup.setPlanNetpntCnt(netpointNosSize);
			addnotesPlanGroupMapper.insertSelective(addnotesPlanGroup);

			// succeed to return
			return true;
		} catch (Exception e) {
			log.error("[addGroup] 记录加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组的分组信息异常: " ,e);
			return false;
		}
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
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null || !StatusEnum.AddnotesPlanStatus.canSort(addnotesPlan.getStatus())) {
			log.error("[sortPlanGroup] 加钞计划[" + addnotesPlanNo + "]不存在或状态异常");
			retList.add("加钞计划[" + addnotesPlanNo + "]不存在或状态异常");
			return retList;
		}
		String clrCenterNo = addnotesPlan.getClrCenterNo();

		// 删除加钞计划某组的排序信息
		//查询分组信息并更新
		AddnotesPlanGroupKey addnotesPlanGroupKey = new AddnotesPlanGroupKey();
		addnotesPlanGroupKey.setAddnotesPlanNo(addnotesPlanNo);
		addnotesPlanGroupKey.setGroupNo(groupNo);
		addnotesPlanGroupKey.setClrTimeInterval(0);
		AddnotesPlanGroup addnotesPlanGroup = addnotesPlanGroupMapper.selectByPrimaryKey(addnotesPlanGroupKey);
		if (addnotesPlanGroup == null) {
			log.error("加钞计划[" + addnotesPlanNo + "]下第[" + groupNo + "]组不存在");
			retList.add("加钞计划[" + addnotesPlanNo + "]下第[" + groupNo + "]组不存在");
			return retList;
		}
		addnotesPlanGroup.setPlanDistance(0);
		addnotesPlanGroup.setPlanTimeCost(0);
		addnotesPlanGroupMapper.updateByPrimaryKey(addnotesPlanGroup);

		// 更新加钞计划明细
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addnotesPlanNo", addnotesPlanNo);
		paramMap.put("lineNo", groupNo);
		addnotesPlanDetailMapper.updateSortNoNull(paramMap);


		// 网点编号
//			List<String> pointNos = addnotesPlanDetailMapper.getNetpointList(paramMap);
//			if (pointNos == null) {
//				retList.add("获取加钞计划[" + addnotesPlanNo + "]第[" + groupNo + "]组的网点编号列表失败!");
//				return retList;
//			}
		List<String> pointNos = addnotesPlanDetailMapper.getDevPointList(paramMap);
		if (pointNos == null || pointNos.size() == 0 ) {
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
			addnotesPlanDetailMapper.updateSortNoByDevNo(paraMap);
		}

		// -- 加钞计划分组
		AddnotesPlanGroup addnotesPlanGroupN = addnotesPlanGroupMapper.selectByPrimaryKey(addnotesPlanGroupKey);
		if (addnotesPlanGroupN == null) {
			log.error("[sortPlanGroup] 加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组不存在");
			retList.add("加钞计划[" + addnotesPlanNo + "]第" + groupNo + "组不存在");
			return retList;
		}
		addnotesPlanGroupN.setPlanDistance(distance);
		addnotesPlanGroupN.setPlanTimeCost(time);
		addnotesPlanGroupMapper.updateByPrimaryKey(addnotesPlanGroupN);

		// succeed to return
		retList.clear();
		retList.add(1);
		retList.add(distance);
		retList.add(time);
		return retList;
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
//			List<NetPointMatrix> costsN2N = netPointMatrixMapper.selectNetpointMatrix(paraMapN2N);
			List<NetpointMatrixDTO>  cost = netPointMatrixInnerResource.selectNetpointMatrix(paraMapN2N);
			List<NetPointMatrix> costsN2N = NetPointMatrixDTOConverter.INSTANCE.domain2dto2(cost);
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
//			List<NetPointMatrix> costsC2N = netPointMatrixMapper.selectNetpointMatrix(paraMapC2N);
			List<NetpointMatrixDTO>  cost1 = netPointMatrixInnerResource.selectNetpointMatrix(paraMapC2N);
			List<NetPointMatrix> costsC2N = NetPointMatrixDTOConverter.INSTANCE.domain2dto2(cost1);
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
//			List<NetPointMatrix> costsN2C = netPointMatrixMapper.selectNetpointMatrix(paraMapN2C);
			List<NetpointMatrixDTO>  cost2 = netPointMatrixInnerResource.selectNetpointMatrix(paraMapN2C);
			List<NetPointMatrix> costsN2C = NetPointMatrixDTOConverter.INSTANCE.domain2dto2(cost2);
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
				startPointType = MatrixPointType.CENTER.getValue();// MatrixPointType.CENTER.getValue();
				endPointNo = netpointNosSorted.get(0);
				endPointType = MatrixPointType.NETPOINT.getValue();// MatrixPointType.NETPOINT.getValue();
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
				startPointType = MatrixPointType.NETPOINT.getValue();
				endPointNo = netpointNosSorted.get(i + 1);
				endPointType = MatrixPointType.NETPOINT.getValue();
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
				startPointType = MatrixPointType.NETPOINT.getValue();
				endPointNo = clrCenterOrgNo;
				endPointType = MatrixPointType.CENTER.getValue();
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
		if (startPointType == MatrixPointType.NETPOINT.getValue() && endPointType == MatrixPointType.NETPOINT.getValue()) {
			type = StatusEnum.MatrixType.N_TO_N.getValue();
		} else if (startPointType == MatrixPointType.CENTER.getValue() && endPointType == MatrixPointType.NETPOINT.getValue()) {
			type = StatusEnum.MatrixType.C_TO_N.getValue();
		} else if (startPointType == MatrixPointType.NETPOINT.getValue() && endPointType == MatrixPointType.CENTER.getValue()) {
			type = StatusEnum.MatrixType.N_TO_C.getValue();
		} else {
			log.error("[getObject] startPointType[" + startPointType + "] or endPointType[" + endPointType + "] error");
			return null;
		}
//		NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
//		netPointMatrixKey.setStartPointNo(startPointNo);
//		netPointMatrixKey.setEndPointNo(endPointNo);
//		netPointMatrixKey.setType(type);
//		netPointMatrixKey.setTactic(tactic);
		Map<String,Object> netPointMatrixMap = new HashMap<>();

		netPointMatrixMap.put("startPointNo",startPointNo);
		netPointMatrixMap.put("endPointNo",endPointNo);
		netPointMatrixMap.put("type",type);
		netPointMatrixMap.put("tactic",tactic);
		NetpointMatrixDTO netpointMatrixDTO = netPointMatrixInnerResource.selectByPrimaryKeyMap(netPointMatrixMap);
		NetPointMatrix netPointMatrix = NetPointMatrixDTOConverter.INSTANCE.domain2dto2(netpointMatrixDTO);
		return netPointMatrix;
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
//			List<NetPointMatrix> costsN2N = netPointMatrixMapper.selectNetpointMatrix(paramMap);
			List<NetpointMatrixDTO>  cost3 = netPointMatrixInnerResource.selectNetpointMatrix(paramMap);
			List<NetPointMatrix> costsN2N = NetPointMatrixDTOConverter.INSTANCE.domain2dto2(cost3);

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

	/**
	 * 组织网点信息s(+其下设备信息s)
	 *
	 * @param =[设备编号，网点编号,网点名称,网点地址,顺序号]
	 * @return [[网点编号, 网点名称, 网点地址, 经度, 纬度, 设备数, 设备编号字符串表示, 排序号, 设备数量]]
	 * Mode liuyuan 2019/08/16 去除同一网点判断，否则同一网点设备只保留第一条记录，不能理解设备号字符串标识什么意思
	 */
	private List<Map<String, Object>> formNetpointsWithDevs(List<Map<String, Object>> tempList, boolean withSortNo, boolean withDevCnt) {
		List<String> netpointNos = new ArrayList<String>(); // 网点编号列表,按sort_no和org_no排序
		HashMap<String, String> netpointNoNameMap = new HashMap<String, String>(); // 网点编号:网点名称
		HashMap<String, String> netpointNoAddressMap = new HashMap<String, String>(); // 网点编号:网点地址
		HashMap<String, Double> netpointNoXMap = new HashMap<String, Double>(); //网点编号：经度
		HashMap<String, Double> netpointNoYMap = new HashMap<String, Double>(); //网点编号：纬度
		HashMap<String, String> netpointNoPlanAddnotesAmtMap = new HashMap<String, String>();//网点编号：加钞金额
		HashMap<String, String> netpointDevNoMap = new HashMap<String, String>(); //网点编号：设备
		HashMap<String, String> netpointKeyEventDetailMap = new HashMap<String, String>(); //网点编号：

		HashMap<String, ArrayList<Map<String, Object>>> netpointNoDevNosMap = new HashMap<String, ArrayList<Map<String, Object>>>(); // 网点编号:设备信息
		HashMap<String, Integer> netpointNoSortNoMap = new HashMap<String, Integer>(); // 网点编号:排序编号

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		String netpointNo;
		for (Map<String, Object> aMap : tempList) {
			String devNo = StringUtil.parseString(aMap.get("devNo"));
			String orgNo = StringUtil.parseString(aMap.get("orgNo"));
			String orgName = StringUtil.parseString(aMap.get("orgName"));
			String address = StringUtil.parseString(aMap.get("address"));
			String planAddnotesAmt = StringUtil.parseString(aMap.get("planAddnotesAmt"));
			Double x = StringUtil.ch2Double(aMap.get("x").toString());
			Double y = StringUtil.ch2Double(aMap.get("y").toString());

			int sortNo = StringUtil.objectToInt(aMap.get("sortNo"));
			String keyEventDetail = StringUtil.parseString(aMap.get("keyEventDetail"));

			netpointNo = orgNo;

			netpointNos.add(netpointNo);
			netpointNoNameMap.put(netpointNo, StringUtil.parseString(orgName));
			netpointNoAddressMap.put(netpointNo, StringUtil.parseString(address));
			netpointNoPlanAddnotesAmtMap.put(netpointNo, StringUtil.parseString(planAddnotesAmt));//加钞金额
			netpointDevNoMap.put(netpointNo, StringUtil.parseString(devNo));
			netpointKeyEventDetailMap.put(netpointNo, StringUtil.parseString(keyEventDetail));
			netpointNoXMap.put(netpointNo, x);
			netpointNoYMap.put(netpointNo, y);
			netpointNoSortNoMap.put(netpointNo, sortNo);

			//创建设备号列表
			ArrayList<Map<String, Object>> netpointNoDevNoList = new ArrayList<Map<String, Object>>();
			netpointNoDevNosMap.put(netpointNo, netpointNoDevNoList);

			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("orgNo", orgNo);
			entity.put("devNo", netpointDevNoMap.get(orgNo));
			entity.put("keyEventDetail", netpointKeyEventDetailMap.get(orgNo));
			entity.put("orgName", netpointNoNameMap.get(orgNo));
			entity.put("address", netpointNoAddressMap.get(orgNo));
			entity.put("planAddnotesAmt", netpointNoPlanAddnotesAmtMap.get(orgNo));
			entity.put("x", netpointNoXMap.get(orgNo));
			entity.put("y", netpointNoYMap.get(orgNo));
			if (withSortNo) {
				entity.put("sortNo", netpointNoSortNoMap.get(orgNo));
			}
			if (withDevCnt) {
				entity.put("devCnt", netpointNoDevNosMap.get(orgNo).size());
			}
			retList.add(entity);
		}
		return retList;
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
		paramMap.put("addnotesPlanNo",addnotesPlanNo);
		List<Map<String, Object>> lineMsgList = addnotesPlanDetailMapper.getLineMsgList(paramMap);
		return lineMsgList;

	}

	//TODO 新框架作为服务使用
	/**
	 * 设备自动分组
	 * 1.查询待分组设备
	 * 2.组成矩阵发送给zjml
	 * 3.解析zjml返回数据
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @param tactic 分组策略
	 * @param groupNum 分组数
	 * @return 分组结果 分组编号 设备编号
	 */
	public List<Map<String, Object>> groupDevAuto(String addnotesPlanNo, int tactic, int groupNum) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("addnotesPlanNo",addnotesPlanNo);
		int dataType = 1;
		//查询待分组设备
		List<String> pointNosList = addnotesPlanDetailMapper.getDevPointList(paramMap);
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
		List<Map<String,Object>> lineMsgList = new ArrayList<>();
		if(devGroupList!=null){
			for(int devNum = 0;devNum<devGroupList.size();devNum++){
				Map<String,Object> devGroupInfo = new HashMap<>();
				devGroupInfo.put("lineNo",devGroupList.get(devNum));
				devGroupInfo.put("devNo",pointNosList.get(devNum));
				lineMsgList.add(devGroupInfo);
			}
		}

		return lineMsgList;
	}

	//TODO 新框架作为服务使用
	/**
	 * 更新设备分组
	 * 1。更新加钞计划详情的设备加钞线路即分组
	 * 2。更新加钞计划分组表中的设备数量和设备对应的网点数量
	 *
	 * @param lineMsgList 线路编号 设备编号
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 更新结果
	 */
	public DTO updateGroupInfo(List<Map<String, Object>> lineMsgList, String addnotesPlanNo) {
		DTO dto = new DTO(RetCodeEnum.SUCCEED);
		for (Map<String, Object> lineMsg : lineMsgList) {
			//更新加钞计划详情设备加钞线路为所属线路
			String lineNo = StringUtil.parseString(lineMsg.get("lineNo"));
			String devNo = StringUtil.parseString(lineMsg.get("devNo"));
			AddnotesPlanDetail addnotesPlanDetail = new AddnotesPlanDetail();
			addnotesPlanDetail.setAddnotesPlanNo(addnotesPlanNo);
			addnotesPlanDetail.setDevNo(devNo);
			addnotesPlanDetail.setLineNo(lineNo);
			addnotesPlanDetailMapper.updateByPrimaryKeySelective(addnotesPlanDetail);
		}

//		List<AddnotesPlanDetail>  as  = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);
		List<Map<String,Object>> groupNoAndDevNumList = addnotesPlanDetailMapper.getDevCountEachGroup(addnotesPlanNo);
		for(Map<String,Object> groupNoAndDevNum:groupNoAndDevNumList){
			String lineNo = StringUtil.parseString(groupNoAndDevNum.get("LINENO"));
			//线路上设备数
			int devCnt = Integer.parseInt(String.valueOf(groupNoAndDevNum.get("DEVNUM")));
			//线路上网点数
			AddnotesPlanDetail addnotesPlanDetail = new AddnotesPlanDetail();
			addnotesPlanDetail.setAddnotesPlanNo(addnotesPlanNo);
			addnotesPlanDetail.setLineNo(lineNo);
			int netpointNosSize = addnotesPlanDetailMapper.getNetCountInGroup(addnotesPlanDetail);

			AddnotesPlanGroup addnotesPlanGroup = new AddnotesPlanGroup();
			addnotesPlanGroup.setAddnotesPlanNo(addnotesPlanNo);
			addnotesPlanGroup.setGroupNo(lineNo);
			//0 : 该组不包含"顺延法"筛选出的设备
			addnotesPlanGroup.setClrTimeInterval(StatusEnum.DevClrTimeInterval.NOPLAN.getType());
			addnotesPlanGroup.setPlanDevCnt(devCnt);
			addnotesPlanGroup.setPlanNetpntCnt(netpointNosSize);
			addnotesPlanGroupMapper.insertSelective(addnotesPlanGroup);
		}

		return dto;
	}


}
