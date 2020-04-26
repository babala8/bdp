package com.zjft.microservice.treasurybrain.usercenter.web_inner;

import com.zjft.microservice.treasurybrain.usercenter.dto.OutSourcingLineMapInnerDTO;
import com.zjft.microservice.treasurybrain.usercenter.service.ScheduleService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 常 健
 * @since 2019/12/31
 */
@RestController
public class OutSourcingInnerResourceImpl implements OutSourcingInnerResource{


	@Resource
	private ScheduleService scheduleService;

	@Override
	public List<OutSourcingLineMapInnerDTO> qryInfoByDutyDate(String dutyDate) {
		return scheduleService.qryInfoByDutyDate(dutyDate);
	}

	@Override
	public List<String> qryOpNameByLineRunNo(String lineRunNo) {
		return scheduleService.qryOpNameByLineRunNo(lineRunNo);
	}
}
