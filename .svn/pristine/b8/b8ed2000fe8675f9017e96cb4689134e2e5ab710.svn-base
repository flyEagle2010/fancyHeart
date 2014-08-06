package com.doteyplay.utils.asst;

import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.doteyplay.utils.ParamUtils;
import com.doteyplay.utils.StrUtils;

public class ParamHandler extends DefaultHandler
{
	private boolean _valid;
	private Properties _data;

	public ParamHandler()
	{
		_data = null;
		_valid = false;
	}

	public ParamHandler(Properties refdata)
	{
		this._data = refdata;
		_valid = false;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		if (this._data != null)
		{
			if (qName.compareToIgnoreCase("object") == 0)
			{
				String tmpFlagData = attributes.getValue(ParamUtils.MSG_STRC_FLAG);
				_valid = (tmpFlagData != null && tmpFlagData.compareTo(ParamUtils.MSG_STRC_VALUE) == 0);
			}
			else if (_valid && qName.compareToIgnoreCase("property") == 0)
			{
				String tmpName = attributes.getValue("name");
				if (tmpName != null && tmpName.length() > 0)
					this._data.setProperty(tmpName, StrUtils.nvl(attributes.getValue("value")));
			}
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException
	{
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
	}

	public Properties getData()
	{
		return this._data;
	}

}
