package com.doteyplay.core.bhns.active;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.source.IEndPointRule;
import com.doteyplay.core.util.dependence.IDependence;

/**
 * 
 * @author
 * 存贮active_service.xml的每个service信息的Bean
 */
public class ActiveServiceInfo implements IDependence
{
	private static final Logger logger = Logger
			.getLogger(ActiveServiceInfo.class);
	/**
	 * 名字.
	 */
	private String name;	
	/**
	 * 入口Id
	 */
	private int portalId;
	/**
	 * 依赖的所有的其它的服务的Id
	 */
	private String[] dependences;
	/**
	 * 依赖的规则的class[暂时无用.]
	 */
	private Class<IEndPointRule> ruleClass;
	/**
	 * 规则的实例ID[暂无用]
	 */
	private IEndPointRule rule;

	@SuppressWarnings("unchecked")
	public ActiveServiceInfo(String name, int portalId, String[] dependences,
			String ruleSource)
	{
		this.name = name;
		this.portalId = portalId;
		this.dependences = dependences;
		
		if(ruleSource != null && !ruleSource.trim().equals(""))
		{
			try
			{
				this.ruleClass = (Class<IEndPointRule>) Class.forName(ruleSource);
				this.rule = ruleClass.newInstance();

			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} catch (InstantiationException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
	
		}
	}

	public String getName()
	{
		return name;
	}

	public int getPortalId()
	{
		return portalId;
	}

	public Class<?> getRuleClass()
	{
		return ruleClass;
	}

	public IEndPointRule getRule()
	{
		return rule;
	}

	public void setRule(IEndPointRule rule)
	{
		this.rule = rule;
	}

	@Override
	public String[] getDependence()
	{
		return dependences;
	}
}
