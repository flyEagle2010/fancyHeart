package com.doteyplay.game.message.item;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.ItemProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class OutfitInstallMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(OutfitInstallMessage.class);

	private long petId;
	private int equipIdx;
	
	public OutfitInstallMessage()
	{
		super(MessageCommands.OUTFIT_INSTALL_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in)
	{
		byte[] bytes = getProtoBufBytes(in);
		try
		{
			ItemProBuf.PWearEquip req = ItemProBuf.PWearEquip
					.parseFrom(bytes);
			petId = req.getHeroId();
			equipIdx = req.getPosId();
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

	public long getPetId()
	{
		return petId;
	}

	public void setPetId(long petId)
	{
		this.petId = petId;
	}

	public int getEquipIdx()
	{
		return equipIdx;
	}

	public void setEquipIdx(int equipIdx)
	{
		this.equipIdx = equipIdx;
	}
}
