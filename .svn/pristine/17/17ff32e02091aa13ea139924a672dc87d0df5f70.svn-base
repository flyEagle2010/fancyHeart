/**
 * 
 */
package com.doteplay.editor.tools.update;

import java.sql.Timestamp;

import com.doteplay.editor.common.DataManager;

/**
 * @author Yang
 */
public class ResUpdateData {

	private int PKId;
	private byte resType;
	private int resId;
	private String dataId;
	private int dataNameId;
	private Timestamp version;

	/**
	 * 0:删除 1:更新
	 */
	private byte actionType = 0;

	/**
	 * 0:非常驻 1:常驻
	 */
	private byte isResidence = 0;

	public int getPKId() {
		return this.PKId;
	}

	public void setPKId(int id) {
		this.PKId = id;
	}

	public byte getResType() {
		return this.resType;
	}

	public void setResType(byte resType) {
		this.resType = resType;
	}

	public int getResId() {
		return this.resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public int getDataNameId() {
		return this.dataNameId;
	}

	public void setDataNameId(int dataNameId) {
		this.dataNameId = dataNameId;
	}

	public Timestamp getVersion() {
		return this.version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	public String toString() {
		return "\r\n" + resType + " " + resId + " " + dataId + " " + DataManager.getTextById(dataNameId) + " " + version + " " + actionType;
	}

	public byte getActionType() {
		return this.actionType;
	}

	public void setActionType(byte actionType) {
		this.actionType = actionType;
	}

	public byte getIsResidence() {
		return this.isResidence;
	}

	public void setIsResidence(byte isResidence) {
		this.isResidence = isResidence;
	}

}
