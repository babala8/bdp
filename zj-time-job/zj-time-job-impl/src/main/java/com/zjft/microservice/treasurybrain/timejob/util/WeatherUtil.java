package com.zjft.microservice.treasurybrain.timejob.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/07/16
 */

public class WeatherUtil {

    public static String getWeatherIdByName(String weatherName) {
        Map<String, String> weatherMap = new HashMap<String, String>();
        weatherMap.put("暴雨", "10");
        weatherMap.put("大暴雨", "11");
        weatherMap.put("特大暴雨", "12");
        weatherMap.put("阵雪", "13");
        weatherMap.put("小雪", "14");
        weatherMap.put("中雪", "15");
        weatherMap.put("大雪", "16");
        weatherMap.put("暴雪", "17");
        weatherMap.put("雾", "18");
        weatherMap.put("冻雨", "19");
        weatherMap.put("沙尘暴", "20");
        weatherMap.put("小到中雨", "21");
        weatherMap.put("中到大雨", "22");
        weatherMap.put("大到暴雨", "23");
        weatherMap.put("暴雨到大暴雨", "24");
        weatherMap.put("大暴雨到特大暴雨", "25");
        weatherMap.put("小到中雪", "26");
        weatherMap.put("中到大雪", "27");
        weatherMap.put("大到暴雪", "28");
        weatherMap.put("浮尘", "29");
        weatherMap.put("扬沙", "30");
        weatherMap.put("强沙尘暴", "31");
        weatherMap.put("霾", "53");
        weatherMap.put("", "99");
        weatherMap.put("晴", "00");
        weatherMap.put("多云", "01");
        weatherMap.put("阴", "02");
        weatherMap.put("阵雨", "03");
        weatherMap.put("雷阵雨", "04");
        weatherMap.put("雷阵雨伴有冰雹", "05");
        weatherMap.put("雨夹雪", "06");
        weatherMap.put("小雨", "07");
        weatherMap.put("中雨", "08");
        weatherMap.put("大雨", "09");

        String name = "";
        if (weatherName != null) {
            name = weatherName;
        }
        return weatherMap.get(name);
    }
}
