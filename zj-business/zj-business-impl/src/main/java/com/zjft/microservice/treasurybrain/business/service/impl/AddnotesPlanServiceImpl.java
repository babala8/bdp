package com.zjft.microservice.treasurybrain.business.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.accountscenter.web_inner.BiztxlogInitInnerResource;
import com.zjft.microservice.treasurybrain.business.domain.*;
import com.zjft.microservice.treasurybrain.business.dto.*;
import com.zjft.microservice.treasurybrain.business.mapstruct.AddnotesPlanDTOConverter;
import com.zjft.microservice.treasurybrain.business.mapstruct.AddnotesPlanDetailDTOConverter;
import com.zjft.microservice.treasurybrain.business.repository.*;
import com.zjft.microservice.treasurybrain.business.service.*;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevBaseInfoInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevCountInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.AddnotesPlanStatus;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.CycleFlag;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.DevCatalog;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineScheduleInnerResource;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.LineWorkInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskDetailInfoInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskDetailPropertyInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskLineInnerResource;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.Map;



/**
 * @author 韩通
 * @author 谢菊花
 */
@Slf4j
@Service
public class AddnotesPlanServiceImpl implements AddnotesPlanService {

	@Resource
	private SysUserResource sysUserResource;

	@Resource
	private AddnotesPlanMapper addnotesPlanMapper;

	@Resource
	private AddnotesPlanDetailMapper addnotesPlanDetailMapper;

	@Resource
	private AddnotesPlanGroupMapper addnotesPlanGroupMapper;

	@Resource
	private AddnotesLineService addnotesLineService;

	@Resource
	private DevBaseInfoInnerResource devBaseInfoInnerResource;

	@Resource
	private DevChooseService devChooseService;

	@Resource
	private AmtPredictService amtPredictService;

	@Resource
	private BiztxlogInitInnerResource biztxlogInitInnerResource;

	@Resource
	private BusinessLineRunService businessLineRunService;

	@Resource
	private AmtPredictServiceImpl amtPredictServiceImpl;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Resource
	private DevCountInnerResource devCountInnerResource;

	@Resource
	private LineScheduleInnerResource lineScheduleInnerResource;

	@Resource
	private TaskLineInnerResource taskLineInnerResource;

	@Resource
	private AddnotesAmountMapper addnotesAmountMapper;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private LineWorkInnerResource lineWorkInnerResource;

	@Resource
	private TaskDetailInfoInnerResource taskDetailInfoInnerResource;

	@Resource
	private TaskDetailPropertyInnerResource taskDetailPropertyInnerResource;

	/**
	 * 添加加钞审核计划
	 * 查询加钞计划，有则提交审核，审核完成后推送当前剩余审核计划数（aop）
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 状态码
	 */
	@Override
	public Map<String, Object> addAddnotesAudit(String addnotesPlanNo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}
		UserDTO authUser = sysUserResource.getAuthUserInfo();

		addnotesPlan.setSubmitOpNo(authUser.getUsername());
		addnotesPlan.setSubmitDate(CalendarUtil.getSysTimeYMD());
		addnotesPlan.setSubmitTime(CalendarUtil.getSysTimeHMS());
		addnotesPlan.setStatus(AddnotesPlanStatus.NEED_AUDIT.getId());
		int result = addnotesPlanMapper.updateByPrimaryKeySelective(addnotesPlan);

//		int count = addnotesPlanMapper.selectWaitVerifyCount();
//		String message = "当前待审核计划个数为：" + count;
//		DTO sendInfo2All = sendInfoResource.sendInfo2All(message);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "提交计划审核成功！");
		return retMap;
	}

	/**
	 * 修改加钞金额（金额调整）
	 *
	 * @param createJsonString modOpNo：最近修改人 addnotesPlanNo：加钞计划编号 devList：设备列表
	 * @return 状态码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> modAddnotesPlanAmts(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);
		String modOpNo = StringUtil.parseString(params.get("modOpNo"));
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));

		//验证加钞编号对应的加钞计划是否存在，若不存在直接返回错误信息
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}

		//当前用户作为修改人，当前时间作为修改时间
		addnotesPlan.setModOpNo(modOpNo);
		addnotesPlan.setModDate(CalendarUtil.getSysTimeYMD());
		addnotesPlan.setModTime(CalendarUtil.getSysTimeHMS());

		long deltaPlanAmt = 0; //调整后的计划金额-原计划金额

		String devListString = StringUtil.parseString(params.get("devList"));
		if (devListString != null && !"".equals(devListString)) {
			JSONArray jsonArray = JSONUtil.parseJSONArray(devListString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonEntity = jsonArray.getJSONObject(i);
				String devNo = jsonEntity.getString("devNo");
				int planAddnotesAmt = jsonEntity.getInteger("planAddnotesAmt") == null ? 0 : jsonEntity.getInteger("planAddnotesAmt");

				log.info("设备[" + devNo + "]预测加钞金额调整为[" + planAddnotesAmt + "]");
				AddnotesPlanDetailKey addnotesPlanDetailKey = new AddnotesPlanDetailKey();
				addnotesPlanDetailKey.setAddnotesPlanNo(addnotesPlanNo);
				addnotesPlanDetailKey.setDevNo(devNo);

				//根据加钞计划编号和设备编号查出加钞计划详细
				AddnotesPlanDetail addnotesPlanDetail = addnotesPlanDetailMapper.selectByPrimaryKey(addnotesPlanDetailKey);
				if (addnotesPlanDetail == null) {
					retMap.put("retMsg", "设备[" + devNo + "]的加钞明细不存在，可能已被删除！");
					return retMap;
				}

				//更新计划加钞金额
				addnotesPlanDetail.setPlanAddnotesAmt(planAddnotesAmt);
				addnotesPlanDetailMapper.updateByPrimaryKey(addnotesPlanDetail);

				//汇总每条明细的调整差额合计值
				deltaPlanAmt += planAddnotesAmt - (addnotesPlanDetail.getPlanPredictAmt() == null ? 0 : addnotesPlanDetail.getPlanPredictAmt());
			}
		}
		log.info("调整差额合计(元) == " + deltaPlanAmt);
		//用原有加钞计划金额加上统计的调整差额合计值作为新的计划加钞金额，并更新加钞计划表
		addnotesPlan.setPlanAddnotesAmt(addnotesPlan.getPlanAddnotesAmt() + deltaPlanAmt);
		addnotesPlanMapper.updateByPrimaryKeySelective(addnotesPlan);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "更新信息成功！");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "更新信息异常!");
//			log.error("modAddnotesPlanAmts Fail: ", e);
//		}
		return retMap;
	}

	/**
	 * 设备金额模型预测
	 *
	 * @param termID,addCashCycle,redictDate
	 * @return cashReqAmt
	 */
	@Override
	public int getAddnotesPredictValue(String termID, int addCashCycle, String predictDate) {

		int predictValue = DEFAULT_AMT_LIMIT;//设置默认加钞量20w（可配置）
		try {

			Map<String, Object> map = new HashMap<>();
			map.put("termID", termID);
			map.put("predictDate", predictDate);
			map.put("addCashCycle", addCashCycle);
			String zjmlIP = CfgProperty.getProperty("zjmlIP");
			String zjmlPort = CfgProperty.getProperty("zjmlPort");
			//4）调用ZJML金额预测接口，返回加钞金额
			String jsonString = fixedRestTemplate.postForObject("http://" + zjmlIP + ":" + zjmlPort + "/coffers/v2/predict/", JSONUtil.createJsonString(map), String.class);
			JSONObject jsonObject = JSONUtil.parseJSONObject(jsonString);
			if ("00".equals(jsonObject.get("retCode"))) {
				JSONObject predictData = jsonObject.getJSONObject("data");
				predictValue = StringUtil.objectToInt(predictData.get("addCashAmout"));
			} else {
				int avgAddCashAmount = addnotesAmountMapper.getAvgAddCashAmountHistory(termID);
				if(avgAddCashAmount != 0) {
					predictValue = avgAddCashAmount;
				}
			}

		} catch (Exception e) {
			log.error("模型预测失败 ", e);
		}
		return predictValue;
	}

	/**
	 * 加钞金额修正
	 *
	 * @param addnotesPlanDetail
	 * @return 现金需求量及剩余天数字段
	 */
	@Override
	public Map<String, Object> modAddnotesPredict(AddnotesPlanDetail addnotesPlanDetail, int predictValue) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			String devNo = addnotesPlanDetail.getDevNo();
			DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectDetailByPrimaryKey(devNo);
			if (devBaseInfo == null) {
				retMap.put("retMsg", "金额预测异常:设备[" + devNo + "]不存在 !");
				return retMap;
			}

			double cashDailyNetOut;     //日均现金净付出量(取款量-存款量)
			double cashDailyOut;        //日均现金付出量(取款量)

			//预测日均现金净付出额:元
			cashDailyNetOut = predictDailyCash(devNo, predictValue, 0);
			//预测日均现金取款额:元
			cashDailyOut = predictDailyCash(devNo, predictValue, -1);
			//设备类型
			Integer catalogNo = null;
			//是否循环标志
			Integer cycleFlag = null;

			double cashReqAmt = 0;
			double clrPeriod = 0;

			clrPeriod = addnotesPlanDetail.getAddClrPeriod().doubleValue();//加钞周期
			catalogNo = addnotesPlanDetail.getDevCatalogTableNo();//设备类型编号 （编号 10001 CRS 10002 CDM 10003 ATM 10004 BSM）
			cycleFlag = addnotesPlanDetail.getCycleFlag();  //设备是否开通循环功能：0，未开通；1，开通

			//取款现金模式(ATM或未开通循环的CRS)
			if (catalogNo == DevCatalog.ATM.getId()
					|| (cycleFlag != null && cycleFlag == CycleFlag.UNCYCLE.getId())) {
				//现金需求量
				cashReqAmt = cashDailyOut * clrPeriod;
			} else if (catalogNo == DevCatalog.CRS.getId()
					&& cycleFlag != null && cycleFlag == CycleFlag.ISCYCLE.getId()) {
				//循环现金模式
				//现金需求量
				cashReqAmt = cashDailyNetOut * clrPeriod;
				if (cashDailyOut > cashReqAmt) {
					cashReqAmt = cashDailyOut;
				}
			}

			int availableAmt = 0; //剩余可用钞量
			int dayAvgWdr = 0; //日均取款量
			int dayAvgDep = 0; //日均存款量
			int availableDays = 0; //剩余可用天数
			if (addnotesPlanDetail.getAvailableAmt() != null) {
				availableAmt = addnotesPlanDetail.getAvailableAmt();
			}

			List<Map<String, Object>> devAvgList = biztxlogInitInnerResource.qryDevDayAvgAmt(devNo, CalendarUtil.getFixedDate(CalendarUtil.getSysTimeYMD(), -30));
			if (devAvgList != null && devAvgList.size() > 0 && devAvgList.get(0) != null) {
				Map devAvgMap = devAvgList.get(0);
				dayAvgWdr = (int) StringUtil.objectToDouble(devAvgMap.get("dayAvgWdr"));
				dayAvgDep = (int) StringUtil.objectToDouble(devAvgMap.get("dayAvgDep"));

				if (cycleFlag == 0) {
					availableDays = dayAvgWdr > 0 ? availableAmt / dayAvgWdr : -1;
				} else if (DevCatalog.CRS.getId() == catalogNo && cycleFlag == 1) {
					//查询设备安全区间,[次日最大现金流出量，钞箱最大限额-次日最大现金流入量]
					int[] threshold = this.predictThresholds4Circle(devBaseInfo2SimpleInfo(devBaseInfo), predictValue);
					if (threshold != null && threshold.length == 2) {
						//大于上限
						if (threshold[1] < availableAmt) {//剩余可用钞量>钞箱最大限额-次日最大现金流入
							availableDays = dayAvgDep > 0 ? (devBaseInfo.getDevStantardSize() - availableAmt) / dayAvgDep : -1;
							//小于下限
						} else if (threshold[0] > availableAmt) {//剩余可用钞量小于次日现金最大流出量
							availableDays = dayAvgWdr > 0 ? availableAmt / dayAvgWdr : -1;
						} else {//安全区间内处理
							if (dayAvgWdr > dayAvgDep) {
								availableDays = availableAmt / (dayAvgWdr - dayAvgDep);
							} else if (dayAvgWdr < dayAvgDep) {
								availableDays = (devBaseInfo.getDevStantardSize() - availableAmt) / dayAvgDep;
							} else {
								availableDays = -1;
							}
						}
					}
				}
			}
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "金额修正成功！");
			retMap.put("cashReqAmt", cashReqAmt);
			retMap.put("availableDays", availableDays);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "金额修正失败!");
			log.error("modAddnotesPredict Fail: ", e);
		}

		return retMap;
	}

	/**
	 * 加钞策略调整
	 *
	 * @param addnotesPlanDetail
	 * @return planPredictAmt
	 */
	@Override
	public int adjustAddnotesPredict(AddnotesPlanDetail addnotesPlanDetail, String addnotesPlanNo, double cashReqAmt, int availableDays) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		int planPredictAmt = 0;
		try {
			String devNo = addnotesPlanDetail.getDevNo();

			DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectDetailByPrimaryKey(devNo);
			if (devBaseInfo == null) {
				retMap.put("retMsg", "金额预测异常:设备[" + devNo + "]不存在 !");
				return 0;
			}
			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
			if (addnotesPlan == null) {
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return 0;
			}

			String planAddnotesDate = addnotesPlan.getPlanAddnotesDate();
			String clrCenterNo = addnotesPlan.getClrCenterNo();

			//计划加钞量(按策略调整)
			planPredictAmt = amtPredictService.getNextEstAddnotesAmt(devBaseInfo, planAddnotesDate, clrCenterNo, cashReqAmt);

			addnotesPlanDetail.setCashReqAmt(new Double(cashReqAmt).intValue());
			addnotesPlanDetail.setPlanPredictAmt(planPredictAmt);
			addnotesPlanDetail.setPlanAddnotesAmt(planPredictAmt);
			addnotesPlanDetail.setUseDays(availableDays);

			addnotesPlanDetailMapper.updateByPrimaryKeySelective(addnotesPlanDetail);


		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "金额调整失败!");
			log.error("adjustAddnotesPredict Fail: ", e);
		}

		return planPredictAmt;
	}

	/**
	 * 加钞值校验
	 * @param devNo
	 * @param addnotesDate
	 * @param useDays
	 * @param predictAmt
	 * @return
	 */
	@Override
	public int compareAddnotesAmount(String devNo, String addnotesDate, int useDays, int predictAmt) {
		//String selectNormalAmountSql = "select t.normal_addnotes_amount from normal_addnotes_amount_info t where t.dev_no = '" + devNo + "' order by t.mod_date desc";

		//JSONObject jsonObject = new JSONObject();

		try {
			List<Integer> list = new ArrayList<>();
			//统计正常加钞值
			double upCoefficient = Double.parseDouble(addnotesAmountMapper.getSysParamValue("compareMaxAmountUp"));
			if(upCoefficient == 0) {
				upCoefficient = 1.1;
			}
			double drawCoefficient = Double.parseDouble(addnotesAmountMapper.getSysParamValue("compareMinAmountDraw"));
			if(drawCoefficient == 0) {
				drawCoefficient = 0.9;
			}
			//jsonObject.put("predictAmtBefore", predictAmt);

			log.debug("获取正常值开始");
			List<Map<String, Integer>> normalAmountList = addnotesAmountMapper.getNormalAmount(devNo);
			if(normalAmountList.size() > 0) {
				int normalAmount = normalAmountList.get(0).get("normal_addnotes_amount");
				int normalAmountSum = normalAmount * useDays;
				log.debug("正常值="+normalAmountSum);
				//jsonObject.put("amountNormal", normalAmountSum);
				if(normalAmountSum > 0) {
					list.add(normalAmountSum);
				}
			}
			log.debug("获取正常值结束");

			//统计上两年同期的加钞平均值
			log.debug("获取两年同期的加钞平均值开始");
			int addnotesAmountUpTwoYears = getAddnotesAmountHistory(devNo, addnotesDate, useDays, -24);
			if(addnotesAmountUpTwoYears > 0) {
				log.debug("两年同期的加钞平均值 = " + addnotesAmountUpTwoYears);
				list.add(addnotesAmountUpTwoYears);
			}
			log.debug("获取两年同期的加钞平均值结束");


			//统计上一年同期的加钞平均值
			log.debug("获取一年同期的加钞平均值开始");
			int addnotesAmountUpOneYears = getAddnotesAmountHistory(devNo, addnotesDate, useDays, -12);
			if(addnotesAmountUpOneYears > 0) {
				log.debug("一年同期的加钞平均值 = " + addnotesAmountUpOneYears);
				list.add(addnotesAmountUpOneYears);
			}
			log.debug("获取一年同期的加钞平均值结束");


			//统计同期环比的加钞平均值
			log.debug("获取同期环比的加钞平均值开始");
			//String selectAddnotesAmountSql = "select t.dev_no,t.addnotes_plan_amount,t.cash_out_time from addnotes_data t where t.dev_no = ? and t.cash_out_time >= ? "
			//		+ "and t.addnotes_plan_amount > 0 and t.dev_back_amount > 0 order by t.cash_out_time";
			String startTime = TMSDateUtil.getFixedDate(addnotesDate, -(useDays+2)) + " 00:00:00";
			int addnotesAmount = 0;
			String cashDate = "";
			int amountRoundDate = 0;
			List<Map<String, String>> amountRoundDateList = addnotesAmountMapper.getAddnotesAmount(devNo, startTime);
			if(amountRoundDateList.size() > 0) {
				addnotesAmount = Integer.parseInt(amountRoundDateList.get(0).get("addnotes_plan_amount"));
				cashDate = amountRoundDateList.get(0).get("cash_out_time").substring(0, 10);
				int useDaysNum = CalendarUtil.getymdSubTime(addnotesDate, cashDate).intValue();//大于0
				if(useDaysNum > 0) {
					amountRoundDate = addnotesAmount/useDaysNum*useDays;
				}
			}
			if(amountRoundDate > 0) {
				log.debug("环比的加钞平均值 = " + amountRoundDate);
				list.add(amountRoundDate);
			}
			log.debug("获取同期环比的加钞平均值结束");


			if(list.size() >= 2) {
				int maxAddnotesAmount = list.get(0);
				int minAddnotesAmount = list.get(0);
				for(int i = 1;i < list.size();i++) {
					if(list.get(i) < minAddnotesAmount) {
						minAddnotesAmount = list.get(i);
					}
					if(list.get(i) > maxAddnotesAmount) {
						maxAddnotesAmount = list.get(i);
					}
				}
				minAddnotesAmount = (int) (minAddnotesAmount * drawCoefficient);
				if(predictAmt < minAddnotesAmount) {
					predictAmt = minAddnotesAmount;
				}
				maxAddnotesAmount = (int) (maxAddnotesAmount * upCoefficient);
				if(predictAmt > maxAddnotesAmount) {
					predictAmt = maxAddnotesAmount;
				}
			}
			//jsonObject.put("predictAmt", predictAmt);
			log.debug("比较得到加钞值="+predictAmt);
		} catch (Exception e) {
			log.error("对比加钞金额异常",e);
			return predictAmt;
			//return jsonObject;
		}
		return predictAmt;
		//return jsonObject;
	}

	/**
	 * 获取历史同期加钞金额
	 * @param devNo
	 * @param addnotesDate
	 * @param useDays
	 * @param months
	 * @return
	 */
	@Override
	public int getAddnotesAmountHistory(String devNo, String addnotesDate, int useDays,int months) {
		//统计上历史同期的加钞金额
		try {

			//获取同期时间段开始日期10天以前的，获取同期结束日期10天以后的，这个时间段内的装卸钞数据
			String startTime = TMSDateUtil.getFixedDate(TMSDateUtil.getFixedMonth(addnotesDate, months), -10) + " 00:00:00";
			String endTime = TMSDateUtil.getFixedMonth(TMSDateUtil.getFixedDate(addnotesDate, useDays + 10), months) + " 00:00:00";
			List<Map<String, String>> addnotesAmountList = addnotesAmountMapper.getAddnotesAmountB(devNo, startTime, endTime);

			int addnotesAmount = 0;
			String cashDate = "";
			int addnotesAmountNext = 0;
			String cashDateNext = "";

			boolean flag = false;

			String cashDate1 = "";
			String cashDate2 = "";

			//同期的开始数据
			if(addnotesAmountList.size() > 0) {
				addnotesAmount = Integer.parseInt(addnotesAmountList.get(0).get("addnotes_plan_amount"));
				cashDate = addnotesAmountList.get(0).get("cash_out_time").substring(0, 10);
				flag = true;
			}
			if(flag) {
				//依次遍历出数据
				for(Map<String, String> map: addnotesAmountList) {
					//下个加钞记录的数据
					addnotesAmountNext = Integer.parseInt(map.get("addnotes_plan_amount"));
					cashDateNext = map.get("cash_out_time").substring(0, 10);

					//下个加钞记录前的同期开始时间
					int num1 = CalendarUtil.getymdSubTime(TMSDateUtil.getFixedMonth(addnotesDate, months), cashDateNext).intValue();//大于0
					if(num1 < 0 && "".equals(cashDate1)) {
						cashDate1 = cashDate;
					}

					//下个加钞记录的同期结束时间
					int num2 = CalendarUtil.getymdSubTime(TMSDateUtil.getFixedMonth(TMSDateUtil.getFixedDate(addnotesDate, useDays), months), cashDateNext).intValue();//大于0
					if(num2 < 0 && "".equals(cashDate2)) {
						cashDate2 = cashDateNext;
					}

					//将下个记录赋值给上个记录
					addnotesAmount = addnotesAmountNext;
					cashDate = cashDateNext;
				}
			}

			//根据获取到的同期开始和结束时间，产生历史同期的数据
			String startTimeSec = cashDate1 + " 00:00:00";
			String endTimeSec = cashDate2 + " 00:00:00";
			List<Map<String, String>> addnotesAmountListSec = addnotesAmountMapper.getAddnotesAmountB(devNo, startTimeSec, endTimeSec);
			int addnotesAmountSum = 0;
			int useDays1 = CalendarUtil.getymdSubTime(cashDate2, cashDate1).intValue();//大于0
			for (Map<String, String> map: addnotesAmountListSec) {
				addnotesAmountSum += Integer.parseInt(map.get("addnotes_plan_amount"));
			}
			if(useDays1 == 0) {
				return 0;
			}
			return addnotesAmountSum/useDays1*useDays;

		} catch (Exception e) {
			log.error("获取历史平均加钞值异常",e);
			return 0;
		}

	}

	/**
	 * 查询维护型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 维护型设备列表
	 */
	@Override
	public Map<String, Object> qryAddnotesPlanDevsForMaintain(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			log.debug("[qryAddnotesPlanDevsForMaintain]:" + createJsonString);
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String orgNo = StringUtil.parseString(params.get("orgNo"));

			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);

			if (addnotesPlan == null) {
				log.error("加钞计划[" + addnotesPlanNo + "]不存在!");
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			List<DevForChooseDTO> retList = devChooseService.qryAddnotesPlanDevsForMaintain(addnotesPlan, orgNo);

			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞设备列表异常!");
			log.error("qryAddnotesPlanDevsForMaintain Fail: ", e);
		}
		return retMap;
	}

	/**
	 * 查询决定型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 决定型设备列表
	 */
	@Override
	public Map<String, Object> qryAddnotesPlanDevsForCash(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			log.debug("[qryAddnotesPlanDevsForKey]:" + createJsonString);
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String orgNo = StringUtil.parseString(params.get("orgNo"));

			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);

			if (addnotesPlan == null) {
				log.error("加钞计划[" + addnotesPlanNo + "]不存在!");
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			List<DevForChooseDTO> retList = devChooseService.qryAddnotesPlanDevsForKey(addnotesPlan, orgNo);

			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞设备列表异常!");
			log.error("qryAddnotesPlanDevsForKey Fail: ", e);
		}
		return retMap;
	}

	/**
	 * 查询预测型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 预测型设备列表
	 */
	@Override
	public Map<String, Object> qryAddnotesPlanDevsForPredict(String createJsonString) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			log.debug("[qryAddnotesPlanDevsForPredict]:" + createJsonString);
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String orgNo = StringUtil.parseString(params.get("orgNo"));

			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);

			if (addnotesPlan == null) {
				log.error("加钞计划[" + addnotesPlanNo + "]不存在!");
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			List<DevForChooseDTO> retList = devChooseService.qryAddnotesPlanDevsForPredict(addnotesPlan, orgNo);

			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞设备列表异常!");
			log.error("qryAddnotesPlanDevsForPredict Fail: ", e);
		}
		return retMap;
	}

	/**
	 * 查询计划型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 计划型设备列表
	 */
	@Override
	public Map<String, Object> qryAddnotesPlanDevsForRunPlan(String createJsonString) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			log.debug("[qryAddnotesPlanDevsForRunPlan]:" + createJsonString);
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String orgNo = StringUtil.parseString(params.get("orgNo"));

			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);

			if (addnotesPlan == null) {
				log.error("加钞计划[" + addnotesPlanNo + "]不存在!");
				retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			List<DevForChooseDTO> retList = devChooseService.qryAddnotesPlanDevsForRunPlan(addnotesPlan, orgNo);

			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询信息成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询加钞设备列表异常!");
			log.error("qryAddnotesPlanDevsForRunPlan Fail: ", e);
		}
		return retMap;
	}

	/**
	 * 保存加钞设备
	 *
	 * @param createJsonString modOpNo:最近修改人，addnotesPlanNo:加钞计划编号，devList:设备列表，devNo:设备编号
	 *                         lineNo:线路号，keyEvent:决定型事件，keyEventDetail:决定型事件描述，chsEstScore:预测型加钞优先度
	 *                         chsAuxScore:辅助型加钞优先度
	 * @return 状态码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addAddnotesPlanDevs(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);
		String modOpNo = StringUtil.parseString(params.get("modOpNo"));
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
		String devListString = StringUtil.parseString(params.get("devList"));

		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}
		addnotesPlan.setModOpNo(modOpNo);
		addnotesPlan.setModDate(CalendarUtil.getSysTimeYMD());
		addnotesPlan.setModTime(CalendarUtil.getSysTimeHMS());
		addnotesPlan.setStatus(AddnotesPlanStatus.DEV_CHOOSE.getId());

		List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);
		List<String> devNoList = new ArrayList<String>();
		for (AddnotesPlanDetail addnotesPlanDetail : addnotesPlanDetails) {
			devNoList.add(addnotesPlanDetail.getDevNo());
		}

		if (devListString != null && !"".equals(devListString)) {
			JSONArray jsonArray = JSONUtil.parseJSONArray(devListString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonEntity = jsonArray.getJSONObject(i);
				String devNo = jsonEntity.getString("devNo");
				String lineNo = jsonEntity.getString("lineNo");
				String keyEvent = jsonEntity.getString("keyEvent");
				String keyEventDetail = jsonEntity.getString("keyEventDetail");
				BigDecimal chsEstScore = jsonEntity.getBigDecimal("chsEstScore");
				BigDecimal chsAuxScore = jsonEntity.getBigDecimal("chsAuxScore");

				//add
				AddnotesPlanDetail addnotesPlanDetail = new AddnotesPlanDetail();
				addnotesPlanDetail.setAddnotesPlanNo(addnotesPlanNo);
				addnotesPlanDetail.setDevNo(devNo);
				addnotesPlanDetail.setKeyEvent(keyEvent);
				addnotesPlanDetail.setKeyEventDetail(keyEventDetail);
				addnotesPlanDetail.setStatus(1);
				addnotesPlanDetail.setChsEstScore(chsEstScore);
				addnotesPlanDetail.setChsAuxScore(chsAuxScore);

				if (lineNo == null || "".equals(lineNo)) {
//					DevBaseInfo devBaseInfo = devBaseInfoMapper.selectDetailByPrimaryKey(devNo);
					DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectDetailByPrimaryKey(devNo);
					lineNo = devBaseInfo.getAddnotesLine();
				}
				addnotesPlanDetail.setLineNo(lineNo);

				if (devNoList.contains(addnotesPlanDetail.getDevNo())) {
					addnotesPlanDetailMapper.updateByPrimaryKeySelective(addnotesPlanDetail);
				} else {
					addnotesPlanDetailMapper.insert(addnotesPlanDetail);
				}
			}

			addnotesPlan.setPlanDevCount(jsonArray.size());
		}

		addnotesPlanMapper.updateByPrimaryKeySelective(addnotesPlan);
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "更新信息成功！");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "更新信息异常!");
//			log.error("addAddnotesPlanDevs Fail: ", e);
//		}
		return retMap;
	}

	/**
	 * 修改加钞设备（设备调整）
	 *
	 * @param createJsonString modOpNo:最近修改人，addnotesPlanNo:加钞计划编号，devList:设备列表，devNo:设备编号
	 * @return 状态码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> modAddnotesPlanDevs(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);
		String modOpNo = StringUtil.parseString(params.get("modOpNo"));
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));

		//验证当前加钞计划是否存在，不存在则返回错误信息
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}

		//当前用户作为加钞信息修改人，当前时间作为修改时间
		addnotesPlan.setModOpNo(modOpNo);
		addnotesPlan.setModDate(CalendarUtil.getSysTimeYMD());
		addnotesPlan.setModTime(CalendarUtil.getSysTimeHMS());

		String devListString = StringUtil.parseString(params.get("devList"));
		if (devListString != null && !"".equals(devListString)) {
			List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);
			List<String> devOldList = new ArrayList<String>();
			List<String> devNewList = new ArrayList<String>();
			List<AddnotesPlanDetail> addnotesPlanDetailList = new ArrayList<AddnotesPlanDetail>();

			//当前加钞明细表中的设备列表--devOldList
			for (AddnotesPlanDetail addnotesPlanDetail : addnotesPlanDetails) {
				devOldList.add(addnotesPlanDetail.getDevNo());
			}

			//前端传来的页面已选设备列表--devNewList 及 已选设备对应的加钞计划明细信息列表--addnotesPlanDetailList
			JSONArray jsonArray = JSONUtil.parseJSONArray(devListString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonEntity = jsonArray.getJSONObject(i);
				String devNo = jsonEntity.getString("devNo");
				devNewList.add(devNo);
				String lineNo = jsonEntity.getString("lineNo");
				String keyEvent = jsonEntity.getString("keyEvent");
				String keyEventDetail = jsonEntity.getString("keyEventDetail");
				BigDecimal chsEstScore = jsonEntity.getBigDecimal("chsEstScore");
				BigDecimal chsAuxScore = jsonEntity.getBigDecimal("chsAuxScore");

				AddnotesPlanDetail addnotesPlanDetail = new AddnotesPlanDetail();
				addnotesPlanDetail.setAddnotesPlanNo(addnotesPlanNo);
				addnotesPlanDetail.setDevNo(devNo);
				addnotesPlanDetail.setKeyEvent(keyEvent);
				addnotesPlanDetail.setKeyEventDetail(keyEventDetail);
				addnotesPlanDetail.setStatus(1);
				addnotesPlanDetail.setChsEstScore(chsEstScore);
				addnotesPlanDetail.setChsAuxScore(chsAuxScore);

				//如果当前设备的线路编号为空，从设备基础信息表DEV_BASE_INFO查询设备的线路号
				if (lineNo == null || "".equals(lineNo)) {
					DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectDetailByPrimaryKey(devNo);
					lineNo = devBaseInfo.getAddnotesLine();
				}
				addnotesPlanDetail.setLineNo(lineNo);
				addnotesPlanDetailList.add(addnotesPlanDetail);
			}

			//遍历devOldList，只在devOldList存在的设备从加钞明细表中删除
			for (String devNo : devOldList) {
				if (!devNewList.contains(devNo)) {
					//delete
					AddnotesPlanDetailKey addnotesPlanDetailKey = new AddnotesPlanDetailKey();
					addnotesPlanDetailKey.setAddnotesPlanNo(addnotesPlanNo);
					addnotesPlanDetailKey.setDevNo(devNo);
					addnotesPlanDetailMapper.deleteByPrimaryKey(addnotesPlanDetailKey);
				}
			}

			//遍历devNewList，只在devNewList存在的设备添加进加钞明细表中
			for (AddnotesPlanDetail addnotesPlanDetail : addnotesPlanDetailList) {
				if (!devOldList.contains(addnotesPlanDetail.getDevNo())) {
					//add
					addnotesPlanDetailMapper.insertSelective(addnotesPlanDetail);
				}
			}
			//加钞计划表中的设备台数为修改后的设备数量
			addnotesPlan.setPlanDevCount(devNewList.size());
		}
		//修改加钞计划状态为1-已选择设备，保证可以重新调用金额预测接口
		addnotesPlan.setStatus(1);
		addnotesPlanMapper.updateByPrimaryKeySelective(addnotesPlan);
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "更新信息成功！");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "更新信息异常!");
//			log.error("modAddnotesPlanDevs Fail: ", e);
//		}
		return retMap;
	}

	/**
	 * 查询加钞计划
	 *
	 * @param string pageSize，curPage，clrCenterNo，planStartDate，planEndDate，status，linrNo，urgencyFlag，genOpNo
	 * @return addnotesPlanList，totalRow，totalPage
	 */
	@Override
	public Map<String, Object> qryAddnotesPlan(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		log.info("------------[qryAddnotesPlan]AddnotesPlanService-------------");
		try {
			JSONObject params = JSONUtil.parseJSONObject(string);
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			String planStartDate = StringUtil.parseString(params.get("planStartDate"));
			String planEndDate = StringUtil.parseString(params.get("planEndDate"));
			String lineNo = StringUtil.parseString(params.get("lineNo"));
			Integer status = StringUtil.objectToInt(params.get("status"));
			Integer urgencyFlag = StringUtil.objectToInt(params.get("urgencyFlag"));
			String genOpNo = StringUtil.parseString(params.get("genOpNo"));
			String orgNo = StringUtil.parseString(params.get("orgNo"));

			Integer orgGradeNo = addnotesPlanMapper.qryOrgGradeNoByOrgNo(orgNo);
			Integer clrCenterFlag = addnotesPlanMapper.qryClrCenterFlag(orgNo);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("planStartDate", planStartDate);
			paramMap.put("planEndDate", planEndDate);
			paramMap.put("lineNo", lineNo);
			paramMap.put("status", status);
			paramMap.put("urgencyFlag", urgencyFlag);
			paramMap.put("genOpNo", genOpNo);
			paramMap.put("orgGradeNo", orgGradeNo);
			paramMap.put("clrCenterFlag", clrCenterFlag);
			paramMap.put("orgNo", orgNo);

			int totalRow = addnotesPlanMapper.qryTotalRowPlan(paramMap);
			int totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;

			List<AddnotesPlan> retAddnotesPlans = addnotesPlanMapper.qryAddnotesPlan(paramMap);

			Map<String, String> addnotesLineMap = addnotesLineService.getAddnotesLineMap();

			List<AddnotesPlanDTO> retList = new ArrayList<AddnotesPlanDTO>();
			for (AddnotesPlan addnotesPlan : retAddnotesPlans) {
				AddnotesPlanDTO addnotesPlanDTO = AddnotesPlanDTOConverter.INSTANCE.domain2dto(addnotesPlan);
				List<AddnotesLineDTO> addnoteLineList = new ArrayList<AddnotesLineDTO>();

				if (addnotesPlan.getLineNo() != null) {
					String lineNoString = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
					String[] stringArr = lineNoString.split(",");

					for (String lineNoTemp : stringArr) {
						AddnotesLineDTO addnotesLineDTO = new AddnotesLineDTO();
						addnotesLineDTO.setLineNo(lineNoTemp.trim());

						String lineName = addnotesLineMap.get(lineNoTemp.trim());
						addnotesLineDTO.setLineName(lineName);

						addnoteLineList.add(addnotesLineDTO);

					}
					addnotesPlanDTO.setLineList(addnoteLineList);
				}
				retList.add(addnotesPlanDTO);
			}
			retMap.put("addnotesPlanList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", RetCodeEnum.SUCCEED.getTip());

			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "加钞规划信息查询异常!");
			log.error("qryAddnotesPlan Fail: ", e);
			return retMap;
		}
	}

	/**
	 * 添加加钞计划
	 *
	 * @param string genOpNo,clrCenterNo,clrCenterName,planAddnotesDate，groupMode，groupNo，awayFlag，isUrgency
	 * @return addnotesPlanNo
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addAddnotesPlan(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

//		try {
		JSONObject params = JSONUtil.parseJSONObject(string);
		String genOpNo = StringUtil.parseString(params.get("genOpNo"));
		String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
		String planAddnotesDate = StringUtil.parseString(params.get("planAddnotesDate"));

		String groupNo = StringUtil.parseString(params.get("groupNo"));
		String note = StringUtil.parseString(params.get("awayFlag"));
		Integer isUrgency = StringUtil.objectToInteger(params.get("isUrgency"));


		ClrCenterTable clrCenterTable = clrCenterInnerResource.selectByPrimaryKey(clrCenterNo); //1-自动 2-手动

		if (CalendarUtil.compare2Dates(planAddnotesDate, CalendarUtil.getSysTimeYMD()) < 0) {
			retMap.put("retMsg", "加钞信息添加失败!，不允许新增今日之前的加钞计划！");
			return retMap;
		}

		String planAddnotesDateTmp = planAddnotesDate.replace("-", ""); //YYYYMMDD
		StringBuffer addnotesPlanNoBuffer = new StringBuffer("JCJH").append(clrCenterNo).append(planAddnotesDateTmp);
		//获取最大顺序号
		String maxNo = addnotesPlanMapper.selectMaxNo(clrCenterNo, planAddnotesDate);
		if (maxNo == null || "".equals(maxNo)) {
			addnotesPlanNoBuffer.append("01");
		} else {
			String tmp = maxNo;
			int maxSeqNo = Integer.parseInt(tmp.substring(tmp.length() - 2));
			if (maxSeqNo >= 99) {
				return null;
			}
			addnotesPlanNoBuffer.append(String.format("%02d", maxSeqNo + 1));
		}
		String addnotesPlanNo = addnotesPlanNoBuffer.toString();

		if (addnotesPlanNo == null) {
			retMap.put("retMsg", "今日新增加钞计划数量已超过上限，不允许再新增!");
			return retMap;
		}
		String genDate = CalendarUtil.getSysTimeYMD(); // 计划生成日期YYYY-MM-DD
		String genTime = CalendarUtil.getSysTimeHMS(); // 计划生成时间hh:mm:ss

		AddnotesPlan addnotesPlan = new AddnotesPlan();
		addnotesPlan.setAddnotesPlanNo(addnotesPlanNo);
		addnotesPlan.setClrCenterNo(clrCenterNo);
		addnotesPlan.setPlanAddnotesDate(planAddnotesDate);
		addnotesPlan.setPlanGenMode(2);
		addnotesPlan.setPlanGenOpNo(genOpNo);
		addnotesPlan.setPlanGenDate(genDate);
		addnotesPlan.setPlanGenTime(genTime);
		addnotesPlan.setLineMode(clrCenterTable.getLineMode());
		addnotesPlan.setLineNo(groupNo);
		addnotesPlan.setStatus(AddnotesPlanStatus.DEV_NOTCHOOSE.getId());
		addnotesPlan.setIsUrgency(isUrgency);
		addnotesPlan.setNote(note);
		// 计划所含设备台数和加钞总额
		addnotesPlan.setPlanDevCount(0);
		addnotesPlan.setPlanAddnotesAmt(0L);

		addnotesPlanMapper.insertSelective(addnotesPlan);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "添加成功！");
		retMap.put("addnotesPlanNo", addnotesPlanNo);
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "加钞信息添加异常!");
//			log.error("addAddNotesPlan Fail: ", e);
//		}
		return retMap;
	}

	/**
	 * 查询加钞计划详细信息
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @return addnotesPlan，detailList
	 */
	@Override
	public Map<String, Object> qryAddnotesPlanDetail(String addnotesPlanNo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
		retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());

		try {
			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
			if (addnotesPlan == null) {
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			AddnotesPlanDTO addnotesPlanDTO = AddnotesPlanDTOConverter.INSTANCE.domain2dto(addnotesPlan);
			Map<String, String> addnotesLineMap = addnotesLineService.getAddnotesLineMap();
			if (addnotesPlan.getLineNo() != null) {
				List<AddnotesLineDTO> addnoteLineList = new ArrayList<AddnotesLineDTO>();
				String lineNoString = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
				String[] stringArr = lineNoString.split(",");

				for (String lineNo : stringArr) {
					AddnotesLineDTO addnotesLineDTO = new AddnotesLineDTO();
					String lineName = addnotesLineMap.get(lineNo.trim());
					addnotesLineDTO.setLineNo(lineNo.trim());
					addnotesLineDTO.setLineName(lineName);

					addnoteLineList.add(addnotesLineDTO);
				}
				addnotesPlanDTO.setLineList(addnoteLineList);
			}

			List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);
			List<AddnotesPlanDetailDTO> detailList = new ArrayList<>();
			for (int i = 0; i < addnotesPlanDetails.size(); i++) {
				AddnotesPlanDetail addnotesPlanDetail = addnotesPlanDetails.get(i);
				AddnotesPlanDetailDTO addnotesPlanDetailDTO = AddnotesPlanDetailDTOConverter.INSTANCE.domain2dto(addnotesPlanDetail);

				String lastAddnoteDate = addnotesPlanDetail.getLastAddnoteDate();
				int notAddCashDay = -1;
				if (lastAddnoteDate != null && !"".equals(lastAddnoteDate)) {
					notAddCashDay = CalendarUtil.getSubDate(CalendarUtil.getSysTimeYMD(), lastAddnoteDate);
				}
				String notAddCashDays = String.valueOf(notAddCashDay);


				addnotesPlanDetailDTO.setNotAddCashDays(notAddCashDays);
				log.debug(Optional.ofNullable(addnotesPlanDetail.getDevLineName()).orElse("").trim());

//				addnotesPlanDetailDTO.setDevLineName(addnotesLineMap.get(Optional.ofNullable(addnotesPlanDetail.getDevLineName()).orElse("").trim()));
				detailList.add(addnotesPlanDetailDTO);
			}

			retMap.put("addnotesPlan", addnotesPlanDTO);
			retMap.put("detailList", detailList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", RetCodeEnum.SUCCEED.getTip());
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "加钞详细信息查询异常");
			log.error("qryAddNotesPlanDetail Fail: ", e);
		}

		return retMap;
	}

	/**
	 * 删除加钞计划
	 *
	 * @param string addnotesPlanNo
	 * @return 状态码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> delAddnotesPlan(String string) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
		log.debug("[delAddNotesPlan]:" + string);
		JSONObject params = JSONUtil.parseJSONObject(string);
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));

		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}

		if (addnotesPlan.getStatus() >= AddnotesPlanStatus.OUTDATE.getId()) {
			retMap.put("retMsg", "该加钞计划已提交，不允许被删除!");
			return retMap;
		}

		addnotesPlanDetailMapper.deleteByAddnotesPlanNo(addnotesPlanNo);

		addnotesPlanGroupMapper.deleteByAddnotesPlanNo(addnotesPlanNo);

		addnotesPlanMapper.deleteByPrimaryKey(addnotesPlanNo);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "删除信息成功！");

//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "删除加钞信息异常!");
//			log.error("delAddNotesPlan Fail: ", e);
//		}
		return retMap;
	}

	/**
	 * 查询加钞计划内容（for设备调整、金额调整）
	 *
	 * @param createJsonString addnotesPlanNo；加钞计划编号
	 * @return AddnotesPlanDetailForDevDTO：加钞计划内容（for设备调整、金额调整），entity：加钞计划表的一部分内容
	 */
	@Override
	public Map<String, Object> qryAddnotesPlanDetailForDev(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));

			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
			if (addnotesPlan == null) {
				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
				return retMap;
			}

			//查询计划加钞设备列表
			List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);

			List<AddnotesPlanDetailForDevDTO> detailForDevs = new ArrayList<>();

			int availableAmt = 0; //剩余可用钞量
			int dayAvgWdr = 0; //日均取款量
			int dayAvgDep = 0; //日均存款量
			int notAddCashDays = -1;//距离上次加钞天数
			for (AddnotesPlanDetail addnotesPlanDetail : addnotesPlanDetails) {
				if (addnotesPlanDetail.getDevStatusDevNo() != null) {
					if (addnotesPlanDetail.getAvailableAmt() != null) {
						availableAmt = addnotesPlanDetail.getAvailableAmt();
					}
					if (addnotesPlanDetail.getLastAddnoteDate() != null && !"".equals(addnotesPlanDetail.getLastAddnoteDate())) {
						notAddCashDays = CalendarUtil.getSubDate(CalendarUtil.getSysTimeYMD(), addnotesPlanDetail.getLastAddnoteDate());
					}
				}

				List<Map<String, Object>> devAvgList = biztxlogInitInnerResource.qryDevDayAvgAmt(addnotesPlanDetail.getDevNo(), CalendarUtil.getFixedDate(CalendarUtil.getSysTimeYMD(), -30));
				if (devAvgList != null && devAvgList.size() > 0 && devAvgList.get(0) != null) {
					Map devAvgMap = devAvgList.get(0);
					dayAvgWdr = (int) StringUtil.objectToDouble(devAvgMap.get("dayAvgWdr"));
					dayAvgDep = (int) StringUtil.objectToDouble(devAvgMap.get("dayAvgDep"));
				}

				AddnotesPlanDetailForDevDTO addnotesPlanDetailForDev = new AddnotesPlanDetailForDevDTO();
				addnotesPlanDetailForDev.setDevNo(addnotesPlanDetail.getDevNo());

				addnotesPlanDetailForDev.setKeyEvent(addnotesPlanDetail.getKeyEvent());
				addnotesPlanDetailForDev.setChsAuxScore(addnotesPlanDetail.getChsAuxScore() == null ? null : addnotesPlanDetail.getChsAuxScore().longValue());
				addnotesPlanDetailForDev.setChsEstScore(addnotesPlanDetail.getChsEstScore() == null ? null : addnotesPlanDetail.getChsAuxScore().longValue());
				addnotesPlanDetailForDev.setKeyEventDetail(addnotesPlanDetail.getKeyEventDetail());
				addnotesPlanDetailForDev.setPlanAddnotesAmt(addnotesPlanDetail.getPlanAddnotesAmt());
				addnotesPlanDetailForDev.setLineNo(addnotesPlanDetail.getLineNo());

				addnotesPlanDetailForDev.setAvailableAmt(availableAmt);
				addnotesPlanDetailForDev.setDayAvgAmt(dayAvgWdr);
				addnotesPlanDetailForDev.setDayAvgDep(dayAvgDep);
				addnotesPlanDetailForDev.setUseDays(addnotesPlanDetail.getUseDays());
				addnotesPlanDetailForDev.setNotAddCashDays(notAddCashDays);

				addnotesPlanDetailForDev.setAddNotesLineName((addnotesPlanDetail.getAddnoteLine() == null || addnotesPlanDetail.getAddnoteLine().getLineName() == null) ? "" : addnotesPlanDetail.getAddnoteLine().getLineName());

				if (addnotesPlanDetail.getDevNo() != null) {
					try {
						addnotesPlanDetailForDev.setDevCatalogName(addnotesPlanDetail.getOrgName());
					} catch (Exception e) {
						addnotesPlanDetailForDev.setDevCatalogName("");
					}
					addnotesPlanDetailForDev.setOrgName((addnotesPlanDetail.getSysOrgNo() == null || addnotesPlanDetail.getOrgName() == null) ? "" : addnotesPlanDetail.getOrgName());
					addnotesPlanDetailForDev.setAddress(addnotesPlanDetail.getAddress());
					addnotesPlanDetailForDev.setDevStantardSize(addnotesPlanDetail.getDevStantardSize());
				}
				detailForDevs.add(addnotesPlanDetailForDev);
			}

			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("addnotesPlanNo", addnotesPlan.getAddnotesPlanNo());
			entity.put("clrCenterNo", addnotesPlan.getClrCenterNo());
			entity.put("clrCenterName", addnotesPlan.getClrCenterName() == null ? "" : addnotesPlan.getClrCenterName());
			entity.put("planAddnotesDate", addnotesPlan.getPlanAddnotesDate());
			entity.put("planStartTime", addnotesPlan.getPlanStartTime());
			entity.put("lastestEndTime", addnotesPlan.getLastestEndTime());
			entity.put("planDevCount", addnotesPlan.getPlanDevCount());
			entity.put("planAddnotesAmt", addnotesPlan.getPlanAddnotesAmt());
			entity.put("planGenMode", addnotesPlan.getPlanGenMode());
			entity.put("planGenOpNo", addnotesPlan.getPlanGenOpNo());
			entity.put("planGenDate", addnotesPlan.getPlanGenDate());
			entity.put("planGenTime", addnotesPlan.getPlanGenTime());
			entity.put("status", addnotesPlan.getStatus());
			entity.put("submitOpNo", addnotesPlan.getSubmitOpNo());
			entity.put("submitDate", addnotesPlan.getSubmitDate());
			entity.put("modOpNo", addnotesPlan.getModOpNo());
			entity.put("modDate", addnotesPlan.getModDate());
			entity.put("modTime", addnotesPlan.getModTime());
			entity.put("linMode", addnotesPlan.getLineMode());
			entity.put("lineNo", addnotesPlan.getLineNo());
			entity.put("note", addnotesPlan.getNote());

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
			retMap.put("detailList", detailForDevs);
			retMap.put("addnotesPlan", entity);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "查询异常!");
			log.error("qryAddnotesPlanDetailForDev Fail: ", e);
		}
		return retMap;
	}

	/**
	 * 加钞计划退审
	 * 查询是否有该加钞计划，有则改为退回调整
	 *
	 * @param createJsonString 处理的计划编号：addnotesPlanNo 审核结果：opinion 当前用户编号：sysUserNo
	 * @return 状态码
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> addAddnotesRefuse(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
		String sysUserNo = StringUtil.parseString(params.get("sysUserNo"));
		String opinion = StringUtil.parseString(params.get("opinion"));
		Integer type = StringUtil.objectToInt(params.get("type"));

		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}

		addnotesPlan.setRefuseSuggestion(opinion);
		addnotesPlan.setAuditOpNo(sysUserNo);
		addnotesPlan.setAuditDate(CalendarUtil.getSysTimeYMD());
		addnotesPlan.setAuditTime(CalendarUtil.getSysTimeHMS());
		addnotesPlan.setStatus(AddnotesPlanStatus.RETURN_ADJUST.getId());

		addnotesPlanMapper.updateByPrimaryKey(addnotesPlan);

		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "计划退审成功！");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "计划退审异常!");
//			log.error("planToDispatch Fail: ", e);
//		}
		return retMap;
	}

	/**
	 * 默认加钞量
	 */
	private static final int DEFAULT_AMT_LIMIT = 200000;

	/**
	 * 更改连接时长后的restTemplate，用于ZJML的返回
	 *
	 * @return restTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(500000);
		requestFactory.setReadTimeout(300000);
		return new RestTemplate(requestFactory);
	}

	private RestTemplate fixedRestTemplate = restTemplate();


	/**
	 * 设备金额预测
	 *
	 * @param createJsonString modOpNo：最近修改人，addnotesPlanNo：加钞计划编号
	 * @return addnotesPlanDTO，detailList
	 */
	@Override
	public Map<String, Object> getAddnotesPlanCashReqAmt(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());

		try {
			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
			String modOpNo = StringUtil.parseString(params.get("modOpNo"));
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			int sumAmt = StringUtil.objectToInt(params.get("sumAmt"));
			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
			addnotesPlan.setStatus(AddnotesPlanStatus.AMOUNT_PREDICTED.getId());
			addnotesPlan.setPlanAddnotesAmt((long) sumAmt);
			addnotesPlan.setModOpNo(modOpNo);
			addnotesPlan.setModDate(CalendarUtil.getSysTimeYMD());
			addnotesPlan.setModTime(CalendarUtil.getSysTimeHMS());

			List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);

			addnotesPlanMapper.updateByPrimaryKeySelective(addnotesPlan);

			AddnotesPlanDTO addnotesPlanDTO = AddnotesPlanDTOConverter.INSTANCE.domain2dto(addnotesPlan);
			List<AddnotesLineDTO> addnoteLineList = new ArrayList<AddnotesLineDTO>();
			Map<String, String> addnotesLineMap = addnotesLineService.getAddnotesLineMap();
			if (addnotesPlan.getLineNo() != null) {
				String lineNoString = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
				String[] stringArr = lineNoString.split(",");
				for (String lineNo : stringArr) {
					AddnotesLineDTO addnotesLineDTO = new AddnotesLineDTO();
					addnotesLineDTO.setLineNo(lineNo.trim());
					String lineName = addnotesLineMap.get(lineNo.trim());
					addnotesLineDTO.setLineName(lineName);
					addnoteLineList.add(addnotesLineDTO);
				}
				addnotesPlanDTO.setLineList(addnoteLineList);
			}

			List<AddnotesPlanDetailDTO> detailList = AddnotesPlanDetailDTOConverter.INSTANCE.domain2dto(addnotesPlanDetails);
			for (int i = 0; i < addnotesPlanDetails.size(); i++) {
				String lastAddnoteDate = addnotesPlanDetails.get(i).getLastAddnoteDate();
				int notAddCashDay = -1;
				if (lastAddnoteDate != null && !"".equals(lastAddnoteDate)) {
					notAddCashDay = CalendarUtil.getSubDate(CalendarUtil.getSysTimeYMD(), lastAddnoteDate);
				}
				String notAddCashDays = String.valueOf(notAddCashDay);
				detailList.get(i).setNotAddCashDays(notAddCashDays);
			}

			retMap.put("addnotesPlan", addnotesPlanDTO);
			retMap.put("detailList", detailList);

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "金额预测成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "金额预测异常!");
			log.error("getAddnotesPlanCashReqAmt Fail: ", e);
		}

		return retMap;
	}


	/**
	 * 设备金额预测
	 *
	 * @param devBaseInfo
	 * modOpNo：最近修改人，addnotesPlanNo：加钞计划编号
	 * @return addnotesPlanDTO，detailList
	 */
//	@Override
//	public Map<String, Object> getAddnotesPlanCashReqAmt(String createJsonString) {
//		Map<String, Object> retMap = new HashMap<String, Object>();
//		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
//		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try {
//			JSONObject params = JSONUtil.parseJSONObject(createJsonString);
//			String modOpNo = StringUtil.parseString(params.get("modOpNo"));
//			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
//
//			AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
//			if (addnotesPlan == null) {
//				retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
//				return retMap;
//			}
//
//			String planAddnotesDate = addnotesPlan.getPlanAddnotesDate();
//			String clrCenterNo = addnotesPlan.getClrCenterNo();
//
//			addnotesPlan.setModOpNo(modOpNo);
//			addnotesPlan.setModDate(CalendarUtil.getSysTimeYMD());
//			addnotesPlan.setModTime(CalendarUtil.getSysTimeHMS());
//
//			List<AddnotesPlanDetail> addnotesPlanDetails = addnotesPlanDetailMapper.qryAddNotesPlanDetailByNo(addnotesPlanNo);
//
//			if (addnotesPlanDetails == null) {
//				retMap.put("retMsg", "加钞计划明细为空！金额预测失败！");
//				return retMap;
//			}
//			int sumAmt = 0;
//			double cashReqAmt = 0;
//			double clrPeriod = 0;
//			int planPredictAmt = 0;
//			double cashDailyNetOut;     //日均现金净付出量(取款量-存款量)
//			double cashDailyOut;        //日均现金付出量(取款量)

	//设备类型
//			Integer catalogNo = null;
//			//是否循环标志
//			Integer cycleFlag = null;
//			for (AddnotesPlanDetail addnotesPlanDetail : addnotesPlanDetails) {
//				String devNo = addnotesPlanDetail.getDevNo();
//				DevBaseInfo devBaseInfo = devBaseInfoResource.selectDetailByPrimaryKey(devNo);
//				if (devBaseInfo == null) {
//					retMap.put("retMsg", "金额预测异常:设备[" + devNo + "]不存在 !");
//					return retMap;
//				}
////				clrPeriod = devBaseInfo.getAwayFlag() == 1 ?1:devBaseInfo.getAddClrPeriod().doubleValue();//杭州银行版本使用加钞模块预测在行设备加钞金额，在行设备不存在清机周期
//				clrPeriod = addnotesPlanDetail.getAddClrPeriod().doubleValue();//加钞周期
//				catalogNo = addnotesPlanDetail.getDevCatalogTableNo();//设备类型编号 （编号 10001 CRS 10002 CDM 10003 ATM 10004 BSM）
//				cycleFlag = addnotesPlanDetail.getCycleFlag();  //设备是否开通循环功能：0，未开通；1，开通
//				//预测日均现金净付出额:元
////				cashDailyNetOut = amtPredictService.predictDailyCash(devNo, 0);
//				//预测日均现金取款额:元
////				cashDailyOut = amtPredictService.predictDailyCash(devNo, -1);
//				//取款现金模式(ATM或未开通循环的CRS)
////				if (catalogNo == DevCatalog.ATM.getId()
////						|| (cycleFlag != null && cycleFlag == CycleFlag.UNCYCLE.getId())) {
////					//现金需求量
////					cashReqAmt = cashDailyOut * clrPeriod;
////				} else if (catalogNo == DevCatalog.CRS.getId()
////						&& cycleFlag != null && cycleFlag == CycleFlag.ISCYCLE.getId()) {
////					//循环现金模式
////					//现金需求量
////					cashReqAmt = cashDailyNetOut * clrPeriod;
////					if (cashDailyOut > cashReqAmt) {
////						cashReqAmt = cashDailyOut;
////					}
////				}
//
//				int availableAmt = 0; //剩余可用钞量
//				int dayAvgWdr = 0; //日均取款量
//				int dayAvgDep = 0; //日均存款量
//				int availableDays = 0; //剩余可用天数
//				if (addnotesPlanDetail.getAvailableAmt() != null) {
//					availableAmt = addnotesPlanDetail.getAvailableAmt();
//				}
//
//				List<Map<String, Object>> devAvgList = biztxlogInitMapper.qryDevDayAvgAmt(devNo, CalendarUtil.getFixedDate(CalendarUtil.getSysTimeYMD(), -30));
//				if (devAvgList != null && devAvgList.size() > 0 && devAvgList.get(0) != null) {
//					Map devAvgMap = devAvgList.get(0);
//					dayAvgWdr = (int) StringUtil.objectToDouble(devAvgMap.get("dayAvgWdr"));
//					dayAvgDep = (int) StringUtil.objectToDouble(devAvgMap.get("dayAvgDep"));
//
//					if (cycleFlag == 0) {
//						availableDays = dayAvgWdr > 0 ? availableAmt / dayAvgWdr : -1;
//					} else if (DevCatalog.CRS.getId() == catalogNo && cycleFlag == 1) {
//						//查询设备安全区间,[次日最大现金流出量，钞箱最大限额-次日最大现金流入量]
//						int[] threshold = amtPredictService.predictThresholds4Circle(devBaseInfo2SimpleInfo(devBaseInfo));
//						if (threshold != null && threshold.length == 2) {
//							//大于上限
//							if (threshold[1] < availableAmt) {//剩余可用钞量>钞箱最大限额-次日最大现金流入
//								availableDays = dayAvgDep > 0 ? (devBaseInfo.getDevStantardSize() - availableAmt) / dayAvgDep : -1;
//								//小于下限
//							} else if (threshold[0] > availableAmt) {//剩余可用钞量小于次日现金最大流出量
//								availableDays = dayAvgWdr > 0 ? availableAmt / dayAvgWdr : -1;
//							} else {//安全区间内处理
//								if (dayAvgWdr > dayAvgDep) {
//									availableDays = availableAmt / (dayAvgWdr - dayAvgDep);
//								} else if (dayAvgWdr < dayAvgDep) {
//									availableDays = (devBaseInfo.getDevStantardSize() - availableAmt) / dayAvgDep;
//								} else {
//									availableDays = -1;
//								}
//							}
//						}
//					}
//				}
//
//				//计划加钞量(按策略调整)
//				planPredictAmt = amtPredictService.getNextEstAddnotesAmt(devBaseInfo, planAddnotesDate, clrCenterNo, cashReqAmt);
//
//				addnotesPlanDetail.setCashReqAmt(new Double(cashReqAmt).intValue());
//				addnotesPlanDetail.setPlanPredictAmt(planPredictAmt);
//				addnotesPlanDetail.setPlanAddnotesAmt(planPredictAmt);
//				addnotesPlanDetail.setUseDays(availableDays);
//
//				addnotesPlanDetailMapper.updateByPrimaryKeySelective(addnotesPlanDetail);
//
//				sumAmt = sumAmt + planPredictAmt;
//			}
//
//			addnotesPlan.setStatus(AddnotesPlanStatus.AMOUNT_PREDICTED.getId());
//			addnotesPlan.setPlanAddnotesAmt((long) sumAmt);
//
//			addnotesPlanMapper.updateByPrimaryKeySelective(addnotesPlan);
//
//			AddnotesPlanDTO addnotesPlanDTO = AddnotesPlanDTOConverter.INSTANCE.domain2dto(addnotesPlan);
//			List<AddnotesLineDTO> addnoteLineList = new ArrayList<AddnotesLineDTO>();
//			Map<String, String> addnotesLineMap = addnotesLineService.getAddnotesLineMap();
//			if (addnotesPlan.getLineNo() != null) {
//				String lineNoString = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
//				String[] stringArr = lineNoString.split(",");
//				for (String lineNo : stringArr) {
//					AddnotesLineDTO addnotesLineDTO = new AddnotesLineDTO();
//					addnotesLineDTO.setLineNo(lineNo.trim());
//					String lineName = addnotesLineMap.get(lineNo.trim());
//					addnotesLineDTO.setLineName(lineName);
//					addnoteLineList.add(addnotesLineDTO);
//				}
//				addnotesPlanDTO.setLineList(addnoteLineList);
//			}
//
//			List<AddnotesPlanDetailDTO> detailList = AddnotesPlanDetailDTOConverter.INSTANCE.domain2dto(addnotesPlanDetails);
//			for (int i = 0; i < addnotesPlanDetails.size(); i++) {
//				String lastAddnoteDate = addnotesPlanDetails.get(i).getLastAddnoteDate();
//				int notAddCashDay = -1;
//				if (lastAddnoteDate != null && !"".equals(lastAddnoteDate)) {
//					notAddCashDay = CalendarUtil.getSubDate(CalendarUtil.getSysTimeYMD(), lastAddnoteDate);
//				}
//				String notAddCashDays = String.valueOf(notAddCashDay);
//				detailList.get(i).setNotAddCashDays(notAddCashDays);
//			}
//
//			retMap.put("addnotesPlan", addnotesPlanDTO);
//			retMap.put("detailList", detailList);
//
//			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
//			retMap.put("retMsg", "金额预测成功！");
//		} catch (Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "金额预测异常!");
//			log.error("getAddnotesPlanCashReqAmt Fail: ", e);
//		}
//		return retMap;
//	}
	private SimpleDevInfo devBaseInfo2SimpleInfo(DevBaseInfo devBaseInfo) {
		SimpleDevInfo simpleDevInfo = new SimpleDevInfo(devBaseInfo.getNo());
		simpleDevInfo.setDevCatalog(devBaseInfo.getDevTypeTable().getDevCatalogTable().getNo());
		simpleDevInfo.setCycleFlag(devBaseInfo.getCycleFlag());
		simpleDevInfo.setDevStantardSize(devBaseInfo.getDevStantardSize());
		simpleDevInfo.setCashboxLimit(devBaseInfo.getCashboxLimit());
		return simpleDevInfo;
	}

	/**
	 * 组织网点信息s(+其下设备信息s)
	 *
	 * @param =[设备编号，网点编号,网点名称,网点地址,顺序号]
	 * @return [[网点编号, 网点名称, 网点地址, 经度, 纬度, 设备数, 设备编号字符串表示, 排序号, 设备数量]]
	 */
	private List<Map<String, Object>> formNetpointsWithDevs(List<Map<String, Object>> tempList, boolean withSortNo, boolean withDevCnt) {
		List<String> netpointNos = new ArrayList<String>(); // 网点编号列表,按sort_no和org_no排序
		HashMap<String, String> netpointNoNameMap = new HashMap<String, String>(); // 网点编号:网点名称
		HashMap<String, String> netpointNoAddressMap = new HashMap<String, String>(); // 网点编号:网点地址
		HashMap<String, Double> netpointNoXMap = new HashMap<String, Double>(); //网点编号：经度
		HashMap<String, Double> netpointNoYMap = new HashMap<String, Double>(); //网点编号：纬度

		HashMap<String, ArrayList<Map<String, Object>>> netpointNoDevNosMap = new HashMap<String, ArrayList<Map<String, Object>>>(); // 网点编号:设备信息
		HashMap<String, Integer> netpointNoSortNoMap = new HashMap<String, Integer>(); // 网点编号:排序编号

		String netpointNo;
		for (Map<String, Object> aMap : tempList) {
			String devNo = StringUtil.parseString(aMap.get("DEVNO"));
			String orgNo = StringUtil.parseString(aMap.get("ORGNO"));
			String orgName = StringUtil.parseString(aMap.get("ORGNAME"));
			String address = StringUtil.parseString(aMap.get("ADDRESS"));
			Double x = StringUtil.ch2Double(aMap.get("X").toString());
			Double y = StringUtil.ch2Double(aMap.get("Y").toString());

			int sortNo = StringUtil.objectToInt(aMap.get("SORTNO"));
			String keyEventDetail = StringUtil.parseString(aMap.get("KEYEVENTDETAIL"));

			netpointNo = orgNo;
			if (!netpointNos.contains(netpointNo)) {
				netpointNos.add(netpointNo);
				netpointNoNameMap.put(netpointNo, StringUtil.parseString(orgName));
				netpointNoAddressMap.put(netpointNo, StringUtil.parseString(address));
				netpointNoXMap.put(netpointNo, x);
				netpointNoYMap.put(netpointNo, y);
				netpointNoSortNoMap.put(netpointNo, sortNo);

				//创建设备号列表
				ArrayList<Map<String, Object>> netpointNoDevNoList = new ArrayList<Map<String, Object>>();
				netpointNoDevNosMap.put(netpointNo, netpointNoDevNoList);
			}
			Map<String, Object> devMap = new HashMap<String, Object>();
			devMap.put("no", devNo);
			devMap.put("keyEventDetail", keyEventDetail);

			netpointNoDevNosMap.get(netpointNo).add(devMap);
		}
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		for (String orgNo : netpointNos) {
			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("orgNo", orgNo);
			entity.put("orgName", netpointNoNameMap.get(orgNo));
			entity.put("address", netpointNoAddressMap.get(orgNo));
			entity.put("x", netpointNoXMap.get(orgNo));
			entity.put("y", netpointNoYMap.get(orgNo));
			if (withSortNo) {
				entity.put("sortNo", netpointNoSortNoMap.get(orgNo));
			}
			if (withDevCnt) {
				entity.put("devCnt", netpointNoDevNosMap.get(orgNo).size());
			}
			entity.put("devs", netpointNoDevNosMap.get(orgNo));
			retList.add(entity);
		}
		return retList;
	}


	/**
	 * 加钞周期调整，需要同步生成线路运行图失败
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO modDevClrTime(DevClrTimeParamListDTO dto) throws ParseException {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "调整失败");

		String devNo = StringUtil.parseString(dto.getDevNo());
		int addClrPeriod = StringUtil.objectToInt(dto.getAddClrPeriod());
		int maxAddClrPeriod = StringUtil.objectToInt(dto.getMaxAddClrPeriod());
		String timeList = JSONUtil.createJsonString(dto.getTimeList());

		//update DevBaseInfo
//		DevBaseInfo devBaseInfo=devBaseInfoMapper.selectByPrimaryKey(devNo);
		DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectByPrimaryKey(devNo);
		if (devBaseInfo == null) {
			dto1.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			dto1.setRetMsg("修改设备加钞周期接口异常!对象已不存在");
			return dto1;
		}

		//查询设备的金库
		String clrCenterNo = devBaseInfo.getClrCenterNo();

		devBaseInfo.setNo(devNo);
		devBaseInfo.setAddClrPeriod(addClrPeriod);
		devBaseInfo.setMaxAddClrPeriod(maxAddClrPeriod);

//		devBaseInfoMapper.updateByPrimaryKeySelective(devBaseInfo);
		devBaseInfoInnerResource.updateByPrimaryKeySelective(devBaseInfo);

		//uptate DevClrTimeParam
		//查询设备原有线路
		List<String> lineNoResult = devCountInnerResource.selectlineNoList(devNo);
		List<String> lineNoList = new ArrayList<String>();
		if (lineNoResult.size() > 0) {
			for (String addnotesLine : lineNoResult) {
				if (!lineNoList.contains(addnotesLine)) {
					log.debug("更新线路" + addnotesLine);
					lineNoList.add(addnotesLine);
				}
			}
		}

		//删除原有设备加钞周期信息
		devCountInnerResource.deleteByDevNo(devNo);
		//添加新的设备加钞周期信息
		if (timeList != null && !"".equals(timeList)) {
			JSONArray jsonArray = JSONUtil.parseJSONArray(timeList);
			for (Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
//				DevClrTimeParam devClrTimeParam = new DevClrTimeParam();
//				devClrTimeParam.setClrTimeInterval(StringUtil.parseString(jsonObject.get("clrTimeInterval")));
//				devClrTimeParam.setArrivalTime(StringUtil.parseString(jsonObject.get("arrivalTime")));
				String addnotesLine = StringUtil.parseString(jsonObject.get("addnotesLine"));
//				devClrTimeParam.setAddnotesLine(addnotesLine);
//				devClrTimeParam.setClrDay(StringUtil.objectToInt(jsonObject.get("clrDay")));
//				devClrTimeParam.setDevNo(devNo);

				if (!lineNoList.contains(addnotesLine)) {
					log.debug("更新线路" + addnotesLine);
					lineNoList.add(addnotesLine);
				}
				Map<String,Object> devClrTimeMap = new HashMap<>();
				devClrTimeMap.put("clrTimeInterval",StringUtil.parseString(jsonObject.get("clrTimeInterval")));
				devClrTimeMap.put("arrivalTime",StringUtil.parseString(jsonObject.get("arrivalTime")));
				devClrTimeMap.put("addnotesLine",addnotesLine);
				devClrTimeMap.put("clrDay",StringUtil.objectToInt(jsonObject.get("clrDay")));
				devClrTimeMap.put("devNo",devNo);
//				devCountInnerResource.insert(devClrTimeParam);
				devCountInnerResource.insert(devClrTimeMap);
			}
		}

		Map<String, Object> retMap = businessLineRunService.genLineRunMap(clrCenterNo, lineNoList);

		dto1.setRetCode(StringUtil.parseString(retMap.get("retCode")));
		dto1.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		return dto1;
	}


	/**
	 * 预估某台设备的下一日现金流量(元)
	 *
	 * @param devNo        设备编号
	 * @param predictValue 模型预测金额
	 * @param type         -1:现金付出(取款), 1:现金收入(存款), 0:付出净差(balance = 取款 - 存款)
	 * @return int
	 */
	public int predictDailyCash(String devNo, int predictValue, int type) {
//		int predictValue = DEFAULT_AMT_LIMIT;
		try {
			if (devNo == null || "".equals(devNo)) {
				log.error("设备号为空!");
				return 0;
			}

			//样本周期30
			ArrayList<TSData> aTSDataList = amtPredictServiceImpl.getQuerySqlByDev(devNo, type);
			if (aTSDataList != null) {
				double allData = 0.0;
				for (TSData tmpTSData : aTSDataList) {
					allData += StringUtil.objectToDouble(tmpTSData.getDataValue());
				}
				double avgData = aTSDataList.size() == 0 ? 0 : allData / aTSDataList.size();
				int addClrPeriod = devBaseInfoInnerResource.selectByPrimaryKey(devNo).getAddClrPeriod();


//				Map<String, Object> map = new HashMap<>();
//				map.put("termID", devNo);
//				map.put("predictDate", CalendarUtil.getFixedDate(CalendarUtil.getSysTimeYMD(), 1));
//				map.put("addCashCycle", addClrPeriod);
				//netflag填0、或不填为库存方案预测，1为周期净流量预测（0需要有训练后的数据库数据）
//				map.put("netflag", 1);

//				String zjmlIP = CfgProperty.getProperty("zjmlIP");
//				String zjmlPort = CfgProperty.getProperty("zjmlPort");
//				String jsonString = fixedRestTemplate.postForObject("http://" + zjmlIP + ":" + zjmlPort + "/coffers/v2/predict/", JSONUtil.createJsonString(map), String.class);
//				JSONObject jsonObject = JSONUtil.parseJSONObject(jsonString);
//				if ("00".equals(jsonObject.get("retCode"))) {
//					JSONObject predictData = jsonObject.getJSONObject("data");
//					predictValue = StringUtil.objectToInt(predictData.get("addCashAmout"));
//				}

				if (type != 0 && predictValue <= 0) {
					for (int i = 1; i < addClrPeriod; i++) {
						predictValue += (int) aTSDataList.get(aTSDataList.size() - i).getDataValue();
					}
				}

				//超过均值3倍，或者低于均值1/3使用一个月前的两个加钞周期的均值
				if (predictValue > 3 * avgData || predictValue < avgData / 3) {
					log.debug("设备[" + devNo + "]预测值为[" + predictValue + "]超出正常区间，进行数据修正");
					for (int i = 0; i < addClrPeriod; i++) {
						int lastMonthPeriod = (int) aTSDataList.get(aTSDataList.size() - 30 - i).getDataValue();
						int lastMonthPeriod2 = (int) aTSDataList.get(aTSDataList.size() - 31 + i).getDataValue();
						predictValue = (lastMonthPeriod + lastMonthPeriod2) / 2;
					}
					log.debug("修正后预测值为【" + predictValue + "】");
				}

			}
			log.debug("predict value with zjml is: " + predictValue);
		} catch (Exception e) {
			log.error("[predictDailyCash] error:", e);
		}
		return predictValue;
	}

	/**
	 * 加钞计划审核出单
	 * @return Map
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> planToTask(String createJsonString) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try{
		JSONObject params = JSONUtil.parseJSONObject(createJsonString);
		String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
		String sysUserNo = StringUtil.parseString(params.get("sysUserNo"));
		String opinion = StringUtil.parseString(params.get("opinion"));

		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
		if (addnotesPlan == null) {
			retMap.put("retMsg", RetCodeEnum.AUDIT_OBJECT_DELETED.getTip());
			return retMap;
		}

		//查询分组列表
		List<String> groupList = addnotesPlanDetailMapper.getGroupNos(addnotesPlanNo);
		if (groupList == null || groupList.size() == 0) {
			retMap.put("retMsg", "尚未分组!");
			return retMap;
		}

		//create a task and its detail
		boolean is_first_in_plan = true; //whether is the first dispatch added from this plan
		String preTaskNo = ""; //previous dispatch no from this plan
		for (String groupNo : groupList) {
			Integer clrTimeInterval = StringUtil.ch2Int(groupNo.substring(groupNo.length() - 1));
			if (clrTimeInterval == -1) {
				clrTimeInterval = null;
			}
			AddnotesPlanGroupKey addnotesPlanGroupKey = new AddnotesPlanGroupKey();
			addnotesPlanGroupKey.setAddnotesPlanNo(addnotesPlanNo);
			addnotesPlanGroupKey.setGroupNo(groupNo);
			addnotesPlanGroupKey.setClrTimeInterval(clrTimeInterval);
			//相应加钞计划中，指定分组的信息
			AddnotesPlanGroup addnotesPlanGroup = addnotesPlanGroupMapper.selectByPrimaryKey(addnotesPlanGroupKey);

			//相应加钞计划中，指定分组的计划明细记录
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("addnotesPlanNo", addnotesPlanNo);
			paraMap.put("lineNo", groupNo);
			List<AddnotesPlanDetail> planDetailList = addnotesPlanDetailMapper.selectByLineNo(paraMap);

			//加钞任务
			/*int taskType = 1;
			String taskNo = is_first_in_plan == false ?
					preTaskNo.substring(0, preTaskNo.length() - 4)
							+ String.format("%04d", Integer.parseInt(preTaskNo.substring(preTaskNo.length() - 4)) + 1)
					:taskInnerResource.getNextLogicId(addnotesPlan.getClrCenterNo(),taskType);*/
//					this.getNextLogicId(addnotesPlan.getClrCenterNo(), taskType);

//
//			TaskInfo taskInfo = new TaskInfo();
//			taskInfo.setTaskNo(taskNo);
//			taskInfo.setTaskType(taskType);
//			taskInfo.setStatus(201);
//			taskInfo.setTaskType(StatusEnum.taskType.ADDNOTESTASK.getId());
//			taskInfo.setAddnotesPlanNo(addnotesPlanNo);
//			taskInfo.setClrCenterNo(addnotesPlan.getClrCenterNo());
//			taskInfo.setLineNo(groupNo);
//			taskInfo.setAddnotesPlanNo(addnotesPlan.getAddnotesPlanNo());
//			taskInfo.setPlanFinishDate(addnotesPlan.getPlanAddnotesDate());
//			taskInfo.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
//			taskInfo.setCreateOpNo(ServletRequestUtil.getUsername());
//			taskInfo.setUrgentFlag(addnotesPlan.getIsUrgency());
//			if (addnotesPlanGroup != null) {
//				taskInfo.setPlanDistance(addnotesPlanGroup.getPlanDistance());
//				taskInfo.setPlanTimeCost(addnotesPlanGroup.getPlanTimeCost());
//			}
//
//			taskInfo.setLineNo(groupNo);
			/*Map<String, Object> taskInfoMap = new HashMap<>();
			taskInfoMap.put("taskNo",taskNo);
//			taskInfoMap.put("taskType",taskType);
			taskInfoMap.put("status",201);
			taskInfoMap.put("taskType",StatusEnum.taskType.ADDNOTESTASK.getId());
			taskInfoMap.put("addnotesPlanNo",addnotesPlanNo);
			taskInfoMap.put("clrCenterNo",addnotesPlan.getClrCenterNo());
			taskInfoMap.put("planFinishDate",addnotesPlan.getPlanAddnotesDate());
			taskInfoMap.put("createTime",CalendarUtil.getSysTimeYMDHMS());
			taskInfoMap.put("createOpNo",ServletRequestUtil.getUsername());
			taskInfoMap.put("urgentFlag",addnotesPlan.getIsUrgency());
			if (addnotesPlanGroup != null) {
				taskInfoMap.put("planDistance",addnotesPlanGroup.getPlanDistance());
				taskInfoMap.put("planTimeCost",addnotesPlanGroup.getPlanTimeCost());
			}
			taskInfoMap.put("lineNo",groupNo);
			int a = taskInnerResource.insertSelectiveByMap(taskInfoMap);
			if (a == -1) {
				throw new RuntimeException("失败");
			}
			preTaskNo = taskNo;
			is_first_in_plan = false;*/

			//任务明细
			for (AddnotesPlanDetail planDetail : planDetailList) {
				//加钞任务
				int taskType = 1;
				String taskNo = is_first_in_plan == false ?
						preTaskNo.substring(0, preTaskNo.length() - 4)
								+ String.format("%04d", Integer.parseInt(preTaskNo.substring(preTaskNo.length() - 4)) + 1)
						:taskInnerResource.getNextLogicId(addnotesPlan.getClrCenterNo(),taskType);
//				TaskDetail taskDetail = new TaskDetail();
//				taskDetail.setCustomerNo(planDetail.getDevNo());
				//默认为 ATM
//				taskDetail.setCustomerType(6);
//				taskDetail.setTaskNo(taskNo);
//				taskDetail.setSort(planDetail.getSortNo());
//				taskDetail.setStatus(StatusEnum.TaskDetailStatus.AUDITTED.getId());

				Map<String, Object> taskInfoMap = new HashMap<>();
				String devNo = planDetail.getDevNo();
				DevBaseInfo devBaseInfo = devBaseInfoInnerResource.selectByPrimaryKey(devNo);
				String address = devBaseInfo.getAddress();
				String customerName = devBaseInfo.getSysOrg().getName();
				taskInfoMap.put("taskNo",taskNo);
//			taskInfoMap.put("taskType",taskType);
				taskInfoMap.put("status",201);
				taskInfoMap.put("taskType",StatusEnum.taskType.ADDNOTESTASK.getId());
				taskInfoMap.put("addnotesPlanNo",addnotesPlanNo);
				taskInfoMap.put("customerNo",planDetail.getDevNo());
				taskInfoMap.put("customerType",6);
				taskInfoMap.put("clrCenterNo",addnotesPlan.getClrCenterNo());
				taskInfoMap.put("planFinishDate",addnotesPlan.getPlanAddnotesDate());
				taskInfoMap.put("createTime",CalendarUtil.getSysTimeYMDHMS());
				taskInfoMap.put("createOpNo",ServletRequestUtil.getUsername());
				taskInfoMap.put("urgentFlag",addnotesPlan.getIsUrgency());
				taskInfoMap.put("address",address);
				taskInfoMap.put("customerName",customerName);
				if (addnotesPlanGroup != null) {
					taskInfoMap.put("planDistance",addnotesPlanGroup.getPlanDistance());
					taskInfoMap.put("planTimeCost",addnotesPlanGroup.getPlanTimeCost());
				}
				//taskInfoMap.put("lineNo",groupNo);
				int a = taskInnerResource.insertSelectiveByMap(taskInfoMap);
				if (a == -1) {
					throw new RuntimeException("失败");
				}

				//插入金额信息
				Map<String, Object> taskdetailMap = new HashMap<>();
				taskdetailMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				taskdetailMap.put("taskNo",taskNo);
				taskdetailMap.put("productNo","X0001");
				taskdetailMap.put("direction",2);
				taskdetailMap.put("amount",planDetail.getPlanAddnotesAmt());
				int g = taskDetailInfoInnerResource.insertSelectiveByMap(taskdetailMap);
				if (g == -1) {
					throw new RuntimeException("插入金额失败");
				}
				//插入金额属性
				Map<String, Object> detailPropertyMap = new HashMap<>();
				detailPropertyMap.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				detailPropertyMap.put("detailId",taskdetailMap.get("id"));
				detailPropertyMap.put("key","currencyCode");
				detailPropertyMap.put("name","钞币类别");
				detailPropertyMap.put("value","人民币");
				detailPropertyMap.put("taskNo",taskNo);
				int b = taskDetailPropertyInnerResource.insertSelectiveByMap(detailPropertyMap);
				if (b == -1) {
					throw new RuntimeException("插入金额属性失败");
				}
				Map<String, Object> detailProperty2Map = new HashMap<>();
				detailProperty2Map.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				detailProperty2Map.put("detailId",taskdetailMap.get("id"));
				detailProperty2Map.put("key","denomination");
				detailProperty2Map.put("name","面值");
				detailProperty2Map.put("value","100");
				detailProperty2Map.put("taskNo",taskNo);
				int e = taskDetailPropertyInnerResource.insertSelectiveByMap(detailProperty2Map);
				if (e == -1) {
					throw new RuntimeException("插入金额属性失败");
				}
				Map<String, Object> detailProperty3Map = new HashMap<>();
				detailProperty3Map.put("id",java.util.UUID.randomUUID().toString().replace("-", ""));
				detailProperty3Map.put("detailId",taskdetailMap.get("id"));
				detailProperty3Map.put("key","currencyType");
				detailProperty3Map.put("name","币种");
				detailProperty3Map.put("value","完成券");
				detailProperty3Map.put("taskNo",taskNo);
				int d = taskDetailPropertyInnerResource.insertSelectiveByMap(detailProperty3Map);
				if (d == -1) {
					throw new RuntimeException("插入金额属性失败");
				}

				//插入线路
				Map<String, Object> lineMap = new HashMap<String, Object>();
				String planFinishDate = addnotesPlan.getPlanAddnotesDate();
				String theYearMonth = planFinishDate.substring(0,7);
				String theDay = planFinishDate.substring(8);
//				LineScheduleDTO lineScheduleDTO = new LineScheduleDTO();
//				lineScheduleDTO = lineScheduleInnerResource.selectByMap(lineMap);
//				String lineWorkId =  lineScheduleDTO.getLineWorkId();
				AddnotesPlanDetail addnotesPlanDetail = new AddnotesPlanDetail();
				addnotesPlanDetail.setAddnotesPlanNo(addnotesPlanNo);
				addnotesPlanDetail.setDevNo(planDetail.getDevNo());
				AddnotesPlanDetail addnotesPlanDetail1 = addnotesPlanDetailMapper.selectByPrimaryKey(addnotesPlanDetail);
				String lineNo = addnotesPlanDetail1.getLineNo();
				lineMap.put("lineNo",lineNo);
				lineMap.put("theYearMonth",theYearMonth);
				lineMap.put("theDay",theDay);
				String lineWorkId = lineWorkInnerResource.qryLineWorkId(lineMap);
				if(lineWorkId != null){
					Map<String, Object> lineWorkMap = new HashMap<String, Object>();
					lineWorkMap.put("lineWorkId",lineWorkId);
					lineWorkMap.put("taskNo",taskNo);
					int c = taskLineInnerResource.insertSelectiveByMap(lineWorkMap);
					if (c == -1) {
						throw new RuntimeException("失败");
					}
				}
				preTaskNo = taskNo;
				is_first_in_plan = false;

//				//默认为 ATM
//				Map<String, Object> taskDetailMap = new HashMap<>();
//				taskDetailMap.put("customerNo",planDetail.getDevNo());
//				taskDetailMap.put("customerType",6);
//				taskDetailMap.put("taskNo",taskNo);
//				taskDetailMap.put("sort",planDetail.getSortNo());
//				taskDetailMap.put("status",StatusEnum.TaskDetailStatus.AUDITTED.getId());

//				int y = taskDetailInfoInnerResource.insertSelectiveByMap(taskDetailMap);
//				if (y == -1) {
//					throw new RuntimeException("失败");
//				}
			}

		}


		addnotesPlan.setRefuseSuggestion(opinion);
		addnotesPlan.setAuditOpNo(sysUserNo);
		addnotesPlan.setAuditDate(CalendarUtil.getSysTimeYMD());
		addnotesPlan.setAuditTime(CalendarUtil.getSysTimeHMS());
		addnotesPlan.setStatus(StatusEnum.AddnotesPlanStatus.SUBMITTED.getId());
		int d = addnotesPlanMapper.updateByPrimaryKey(addnotesPlan);
		if (d == -1) {
			throw new RuntimeException("失败");
		}
		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "出单成功！");

		return retMap;
	}

	@Override
	public AddnotesPlanDTO selectByPrimaryKey(String addnotesPlanNo) {
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectByPrimaryKey(addnotesPlanNo);
		AddnotesPlanDTO addnotesPlanDTO = AddnotesPlanDTOConverter.INSTANCE.domain2dto(addnotesPlan);
		return addnotesPlanDTO;
	}

	@Override
	public AddnotesPlanDTO selectDetailByPrimaryKey(String addnotesPlanNo) {
		AddnotesPlan addnotesPlan = addnotesPlanMapper.selectDetailByPrimaryKey(addnotesPlanNo);
		AddnotesPlanDTO addnotesPlanDTO = AddnotesPlanDTOConverter.INSTANCE.domain2dto(addnotesPlan);
		return addnotesPlanDTO;
	}

//	private String getNextLogicId(String clrCenterNo, int taskType) {
//		String currentDate = CalendarUtil.getSysTimeYMD();
//		String currentDate1 = CalendarUtil.getDate().substring(2);
//		String taskType1 = String.format("%02d", taskType);
//		String logicId = clrCenterNo + taskType1 + currentDate1.replaceAll("-", "");
//		String maxTaskNo = taskInnerResource.getNextLogicId(clrCenterNo,taskType);
//		if (maxTaskNo != null) {
//			String lastId = maxTaskNo;
//			String seq = String.format("%04d", Integer.parseInt(lastId.substring(lastId.length() - 4)) + 1);
//			logicId += seq;
//		} else {
//			logicId += "0001";
//		}
//		return logicId;
//	}

	@Override
	public int updateByPrimaryKeyByMap(Map<String, Object> addnotesPlanlMap) {
		return addnotesPlanMapper.updateByPrimaryKeyByMap(addnotesPlanlMap);
	}


	public int[] predictThresholds4Circle(SimpleDevInfo simpleDevInfo, int predictValue) {
		int[] safeSpace = new int[2];
		Integer catalogNo = simpleDevInfo.getDevCatalog();
		Integer cycleFlag = simpleDevInfo.getCycleFlag();
		int maxVolumn = simpleDevInfo.getDevStantardSize();
		int cashboxLimit = StringUtil.ch2Int(simpleDevInfo.getCashboxLimit());
		cashboxLimit = cashboxLimit > 0 ? cashboxLimit : 5000;
		if (catalogNo != DevCatalog.CRS.getId()
				|| cycleFlag == null || cycleFlag == CycleFlag.UNCYCLE.getId()) {
			log.error("[predictThresholds4Circle] devNo[" + simpleDevInfo.getNo() + "] is not a circle CRS");
			return null;
		}
		int predictDailyWdr = this.predictDailyCash(simpleDevInfo.getNo(), predictValue, -1);
		safeSpace[0] = predictDailyWdr > cashboxLimit ? predictDailyWdr : cashboxLimit;
		safeSpace[1] = maxVolumn - this.predictDailyCash(simpleDevInfo.getNo(), predictValue, 1);
		log.debug("The safe range of CRS[" + simpleDevInfo.getNo() + "] is [" + safeSpace[0] + ", " + safeSpace[1] + "].");
		return safeSpace;
	}

}
