package com.doteyplay.game.message.pet;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.constants.item.OutfitUpgradeResult;
import com.doteyplay.game.constants.item.PetGoldUpgradeResultType;
import com.doteyplay.game.message.proto.HeroProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class PetGoldQualityUpgradeMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(PetGoldQualityUpgradeMessage.class);

	private long petId;
	
	private OutfitUpgradeResult result;
	private PetGoldUpgradeResultType type;
	private int skillId;
	
	public PetGoldQualityUpgradeMessage()
	{
		super(MessageCommands.PET_GOLD_QUALITY_UPGRADE_MESSAGE);
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
		HeroProBuf.PGoldQualityUpgrade.Builder builder = HeroProBuf.PGoldQualityUpgrade
				.newBuilder();
		builder.setResult(result.ordinal());
		if(result == OutfitUpgradeResult.SUCCESS)
		{
			builder.setChangeFlag(type.ordinal());
			if(type != PetGoldUpgradeResultType.UP_2_GOLD)
				builder.setSkillId(skillId);
		}
		
		HeroProBuf.PGoldQualityUpgrade resp = builder.build();
		out.put(resp.toByteArray());
	}

	public void release()
	{
	}

	public long getPetId()
	{
		return petId;
	}

	public void setResult(OutfitUpgradeResult result)
	{
		this.result = result;
	}

	public void setType(PetGoldUpgradeResultType type)
	{
		this.type = type;
	}

	public void setSkillId(int skillId)
	{
		this.skillId = skillId;
	}
	
}
