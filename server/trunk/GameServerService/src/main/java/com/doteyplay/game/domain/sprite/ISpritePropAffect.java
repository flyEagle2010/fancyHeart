package com.doteyplay.game.domain.sprite;

import java.util.List;

/**
 * 可对属性造成影响的抽象
 * @author AllenGao
 *
 */
public interface ISpritePropAffect
{
	/**
	 * 品阶
	 * @return
	 */
	public abstract int getQuality();
	
	/**
	 * 星级
	 * @return
	 */
	public abstract int getStar();
	
	/**
	 * 等级
	 * @return
	 */
	public abstract int getLevel();
	
}
