package com.zjft.microservice.treasurybrain.timejob.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.timejob.dto.CityInfoDTO;
import com.zjft.microservice.treasurybrain.timejob.dto.CityWeatherDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 常 健
 * @since 2019/07/16
 * 类说明 天气转换
 */
public class WeatherParseUtil {

	public static List<CityWeatherDTO> getWeatherByHtml(String html, CityInfoDTO city) {
		List<CityWeatherDTO> weatherList = new ArrayList<CityWeatherDTO>();
		Document document = Jsoup.parse(html);
		Elements elements = document.select("div#7d > ul > li");
		int interval = 0;
		for (Element element : elements) {
			CityWeatherDTO weather = new CityWeatherDTO();
			weather.setCityId(city.getCityNo());
			weather.setCityName(city.getCityName());
			weather.setWeatherDate(DateUtil.getDateByInterval(interval));
			weather.setDayWeatherId(getDayWeatherId(element));
			weather.setDayWeather(getDayWeather(element));
			weather.setDayTemp(getDayTemp(element));
			weather.setDayWind(getDayWind(element));
			weather.setDayWindComp(getDayWindComp(element));
			weather.setDayWeatherId(city.getWeatherId());

			weatherList.add(weather);
			weather.toString();
			interval++;
		}
		return weatherList;
	}

	public static List<CityWeatherDTO> getWeatherByApi(String url, CityInfoDTO city) {
		String response = HttpUtil.sendHttpGet(url);

		List<CityWeatherDTO> weatherList = new ArrayList<CityWeatherDTO>();

		if (response != null && response != "") {
			JSONObject respObject = JSONObject.parseObject(response);
			int status = respObject.getInteger("status");
			if (status == 1000) {
				JSONObject dataOjbect = respObject.getJSONObject("data");
				JSONArray forecastArray = dataOjbect.getJSONArray("forecast");
				int interval = 0;
				for (int i = 0; i < forecastArray.size(); i++) {
					JSONObject forecastObj = (JSONObject) forecastArray.get(i);
					int low = getDayTemp(forecastObj.getString("low"));
					int high = getDayTemp(forecastObj.getString("high"));
					CityWeatherDTO weather = new CityWeatherDTO();
					weather.setCityId(city.getCityNo());
					weather.setCityName(city.getCityName());
					weather.setWeatherDate(DateUtil.getDateByInterval(interval));
					weather.setDayWeatherId(WeatherUtil.getWeatherIdByName(forecastObj.getString("type")));
					weather.setDayWeather(forecastObj.getString("type"));
					weather.setDayTemp((low + high) / 2);
					weather.setDayWind(forecastObj.getString("fengxiang"));
					weather.setDayWindComp(getDayWindComp(forecastObj.getString("fengli")));
					weather.setDayWeatherId(city.getWeatherId());

					weatherList.add(weather);
					weather.toString();
					interval++;
				}
			}

		}
		return weatherList;
	}

	public static String getDayWeatherId(Element element) {
		String classStr = element.select("big").get(0).attr("class");
		String dayWeatherId = classStr.substring(classStr.length() - 2, classStr.length());
		return dayWeatherId;
	}

	public static String getDayWeather(Element element) {
		String dayWeather = element.select("p.wea").get(0).html();
		return dayWeather;
	}

	public static float getDayTemp(Element element) {
		Elements dayElements = element.select("p.tem > span");
		Elements nightElements = element.select("p.tem > i");
		int highTem = 0;
		int lowTem = 0;
		if (dayElements.size() > 0) {
			highTem = Integer.parseInt(dayElements.get(0).html().replace("℃", ""));
		}

		lowTem = Integer.parseInt(nightElements.get(0).html().replace("℃", ""));
		int avgTem = (highTem + lowTem) / 2;
		return avgTem;
	}

	public static String getDayWind(Element element) {
		String dayWind = element.select("p.win > em > span").get(0).attr("title");
		return dayWind;
	}

	public static String getDayWindComp(Element element) {
		String dayWindComp = element.select("p.win > i").get(0).html().split("转")[0].
				replace("&lt;", "<").replace("&gt;", ">");
		return dayWindComp;
	}

	public static int getDayTemp(String str) {
		int begin = str.length() - 3;
		int end = str.length() - 1;
		String temp = str.substring(begin, end);
		return Integer.parseInt(temp);
	}

	public static String getDayWindComp(String str) {
		String str1 = str.replace("<![CDATA[", "");
		String dayWindComp = str1.replace("]]>", "");
		return dayWindComp;
	}
}
