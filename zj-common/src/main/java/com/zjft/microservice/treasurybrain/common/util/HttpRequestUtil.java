package com.zjft.microservice.treasurybrain.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestUtil {

    private static Log log = LogFactory.getLog(HttpRequestUtil.class);
    
    private static String CHARSET = "UTF-8";

    /**
     * 获取浏览器信息
     * @param request
     * @return browser-type|browser-version
     */
    public static String getBrowserInfo(HttpServletRequest request) {
    	String browserInfo = "unkown|0";
    	try {
    		String userAgent = request.getHeader("user-agent").toLowerCase();
    		//patterns
    	    Pattern generalPattern = Pattern.compile("(msie|firefox|opera|chrome|netscape)\\D+(\\d[\\d.]*)");
    	    Pattern safariPattern = Pattern.compile("version\\D+(\\d[\\d.]*).*safari");
    	    Pattern msieVersionPattern = Pattern.compile("rv:(\\d[\\d.]*)");
    	    
    	    //match
    	    Matcher generalMatcher = generalPattern.matcher(userAgent);
    	    if(generalMatcher.find()) {  //check msie|firefox|opera|chrome|netscape
    	    	browserInfo = generalMatcher.group(1) + "|" + generalMatcher.group(2);
    	    } else { //check safari
    	         Matcher safariMatcher = safariPattern.matcher(userAgent);
    	         if(safariMatcher.find()) {  
    	        	 browserInfo = "safari|" + safariMatcher.group(1);
    	         } else if(userAgent.indexOf("trident") > -1) { //check trident
    	        	 	Matcher msieVersionMatcher = msieVersionPattern.matcher(userAgent);
    		    		if(msieVersionMatcher.find()) {
    		    			browserInfo = "msie|" + msieVersionMatcher.group(1);
    		    		}
    		      }
    	     }
    	} catch(Exception e) {
    		log.error("Exception while getting browser info: ", e);
    	}
    	
    	return browserInfo;
    }
 
	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url请求的服务url地址
	 *            ?之前的地址
	 * @param params请求的参数
	 * @return 页面内容
	 */
    public static String restfulGet(String url, Map<String,Object> params) throws Exception {
		
		String jsonResult = "";
		StringBuffer reqURL = new StringBuffer(CfgProperty.getProperty("bdpApiUrl"))
											.append(url)
											.append("?ak=")
											.append(CfgProperty.getProperty("bdpApiAk"));
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(
					params.size());
			for (String key : params.keySet()) {
				pairs.add(new BasicNameValuePair(key, params.get(key)
						.toString()));
			}
			reqURL.append("&")
				  .append(EntityUtils.toString(new UrlEncodedFormEntity(pairs,
							CHARSET)));
		}
		
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
			} catch(Exception e) {
				log.error(e);
			}
		} catch(Exception e) {
			log.error("Excepiton while do get request: ", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				log.error("Exception while closing httpClient: ", ex);
			}
		}
		return jsonResult;
	}

	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url请求的服务url地址
	 *            ?之前的地址
	 * @param params请求的参数
	 * @return 页面内容
	 */
	public static String restfulPost(String url, Map<String, Object> params) {
		String jsonResult = "";
		StringBuffer reqURL = new StringBuffer(
				CfgProperty.getProperty("bdpApiUrl")).append(url);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("ak", CfgProperty.getProperty("bdpApiAk")));
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
		}
		try {
			HttpPost httpPost = new HttpPost(reqURL.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			
			try {
				if (response.getStatusLine().getStatusCode() == 200) { // if response successfully
					HttpEntity httpEntity = response.getEntity();
					jsonResult = EntityUtils.toString(httpEntity, CHARSET).trim();
					EntityUtils.consume(httpEntity);
				}
				response.close();
			} catch (Exception e) {
				log.error(e);
			}
		} catch (Exception e) {
			log.error("Excepiton while do post request: ", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				log.error("Exception while closing httpClient: ", ex);
			}
		}
		return jsonResult;

	}

    public static void main(String[] args){
    	String responseJson = 
				"{\"retCode\":\"00\",\"retMsg\":\"查询成功\",\"retList\":[" +
					"{\"sid\":\"10000\",\"serviceName\":\"查询近三个月ATM交易额信息\",\"serviceURL\":\"/bi/v1/bds/10000\",\"serviceMethod\":\"get\"," +
					 "\"returnParam\":[{\"name\":\"设备号\",\"type\":\"String\",\"desc\":\"ATM设备号，一般10位\"}," +
					                 "{\"name\":\"交易额\",\"type\":\"Integer\",\"desc\":\"近三个月交易额（万元）\"}," +
					                 "{\"name\":\"网点名称\",\"type\":\"String\",\"desc\":\"网点名称\"}," +
					                 "{\"name\":\"设备效益\",\"type\":\"String\",\"desc\":\"近三个月折算ATM效益值\"}" +
					                 "]}," +
					"{\"sid\":\"10001\",\"serviceName\":\"查询近三个月备件使用情况\",\"serviceURL\":\"/bi/v1/bds/10001\",\"serviceMethod\":\"get\"," +
					 "\"returnParam\":[{\"name\":\"备件模块名称\",\"type\":\"String\",\"desc\":\"备件模块名称\"}," +
					                 "{\"name\":\"库房名称\",\"type\":\"String\",\"desc\":\"库房名称，以O开头\"}," +
					                 "{\"name\":\"备件使用数量\",\"type\":\"String\",\"desc\":\"使用数量（近三个月）\"}," +
					                 "{\"name\":\"备件使用率\",\"type\":\"String\",\"desc\":\"近三个月使用率\"}" +
					                 "]}]}";
    	System.out.println(responseJson);
    	
    	String jsonString = 
    			"{\"retCode\":\"00\",\"retMsg\":\"查询成功\",\"data\":[" +
    			 "{\"设备号\":\"000001\",\"交易额\":\"6500\",\"网点名称\":\"江苏支行\",\"设备效益\":\"0.87\"}," +
    			 "{\"设备号\":\"000002\",\"交易额\":\"4300\",\"网点名称\":\"上海分行\",\"设备效益\":\"0.56\"}," +
    			 "{\"设备号\":\"000003\",\"交易额\":\"8900\",\"网点名称\":\"深圳支行\",\"设备效益\":\"0.98\"}]}"; 
    	       
    	System.out.println(jsonString); 
    }

}
