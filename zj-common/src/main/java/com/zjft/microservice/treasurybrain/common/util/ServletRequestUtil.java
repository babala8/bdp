package com.zjft.microservice.treasurybrain.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletRequestUtil {

    public static HttpServletRequest getHttpRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

	public static String getUsername(){
		return getHttpRequest().getHeader("username");
	}

    public static HttpSession getSession() {
        return getHttpRequest().getSession();
    }

    public static ServletContext getServletContext() {
        return getHttpRequest().getSession().getServletContext();
    }

}
