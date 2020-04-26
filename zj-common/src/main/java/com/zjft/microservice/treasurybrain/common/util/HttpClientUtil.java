package com.zjft.microservice.treasurybrain.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
	
	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	private static String CHARSET = "UTF-8";
	
	//关键字检索
	public static String search(String query, String region, String scope, 
									String pageSize, String pageNum, String ak) {
		
		String strResult = "";
		StringBuffer reqURL = new StringBuffer(StringUtil.parseString(CfgProperty.getProperty("mapApiUrl")))
											.append("/place/v2/search?query=").append(query)
											.append("&region=").append(region)
											.append("&scope=").append(scope)
											.append("&page_size=").append(pageSize)
											.append("&page_num=").append(pageNum)
											.append("&output=json&ak=").append(ak);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(reqURL.toString());
			CloseableHttpResponse response = httpClient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() == 200) {  //if response successfully
					HttpEntity httpEntity = response.getEntity();
					strResult = EntityUtils.toString(httpEntity, CHARSET).trim();
					EntityUtils.consume(httpEntity);
				} 
				response.close();
			} catch(Exception e) {
				log.error(e);
			}
			
		} catch(Exception e) {
			log.error("Excepiton while requesting baidu map webservice: ", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				log.error("Exception while closing httpClient: ", ex);
			}
		}
		return strResult;
	}
	
	//解析地址
	public static String geocoder(String address, String city, String ak) throws Exception {
		
		String jsonResult = "";
		String geoInfo = "";
		StringBuffer reqURL = new StringBuffer(StringUtil.parseString(CfgProperty.getProperty("mapApiUrl")))
											.append("/geocoder/v2/?address=")
											.append(URLEncoder.encode(address, CHARSET))
											.append("&city=").append(URLEncoder.encode(city, CHARSET))
											.append("&output=json&ak=").append(ak);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(reqURL.toString());
			CloseableHttpResponse response = httpClient.execute(httpget);
			
			try {
				if (response.getStatusLine().getStatusCode() == 200) {  //if response successfully
					HttpEntity httpEntity = response.getEntity();
					jsonResult = EntityUtils.toString(httpEntity, CHARSET).trim();
					EntityUtils.consume(httpEntity);
				} 
				response.close();
				//parse json
				geoInfo = BaiduMapJsonDecoder.parseGeoInfo(jsonResult); //lng|lat
			} catch(Exception e) {
				log.error(e);
			}
		} catch(Exception e) {
			log.error("Excepiton while requesting baidu map webservice: ", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				log.error("Exception while closing httpClient: ", ex);
			}
		}
		return geoInfo;
	}
	
	//批量算路程接口，返回路线规划距离和行驶时间
	public static List<Map<String, Object>> routematrix(List<Map<String, Object>> origins, List<Map<String, Object>> destinations, int tactics, String ak) throws Exception {
		List<Map<String, Object>> retMap = new ArrayList<Map<String, Object>>();
		StringBuffer originsTmp = new StringBuffer();
		String jsonResult = "";
		for(int i = 0; i < origins.size(); i++) {
			Map<String, Object> tmpMap = origins.get(i);
			originsTmp.append(tmpMap.get("y"));
			originsTmp.append(",");
			originsTmp.append(tmpMap.get("x"));
			originsTmp.append(URLEncoder.encode("|", CHARSET));
		}
		
		StringBuffer destinationsTmp = new StringBuffer();
		for (int i = 0; i < destinations.size(); i++) {
			Map<String, Object> tmpMap = destinations.get(i);
			destinationsTmp.append(tmpMap.get("y"));
			destinationsTmp.append(",");
			destinationsTmp.append(tmpMap.get("x"));
			destinationsTmp.append(URLEncoder.encode("|", CHARSET));
		}
		
		StringBuffer reqURL = new StringBuffer(StringUtil.parseString(CfgProperty.getProperty("mapApiUrl")))
											.append	("/routematrix/v2/driving?output=json")
											.append("&origins=").append(originsTmp.toString().subSequence(0, originsTmp.toString().length()-3))
											.append("&destinations=").append(destinationsTmp.toString().subSequence(0, destinationsTmp.toString().length()-3))
											.append("&tactics=").append(tactics)
											.append("&ak=").append(ak);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(reqURL.toString());
			CloseableHttpResponse response = httpClient.execute(httpget);
			try {
				if(response.getStatusLine().getStatusCode() == 200) {  //if response successfully
					HttpEntity httpEntity = response.getEntity();
					jsonResult = EntityUtils.toString(httpEntity, CHARSET).trim();
					log.debug("requesting baidu map webservice ,result: " + jsonResult);
					EntityUtils.consume(httpEntity);
				} 
				response.close();
				
				JSONObject jobj = JSONUtil.parseJSONObject(jsonResult);
				if("0".equals(jobj.getString("status"))) {
					JSONArray jsonArray =  jobj.getJSONArray("result");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int distance = StringUtil.objectToInt(jsonObject.getJSONObject("distance").getString("value").trim());
						int timecost = StringUtil.objectToInt(jsonObject.getJSONObject("duration").getString("value").trim());
						Map<String, Object> tmp = new HashMap<String, Object>();
						tmp.put("distance", distance);
						tmp.put("timecost", (int)Math.ceil(timecost/60));
						retMap.add(tmp);
					}
				} else {
					log.error("requesting baidu map webservice,response error!");
				}
			} catch(Exception e) {
				log.error(e);
			}
		} catch(Exception e1) {
			log.error("Excepiton while requesting baidu map webservice: ", e1);
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				log.error("Exception while closing httpClient: ", ex);
			}
		}
		return retMap;
	}
	
	//规划两点之间线路
	public static String direction(String origin, String destination, String mode, String region, String originRegion, String destinationRegion, String tactics, String ak) throws Exception {
		String jsonResult = "";
		String directionRoute = "";
		StringBuffer reqURL = new StringBuffer(StringUtil.parseString(CfgProperty.getProperty("mapApiUrl")))
											.append	("/direction/v1?output=json")
											.append("&origin=").append(origin)
											.append("&destination=").append(destination)
											.append("&mode=").append(mode)
											.append("&region=").append(region)
											.append("&origin_region=").append(originRegion)
											.append("&destination_region=").append(destinationRegion)
											.append("&tactics=").append(tactics)
											.append("&ak=").append(ak);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(reqURL.toString());
			CloseableHttpResponse response = httpClient.execute(httpget);
			try {
				if(response.getStatusLine().getStatusCode() == 200) {  //if response successfully
					HttpEntity httpEntity = response.getEntity();
					jsonResult = EntityUtils.toString(httpEntity, CHARSET).trim();
					log.debug("requesting baidu map webservice ,result: " + jsonResult);
					EntityUtils.consume(httpEntity);
				} 
				response.close();
				
				//parse the result
				directionRoute = BaiduMapJsonDecoder.parseDirectionRoute(jsonResult);
			} catch(Exception e) {
				log.error(e);
			}
		} catch(Exception e1) {
			log.error("Excepiton while requesting baidu map webservice: ", e1);
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				log.error("Exception while closing httpClient: ", ex);
			}
		}
		return directionRoute;
	}
	
}
