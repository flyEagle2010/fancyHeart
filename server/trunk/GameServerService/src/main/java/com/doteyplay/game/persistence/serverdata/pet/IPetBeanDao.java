package com.doteyplay.game.persistence.serverdata.pet;

import java.util.List;

import com.doteyplay.core.dbcs.daoproxy.IDaoExecutor;
import com.doteyplay.core.dbcs.executor.DAOInfo;
import com.doteyplay.game.domain.gamebean.PetBean;

public interface IPetBeanDao extends IDaoExecutor
{
	@DAOInfo(Params = "")
	public PetBean selectPetBean(long petId);

	@DAOInfo(Params = "")
	public List<PetBean> selectPetBeanListByRoleId(long roleId);

	@DAOInfo(Params = "")
	public void insertPetBean(PetBean petBean);

	@DAOInfo(Params = "")
	public void updatePetBean(PetBean petBean);
}