package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.HolidayLineRunDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

/**
 * @author 吴朋
 * @since 2019/9/25 21:30
 */
@Api(value = "线路中心：节假日线路管理", tags = "线路中心：节假日线路管理")
public interface HolidayLineRunResource {
	String PREFIX = "${line-center:}/v2/HolidayLineRunMap";

	@PutMapping(PREFIX)
	@ApiOperation(value = "节假日线路管理", notes = "节假日线路管理")
	DTO manageHolidayLineRunMap(@RequestBody HolidayLineRunDTO holidayLineRunDTO) throws ParseException;
}
