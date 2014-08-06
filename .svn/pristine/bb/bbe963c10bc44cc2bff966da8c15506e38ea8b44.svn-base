package com.doteyplay.simplescheme;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.doteyplay.utils.IOUtils;

public class SchemeDataManager
{
	private final String SCHEME_ROOT_PATH = "scheme";
	private final String SCHEME_DATA_ROOT_PATH = "data";

	private String rootPath;
	private Map<String, SchemeDataGroup> allDataMap;
	private List<SchemeDataGroup> allDataList;

	public SchemeDataManager()
	{
		allDataMap = new HashMap<String, SchemeDataGroup>();
		allDataList = new Vector<SchemeDataGroup>();
	}

	public String getRootPath()
	{
		return rootPath;
	}

	public void setRootPath(String rootPath)
	{
		this.rootPath = rootPath;
	}

	public void loadSchemeFromText(String schemetext)
	{
		SchemeLoader.manualLoadSchemeText(schemetext);
	}

	public void loadAllScheme()
	{
		if (rootPath == null || rootPath.length() <= 0)
		{
			System.out.println("δָ����·�����޷�����ģ��");
			return;
		}

		String tmpSchemePath = IOUtils.fixPath(this.rootPath, true) + SCHEME_ROOT_PATH + File.separator;
		if (!IOUtils.dirExists(tmpSchemePath))
		{
			System.out.println("ģ���ļ��в�����:" + tmpSchemePath);
			return;
		}

		SchemeLoader.loadAllScheme(tmpSchemePath);
		List<SimpleScheme> tmpList = SchemeLoader.getAllScheme();
		if (tmpList != null && tmpList.size() > 0)
		{
			synchronized (allDataMap)
			{
				SimpleScheme tmpScheme;
				SchemeDataGroup tmpDataGroup;
				for (int i = 0; i < tmpList.size(); i++)
				{
					tmpScheme = (SimpleScheme) tmpList.get(i);
					if (tmpScheme != null && !allDataMap.containsKey(tmpScheme.getName()))
					{
						tmpDataGroup = new SchemeDataGroup(tmpScheme);
						allDataMap.put(tmpScheme.getName(), tmpDataGroup);
						allDataList.add(tmpDataGroup);
					}
				}
			}
		}
	}

	public void initAllScheme()
	{
		SchemeLoader.manualInitAllScheme();
	}

	public List<SchemeDataGroup> getAllScheme()
	{
		List<SchemeDataGroup> tmpList = new ArrayList<SchemeDataGroup>(allDataList.size());
		tmpList.addAll(allDataList);
		return tmpList;
	}

	public void loadAllDataIndex()
	{
		if (rootPath == null || rootPath.length() <= 0)
		{
			System.out.println("δָ����·�����޷��������");
			return;
		}

		String tmpRootPath = IOUtils.fixPath(this.rootPath, true) + SCHEME_DATA_ROOT_PATH + File.separator;
		if (!IOUtils.dirExists(tmpRootPath))
		{
			System.out.println("����ļ��в�����:" + tmpRootPath);
			return;
		}

		SchemeDataGroup tmpDataGroup;
		for (int i = 0; i < allDataList.size(); i++)
		{
			tmpDataGroup = (SchemeDataGroup) allDataList.get(i);
			if (tmpDataGroup.getScheme() != null && !tmpDataGroup.getScheme().isSubScheme())
				tmpDataGroup.loadDataIndex(tmpRootPath + tmpDataGroup.getGroupName());
		}
	}

	public SimpleSchemeData getData(String dataname, String primarykey)
	{
		SchemeDataGroup tmpDataGroup = allDataMap.get(dataname);
		if (tmpDataGroup != null)
		{
			SimpleSchemeData tmpData = tmpDataGroup.findData(primarykey);
			if (tmpData == null)
			{
				tmpData = tmpDataGroup.findData(primarykey);
			}
			return tmpData;
		}
		return null;

	}

	public List<SimpleDataBrief> getGroupDataBrief(String dataname)
	{
		SchemeDataGroup tmpDataGroup = allDataMap.get(dataname);
		if (tmpDataGroup != null)
			return tmpDataGroup.getAllDataBrief();
		else
			return null;
	}

	public SimpleSchemeData loadData(String dataname, String primarykey, String version)
	{
		if (dataname == null || dataname.length() <= 0 || version == null || primarykey == null
				|| primarykey.length() <= 0)
			return null;

		SchemeDataGroup tmpDataGroup = allDataMap.get(dataname);
		if (tmpDataGroup == null)
		{
			synchronized (allDataMap)
			{
				SimpleScheme tmpScheme = SchemeLoader.getScheme(dataname);
				if (tmpScheme != null)
				{
					tmpDataGroup = new SchemeDataGroup(tmpScheme);
					allDataMap.put(dataname, tmpDataGroup);
					allDataList.add(tmpDataGroup);
				}
			}
		}
		if (tmpDataGroup != null)
		{
			return tmpDataGroup.findData(primarykey);
		}
		return null;
	}

	public byte[] assembleData(SimpleSchemeData data) throws IOException
	{
		if (data == null)
			return null;

		ByteArrayOutputStream tmpOutStream = new ByteArrayOutputStream();
		data.encode(tmpOutStream);
		return tmpOutStream.toByteArray();
	}

	public byte[] assembleData(String dataname, String primarykey) throws IOException
	{
		SchemeDataGroup tmpDataGroup = allDataMap.get(dataname);
		if (tmpDataGroup != null)
		{
			SimpleSchemeData tmpData = tmpDataGroup.findData(primarykey);
			if (tmpData != null)
			{
				ByteArrayOutputStream tmpOutStream = new ByteArrayOutputStream();
				tmpData.encode(tmpOutStream);
				return tmpOutStream.toByteArray();
			}
		}
		return null;
	}

	// ***********************************
	public void test()
	{
		// SchemeLoader.loadAllScheme("E:/work/LunaScheme/schemes");
		// SimpleSchemeData tmpData = getData("create", "12110");
		// if (tmpData != null)
		// {
		// ICompatibleValue tmpField = tmpData.getField("AAA");
		// if (tmpField.dataType() == ICompatibleValue.DATATYPE_BYTE)
		// tmpField.asByte();
		// }
	}

}
