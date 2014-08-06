package com.doteyplay.game.domain.gamebean;

public class PetBean extends SpriteBean
{
	private long roleId;
	/**
	 * 是否是主英雄
	 */
	private int selected;
	/**
	 * 英雄从属关系.
	 * 0,1,2,3,4表示从属于五组中的几组.
	 */
	private String groupStr="";
	
	
	public long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public int getSelected()
	{
		return selected;
	}
	
	public boolean isSelected(){
		return selected==1;
	}

	public void setSelected(int selected)
	{
		this.selected = selected;
	}

	public String getGroupStr() {
		return groupStr;
	}

	public void setGroupStr(String groupStr) {
		this.groupStr = groupStr;
	}

	

	
}
