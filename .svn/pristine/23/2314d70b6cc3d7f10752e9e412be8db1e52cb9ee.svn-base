package com.doteyplay.game.service.bo.virtualworld.chat.handle;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.constants.common.RewardType;
import com.doteyplay.game.domain.gamebean.RoleBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.persistence.serverdata.role.IRoleBeanDao;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.MoneyPatameterObject;

/**
 * @className:MoneyProcessImpl.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 上午11:33:43
 */
public class MoneyHandlerImpl extends AbstractHandlerImpl<MoneyPatameterObject>{

	@Override
	public void onlineHandle(Role role, MoneyPatameterObject t) {
		// TODO Auto-generated method stub
		role.addMoney(t.getNum(), RewardType.CHAT, true);
	}

	@Override
	public void offlineHandle(long roleId, MoneyPatameterObject t) {
		// TODO Auto-generated method stub
		//采用邮件服务.发送其它值.
		IRoleBeanDao dao = DBCS.getExector(IRoleBeanDao.class);
		
		RoleBean roleBean = dao.selectRoleBean(roleId);
		
		if (roleBean == null) {
			throw new RuntimeException("GM do 角色属性添加,Id对应的角色不存在");
		}
		roleBean.setMoney((t.getNum() + roleBean.getMoney()) > 0 ? t.getNum()
				+ roleBean.getMoney() : 0);
		
		dao.updateRoleBean(roleBean);
	}
	
	
	
	


}
