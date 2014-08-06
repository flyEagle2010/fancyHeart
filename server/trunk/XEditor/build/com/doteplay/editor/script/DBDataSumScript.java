package com.doteplay.editor.script;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.script.common.IScript;

public class DBDataSumScript implements IScript {

	@Override
	public boolean eval() {
		
		Connection conn = BaseConnectionPool.getConnection();
		if (conn == null)
			return false;

		try {
			
			int sum = 0;
			int i = checkSum(conn, "gd_editorData") / 1024;
			sum += i;
			System.out.println("gd_editorData = " + i + "kb");

			i = checkSum(conn, "gd_serverData") / 1024;
			sum += i;
			System.out.println("gd_serverData = " + i + "kb");

			i = checkSum(conn, "gd_clientData") / 1024;
			sum += i;
			System.out.println("gd_clientData = " + i + "kb");

			i = checkSum(conn, "gd_client176Data") / 1024;
			sum += i;
			System.out.println("gd_client176Data = " + i + "kb");

			i = checkSum(conn, "gd_client128Data") / 1024;
			sum += i;
			System.out.println("gd_client128Data = " + i + "kb");
			
			System.out.println("sum = " + sum + "kb");
			
		} catch (RuntimeException e) {
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

		return true;
	}

	public int checkSum(Connection conn, String blobFieldName) {

		int size = 0;
		try {
			byte[] db_data = null;
			String sql;
			PreparedStatement pstat;
			ResultSet rs;
			sql = "select " + blobFieldName + " from " + EditorConfig.RES_DATA_TABLE_EDITOR;
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			while (rs.next()) {
				db_data = rs.getBytes(blobFieldName);
				if(db_data != null){
					size += db_data.length;
				}
			}
			rs.close();
			pstat.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
	@Override
	public String getName() {
		return "计算数据总大小";
	}

}
