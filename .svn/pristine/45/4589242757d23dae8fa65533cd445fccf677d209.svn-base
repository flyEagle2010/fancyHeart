package com.doteyplay.game.domain.pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.constants.skill.SkillActionType;
import com.doteyplay.game.domain.gamebean.SkillBean;
import com.doteyplay.game.domain.skill.AbstractSpriteSkillManager;
import com.doteyplay.game.domain.skill.SpriteSkill;
import com.doteyplay.game.domain.sprite.AbstractSprite;
import com.doteyplay.game.persistence.serverdata.skill.ISkillBeanDao;

public class PetSkillManager extends AbstractSpriteSkillManager
{

	private static Random random = new Random();

	protected PetSkillManager(AbstractSprite owner,
			List<SkillBean> skillBeanList)
	{
		super(owner, skillBeanList);
	}

	public void init()
	{
		if (skillBeanList == null || skillBeanList.size() == 0)
		{
			skillBeanList = new ArrayList<SkillBean>();
			SkillBean bean = new SkillBean();
			bean.setSkillId(this.owner.getSpriteDataObject().getSkill1());
			bean.setRoleId(owner.getTopOwner().getRoleId());
			bean.setPetId(owner.getId());

			ISkillBeanDao dao = DBCS.getExector(ISkillBeanDao.class);
			dao.insertSkillBean(bean);
			skillBeanList.add(bean);
		}

		super.init();
	}

	public SpriteSkill randomPassiveSkillLevelUp()
	{
		List<SpriteSkill> notFullLevelSkills = new ArrayList<SpriteSkill>();
		for (SpriteSkill skill : this.skillMap.values())
			if (skill.getSkillData().getSkillType() == SkillActionType.PASSIVE
					.ordinal() && !skill.isMaxLevel())
				notFullLevelSkills.add(skill);

		if (notFullLevelSkills.size() <= 0)
			return null;

		int idx = random.nextInt(notFullLevelSkills.size());

		SpriteSkill skill = notFullLevelSkills.get(idx);
		skill.levelUp();
		return skill;
	}

}
