package com.doteyplay.core.util.xml;

import org.xml.sax.Attributes;


public class XmlSimpleParamters implements ISimpleParamters
{
	private String refNodeName;
	private Attributes refAttributes;

	public XmlSimpleParamters(String nodename, Attributes refattributes)
	{
		this.refNodeName = nodename;
		this.refAttributes = refattributes;
	}

	public void release()
	{
		this.refAttributes = null;
	}

	public String getDataName()
	{
		return this.refNodeName;
	}

	public int getLength()
	{
		return (refAttributes != null) ? refAttributes.getLength() : 0;
	}

	public String getParamterName(int index)
	{
		return (refAttributes != null) ? refAttributes.getQName(index) : "";
	}

	public String getValue(int index)
	{
		return (refAttributes != null) ? refAttributes.getValue(index) : "";
	}

	public int getIndex(String prtyname)
	{
		return (refAttributes != null && prtyname != null) ? refAttributes.getIndex(prtyname) : 0;
	}

	public String getValue(String prtyname)
	{
		return (refAttributes != null) ? refAttributes.getValue(prtyname) : "";
	}
}
