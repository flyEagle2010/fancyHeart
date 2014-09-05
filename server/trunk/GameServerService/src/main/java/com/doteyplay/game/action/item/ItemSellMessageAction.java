package com.doteyplay.game.action.item;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.game.message.item.ItemSellMessage;
import com.doteyplay.game.service.bo.item.IItemService;

public @ActionAnnotation(message = com.doteyplay.game.message.item.ItemSellMessage.class)
class ItemSellMessageAction implements
		ServiceMessageAction<ItemSellMessage, IItemService>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.doteyplay.net.message.IMessageAction#processMessage(com.doteyplay
	 * .net.message.AbstractMessage, java.lang.Object)
	 */
	public void processMessage(ItemSellMessage message, IItemService service)
	{
		if(service.lock())
		{
			try
			{
				service.sellItem(message.getItemIdList(), message.getItemNumList());
			}
			finally
			{
				service.unlock();
			}
		}
	}

	private ItemSellMessageAction()
	{
	}

}
