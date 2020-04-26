package com.zjft.microservice.treasurybrain.linecenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.HolidayLineRunDTO;

import java.text.ParseException;

/**
 * @author 吴朋
 * @since 2019/9/25
 */
public interface HolidayLineRunService {
	/**
	 * 节假日线路运行图规划
	 */
	DTO manageHolidayLineRunMap(HolidayLineRunDTO holidayLineRunDTO) throws ParseException;
}
