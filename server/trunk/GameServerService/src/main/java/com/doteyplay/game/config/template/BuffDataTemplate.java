package com.doteyplay.game.config.template;

import com.doteyplay.game.util.excel.ExcelCellBinding;
import com.doteyplay.game.util.excel.ExcelRowBinding;
import com.doteyplay.game.util.excel.TemplateConfigException;
import com.doteyplay.game.util.excel.TemplateObject;

@ExcelRowBinding
public class BuffDataTemplate extends TemplateObject
{
	@ExcelCellBinding
	protected int damageType;

	@ExcelCellBinding
	protected int time;

	@ExcelCellBinding
	protected boolean isOfflineSave;
	/**
	 * 离线计时
	 */
	@ExcelCellBinding
	protected boolean isOfflineReduce;

	/**
	 * 持续伤害
	 */
	@ExcelCellBinding
	protected int damage;
	/**
	 * 持续恢复
	 */
	@ExcelCellBinding
	protected int health;

	/**
	 * 打断移除
	 */
	@ExcelCellBinding
	protected boolean canRemove;

	/**
	 * 移动
	 */
	@ExcelCellBinding
	protected boolean canMove;

	/**
	 * 主动施法
	 */
	@ExcelCellBinding
	protected boolean canUseSkill;

	/**
	 * 被治疗
	 */
	@ExcelCellBinding
	protected boolean canHealth;

	/**
	 * 物理免疫
	 */
	@ExcelCellBinding
	protected boolean isPd;

	/**
	 * 法术免疫
	 */
	@ExcelCellBinding
	protected boolean isMd;

	/**
	 * 攻击力
	 */
	@ExcelCellBinding
	protected int ap;
	/**
	 * 物理防御
	 */
	@ExcelCellBinding
	protected int pd;

	/**
	 * 法术防御
	 */
	@ExcelCellBinding
	protected int md;

	/**
	 * 暴击
	 */
	@ExcelCellBinding
	protected int crh;

	/**
	 * 闪避
	 */
	@ExcelCellBinding
	protected int dodge;
	
	/**
	 * 分组
	 */
	@ExcelCellBinding
	protected int groupId;
	
	/**
	 * 等级
	 */
	@ExcelCellBinding
	protected int level;

	public int getDamageType()
	{
		return damageType;
	}

	public void setDamageType(int damageType)
	{
		this.damageType = damageType;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public boolean isOfflineSave()
	{
		return isOfflineSave;
	}

	public void setOfflineSave(boolean isOfflineSave)
	{
		this.isOfflineSave = isOfflineSave;
	}

	public boolean isOfflineReduce()
	{
		return isOfflineReduce;
	}

	public void setOfflineReduce(boolean isOfflineReduce)
	{
		this.isOfflineReduce = isOfflineReduce;
	}

	public int getDamage()
	{
		return damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public boolean isCanRemove()
	{
		return canRemove;
	}

	public void setCanRemove(boolean canRemove)
	{
		this.canRemove = canRemove;
	}

	public boolean isCanMove()
	{
		return canMove;
	}

	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	public boolean isCanUseSkill()
	{
		return canUseSkill;
	}

	public void setCanUseSkill(boolean canUseSkill)
	{
		this.canUseSkill = canUseSkill;
	}

	public boolean isCanHealth()
	{
		return canHealth;
	}

	public void setCanHealth(boolean canHealth)
	{
		this.canHealth = canHealth;
	}

	public boolean isPd()
	{
		return isPd;
	}

	public void setPd(boolean isPd)
	{
		this.isPd = isPd;
	}

	public boolean isMd()
	{
		return isMd;
	}

	public void setMd(boolean isMd)
	{
		this.isMd = isMd;
	}

	public int getAp()
	{
		return ap;
	}

	public void setAp(int ap)
	{
		this.ap = ap;
	}

	public int getPd()
	{
		return pd;
	}

	public void setPd(int pd)
	{
		this.pd = pd;
	}

	public int getMd()
	{
		return md;
	}

	public void setMd(int md)
	{
		this.md = md;
	}

	public int getCrh()
	{
		return crh;
	}

	public void setCrh(int crh)
	{
		this.crh = crh;
	}

	public int getDodge()
	{
		return dodge;
	}

	public void setDodge(int dodge)
	{
		this.dodge = dodge;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	@Override
	public String toString()
	{
		return "BuffDataTemplate [damageType=" + damageType + ", time=" + time
				+ ", isOfflineSave=" + isOfflineSave + ", isOfflineReduce="
				+ isOfflineReduce + ", damage=" + damage + ", health=" + health
				+ ", canRemove=" + canRemove + ", canMove=" + canMove
				+ ", canUseSkill=" + canUseSkill + ", canHealth=" + canHealth
				+ ", isPd=" + isPd + ", isMd=" + isMd + ", ap=" + ap + ", pd="
				+ pd + ", md=" + md + ", crh=" + crh + ", dodge=" + dodge
				+ ", groupId=" + groupId + ", level=" + level + "]";
	}
}
