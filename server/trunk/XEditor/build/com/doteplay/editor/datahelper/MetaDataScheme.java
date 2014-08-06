package com.doteplay.editor.datahelper;

import java.util.HashMap;

import com.doteplay.editor.util.xml.simpleXML.ISimpleXmlSupport;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;
import com.doteplay.editor.util.xml.simpleXML.SimpleXmlParser;
import com.doteplay.editor.util.xml.simpleXML.StrUtils;

/*
 * 元数据模板定义。 自描述兼容类型元数据，设计目标：为逐步降低各类数据之间的耦合度，设计提供一种高效文本访问模式元数据类型，来逐步取代不同数据直接的直接访问
 * 设计要求： 1、业务无关，完全体现元数据要求; 2、高效，保证正常的工作效率;
 * 3、自描述，能检查到数据来源和编辑模式;4、较低内存使用，保证普通PC上直接可用
 */
public class MetaDataScheme implements ISimpleXmlSupport
{

	public final static int COUNT_DATA_TYPE = 3;// 数据类型总数
	public final static int DATA_TYPE_STRING = 0;// 字符串数据
	public final static int DATA_TYPE_INT = 1;// 整型数据
	public final static int DATA_TYPE_DATE = 2;// 字符串数据

	private String dataGroup;
	private HashMap<String, MetaDataField> dataMap;
	private int[] fieldCounts;

	public MetaDataScheme()
	{
		dataGroup = "unknown";
		dataMap = new HashMap<String, MetaDataField>();
		fieldCounts = new int[COUNT_DATA_TYPE];
	}

	public void reset()
	{
		dataMap.clear();
		for (int i = 0; i < COUNT_DATA_TYPE; i++)
			fieldCounts[0] = 0;
	}

	public void regField(String dataname, String datadesc, int datatype)
			throws MetaDataException
	{
		if (dataname != null && dataname.length() > 0 && datatype >= 0
				&& datatype < COUNT_DATA_TYPE && !dataMap.containsKey(dataname))
		{
			MetaDataField newField = null;
			if (datatype >= 0 && datatype < COUNT_DATA_TYPE)
				newField = new MetaDataField(dataname, datadesc, datatype,
						fieldCounts[datatype]++);
			if (newField != null)
			{
				dataMap.put(dataname, newField);
				newField = null;
				return;
			} else
				throw new MetaDataException("元数据字段" + dataname + "类型无效");
		}
		throw new MetaDataException("元数据字段" + dataname + "注册失败");
	}

	public void loadDataScheme(String schemecontent)
	{
		SimpleXmlParser.parseXmlData(schemecontent, this);
	}

	public void loadDataSchemeFile(String fname)
	{
		SimpleXmlParser.parseXmlFramFile(fname, this);
	}

	public boolean fieldExist(String fname)
	{
		return (fname != null && dataMap.containsKey(fname));
	}

	public int getTypeFieldCount(int datatype)
	{
		if (datatype >= 0 && datatype < COUNT_DATA_TYPE)
			return fieldCounts[datatype];
		else
			return 0;
	}

	public MetaDataField fieldInfo(String fieldname)
	{
		if (fieldname != null)
			return (MetaDataField) this.dataMap.get(fieldname);
		else
			return null;
	}

	@Override
	public void readXmlData(String nodename, SimpleAttributes attributes)
	{
		if ("field".equals(nodename))
		{
			try
			{
				String tmpName = attributes.getValue("name");
				String tmpDesc = attributes.getValue("desc");
				int tmpDataType = StrUtils.parseint(
						attributes.getValue("datatype"), DATA_TYPE_STRING);
				this.regField(tmpName, tmpDesc, tmpDataType);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else if ("datagroup".equals(nodename))
		{
			try
			{
				this.dataGroup = attributes.getValue("name");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setDataGroup(String dataGroup)
	{
		this.dataGroup = dataGroup;
	}

	public String getDataGroup()
	{
		return dataGroup;
	}

}
