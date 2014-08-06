package com.doteyplay.game.action.tollgate;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.tollgate.EnterBattleMessage;
import com.doteyplay.game.service.bo.tollgate.ITollgateInfoService;

/**
 * @className:NodeEnterMessageAction.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月18日 下午3:25:50
 */
public @ActionAnnotation(message = com.doteyplay.game.message.tollgate.EnterBattleMessage.class) class EnterBattleMessageAction implements ServiceMessageAction<EnterBattleMessage, ITollgateInfoService>{

	@Override
	public void processMessage(EnterBattleMessage message, ITollgateInfoService p)
			throws MessageProcessException {
		// TODO Auto-generated method stub
		p.enterBattle(message.getTollgateId(), message.getNodeId(),message.getGroupId());
	}

}
