package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.domain.SimpleDevInfo;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;

/**
 * 加钞金额预测服务
 * @author hongwei
 *
 */
public interface AmtPredictService {

	/**
	 * 预测单台设备加钞金额
	 * @param devBaseInfo
	 * @param planAddnotesDate
	 * @param clrCenterNo
	 * @param cashReqAmt  现金需求量
	 * @return
	 *
	 */
	public int getNextEstAddnotesAmt(DevBaseInfo devBaseInfo, String planAddnotesDate, String clrCenterNo, double cashReqAmt);

	/**
	 * 预测设备下一日现金流量
	 * @param devNo
	 * @param type  -1:现金付出(取款), 1:现金收入(存款), 0:净付出(balance=取款-存款)
	 * @return
	 */
	public int predictDailyCash(String devNo, int type);

	/**
	 * 预测CRS现金量完全空间上下限阈值
	 * @param
	 * @return [min_threshold, max_threshold]
	 */
	public int[] predictThresholds4Circle(SimpleDevInfo simpleDevInfo);

}
