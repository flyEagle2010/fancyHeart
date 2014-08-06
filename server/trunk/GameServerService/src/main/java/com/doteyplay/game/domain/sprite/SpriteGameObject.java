package com.doteyplay.game.domain.sprite;

import com.doteyplay.game.domain.common.AbstractGameObject;
import com.doteyplay.game.gamedata.data.sprite.SpriteGameData;

public class SpriteGameObject extends AbstractGameObject<SpriteGameData>
{
	public SpriteGameObject()
	{
		super(new SpriteGameData());
	}
}
