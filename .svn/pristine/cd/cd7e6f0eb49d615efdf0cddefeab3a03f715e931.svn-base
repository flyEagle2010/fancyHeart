package com.doteyplay.game.message.common;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.ItemProBuf;
import com.doteyplay.game.message.proto.ItemProBuf.PItemChangeLog;
import com.doteyplay.net.message.AbstractMessage;

/**
 */
public class CommonItemUpdateMessage extends AbstractMessage
{

	private static final long serialVersionUID = 5889386302827062340L;

	private List<PItemChangeLog.Builder> itemLogList;
	
	public CommonItemUpdateMessage(List<PItemChangeLog.Builder> itemLogList)
	{
		super(MessageCommands.COMMON_ITEM_UPDATE_MESSAGE);
		this.itemLogList = itemLogList;
	}

	@Override
	public void release()
	{

	}

	@Override
	public void decodeBody(IoBuffer in)
	{

	}

	@Override
	public void encodeBody(IoBuffer out)
	{
		ItemProBuf.PUpItem.Builder builder = ItemProBuf.PUpItem
				.newBuilder();
		
		for(PItemChangeLog.Builder itemLog:itemLogList)
			builder.addItemLogList(itemLog);

		ItemProBuf.PUpItem resp = builder.build();
		out.put(resp.toByteArray());
	}

}
