package com.doteyplay.game.action.pet;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.pet.PetGoldQualityUpgradeMessage;
import com.doteyplay.game.service.bo.role.IRoleService;

@ActionAnnotation(message = com.doteyplay.game.message.pet.PetGoldQualityUpgradeMessage.class)
public class PetGoldQualityUpgradeMessageAction implements
		ServiceMessageAction<PetGoldQualityUpgradeMessage, IRoleService>
{

	@Override
	public void processMessage(PetGoldQualityUpgradeMessage message,
			IRoleService service) throws MessageProcessException
	{
		// TODO Auto-generated method stub
		service.highQualityUpgrade(message.getPetId());
	}

}
