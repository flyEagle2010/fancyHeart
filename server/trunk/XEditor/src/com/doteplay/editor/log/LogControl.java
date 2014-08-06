package com.doteplay.editor.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.database.BaseConnectionPool;

public class LogControl {

	private static LogControl instance;

	private LogControl() {

	}

	public static LogControl getInstance() {
		if (instance == null) {
			instance = new LogControl();
		}
		return instance;
	}

	public void write(LogData logData) {

		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return;
		String sql;
		PreparedStatement pstat;

		sql = "insert into t_editor_log (userID,tableName,tableText,action,recordID,recordName,editorVersion,dataId,dataTime) "
				+ "values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, logData.userID);
			pstat.setString(2, logData.table);
			pstat.setString(3, logData.tableName);
			pstat.setString(4, logData.action);
			pstat.setInt(5, logData.recordID);
			pstat.setString(6, logData.recordName);
			pstat.setString(7, EditorConfig.VERSION_BUILD);
			pstat.setString(8, logData.dataId);

			pstat.executeUpdate();
			pstat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void read() {

	}

} // @jve:decl-index=0:visual-constraint="10,10"
