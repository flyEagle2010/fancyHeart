package com.doteplay.editor.common;

import java.sql.Timestamp;

import com.doteplay.editor.EditorConfig;

public abstract class AbstractVersionData extends AbstractData
{

	public int lastEditUserId;// 最后提交用户ID
	public String lastEditUserName = "";// 最后提交用户名
	public String lastEditorVersion = "";// 编辑器版本
	public Timestamp lastEditorDateTime;// 编辑器时间
	public int version = 0;// 本地版本
	public int db_version = 0;// 数据库版本

	/**
	 * 版本数据是否可编辑 用于限制编辑和保存 true : 各个非缺省版本可以独自编辑和保存数据. false :
	 * 只能在缺省版本下编辑和保存数据,其他版本没有数据或系统生成.
	 */
	public boolean versionDataEditable;
	// /**
	// * 版本数据生成标志
	// * 0:缺省生成 1:自定义
	// */
	/**
	 * 版本数值转换系数
	 */
	protected static float[][] versionValueModulus = new float[1][2];

	protected void initVersionValueModulus()
	{
		versionValueModulus[1][0] = 1f;
		versionValueModulus[1][1] = 1f;
	}

	public boolean isVersionDataValid(int versionType)
	{
		return true;
	}

	/**
	 * 获得指定版本的转换数值
	 * 
	 * @param vtype
	 * @param value
	 * @param axisType
	 * @return
	 */
	public static int getVersionValue(int vtype, int value, int axisType)
	{
		return getVersionValue(EditorConfig.VERSION_DEFAULT_TYPE, vtype, value,
				axisType);
	}

	/**
	 * 从指定版本变化为其他版本
	 * 
	 * @param fromVersionType
	 * @param toVersionType
	 * @param value
	 * @param axisType
	 * @return
	 */
	public static int getVersionValue(int fromVersionType, int toVersionType,
			int value, int axisType)
	{
		if (fromVersionType == toVersionType)
			return value;

		float f = versionValueModulus[toVersionType][axisType];
		float f_default = versionValueModulus[fromVersionType][axisType];
		if (f == f_default)
			return value;
		if (value < 0)
			f = value * f / f_default - 0.5f;
		else
			f = value * f / f_default + 0.5f;
		// f=value*f;
		return (int) f;
	}

	/**
	 * 获得当前版本的转换数值
	 * 
	 * @param value
	 * @param axisType
	 * @return
	 */
	public static int getVersionValue(int value, int axisType)
	{
		return getVersionValue(EditorConfig.VERSION_TYPE, value, axisType);
	}

	public int getDefaultVersionValue(int value, int axisType)
	{
		return getDefaultVersionValue(EditorConfig.VERSION_TYPE, value,
				axisType);
	}

	public int getDefaultVersionValue(int vtype, int value, int axisType)
	{
		return getVersionValue(vtype, EditorConfig.VERSION_DEFAULT_TYPE, value,
				axisType);
	}

	public boolean isDeferrentVersion()
	{
		if (isInDB == 1)
		{
			if (version != db_version)
				return true;
		}
		return false;
	}

	public int getLastEditUserId()
	{
		return lastEditUserId;
	}

	public String getLastEditUserName()
	{
		return lastEditUserName;
	}

	public String getLastEditorVersion()
	{
		return lastEditorVersion;
	}

	public Timestamp getLastEditorDateTime()
	{
		return lastEditorDateTime;
	}

	public int getVersion()
	{
		return version;
	}

	public int getDb_version()
	{
		return db_version;
	}

}
