package com.doteplay.editor.datahelper;

import java.util.Date;

import com.doteplay.editor.util.xml.simpleXML.StrUtils;

/*
 * 自描述兼容类型元数据。 
 * 目标：为逐步降低各类数据之间的耦合度，设计提供一种高效文本访问模式元数据类型，来逐步取代不同数据之间的直接访问 
 * 设计要求：
 * 1、业务无关，完全体现元数据要求; 
 * 2、高效，保证正常的工作效率; 
 * 3、自描述，能检查到数据来源和编辑模式;
 * 4、较低内存使用，保证普通PC上直接可用
 */
public class MetaData
{

	public final static String DFT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

	private MetaDataScheme refScheme;// 数据模板

	private String dataGroup;
	private int[] intDatas;
	private String[] strDatas;
	private Date[] dateDatas;

	public MetaData(MetaDataScheme scheme)
	{
		if (scheme != null)
		{
			this.refScheme = scheme;
			dataGroup = scheme.getDataGroup();
			int fcount = scheme
					.getTypeFieldCount(MetaDataScheme.DATA_TYPE_STRING);
			if (fcount > 0)
				strDatas = new String[fcount];
			fcount = scheme.getTypeFieldCount(MetaDataScheme.DATA_TYPE_INT);
			if (fcount > 0)
				intDatas = new int[fcount];
			fcount = scheme.getTypeFieldCount(MetaDataScheme.DATA_TYPE_DATE);
			if (fcount > 0)
				dateDatas = new Date[fcount];
		}
	}

	public String getDataGroup()
	{
		return dataGroup;
	}

	public boolean fieldExist(String dname)
	{
		return (refScheme != null && refScheme.fieldExist(dname));
	}

	public void setIntData(String dname, int dvalue)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_INT)
			{
				this.intDatas[tmpField.getIndex()] = dvalue;
			}
		}
	}

	public int getIntData(String dname)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_INT)
			{
				return this.intDatas[tmpField.getIndex()];
			}
		}
		return 0;
	}

	public void setStrData(String dname, String dvalue)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_STRING)
			{
				this.strDatas[tmpField.getIndex()] = dvalue;
			}
		}
	}

	public String getStrData(String dname)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_STRING)
			{
				return this.strDatas[tmpField.getIndex()];
			}
		}
		return null;
	}

	public void setDateData(String dname, Date dvalue)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_DATE)
			{
				this.dateDatas[tmpField.getIndex()] = dvalue;
			}
		}
	}

	public Date getDateData(String dname)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_DATE)
			{
				return this.dateDatas[tmpField.getIndex()];
			}
		}
		return null;
	}

	public void setData(String dname, String dvalue)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_STRING)
			{
				this.strDatas[tmpField.getIndex()] = dvalue;
			} else if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_INT)
			{
				this.intDatas[tmpField.getIndex()] = StrUtils.parseint(dvalue,
						0);
			} else if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_DATE)
			{
				this.dateDatas[tmpField.getIndex()] = StrUtils.parseDate(
						dvalue, DFT_DATE_FORMAT, new Date(0l));
			}
		}
	}

	public String getData(String dname)
	{
		if (refScheme != null)
		{
			MetaDataField tmpField = refScheme.fieldInfo(dname);
			if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_STRING)
			{
				return this.strDatas[tmpField.getIndex()];
			} else if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_INT)
			{
				return String.valueOf(intDatas[tmpField.getIndex()]);
			} else if (tmpField != null
					&& tmpField.getDataType() == MetaDataScheme.DATA_TYPE_DATE)
			{
				return StrUtils.dateToStr(dateDatas[tmpField.getIndex()],
						DFT_DATE_FORMAT);
			}
		}
		return null;
	}

}
