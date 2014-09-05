package com.doteyplay.game.message.pet;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.domain.pet.Pet;
import com.doteyplay.game.message.proto.NpcProBuf;
import com.doteyplay.net.message.AbstractMessage;

public class PetAddRemoveMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(PetAddRemoveMessage.class);

	private List<Pet> petList;
	private boolean addOrRemove; 
	
	public PetAddRemoveMessage()
	{
		super(MessageCommands.ITEM_SELL_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in)
	{
		
	}

	@Override
	public void encodeBody(IoBuffer out)
	{
		NpcProBuf.PAddOrRemoveNpc.Builder builder = NpcProBuf.PAddOrRemoveNpc.newBuilder();
		builder.setAddOrRemove(addOrRemove);
		
		if(petList != null || petList.size() > 0)
		{
			for(Pet pet:petList)
			{
				NpcProBuf.PNpc.Builder petBuilder = NpcProBuf.PNpc.newBuilder();
				petBuilder.setNpcId(pet.getId());
				petBuilder.setSpriteId(pet.getSpriteDataObject().getId());
				
				builder.addNpcs(petBuilder);
			}
		}
		
		NpcProBuf.PAddOrRemoveNpc resp = builder.build();
		out.put(resp.toByteArray());
	}

	public void release()
	{
	}
}
