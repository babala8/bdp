package com.zjft.microservice.treasurybrain.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public final class URLUtil {
	
	private static Log log = LogFactory.getLog(URLUtil.class);
	
	public static void sendToken(String staticURL, String webToken) throws IOException {
		//定义JSON字符串
		String jsonStr = "{\"webToken\": \"" + webToken + "\"}";
		
		HttpURLConnection connection = null;
		try {
			URL url = new URL(staticURL + "/dispatchToken");
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("Content-Type", "application/com.zjft.insight.json");
			connection.setRequestProperty("Content-Length", String.valueOf(jsonStr.length()));
			connection.connect();
			
			OutputStream outputStream = null;
			OutputStreamWriter outputStreamWriter = null;
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;
			
			try {
				outputStream = connection.getOutputStream();
				outputStreamWriter = new OutputStreamWriter(outputStream);
				
				outputStreamWriter.write(jsonStr);
				outputStreamWriter.flush();
				
				log.info("http reponse code == " + connection.getResponseCode());
				if (connection.getResponseCode() >= 300) {
					throw new IOException("HTTP Request is not success, Response code is " + connection.getResponseCode());
				}
				
				inputStream = connection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
				}
			} finally {
				if (outputStreamWriter != null) {
					outputStreamWriter.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (IOException e) {
			log.error("URLUtil.sendToken is fail.");
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void deleteToken(String staticURL, String webToken) throws IOException {
		URL url = new URL(staticURL + "/dispatchToken?webToken=" + webToken);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        
		connection.setDoOutput(true);
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Accept-Charset", "utf-8");
		connection.setRequestProperty("Content-Type", "application/com.zjft.insight.json");
		connection.connect();
        
        log.info("http reponse code == " + connection.getResponseCode());
        if (connection.getResponseCode() >= 300) {
            throw new IOException("HTTP Request is not success, Response code is " + connection.getResponseCode());
        }
	}
	
}
