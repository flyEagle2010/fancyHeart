package com.doteyplay.game.action.pet;

import com.doteyplay.core.action.ActionAnnotation;
import com.doteyplay.core.action.ServiceMessageAction;
import com.doteyplay.exception.MessageProcessException;
import com.doteyplay.game.message.pet.PetCallMessage;
import com.doteyplay.game.message.tollgate.GroupUpdateMessage;
import com.doteyplay.game.service.bo.role.IRoleService;
import com.doteyplay.game.service.bo.tollgate.ITollgateInfoService;

/**
 * 
* @className:PetCallMessageAction.java
* @classDescription: 召唤英雄的信息处理
* @author:Tom.Zheng
* @createTime:2014年8月29日 上午11:32:22
 */

@ActionAnnotation(message = com.doteyplay.game.message.pet.PetCallMessage.class)
public  class PetCallMessageAction implements ServiceMessageAction<PetCallMessage, IRoleService>{

	@Override
	public void processMessage(PetCallMessage message,
			IRoleService p) throws MessageProcessException {
		// TODO Auto-generated method stub
		p.summonPet(message.getCallPetSpriteId());
	}

}
