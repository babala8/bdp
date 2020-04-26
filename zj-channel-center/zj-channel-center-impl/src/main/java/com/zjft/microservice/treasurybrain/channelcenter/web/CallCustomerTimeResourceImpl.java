package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.AddCallCustomerLineRunDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeListDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.CallCustomerTimeService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/17 10:30
 */

@Slf4j
@RestController
public class CallCustomerTimeResourceImpl implements CallCustomerTimeResource {

	@Resource
	CallCustomerTimeService callCustomerTimeService;

	@Override
	public CallCustomerTimeListDTO qryByCustomerNo(String customerNo) {
		try{
			return callCustomerTimeService.qryByCustomerNo(customerNo);
		}catch (Exception e){
			log.error("查询单个上门客户的周期信息发生异常：",e);
			return new CallCustomerTimeListDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	/**
	 * 加钞周期调整，需要同步生成线路运行图失败
	 */
	public DTO modCallCustomerTime(CallCustomerTimeListDTO dto) {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "调整失败");
		try {
			dto1 = callCustomerTimeService.modCallCustomerTime(dto);
		} catch (Exception e) {
			dto1.setRetException("上门收款周期调整失败！");
			log.error("上门收款周期调整失败", e);
		}
		return dto1;
	}

	@Override
	public List<CallCustomerTimeDTO> qryByLineNoAndWeekDate(Map<String, Object> map) {
		return callCustomerTimeService.qryByLineAndWeekDay(map);
	}

}
