package com.doteplay.editor.log;

public class LogData {

	/*
	 * editorUserID,table,tableName,action,recordID,recordName,time
	 */

	public int userID;
	public String table;
	public String tableName;
	public String action;
	public int recordID;
	public String recordName;
	public String dataId;

	public LogData(int user, String table, String tableName, String action, int recordID, String recordName, String dataId) {
		this.userID = user;
		this.table = table;
		this.tableName = tableName;
		this.action = action;
		this.recordID = recordID;
		this.recordName = recordName;
		this.dataId=dataId;
	}

	public String toString() {
		return userID + "-" + table + ":" + tableName + " " + action + " Id=" + recordID + "," + recordName;
	}

}
