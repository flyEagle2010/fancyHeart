package com.doteyplay.xmlsupport;

import java.util.ArrayList;
import java.util.List;

public class SimpleXmlBuilder
{
	private SimpleXmlBuilder parentNode;
	private String name;
	private List<SimpleXmlBuilder> subNodeList;
	public StringBuffer properiesBuffer;

	public SimpleXmlBuilder(String name)
	{
		this(name, null);
	}

	public SimpleXmlBuilder(String name, SimpleXmlBuilder parent)
	{
		this.parentNode = parent;
		this.name = name;
		properiesBuffer = new StringBuffer();
	}
	
	public SimpleXmlBuilder addPrty(String prtyname, int value)
	{
		return addPrty(prtyname, String.valueOf(value));
	}
	
	public SimpleXmlBuilder addPrty(String prtyname, long value)
	{
		return addPrty(prtyname, String.valueOf(value));
	}

	public SimpleXmlBuilder addPrty(String prtyname, String value)
	{
		if (prtyname != null && prtyname.length() > 0)
		{
			properiesBuffer.append(" ").append(prtyname).append("=\"");
			if (value != null)
				encodeChars(value, properiesBuffer);
			properiesBuffer.append("\"");
		}
		return this;
	}

	public SimpleXmlBuilder addNode(String name)
	{
		if (name != null && name.length() > 0)
		{
			SimpleXmlBuilder r = new SimpleXmlBuilder(name, this);
			synchronized (this)
			{
				if (subNodeList == null)
					subNodeList = new ArrayList<SimpleXmlBuilder>();
				subNodeList.add(r);
			}
			return r;
		}
		else
			return null;
	}

	public void exportXml(String prefix, StringBuffer xmlbuffer)
	{
		if (xmlbuffer == null)
			return;

		synchronized (this)
		{
			if (this.parentNode == null)
			{
				xmlbuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				prefix = "";
			}
			else if (prefix == null)
				prefix = "";

			if (subNodeList == null || subNodeList.size() <= 0)
				xmlbuffer.append(prefix).append("<").append(name).append(properiesBuffer).append("/>\r\n");
			else
			{
				xmlbuffer.append(prefix).append("<").append(name).append(properiesBuffer).append(">\r\n");
				SimpleXmlBuilder tmpNode;
				for (int i = 0; i < subNodeList.size(); i++)
				{
					tmpNode = subNodeList.get(i);
					tmpNode.exportXml(prefix + "  ", xmlbuffer);
				}
				xmlbuffer.append(prefix).append("</").append(name).append(">\r\n");
			}
		}
	}

	public String exportXml()
	{
		StringBuffer buf = new StringBuffer();
		this.exportXml("", buf);
		return buf.toString();
	}

	public static String simpleMessage(String msg, int status)
	{
		return new SimpleXmlBuilder("message").addPrty("msg", msg).addPrty("status", status).exportXml();
	}

	private final static void encodeChars(String pvalue, StringBuffer buffer)
	{
		if (pvalue != null && buffer != null)
		{
			int tmpLen = pvalue.length();
			char tmpChar;
			for (int i = 0; i < tmpLen; i++)
			{
				tmpChar = pvalue.charAt(i);
				switch (tmpChar)
				{
					case 38: // '&'
						buffer.append("&amp;");
						break;
					case 34: // '"'
						buffer.append("&quot;");
						break;
					case 60: // '<'
						buffer.append("&lt;");
						break;
					case 62: // '>'
						buffer.append("&gt;");
						break;
					default:
						buffer.append(tmpChar);
						break;
				}
			}
		}
	}
}
