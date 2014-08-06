package com.doteyplay.game.service.bo.role;

import java.util.Random;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.config.template.SpriteDataObject;
import com.doteyplay.game.constants.common.CommonPropUpdateType;
import com.doteyplay.game.constants.common.RewardType;
import com.doteyplay.game.constants.item.ItemConstants;
import com.doteyplay.game.constants.item.OutfitUpgradeResult;
import com.doteyplay.game.constants.sprite.SpriteQualityType;
import com.doteyplay.game.constants.sprite.SpriteStarType;
import com.doteyplay.game.domain.item.RoleItem;
import com.doteyplay.game.domain.pet.Pet;
import com.doteyplay.game.domain.pet.PetSkillManager;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.domain.sprite.AbstractSprite;
import com.doteyplay.game.gamedata.data.sprite.SpriteStarData;
import com.doteyplay.game.message.common.CommonPropUpdateMessage;
import com.doteyplay.game.persistence.serverdata.pet.IPetBeanDao;
import com.doteyplay.game.service.bo.item.IItemService;
import com.doteyplay.game.service.runtime.GlobalRoleCache;
import com.doteyplay.game.util.excel.TemplateService;

public class RoleService extends AbstractSimpleService<IRoleService> implements
		IRoleService
{
	private static final Logger logger = Logger.getLogger(RoleService.class);

	private Role role;
	private static Random random = new Random();

	@Override
	public int getPortalId()
	{
		return IRoleService.PORTAL_ID;
	}

	@Override
	public void initialize()
	{
		this.role = GlobalRoleCache.getInstance().getRoleById(getServiceId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.doteyplay.game.service.bo.role.IRoleService#summonPet(int)
	 */
	@Override
	public void summonPet(int petSpriteId)
	{
		if (role.getPetManager().getPetBySriteId(petSpriteId) != null)
			return;

		SpriteDataObject petData = TemplateService.getInstance().get(
				petSpriteId, SpriteDataObject.class);
		if (petData == null || petData.getCanSummon() == CommonConstants.FALSE)
			return;

		IItemService itemService = BOServiceManager.findService(
				IItemService.PORTAL_ID, role.getRoleId());
		if (itemService.lock())
		{
			try
			{
				RoleItem item = itemService.addOrRemoveItem(
						petData.getStoneId(), -petData.getCostStoneNum(), true);
				if (item == null)
					return;
			} finally
			{
				itemService.unlock();
			}
		}

		Pet pet = role.getPetManager().addPet(petSpriteId);
		if (pet == null)
			return;
	}

	public void qualityUpgrade(long petId)
	{
		Pet pet = role.findPet(petId);
		if (pet == null)
			return;

		int oldQuality = pet.getSpriteBean().getQuality();
		if (oldQuality >= SpriteQualityType.QUALITY_8.ordinal())
			return;

		OutfitUpgradeResult result = null;
		IItemService itemService = BOServiceManager.findService(
				IItemService.PORTAL_ID, role.getRoleId());
		if (itemService.lock())
		{
			try
			{
				result = itemService.upgradeQualityRemoveOutfit(petId);
			} finally
			{
				itemService.unlock();
			}
		}

		if (result == OutfitUpgradeResult.SUCCESS)
		{
			pet.getSpriteBean().setQuality(oldQuality + 1);
			IPetBeanDao dao = DBCS.getExector(IPetBeanDao.class);
			dao.updatePetBean(pet.getBean());

			if (pet.getSpriteBean().getQuality() == SpriteQualityType.QUALITY_1
					.ordinal())
			{
				pet.getSkillManager().addSKill(
						pet.getSpriteDataObject().getSkill2());
			} else if (pet.getSpriteBean().getQuality() == SpriteQualityType.QUALITY_3
					.ordinal())
			{
				pet.getSkillManager().addSKill(
						pet.getSpriteDataObject().getSkill3());
			} else if (pet.getSpriteBean().getQuality() == SpriteQualityType.QUALITY_6
					.ordinal())
			{
				pet.getSkillManager().addSKill(
						pet.getSpriteDataObject().getSkill4());
			}

			CommonPropUpdateMessage message = new CommonPropUpdateMessage(
					petId, CommonPropUpdateType.QUALITY, 1, pet.getSpriteBean()
							.getQuality(), RewardType.DEFAULT);
			sendMessage(message);
		}
	}

	public void starUpgrade(long petId)
	{
		Pet pet = role.findPet(petId);
		if (pet == null)
			return;
		int oldStar = pet.getSpriteBean().getStar();
		if (oldStar >= SpriteStarType.values().length)
			return;

		SpriteStarData starData = pet.getSpriteDataObject().getStarDataList()
				.get(oldStar - 1);
		if (starData == null)
			return;

		if (role.getRoleBean().getMoney() < starData.upgradeMoney)
			return;

		IItemService itemService = BOServiceManager.findService(
				IItemService.PORTAL_ID, role.getRoleId());
		if (itemService.lock())
		{
			try
			{
				RoleItem item = itemService.addOrRemoveItem(pet
						.getSpriteDataObject().getStoneId(),
						-starData.stoneNum, true);
				if (item == null)
					return;
			} finally
			{
				itemService.unlock();
			}
		}

		role.addMoney(-starData.upgradeMoney, RewardType.UP_STAR, true);
		pet.getBean().setStar(oldStar + 1);
		IPetBeanDao dao = DBCS.getExector(IPetBeanDao.class);
		dao.updatePetBean(pet.getBean());

		CommonPropUpdateMessage message = new CommonPropUpdateMessage(petId,
				CommonPropUpdateType.STAR, 1, pet.getSpriteBean().getStar(),
				RewardType.DEFAULT);
		sendMessage(message);
	}

	public void highQualityUpgrade(long petId)
	{
		Pet pet = role.findPet(petId);
		if (pet == null)
			return;

		int oldQuality = pet.getSpriteBean().getQuality();
		if (oldQuality < SpriteQualityType.QUALITY_8.ordinal()
				|| pet.getBean().getStar() != SpriteStarType.STAR_5.ordinal())
			return;
		
		if(pet.getSkillManager().isAllPassiveSkillLevelMAX())
			return;

		if (role.getRoleBean().getMoney() < pet.getSpriteDataObject()
				.getHighQualityCostMoney())
			return;

		IItemService itemService = BOServiceManager.findService(
				IItemService.PORTAL_ID, role.getRoleId());
		if (itemService.lock())
		{
			try
			{
				RoleItem item = itemService.addOrRemoveItem(pet
						.getSpriteDataObject().getStoneId(), -pet
						.getSpriteDataObject().getHighQualityCostMoney(), true);
				if (item == null)
					return;
			} finally
			{
				itemService.unlock();
			}
		}

		//没有达到金色品质
		if (pet.getSpriteBean().getQuality() < SpriteQualityType.QUALITY_9
				.ordinal())
		{
			if (random.nextInt(10000) <= pet.getSpriteDataObject()
					.getGoldQualityPercent())
			{
				qualityUp2Gold(pet);
			} else
			{
				boolean levelUp = ((PetSkillManager) pet.getSkillManager())
						.randomPassiveSkillLevelUp() != null;
				if (!levelUp)
					qualityUp2Gold(pet);
			}
		} else
		{
			//已经是金色品质
			int totalPercent = pet.getSpriteDataObject().getSkill6Percent()
					+ pet.getSpriteDataObject().getSkill7Percent();

			//随机获得技能
			if (random.nextInt(10000) < totalPercent)
			{
				boolean hasAddSkill = false;
				if (!pet.getSkillManager().hasSkill(
						pet.getSpriteDataObject().getSkill6())
						|| !pet.getSkillManager().hasSkill(
								pet.getSpriteDataObject().getSkill7()))
				{

					if (!pet.getSkillManager().hasSkill(
							pet.getSpriteDataObject().getSkill6()))
					{
						pet.getSkillManager().addSKill(
								pet.getSpriteDataObject().getSkill6());
						hasAddSkill = true;
					} else if (!pet.getSkillManager().hasSkill(
							pet.getSpriteDataObject().getSkill7()))
					{
						pet.getSkillManager().addSKill(
								pet.getSpriteDataObject().getSkill7());
						hasAddSkill = true;
					}

				} 
				//获得技能失败则随机提升技能等级
				if (!hasAddSkill)
					((PetSkillManager) pet.getSkillManager())
							.randomPassiveSkillLevelUp();
			} else
			{
				//尝试提升技能
				boolean levelUp = ((PetSkillManager) pet.getSkillManager())
						.randomPassiveSkillLevelUp() != null;
				if (!levelUp)
				{
					//技能提升失败则获得技能
					if (!pet.getSkillManager().hasSkill(
							pet.getSpriteDataObject().getSkill6()))
					{
						pet.getSkillManager().addSKill(
								pet.getSpriteDataObject().getSkill6());
					} else if (!pet.getSkillManager().hasSkill(
							pet.getSpriteDataObject().getSkill7()))
					{
						pet.getSkillManager().addSKill(
								pet.getSpriteDataObject().getSkill7());
					}
				}
			}
		}
	}
	

	private void qualityUp2Gold(Pet pet)
	{
		pet.getSpriteBean().setQuality(SpriteQualityType.QUALITY_9.ordinal());
		IPetBeanDao dao = DBCS.getExector(IPetBeanDao.class);
		dao.updatePetBean(pet.getBean());

		pet.getSkillManager().addSKill(pet.getSpriteDataObject().getSkill5());

		CommonPropUpdateMessage message = new CommonPropUpdateMessage(
				pet.getId(), CommonPropUpdateType.QUALITY, 1, pet
						.getSpriteBean().getQuality(), RewardType.DEFAULT);
		sendMessage(message);
	}
}
