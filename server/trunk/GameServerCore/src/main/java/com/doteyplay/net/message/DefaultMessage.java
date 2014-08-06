package com.doteyplay.net.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.core.util.ByteBufUitl;
import com.doteyplay.core.util.EncodeBufCallBack;
import com.doteyplay.game.MessageCommands;

public class DefaultMessage extends AbstractMessage 
{
	private static final long serialVersionUID = -2318543364969328542L;
	
	private byte[] data;
	private boolean needEncodeHead;
	
	public DefaultMessage()
	{
		super(MessageCommands.SERVER_TRANSFER_MESSAGE);
	}
	
	public DefaultMessage(MessageCommands command,byte[] data)
	{
		super(command);
		this.data = data;
		if(command != MessageCommands.SERVER_TRANSFER_MESSAGE)
			needEncodeHead = true;
	}

	@Override
	public void decodeBody(IoBuffer arg0)
	{
	}

	@Override
	public void encodeBody(IoBuffer out)
	{
		out.put(data);
	}

	public IoBuffer encodeIoBuffer()
	{
		if(needEncodeHead)
			return super.encodeIoBuffer();
		
		return ByteBufUitl.getOutPutBuf(new EncodeBufCallBack()
		{
			@Override
			public void encode(IoBuffer out)
			{
				out.put(data);
			}
		});
	}
	
	@Override
	public void release()
	{
		data = null;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data)
	{
		this.data = data;
	}
}