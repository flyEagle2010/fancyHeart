package com.doteyplay.game.message.common;

import org.apache.mina.core.buffer.IoBuffer;

import com.doteyplay.game.MessageCommands;
import com.doteyplay.game.constants.common.CommonPropUpdateType;
import com.doteyplay.game.constants.common.RewardType;
import com.doteyplay.game.message.proto.AccountProBuf;
import com.doteyplay.net.message.AbstractMessage;

/**
 */
public class CommonPropUpdateMessage extends AbstractMessage
{

	private static final long serialVersionUID = 5889386302827062340L;

	private CommonPropUpdateType type;
	private int addVlaue;
	private int finalValue;
	private RewardType rewardType;
	private long pkId;

	public CommonPropUpdateMessage(long pkId,CommonPropUpdateType type, int addVlaue,
			int finalValue, RewardType rewardType)
	{
		super(MessageCommands.COMMON_PROP_UPDATE_MESSAGE);
		this.type = type;
		this.addVlaue = addVlaue;
		this.finalValue = finalValue;
		this.rewardType = rewardType;
		this.pkId = pkId;
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
		AccountProBuf.PUpRole.Builder builder = AccountProBuf.PUpRole
				.newBuilder();
		builder.setAddValue(addVlaue);
		builder.setFinalValue(finalValue);
		builder.setItemType(String.valueOf(rewardType.ordinal()));
		builder.setFieldType(type.ordinal());
		builder.setUpdatePkId(pkId);
		
		AccountProBuf.PUpRole resp = builder.build();
		out.put(resp.toByteArray());
	}

}
