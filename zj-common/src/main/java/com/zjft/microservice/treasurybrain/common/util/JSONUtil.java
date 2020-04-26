package com.zjft.microservice.treasurybrain.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Json Utility
 * 
 * @author hongwei
 * @since 2015-3-24
 *
 */
public class JSONUtil {

	/**
	 * create a plain com.zjft.insight.json string from a java object
	 * @param object
	 * @return if object is null, then return null.
	 */
	public static final String createJsonString(Object object) {
		return object == null ? null : JSON.toJSONString(object, false);
	}
	
	/**
	 * create a JSONObject from parsing a plain com.zjft.insight.json string
	 * @param text
	 * @return if text is null, or empty string, then return null.
	 */
	public static final JSONObject parseJSONObject(String text) {
		return JSON.parseObject(text);
	}
	
	/**
	 * create a JSONArray from parsing a plain com.zjft.insight.json string
	 * @param text
	 * @return
	 */
	public static final JSONArray parseJSONArray(String text) {
		return JSON.parseArray(text);
	}
	
	
	/**
	 * create a specific java bean from parsing a plain text
	 * @param <T>
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static final <T> T parseBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}
	
	/**
	 * create a Java List from parsing a plain com.zjft.insight.json string
	 * @param text
	 * @return
	 */
	public static final <T> List<T> parseList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}
	
}
