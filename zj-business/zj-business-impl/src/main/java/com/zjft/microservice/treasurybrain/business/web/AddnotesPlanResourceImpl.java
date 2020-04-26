package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlan;
import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanDetail;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDetailForDevDTO;
import com.zjft.microservice.treasurybrain.business.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.business.dto.DevForChooseDTO;
import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanDetailMapper;
import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanMapper;
import com.zjft.microservice.treasurybrain.business.service.*;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AddnotesPlanResourceImpl implements AddnotesPlanResource {

	@Resource
	private AddnotesPlanService addnotesPlanService;

	@Resource
	private AddnotesDevAnalyseService addnotesDevAnalyseService;

	@Resource
	private GroupService groupService;

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private AddnotesPlanDetailMapper addnotesPlanDetailMapper;

	@Resource
	private AddnotesPlanMapper addnotesPlanMapper;

	//当返回值为null时，使用此List代替
	List arrayList = new ArrayList(0);


	/**
	 *添加加钞计划审核
	 */
	@Override
		public DTO addAddnotesAudit(Map<String, Object> paramMap) {
			ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "提交加钞计划审核失败");
			try {
				String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
				Map<String, Object> retMap = addnotesPlanService.addAddnotesAudit(addnotesPlanNo);

				dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
				dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

			} catch (Exception e) {
				log.error("提交加钞计划审核失败 ", e);
				dto.setRetException("提交加钞计划审核失败");
			}
		return dto;
	}

	/**
	 *修改加钞金额（金额调整）
	 */
	@Override
	public DTO modAddnotesPlanAmts(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "修改失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String modOpNo = authUser.getUsername();
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			List<AddnotesPlanDetailForDevDTO> devList = (List<AddnotesPlanDetailForDevDTO>) paramMap.get("devList");

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("modOpNo", modOpNo);
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("devList", devList);

			Map<String, Object> retMap = addnotesPlanService.modAddnotesPlanAmts(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("修改金额列表失败:", e);
			dto.setRetException("修改金额失败!");
		}
		return dto;
	}

	/**
	 *设备金额预测
	 */
	@Override
	public DTO addnotesPlanCashReqAmt(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "预测现金需求量失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String modOpNo = authUser.getUsername();

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));

			//检查加钞编号addnotesPlanNo是否传入，没有则提示缺少参数
			if (addnotesPlanNo == null || addnotesPlanNo.equals("")) {
				dto.setRetMsg("加钞编号为空，缺少参数");
				return dto;
			}

			int sumAmt = 0;

			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);

			//查询加钞计划明细
			List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);

			if(addnotesPlanDetails.size()==0){
				dto.setRetMsg(" 加钞编号为 ["+ addnotesPlanNo + "]的加钞计划不存在 !");
				return dto;
			}

			//遍历明细，取设备号，加钞周期  分别调用模型预测接口，加钞金额修正接口，策略调整接口，最终取得实际加钞预测金额。
			for (AddnotesPlanDetail addnotesPlanDetail : addnotesPlanDetails) {
				//获取设备号
				String termID = addnotesPlanDetail.getDevNo();
				//获取加钞周期
				int addCashCycle = addnotesPlanDetail.getAddClrPeriod();
				//获取系统当前日期的后一天作为预测日期
				String predictDate = CalendarUtil.getFixedDate(CalendarUtil.getSysTimeYMD(), 1);

				//模型预测
				int predictValue = addnotesPlanService.getAddnotesPredictValue( termID, addCashCycle, predictDate);
				//预测值校验
				int checkValue = addnotesPlanService.compareAddnotesAmount(termID, addnotesPlan.getPlanAddnotesDate(), addCashCycle, predictValue);
				//加钞金额修正
				Map<String, Object> retMap = addnotesPlanService.modAddnotesPredict(addnotesPlanDetail,predictValue);
				double cashReqAmt = (double)retMap.get("cashReqAmt");
				int availableDays = StringUtil.objectToInt(retMap.get("availableDays"));
				//加钞金额策略调整
				int planPredictAmt  = addnotesPlanService.adjustAddnotesPredict(addnotesPlanDetail,addnotesPlanNo,cashReqAmt,availableDays);
				sumAmt = sumAmt + planPredictAmt;

			}
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("modOpNo", modOpNo);
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("sumAmt", sumAmt);
			Map<String, Object> resultMap = addnotesPlanService.getAddnotesPlanCashReqAmt( JSONUtil.createJsonString(params));

//			if (addnotesPlanDetails == null) {
//				dto.setRetMsg( "加钞计划明细为空！金额预测失败！");
//				return dto;
//			}
//
//
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("modOpNo", modOpNo);
//			params.put("addnotesPlanNo", addnotesPlanNo);
//
//			Map<String, Object> retMap = addnotesPlanService.getAddnotesPlanCashReqAmt(JSONUtil.createJsonString(params));
//			Map<String, Object> retMap = addnotesPlanService.getAddnotesPlanModelCashReqAmt(JSONUtil.createJsonString(params));

			dto.setElement(resultMap);
			dto.setRetCode(StringUtil.parseString(resultMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(resultMap.get("retMsg")));
		} catch (Exception e) {
			log.error("预测现金需求量失败", e);
			dto.setRetException("预测现金需求量失败!");
		}
		return dto;
	}

	/**
	 *计划分组信息查询
	 */
//	@Override
//	public ObjectDTO qryGroupTsp(String addnotesPlanNo) {
//		log.info("------------[qryGroupTsp]AddnotesPlanWebService-------------");
//		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
//		Map<String, Object> element = new HashMap<String, Object>();
//		try {
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("addnotesPlanNo", addnotesPlanNo);
//
//			Map<String, Object> aMap = groupService.qryGroupsTsp(JSONUtil.createJsonString(params));
//
//			List<HashMap<String, Object>> group = (List<HashMap<String, Object>>) aMap.get("groupList");
//			List<HashMap<String, Object>> netPointsNotGroup = (List<HashMap<String, Object>>) aMap.get("netPointsNotGroupList");
//			element.put("planAddnotesDate", aMap.get("planAddnotesDate"));
//			element.put("netPointNum", aMap.get("netPointNum"));
//			element.put("planDevCount", aMap.get("planDevCount"));
//			element.put("groupNum", aMap.get("groupNum"));
//			element.put("clrCenterNo", aMap.get("clrCenterNo"));
//			element.put("maxNetpointNumOfGroup", aMap.get("maxNetpointNumOfGroup"));
//			element.put("earliestStartTime", aMap.get("earliestStartTime"));
//			element.put("lastestArrivalTime", aMap.get("lastestArrivalTime"));
//			element.put("cashTruckNum", aMap.get("cashTruckNum"));
//			element.put("lineMode", aMap.get("lineMode"));
//			element.put("group", group);
//			element.put("netPointsNotGroup", netPointsNotGroup);
//
//			dto.setElement(element);
//			dto.setRetCode(StringUtil.parseString(aMap.get("retCode")));
//			dto.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
//		} catch (Exception e) {
//			log.error("查询计划分组信息失败:", e);
//			dto.setRetException("查询失败");
//			return dto;
//		}
//		return dto;
//	}

	/**
	 *查询维护型设备列表
	 */
	@Override
	public ListDTO qryAddnotesPlanDevsForMaintain(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "获取失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("orgNo", orgNo);

			Map<String, Object> retMap = addnotesPlanService.qryAddnotesPlanDevsForMaintain(JSONUtil.createJsonString(params));
			List<AddnotesPlanDTO> retList = (List<AddnotesPlanDTO>) retMap.get("retList");

			if (retList == null){
				retList = arrayList;
			}
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败:", e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}

	/**
	 *查询决定型设备列表
	 */
	@Override
	public ListDTO<DevForChooseDTO> qryAddnotesPlanDevsForKey(Map<String, Object> paramMap) {
		ListDTO<DevForChooseDTO> dto = new ListDTO<>(RetCodeEnum.FAIL.getCode(), "获取失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("orgNo", orgNo);

			Map<String, Object> retMap = addnotesPlanService.qryAddnotesPlanDevsForCash(JSONUtil.createJsonString(params));
			List retList = (List) retMap.get("retList");

			if (retList == null){
				retList = arrayList;
			}
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败:", e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}

	/**
	 *查询预测型设备列表
	 */
	@Override
	public ListDTO qryAddnotesPlanDevsForPredict(Map<String, Object> paramMap) {

		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "获取失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("orgNo", orgNo);

			Map<String, Object> retMap = addnotesPlanService.qryAddnotesPlanDevsForPredict(JSONUtil.createJsonString(params));
			List retList = (List) retMap.get("retList");

			if (retList == null){
				retList = arrayList;
			}
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败:", e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}

	/**
	 *查询计划型设备列表
	 */
	@Override
	public ListDTO qryAddnotesPlanDevsForRunPlan(Map<String, Object> paramMap) {

		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "获取失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("orgNo", orgNo);

			Map<String, Object> retMap = addnotesPlanService.qryAddnotesPlanDevsForRunPlan(JSONUtil.createJsonString(params));
			List retList = (List) retMap.get("retList");

			if (retList == null){
				retList = arrayList;
			}
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败:", e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}

	/**
	 *保存加钞设备
	 */
	@Override
	public DTO addAddnotesPlanDevs(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "修改失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String modOpNo = authUser.getUsername();

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			List<AddnotesPlanDetailForDevDTO> devList = (List<AddnotesPlanDetailForDevDTO>) paramMap.get("devList");

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("modOpNo", modOpNo);
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("devList", devList);

			Map<String, Object> retMap = addnotesPlanService.addAddnotesPlanDevs(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("增加设备列表失败:", e);
			dto.setRetException("增加设备列表失败!");
		}
		return dto;
	}

	/**
	 *修改加钞设备（设备调整）
	 */
	@Override
	public DTO modAddnotesPlanDevs(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "修改失败");
		try {
			//获取当前用户作为修改人
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String modOpNo = authUser.getUsername();

			//取出前端传来的加钞计划编号和选择的设备列表
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			List<AddnotesPlanDetailForDevDTO> devList = (List<AddnotesPlanDetailForDevDTO>) paramMap.get("devList");

			//参数校验：加钞编号不为空
			if (StringUtil.isNullorEmpty(addnotesPlanNo)) {
				dto.setResult(RetCodeEnum.PARAM_LACK);
			}


			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("modOpNo", modOpNo);
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("devList", devList);

			//修改计划中的加钞设备服务
			Map<String, Object> retMap = addnotesPlanService.modAddnotesPlanDevs(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("修改设备列表失败:", e);
			dto.setRetException("修改设备列表失败!");
		}
		return dto;
	}

	/**
	 *加钞设备分析
	 */
	@Override
	public ObjectDTO getAddnotesDevAnalyse(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> retMap = addnotesDevAnalyseService.getAddnotesDevAnalyse(paramMap);

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败" + e.getMessage(), e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}

	/**
	 *设备分组信息修改
	 */
	@Override
	public DTO modGroup(Map<String, Object> paramMap) {
		log.info("------------[modGroup]AddnotesPlanWebService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(paramMap.get("groupNo"));
			String netPointsGroup = JSONUtil.createJsonString(paramMap.get("netPointsGroup"));
			String netPointsNotGroup = JSONUtil.createJsonString(paramMap.get("netPointsNotGroup"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("groupNo", groupNo);
			params.put("netPointsGroup", netPointsGroup);
			params.put("netPointsNotGroup", netPointsNotGroup);

			Map<String, Object> retMap = groupService.modGroup(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("分组信息修改失败:", e);
			dto.setRetException("分组信息修改失败");
			return dto;
		}
		return dto;
	}

	/**
	 *设备分组详细信息查询
	 */
	@Override
	public ObjectDTO qryGroupByNo(Map<String, Object> paramMap) {
		log.info("------------[qryGroupByNo]AddnotesPlanWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		Map<String, Object> element = new HashMap<String, Object>();
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(paramMap.get("groupNo"));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("groupNo", groupNo);

			Map<String, Object> retMap = groupService.qryGroupByNo(JSONUtil.createJsonString(params));

			List<HashMap<String, Object>> netPointsGroup = (List<HashMap<String, Object>>) retMap.get("netPointsGroupList");
			List<HashMap<String, Object>> netPointsNotGroup = (List<HashMap<String, Object>>) retMap.get("netPointsNotGroupList");
			element.put("planNetPntCnt", retMap.get("planNetPntCnt"));
			element.put("planDevCnt", retMap.get("planDevCnt"));
			element.put("planDistance", retMap.get("planDistance"));
			element.put("planTimeCost", retMap.get("planTimeCost"));
			element.put("netPointsGroup", netPointsGroup);
			element.put("netPointsNotGroup", netPointsNotGroup);
			dto.setElement(element);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询计划分组详细信息失败:", e);
			dto.setRetException("查询失败");
			return dto;
		}
		return dto;
	}

	@Override
	/**
	 * 加钞周期调整，需要同步生成线路运行图失败
	 */
	public DTO modDevClrTime(DevClrTimeParamListDTO dto) {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "调整失败");
		try {
			dto1 = addnotesPlanService.modDevClrTime(dto);
		} catch (Exception e) {
			dto1.setRetException("加钞周期调整失败！");
			log.error("加钞周期调整", e);
		}
		return dto1;
	}

//	/**
//	 * 查询加钞任务导出信息
//	 */
//	@Override
//	public ObjectDTO qryExportDispatch(Map<String, Object> paramMap) {
//		log.info("------------[qryExportDispatch]AddnotesPlanWebService-------------");
//		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
//		try {
//			String dispatchNos = JSONUtil.createJsonString(paramMap.get("noArr"));
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("dispatchNos", dispatchNos);
//
//			Map<String, Object> retMap = dispatchService.qryExportDispatch(JSONUtil.createJsonString(params));
//
//			dto.setElement(retMap);
//			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
//			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
//		} catch (Exception e) {
//			log.error("查询加钞任务导出信息失败 ", e);
//			dto.setRetException("查询加钞任务导出信息失败");
//		}
//		return dto;
//	}

//	/**
//	 * 加钞任务详细信息查询
//	 */
//	@Override
//	public ObjectDTO qryDispatchDetail(String dispatchNo) {
//		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
//		;
//		try {
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("dispatchNo", dispatchNo);
//
//			Map<String, Object> retMap = dispatchService.qryDispatchDetail(JSONUtil.createJsonString(params));
//			dto.setElement(retMap);
//			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
//			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
//		} catch (Exception e) {
//			log.error("查询明细失败", e);
//			dto.setRetException("查询明细失败!");
//		}
//		return dto;
//	}


	/**
	 * 查询加钞计划
	 */
	@Override
	public PageDTO<AddnotesPlanDTO> qryAddnotesPlan(Map<String, Object> paramMap) {
		PageDTO<AddnotesPlanDTO> dto = new PageDTO<AddnotesPlanDTO>();
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String planStartDate = StringUtil.parseString(paramMap.get("planStartDate"));
			String planEndDate = StringUtil.parseString(paramMap.get("planEndDate"));
			String lineNo = StringUtil.parseString(paramMap.get("lineNo"));
			int status = StringUtil.objectToInt(paramMap.get("status"));
			int urgencyFlag = StringUtil.objectToInt(paramMap.get("urgencyFlag"));
			Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 20;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("orgNo", orgNo);
			params.put("urgencyFlag", urgencyFlag);
			params.put("clrCenterNo", clrCenterNo);
			params.put("planStartDate", planStartDate);
			params.put("planEndDate", planEndDate);
			params.put("lineNo", lineNo);
			params.put("status", status);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> aMap = addnotesPlanService.qryAddnotesPlan(JSONUtil.createJsonString(params));

			List<AddnotesPlanDTO> retList = (List<AddnotesPlanDTO>) aMap.get("addnotesPlanList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);

			dto.setRetCode(StringUtil.parseString(aMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	/**
	 * 添加加钞计划
	 */
	@Override
	public ObjectDTO addAddnotesPlan(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "添加失败");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String genOpNo = authUser.getUsername();

			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String clrCenterName = StringUtil.parseString(paramMap.get("clrCenterName"));
			String planAddnotesDate = StringUtil.parseString(paramMap.get("planAddnotesDate"));
			String groupMode = StringUtil.parseString(paramMap.get("groupMode"));
			String groupNo = StringUtil.parseString(paramMap.get("groupNo"));
			String awayFlag = StringUtil.parseString(paramMap.get("awayFlag"));
			String isUrgency = StringUtil.parseString(paramMap.get("isUrgency"));

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("genOpNo", genOpNo);
			params.put("clrCenterNo", clrCenterNo);
			params.put("clrCenterName", clrCenterName);
			params.put("planAddnotesDate", planAddnotesDate);
			params.put("groupMode", groupMode);
			params.put("groupNo", groupNo);
			params.put("awayFlag", awayFlag);
			params.put("isUrgency", isUrgency);

			Map<String, Object> retMap = addnotesPlanService.addAddnotesPlan(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("添加失败", e);
			dto.setRetException("添加失败!");
		}
		return dto;
	}

	/**
	 * 查询加钞计划详情
	 */
	@Override
	public ObjectDTO qryAddnotesPlanDetail(String addnotesPlanNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> retMap = addnotesPlanService.qryAddnotesPlanDetail(addnotesPlanNo);

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询明细失败", e);
			dto.setRetException("查询明细失败!");
		}
		return dto;
	}

	/**
	 * 删除加钞计划
	 */
	@Override
	public DTO delAddnotesPlan(String addnotesPlanNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("addnotesPlanNo", addnotesPlanNo);
			Map<String, Object> retMap = addnotesPlanService.delAddnotesPlan(JSONUtil.createJsonString(paramsMap));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("删除加钞计划失败 ", e);
			dto.setRetException("删除失败");
		}
		return dto;
	}

	/**
	 * 查询加钞计划内容
	 */
	@Override
	public ObjectDTO qryAddnotesPlanDetailForDev(String addnotesPlanNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);

			Map<String, Object> retMap = addnotesPlanService.qryAddnotesPlanDetailForDev(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败" + e.getMessage(), e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}


	/**
	 * 加钞计划退审
	 */
	@Override
	public DTO addAddnotesRefuse(Map<String, Object> paramMap) {
		DTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "加钞计划退审失败");
		try {

			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String opinion = StringUtil.parseString(paramMap.get("opinion"));
			String type = StringUtil.parseString(paramMap.get("type"));

			UserDTO authUser = sysUserResource.getAuthUserInfo();

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("opinion", opinion);
			params.put("type", type);
			params.put("sysUserNo", authUser.getUsername());

			Map<String, Object> retMap = addnotesPlanService.addAddnotesRefuse(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("加钞计划退审失败 ", e);
			dto.setRetException("加钞计划退审失败");
		}
		return dto;
	}

	/**
	 * 任务单出单
	 */
	@Override
	public DTO planToTask(Map<String, Object> paramMap) {
		log.info("------------[planToTask]AddnotesPlanWebService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			String addnotesPlanNo = StringUtil.parseString(paramMap.get("addnotesPlanNo"));
			String opinion = StringUtil.parseString(paramMap.get("opinion"));
			int status = StringUtil.objectToInt(paramMap.get("status"));
			int taskType = StringUtil.objectToInt(paramMap.get("taskType"));

			String authUser = ServletRequestUtil.getUsername();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesPlanNo", addnotesPlanNo);
			params.put("opinion", opinion);
			params.put("status", status);
			params.put("taskType", taskType);
			params.put("sysUserNo", authUser);

			Map<String, Object> retMap = addnotesPlanService.planToTask(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);

		} catch (Exception e) {
			log.error("出单失败:", e);
			dto.setRetException("出单失败");
			return dto;
		}
		return dto;
	}


}
