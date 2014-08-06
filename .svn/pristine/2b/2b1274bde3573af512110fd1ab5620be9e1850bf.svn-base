package com.doteyplay.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class ReflectUtils
{
	public static Object getSimplePrty(Object object, String prtyName)
	{
		Object result = null;
		if (object != null && prtyName != null && prtyName.length() > 0)
		{
			Field tmpField=null;
			try
			{
				tmpField = object.getClass().getField(prtyName);
			}
			catch (Exception e)
			{
				result = null;
			}
			try
			{
				if (tmpField != null)
					result = tmpField.get(object);
				else
				{
					if (prtyName.length() > 1)
						result = getSimpleMethod(object, "get" + prtyName.substring(0, 1).toUpperCase()
								+ prtyName.substring(1));
					else
						result = getSimpleMethod(object, "get" + prtyName.substring(0).toUpperCase());
				}
			}
			catch (Exception e)
			{
				result = null;
			}
		}
		return result;
	}

	public static Object getSimpleMethod(Object object, String methodName)
	{
		return getSimpleMethod(object, methodName, null);
	}

	public static Object getSimpleMethod(Object object, String methodName, String[] args)
	{
		Object result = null;
		if (object != null && methodName != null && methodName.length() > 0)
		{
			Method tmpMethod;
			try
			{
				if (args == null || args.length == 0)
					tmpMethod = object.getClass().getMethod(methodName, new Class[] {});
				else
					tmpMethod = object.getClass().getMethod(methodName, new Class[] { String.class });
				if (tmpMethod != null && tmpMethod.getReturnType() != null)
				{
					if (args == null || args.length == 0)
						result = tmpMethod.invoke(object, new Object[] {});
					else
						result = tmpMethod.invoke(object, args);
				}
			}
			catch (Exception e)
			{
				result = null;
			}
		}
		return result;
	}

	public static void setSimplePrty(Object object, String prtyName, Object prtyValue)
	{
		if (object != null && prtyName != null && prtyName.length() > 0)
		{
			Field tmpField;
			try
			{
				tmpField = object.getClass().getField(prtyName);
				if (tmpField != null)
					tmpField.set(object, prtyValue);
				else
				{
					if (prtyName.length() > 1)
						setSimpleMethod(object, "set" + prtyName.substring(0, 1).toUpperCase()
								+ prtyName.substring(1), prtyValue);
					else
						setSimpleMethod(object, "set" + prtyName.substring(0).toUpperCase(), prtyValue);
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	public static void setSimpleMethod(Object object, String methodName, Object value)
	{
		if (object != null && methodName != null && methodName.length() > 0)
		{
			Method tmpMethod;
			try
			{
				if (value != null)
					tmpMethod = object.getClass().getMethod(methodName, new Class[] { value.getClass() });
				else
					tmpMethod = object.getClass().getMethod(methodName, new Class[] { String.class });
				if (tmpMethod != null)
					tmpMethod.invoke(object, new Object[] { value });
			}
			catch (Exception e)
			{
			}
		}
	}

	public static String beanToBracketStr(Object object)
	{
		if (object != null)
		{
			StringBuffer tmpBuffer = new StringBuffer();
			tmpBuffer.append("[_class=" + object.getClass().getName() + "]");
			Field[] tmpFields = object.getClass().getFields();
			String tmpKeyName = "";
			Field tmpField = null;
			for (int i = 0; tmpFields != null && i < tmpFields.length; i++)
			{
				tmpField = tmpFields[i];
				if (tmpField != null && tmpField.getType() != null)
				{
					tmpKeyName = tmpField.getName();
					Object tmpMethodValue = null;
					try
					{
						tmpMethodValue = tmpField.get(object);
					}
					catch (Exception e)
					{
						tmpMethodValue = null;
					}
					tmpBuffer.append(";[" + tmpKeyName.toLowerCase() + "=");
					if (tmpMethodValue == null)
						tmpBuffer.append("]");
					else
						tmpBuffer.append(String.valueOf(tmpMethodValue) + "]");
				}
			}
			Method[] tmpMethods = object.getClass().getMethods();
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
							tmpBuffer.append(";[" + tmpKeyName.substring(3).toLowerCase() + "=");
							if (tmpMethodValue == null)
								tmpBuffer.append("]");
							else
								tmpBuffer.append(String.valueOf(tmpMethodValue) + "]");
						}
					}
				}
			}
			return tmpBuffer.toString();
		}
		else
			return "";
	}

	public static Properties beanToProperties(Object object)
	{
		if (object != null)
		{
			Properties tmpBuffer = new Properties();
			tmpBuffer.setProperty("_class", object.getClass().getName());
			Field[] tmpFields = object.getClass().getFields();
			String tmpKeyName = "";
			Field tmpField = null;
			for (int i = 0; tmpFields != null && i < tmpFields.length; i++)
			{
				tmpField = tmpFields[i];
				if (tmpField != null && tmpField.getType() != null)
				{
					tmpKeyName = tmpField.getName();
					Object tmpMethodValue = null;
					try
					{
						tmpMethodValue = tmpField.get(object);
					}
					catch (Exception e)
					{
						tmpMethodValue = null;
					}
					tmpBuffer.setProperty(tmpKeyName.toLowerCase(), (tmpMethodValue != null) ? String
							.valueOf(tmpMethodValue) : "");
				}
			}
			Method[] tmpMethods = object.getClass().getMethods();
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
							tmpBuffer.setProperty(tmpKeyName.substring(3).toLowerCase(), (tmpMethodValue != null) ? String
									.valueOf(tmpMethodValue) : "");
						}
					}
				}
			}
			return tmpBuffer;
		}
		else
			return null;
	}
}
