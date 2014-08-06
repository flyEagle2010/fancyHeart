package com.doteyplay.game.persistence.serverdata.skill;

import java.util.List;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.SkillBean;

public interface ISkillBeanDao extends IDaoExecutor
{
	@DAOInfo(Params = "")
	public List<SkillBean> selectSkillBeanList(long roleId);

	@DAOInfo(Params = "")
	public void insertSkillBean(SkillBean skillBean);

	@DAOInfo(Params = "roleId,petId,skillId,newSkillId")
	public void updateSkillBean(long roleId,long petId,int skillId,int newSkillId);

}