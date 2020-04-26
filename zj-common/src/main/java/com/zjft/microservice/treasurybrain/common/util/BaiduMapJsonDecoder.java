package com.zjft.microservice.treasurybrain.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Baidu Map JSON Decoder
 * @author hongwei
 * 
 * @since 2015-9-18
 *
 */
public class BaiduMapJsonDecoder {
	
	private static Log log = LogFactory.getLog(BaiduMapJsonDecoder.class);

	/**
	 * parse the result of geocoder
	 * @param jsonStr
	 * @return lng|lat
	 * @exception return null
	 */
	public static String parseGeoInfo(String jsonStr) {
		String geoInfo = null;
		try {
			JSONObject jobj = JSONUtil.parseJSONObject(jsonStr);
			if("0".equals(jobj.getString("status"))) {
				JSONObject jLocation = jobj.getJSONObject("result").getJSONObject("location");
				geoInfo = new StringBuffer(jLocation.getString("lng").trim()).append("|")
										.append(jLocation.getString("lat").trim()).toString();
			}
		} catch(Exception e) {
			log.error("Exception in parseGeoInfo: ", e);
		}
		return geoInfo;
	}
	
	/**
	 * parse the result of direction
	 * @param jsonStr
	 * @return direction route with JsonArray string form
	 * @exception return null
	 */
	public static String parseDirectionRoute(String jsonStr) {
		String directionRoute = null;
		try {
			JSONObject jobj = JSONUtil.parseJSONObject(jsonStr);
			if("0".equals(jobj.getString("status"))) {
				JSONArray jRoutes = jobj.getJSONObject("result").getJSONArray("routes");
				directionRoute = jRoutes.toString();
			}
		} catch(Exception e) {
			log.error("Exception in parseDirectionRoute: ", e);
		}
		return directionRoute;
	}
	
}
