package com.doteyplay.game.util.excel;

import java.util.Arrays;

import com.doteyplay.core.util.dependence.IDependence;
import com.doteyplay.core.util.dependence.IDependenceAssembly;

public class TemplateConfig implements IDependence
{
	private String fileName;
	private String parserClassName;
	private Class classes[];
	private String[] depends;

	public TemplateConfig(String fileName, Class classes[],String[] depends)
	{
		this.fileName = fileName;
		this.classes = classes;
		this.depends = depends;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getParserClassName()
	{
		return parserClassName;
	}

	public void setParserClassName(String parserClassName)
	{
		this.parserClassName = parserClassName;
	}

	public void setClasses(Class classes[])
	{
		this.classes = classes;
	}

	public Class[] getClasses()
	{
		return classes;
	}

	public String[] getDepends()
	{
		return depends;
	}

	public void setDepends(String[] depends)
	{
		this.depends = depends;
	}

	@Override
	public String toString()
	{
		return "TemplateConfig [fileName=" + fileName + ", parserClassName="
				+ parserClassName + ", classes=" + Arrays.toString(classes)
				+ ", depends=" + Arrays.toString(depends) + "]";
	}

	@Override
	public String[] getDependence()
	{
		return depends;
	}
}
