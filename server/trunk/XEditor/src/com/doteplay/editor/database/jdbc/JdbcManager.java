package com.doteplay.editor.database.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.doteplay.editor.database.BaseConnectionPool;

public class JdbcManager
{
	private static final JdbcManager jdbc = new JdbcManager();
	private Connection conn;

	private JdbcManager()
	{
	}

	public static JdbcManager getInstance()
	{
		return jdbc;
	}

	public Connection getConnection() throws SQLException
	{
		if (conn == null || conn.isClosed())
		{
			conn = BaseConnectionPool.getConnection();
		}
		return conn;
	}

	public void close(ResultSet rs, Statement stmt, Connection conn)
			throws SQLException
	{
		if (rs != null)
		{
			rs.close();
		}
		if (stmt != null)
		{
			stmt.close();
		}
		if (conn != null)
		{
			conn.close();
		}
	}

	public void close(ResultSet rs, PreparedStatement pstmt, Connection conn)
			throws SQLException
	{
		if (rs != null)
		{
			rs.close();
		}
		if (pstmt != null)
		{
			pstmt.close();
		}
		if (conn != null)
		{
			conn.close();
		}
	}

	public void close(ResultSet rs) throws SQLException
	{
		close(rs, null, null);
	}

	public void close(Statement stmt) throws SQLException
	{
		close(null, stmt, null);
	}

	public void close(Connection conn) throws SQLException
	{
		close(null, null, conn);
	}
}
