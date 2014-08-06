package com.doteplay.editor.common;

public class AbstractData
{
	public int PKId;
	/**
	 * 数据唯一ID
	 */
	public String id;
	/**
	 * 名称
	 */
	public int nameId;
	/**
	 * 描述
	 */
	public String description = "";

	/**
	 * 数据类型
	 */
	public int dataType = 0;
	/**
	 * 是否在数据库中
	 */
	public int isInDB = 0;
	/**
	 * 数据对象对应的数据库表名称
	 */
	protected String dataTable = null;

}
