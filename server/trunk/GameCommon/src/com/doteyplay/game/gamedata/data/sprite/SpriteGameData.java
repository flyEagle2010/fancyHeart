package com.doteyplay.game.gamedata.data.sprite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.constants.DamageType;
import com.doteyplay.game.constants.sprite.SpritePositionType;
import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.constants.sprite.SpriteQualityType;
import com.doteyplay.game.constants.sprite.SpriteStarType;
import com.doteyplay.game.gamedata.data.IGameData;

public class SpriteGameData implements IGameData
{
	public int descId;
	public String imageId = "";
	public String cardId = "";
	
	public DamageType attackType = DamageType.PHYSICS;
	public SpritePositionType positionType = SpritePositionType.POSITION_TYPE_F;
	
	public List<SpriteQualityPropData> propDataList = new ArrayList<SpriteQualityPropData>();
	public List<SpriteStarData> starDataList = new ArrayList<SpriteStarData>();

	public SpriteGameData()
	{
		for (SpriteQualityType qType : SpriteQualityType.values())
		{
			for (SpritePropType type : SpritePropType.values())
			{
				SpriteQualityPropData data = new SpriteQualityPropData();
				data.spritePropType = type;
				data.spriteQualityType = qType;

				propDataList.add(data);
			}
		}

		for (SpriteStarType sType : SpriteStarType.values())
		{
			SpriteStarData data = new SpriteStarData();
			
			data.spriteStarType = sType;
			data.propRate = new ArrayList<Integer>()
			{
				{
					for (int i = 0; i < SpritePropType.values().length; i++)
						add(0);
				}
			};
			
			starDataList.add(data);
		}
	}

	@Override
	public void load(DataInputStream in) throws IOException
	{
		this.descId = in.readInt();
		
		this.attackType = DamageType.values()[in.readByte()];
		this.positionType = SpritePositionType.values()[in.readByte()];
		
		this.imageId = in.readUTF();
		this.cardId = in.readUTF();
		
		propDataList.clear();;

		int size = in.readShort();
		for (int i = 0; i < size; i++)
		{
			SpriteQualityPropData data = new SpriteQualityPropData();
			data.load(in);

			propDataList.add(data);
		}
		
		starDataList.clear();
		size = in.readShort();
		for (int i = 0; i < size; i++)
		{
			SpriteStarData data = new SpriteStarData();
			data.load(in);

			starDataList.add(data);
		}
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(descId);
		
		out.writeByte(attackType.ordinal());
		out.writeByte(positionType.ordinal());
		
		out.writeUTF(this.imageId);
		out.writeUTF(this.cardId);
		
		out.writeShort(propDataList.size());

		for (SpriteQualityPropData data : propDataList)
			data.save(out);
		
		out.writeShort(starDataList.size());
		
		for (SpriteStarData data : starDataList)
			data.save(out);
	}
}