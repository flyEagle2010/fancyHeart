package com.doteyplay.utils.asst;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ParamParser extends DefaultHandler
{
	private static class DtdExcludeResolver implements EntityResolver
	{
		public InputSource resolveEntity(String publicId, String systemId) throws SAXException,
				IOException
		{
			InputSource result = new InputSource(new StringReader(""));
			return result;
		}
	}

	public static Properties parse(String soustr)
	{
		Properties result = new Properties();
		if (soustr != null && soustr.length() > 0)
		{
			ParamHandler tmpHandler = null;
			SAXParser parser = null;
			SAXParserFactory factory = null;
			try
			{
				tmpHandler = new ParamHandler(result);
				factory = SAXParserFactory.newInstance();
				factory.setNamespaceAware(false);
				factory.setValidating(false);

				try
				{
					parser = factory.newSAXParser();
					XMLReader tmpReader = parser.getXMLReader();
					tmpReader.setEntityResolver(new DtdExcludeResolver());
					tmpReader.setContentHandler(tmpHandler);

					InputSource tmpInputSource = new InputSource(new StringReader(soustr));
					parser.getXMLReader().parse(tmpInputSource);
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
		return result;
	}
}
