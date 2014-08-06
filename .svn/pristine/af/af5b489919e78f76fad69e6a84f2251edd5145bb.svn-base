package com.doteyplay.core.dbcs.executor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.doteyplay.core.dbcs.daoproxy.DaoExecutorProxyEntrance;
import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.dbspace.DBZone;

/**
 * DAO接口执行代理
 * 对应Mapper，跟一组数据库操作接对应
 * @author 
 * 
 */
public class ExecutorProxy
{
	private static final Logger logger = Logger.getLogger(ExecutorProxy.class);
	
	private static final String TRANSACTION_FLAG = "Transaction";
	private static final String START_TRANSACTION_FLAG = "startTransaction";
	private static final String COMMIT_TRANSACTION_FLAG = "commitTransaction";
	private static final String END_TRANSACTION_FLAG = "endTransaction";

	private String beanName;
	private Class<? extends IDaoExecutor> excutorInterface;
	private Map<String, StatementProxy> statementMap;// method名称与实现的对应
	private IDaoExecutor daoExcutor;
	private boolean available;
	
	private DBZone dbzone;
	private boolean isGameDataZone;

	public ExecutorProxy(String beanName)
	{
		this.beanName = beanName;
		statementMap = new HashMap<String, StatementProxy>();
	}

	public void initialize(Class<? extends IDaoExecutor> interfaceClass, DBZone dbzone,boolean isGameDataZone)
	{
		if (interfaceClass == null || dbzone == null)
		{
			return;
		}
		
		this.dbzone = dbzone;
		this.isGameDataZone = isGameDataZone;

		if (!IDaoExecutor.class.isAssignableFrom(interfaceClass))
		{
			logger.error("executor of " + this.beanName + "[" + interfaceClass.getName()
					+ "]  need inherit IDaoExecutor interface");
			return;
		}

		String tmpClassName=interfaceClass.getSimpleName();
		Method[] tmpMethods = interfaceClass.getMethods();
		DAOInfo tmpDAOInfo;
		StatementProxy tmpStatementProxy;
		DaoFunctionAnnotation functionAnnotation;
		for (Method tmpMethod : tmpMethods)
		{
			tmpDAOInfo = (DAOInfo) tmpMethod.getAnnotation(DAOInfo.class);
			if (tmpDAOInfo != null)
			{
				functionAnnotation = new DaoFunctionAnnotation(tmpClassName+"."+tmpMethod.getName(),tmpDAOInfo.Params(),tmpMethod.getParameterTypes());
				if (functionAnnotation.isValid())
				{
					tmpStatementProxy = new StatementProxy(beanName, tmpMethod, dbzone, functionAnnotation,isGameDataZone);
					statementMap.put(tmpMethod.getName(), tmpStatementProxy);
				}
				else
				{
					logger.error(this.beanName + "." + tmpMethod.getName() + ": method annotation is invalid");
				}
			}
			else
			{
				if(!tmpMethod.getName().contains(TRANSACTION_FLAG))
					logger.error(this.beanName + "." + tmpMethod.getName() + ": method annotation not exist");
			}
		}

		this.excutorInterface = interfaceClass;
		Class<?> clazzProxy = Proxy.getProxyClass(excutorInterface.getClassLoader(), excutorInterface);
		try
		{
			Constructor<?> excutorProxyConstructor = clazzProxy.getConstructor(InvocationHandler.class);
			daoExcutor = (IDaoExecutor) excutorProxyConstructor
					.newInstance(new DaoExecutorProxyEntrance(this));
			available = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void regStatementProxy(StatementProxy statementProxy)
	{
		statementMap.put(statementProxy.getMethodName(), statementProxy);
	}

	public Object invokeStatement(String methodname, Object[] args)
	{
		if (!available)
		{
			logger.error("executor of " + this.beanName + " is invalid");
			return null;
		}

		try
		{
			if(START_TRANSACTION_FLAG.equals(methodname))
			{
				if(this.dbzone != null)
					dbzone.startTransaction();
				return true;
			}
			else if(COMMIT_TRANSACTION_FLAG.equals(methodname))
			{
				if(this.dbzone != null)
					dbzone.commitTransaction();
				return true;
			}
			else if(END_TRANSACTION_FLAG.equals(methodname))
			{
				if(this.dbzone != null)
					dbzone.endTransaction();
				return true;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}

		StatementProxy tmpStatementProxy = statementMap.get(methodname);
		if(tmpStatementProxy==null)
		{
			logger.error("executor "+methodname+" of " + this.beanName + " not exist");
			return null;
		}
		return tmpStatementProxy.invoke(args);
	}

	public IDaoExecutor getExecutor()
	{
		return this.daoExcutor;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public Class<? extends IDaoExecutor> getExcutorInterface()
	{
		return excutorInterface;
	}

	public String getExcutorInterfaceName()
	{
		return (excutorInterface != null) ? excutorInterface.getSimpleName() : null;
	}

}
