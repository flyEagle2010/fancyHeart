package com.doteyplay.game.domain.common;

import java.util.HashMap;
import java.util.Map;

public class TextObjectManager
{
	private Map<Integer, TextGameObject> map = new HashMap<Integer, TextGameObject>();

	private TextObjectManager()
	{
		map = GameObjectManager.getInstance().getAll(TextGameObject.class);
	}

	public String get_text(int id)
	{
		TextGameObject obj = map.get(id);
		if (obj == null)
			return null;
		return obj.getGameData().name;
	}

	// ///////////////////////////////////////////////////////////////
	private final static TextObjectManager instance = new TextObjectManager();

	public final static String getText(int id)
	{
		return instance.get_text(id);
	}
}
