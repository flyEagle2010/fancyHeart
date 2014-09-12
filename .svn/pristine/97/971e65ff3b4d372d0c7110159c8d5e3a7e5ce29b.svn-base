package com.doteyplay.game.message.pet;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.HeroProBuf;
import com.doteyplay.game.message.proto.ItemProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class PetStarUpgradeMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(PetStarUpgradeMessage.class);

	private long petId;
	
	
	public PetStarUpgradeMessage()
	{
		super(MessageCommands.PET_STAR_UPGRADE_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in)
	{
		byte[] bytes = getProtoBufBytes(in);
		try
		{
			HeroProBuf.PEvolve req = HeroProBuf.PEvolve
					.parseFrom(bytes);
			this.petId = req.getHeroId();
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
}
