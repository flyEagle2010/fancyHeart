package com.doteyplay.game.message.pet;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.message.proto.HeroProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 宠物召唤消息.
 * 
 * @className:PetCallMessage.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年8月29日 上午11:21:33
 */
public class PetCallMessage extends AbstractMessage {
	/**
	 * @author:Tom.Zheng
	 * @createTime:2014年8月29日 上午11:28:56
	 */
	private static final long serialVersionUID = -4885184719392349674L;

	private static final Logger logger = Logger.getLogger(PetCallMessage.class);

	/**
	 * 召唤英雄ID
	 */
	private int callPetSpriteId;

	public PetCallMessage() {
		super(MessageCommands.PET_CALL_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in) {
		byte buff[] = this.getProtoBufBytes(in);
		try {
			HeroProBuf.PCallHero call = HeroProBuf.PCallHero.parseFrom(buff);
			callPetSpriteId = call.getHeroId();
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void encodeBody(IoBuffer out) {

	}

	public void release() {
	}

	public int getCallPetSpriteId() {
		return callPetSpriteId;
	}

	
}
