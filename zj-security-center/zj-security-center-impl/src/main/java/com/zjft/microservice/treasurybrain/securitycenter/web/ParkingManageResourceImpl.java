package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.securitycenter.service.SampleMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhangjs
 * @since 2019/8/28 18:51
 */
@Slf4j
@RestController
public class ParkingManageResourceImpl implements ParkingManageResource {

	@Resource
	private SampleMessageService parkingManageService;

	@Override
	public DTO parkingGuidePushMessage(Map<String, Object> paramMap) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"泊车引导失败");
		try{
			log.info(paramMap.toString());
			log.info(paramMap.get("ParkingGuideList").toString());
			ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>)paramMap.get("ParkingGuideList");
			String parkingGuideType = StringUtil.parseString(paramMap.get("ParkingGuideType"));
//			log.info(list.toString());

			String type = "";
			if("callOutType".equals(parkingGuideType)) {
				type = "parkingCall";
			}else if("callEnterType".equals(parkingGuideType)) {
				type = "enterType";
			}else if ("callLeaveType".equals(parkingGuideType)){
				type = "leaveType";
			}

			Map<String,Object> retMap = new HashMap<>();
			for(int i= 0;i < list.size();i++) {
				LinkedHashMap parkingGuide = (LinkedHashMap)list.get(i);

                String parkingCarNo =  StringUtil.parseString(parkingGuide.get("parkingCarNo"));
				String parkingCarType =  StringUtil.parseString(parkingGuide.get("parkingCarType"));
				String parkingCarLeaveDate =  StringUtil.parseString(parkingGuide.get("parkingCarLeaveDate"));
				String parkingCarEnterDate =  StringUtil.parseString(parkingGuide.get("parkingCarEnterDate"));
				String lineName = StringUtil.parseString(parkingGuide.get("lineName"));
				int location = Integer.parseInt(StringUtil.parseString(parkingGuide.get("location")));

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("parkingCarNo", parkingCarNo);
				params.put("parkingCarType", parkingCarType);
				params.put("parkingCarLeaveDate", parkingCarLeaveDate);
				params.put("parkingCarEnterDate", parkingCarEnterDate);
				params.put("lineName", lineName);
				params.put("location", location);
				params.put("type", type);
                retMap = parkingManageService.parkingGuidePushMessage(JSONUtil.createJsonString(params));

			}

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

		}
		catch(Exception e) {
			log.error("泊车引导失败", e);
			dto.setRetException("泊车引导失败!");
		}
		return dto;
	}
}
