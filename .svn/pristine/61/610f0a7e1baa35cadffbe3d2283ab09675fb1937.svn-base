package com.doteyplay.game.action.pet;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.constants.item.OutfitUpgradeResult;
import com.doteyplay.game.message.pet.PetQualityUpgradeMessage;
import com.doteyplay.game.message.utils.ResponseMessageUtils;
import com.doteyplay.game.service.bo.role.IRoleService;

@ActionAnnotation(message = com.doteyplay.game.message.pet.PetQualityUpgradeMessage.class)
public class PetQualityUpgradeMessageAction implements
		ServiceMessageAction<PetQualityUpgradeMessage, IRoleService>
{

	@Override
	public void processMessage(PetQualityUpgradeMessage message,
			IRoleService service) throws MessageProcessException
	{
		// TODO Auto-generated method stub
		OutfitUpgradeResult result = service.qualityUpgrade(message.getPetId());
		ResponseMessageUtils.sendResponseMessage(message.getCommandId(),
				result.ordinal(), service.getServiceId());
	}

}
