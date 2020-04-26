package com.zjft.microservice.treasurybrain.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.business.domain.*;
import com.zjft.microservice.treasurybrain.business.service.AmtPredictService;
import com.zjft.microservice.treasurybrain.channelcenter.web.DevBaseInfoResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevBaseInfoInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.CfgProperty;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.CycleFlag;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.DevCatalog;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;
import com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.AddnotesRuleResource;
import com.zjft.microservice.treasurybrain.param.web.DevRuleExecResource;
import com.zjft.microservice.treasurybrain.param.web.SpDateCoeffResource;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备金额预测
 *
 * @author 韩通
 * @author 徐全发
 * @since 2019-03-14
 */
@Slf4j
@Service
public class AmtPredictServiceImpl implements AmtPredictService {

	/**
	 * 余量（元）
	 */
	private static final double OVERMEASURE = 20000;

	/**
	 * 默认加钞量
	 */
	private static final int DEFAULT_AMT_LIMIT = 200000;

	/**
	 * 最大样本数
	 */
	private static final int MAX_SAMPLE_VOLUME = 730;

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private DevBaseInfoInnerResource devBaseInfoInnerResource;

	@Resource
	private AddnotesRuleResource addnotesRuleResource;

	@Resource
	private DevRuleExecResource devRuleExecResource;

	@Resource
	private SpDateCoeffResource spDateCoeffResource;

	@Resource
	private JdbcTemplate jdbcTemplate;

//	@Resource
//	RestTemplate selfRestTemplate;

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
	 * 预估某台设备的下一日现金流量(元)
	 *
	 * @param devNo 设备编号
	 * @param type  -1:现金付出(取款), 1:现金收入(存款), 0:付出净差(balance = 取款 - 存款)
	 * @return int
	 */
	@Override
	public int predictDailyCash(String devNo, int type) {
		int predictValue = DEFAULT_AMT_LIMIT;
		try {
			if (devNo == null || "".equals(devNo)) {
				log.error("设备号为空!");
				return 0;
			}

			//样本周期30
			ArrayList<TSData> aTSDataList = this.getQuerySqlByDev(devNo, type);
			if (aTSDataList != null) {
				double allData = 0.0;
				for (TSData tmpTSData : aTSDataList) {
					allData += StringUtil.objectToDouble(tmpTSData.getDataValue());
				}
				double avgData = aTSDataList.size() == 0 ? 0 : allData / aTSDataList.size();
				int addClrPeriod = devBaseInfoInnerResource.selectByPrimaryKey(devNo).getAddClrPeriod();


				Map<String, Object> map = new HashMap<>();
				map.put("termID", devNo);
				map.put("predictDate", CalendarUtil.getFixedDate(CalendarUtil.getSysTimeYMD(), 1));
				map.put("addCashCycle", addClrPeriod);
				//netflag填0、或不填为库存方案预测，1为周期净流量预测（0需要有训练后的数据库数据）
				map.put("netflag", 1);

				String zjmlIP = CfgProperty.getProperty("zjmlIP");
				String zjmlPort = CfgProperty.getProperty("zjmlPort");
				String jsonString = fixedRestTemplate.postForObject("http://" + zjmlIP + ":" + zjmlPort + "/coffers/v2/predict/", JSONUtil.createJsonString(map), String.class);
				JSONObject jsonObject = JSONUtil.parseJSONObject(jsonString);
				if ("00".equals(jsonObject.get("retCode"))) {
					JSONObject predictData = jsonObject.getJSONObject("data");
					predictValue = StringUtil.objectToInt(predictData.get("addCashAmout"));
				}

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
	 * 抽取样本数据集
	 *
	 * @param devNo 设备编号
	 * @param type  -1:现金付出(取款), 1:现金收入(存款), 0:净付出(balance = 取款 - 存款)
	 * @return ArrayList<TSData>
	 */
	public ArrayList<TSData> getQuerySqlByDev(String devNo, int type) {
		String sql = "";
		//取款量样本
		if (type == -1) {
			sql = "select * from (select AMOUNT_PROCESS_CWD as dataValue, TRANDATE as dateTime " +
					"from BIZTXLOG_INIT where TERMID = '" + devNo + "' order by TRANDATE desc) where rownum <= " + MAX_SAMPLE_VOLUME;
		}
		//存款量样本
		else if (type == 1) {
			sql = "select * from (select AMOUNT_PROCESS_CDM as dataValue, TRANDATE as dateTime  " +
					"from BIZTXLOG_INIT where TERMID = '" + devNo + "' order by TRANDATE desc) where rownum <= " + MAX_SAMPLE_VOLUME;
		}
		//净付出量样本
		else if (type == 0) {
			sql = "select * from (select AMOUNT_PROCESS_CWD - AMOUNT_PROCESS_CDM as dataValue, TRANDATE as dateTime  " +
					"from BIZTXLOG_INIT where TERMID = '" + devNo + "' order by TRANDATE desc) where rownum <= " + MAX_SAMPLE_VOLUME;
		}

		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			ArrayList<TSData> tSDataList = new ArrayList<TSData>();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> tmpMap : list) {
					TSData tSData = new TSData();
					tSData.setDataValue(StringUtil.objectToDouble(tmpMap.get("dataValue")));
					tSData.setDateTime(StringUtil.parseString(tmpMap.get("dateTime")));

					tSDataList.add(tSData);
				}
				return tSDataList;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("[getQuerySqlByDev] error: ", e);
			return null;
		}
	}

	@Override
	public int[] predictThresholds4Circle(SimpleDevInfo simpleDevInfo) {
		int[] safeSpace = new int[2];
		Integer catalogNo = simpleDevInfo.getDevCatalog();
		Integer cycleFlag = simpleDevInfo.getCycleFlag();
		int maxVolumn = simpleDevInfo.getDevStantardSize() * 10000;
		int cashboxLimit = StringUtil.ch2Int(simpleDevInfo.getCashboxLimit());
		cashboxLimit = cashboxLimit > 0 ? cashboxLimit : 5000;
		if (catalogNo != DevCatalog.CRS.getId()
				|| cycleFlag == null || cycleFlag == CycleFlag.UNCYCLE.getId()) {
			log.error("[predictThresholds4Circle] devNo[" + simpleDevInfo.getNo() + "] is not a circle CRS");
			return null;
		}
		int predictDailyWdr = this.predictDailyCash(simpleDevInfo.getNo(), -1);
		safeSpace[0] = predictDailyWdr > cashboxLimit ? predictDailyWdr : cashboxLimit;
		safeSpace[1] = maxVolumn - this.predictDailyCash(simpleDevInfo.getNo(), 1);
		log.debug("The safe range of CRS[" + simpleDevInfo.getNo() + "] is [" + safeSpace[0] + ", " + safeSpace[1] + "].");
		return safeSpace;
	}

	@Override
	public int getNextEstAddnotesAmt(DevBaseInfo devBaseInfo, String planAddnotesDate, String clrCenterNo, double cashReqAmt) {
		double cashStrategy = calcCashStrategy(cashReqAmt, devBaseInfo, planAddnotesDate, clrCenterNo);

		//放余量
		int cash = (int) (cashStrategy + OVERMEASURE);
		//取整调整
		//设备最大加钞量
		int devMaxAddnotesAmt = devBaseInfo.getDevStantardSize();
		cash = ceilDevAmt(cash, devMaxAddnotesAmt);

		log.debug("设备[" + devBaseInfo.getNo() + "]: 预测下一日加钞金额(元) == " + cash);
		return cash;
	}

	/**
	 * 应用加钞策略修正设备加钞量
	 *
	 * @param cashReqAmt
	 * @param devBaseInfo
	 * @param planAddnotesDate
	 * @param clrCenterNo
	 * @return
	 */
	private double calcCashStrategy(double cashReqAmt, DevBaseInfo devBaseInfo, String planAddnotesDate, String clrCenterNo) {
		double cashStrategy = 0;
		//策略系数
		double coeffStrategy = 1;

		//配钞系数
		Double addnotesCoeff = null;
		//定额配比
		Double quotaRatio = null;

		//设备信息
		String devNo = devBaseInfo.getNo();
		Integer districtNo = StringUtil.ch2Int(devBaseInfo.getSysOrg().getAreaType());
		//设备满额加钞量
		int devMaxAddnotesAmt = devBaseInfo.getDevStantardSize();

		//查询设备在加钞日期之日可用的特殊规则
//		DevRuleExecKey devRuleExecKey = new DevRuleExecKey();
//		devRuleExecKey.setDevNo(devNo);
//		devRuleExecKey.setEndDate(planAddnotesDate);
//		devRuleExecKey.setStartDate(planAddnotesDate);
		Map<String,Object> devRuleExecMap = new HashMap<String,Object>();
		devRuleExecMap.put("devNo", devNo);
		devRuleExecMap.put("endDate", planAddnotesDate);
		devRuleExecMap.put("startDate", planAddnotesDate);
		DevRuleExecDTO devRuleExec = devRuleExecResource.qryDevRuleExecByKey(devRuleExecMap);
		if (devRuleExec != null) {
			//更新设备执行规则状态置为执行中
			devRuleExec.setStatus(1);
			Map<String,Object> mapByUpdate = new HashMap<String,Object>();
			mapByUpdate.put("devNo", devRuleExec.getDevNo());
			mapByUpdate.put("startDate", devRuleExec.getStartDate());
			mapByUpdate.put("endDate", devRuleExec.getEndDate());
			mapByUpdate.put("addnotesRuleId", devRuleExec.getAddnotesRuleId());
			mapByUpdate.put("status", devRuleExec.getStatus());
			devRuleExecResource.modDevRuleExecByKey(mapByUpdate);
//			devRuleExecMapper.updateByPrimaryKey(devRuleExec);

			String ruleId = devRuleExec.getAddnotesRuleId();
//			AddnotesRule addnotesRule = addnotesRuleMapper.selectByPrimaryKey(ruleId);
			AddnotesRuleDTO addnotesRule = (AddnotesRuleDTO)addnotesRuleResource.detailAddnotesRule(ruleId)
					.getElement();
			if (addnotesRule != null) {
				quotaRatio = addnotesRule.getQuotaRatio();
				addnotesCoeff = addnotesRule.getAddnotesCoeff();
				//按额定配比加钞
				if (quotaRatio != null && quotaRatio >= 0 && quotaRatio <= 1) {
					return devMaxAddnotesAmt * quotaRatio;
				}
				//按配钞系数调整
				if (addnotesCoeff != null && addnotesCoeff >= 0) {
					return cashReqAmt * addnotesCoeff;
				}
			}
		}

		//加钞日期是否在特殊日期时段内
//		SpdateCoeffKey spdateCoeffKey = new SpdateCoeffKey();
//		spdateCoeffKey.setClrCenterNo(clrCenterNo);
//		spdateCoeffKey.setEndDate(planAddnotesDate);
//		spdateCoeffKey.setStartDate(planAddnotesDate);
		Map<String,Object> spdateCoeffMap = new HashMap<String,Object>();
		spdateCoeffMap.put("clrCenterNo", clrCenterNo);
		spdateCoeffMap.put("startDate", planAddnotesDate);
		spdateCoeffMap.put("endDate", planAddnotesDate);

//		SpdateCoeff spdateCoeff = spdateCoeffMapper.selectSpdateCoeffByKey(spdateCoeffKey);

		SpDateCoeffDTO spdateCoeff = spDateCoeffResource.qrySpDateCoeffByKey(spdateCoeffMap);
		if (spdateCoeff != null && spdateCoeff.getAddnotesCoeff() != null) {
			addnotesCoeff = spdateCoeff.getAddnotesCoeff().doubleValue();
			coeffStrategy *= addnotesCoeff;
		}

		cashStrategy = cashReqAmt * coeffStrategy;
		return cashStrategy;
	}

	/**
	 * 向上取整计算调整设备加钞量
	 *
	 * @param cash
	 * @param devMaxAddnotesAmt
	 * @return
	 */
	private int ceilDevAmt(int cash, int devMaxAddnotesAmt) {
		//加钞步长
		SysParamDTO paramDTO = sysParamResource.qrySysParamByName("addnotesAmountStep");
		// TODO: 熔断控制
//		int addnotesAmountStep = StringUtil.objectToInt(sysParamMapper.qryValueByName("addnotesAmountStep"));
		int addnotesAmountStep = StringUtil.objectToInt(paramDTO.getParamValue());
		if (addnotesAmountStep == -1) {
			addnotesAmountStep = 10000;
		}

		int justified = cash;
		//向上修正加钞量为addnotesAmountStep的整倍数
		if (justified < devMaxAddnotesAmt && justified % addnotesAmountStep != 0) {
			justified = (justified / addnotesAmountStep + 1) * addnotesAmountStep;
		}
		if (justified > devMaxAddnotesAmt) {
			log.debug("加钞预估量[" + justified + "]超过设备最大加钞量，修正为设备最大加钞量[" + devMaxAddnotesAmt + "]");
			justified = devMaxAddnotesAmt;
		}
		return justified;
	}
}
