package com.doteyplay.game.action.item;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.game.constants.item.OutfitInstallResult;
import com.doteyplay.game.message.item.OutfitInstallMessage;
import com.doteyplay.game.message.utils.ResponseMessageUtils;
import com.doteyplay.game.service.bo.item.IItemService;

public @ActionAnnotation(message = com.doteyplay.game.message.item.OutfitInstallMessage.class)
class OutfitInstallMessageAction implements
		ServiceMessageAction<OutfitInstallMessage, IItemService>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.doteyplay.net.message.IMessageAction#processMessage(com.doteyplay
	 * .net.message.AbstractMessage, java.lang.Object)
	 */
	public void processMessage(OutfitInstallMessage message, IItemService service)
	{
		if (service.lock())
		{
			try
			{
				OutfitInstallResult result = service.installOutfit(
						message.getPetId(), message.getEquipIdx());
				ResponseMessageUtils.sendResponseMessage(
						message.getCommandId(), result.ordinal(),
						service.getServiceId());
			} finally
			{
				service.unlock();
			}
		}
	}

	private OutfitInstallMessageAction()
	{
	}

}
