package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlan;
import com.zjft.microservice.treasurybrain.business.domain.SimpleDevInfo;
import com.zjft.microservice.treasurybrain.business.dto.DevForChooseDTO;
import com.zjft.microservice.treasurybrain.business.service.AmtPredictService;
import com.zjft.microservice.treasurybrain.business.service.DevChooseService;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevBaseInfoInnerResource;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.DevCatalog;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum.ModName;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.dto.EstDTO;
import com.zjft.microservice.treasurybrain.param.dto.KeyDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import com.zjft.microservice.treasurybrain.param.web.DevconfigResource;
import com.zjft.microservice.treasurybrain.param.web.SysParamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 设备选择
 *
 * @author 韩通
 * @author 谢菊花
 * @author 徐全发
 * @since 2019-03-14
 */
@Slf4j
@Service
public class DevChooseServiceImpl implements DevChooseService {

//	@Resource
//	private DevStatusResource devStatusResource;

	@Resource
	private SysParamResource sysParamResource;

	@Resource
	private DevBaseInfoInnerResource devBaseInfoInnerResource;

	@Resource
	private AmtPredictService amtPredictService;

	@Resource
	private DevconfigResource devconfigResource;

	/**
	 * 因发现最后传输数据的时候没有传availableAmt，此方法已不需要
	 */
//	private List<Map<String, Object>> getAvailableAmt(String devNos) {
//		log.debug("获取余钞量，params：" + devNos);
//		List<Map<String, Object>> availableAmt = null;
//		String devNosSb = StringUtil.parseString(devNos).replace("\'", "");
//		try {
//			if (!"".equals(devNosSb)) {
////				availableAmt = devStatusTableMapper.queryForList(devNosSb);
//				availableAmt = devStatusResource.queryForList(devNosSb);
//			}
//		} catch (Exception e) {
//			log.error("获取余钞量失败：", e);
//		}
//		return availableAmt;
//	}

	private SimpleDevInfo devMap2BaseInfo(Map<String, Object> devMap) {
		SimpleDevInfo simpleDevInfo = new SimpleDevInfo(StringUtil.parseString(devMap.get("devNo")));
		simpleDevInfo.setDevCatalog(StringUtil.objectToInt(devMap.get("devCatalog")));
		simpleDevInfo.setCycleFlag(StringUtil.objectToInt(devMap.get("cycleFlag")));
		simpleDevInfo.setDevStantardSize(StringUtil.objectToInt(devMap.get("devStantardSize")));
		simpleDevInfo.setCashboxLimit(StringUtil.parseString(devMap.get("cashboxLimit")));
		return simpleDevInfo;
	}

	/**
	 * 计算设备预测型参数评分分值,保留两位小数
	 *
	 * @param time001   设备余钞剩余使用时长，单位：小时
	 * @param weight001 设备余钞剩余使用时长权重，[0-1]
	 * @param time002   设备距上次加钞的间隔时长，单位：小时
	 * @param weight002 设备距上次加钞的间隔时长权重，[0-1]
	 * @return String
	 * @author YT
	 * @since 2012-12-26
	 */
	private String calcEstScore(double time001, double weight001, double time002, double weight002) {
		DecimalFormat format = new DecimalFormat(".######");
		if (time001 == 0) {
//			weight001 = 0;
			time001 = 1;
		}
		double estScore = (weight001 / time001) + (time002 * weight002);
		return format.format(estScore);
	}

	/**
	 * 此方法用于连接availableAmt与其他数据，因发现最后传输数据的时候没有传availableAmt，此方法已不需要
	 */
//	private List<Map<String, Object>> combineListById(List<Map<String, Object>> list1, List<Map<String, Object>> list2, String ID1, String ID2) {
//		log.debug("--------------combineListById-------------");
//		log.debug("params--->list1:" + list1 + "   list2:" + list2 + "   ID:" + ID1 + ID2);
//		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
//		try {
//			for (Map<String, Object> aMap : list1) {
//				for (Map<String, Object> aMap2 : list2) {
//					if (aMap2.get(ID1).equals(aMap.get(ID2))) {
//						aMap.putAll(aMap2);
//					}
//				}
//				newList.add(aMap);
//			}
//		} catch (Exception e) {
//			log.error("组装list失败： ", e);
//		}
//		log.debug("newList--->" + newList);
//		return newList;
//	}


	@Override
	public List<DevForChooseDTO> qryAddnotesPlanDevsForKey(AddnotesPlan addnotesPlan, String param) {
		String clrCenterNo = addnotesPlan.getClrCenterNo();
		Integer lineMode = addnotesPlan.getLineMode();
		String lineNo = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
		String awayFlag = addnotesPlan.getNote();

		List<DevForChooseDTO> retList = new ArrayList<>();
		List<DevForChooseDTO> tempList = new ArrayList<>();
		//加钞设备数量总上限约束
//		int addnotesCountLimit = StringUtil.objectToInt(sysParamMapper.qryValueByName("addnotesCountLimit"));
		int addnotesCountLimit = StringUtil.objectToInt(sysParamResource.qrySysParamByName("addnotesCountLimit").getParamValue());

		if (addnotesCountLimit == -1) {
			addnotesCountLimit = 0;
		}
		//单条线路加钞设备数量上限约束
//		int addnotesCountLimitOneLine = StringUtil.objectToInt(sysParamMapper.qryValueByName("addnotesCountLimitOneLine"));
		int addnotesCountLimitOneLine = StringUtil.objectToInt(sysParamResource.qrySysParamByName("addnotesCountLimitOneLine").getParamValue());
		if (addnotesCountLimitOneLine == -1) {
			addnotesCountLimitOneLine = 0;
		}

		if (addnotesCountLimit <= 0 || addnotesCountLimitOneLine <= 0) {
			log.info("加钞设备数量上限约束不合法!");
			return null;
		}
		//本次筛选 上限约束：默认:加钞设备数量总上限约束
		int selectLimit = addnotesCountLimit;
		//某一条线路进行筛选
		if (lineMode == 2 && !lineNo.equals("-1")) {
			selectLimit = addnotesCountLimitOneLine;
		}
		if (selectLimit > 0) {
			//获取决定型事件设备列表
//			List<DevChsKeyEvent> keyEventParam = devChsKeyEventMapper.getKeyEventByClrNo(clrCenterNo);
			ObjectDTO keyEvents = devconfigResource.getDevconfig(clrCenterNo);
			Map<String,Object> keyEventParamMap = (Map<String,Object>)keyEvents.getElement();
			List<KeyDTO> keyEventParam = (List<KeyDTO>)keyEventParamMap.get("keyList");
			StringBuilder devNosSb = new StringBuilder("");
			for (KeyDTO devChsKeyEvent : keyEventParam) {
				if (devChsKeyEvent == null || devChsKeyEvent.getIsValid() != 1) {
					continue;
				}
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("clrCenterNo", clrCenterNo);
				params.put("lineMode", lineMode);
				params.put("lineNo", lineNo);
				params.put("awayFlag", awayFlag);
				params.put("eventNo", devChsKeyEvent.getEventNo());
				params.put("devNos", devNosSb.toString());
				params.put("orgNo", param);
				if ("001".equals(devChsKeyEvent.getEventNo()) || "002".equals(devChsKeyEvent.getEventNo()) || "003".equals(devChsKeyEvent.getEventNo())) {
//					List<Map<String, Object>> list = devBaseInfoMapper.getKeyEventDevice(params);
					List<Map<String, Object>> list = devBaseInfoInnerResource.getKeyEventDevice(params);
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> retmap = list.get(i);
							String devNo = StringUtil.parseString(retmap.get("devNo"));
							String devCatalogName = StringUtil.parseString(retmap.get("devCatalogName"));
							String address = StringUtil.parseString(retmap.get("address"));
							String orgNo = StringUtil.parseString(retmap.get("orgNo"));
							String orgName = StringUtil.parseString(retmap.get("orgName"));
							String addnotesLine = StringUtil.parseString(retmap.get("addnotesLine"));
							String addnotesLineName = StringUtil.parseString(retmap.get("addnotesLineName"));

							DevForChooseDTO devForChooseDTO = new DevForChooseDTO();
							devForChooseDTO.setDevNo(devNo);
							devForChooseDTO.setDevCatalogName(devCatalogName);
							devForChooseDTO.setAddress(address);
							devForChooseDTO.setOrgNo(orgNo);
							devForChooseDTO.setOrgName(orgName);
							devForChooseDTO.setKeyEvent(devChsKeyEvent.getEventNo());
							devForChooseDTO.setKeyEventDetail(devChsKeyEvent.getEventName());
							devForChooseDTO.setLineNo(addnotesLine);
							devForChooseDTO.setLineName(addnotesLineName);
							tempList.add(devForChooseDTO);

							if ("".equals(devNosSb.toString())) {
								devNosSb.append("'").append(devNo).append("'");
							} else {
								devNosSb.append(",'").append(devNo).append("'");
							}
						}
					}
				}
			}

//			List<Map<String, Object>> availableAmt = this.getAvailableAmt(devNosSb.toString());
//			tempList = this.combineListById(tempList, availableAmt, "DEVNO", "devNo");
		}

		if (tempList.size() >= selectLimit && "2".equals(awayFlag)) {
			retList.addAll(tempList.subList(0, selectLimit));
		} else {
			retList.addAll(tempList);
		}
		return retList;
	}

	@Override
	public List<DevForChooseDTO> qryAddnotesPlanDevsForPredict(AddnotesPlan addnotesPlan, String param) {
		List<DevForChooseDTO> retList = new ArrayList<>();
		String clrCenterNo = addnotesPlan.getClrCenterNo();
		Integer lineMode = addnotesPlan.getLineMode();
		String lineNo = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
		String awayFlag = addnotesPlan.getNote();

		//加钞设备数量总上限约束
//		sysParamMapper.qryValueByName("addnotesCountLimit")
		SysParamDTO paramDTO = sysParamResource.qrySysParamByName("addnotesCountLimit");
		int addnotesCountLimit = StringUtil.objectToInt(paramDTO.getParamValue());
		if (addnotesCountLimit == -1) {
			addnotesCountLimit = 0;
		}
		//单条线路加钞设备数量上限约束
//		sysParamMapper.qryValueByName("addnotesCountLimitOneLine")
		SysParamDTO paramOneLine = sysParamResource.qrySysParamByName("addnotesCountLimitOneLine");
		int addnotesCountLimitOneLine = StringUtil.objectToInt(paramOneLine.getParamValue());
		if (addnotesCountLimitOneLine == -1) {
			addnotesCountLimitOneLine = 0;
		}

		if (addnotesCountLimit <= 0 || addnotesCountLimitOneLine <= 0) {
			log.info("加钞设备数量上限约束不合法!");
			return null;
		}
		//本次筛选 上限约束：默认:加钞设备数量总上限约束
		int selectLimit = addnotesCountLimit;
		//某一条线路进行筛选
		if (lineMode == 2 && !"-1".equals(lineNo)) {
			selectLimit = addnotesCountLimitOneLine;
		}
		if (selectLimit > 0) {
			//获取决定型与故障型事件设备列表
//			List<DevChsKeyEvent> keyEventParam = devChsKeyEventMapper.getKeyEventByClrNo(clrCenterNo);
			ObjectDTO keyEvents = devconfigResource.getDevconfig(clrCenterNo);
			Map<String,Object> keyEventParamMap = (Map<String,Object>)keyEvents.getElement();
			List<KeyDTO> keyEventParam = (List<KeyDTO>)keyEventParamMap.get("keyList");
			StringBuilder devNosSb = new StringBuilder("");
			for (KeyDTO devChsKeyEvent : keyEventParam) {
				if (devChsKeyEvent == null || devChsKeyEvent.getIsValid() != 1) {
					continue;
				}
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("clrCenterNo", clrCenterNo);
				params.put("lineMode", lineMode);
				params.put("lineNo", lineNo);
				params.put("awayFlag", awayFlag);
				params.put("eventNo", devChsKeyEvent.getEventNo());
				params.put("devNos", devNosSb.toString());
				params.put("orgNo", param);
				List<Map<String, Object>> list = null;
				if ("005".equals(devChsKeyEvent.getEventNo())) {
//					list = devBaseInfoMapper.getKeyEventDeviceForfault(params);
					list = devBaseInfoInnerResource.getKeyEventDeviceForfault(params);
				} else {
//					list = devBaseInfoMapper.getKeyEventDevice(params);
					list = devBaseInfoInnerResource.getKeyEventDevice(params);
				}
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> retmap = list.get(i);
						String devNo = StringUtil.parseString(retmap.get("devNo"));
						if ("".equals(devNosSb.toString())) {
							devNosSb.append("'").append(devNo).append("'");
						} else {
							devNosSb.append(",'").append(devNo).append("'");
						}
					}
				}
			}
			//获取预测型事件设备列表
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("lineMode", lineMode);
			params.put("lineNo", lineNo);
			params.put("awayFlag", awayFlag);
			params.put("devNos", devNosSb.toString());
			params.put("orgNo", param);
//			List<Map<String, Object>> list = devBaseInfoMapper.getAvaileAmtAndTimeInterval(params);
			List<Map<String, Object>> list = devBaseInfoInnerResource.getAvaileAmtAndTimeInterval(params);
			double weight001 = 0;//设备余钞剩余使用时长权重，[0-1]
			double weight002 = 0; //设备距上次加钞的间隔时长权重，[0-1]
//			List<DevChsEstParam> devChsEstparamList = devChsEstParamMapper.selectByClrNo(clrCenterNo);
			List<EstDTO> devChsEstparamList = (List<EstDTO>)keyEventParamMap.get("estList");
			for (EstDTO devChsEstParam : devChsEstparamList) {
				if ("001".equals(devChsEstParam.getEventNo())) {
					weight001 = devChsEstParam.getWeight().doubleValue();
				}
				if ("002".equals(devChsEstParam.getEventNo())) {
					weight002 = devChsEstParam.getWeight().doubleValue();
				}
			}
			int time001 = 0;  //设备余钞剩余使用时长，单位：小时
			int time002 = 0;  //设备距上次加钞的间隔时长，单位：小时
			double avg = 0;  //平均每小时取款量，单位：元/小时
			String estScore = "";  //设备预测加钞优先度分值,保留两位小数
			List<Object[]> resultList = new ArrayList<Object[]>();
			double tmpWeight001 = 0;

			//计算设备预测优先度分值
			for (Map<String, Object> devMap : list) {
				SimpleDevInfo simpleDevInfo = devMap2BaseInfo(devMap);
				Object[] devScore = new Object[11];
				String devNo = StringUtil.parseString(devMap.get("devNo"));
				if (DevCatalog.ATM.getId() == StringUtil.objectToInt(devMap.get("devCatalog"))
						|| StringUtil.objectToDouble(devMap.get("cycleFlag")) == 0) {
					//avg = 最近一个周日均取款量/24     向下取整
					tmpWeight001 = weight001;
					avg = 1.0 * StringUtil.objectToDouble(devMap.get("avgWdrAmt")) / 24;
					if (avg != 0) {
						if (devMap.get("availableAmt") == null) {
							tmpWeight001 = 0;
							time001 = 1;
							devScore[3] = "设备余钞量未知!";
						} else {
							time001 = (int) Math.round(Double.parseDouble(devMap.get("availableAmt").toString()) / avg);
							devScore[3] = "预测余钞剩余使用时长[" + time001 + "]小时!";
						}
					} else {
						tmpWeight001 = 0;
						time001 = 1;
						devScore[3] = "日均取款量为0!";
					}
				} else if (DevCatalog.CRS.getId() == StringUtil.objectToInt(devMap.get("devCatalog"))
						&& StringUtil.objectToInt(devMap.get("cycleFlag")) == 1) {
					//查询设备安全区间
					int[] threshold = amtPredictService.predictThresholds4Circle(simpleDevInfo);
					if (threshold == null || threshold.length != 2) {
						log.error("设备编号：[" + devNo + "]的设备的安全范围获取异常!");
						continue;
					}
					//大于上限
					if (threshold[1] < Integer.valueOf(devMap.get("availableAmt").toString())) {
						tmpWeight001 = weight001;
						avg = 1.0 * StringUtil.objectToDouble(devMap.get("avgDepAmt")) / 24;
						if (avg != 0) {
							if (devMap.get("availableAmt") == null) {
								tmpWeight001 = 0;
								time001 = 1;
								devScore[3] = "设备余钞量未知!";
							} else {
								time001 = (int) Math.round((StringUtil.objectToInt(devMap.get("devStantardSize"))
										- Double.parseDouble(devMap.get("availableAmt").toString())) / avg);
								devScore[3] = "预测钞箱未来[" + time001 + "]小时后将满!";
							}
						} else {
							tmpWeight001 = 0;
							time001 = 1;
							devScore[3] = "日均存款量为0!";
						}
						//小于下限
					} else if (threshold[0] > Integer.valueOf(devMap.get("availableAmt").toString())) {
						tmpWeight001 = weight001;
						avg = 1.0 * StringUtil.objectToDouble(devMap.get("avgWdrAmt")) / 24;
						if (avg != 0) {
							if (devMap.get("availableAmt") == null) {
								tmpWeight001 = 0;
								time001 = 1;
								devScore[3] = "设备余钞量未知!";
							} else {
								time001 = (int) Math.round(Double.parseDouble(devMap.get("availableAmt").toString()) / avg);
								devScore[3] = "预测余钞剩余使用时长[" + time001 + "]小时!";
							}
						} else {
							tmpWeight001 = 0;
							time001 = 1;
							devScore[3] = "日均取款量为0!";
						}
					} else if ("0".equals(awayFlag)) {
						devScore[3] = "设备余钞在安全范围内!";
						tmpWeight001 = 0;
						weight002 = 0;
					} else {
						log.info("设备编号：[" + devNo + "]的设备余钞在安全范围内!");
						continue;
					}
				} else {
					continue;
				}

				if (devMap.get("timeInterVal") == null || "".equals(devMap.get("timeInterVal"))) {
					time002 = 168;  //当无上次加钞时间时，时间间隔采用默认值：一周168小时
					devScore[3] = devScore[3] + "|距离上次加钞时长未知!";
				} else {
					time002 = StringUtil.objectToInt(devMap.get("timeInterVal"));
					devScore[3] = devScore[3] + "|距离上次加钞时长[" + time002 + "]小时!";
				}

				estScore = this.calcEstScore(time001, tmpWeight001, time002, weight002);

				devScore[0] = devNo;   //设备号
				devScore[1] = estScore; //预测优先度分值
				devScore[2] = 0;        //初始化辅助型分值为0
				devScore[4] = StringUtil.parseString(devMap.get("orgNo"));
				devScore[5] = StringUtil.parseString(devMap.get("orgName"));
				devScore[6] = StringUtil.parseString(devMap.get("address"));
				devScore[7] = StringUtil.parseString(devMap.get("devCatalogName"));
				devScore[8] = StringUtil.parseString(devMap.get("availableAmt"));
				devScore[9] = StringUtil.parseString(devMap.get("addnotesLine"));
				devScore[10] = StringUtil.parseString(devMap.get("addnotesLineName"));

				log.debug("devNo=" + devScore[0] + " score=" + devScore[1] + " aux=" + devScore[2]);
				resultList.add(devScore);
			}
			//对计算所得的设备加钞预测优先度分值进行排序
			Collections.sort(resultList, new Comparator<Object>() {
				@Override
				public int compare(Object obj1, Object obj2) {
					int sortFlag;
					double aScore = Double.parseDouble(((Object[]) obj1)[1].toString());
					double bScore = Double.parseDouble(((Object[]) obj2)[1].toString());
					if (aScore > bScore) {
						sortFlag = -1;
					} else if (aScore < bScore) {
						sortFlag = 1;
					} else {
						sortFlag = 0;
					}
					return sortFlag;
				}
			});

			if (resultList != null && resultList.size() != 0 && "2".equals(awayFlag)) {
				Object[] devScoreTest = (Object[]) resultList.get(0);
				double devScoreTest1 = StringUtil.ch2Double("" + devScoreTest[1]);
				if (devScoreTest1 == 0.0 && ((Integer)(devconfigResource.qryIsValidCountsByClrNo(clrCenterNo).getElement()) == 0)) {
					resultList.clear();
				}
			}

			int estSize = (resultList.size() > selectLimit && "2".equals(awayFlag)) ? selectLimit : resultList.size();
			for (int i = 0; i < estSize; i++) {
				Object[] obj = resultList.get(i);
//				Map<String, Object> devScoreMap = new HashMap<String, Object>();
//				devScoreMap.put("devNo", obj[0].toString());
//				devScoreMap.put("devCatalogName", obj[7].toString());
//				devScoreMap.put("address", obj[6].toString());
//				devScoreMap.put("orgNo", obj[4].toString());
//				devScoreMap.put("orgName", obj[5].toString());
//				devScoreMap.put("keyEvent", "");
//				devScoreMap.put("keyEventDetail", obj[3].toString());
//				devScoreMap.put("chsEstScore", new BigDecimal(obj[1].toString()));
//				devScoreMap.put("chsAuxScore", new BigDecimal(obj[2].toString()));
//				devScoreMap.put("availableAmt", obj[8].toString());
//				devScoreMap.put("lineNo", obj[9].toString());
//				devScoreMap.put("lineName", obj[10].toString());
//				retList.add(devScoreMap);

				DevForChooseDTO devForChooseDTO = new DevForChooseDTO();
				devForChooseDTO.setDevNo(obj[0].toString());
				devForChooseDTO.setDevCatalogName(obj[7].toString());
				devForChooseDTO.setAddress(obj[6].toString());
				devForChooseDTO.setOrgNo(obj[4].toString());
				devForChooseDTO.setOrgName(obj[5].toString());
				devForChooseDTO.setKeyEvent("");
				devForChooseDTO.setKeyEventDetail(obj[3].toString());
				devForChooseDTO.setChsEstScore(new BigDecimal(obj[1].toString()));
				devForChooseDTO.setChsAuxScore(new BigDecimal(obj[2].toString()));
				devForChooseDTO.setAvailableAmt(obj[8].toString());
				devForChooseDTO.setLineNo(obj[9].toString());
				devForChooseDTO.setLineName(obj[10].toString());
				retList.add(devForChooseDTO);
			}
			list.clear();
			resultList.clear();
		}

		return retList;
	}

	@Override
	public List<DevForChooseDTO> qryAddnotesPlanDevsForMaintain(AddnotesPlan addnotesPlan, String param) {
		String clrCenterNo = addnotesPlan.getClrCenterNo();
		Integer lineMode = addnotesPlan.getLineMode();
		String lineNo = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
		String awayFlag = addnotesPlan.getNote();

		List<DevForChooseDTO> retList = new ArrayList<>();
		List<DevForChooseDTO> tempList = new ArrayList<>();
		//加钞设备数量总上限约束
//		int addnotesCountLimit = StringUtil.objectToInt(sysParamMapper.qryValueByName("addnotesCountLimit"));
		int addnotesCountLimit = StringUtil.objectToInt(sysParamResource.qrySysParamByName("addnotesCountLimit").getParamValue());

		if (addnotesCountLimit == -1) {
			addnotesCountLimit = 0;
		}
		//单条线路加钞设备数量上限约束
//		int addnotesCountLimitOneLine = StringUtil.objectToInt(sysParamMapper.qryValueByName("addnotesCountLimitOneLine"));
		int addnotesCountLimitOneLine = StringUtil.objectToInt(sysParamResource.qrySysParamByName("addnotesCountLimitOneLine").getParamValue());

		if (addnotesCountLimitOneLine == -1) {
			addnotesCountLimitOneLine = 0;
		}

		if (addnotesCountLimit <= 0 || addnotesCountLimitOneLine <= 0) {
			log.info("加钞设备数量上限约束不合法!");
			return null;
		}
		//本次筛选 上限约束：默认:加钞设备数量总上限约束
		int selectLimit = addnotesCountLimit;
		//某一条线路进行筛选
		if (lineMode == 2 && !lineNo.equals("-1")) {
			selectLimit = addnotesCountLimitOneLine;
		}
		if (selectLimit > 0) {
			//获取维护型事件设备列表
//			List<DevChsKeyEvent> keyEventParam = devChsKeyEventMapper.getKeyEventByClrNo(clrCenterNo);
			ObjectDTO keyEvents = devconfigResource.getDevconfig(clrCenterNo);
			Map<String,Object> keyEventParamMap = (Map<String,Object>)keyEvents.getElement();
			List<KeyDTO> keyEventParam = (List<KeyDTO>)keyEventParamMap.get("keyList");
			StringBuilder devNosSb = new StringBuilder("");
			for (KeyDTO devChsKeyEvent : keyEventParam) {
				if (devChsKeyEvent == null || devChsKeyEvent.getIsValid() != 1) {
					continue;
				}
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("clrCenterNo", clrCenterNo);
				params.put("lineMode", lineMode);
				params.put("lineNo", lineNo);
				params.put("awayFlag", awayFlag);
				params.put("eventNo", devChsKeyEvent.getEventNo());
				params.put("devNos", devNosSb.toString());
				params.put("orgNo", param);
				if (!"001".equals(devChsKeyEvent.getEventNo()) && !"002".equals(devChsKeyEvent.getEventNo()) && !"003".equals(devChsKeyEvent.getEventNo()) && !"005".equals(devChsKeyEvent.getEventNo())) {
//					List<Map<String, Object>> list = devBaseInfoMapper.getKeyEventDevice(params);
					List<Map<String, Object>> list = devBaseInfoInnerResource.getKeyEventDevice(params);
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> retmap = list.get(i);
							String devNo = StringUtil.parseString(retmap.get("devNo"));
							String devCatalogName = StringUtil.parseString(retmap.get("devCatalogName"));
							String address = StringUtil.parseString(retmap.get("address"));
							String orgNo = StringUtil.parseString(retmap.get("orgNo"));
							String orgName = StringUtil.parseString(retmap.get("orgName"));
							String addnotesLine = StringUtil.parseString(retmap.get("addnotesLine"));
							String addnotesLineName = StringUtil.parseString(retmap.get("addnotesLineName"));

							DevForChooseDTO devForChooseDTO = new DevForChooseDTO();
							devForChooseDTO.setDevNo(devNo);
							devForChooseDTO.setDevCatalogName(devCatalogName);
							devForChooseDTO.setAddress(address);
							devForChooseDTO.setOrgNo(orgNo);
							devForChooseDTO.setOrgName(orgName);
							devForChooseDTO.setKeyEvent(devChsKeyEvent.getEventNo());
							devForChooseDTO.setKeyEventDetail(devChsKeyEvent.getEventName());
							devForChooseDTO.setLineNo(addnotesLine);
							devForChooseDTO.setLineName(addnotesLineName);
							tempList.add(devForChooseDTO);

							if ("".equals(devNosSb.toString())) {
								devNosSb.append("'").append(devNo).append("'");
							} else {
								devNosSb.append(",'").append(devNo).append("'");
							}
						}
					}
				} else if ("005".equals(devChsKeyEvent.getEventNo())) {
//					List<Map<String, Object>> list = devBaseInfoMapper.getKeyEventDeviceForfault(params);
					List<Map<String, Object>> list = devBaseInfoInnerResource.getKeyEventDeviceForfault(params);
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> retmap = list.get(i);
							String devNo = StringUtil.parseString(retmap.get("devNo"));
							String keyEventDetail = "";
							if (retmap.get("idcDeviceStatus") != null && retmap.get("idcDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.IDC.getId() + ",";
							}
							if (retmap.get("chkDeviceStatus") != null && retmap.get("chkDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.CHK.getId() + ",";
							}
							if (retmap.get("pbkDeviceStatus") != null && retmap.get("pbkDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.PBK.getId() + ",";
							}
							if (retmap.get("pinDeviceStatus") != null && retmap.get("pinDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.PIN.getId() + ",";
							}
							if (retmap.get("siuDeviceStatus") != null && retmap.get("siuDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.SIU.getId() + ",";
							}
							if (retmap.get("depDeviceStatus") != null && retmap.get("depDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.DEP.getId() + ",";
							}
							if (retmap.get("camDeviceStatus") != null && retmap.get("camDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.CAM.getId() + ",";
							}
							if (retmap.get("cimDeviceStatus") != null && retmap.get("cimDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.CIM.getId() + ",";
							}
							if (retmap.get("cdmDeviceStatus") != null && retmap.get("cdmDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.CDM.getId() + ",";
							}
							if (retmap.get("sprDeviceStatus") != null && retmap.get("sprDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.SPR.getId() + ",";
							}
							if (retmap.get("rprDeviceStatus") != null && retmap.get("rprDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.RPR.getId() + ",";
							}
							if (retmap.get("jprDeviceStatus") != null && retmap.get("jprDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.JPR.getId() + ",";
							}
							if (retmap.get("ttuDeviceStatus") != null && retmap.get("ttuDeviceStatus").toString().equals("FATAL")) {
								keyEventDetail = keyEventDetail + ModName.TTU.getId() + ",";
							}

							String devCatalogName = StringUtil.parseString(retmap.get("devCatalogName"));
							String address = StringUtil.parseString(retmap.get("address"));
							String orgNo = StringUtil.parseString(retmap.get("orgNo"));
							String orgName = StringUtil.parseString(retmap.get("orgName"));
							String addnotesLine = StringUtil.parseString(retmap.get("addnotesLine"));
							String addnotesLineName = StringUtil.parseString(retmap.get("addnotesLineName"));

							DevForChooseDTO devForChooseDTO = new DevForChooseDTO();
							devForChooseDTO.setDevNo(devNo);
							devForChooseDTO.setDevCatalogName(devCatalogName);
							devForChooseDTO.setAddress(address);
							devForChooseDTO.setOrgNo(orgNo);
							devForChooseDTO.setOrgName(orgName);
							devForChooseDTO.setKeyEvent(devChsKeyEvent.getEventNo());
							devForChooseDTO.setKeyEventDetail(devChsKeyEvent.getEventName());
							devForChooseDTO.setLineNo(addnotesLine);
							devForChooseDTO.setLineName(addnotesLineName);
							tempList.add(devForChooseDTO);

							if ("".equals(devNosSb.toString())) {
								devNosSb.append("'").append(devNo).append("'");
							} else {
								devNosSb.append(",'").append(devNo).append("'");
							}
						}
					}
				}
			}
		}

		if (tempList.size() >= selectLimit && "2".equals(awayFlag)) {
			retList.addAll(tempList.subList(0, selectLimit));
		} else {
			retList.addAll(tempList);
		}
		return retList;
	}

	@Override
	public List<DevForChooseDTO> qryAddnotesPlanDevsForRunPlan(AddnotesPlan addnotesPlan, String param) {
		String clrCenterNo = addnotesPlan.getClrCenterNo();
		Integer lineMode = addnotesPlan.getLineMode();
		String lineNo = addnotesPlan.getLineNo().replaceAll("\"|\\[|\\]", "");
		String awayFlag = addnotesPlan.getNote();
		String planAddnotesDate = addnotesPlan.getPlanAddnotesDate();

		List<DevForChooseDTO> retList = new ArrayList<>();
		List<DevForChooseDTO> tempList = new ArrayList<>();
		//加钞设备数量总上限约束
		int addnotesCountLimit = StringUtil.objectToInt(sysParamResource.qrySysParamByName("addnotesCountLimit").getParamValue());

		if (addnotesCountLimit == -1) {
			addnotesCountLimit = 0;
		}
		//单条线路加钞设备数量上限约束
		int addnotesCountLimitOneLine = StringUtil.objectToInt(sysParamResource.qrySysParamByName("addnotesCountLimitOneLine").getParamValue());

		if (addnotesCountLimitOneLine == -1) {
			addnotesCountLimitOneLine = 0;
		}

		if (addnotesCountLimit <= 0 || addnotesCountLimitOneLine <= 0) {
			log.info("加钞设备数量上限约束不合法!");
			return null;
		}
		//本次筛选 上限约束：默认:加钞设备数量总上限约束
		int selectLimit = addnotesCountLimit;
		//某一条线路进行筛选
		if (lineMode == 2 && !lineNo.equals("-1")) {
			selectLimit = addnotesCountLimitOneLine;
		}
		if (selectLimit > 0) {
			//获取计划型事件设备列表
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("lineMode", lineMode);
			params.put("lineNo", lineNo);
			params.put("awayFlag", awayFlag);
			params.put("orgNo", param);
			params.put("planAddnotesDate", planAddnotesDate.replaceAll("-", ""));

			List<Map<String, Object>> list = devBaseInfoInnerResource.getKeyEventDeviceForLineRun(params);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> retmap = list.get(i);
					String devNo = StringUtil.parseString(retmap.get("devNo"));

					String devCatalogName = StringUtil.parseString(retmap.get("devCatalogName"));
					String address = StringUtil.parseString(retmap.get("address"));
					String orgNo = StringUtil.parseString(retmap.get("orgNo"));
					String orgName = StringUtil.parseString(retmap.get("orgName"));
					String addnotesLine = StringUtil.parseString(retmap.get("addnotesLine"));
					String addnotesLineName = StringUtil.parseString(retmap.get("addnotesLineName"));

					DevForChooseDTO devForChooseDTO = new DevForChooseDTO();
					devForChooseDTO.setDevNo(devNo);
					devForChooseDTO.setDevCatalogName(devCatalogName);
					devForChooseDTO.setAddress(address);
					devForChooseDTO.setOrgNo(orgNo);
					devForChooseDTO.setOrgName(orgName);
					devForChooseDTO.setKeyEvent("999");
					devForChooseDTO.setKeyEventDetail("计划加钞");
					devForChooseDTO.setLineNo(addnotesLine);
					devForChooseDTO.setLineName(addnotesLineName);
					tempList.add(devForChooseDTO);

				}
			}
		}

		if (tempList.size() >= selectLimit && "2".equals(awayFlag)) {
			retList.addAll(tempList.subList(0, selectLimit));
		} else {
			retList.addAll(tempList);
		}
		return retList;
	}

}
