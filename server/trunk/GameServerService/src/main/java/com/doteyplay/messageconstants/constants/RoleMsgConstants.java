package com.doteyplay.messageconstants.constants;

import com.doteyplay.core.resourcebundle.WordResource;
import com.doteyplay.messageconstants.MessageUtil;
import com.doteyplay.messageconstants.MsgPolymer;

public enum RoleMsgConstants implements IMsgConstants
{

	ROLE_LEVEL_NOT_ENOUGH("角色等级不足"), ;

	private final String message;

	private RoleMsgConstants(String message)
	{
		this.message = message;
	}

	/**
	 * 通过索引获取相应的枚举对象
	 * 
	 * @param index
	 *            必须为有效的索引
	 * @return
	 */
	public static RoleMsgConstants valueOf(int index)
	{
		return values()[index];
	}

	@Override
	public String getMessage()
	{
		return WordResource.get("RoleMsgConstants." + name(), message);
	}

	@Override
	public String getMessage(String... param)
	{
		return MessageUtil.getMessage(
				WordResource.get("RoleMsgConstants." + name(), message), param);
	}

	@Override
	public MsgPolymer getMsgPolymer(String... param)
	{
		return new MsgPolymer(this, param);
	}

	public String toString()
	{
		return message;
	}
}
