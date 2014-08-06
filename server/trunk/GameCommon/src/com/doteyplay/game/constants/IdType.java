package com.doteyplay.game.constants;

public enum IdType
{
	ROLE_ID("t_role","areaId","id"),
	PET_ID("t_pet","areaId","id"),
	;
	private final String tableName;
	private final String groupName;
	private final String keyName;
	
	private IdType(String tableName,String groupName,String keyName)
	{
		this.tableName = tableName;
		this.groupName = groupName;
		this.keyName = keyName;
	}

	public String getTableName()
	{
		return tableName;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public String getKeyName()
	{
		return keyName;
	}
}