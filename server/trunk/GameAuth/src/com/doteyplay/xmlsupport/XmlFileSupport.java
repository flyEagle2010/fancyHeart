package com.doteyplay.xmlsupport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlFileSupport extends DefaultHandler
{
	private static class DtdExcludeResolver implements EntityResolver
	{

		public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
		{
			InputSource result = new InputSource(new StringReader(""));
			return result;
		}
	}

	private IParamterSupport _refHolder;

	public XmlFileSupport(IParamterSupport paramholder)
	{
		this._refHolder = paramholder;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		if (_refHolder != null)
		{
			XmlSimpleParamters refAtt = new XmlSimpleParamters(qName, attributes);
			_refHolder.putParamter(refAtt);
			refAtt.release();
			refAtt = null;
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (this._refHolder != null)
		{
			_refHolder.endParamter(qName);
		}
	}

	// ***********************************************************************************************
	// ����Դ����
	public static void parseXmlFromResource(String resname, IParamterSupport paramholder)
	{
		InputStream tmpIn = null;
		try
		{
			tmpIn = XmlFileSupport.class.getResourceAsStream(resname);
			if (tmpIn != null)
				parseXmlData(tmpIn, paramholder);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			if (tmpIn != null)
			{
				try
				{
					tmpIn.close();
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		}
	}

	// ***********************************************************************************************
	// ���ļ�����
	public static void parseXmlFromFile(String filename, IParamterSupport paramholder)
	{
		File tmpFile = null;
		FileInputStream tmpIn = null;
		try
		{
			tmpFile = new File(filename);
			if (!tmpFile.exists())
				return;

			tmpIn = new FileInputStream(tmpFile);
			parseXmlData(tmpIn, paramholder);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			if (tmpIn != null)
			{
				try
				{
					tmpIn.close();
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
			tmpFile = null;
		}
	}

	// ���ַ����
	public static void parseXmlData(String soustr, IParamterSupport paramholder)
	{
		parseXmlData(new ByteArrayInputStream(soustr.getBytes()), paramholder);
	}

	// ��������
	public static void parseXmlData(InputStream soustream, IParamterSupport paramholder)
	{
		if (soustream != null && paramholder != null)
		{
			XmlFileSupport tmpHandler = null;
			SAXParser parser = null;
			SAXParserFactory factory = null;
			try
			{
				tmpHandler = new XmlFileSupport(paramholder);
				factory = SAXParserFactory.newInstance();
				factory.setNamespaceAware(false);
				factory.setValidating(false);
				try
				{
					parser = factory.newSAXParser();
					parser.getXMLReader().setEntityResolver(new DtdExcludeResolver());
					parser.parse(new InputSource(soustream), tmpHandler);
					paramholder.onComplete();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			finally
			{
				factory = null;
				parser = null;
				tmpHandler = null;
			}
		}
	}
}
