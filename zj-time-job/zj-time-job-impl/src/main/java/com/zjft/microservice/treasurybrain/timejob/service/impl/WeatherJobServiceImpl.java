package com.zjft.microservice.treasurybrain.timejob.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.timejob.dto.CityInfoDTO;
import com.zjft.microservice.treasurybrain.timejob.dto.CityWeatherDTO;
import com.zjft.microservice.treasurybrain.timejob.mapstruct.CityInfoConverter;
import com.zjft.microservice.treasurybrain.timejob.mapstruct.CityWeatherConverter;
import com.zjft.microservice.treasurybrain.timejob.po.CityInfoPO;
import com.zjft.microservice.treasurybrain.timejob.po.CityWeatherPO;
import com.zjft.microservice.treasurybrain.timejob.repository.CityInfoMapper;
import com.zjft.microservice.treasurybrain.timejob.repository.CityWeatherMapper;
import com.zjft.microservice.treasurybrain.timejob.service.WeatherJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/07/16
 */
@Slf4j
@Service
public class WeatherJobServiceImpl implements WeatherJobService {

	@Resource
	private CityInfoMapper cityInfoMapper;

	@Resource
	private CityWeatherMapper cityWeatherMapper;

	/**
	 * 通过cityNo获取地市信息
	 */
	@Override
	public CityInfoDTO getCityInfo(String cityNo) {
		CityInfoDTO cityInfoDTO = new CityInfoDTO();
		cityInfoDTO.setResult(RetCodeEnum.FAIL);
		try {
			CityInfoPO cityInfoPO;
			cityInfoPO = cityInfoMapper.getCityInfo(cityNo);
			cityInfoDTO = CityInfoConverter.INSTANCE.domain2dto(cityInfoPO);
			cityInfoDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("获取地市信息异常：", e);
			cityInfoDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return cityInfoDTO;
	}

	/**
	 * 获取地市信息List
	 */
	@Override
	public ListDTO<CityInfoDTO> getCityInfoList() {
		ListDTO<CityInfoDTO> list = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			List<CityInfoPO> doList = cityInfoMapper.getCityInfoList();
			List<CityInfoDTO> dtoList = CityInfoConverter.INSTANCE.domain2dto(doList);
			list.setRetList(dtoList);
			list.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("获取地市信息异常：", e);
			list.setResult(RetCodeEnum.EXCEPTION);
		}
		return list;
	}

	/**
	 * 判断是否存在天气信息
	 */
	@Override
	public boolean exist(String cityId, String weatherDate) {
		boolean flag = false;
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap = cityWeatherMapper.getWeatherKey(cityId, weatherDate);
			if (retMap != null && retMap.size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error("查询是否存在异常：", e);
		}
		return flag;
	}

	/**
	 * 新增天气信息
	 */
	@Override
	public int addCityWeatherInfo(CityWeatherDTO cityWeatherDTO) {
		int x = 0;
		try {
			CityWeatherPO cityWeatherPO = CityWeatherConverter.INSTANCE.dto2domain(cityWeatherDTO);
			int y = cityWeatherMapper.insert(cityWeatherPO);
			if (y == 1) {
				x = 1;
			}
		} catch (Exception e) {
			log.error("插入天气信息异常：", e);
		}
		return x;
	}

	/**
	 * 更新天气信息
	 */
	@Override
	public int updateCityWeatherInfo(CityWeatherDTO cityWeatherDTO) {
		int x = 0;
		try {
			CityWeatherPO cityWeatherPO = CityWeatherConverter.INSTANCE.dto2domain(cityWeatherDTO);
			int y = cityWeatherMapper.updateByPrimaryKey(cityWeatherPO);
			if (y == 1) {
				x = 1;
			}
		} catch (Exception e) {
			log.error("更新天气信息异常：", e);
		}
		return x;
	}

}
