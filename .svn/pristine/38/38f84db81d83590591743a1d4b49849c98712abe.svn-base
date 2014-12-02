package com.doteyplay.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @className:IPUtils.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年11月24日 下午7:03:45
 */
public class IPUtils {

	public static  String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
