package com.doteyplay.game.service.event.role;

import com.doteyplay.core.bhns.event.IServiceEvent;
import com.doteyplay.game.service.event.EventConst;

/**
 * 用户升级事件
 * @author 
 *
 */
public interface IRoleLevelUpEvent extends IServiceEvent{

	public final static int EVENT_ID= EventConst.ROLE_LEVEL_UP_EVENT.ordinal();
	
	public void onLevelUp(int roleId, int oldLevel, int nowLevel);
}
