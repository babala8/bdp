package com.zjft.microservice.treasurybrain.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}


	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}
}
