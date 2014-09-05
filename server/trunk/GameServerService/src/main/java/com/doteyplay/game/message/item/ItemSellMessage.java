package com.doteyplay.game.message.item;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.ItemProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class ItemSellMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(ItemSellMessage.class);

	private List<Integer> itemIdList;
	private List<Integer> itemNumList;
	
	public ItemSellMessage()
	{
		super(MessageCommands.ITEM_SELL_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in)
	{
		byte[] bytes = getProtoBufBytes(in);
		try
		{
			ItemProBuf.PSellGroup req = ItemProBuf.PSellGroup
					.parseFrom(bytes);
			itemIdList = req.getItemIdList();
			itemNumList = req.getItemSellNumList();
		} catch (InvalidProtocolBufferException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void encodeBody(IoBuffer out)
	{
	}

	public void release()
	{
	}

	public List<Integer> getItemIdList()
	{
		return itemIdList;
	}

	public void setItemIdList(List<Integer> itemIdList)
	{
		this.itemIdList = itemIdList;
	}

	public List<Integer> getItemNumList()
	{
		return itemNumList;
	}

	public void setItemNumList(List<Integer> itemNumList)
	{
		this.itemNumList = itemNumList;
	}
}
