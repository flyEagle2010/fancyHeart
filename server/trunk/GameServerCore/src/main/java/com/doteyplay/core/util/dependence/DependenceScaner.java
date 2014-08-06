package com.doteyplay.core.util.dependence;

import java.util.HashSet;
import java.util.Set;

/**
 * service相互之间的依赖检查.数据是否完整.
* @className:DependenceScaner.java
* @classDescription:
* @author:Tom.Zheng
* @createTime:2014年6月25日 下午4:35:05
 */
public class DependenceScaner
{
	/**
	 *
	 * @param itemname 服务器的名字.
	 * @param assembly 服务器持有者.ActionServiceHolder对象.是固定值.这个地方,有点绕.理解了就不难了.其实就是一个掌管所有的服务器的服务者.
	 * @param itembuffer 当前的所有的被依赖者.是一个不重复的集合.HashSet
	 * @return
	 */
	private static boolean internalCheckRelation(String itemname, IDependenceAssembly assembly,
			Set<String> itembuffer)
	{	//根据itemname,获取相应的服务.
		IDependence tmpIDependence = assembly.getItem(itemname);
		//itemname 这个服务,不通为null
		if (tmpIDependence == null)
			return false;
		//第一遍是空的.不包含 .
		if (itembuffer.contains(itemname))
			return false;
		else
			itembuffer.add(itemname);//添加服务名A

		boolean r = true;
		//深入检查依赖关系.
		String[] tmpDependences = tmpIDependence.getDependence();
		//如果没有依赖.直接返回true;
		if (tmpDependences != null && tmpDependences.length > 0)
		{
			for (int i = 0; r && i < tmpDependences.length; i++)
			{	//循环每一个依赖的服务Id;
				//itembuffer存贮集合,每次都会添加自己.
				if (itembuffer.contains(tmpDependences[i]))
					r = false;
				else
					//依赖检查.A的依赖不能有A, 如果A有依赖B,那么B的依赖不能有AB.同时A的依赖有C,C的依赖不能有ABC; A
					r = internalCheckRelation(tmpDependences[i], assembly, itembuffer);
				
				if(!r)
					break;
			}
		}
		return r;
	}
	/**
	 * 
	 * @param itemname
	 * @param assembly
	 * @return false 依赖关系不合法. 
	 */
	public static boolean checkValidation(String itemname, IDependenceAssembly assembly)
	{
		if (itemname == null || assembly == null)
			return true;

		Set<String> tmpSet = new HashSet<String>();
		boolean r = internalCheckRelation(itemname, assembly, tmpSet);
		tmpSet.clear();
		tmpSet = null;
		return r;
	}

}
