package com.doteyplay.game.domain.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.domain.gamedatabean.GameDataBean;
import com.doteyplay.game.domain.sprite.SpriteGameObject;
import com.doteyplay.game.editor.ResourceType;
import com.doteyplay.game.persistence.gamedata.IGameDataBeanDao;

public class GameObjectManager
{
	private Map<Integer, GameDataBean> dataMap = new HashMap<Integer, GameDataBean>();

	private Map<Class<? extends AbstractGameObject>, Map<Integer, AbstractGameObject>> objectMap = new HashMap<Class<? extends AbstractGameObject>, Map<Integer, AbstractGameObject>>();

	private Map<ResourceType, Class<? extends AbstractGameObject>> resourceMappingClz = new HashMap<ResourceType, Class<? extends AbstractGameObject>>();

	private GameObjectManager()
	{
		registMappingClz();
		init();
	}

	private void init()
	{
		List<GameDataBean> list = DBCS.getExector(IGameDataBeanDao.class)
				.selectGameDataBeanAll();
		
		for (GameDataBean bean : list)
		{
			Class clz = resourceMappingClz.get(ResourceType.values()[bean
					.getResourceType()]);

			AbstractGameObject object = null;
			try
			{
				object = (AbstractGameObject) clz.newInstance();
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			if (object != null)
			{
				object.init(bean);

				Map<Integer, AbstractGameObject> tmpMap = objectMap.get(clz);

				if (tmpMap == null)
				{
					tmpMap = new HashMap<Integer, AbstractGameObject>();
					objectMap.put(clz, tmpMap);
				}

				tmpMap.put(bean.getResourceId(), object);
				dataMap.put(bean.getPKId(), bean);
			}
		}
	}

	private void registMappingClz()
	{
		resourceMappingClz.put(ResourceType.RESTYPE_SPRITE,
				SpriteGameObject.class);
		resourceMappingClz.put(ResourceType.RESTYPE_TEXT,
				TextGameObject.class);
	}

	public <T extends AbstractGameObject> Map<Integer, T> getAll(
			Class<? extends T> clz)
	{
		return (Map<Integer, T>) objectMap.get(clz);
	}

	public <T extends AbstractGameObject> T get(int id,
			Class<? extends T> clz)
	{
		return (T) objectMap.get(clz).get(id);
	}
	
	// //////////////////////////////////////////////////////////////
	private final static GameObjectManager instance = new GameObjectManager();

	public final static GameObjectManager getInstance()
	{
		return instance;
	}
}
