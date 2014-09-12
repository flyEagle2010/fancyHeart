package com.doteyplay.game.action.pet;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.constants.item.ItemCreateResult;
import com.doteyplay.game.constants.pet.PetStarUpgradeResult;
import com.doteyplay.game.message.pet.PetCallMessage;
import com.doteyplay.game.message.pet.PetStarUpgradeMessage;
import com.doteyplay.game.message.tollgate.GroupUpdateMessage;
import com.doteyplay.game.message.utils.ResponseMessageUtils;
import com.doteyplay.game.service.bo.role.IRoleService;
import com.doteyplay.game.service.bo.tollgate.ITollgateInfoService;

@ActionAnnotation(message = com.doteyplay.game.message.pet.PetStarUpgradeMessage.class)
public class PetStarUpgradeMessageAction implements
		ServiceMessageAction<PetStarUpgradeMessage, IRoleService>
{

	@Override
	public void processMessage(PetStarUpgradeMessage message,
			IRoleService service) throws MessageProcessException
	{
		// TODO Auto-generated method stub
		PetStarUpgradeResult result = service.starUpgrade(message.getPetId());
		ResponseMessageUtils.sendResponseMessage(message.getCommandId(),
				result.ordinal(), service.getServiceId());
	}

}
