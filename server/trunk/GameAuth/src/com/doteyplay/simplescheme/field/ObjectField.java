package com.doteyplay.simplescheme.field;

import com.doteyplay.simplescheme.SchemeConstants;
import com.doteyplay.simplescheme.SchemeLoader;
import com.doteyplay.simplescheme.SimpleScheme;
import com.doteyplay.simplescheme.SimpleSchemeData;
import com.doteyplay.simplescheme.fieldvalue.AbstractFieldValue;
import com.doteyplay.simplescheme.fieldvalue.ObjectFieldValue;
import com.doteyplay.simplescheme.value.CompatibleNone;
import com.doteyplay.simplescheme.value.ICompatibleValue;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class ObjectField extends AbstractField
{
	private String dataTypeFlag;
	private SimpleScheme dataScheme;
	private ICompatibleValue defaultValue;

	public ObjectField(String name, String title, String datatypeflag, SimpleScheme scheme)
	{
		super(name, title, SchemeConstants.DATATYPE_OBJECT, scheme);
		dataTypeFlag = datatypeflag;
		defaultValue = new CompatibleNone();
	}

	public byte fieldType()
	{
		return SchemeConstants.FIELD_TYPE_OBJECT;
	}

	public ICompatibleValue defaultValue()
	{
		return defaultValue;
	}

	public void initExtProperty(ISimpleParamters element)
	{
	}

	public SimpleScheme getDataScheme()
	{
		return dataScheme;
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_FIELD).addPrty(
				SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(SchemeConstants.PROPERTY_FLAG_TITLE,
				this.title).addPrty(SchemeConstants.PROPERTY_FLAG_DATATYPE, "#" + this.dataTypeFlag).addPrty(
				SchemeConstants.PROPERTY_FLAG_INDEX, this.getLocalIndex());
		if (this.data != null)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_DATA, this.data);
		if (this.syncFlag != null)
			tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_SYNC, this.syncFlag);
		return tmpNode;
	}

	public void init()
	{
		SimpleScheme tmpScheme = SchemeLoader.getScheme(scheme.getName() + "." + dataTypeFlag);
		if (tmpScheme != null)
			dataScheme = tmpScheme;
		else
			dataScheme = SchemeLoader.getScheme(dataTypeFlag);
	}

	public AbstractFieldValue creaetFieldValue(SimpleSchemeData refdata)
	{
		return new ObjectFieldValue(refdata, this, dataScheme);
	}

	public String getExtProperty(String prtyname)
	{
		return null;
	}
}
