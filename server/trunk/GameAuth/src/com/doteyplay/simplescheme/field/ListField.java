package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SchemeLoader;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.fieldvalue.ListFieldValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ListField extends AbstractField
{
	private String dataTypeFlag;
	private SimpleScheme itemScheme;

	public ListField(String name, String title, byte datatype, SimpleScheme scheme)
	{
		super(name, title, datatype, scheme);
	}

	public ListField(String name, String title, String datatypeflag, SimpleScheme scheme)
	{
		super(name, title, SchemeConstants.DATATYPE_OBJECT, scheme);
		dataTypeFlag = datatypeflag;
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_LIST;
	}

	public SimpleScheme getItemScheme()
	{
		return itemScheme;
	}

	public void initExtProperty(ISimpleParamters element)
	{
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_LIST).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				this.title).addPrty(SchemeConstants.PROPERTY_FLAG_INDEX, this.getLocalIndex());
		if (this.dataType == SchemeConstants.DATATYPE_OBJECT)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE, "#" + this.dataTypeFlag);
		else
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE,
					SchemeConstants.DATATYPE_FLAGS[this.dataType]);
		if (this.syncFlag != null)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_SYNC, this.syncFlag);
		return tmpNode;
	}

	public void init()
	{
		if (this.dataType == SchemeConstants.DATATYPE_OBJECT)
		{
			String tmpKey = scheme.getName() + "." + dataTypeFlag;
			itemScheme = SchemeLoader.getScheme(tmpKey);
			if (itemScheme != null)
			{
				dataTypeFlag = tmpKey;
			}
			else
			{
				itemScheme = SchemeLoader.getScheme(dataTypeFlag);
			}
		}
	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return new ListFieldValue(refdata, this, itemScheme);
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}
}
