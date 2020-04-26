package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanDetail;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import com.zjft.microservice.treasurybrain.business.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;

import java.text.ParseException;
import java.util.Map;

public interface AddnotesPlanService {

	/**
	 * 查询加钞计划
	 *
	 * @param string pageSize，curPage，clrCenterNo，planStartDate，planEndDate，status，urgencyFlag，genOpNo
	 * @return addnotesPlanList，totalRow，totalPage
	 */
	Map<String, Object> qryAddnotesPlan(String string);

	/**
	 * 修改加钞金额（金额调整）
	 *
	 * @param createJsonString modOpNo：最近修改人 addnotesPlanNo：加钞计划编号 devList：设备列表
	 * @return 状态码
	 */
	Map<String, Object> modAddnotesPlanAmts(String createJsonString);


	/**
	 * 设备金额模型预测
	 *
	 * @param String termID,int addCashCycle,String predictDate
	 * @return cashReqAmt
	 */
	int getAddnotesPredictValue(String termID, int addCashCycle, String predictDate);

	/**
	 * 加钞金额修正
	 *
	 * @param AddnotesPlanDetail addnotesPlanDetail
	 * @return 12、现金需求量及剩余天数字段
	 */
	Map<String, Object> modAddnotesPredict(AddnotesPlanDetail addnotesPlanDetail, int predictValue);

	/**
	 * 加钞策略调整
	 *
	 * @param AddnotesPlanDetail addnotesPlanDetail
	 * @return planPredictAmt
	 */
	int adjustAddnotesPredict(AddnotesPlanDetail addnotesPlanDetail, String addnotesPlanNo, double cashReqAmt, int availableDays);

	/**
	 * 查询维护型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 维护型设备列表
	 */
	Map<String, Object> qryAddnotesPlanDevsForMaintain(String createJsonString);

	/**
	 * 查询决定型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 决定型设备列表
	 */
	Map<String, Object> qryAddnotesPlanDevsForCash(String createJsonString);

	/**
	 * 查询预测型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 预测型设备列表
	 */
	Map<String, Object> qryAddnotesPlanDevsForPredict(String createJsonString);

	/**
	 * 查询计划型设备列表
	 *
	 * @param createJsonString addnotesPlanNo：加钞计划编号，orgNo：所属机构
	 * @return 计划型设备列表
	 */
	Map<String, Object> qryAddnotesPlanDevsForRunPlan(String createJsonString);

	/**
	 * 保存加钞设备
	 *
	 * @param createJsonString modOpNo:最近修改人，addnotesPlanNo:加钞计划编号，devList:设备列表，devNo:设备编号
	 *                         lineNo:线路号，keyEvent:决定型事件，keyEventDetail:决定型事件描述，chsEstScore:预测型加钞优先度
	 *                         chsAuxScore:辅助型加钞优先度
	 * @return 状态码
	 */
	Map<String, Object> addAddnotesPlanDevs(String createJsonString);

	/**
	 * 修改加钞设备（设备调整）
	 *
	 * @param createJsonString modOpNo:最近修改人，addnotesPlanNo:加钞计划编号，devList:设备列表，devNo:设备编号
	 * @return 状态码
	 */
	Map<String, Object> modAddnotesPlanDevs(String createJsonString);

	/**
	 * 添加加钞计划
	 *
	 * @param string genOpNo,clrCenterNo,clrCenterName,planAddnotesDate，groupMode，groupNo，awayFlag，isUrgency
	 * @return addnotesPlanNo
	 */
	Map<String, Object> addAddnotesPlan(String string);

	/**
	 * 查询加钞计划详细信息
	 *
	 * @param string addnotesPlanNo：加钞计划编号
	 * @return addnotesPlan，detailList
	 */
	Map<String, Object> qryAddnotesPlanDetail(String string);

	/**
	 * 删除加钞计划
	 *
	 * @param string addnotesPlanNo
	 * @return 状态码
	 */
	Map<String, Object> delAddnotesPlan(String string);

	/**
	 * 查询加钞计划内容（for设备调整、金额调整）
	 *
	 * @param createJsonString addnotesPlanNo；加钞计划编号
	 * @return AddnotesPlanDetailForDevDTO：加钞计划内容（for设备调整、金额调整），entity：加钞计划表的一部分内容
	 */
	Map<String, Object> qryAddnotesPlanDetailForDev(String createJsonString);

	/**
	 * 加钞计划退审
	 * 查询是否有该加钞计划，有则改为退回调整
	 *
	 * @param createJsonString 处理的计划编号：addnotesPlanNo 审核结果：opinion 当前用户编号：sysUserNo
	 * @return 状态码
	 */
	Map<String, Object> addAddnotesRefuse(String createJsonString);


	/**
	 * 设备金额预测
	 *
	 * @param createJsonString modOpNo：最近修改人，addnotesPlanNo：加钞计划编号
	 * @return addnotesPlanDTO，detailList
	 */
	Map<String, Object> getAddnotesPlanCashReqAmt(String createJsonString);

	/**
	 * 添加加钞审核计划
	 * 查询加钞计划，有则提交审核，审核完成后推送当前剩余审核计划数（aop）
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 状态码
	 */
	Map<String, Object> addAddnotesAudit(String addnotesPlanNo);


	/**
	 * 加钞周期调整，需要同步生成线路运行图失败
	 */
	DTO modDevClrTime(DevClrTimeParamListDTO dto) throws ParseException;

	/**
	 * 加钞计划审核出单
	 * @return Map
	 */
	Map<String, Object> planToTask(String createJsonString);

	/**
	 * 根据addnotesPlanNo查询预测总金额
	 * @param addnotesPlanNo
	 * @return
	 */


	AddnotesPlanDTO selectByPrimaryKey(String addnotesPlanNo);

	AddnotesPlanDTO selectDetailByPrimaryKey(String addnotesPlanNo);

	int updateByPrimaryKeyByMap(Map<String, Object> addnotesPlanMap);

	/**
	 * 比较交易金额
	 * @param devNo
	 * @param addnotesDate
	 * @param useDays
	 * @param predictAmt
	 * @return
	 */
	int compareAddnotesAmount(String devNo, String addnotesDate, int useDays, int predictAmt);

	/**
	 * 查询历史同期金额
	 * @param devNo
	 * @param addnotesDate
	 * @param useDays
	 * @param months
	 * @return
	 */
	int getAddnotesAmountHistory(String devNo, String addnotesDate, int useDays,int months);
}
