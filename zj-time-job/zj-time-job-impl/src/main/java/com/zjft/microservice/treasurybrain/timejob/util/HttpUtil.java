package com.zjft.microservice.treasurybrain.timejob.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.UnknownHostException;


/**
 * @author 常 健
 * @since 2019/07/17
 */

public class HttpUtil {

	private static Log log = LogFactory.getLog(HttpUtil.class);

	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(15000)
			.setConnectTimeout(15000)
			.setConnectionRequestTimeout(15000)
			.build();

	private HttpUtil() {
	}

	/**
	 * 发送 get请求
	 *
	 * @param httpUrl
	 */
	public static String sendHttpGet(String httpUrl) {
		// 创建get请求
		HttpGet httpGet = new HttpGet(httpUrl);
		return sendHttpGet(httpGet);
	}

	/**
	 * 发送Get请求
	 *
	 * @param httpGet
	 * @return
	 */
	private static String sendHttpGet(HttpGet httpGet) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		int statusCode = 0;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			httpGet.setConfig(requestConfig);
			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
			// 执行请求
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			statusCode = response.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {
			log.error("ClientProtocolException", e);
		} catch (UnknownHostException e) {
			log.error("----------------------------网络连接异常，无法访问-------------------------");
			log.error("网络连接异常，无法访问", e);
			log.error("------------------------------------------------------------------------");
		} catch (Exception e) {
			log.error("Exception", e);
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
		}

		if (statusCode != 200) {
			log.info("状态码:" + statusCode);
			return null;
		}
		return responseContent;
	}
}
