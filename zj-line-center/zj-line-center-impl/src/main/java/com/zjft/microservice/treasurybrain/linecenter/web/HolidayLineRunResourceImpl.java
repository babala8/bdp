package com.zjft.microservice.treasurybrain.linecenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.HolidayLineRunDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.HolidayLineRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author 吴朋
 * @since 2019/9/25 21:30
 */
@Slf4j
@RestController
	public class HolidayLineRunResourceImpl implements HolidayLineRunResource{

	@Resource
	private HolidayLineRunService holidayLineRunService;

	/**
	 * 节假日线路运行图规划
	 */
	@Override
	public DTO manageHolidayLineRunMap(HolidayLineRunDTO holidayLineRunDTO) throws ParseException {
		return holidayLineRunService.manageHolidayLineRunMap(holidayLineRunDTO);
	}
}
