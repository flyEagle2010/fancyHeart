package com.doteyplay.support.auth.action;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.doteyplay.core.bhns.AbstractSimpleService;
import com.doteyplay.core.bhns.BOServiceManager;
import com.doteyplay.core.bhns.active.ActiveServiceHolder;
import com.doteyplay.core.bhns.gateway.IGateWayService;
import com.doteyplay.game.domain.gamebean.UserBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.domain.user.User;
import com.doteyplay.game.message.login.LoginGameMessage;
import com.doteyplay.game.service.bo.gateway.IGameGateWayService;
import com.doteyplay.game.service.bo.virtualworld.IVirtualWorldService;
import com.doteyplay.game.service.runtime.GlobalUserCache;
import com.doteyplay.luna.common.action.BaseAction;
import com.doteyplay.luna.common.message.DecoderMessage;
import com.google.gson.Gson;

public class CheckAuthAction extends BaseAction
{

	private static Logger logger = Logger.getLogger(CheckAuthAction.class
			.getName());

	private Gson gson = new Gson();

	private static CheckAuthAction action = new CheckAuthAction();

	private CheckAuthAction()
	{

	}

	public static synchronized CheckAuthAction getInstance()
	{
		if (action == null)
			action = new CheckAuthAction();
		return action;
	}

	@Override
	public void doAction(IoSession session, DecoderMessage dMessage)
	{
		long sessionId = dMessage.getLong();
		boolean success = dMessage.getBoolean();

		UserBean userBean = null;
		IVirtualWorldService virtualWorldService = BOServiceManager
				.findDefaultService(IVirtualWorldService.PORTAL_ID);
		if (success)
			userBean = gson.fromJson(dMessage.getString(), UserBean.class);

		virtualWorldService.loginResult(success, userBean, sessionId);
	}

	@Override
	public void doAction(long roleId)
	{

	}
}
