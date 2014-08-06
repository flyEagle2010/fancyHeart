package com.doteyplay.simplescheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.utils.IOUtils;
import com.doteyplay.xmlsupport.IParamterSupport;
import com.doteyplay.xmlsupport.ISimpleParamters;
import com.doteyplay.xmlsupport.XmlFileSupport;

public class SchemeDataGroup implements IParamterSupport
{
	private final static String INDEX_FILE_NAME = "_index.xml";

	private String rootPath;
	private SimpleScheme dataScheme;
	private Map<String, SimpleSchemeData> dataMap;
	private List<SimpleSchemeData> dataList;

	public SchemeDataGroup(SimpleScheme scheme)
	{
		rootPath = null;
		this.dataScheme = scheme;
		dataMap = new HashMap<String, SimpleSchemeData>();
		dataList = new ArrayList<SimpleSchemeData>();
	}

	public SimpleScheme getScheme()
	{
		return this.dataScheme;
	}

	public String getGroupName()
	{
		return (this.dataScheme != null) ? this.dataScheme.getName() : "";
	}

	public SimpleSchemeData findData(String primarykey)
	{
		if (primarykey == null || primarykey.length() <= 0)
			return null;

		SimpleSchemeData newdata = dataMap.get(primarykey);
		if (newdata == null)
		{
			newdata = dataScheme.newInstnce(primarykey, "", dataScheme.getVersion());
			// FileInputStream inStream = null;
			// File srcfile;
			// try
			// {
			// newdata = dataScheme.newInstnce(primarykey, "",
			// dataScheme.getVersion());
			// srcfile = new File(this.rootPath + primarykey + ".dat");
			// if (srcfile.exists())
			// {
			// inStream = new FileInputStream(srcfile);
			// newdata.decode(inStream);
			// inStream.close();
			// inStream = null;
			// }
			// }
			// catch (Exception e)
			// {
			// e.printStackTrace();
			// }
			// finally
			// {
			// try
			// {
			// if (inStream != null)
			// {
			// inStream.close();
			// inStream = null;
			// }
			// }
			// catch (Exception e)
			// {
			// e.printStackTrace();
			// }
			// }
			dataMap.put(newdata.getPrimaryKey(), newdata);
			dataList.add(newdata);
		}
		if (!newdata.isInited())
		{
			try
			{
				SchemeDataLoader.loadSchemeDataFromXmlFile(newdata, rootPath + primarykey + ".xml");
				newdata.setInited(true);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newdata;
	}

	public void verifyData(IDataVerify verify)
	{
		SimpleSchemeData tmpItemData;
		for (int i = 0; i < dataList.size(); i++)
		{
			tmpItemData = (SimpleSchemeData) dataList.get(i);
			if (tmpItemData != null)
			{
				tmpItemData.verify(verify);
			}
		}
	}

	@Override
	public void putParamter(ISimpleParamters element)
	{
		String tmpElementName = element.getDataName();
		if (SchemeConstants.ELEMENT_FLAG_DATABRIEF.equals(tmpElementName))
		{
			String tmpScheme = element.getValue(SchemeConstants.PROPERTY_FLAG_SCHEME);
			String tmpId = element.getValue(SchemeConstants.PROPERTY_FLAG_ID);
			String tmpName = element.getValue(SchemeConstants.PROPERTY_FLAG_NAME);
			String tmpVersion = element.getValue(SchemeConstants.PROPERTY_FLAG_VERSION);

			if (this.dataScheme == null || !this.dataScheme.getName().equals(tmpScheme))
			{
				return;
			}
			if (this.dataMap.containsKey(tmpId))
			{
				return;
			}
			SimpleSchemeData newdata = dataScheme.newInstnce(tmpId, tmpName, tmpVersion);
			dataMap.put(newdata.getPrimaryKey(), newdata);
			dataList.add(newdata);
		}
	}
	
	@Override
	public void onComplete()
	{
		
	}

	@Override
	public void endParamter(String paramtername)
	{
	}

	public void loadDataIndex(String rootpath)
	{
		this.rootPath = IOUtils.fixPath(rootpath, true);
		try
		{
			try
			{
				XmlFileSupport.parseXmlFromFile(this.rootPath + INDEX_FILE_NAME, this);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<SimpleDataBrief> getAllDataBrief()
	{
		String tmpSchemeName = this.getGroupName();
		List<SimpleDataBrief> tmpList = new ArrayList<SimpleDataBrief>(this.dataList.size());
		for (SimpleSchemeData tmpData : dataList)
		{
			tmpList.add(new SimpleDataBrief(tmpSchemeName, tmpData.getPrimaryKey(), tmpData.getName(),
					tmpData.getVersion()));
		}
		return tmpList;
	}
}
