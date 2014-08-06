package com.doteyplay.game.domain.common;

import java.util.concurrent.atomic.AtomicLong;

import com.doteyplay.game.domain.gamebean.IdBean;

public class IdObj
{
	private IdBean idBean;
	private AtomicLong curId;

	public IdObj(IdBean idBean)
	{
		this.idBean = idBean;
		this.curId = new AtomicLong(idBean.getNum());
	}

	public long getNewId()
	{
		return curId.incrementAndGet();
	}

	public IdBean getIdBean()
	{
		return idBean;
	}

	public void setIdBean(IdBean idBean)
	{
		this.idBean = idBean;
	}

	public AtomicLong getCurId()
	{
		return curId;
	}

	public void setCurId(AtomicLong curId)
	{
		this.curId = curId;
	}

}
