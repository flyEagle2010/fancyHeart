package com.doteyplay.core.behavior;

/**
 * 从行为聚合接口
 * 
 */
public interface IBehaviorAssembly
{
	/**
	 * 从行为聚合中获得一个行为接口
	 * 
	 * @return IBehavior
	 */
	public <T extends IBehavior> T getBehavior(int behaviorid);

}
