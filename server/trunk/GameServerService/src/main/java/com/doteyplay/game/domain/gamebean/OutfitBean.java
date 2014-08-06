package com.doteyplay.game.domain.gamebean;

public class OutfitBean extends BaseBean
{
	private long roleId;
	private long petId;
	private byte[] outfitData;

	public long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(long roleId)
	{
		this.roleId = roleId;
	}

	public long getPetId()
	{
		return petId;
	}

	public void setPetId(long petId)
	{
		this.petId = petId;
	}

	public byte[] getOutfitData()
	{
		return outfitData;
	}

	public void setOutfitData(byte[] outfitData)
	{
		this.outfitData = outfitData;
	}
}
