package com.doteyplay.game.domain.sprite;

import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.config.template.SpriteDataObject;
import com.doteyplay.game.config.template.TextDataTemplate;
import com.doteyplay.game.domain.gamebean.SpriteBean;
import com.doteyplay.game.domain.property.AbstractSpritePropertyManager;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.domain.skill.AbstractSpriteSkillManager;
import com.doteyplay.game.util.excel.TemplateService;

/**
 * ¾«Áé»ùÀà
 */
public abstract class AbstractSprite<T extends SpriteBean> extends
		AbstractSceneObject //implements ISpritePropAffect
{
	protected int spriteType;

	protected SpriteDataObject spriteDataObject;

	protected AbstractSpritePropertyManager propertyManager;
	
	protected AbstractSpriteSkillManager skillManager;

	protected Role topOwner;

	protected T bean;

	public AbstractSprite(int spriteType, T dataBean, Role owner)
	{
		super(CommonConstants.SCENE_OBJECT_TYPE_SPRITE);

		this.bean = dataBean;
		this.id = dataBean.getId();

		this.spriteType = spriteType;
		this.spriteDataObject = TemplateService.getInstance().get(
				bean.getSpriteId(), SpriteDataObject.class);

		this.name = spriteDataObject.getNameId();

		topOwner = owner;

	}
	
	public void init()
	{
		this.propertyManager.init();
		this.skillManager.init();
	}

	public int getSpriteType()
	{
		return spriteType;
	}

	public AbstractSpritePropertyManager getPropertyManager()
	{
		return propertyManager;
	}

	public void setSpriteType(int spriteType)
	{
		this.spriteType = spriteType;
	}

	public Role getTopOwner()
	{
		return topOwner;
	}

	public SpriteDataObject getSpriteDataObject()
	{
		return spriteDataObject;
	}
	
	public SpriteBean getSpriteBean()
	{
		return bean;
	}
	
	public abstract boolean addExp(int exp);

	public AbstractSpriteSkillManager getSkillManager()
	{
		return skillManager;
	}

	public T getBean()
	{
		return bean;
	}
}
