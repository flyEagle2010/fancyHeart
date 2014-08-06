package com.doteyplay.xmlsupport;

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

	public String getDataName()
	{
		return this.refNodeName;
	}

	public boolean isDataGroup(String paramprefix)
	{
		return (refNodeName != null && paramprefix != null && refNodeName.startsWith(paramprefix));
	}

	public void release()
	{
		this.refAttributes = null;
	}

	public int getLength()
	{
		return (refAttributes != null) ? refAttributes.getLength() : 0;
	}

	public String getParamterName(int index)
	{
		return (refAttributes != null) ? refAttributes.getQName(index) : null;
	}

	public String getValue(int index)
	{
		return (refAttributes != null) ? refAttributes.getValue(index) : null;
	}

	public int getIndex(String prtyname)
	{
		return (refAttributes != null) ? refAttributes.getIndex(prtyname) : null;
	}

	public String getValue(String prtyname)
	{
		return (refAttributes != null) ? refAttributes.getValue(prtyname) : null;
	}
}
