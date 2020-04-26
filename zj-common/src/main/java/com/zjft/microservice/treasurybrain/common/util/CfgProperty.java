package com.zjft.microservice.treasurybrain.common.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * 加載配置文件
 *
 * @author 杨光
 */
@Slf4j
public class CfgProperty {

	private static ResourceBundle pr = null;

	static {
		try {
			pr = ResourceBundle.getBundle("config/cfg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pr == null) {
			log.error("can not find cfg.properties");
		}
	}

	public static String getProperty(String disStr) {
		if (disStr == null || "".equals(disStr)) {
			return "";
		}
		try {
			return new String(pr.getString(disStr).getBytes(StandardCharsets.ISO_8859_1), "GB18030");
		} catch (Exception e) {
			log.error("获取属性[" + disStr + "]失败", e);
			return disStr;
		}
	}

	public static void main(String[] args) {
		System.out.println(getProperty("bdpApiUrl"));
	}
}
