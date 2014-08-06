package com.doteplay.editor.script;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.script.common.IScript;

@Deprecated
public class SceneCleanScript implements IScript {

	@Override
	public boolean eval() {
		
//		File f = new File(EditorConfig.confPath + "Book1.xls");
//		ExcelDataImporter excelDataImporter = new ExcelDataImporter(f);
//
//		String[][] changeTable = excelDataImporter.readSheet("Sheet1", 4, 254);
//
//		Connection conn = BaseConnectionPool.getConnection();
//		if (conn == null)
//			return false;
//
//		try {
//			for (int row = 1; row < changeTable.length; row++) {
//
//				String pkid = changeTable[row][0];
//				String oldId = changeTable[row][1];
//				String name = changeTable[row][2];
//				String newId = changeTable[row][3];
//
//				if (newId.equals("0")) {
//					try {
//						boolean isDone = db_delete(conn, pkid);
//						System.out.println(oldId + "_" + name + " delete " + isDone);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			System.out.println("delete ok");
//			
//			for (int row = 1; row < changeTable.length; row++) {
//
//				String pkid = changeTable[row][0];
//				String oldId = changeTable[row][1];
//				String name = changeTable[row][2];
//				String newId = changeTable[row][3];
//
//				if (newId.equals("0")) {
//					continue;
//				}
//
//				try {
//
//					boolean isDone = db_changeId(conn, pkid, newId);
//					System.out.println(oldId + "_" + name + " changeId " + isDone);
//				} catch (Exception e) {
//					System.out.println(oldId + "_" + name +"_"+pkid + " changeId false " );
//					e.printStackTrace();
//					return false;
//				}
//				
//			}
//			
//			System.out.println("change ok");
//		} catch (RuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally
//		{
//			try {
//				conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		return true;
	}

	public final boolean db_delete(Connection conn, String pkid) throws Exception {

		String sql;
		PreparedStatement pstat;
		boolean ok = false;
		int result = 0;

		sql = "delete from " + EditorConfig.RES_DATA_TABLE_EDITOR + " where PKId=?";

		pstat = conn.prepareStatement(sql);
		pstat.setString(1, pkid);

		result = pstat.executeUpdate();
		pstat.close();

		if (result > 0) {
			ok = true;
		}
		pstat.close();
		return ok;
	}

	public final boolean db_changeId(Connection conn, String pkid, String newId) throws Exception {

		String sql;
		PreparedStatement pstat;
		boolean ok = false;
		int result = 0;

		sql = "update " + EditorConfig.RES_DATA_TABLE_EDITOR + " set resourceId=? where PKId=?";

		pstat = conn.prepareStatement(sql);
		pstat.setString(1, newId);
		pstat.setString(2, pkid);

		result = pstat.executeUpdate();
		pstat.close();

		if (result > 0) {
			ok = true;
		}
		pstat.close();
		return ok;
	}

	@Override
	public String getName() {
		return "«Â¿Ì≥°æ∞";
	}

}
