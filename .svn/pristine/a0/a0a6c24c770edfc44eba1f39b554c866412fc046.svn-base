package com.doteyplay.game.service.bo.virtualworld.chat.handle;

import com.doteyplay.core.dbcs.DBCS;
import com.doteyplay.game.domain.gamebean.RoleBean;
import com.doteyplay.game.domain.role.Role;
import com.doteyplay.game.persistence.serverdata.role.IRoleBeanDao;
import com.doteyplay.game.service.bo.virtualworld.chat.patameter.ExpPatameterObject;

/**
 * @className:ExpHandlerImpl.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月10日 下午4:37:05
 */
public class ExpHandlerImpl extends AbstractHandlerImpl<ExpPatameterObject>{

	@Override
	public void onlineHandle(Role role, ExpPatameterObject t) {
		// TODO Auto-generated method stub
		role.addExp(t.getNum());
	}

	@Override
	public void offlineHandle(long roleId, ExpPatameterObject t) {
		// TODO Auto-generated method stub
		//采用邮件服务.发送其它值.
		IRoleBeanDao dao = DBCS.getExector(IRoleBeanDao.class);
		
		RoleBean roleBean = dao.selectRoleBean(roleId);
		
		if (roleBean == null) {
			throw new RuntimeException("GM do 角色属性添加,Id对应的角色不存在");
		}
		roleBean.setExp((t.getNum()+roleBean.getExp())>0?t.getNum()+roleBean.getExp():0);
		
		dao.updateRoleBean(roleBean);
	}

}
