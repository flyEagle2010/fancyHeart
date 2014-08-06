package com.doteyplay.game.gamedata.data.sprite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.constants.sprite.SpriteStarType;
import com.doteyplay.game.gamedata.data.IGameData;

public class SpriteStarData implements IGameData
{
	public SpriteStarType spriteStarType = SpriteStarType.STAR_1;
	public List<Integer> propRate = new ArrayList<Integer>();
	public int stoneNum;
	public int upgradeMoney;

	public void load(DataInputStream in) throws IOException
	{
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
	}
}
