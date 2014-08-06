package com.doteyplay.core.dbcs.executor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.dbspace.DBZone;

/**
 * DAO接口函数执行代理 对应Mapper中的具体Statement，是接口中的具体方法的执行
 * 
 * @author 
 * 
 */
public class StatementProxy
{
	private static final Logger logger = Logger.getLogger(StatementProxy.class);

	private final static String SELECT_LIST_SUFFIX = "ForList";

	private final static int PARAM_SOURCE = 0;
	private final static int PARAM_MAP = 1;

	private final static int ACTION_NONE = 0;
	private final static int ACTION_SELECT = 1;
	private final static int ACTION_SELECT_FORLIST = 2;
	private final static int ACTION_INSERT = 3;
	private final static int ACTION_UPDATE = 4;
	private final static int ACTION_DELETE = 5;
	private final static int ACTION_SQL = 7;
	private final static int ACTION_PROCEDURE = 8;
	private final static int ACTION_SELECT_FORMAP = 9;
	
	private final static int ACTION_START_TRANSACTION= 10;
	private final static int ACTION_COMMIT_TRANSACTION= 11;
	private final static int ACTION_END_TRANSACTION= 12;

	private DaoFunctionAnnotation daoFunctionAnnotation;
	private String beanName;
	private String methodName;
	private String statementId;
	private int actionType;
	private int paramType;
	private boolean available;
	private boolean isGameDataZone;

	public StatementProxy(String beanName, Method method, DBZone dbzone, DaoFunctionAnnotation functionAnnotation, boolean isGameDataZone)
	{
		this.beanName = beanName;
		this.methodName = method.getName();
		this.daoFunctionAnnotation = functionAnnotation;
		this.isGameDataZone = isGameDataZone;
		this.available = this.daoFunctionAnnotation.isValid();
		if (this.available)
			init(method, dbzone);
		else
			logger.error(beanName + "." + methodName + ":annotation is invalid");
	}

	private void init(Method method, DBZone dbzone)
	{
		String tmpStatementId = null;
		MappedStatement tmpMappedStatement = null;
		if (methodName.endsWith(SELECT_LIST_SUFFIX))
		{
			tmpStatementId = this.beanName + "."
					+ methodName.substring(0, this.methodName.length() - SELECT_LIST_SUFFIX.length());
			try
			{
				tmpMappedStatement = dbzone.getMappedStatement(tmpStatementId);
			} catch (Exception e)
			{
			}
		}
		if (tmpMappedStatement == null)
		{
			tmpStatementId = this.beanName + "." + methodName;
			try
			{
				tmpMappedStatement = dbzone.getMappedStatement(tmpStatementId);
			} catch (Exception e)
			{
			}
		}

		if (tmpMappedStatement == null)
		{
			logger.error(beanName + "." + methodName + " can't match any mappedstatement");
			this.available = false;
			return;
		}

		this.statementId = tmpStatementId;
		Class<?>[] tmpMParamList = method.getParameterTypes();
		Class<?> tmpParamClass = DBZone.getMappedStatementParamClass(tmpMappedStatement);
		if (tmpMParamList != null && tmpMParamList.length > 0)
		{
			if (tmpMParamList.length > 1 && (tmpParamClass == null || !Map.class.isAssignableFrom(tmpParamClass)))
			{
				logger.error(beanName + "." + methodName + " matched statement " + tmpMappedStatement.getId()
						+ ",but this statement's parameterclass is" + tmpParamClass);
				this.available = false;
				return;
			}

			if (tmpParamClass != null && Map.class.isAssignableFrom(tmpParamClass))
			{
				this.paramType = PARAM_MAP;
			} else
			{
				this.paramType = PARAM_SOURCE;
			}
		} else
		{
			this.paramType = PARAM_SOURCE;
		}

		if (DBZone.isSelectStatement(tmpMappedStatement))
		{
			Class<?> tmpResultClass = method.getReturnType();
			if (Collection.class.isAssignableFrom(tmpResultClass))
			{
				this.actionType = ACTION_SELECT_FORLIST;
			} else
				this.actionType = ACTION_SELECT;
		} else if (DBZone.isInsertStatement(tmpMappedStatement))
		{
			this.actionType = ACTION_INSERT;
		} else if (DBZone.isUpdateStatement(tmpMappedStatement))
		{
			this.actionType = ACTION_UPDATE;
		} else if (DBZone.isDeleteStatement(tmpMappedStatement))
		{
			this.actionType = ACTION_DELETE;
		} else if (DBZone.isProcedureStatement(tmpMappedStatement))
		{
			this.actionType = ACTION_PROCEDURE;
		} else if (DBZone.isUnknownStatement(tmpMappedStatement))
		{
			this.actionType = ACTION_SQL;
		} else
			this.actionType = ACTION_NONE;

		this.available = true;
	}

	public Object invoke(Object[] args)
	{
		if (!this.available)
		{
			logger.error(this.beanName + "." + this.methodName + " is invalid");
			return null;
		}

		if (daoFunctionAnnotation.getParamCount() > 0
				&& (args == null || args.length != daoFunctionAnnotation.getParamCount()))
		{
			logger.error(beanName + "." + methodName + ":invalid invoke paramter list,this method need "
					+ daoFunctionAnnotation.getParamCount() + " paramter");
			return null;
		}

		
		DBZone[] dbZones = null;
		if(isGameDataZone)
		{
			dbZones = new DBZone[1];
			dbZones[0] = DBSpace.getGameDataDBZone();
		}
		else
			dbZones = this.daoFunctionAnnotation.getDBZone(args);
		if (dbZones == null || dbZones.length == 0)
		{
			logger.error(beanName + "." + methodName + " can't match a valid dbzone,dbrule="
					+ daoFunctionAnnotation.getDbRuleName());
			return null;
		}

		Object tmpParam = null;
		if (this.paramType == PARAM_MAP)
		{
			tmpParam = daoFunctionAnnotation.createStatementMap(args);
		} else if (daoFunctionAnnotation.getParamCount() > 0)
		{
			tmpParam = args[0];
		}

		Object result = null;
		int i = 0;
		for (i = 0; i < dbZones.length; i++)
		{
			try
			{
				switch (actionType)
				{
				case ACTION_SELECT:
					result = (daoFunctionAnnotation.getParamCount() > 0) ? dbZones[i].queryForObject(statementId,
							tmpParam) : dbZones[i].queryForObject(statementId);
					if (result != null)
					{
						i = dbZones.length;
					}
					break;
				case ACTION_SELECT_FORLIST:
					result = (daoFunctionAnnotation.getParamCount() > 0) ? dbZones[i].queryForList(statementId,
							tmpParam) : dbZones[i].queryForList(statementId);
					if (result != null)
					{
						i = dbZones.length;
					}
					break;
				case ACTION_INSERT:
					result = (daoFunctionAnnotation.getParamCount() > 0) ? dbZones[i].insert(statementId, tmpParam)
							: dbZones[i].insert(statementId);
					break;
				case ACTION_UPDATE:
					result = (daoFunctionAnnotation.getParamCount() > 0) ? dbZones[i].update(statementId, tmpParam)
							: dbZones[i].update(statementId);
					break;
				case ACTION_DELETE:
					result = (daoFunctionAnnotation.getParamCount() > 0) ? dbZones[i].delete(statementId, tmpParam)
							: dbZones[i].delete(statementId);
					break;
				case ACTION_PROCEDURE:
					result = null;
					break;
				case ACTION_SQL:
					result = null;
					break;
				default:
					logger.error("invalid option type");
					break;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public String getMethodName()
	{
		return methodName;
	}

}
