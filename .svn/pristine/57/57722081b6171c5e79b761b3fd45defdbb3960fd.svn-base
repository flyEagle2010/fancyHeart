package com.doteyplay.game.gamedata.data.sprite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.constants.sprite.SpriteQualityType;
import com.doteyplay.game.gamedata.data.IGameData;

public class SpriteQualityPropData implements IGameData
{
	public SpritePropType spritePropType = SpritePropType.HP;
	public SpriteQualityType spriteQualityType = SpriteQualityType.QUALITY_0;
	public int baseValue;
	public int rate;
	
	private List<Integer> curQualityItemList = new ArrayList<Integer>();

	@Override
	public void load(DataInputStream in) throws IOException
	{

		spritePropType = SpritePropType.values()[in.readByte()];
		spriteQualityType = SpriteQualityType.values()[in.readByte()];

		baseValue = in.readInt();
		rate = in.readInt();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(spritePropType.ordinal());
		out.writeByte(spriteQualityType.ordinal());

		out.writeInt(baseValue);
		out.writeInt(rate);
	}

	public SpritePropType getSpritePropType()
	{
		return spritePropType;
	}

	public SpriteQualityType getSpriteQualityType()
	{
		return spriteQualityType;
	}

	public int getBaseValue()
	{
		return baseValue;
	}

	public int getRate()
	{
		return rate;
	}

	public List<Integer> getCurQualityItemList()
	{
		return curQualityItemList;
	}
}
