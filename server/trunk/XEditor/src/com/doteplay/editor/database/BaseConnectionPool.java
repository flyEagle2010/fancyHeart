package com.doteplay.editor.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.persistence.DaoConfig;
import com.doteplay.editor.util.xml.simpleXML.SimpleAttributes;

public class BaseConnectionPool
{

	private static List<ConnectionInfo> connectionInfoList = new ArrayList<ConnectionInfo>();
	private static BasicDataSource dataSource = null;

	public static void regConnectionInfo(SimpleAttributes attributes)
	{
		ConnectionInfo tmpConInfo = new ConnectionInfo(attributes);
		if (tmpConInfo != null && tmpConInfo.valid)
		{
			connectionInfoList.add(tmpConInfo);
		}
		tmpConInfo = null;
	}

	public static String[] connectionTitles()
	{
		String[] tmpTitles = new String[connectionInfoList.size()];
		ConnectionInfo tmpConInfo;
		for (int i = 0; i < connectionInfoList.size(); i++)
		{
			tmpConInfo = (ConnectionInfo) connectionInfoList.get(i);
			tmpTitles[i] = tmpConInfo.title;
			tmpConInfo = null;
		}
		return tmpTitles;
	}

	public static ConnectionInfo findConnectionInfo(String contitle)
	{
		ConnectionInfo result = null;
		if (contitle != null)
		{
			ConnectionInfo tmpConInfo;
			for (int i = 0; result == null && i < connectionInfoList.size(); i++)
			{
				tmpConInfo = (ConnectionInfo) connectionInfoList.get(i);
				if (contitle.equals(tmpConInfo.title))
					result = tmpConInfo;
			}
		}
		return result;
	}

	public static boolean init(ConnectionInfo tmpConInfo)
	{
		if (dataSource != null)
			close();
		try
		{
			if (tmpConInfo != null)
			{
				dataSource = tmpConInfo.initDataSource();
				Properties p = tmpConInfo.getDataSourceProperties();
				DaoConfig.initialize(p);
				System.out.println("DaoConfig init:" + p);
			}

			return (dataSource != null);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		dataSource = null;
		return false;
	}

	public static boolean init(String contitle)
	{
		ConnectionInfo tmpConInfo = findConnectionInfo(contitle);
		return init(tmpConInfo);
	}

	public static boolean close()
	{
		try
		{
			if (dataSource != null)
				dataSource.close();
			dataSource = null;
			return true;
		} catch (Exception e)
		{

		}
		return false;
	}

	public static Connection getConnection()
	{
		if (EditorConfig.useDataBase == false)
		{
			return null;
		}
		if (dataSource == null)
			return null;
		try
		{
			return dataSource.getConnection();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
