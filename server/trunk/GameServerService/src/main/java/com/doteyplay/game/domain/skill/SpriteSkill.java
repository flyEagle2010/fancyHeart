package com.doteyplay.game.domain.skill;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.config.template.SkillDataObject;
import com.doteyplay.game.domain.gamebean.SkillBean;
import com.doteyplay.game.domain.sprite.AbstractSprite;
import com.doteyplay.game.persistence.serverdata.skill.ISkillBeanDao;
import com.doteyplay.game.util.excel.TemplateService;

public class SpriteSkill
{
	private AbstractSprite owner;
	private SkillBean bean;
	private int skillGroupId;
	private int skillLevel;
	private SkillDataObject skillData;
	
	public SpriteSkill(AbstractSprite owner,SkillBean bean)
	{
		this.owner = owner;
		this.bean = bean;
		if(bean != null)
		{
			this.skillGroupId = bean.getSkillId() / 100;
			this.skillLevel = bean.getSkillLevel() % 100;
			skillData = TemplateService.getInstance().get(bean.getSkillId(), SkillDataObject.class);
		}
	}
	
	public boolean levelUp()
	{
		int oldSkillId = getBean().getSkillId();
		int newSkillId = getBean().getSkillId() + 1;
		SkillDataObject skillData = TemplateService.getInstance().get(newSkillId, SkillDataObject.class);
		if(skillData == null)
			return false;
		
		bean.setSkillId(newSkillId);
		skillLevel = skillLevel + 1;
		skillData = TemplateService.getInstance().get(bean.getSkillId(), SkillDataObject.class);
		
		ISkillBeanDao dao = DBCS.getExector(ISkillBeanDao.class);
		dao.updateSkillBean(owner.getTopOwner().getRoleId(), owner.getId(), oldSkillId, newSkillId);
		return true;
	}
	
	public boolean isMaxLevel()
	{
		int newSkillId = getBean().getSkillId() + 1;
		SkillDataObject skillData = TemplateService.getInstance().get(newSkillId, SkillDataObject.class);
		if(skillData == null)
			return true;
		return false;
	}
	
	public AbstractSprite getOwner()
	{
		return owner;
	}

	public void setOwner(AbstractSprite owner)
	{
		this.owner = owner;
	}

	public SkillBean getBean()
	{
		return bean;
	}

	public void setBean(SkillBean bean)
	{
		this.bean = bean;
	}

	public int getSkillGroupId()
	{
		return skillGroupId;
	}

	public void setSkillGroupId(int skillGroupId)
	{
		this.skillGroupId = skillGroupId;
	}

	public int getSkillLevel()
	{
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel)
	{
		this.skillLevel = skillLevel;
	}

	public SkillDataObject getSkillData()
	{
		return skillData;
	}
}
