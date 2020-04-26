package com.zjft.microservice.treasurybrain.timejob.jobs;

import com.zjft.microservice.quartz.common.ResultEnum;
import com.zjft.microservice.quartz.jobs.BaseZjJob;
import com.zjft.microservice.treasurybrain.common.util.CfgProperty;
import com.zjft.microservice.treasurybrain.timejob.dto.CityInfoDTO;
import com.zjft.microservice.treasurybrain.timejob.dto.CityWeatherDTO;
import com.zjft.microservice.treasurybrain.timejob.service.WeatherJobService;
import com.zjft.microservice.treasurybrain.timejob.util.HttpUtil;
import com.zjft.microservice.treasurybrain.timejob.util.WeatherParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 常 健
 * @since 2019/07/16
 */
@Slf4j
public class WeatherJob extends BaseZjJob {

	@Resource
	private WeatherJobService weatherJobService;

	@Override
	protected ResultEnum taskPerform(JobExecutionContext jobExecutionContext) throws Exception {

		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		String cityNo = jobDetail.getJobDataMap().getString("cityNo");
		String weatherlUrl = CfgProperty.getProperty("weatherlUrl");
		String etouchUrl = CfgProperty.getProperty("etouchUrl");

		CityInfoDTO city = weatherJobService.getCityInfo(cityNo);
		boolean flag = false;
		List<CityWeatherDTO> weatherList = new ArrayList<>();

		if (weatherlUrl != null) {
			try {
				String html = HttpUtil.sendHttpGet(String.format(weatherlUrl, city.getWeatherId()));
				log.info("page crawling start.....");
				weatherList = WeatherParseUtil.getWeatherByHtml(html, city);
				log.debug(weatherList.toString());
				flag = true;
			} catch (Exception e) {
				log.error("weather page crawling failed：" + e);
				return ResultEnum.FAIL;
			}
		}
		if (!flag && etouchUrl != null) {
			String url = String.format(etouchUrl, city.getWeatherId());
			try {
				log.info("weather api crawling start......");
				weatherList = WeatherParseUtil.getWeatherByApi(url, city);
				log.debug(weatherList.toString());
			} catch (Exception e) {
				log.error("weather api crawling failed：" + e);
				return ResultEnum.FAIL;
			}
		}

		try {
			if (weatherList.size() == 0) {
				log.error("获取天气信息失败");
				return ResultEnum.FAIL;
			}
			for (CityWeatherDTO wet : weatherList) {
				String cityId = wet.getCityId();
				String weatherDate = wet.getWeatherDate();
				int insertFlag = 0;
				int updateFlag = 0;
				//判断是否存在当天天气信息
				boolean x = weatherJobService.exist(cityId, weatherDate);
				if (x) {
					//update
					updateFlag = weatherJobService.updateCityWeatherInfo(wet);
				} else {
					//insert
					insertFlag = weatherJobService.addCityWeatherInfo(wet);
				}
				if (insertFlag == 0 && updateFlag == 0) {
					return ResultEnum.FAIL;
				}
			}
		} catch (Exception e) {
			log.error("天气更新异常：", e);
			return ResultEnum.EXCEPTION;
		}
		return ResultEnum.SUCCEED;
	}
}
