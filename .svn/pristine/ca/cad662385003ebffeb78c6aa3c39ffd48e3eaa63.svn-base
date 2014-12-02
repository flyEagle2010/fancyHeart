package com.doteyplay.game.message.role;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.constants.account.LoginResult;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.message.proto.AccountProBuf;
import com.doteyplay.net.message.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class CreateRoleMessage extends AbstractMessage
{
	private static final Logger logger = Logger
			.getLogger(CreateRoleMessage.class);

	private int spriteId;
	private String name;

	
	public CreateRoleMessage()
	{
		super(MessageCommands.CREATE_ROLE_MESSAGE);
	}

	@Override
	public void decodeBody(IoBuffer in)
	{
		byte[] bytes = getProtoBufBytes(in);
		try
		{
			AccountProBuf.CreateRoleReq req = AccountProBuf.CreateRoleReq
					.parseFrom(bytes);
			spriteId = req.getSpriteId();
			name = req.getRoleName();
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

	public int getSpriteId()
	{
		return spriteId;
	}

	public void setSpriteId(int spriteId)
	{
		this.spriteId = spriteId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
