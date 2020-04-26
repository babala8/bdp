package com.zjft.microservice.treasurybrain.timejob.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/07/16
 */

@Data
public class CityWeatherDTO extends DTO {

	@ApiModelProperty(value = "城市编号")
	private String cityId;

	@ApiModelProperty(value = "城市名称")
	private String cityName;

	@ApiModelProperty(value = "日期")
	private String weatherDate;

	@ApiModelProperty(value = "天气编号")
	private String dayWeatherId;

	@ApiModelProperty(value = "天气类型")
	private String dayWeather;

	@ApiModelProperty(value = "温度")
	private float dayTemp;

	@ApiModelProperty(value = "风向")
	private String dayWind;

	@ApiModelProperty(value = "风级")
	private String dayWindComp;

}
