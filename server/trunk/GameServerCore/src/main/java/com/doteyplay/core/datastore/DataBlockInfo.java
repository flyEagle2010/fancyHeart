package com.doteyplay.core.datastore;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import com.doteyplay.core.util.dependence.IDependence;
/**
 * 数据块信息
 * 
 * @author 
 * 
 */
public class DataBlockInfo implements IDependence
{
	private static final Logger logger = Logger.getLogger(DataBlockInfo.class);

	private String blockName;
	private String interfaceFlagName;
	private String interfaceClassName;
	private String implClassName;
	private String[] dependence;
	private Class<?> interfaceClass;
	private IDataBlock impl;
	private boolean loaded;
	private boolean valid;

	public DataBlockInfo(String name, String interfacename, String implname, String[] dependences)
	{
		this.blockName = name;
		this.interfaceClassName = interfacename;
		this.implClassName = implname;
		this.dependence = dependences;
		this.loaded = false;
		this.valid = true;
	}

	public String getName()
	{
		return blockName;
	}

	@Override
	public String[] getDependence()
	{
		return dependence;
	}

	public String getInterfaceClassName()
	{
		return interfaceClassName;
	}

	public String getInterfaceFlagName()
	{
		return interfaceFlagName;
	}

	public String getImplClassName()
	{
		return implClassName;
	}

	public boolean isLoaded()
	{
		return this.loaded;
	}

	public IDataBlock createImplementInstance()
	{
		if (!this.loaded)
		{
			try
			{
				this.impl = null;
				this.loaded = true;
				this.valid = false;
				this.interfaceClass = Class.forName(this.interfaceClassName);
				this.interfaceFlagName = this.interfaceClass.getSimpleName();
				if (!IDataBlock.class.isAssignableFrom(interfaceClass))
				{
					return null;
				}
				Class<?> tmpImplClass = Class.forName(this.implClassName);
				if (!interfaceClass.isAssignableFrom(tmpImplClass))
				{
					return null;
				}
				Constructor<?> c = tmpImplClass.getDeclaredConstructor(null);
				c.setAccessible(true);
				this.impl = (IDataBlock) c.newInstance();
				this.valid = true;
			}
			catch (Exception e)
			{
				this.impl = null;
				e.printStackTrace();
			}
		}
		if (this.loaded && !this.valid)
		{
			logger.error("datablock " + this.blockName + " is invalid");
		}
		return this.impl;
	}

	public boolean isValid()
	{
		return valid;
	}

}
