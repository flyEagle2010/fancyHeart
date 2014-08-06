package com.doteyplay.game.domain.skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.config.template.SkillDataObject;
import com.doteyplay.game.config.template.SpriteDataObject;
import com.doteyplay.game.constants.skill.SkillActionType;
import com.doteyplay.game.constants.sprite.SpritePropType;
import com.doteyplay.game.domain.gamebean.SkillBean;
import com.doteyplay.game.domain.sprite.AbstractSprite;
import com.doteyplay.game.gamedata.data.sprite.SpriteQualityPropData;
import com.doteyplay.game.persistence.serverdata.skill.ISkillBeanDao;
import com.doteyplay.game.util.excel.TemplateService;

public abstract class AbstractSpriteSkillManager
{
	// key为技能组
	protected Map<Integer, SpriteSkill> skillMap = new HashMap<Integer, SpriteSkill>();

	protected AbstractSprite owner;

	protected List<SkillBean> skillBeanList;

	protected AbstractSpriteSkillManager(AbstractSprite owner,
			List<SkillBean> skillBeanList)
	{
		this.owner = owner;
		this.skillBeanList = skillBeanList;
	}

	public void init()
	{
		for (SkillBean bean : skillBeanList)
		{
			SpriteSkill skill = new SpriteSkill(this.owner, bean);
			skillMap.put(skill.getSkillGroupId(), skill);
		}
	}

	public Map<Integer, SpriteSkill> getSkillMap()
	{
		return skillMap;
	}

	public boolean addSKill(int skillId)
	{
		if (this.hasSkill(skillId))
			return false;

		SkillBean bean = new SkillBean();
		bean.setRoleId(owner.getTopOwner().getRoleId());
		bean.setPetId(owner.getId());
		bean.setSkillId(skillId);

		SpriteSkill skill = new SpriteSkill(owner, bean);

		ISkillBeanDao dao = DBCS.getExector(ISkillBeanDao.class);
		dao.insertSkillBean(bean);
		skillMap.put(skill.getSkillGroupId(), skill);

		return true;
	}

	public boolean hasSkill(int skillId)
	{
		int skillGroup = skillId / 100;
		return skillMap.get(skillGroup) != null;
	}

	public boolean skillLevelUp(int skillId)
	{
		int skillGroupId = skillId / 100;
		SpriteSkill skill = skillMap.get(skillGroupId);
		if (skill == null)
			return false;

		return skill.levelUp();
	}

	// 是否全部被动技能都达到最大等级
	public boolean isAllPassiveSkillLevelMAX()
	{
		if (skillMap.size() == 0)
			return false;
		if (owner.getSpriteDataObject().getSkill1() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill1()))
			return false;
		if (owner.getSpriteDataObject().getSkill2() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill2()))
			return false;
		if (owner.getSpriteDataObject().getSkill3() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill3()))
			return false;
		if (owner.getSpriteDataObject().getSkill4() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill4()))
			return false;
		if (owner.getSpriteDataObject().getSkill5() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill5()))
			return false;
		if (owner.getSpriteDataObject().getSkill6() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill6()))
			return false;
		if (owner.getSpriteDataObject().getSkill7() != 0
				&& !this.hasSkill(owner.getSpriteDataObject().getSkill7()))
			return false;

		for (SpriteSkill skill : skillMap.values())
			if (skill.getSkillData().getSkillType() == SkillActionType.PASSIVE
					.ordinal() && !skill.isMaxLevel())
				return false;
		return true;
	}
}
