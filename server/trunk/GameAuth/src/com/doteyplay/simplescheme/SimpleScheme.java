package com.doteyplay.simplescheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.simplescheme.field.AbstractField;
import com.doteyplay.xmlsupport.SimpleXmlBuilder;

public class SimpleScheme
{
	private String name;
	private String title;
	private String version;
	private String namespace;
	private boolean valid;
	private boolean schemeLocked;
	private SchemeLoader schemeLoader;
	private SimpleScheme parentScheme;
	private Map<String, AbstractField> fieldMap;
	private List<AbstractField> fieldList;

	public SimpleScheme(String name, String title, String version, SimpleScheme parent, SchemeLoader loader)
	{
		this.schemeLoader = loader;
		if (parent != null)
		{
			this.parentScheme = parent;
			this.namespace = parent.getName();
			this.name = this.namespace + "." + name;
			this.title = title;
			this.version = version;
		}
		else
		{
			this.parentScheme = null;
			this.namespace = "";
			this.name = name;
			this.title = title;
			this.version = version;
		}
		fieldMap = new HashMap<String, AbstractField>();
		fieldList = new ArrayList<AbstractField>();
		schemeLocked = false;
	}

	public SchemeLoader getLoader()
	{
		return this.schemeLoader;
	}

	public boolean isSchemeOf(String schemename)
	{
		return (schemename != null && schemename.equals(this.name));
	}

	public boolean isSchemeLike(String schemename)
	{
		return (schemename != null && this.name != null && this.name.endsWith("." + schemename));
	}

	public void lockScheme()
	{
		schemeLocked = true;
	}

	public boolean isValid()
	{
		return valid;
	}

	public String getName()
	{
		return name;
	}

	public String getTitle()
	{
		return title;
	}

	public String getVersion()
	{
		return version;
	}

	public String getNamespace()
	{
		return namespace;
	}

	public int getFieldCount()
	{
		return fieldList.size();
	}

	public AbstractField getField(String fieldname)
	{
		if (fieldname != null && fieldname.length() > 0)
			return (AbstractField) fieldMap.get(fieldname);
		else
			return null;
	}

	public AbstractField getFieldByIndex(int idx)
	{
		if (idx >= 0 && idx < fieldList.size())
			return (AbstractField) fieldList.get(idx);
		else
			return null;
	}

	public boolean regField(AbstractField field)
	{
		if (field == null)
			return false;

		if (fieldMap.containsKey(field.getName()))
			return false;

		field.setLocalIndex((byte) fieldList.size());
		fieldList.add(field);
		fieldMap.put(field.getName(), field);
		return true;
	}

	/**
	 * ������ֶ����ԣ��������ֶ�����
	 * 
	 * @return
	 */
	public boolean init()
	{
		AbstractField tmpField;
		for (int i = 0; i < fieldList.size(); i++)
		{
			tmpField = (AbstractField) fieldList.get(i);
			tmpField.init();
		}
		return true;
	}

	public int fieldIndex(String fieldname)
	{
		AbstractField tmpField = this.fieldMap.get(fieldname);
		if (tmpField != null)
			return tmpField.getLocalIndex();
		else
			return -1;
	}

	public SimpleXmlBuilder exportXml(SimpleXmlBuilder xmlbuffer)
	{
		SimpleXmlBuilder tmpNode;
		if (xmlbuffer != null)
			tmpNode = xmlbuffer.addNode(SchemeConstants.ELEMENT_FLAG_SCHEME);
		else
			tmpNode = new SimpleXmlBuilder(SchemeConstants.ELEMENT_FLAG_SCHEME);
		tmpNode.addPrty(SchemeConstants.PROPERTY_FLAG_NAME, this.name).addPrty(
				SchemeConstants.PROPERTY_FLAG_VERSION, this.version);
		AbstractField tmpField;
		for (int i = 0; i < fieldList.size(); i++)
		{
			tmpField = (AbstractField) fieldList.get(i);
			tmpField.exportXml(tmpNode);
		}
		return tmpNode;
	}

	public String exportXml()
	{
		SimpleXmlBuilder tmpNode = exportXml(null);
		return tmpNode.exportXml();
	}

	public SimpleSchemeData newInstnce(String primarykey, String name, String version)
	{
		SimpleSchemeData instance = new SimpleSchemeData(this, primarykey, name, version);
		if (!this.isSubScheme())
			instance.initNewInstance();
		return instance;
	}

	public boolean isSubScheme()
	{
		return (parentScheme != null);
	}

	public SimpleScheme getParentScheme()
	{
		return parentScheme;
	}
}
