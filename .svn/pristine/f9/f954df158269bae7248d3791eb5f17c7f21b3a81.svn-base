package com.doteyplay.game.action.item;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.game.constants.item.ItemCreateResult;
import com.doteyplay.game.message.item.ItemCreateMessage;
import com.doteyplay.game.message.utils.ResponseMessageUtils;
import com.doteyplay.game.service.bo.item.IItemService;

public @ActionAnnotation(message = com.doteyplay.game.message.item.ItemCreateMessage.class)
class ItemCreateMessageAction implements
		ServiceMessageAction<ItemCreateMessage, IItemService>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.doteyplay.net.message.IMessageAction#processMessage(com.doteyplay
	 * .net.message.AbstractMessage, java.lang.Object)
	 */
	public void processMessage(ItemCreateMessage message, IItemService service)
	{
 		if (service.lock())
		{
			try
			{
				if (service.createItem(message.getItemId(),
						message.getItemNum()))
					ResponseMessageUtils.sendResponseMessage(
							message.getCommandId(),
							ItemCreateResult.SUCCESS.ordinal(),
							service.getServiceId());
				else
					ResponseMessageUtils.sendResponseMessage(
							message.getCommandId(),
							ItemCreateResult.ITEM_NOT_ENOUGH.ordinal(),
							service.getServiceId());
			} finally
			{
				service.unlock();
			}
		}
	}

	private ItemCreateMessageAction()
	{
	}

}
