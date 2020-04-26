package com.zjft.microservice.treasurybrain.timejob.service;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.timejob.dto.CityInfoDTO;
import com.zjft.microservice.treasurybrain.timejob.dto.CityWeatherDTO;

/**
 * @author 常 健
 * @since 2019/07/16
 */
public interface WeatherJobService {

	/**
	 * 通过cityNo获取地市信息
	 */
	CityInfoDTO getCityInfo(String cityNo);

	/**
	 * 获取地市信息List
	 */
	ListDTO<CityInfoDTO> getCityInfoList();

	/**
	 * 判断是否存在天气信息
	 */
	boolean exist(String cityId, String weatherDate);

	/**
	 * 新增天气信息
	 */
	int addCityWeatherInfo(CityWeatherDTO cityWeatherDTO);

	/**
	 * 更新天气信息
	 */
	int updateCityWeatherInfo(CityWeatherDTO cityWeatherDTO);

}
