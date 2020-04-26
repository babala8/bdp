package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerInfo;
import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerTimeDO;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.CallCustomerInnerResource;
import com.zjft.microservice.treasurybrain.linecenter.dto.AddCallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeListDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.CallCustomerInfoConverter;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.CallCustomerTimeDTOConverter;
import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerTimePO;
import com.zjft.microservice.treasurybrain.channelcenter.repository.CallCustomerTimeMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.CallCustomerTimeService;
import com.zjft.microservice.treasurybrain.channelcenter.web.CallCustomerResource;
import com.zjft.microservice.treasurybrain.common.domain.CallCustomerTime;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.*;

import com.zjft.microservice.treasurybrain.linecenter.web.OnSiteCollectionResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author zhangjs
 * @since 2019/9/24 10:54
 */
@Slf4j
@Service
public class CallCustomerTimeServiceImpl implements CallCustomerTimeService {

	@Resource
	CallCustomerInnerResource callCustomerInnerResource;

	@Resource
	CallCustomerTimeMapper callCustomerTimeMapper;

	@Resource
	OnSiteCollectionResource onSiteCollectionResource;

	@Override
	public CallCustomerTimeListDTO qryByCustomerNo(String customerNo) {

		CallCustomerInfo callCustomerInfo = CallCustomerInfoConverter.INSTANCE.dto2domain(callCustomerInnerResource.selectByPrimaryKey(customerNo));
		CallCustomerTimeListDTO callCustomerTimeListDTO = new CallCustomerTimeListDTO();
		if(null!=callCustomerInfo && !StringUtil.isNullorEmpty(callCustomerInfo.getCustomerNo())){
			//查询上门客户信息
			callCustomerTimeListDTO.setCustomerNo(callCustomerInfo.getCustomerNo());
			callCustomerTimeListDTO.setCallClrPeriod(callCustomerInfo.getCallClrPeriod());
			//查询上门收款时间段信息
			List<CallCustomerTimeDO> callCustomerTimeDOS = callCustomerTimeMapper.qryByCustomerNo(customerNo);
			List<CallCustomerTimeDTO> callCustomerTimeDTOS =  CallCustomerTimeDTOConverter.INSTANCE.do2dto(callCustomerTimeDOS);
			callCustomerTimeListDTO.setTimeList(callCustomerTimeDTOS);
			callCustomerTimeListDTO.setResult(RetCodeEnum.SUCCEED);

		}else{
			callCustomerTimeListDTO.setResult(RetCodeEnum.AUDIT_OBJECT_DELETED);
		}
		return callCustomerTimeListDTO;
	}

	/**
	 * 加钞周期调整，需要同步生成线路运行图失败
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO modCallCustomerTime(CallCustomerTimeListDTO dto) throws ParseException {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "调整失败");

		String customerNo = StringUtil.parseString(dto.getCustomerNo());
		int callClrPeriod = StringUtil.objectToInt(dto.getCallClrPeriod());

		String timeList = JSONUtil.createJsonString(dto.getTimeList());

		CallCustomerInfo callCustomerInfo = CallCustomerInfoConverter.INSTANCE.dto2domain(callCustomerInnerResource.selectByPrimaryKey(customerNo));
		if (callCustomerInfo == null) {
			dto1.setRetCode(RetCodeEnum.AUDIT_OBJECT_DELETED.getCode());
			dto1.setRetMsg("修改上门收款周期接口异常!对象已不存在");
			return dto1;
		}

		//查询设备的金库
//		String clrCenterNo = devBaseInfo.getClrCenterNo();

		callCustomerInfo.setCustomerNo(customerNo);
		callCustomerInfo.setCallClrPeriod(callClrPeriod);

		callCustomerInnerResource.updateByPrimaryKeySelective(CallCustomerInfoConverter.INSTANCE.domain2dto(callCustomerInfo));

		//查询上门收款原有线路
		List<String> lineNoResult = callCustomerTimeMapper.selectlineNoList(customerNo);
		List<String> lineNoList = new ArrayList<String>();
		if (lineNoResult.size() > 0) {
			for (String callCustomerLine : lineNoResult) {
				if (!lineNoList.contains(callCustomerLine)) {
					log.debug("更新线路" + callCustomerLine);
					lineNoList.add(callCustomerLine);
				}
			}
		}

		//删除原有上门收款周期信息
		callCustomerTimeMapper.deleteByCustomerNo(customerNo);
		//添加新的上门收款周期信息
		if (timeList != null && !"".equals(timeList)) {
			JSONArray jsonArray = JSONUtil.parseJSONArray(timeList);
			for (Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
				CallCustomerTime callCustomerTime = new CallCustomerTime();
				callCustomerTime.setClrTimeInterval(StringUtil.objectToInt(jsonObject.get("clrTimeInterval")));
				callCustomerTime.setArrivalTime(StringUtil.parseString(jsonObject.get("arrivalTime")));
				String callCustomerLine = StringUtil.parseString(jsonObject.get("callCustomerLine"));
				callCustomerTime.setCallCustomerLine(callCustomerLine);
				callCustomerTime.setClrDay(StringUtil.objectToInt(jsonObject.get("clrDay")));
				callCustomerTime.setCustomerNo(customerNo);

				if (!lineNoList.contains(callCustomerLine)) {
					log.debug("更新线路" + callCustomerLine);
					lineNoList.add(callCustomerLine);
				}
				callCustomerTimeMapper.insert(callCustomerTime);
			}
		}

		AddCallCustomerLineRunDTO addCallCustomerLineRunDTO = new AddCallCustomerLineRunDTO();
		addCallCustomerLineRunDTO.setLineNos(lineNoList);
		addCallCustomerLineRunDTO.setTheYearMonth(CalendarUtil.getSysTimeYM());

		DTO dto2 = onSiteCollectionResource.addAndCoverLineRun(addCallCustomerLineRunDTO);

		Map<String, Object> retMap = new HashMap<>();
		retMap.put("retCode",dto2.getRetCode());
		retMap.put("retMsg",dto2.getRetMsg());

		dto1.setRetCode(StringUtil.parseString(retMap.get("retCode")));
		dto1.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		return dto1;
	}

	@Override
	public List<CallCustomerTimeDTO> qryByLineAndWeekDay(Map<String, Object> map) {
		List<CallCustomerTimePO> callCustomerTimePOS = callCustomerTimeMapper.qryByLineNoAndWeekDay(map);
		List<CallCustomerTimeDTO> callCustomerTimeDTOS = CallCustomerTimeDTOConverter.INSTANCE.po2dto(callCustomerTimePOS);
		return callCustomerTimeDTOS;
	}

}
