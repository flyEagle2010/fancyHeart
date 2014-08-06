package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.value.ICompatibleValue;
import com.doteyplay.utils.StrUtils;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class EnumField extends AbstractField
{
	private byte[] enumValues;
	private String[] enumTitles;

	public EnumField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, ICompatibleValue.DATATYPE_BYTE, scheme);
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_ENUM;
	}

	public void initExtProperty(ISimpleParamters element)
	{
		String[] enumDefines = StrUtils.simpleSplit(element.getValue(SchemeConstants.PROPERTY_FLAG_VALUE),
				"|");
		if (enumDefines != null && enumDefines.length > 0)
		{
			enumValues = new byte[enumDefines.length];
			enumTitles = new String[enumDefines.length];
			int i, pos;
			for (i = 0; i < enumDefines.length; i++)
			{
				pos = -1;
				if (enumDefines[i] != null && enumDefines[i].length() > 0)
				{
					pos = enumDefines[i].indexOf(':');
					if (pos > 0 && pos < enumDefines[i].length() - 1)
					{
						enumValues[i] = StrUtils.parsebyte(enumDefines[i].substring(0, pos), (byte) 0);
						enumTitles[i] = enumDefines[i].substring(pos + 1);
					}
				}
				if (pos <= 0)
				{
					enumValues[i] = 0;
					enumTitles[i] = "";
				}
			}
		}
		else
		{
			enumValues = new byte[0];
			enumTitles = new String[0];
		}
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_ENUM).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				this.title).addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE,
				SchemeConstants.DATATYPE_FLAGS[this.dataType]).addPrty(SchemeConstants.PROPERTY_FLAG_INDEX,
				this.getLocalIndex());
		if (enumValues != null && enumValues.length > 0)
		{
			StringBuffer tmpBuffer = new StringBuffer(enumValues[0]).append(':').append(enumTitles[0]);
			for (int i = 1; i < enumValues.length; i++)
			{
				tmpBuffer.append('|').append(enumValues[i]).append(':').append(enumTitles[i]);
			}
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DATA, tmpBuffer.toString());
		}
		return tmpNode;
	}

	public void init()
	{
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return null;
	}

}
