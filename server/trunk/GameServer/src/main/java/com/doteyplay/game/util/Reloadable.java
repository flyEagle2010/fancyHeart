package com.doteyplay.game.util;

/**
 * 指示此对象可重新装载数据，并以新数据运行
 * 
 */
public interface Reloadable
{
	/**
	 * 重新装载数据，并以新数据运行
	 * @return
	 */
	public boolean reload();
}
