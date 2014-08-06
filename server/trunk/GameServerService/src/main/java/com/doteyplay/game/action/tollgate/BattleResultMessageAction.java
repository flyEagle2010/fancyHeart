package com.doteyplay.game.action.tollgate;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.tollgate.BattleResultMessage;
import com.doteyplay.game.service.bo.tollgate.ITollgateInfoService;

/**
 * @className:AcceptBattleResultMessageAction.java
 * @classDescription:接收战斗结果信息
 * @author:Tom.Zheng
 * @createTime:2014年7月18日 下午5:01:08
 */
public @ActionAnnotation(message = com.doteyplay.game.message.tollgate.BattleResultMessage.class) class BattleResultMessageAction implements ServiceMessageAction<BattleResultMessage, ITollgateInfoService>{

	@Override
	public void processMessage(BattleResultMessage message,
			ITollgateInfoService p) throws MessageProcessException {
		// TODO Auto-generated method stub
		p.acceptBattleResult(message.getTollgateId(), message.getNodeId(), message.getStar());
	}

}
