package com.doteyplay.game.message.item;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.ItemProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class ItemCreateMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(ItemCreateMessage.class);

	private int itemId;
	private int itemNum;
	
	public ItemCreateMessage()
	{
		super(MessageCommands.ITEM_CREATE_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in)
	{
		byte[] bytes = getProtoBufBytes(in);
		try
		{
			ItemProBuf.PComposeItem req = ItemProBuf.PComposeItem
					.parseFrom(bytes);
			itemId = req.getItemId();
			itemNum = req.getItemNum();
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

	public int getItemId()
	{
		return itemId;
	}

	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}

	public int getItemNum()
	{
		return itemNum;
	}

	public void setItemNum(int itemNum)
	{
		this.itemNum = itemNum;
	}

}
