package com.doteyplay.core.dbcs.config;

import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.dbspace.IDBZoneRule;
import com.doteyplay.core.util.xml.IParamterSupport;
import com.doteyplay.core.util.xml.ISimpleParamters;
import com.doteyplay.core.util.xml.XmlFileSupport;

/**
 * 数据库规则配置读取器
 * 
 * @author 
 * 
 */
public class DbRuleReader implements IParamterSupport
{
	private String ruleFile;

	public DbRuleReader(String rulefile)
	{
		this.ruleFile = rulefile;
	}

	public void doRead()
	{
		XmlFileSupport.parseXmlFromResource(ruleFile, this);
	}

	@Override
	public void putParamter(ISimpleParamters paramter)
	{
		if ("RULE".equals(paramter.getDataName()))
		{
			String tmpRuleClass = paramter.getValue("SOURCE");
			try
			{
				Class<?> tmpClass = Class.forName(tmpRuleClass);
				if (IDBZoneRule.class.isAssignableFrom(tmpClass))
				{
					DBSpace.regRule((IDBZoneRule) tmpClass.newInstance());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onComplete()
	{

	}

}
