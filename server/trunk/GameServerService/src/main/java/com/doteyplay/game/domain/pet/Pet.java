package com.doteyplay.game.domain.pet;

import java.util.List;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.constants.common.CommonPropUpdateType;
import com.doteyplay.game.constants.common.RewardType;
import com.doteyplay.game.domain.gamebean.PetBean;
import com.doteyplay.game.domain.gamebean.SkillBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.domain.sprite.AbstractSprite;
import com.doteyplay.game.message.common.CommonPropUpdateMessage;
import com.doteyplay.game.persistence.serverdata.pet.IPetBeanDao;

public class Pet extends AbstractSprite<PetBean>
{
	private List<SkillBean> skillBeanList;

	public Pet(PetBean bean, Role owner, List<SkillBean> skillBeanList)
	{
		super(CommonConstants.SPRITE_TYPE_PET, bean, owner);
		this.skillBeanList = skillBeanList;
		this.topOwner = owner;
	}

	public void init()
	{
		this.propertyManager = new PetPropertyManager(this);
		this.skillManager = new PetSkillManager(this, skillBeanList);
		super.init();
	}

	public synchronized boolean addExp(int exp)
	{
		int oldLevel = bean.getLevel();
		if (oldLevel >= CommonConstants.ROLE_MAX_LEVEL)
			return false;

		if (oldLevel >= topOwner.getRoleBean().getLevel())
			return false;

		bean.setExp(this.getSpriteBean().getExp() + exp);

		long curUpgradeExp = CommonConstants.NPC_LEVEL_EXP[bean.getLevel()];

		int changeLevel = 0;
		while (curUpgradeExp < this.bean.getExp())
		{
			changeLevel++;
			this.bean.setLevel(this.bean.getLevel() + 1);
			curUpgradeExp = CommonConstants.ROLE_LEVEL_EXP[bean.getLevel()];
		}

		CommonPropUpdateMessage message = new CommonPropUpdateMessage(
				this.getId(),
				CommonPropUpdateType.EXP,
				exp,
				(int) (this.getBean().getExp() - CommonConstants.ROLE_LEVEL_EXP[getBean()
						.getLevel()]), RewardType.DEFAULT);
		topOwner.sendMsg(message);

		if (changeLevel > 0)
		{
			IPetBeanDao dao = DBCS.getExector(IPetBeanDao.class);
			dao.updatePetBean(bean);

			CommonPropUpdateMessage levelMessage = new CommonPropUpdateMessage(
					this.getId(), CommonPropUpdateType.LEVEL, changeLevel,
					bean.getLevel(), RewardType.DEFAULT);
			topOwner.sendMsg(levelMessage);
		}
		return true;
	}

	public String getName()
	{
		if (this.getSpriteDataObject().getIsHero() == CommonConstants.TRUE)
			return this.topOwner.getRoleBean().getName();
		return super.getName();
	}
	
	public void onLeaveGame()
	{
		IPetBeanDao dao = DBCS.getExector(IPetBeanDao.class);
		dao.updatePetBean(this.bean);
	}
}
