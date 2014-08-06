package com.doteyplay.game.util.excel;

import java.io.IOException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class JdomUtils
{

	public static final String ARRAY_SEPARATOR = ";";

	public JdomUtils()
	{
	}

	public static Element getRootElemet(String xmlPath)
	{
		SAXBuilder builder;
		Document doc;
		builder = new SAXBuilder();
		doc = null;
		try
		{
			doc = builder.build(xmlPath);
			return doc.getRootElement();
		} catch (JDOMException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Element getRootElemet(URL xmlPath)
	{
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try
		{
			doc = builder.build(xmlPath);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc.getRootElement();
	}

	public static int getIntAttriValue(Element e, String attrName)
	{
		String attributeValue = e.getAttributeValue(attrName);
		if (attributeValue == null)
			return -1;
		else
			return Integer.valueOf(attributeValue).intValue();
	}

	public static short getShortAttriValue(Element e, String attrName)
	{
		String attributeValue = e.getAttributeValue(attrName);
		if (attributeValue == null)
			return -1;
		else
			return Short.valueOf(attributeValue).shortValue();
	}

	public static byte getByteAttriValue(Element e, String attrName)
	{
		String attributeValue = e.getAttributeValue(attrName);
		if (attributeValue == null)
			return -1;
		else
			return Byte.valueOf(attributeValue).byteValue();
	}

	public static int[] getIntArrayAttriValue(Element e, String attrName)
	{
		String temp[] = e.getAttributeValue(attrName).split(";");
		int result[] = new int[temp.length];
		for (int i = 0; i < temp.length; i++)
			result[i] = Integer.valueOf(temp[i]).intValue();

		return result;
	}

	public static short[] getShortArrayAttriValue(Element e, String attrName)
	{
		String temp[] = e.getAttributeValue(attrName).split(";");
		short result[] = new short[temp.length];
		for (int i = 0; i < temp.length; i++)
			result[i] = Short.valueOf(temp[i]).shortValue();

		return result;
	}
}
