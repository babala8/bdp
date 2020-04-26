package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

/**
 * @author 常 健
 * @since 2019/07/16
 */
@Data
public class CityWeatherPO {
	private String cityId;

	private String cityName;

	private String weatherDate;

	private String dayWeatherId;

	private String dayWeather;

	private float dayTemp;

	private String dayWind;

	private String dayWindComp;
}
