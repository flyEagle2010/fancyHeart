package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SchemeLoader;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.fieldvalue.ReferenceFieldValue;
import com.doteyplay.simplescheme.value.CompatibleInt;
import com.doteyplay.simplescheme.value.ICompatibleValue;
import com.doteyplay.utils.StrUtils;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ReferenceField extends AbstractField
{
	private String[] refSchemeNames;
	private SimpleScheme[] refSchemes;
	private CompatibleInt defaultValue;

	public ReferenceField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, ICompatibleValue.DATATYPE_INT, scheme);
		defaultValue = new CompatibleInt();
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_REFERENCE;
	}

	public int refSchemeCount()
	{
		return (refSchemeNames != null) ? refSchemeNames.length : 0;
	}

	public String refSchemeName(int idx)
	{
		if (idx >= 0 && refSchemeNames != null && idx < refSchemeNames.length)
			return refSchemeNames[idx];
		else
			return null;

	}

	public void initExtProperty(ISimpleParamters element)
	{
		refSchemeNames = StrUtils.simpleSplit(this.data, "|");
		defaultValue.setValue(element.getValue(SchemeConstants.PROPERTY_FLAG_DEFAULT));
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_REFERENCE).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				SchemeConstants.DATATYPE_FLAGS[this.dataType]).addPrty(SchemeConstants.PROPERTY_FLAG_INDEX,
				this.getLocalIndex());
		if (refSchemeNames != null && refSchemeNames.length > 0)
		{
			StringBuffer tmpBuffer = new StringBuffer(refSchemeNames[0]);
			for (int i = 1; i < refSchemeNames.length; i++)
			{
				tmpBuffer.append("|").append(refSchemeNames[i]);
			}
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DATA, tmpBuffer.toString());
		}
		return tmpNode;
	}

	public void init()
	{
		if (refSchemeNames != null && refSchemeNames.length > 0)
		{
			refSchemes = new SimpleScheme[refSchemeNames.length];
			SimpleScheme tmpScheme;
			for (int i = 0; i < refSchemeNames.length; i++)
			{
				tmpScheme = SchemeLoader.getScheme(scheme.getName() + "." + refSchemeNames[i]);
				if (tmpScheme != null)
					refSchemes[i] = tmpScheme;
				else
					refSchemes[i] = SchemeLoader.getScheme(refSchemeNames[i]);
			}
		}
	}

	public ICompatibleValue newData()
	{
		return defaultValue.clone();
	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return new ReferenceFieldValue(refdata, this, defaultValue.asInt());
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}
}
