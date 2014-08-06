package com.doteplay.editor.database.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcOperator
{
	private static JdbcManager manager = JdbcManager.getInstance();

	public static List<Object[]> executeQuerySQL(String sql, int returnCount)
			throws SQLException
	{
		Connection conn = manager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		try
		{
			List<Object[]> list = new ArrayList<Object[]>();
			Object[] records = null;
			while (rs.next())
			{
				records = new Object[returnCount];
				for (int i = 0; i < returnCount; i++)
				{
					records[i] = rs.getObject(i + 1);
				}
				list.add(records);
			}
			return list;
		} catch (SQLException e)
		{
			throw new SQLException();
		} finally
		{
			manager.close(rs, null, conn);
		}
	}

	/**
	 * 添加或修改,以statement方式
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public static void executeSQL(String sql) throws SQLException
	{
		Connection conn = manager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try
		{
			conn.setAutoCommit(false);
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e)
		{
			conn.rollback();
			throw new SQLException(
					"database manipulation language has appear a fatal error : "
							+ e.getMessage());
		} finally
		{
			manager.close(rs, stmt, conn);// 关闭所有连接
		}
	}

	/**
	 * 添加或修改,以PreparedStatement方式
	 * 
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	public static void executeSQL(String sql, Object[] params)
			throws SQLException
	{
		Connection conn = manager.getConnection();
		PreparedStatement pstmt = null;
		try
		{
			if (sql != null)
			{
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < params.length; i++)
				{
					if (params[i] != null && !"".equals(params[i]))
					{
						pstmt.setObject(i + 1, params[i]);
					} else
					{
						pstmt.setNull(i + 1, 12);
					}
				}
				pstmt.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e)
		{
			conn.rollback();
			throw new SQLException(
					"database manipulation language has appear a fatal error : "
							+ e.getMessage());
		} finally
		{
			manager.close(null, pstmt, conn);// 关闭所有连接
		}
	}

	/**
	 * 批量操作(添加或修改)数据库
	 * 
	 * @param sqls
	 * @throws SQLException
	 */
	public static void executeBatchSQL(String[] sqls) throws SQLException
	{
		Connection conn = manager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try
		{
			conn.setAutoCommit(false);
			for (int i = 0; i < sqls.length; i++)
			{
				if (sqls[i] != null)
				{
					stmt.addBatch(sqls[i]);
				}
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException e)
		{
			conn.rollback();
			throw new SQLException(
					"database manipulation language has appear a fatal error : "
							+ e.getMessage());
		} finally
		{
			manager.close(rs, stmt, conn);// 关闭所有连接
		}
	}
}
