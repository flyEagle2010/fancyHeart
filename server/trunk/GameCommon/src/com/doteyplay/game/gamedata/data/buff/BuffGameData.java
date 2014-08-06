package com.doteyplay.game.gamedata.data.buff;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.constants.ActivateEvent;
import com.doteyplay.game.constants.DamageType;
import com.doteyplay.game.constants.skill.SkillActionType;
import com.doteyplay.game.constants.skill.SkillAmmoType;
import com.doteyplay.game.constants.skill.SkillEffectRange;
import com.doteyplay.game.constants.skill.SkillTargetSelectType;
import com.doteyplay.game.gamedata.data.IGameData;

public class BuffGameData implements IGameData
{
	
	public DamageType damageType = DamageType.PHYSICS;
	/**
	 * 持续伤害
	 */
	public int damage;
	/**
	 * 持续恢复
	 */
	public int health;
	
	/**
	 * 打断移除
	 */
	public boolean canRemove;
	
	/**
	 * 移动
	 */
	public boolean canMove;
	
	/**
	 * 主动施法
	 */
	public boolean canUseSkill;
	
	/**
	 * 被治疗
	 */
	public boolean canHealth;
	
	/**
	 * 物理免疫
	 */
	public boolean isPd;
	
	/**
	 * 法术免疫
	 */
	public boolean isMd;
	
	/**
	 * 离线存储
	 */
	public boolean isOfflineSave;
	
	/**
	 * 离线计时
	 */
	public boolean isOfflineReduce;
	
	/**
	 * 持续时间
	 */
	public int time;
	
	/**
	 * 攻击力
	 */
	public int ap;
	/**
	 * 物理防御
	 */
	public int pd;
	
	/**
	 * 法术防御
	 */
	public int md;
	
	/**
	 * 暴击
	 */
	public int crh;
	
	/**
	 * 闪避
	 */
	public int dodge;
	
	
	@Override
	public void load(DataInputStream in) throws IOException
	{
		this.damage = in.readInt();
		this.health = in.readInt();
		this.time = in.readInt();
		this.ap = in.readInt();
		this.pd = in.readInt();
		this.md = in.readInt();
		this.crh = in.readInt();
		this.dodge = in.readInt();
		
		this.canRemove = in.readBoolean();
		this.canMove = in.readBoolean();
		this.canUseSkill = in.readBoolean();
		this.canHealth = in.readBoolean();
		this.isPd = in.readBoolean();
		this.isMd = in.readBoolean();
		this.isOfflineSave = in.readBoolean();
		this.isOfflineReduce = in.readBoolean();
		
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(damage);
		out.writeInt(health);
		out.writeInt(time);
		out.writeInt(ap);
		out.writeInt(pd);
		out.writeInt(md);
		out.writeInt(crh);
		out.writeInt(dodge);
		
		out.writeBoolean(canRemove);
		out.writeBoolean(canMove);
		out.writeBoolean(canUseSkill);
		out.writeBoolean(canHealth);
		out.writeBoolean(isPd);
		out.writeBoolean(isMd);
		out.writeBoolean(isOfflineSave);
		out.writeBoolean(isOfflineReduce);
	}
}
