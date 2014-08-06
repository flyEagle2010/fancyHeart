package com.doteyplay.core.dbcs.executor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.dbspace.DBZone;
import com.doteyplay.core.dbcs.dbspace.IDBZoneRule;

/**
 * DAO接口函数数据库映射处理器 包含参数列表、数据库映射规则信息
 * 
 * @author 
 * 
 */
public class DaoFunctionAnnotation
{
	private static final Logger logger = Logger.getLogger(DaoFunctionAnnotation.class);

	private String functionName;
	private int paramCount;
	private String[] paramList;
	private int ruleParamIndex;
	private String ruleParamPrty;
	private Field ruleParamField;
	private Method ruleParamMethod;
	private boolean useDefaultDBZone;
	private DBZone[] defaultDBZone;
	private IDBZoneRule dbRule;
	private boolean valid;

	public DaoFunctionAnnotation(String functionname, String annotationinfo, Class<?>[] args)
	{
		this.functionName = functionname;
		this.useDefaultDBZone = false;
		this.dbRule = null;
		this.ruleParamIndex = -1;

		int argCount = (args != null) ? args.length : 0;
		if (annotationinfo == null || annotationinfo.length() <= 0)
			paramList = new String[0];
		else
			paramList = annotationinfo.split(",");

		if (argCount > 1 && paramList.length != args.length)
		{
			logger.error("annotation syntax of " + functionName + " error,field count not equal args count");
			paramCount = 0;
			valid = false;
			return;
		}

		if (paramList.length == 0)
		{
			// 无DB规则，使用缺省数据库
			paramCount = args.length;
			useDefaultDBZone = true;
			defaultDBZone=new DBZone[1];
			defaultDBZone[0]=DBSpace.getDefaultDBZone();
			valid = true;
			dbRule = null;
			return;
		}
		else if (paramList.length == 1 && argCount == 0)
		{
			// 有DB规则,无参数
			if (paramList[0].length() < 3 || paramList[0].charAt(0) != '['
					|| paramList[0].charAt(paramList[0].length() - 1) != ']')
			{
				logger.error("annotation syntax of " + functionName
						+ " error,function has no argument,but annotation contain a field");
				paramCount = 0;
				valid = false;
			}
			else
			{
				paramCount = 0;
				dbRule = DBSpace.getZoneRule(annotationinfo.substring(1, annotationinfo.length() - 1));
				valid = (dbRule != null);
			}
			return;
		}
		else
		{
			paramCount = args.length;
			valid = true;

			int i = 0;
			int pos = -1;
			while (valid && i < paramCount)
			{
				if (paramList[i].charAt(paramList[i].length() - 1) == ']')
				{
					pos = paramList[i].indexOf('[');
					if (pos > 0)
					{
						ruleParamIndex = i;
						dbRule = DBSpace.getZoneRule(paramList[i].substring(pos + 1,
								paramList[i].length() - 1));
						paramList[i] = paramList[i].substring(0, pos);
						valid = (dbRule != null);
					}
					else
						valid = false;
				}
				else
				{
					if (paramList[i] == null || paramList[i].length() <= 0)
						valid = false;
				}
				i++;
			}
			if (valid)
			{
				if (ruleParamIndex < 0)
				{
					// 未指定数据库规则
					useDefaultDBZone = true;
					defaultDBZone=new DBZone[1];
					defaultDBZone[0]=DBSpace.getDefaultDBZone();
				}
				else if (paramCount == 1)
				{
					if (Map.class.isAssignableFrom(args[0]))
					{
						this.ruleParamPrty = paramList[0];
					}
					else if (isSimpleClass(args[0]))
					{
						//先检查getMethod是否存在
						String tmpMethodName = "get";
						if (paramList[0].length() > 0)
							tmpMethodName = tmpMethodName + paramList[0].substring(0, 1).toUpperCase()
									+ paramList[0].substring(1);
						else
							tmpMethodName = tmpMethodName + paramList[0].toUpperCase();

						try
						{
							ruleParamMethod = args[0].getMethod(tmpMethodName);
						}
						catch (Exception e)
						{
							ruleParamMethod = null;
							valid = false;
							e.printStackTrace();
						}

						if (ruleParamMethod == null)
						{
							//在检查Field是否存在
							try
							{
								ruleParamField = args[0].getField(paramList[0]);
							}
							catch (Exception e)
							{
								ruleParamField = null;
								valid = false;
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	private boolean isSimpleClass(Class<?> classtype)
	{
		return (classtype != String.class && classtype != Integer.class && classtype != int.class
				&& classtype != Byte.class && classtype != byte.class && classtype != Long.class
				&& classtype != long.class && classtype != Short.class && classtype != short.class);
	}

	public int getParamCount()
	{
		return paramCount;
	}

	public String[] getParamList()
	{
		return paramList;
	}

	public int getRuleParamIndex()
	{
		return ruleParamIndex;
	}

	public IDBZoneRule getDbRule()
	{
		return dbRule;
	}

	public String getDbRuleName()
	{
		return (dbRule != null) ? dbRule.getName() : "?";
	}

	public boolean isValid()
	{
		return valid;
	}

	@SuppressWarnings("rawtypes")
	public DBZone[] getDBZone(Object[] args)
	{
		if (!valid)
			return null;
		
		if(this.useDefaultDBZone)
		{
			return defaultDBZone;			
		}

		if (this.paramCount > 0 && (args == null || this.paramCount != args.length))
		{
			logger.error("dbrule need " + this.paramCount + " paramter,invoking fail");
			return null;
		}
		
		if (this.paramCount > 0)
		{
			if (this.ruleParamMethod != null)
			{
				try
				{
					return dbRule.getDBZone(this.ruleParamMethod.invoke(args[0]).toString());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
			else if (this.ruleParamField != null)
			{
				try
				{
					return dbRule.getDBZone(this.ruleParamField.get(args[0]).toString());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
			else if (this.ruleParamPrty != null)
			{
				try
				{
					return dbRule.getDBZone(((Map) args[0]).get(this.ruleParamPrty).toString());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
			else
				return dbRule.getDBZone(args[this.ruleParamIndex].toString());
		}
		else
			return dbRule.getDBZone(null);
	}

	public Object createStatementMap(Object[] args)
	{
		Object tmpParam = null;
		if (this.paramCount > 0 && paramList != null)
		{
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			for (int i = 0; i < paramList.length; i++)
			{
				tmpMap.put(paramList[i], args[i]);
			}
			tmpParam = tmpMap;
			tmpMap = null;
		}
		return tmpParam;
	}
}
