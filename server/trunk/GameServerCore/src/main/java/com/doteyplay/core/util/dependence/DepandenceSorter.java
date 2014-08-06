package com.doteyplay.core.util.dependence;

import java.util.ArrayList;
import java.util.List;

/**
 * 依赖关系排序.
 * 1.先找无依赖其它元素的,添加到加入到新的结果集中,同时在剩下的结果集中,寻找到对该结果集有依赖的元素,清理掉该依赖.
 * 2.循环所有的元素.
 * 3.完成排序.
 * 这样完成的元素排序,从后到前,先移动对其它元素无依赖的元素A到新的集合,清理其它元素对A的依赖关系,三.按以上标准进行循环.
 */
public class DepandenceSorter
{
	/**
	 * 排序后的结果
	 */
	private final List<String> result;
	/**
	 * 未排序的集合.
	 */
	private final List<Element> unsortList;

	public DepandenceSorter()
	{
		result = new ArrayList<String>();
		unsortList = new ArrayList<DepandenceSorter.Element>();
	}
	/**
	 * 添加所有的元素.依依赖关系.
	 * @param itemName
	 * @param depands
	 */
	public void addElement(String itemName, String... depands)
	{
		Element e = new Element();

		e.itemName = itemName;
		if (depands != null)
		{
			for (String tmp : depands)
				e.depands.add(tmp);
		}
		unsortList.add(e);
	}
	/**
	 * 排序操作
	 * @return
	 */
	public List<String> sort()
	{
		while (unsortList.size() > 0)
		{
			for (int i = unsortList.size() - 1; i >= 0; --i)
			{
				Element e = unsortList.get(i);
				if (e.depands == null || e.depands.size() == 0)
				{	//无依赖的元素e,首先加入结果集
					result.add(e.itemName);
					//从未排序中移除.
					unsortList.remove(i);
					//移除对e的所有的依赖关系.
					removeParent(e.itemName);
				}
			}
		}

		return result;
	}
	
	/**
	 * 移除其它元素,对此元素的依赖关系.
	 * @param pId
	 */
	private void removeParent(String pId)
	{
		for (Element e : unsortList)
			if (e.depands != null)
				e.depands.remove(pId);
	}

	/**
	 * 
	* @className:DepandenceSorter.java
	* @classDescription: 内部类.
	 */
	private class Element
	{
		/**
		 * 存贮依赖关系
		 */
		List<String> depands = new ArrayList<String>();
		/**
		 * 元素名.
		 */
		String itemName;
	}

//	 public static void main(String[] args)
//	 {
//	 DepandenceSorter s = new DepandenceSorter();
//	 s.addElement(1);
//	 s.addElement(2);
//	 s.addElement(3);
//	 s.addElement(4,1,3);
//	 s.addElement(5, 3,8);
//	 s.addElement(6,4,5,8);
//	 s.addElement(7, 3);
//	 s.addElement(8, 7);
//	 System.out.println(s.sort().toString());
//	 }
}
