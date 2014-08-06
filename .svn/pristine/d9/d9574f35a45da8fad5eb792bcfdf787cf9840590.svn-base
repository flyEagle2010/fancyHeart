package com.doteyplay.utils;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import com.doteyplay.utils.asst.ParamParser;

public class ParamUtils
{
	public final static String MSG_STRC_FLAG = "_structure";
	public final static String MSG_STRC_VALUE = "_PARAMDATA";
	public final static String MSG_STRC = MSG_STRC_FLAG + "=\"" + MSG_STRC_VALUE + "\"";

	public static boolean checkStatus(Properties properties)
	{
		if (properties != null)
		{
			if (properties.containsKey("_success") == true)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public static boolean checkStatus(String chackData)
	{
		if (chackData != null)
			return (chackData.indexOf("name=\"_success\"") >= 0);
		else
			return false;
	}

	public static String getMessage(Properties properties)
	{
		if (properties != null)
			return properties.getProperty("_message", "");
		else
			return "";
	}

	public static String packSimpleError(String errorMsg, int errorCode)
	{
		StringBuffer tmpPkgBuffer = new StringBuffer();
		tmpPkgBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		tmpPkgBuffer.append("<object class=\"message\" " + MSG_STRC + ">\n");
		tmpPkgBuffer.append("<property name=\"_fault\" value=\"\"/>\n");
		tmpPkgBuffer.append("<property name=\"_message\" value=\"");
		StrUtils.xmlEncoded(errorMsg, tmpPkgBuffer);
		tmpPkgBuffer.append("\"/>\n");
		tmpPkgBuffer.append("<property name=\"_errorcode\" value=\"" + String.valueOf(errorCode)
				+ "\"/>\n");
		tmpPkgBuffer.append("</object>");
		return tmpPkgBuffer.toString();
	}

	public static String packSimpleSuccess()
	{
		return packSimpleSuccess("");
	}

	public static String packSimpleSuccess(String successMsg)
	{
		StringBuffer tmpPkgBuffer = new StringBuffer();
		tmpPkgBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		tmpPkgBuffer.append("<object class=\"message\" " + MSG_STRC + ">\n");
		tmpPkgBuffer.append("<property name=\"_success\" value=\"\"/>\n");
		tmpPkgBuffer.append("<property name=\"_message\" value=\"");
		StrUtils.xmlEncoded(successMsg, tmpPkgBuffer);
		tmpPkgBuffer.append("\"/>\n</object>");
		return tmpPkgBuffer.toString();
	}

	public static String object2Xml(Object object)
	{
		return object2Xml(object, null);
	}

	public static String object2Xml(Object object, HashMap extData)
	{
		if (object != null)
		{
			StringBuffer tmpBuffer = new StringBuffer();
			tmpBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			tmpBuffer.append("<object class=\"" + object.getClass().getName() + "\" " + MSG_STRC + ">\n");
			tmpBuffer.append("<property name=\"_success\" value=\"\"/>\n");
			tmpBuffer.append("<property name=\"_message\" value=\"\"/>\n");
			Method[] tmpMethods = object.getClass().getMethods();
			String tmpKeyName = "";
			Method tmpMethod = null;
			for (int i = 0; tmpMethods != null && i < tmpMethods.length; i++)
			{
				tmpMethod = tmpMethods[i];
				if (tmpMethod != null && tmpMethod.getReturnType() != null)
				{
					tmpKeyName = tmpMethod.getName();
					if (tmpKeyName != null && tmpKeyName.length() > 3 && tmpKeyName.startsWith("get") == true
							&& tmpKeyName.compareToIgnoreCase("getClass") != 0)
					{
						Class[] params = tmpMethod.getParameterTypes();
						if (params.length == 0)
						{
							Object tmpMethodValue = null;
							try
							{
								tmpMethodValue = tmpMethod.invoke(object, new Object[] {});
							}
							catch (Exception e)
							{
								tmpMethodValue = null;
							}
							tmpBuffer.append("<property name=\"");
							StrUtils.xmlEncoded(tmpKeyName.substring(3).toLowerCase(), tmpBuffer);
							tmpBuffer.append("\" value=\"");
							if (tmpMethodValue == null)
								tmpBuffer.append("null");
							else
								StrUtils.xmlEncoded(String.valueOf(tmpMethodValue), tmpBuffer);
							tmpBuffer.append("\"/>\n");
						}
					}
				}
			}
			if (extData != null)
			{
				Object tmpKey = null;
				Object tmpKeyValue = null;
				Iterator tmpItor = extData.keySet().iterator();
				while (tmpItor != null && tmpItor.hasNext())
				{
					tmpKey = tmpItor.next();
					if (tmpKey != null && tmpKey instanceof String)
					{
						tmpKeyValue = extData.get(tmpKey);
						tmpBuffer.append("<property name=\"");
						StrUtils.xmlEncoded(((String) tmpKey).toLowerCase(), tmpBuffer);
						tmpBuffer.append("\" value=\"");
						if (tmpKeyValue == null)
							tmpBuffer.append("null");
						else
							StrUtils.xmlEncoded(String.valueOf(tmpKeyValue), tmpBuffer);
						tmpBuffer.append("\"/>\n");
					}
				}
			}
			tmpBuffer.append("</object>");
			return tmpBuffer.toString();
		}
		else
			return "";
	}

	public static String properties2Xml(Properties properties)
	{
		if (properties != null)
		{
			StringBuffer tmpPkgBuffer = new StringBuffer();
			String tmpPrtyName = null;
			tmpPkgBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			tmpPkgBuffer.append("<object class=\"" + Properties.class.getName() + "\" " + MSG_STRC
					+ ">\r\n");
			tmpPkgBuffer
					.append("<property name=\"_success\" value=\"\"/>\r\n<property name=\"_message\" value=\"\"/>\r\n");
			Enumeration tmpEnum = properties.keys();
			while (tmpEnum != null && tmpEnum.hasMoreElements())
			{
				tmpPrtyName = (String) tmpEnum.nextElement();
				tmpPkgBuffer.append("<property name=\"");
				StrUtils.xmlEncoded(tmpPrtyName, tmpPkgBuffer, true);
				tmpPkgBuffer.append("\" value=\"");
				StrUtils.xmlEncoded(properties.getProperty(tmpPrtyName), tmpPkgBuffer, true, true);
				tmpPkgBuffer.append("\"/>\n");
			}
			tmpPkgBuffer.append("</object>");
			return tmpPkgBuffer.toString();
		}
		else
			return "";
	}

	public static Properties xml2Properties(String xmlData)
	{
		return ParamParser.parse(xmlData);
	}

	public static void exportProperties(Properties properties)
	{
		if (properties != null)
		{
			Enumeration tmpEnum = properties.keys();
			while (tmpEnum != null && tmpEnum.hasMoreElements())
			{
				String tmpKey = (String) tmpEnum.nextElement();
				System.out.println(tmpKey + "=" + properties.getProperty(tmpKey));
			}
		}
	}

	public static String propertiesToBracketStr(Properties properties)
	{
		if (properties != null)
		{
			StringBuffer tmpBuffer = new StringBuffer();
			Enumeration tmpEnum = properties.keys();
			while (tmpEnum != null && tmpEnum.hasMoreElements())
			{
				String tmpKey = (String) tmpEnum.nextElement();
				tmpBuffer.append(";["+tmpKey+"="+properties.getProperty(tmpKey)+"]");
			}
			if(tmpBuffer.length()>1)
				return tmpBuffer.substring(1);
			else
				return "";
		}
		else
			return "";
	}

	public static void main(String[] args)
	{
		Properties tmpPrties = new Properties();
		tmpPrties.setProperty("aa", "baaa\r\nbddf  adsfas\r\nhuhafuasdf");
		tmpPrties.setProperty("�й�", "����");
		tmpPrties.setProperty("<>*&%^%$#@", "<>&^%$$#!@");
		tmpPrties.setProperty("content", "aaaa");
		long tmpTime = System.currentTimeMillis();
		String tmpData = ParamUtils.properties2Xml(tmpPrties);
		System.out.println("package content take:"
				+ String.valueOf(System.currentTimeMillis() - tmpTime) + "ms");
		System.out.println(tmpData);
		tmpTime = System.currentTimeMillis();
		Properties tmpPrties2 = ParamUtils.xml2Properties(tmpData);
		System.out.println("parse content take:" + String.valueOf(System.currentTimeMillis() - tmpTime)
				+ "ms");
		ParamUtils.exportProperties(tmpPrties2);
	}
}
