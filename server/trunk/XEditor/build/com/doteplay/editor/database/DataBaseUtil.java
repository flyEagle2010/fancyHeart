/**
 * 
 */
package com.doteplay.editor.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import com.doteplay.editor.EditorConfig;

public class DataBaseUtil
{

	public static final int getMaxDataId(int resourceType, String pkId)
	{
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return -1;
		int result = -1;
		try
		{
			String sql;
			PreparedStatement pstat;
			ResultSet rs;
			sql = "select max(" + pkId + ") from "
					+ EditorConfig.RES_DATA_TABLE_EDITOR
					+ " where resourceType=?";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, resourceType);

			rs = pstat.executeQuery();
			if (rs.next())
			{
				result = rs.getInt(1);
			}
			pstat.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	// 数据库操作函数
	public static final Object getColumnObjectByType(ResultSet rs, int i,
			int type) throws Exception
	{
		Object obj;
		if (type == java.sql.Types.TIMESTAMP)
		{
			obj = rs.getTimestamp(i);
		} else if (type == java.sql.Types.DATE)
		{
			obj = rs.getDate(i);
		} else if (type == java.sql.Types.BLOB)
		{
			obj = rs.getBlob(i);
		} else
		{
			obj = rs.getString(i);
		}
		return obj;
	}

	public static final Vector<String[]> getStringListBySQL(String sql)
	{
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return null;
		Vector<String[]> v = new Vector<String[]>();
		try
		{
			int i;
			String[] ss;
			PreparedStatement pstat = conn.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int iColCount = rsmd.getColumnCount();
			while (rs.next())
			{
				ss = new String[iColCount];
				for (i = 1; i <= iColCount; i++)
				{
					// ss[i-1]=getColumnObjectByType(rs,i,rsmd.getColumnType(i));

					ss[i - 1] = rs.getString(i);
				}
				v.addElement(ss);
			}
			rs.close();
			pstat.close();
			// return v;
		} catch (Exception e)
		{
			e.printStackTrace();
			v = null;
		} finally
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
				// v=null;
			}
		}
		return v;
	}

	public static final boolean clearDBTable(Connection conn, String tableName)
	{

		if (conn == null)
		{
			return false;
		}

		boolean ok = false;
		String sql;
		PreparedStatement pstat;
		sql = "TRUNCATE TABLE " + tableName;

		try
		{

			pstat = conn.prepareStatement(sql);
			int result = pstat.executeUpdate();
			if (result == 1)
			{
				ok = true;
			}
			pstat.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		return ok;
	}

}
