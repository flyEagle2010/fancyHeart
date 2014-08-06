package com.doteyplay.core.util.dependence;

import java.util.HashSet;
import java.util.Set;

/**
 * service�໥֮����������.�����Ƿ�����.
* @className:DependenceScaner.java
* @classDescription:
* @author:Tom.Zheng
* @createTime:2014��6��25�� ����4:35:05
 */
public class DependenceScaner
{
	/**
	 *
	 * @param itemname ������������.
	 * @param assembly ������������.ActionServiceHolder����.�ǹ̶�ֵ.����ط�,�е���.�����˾Ͳ�����.��ʵ����һ���ƹ����еķ������ķ�����.
	 * @param itembuffer ��ǰ�����еı�������.��һ�����ظ��ļ���.HashSet
	 * @return
	 */
	private static boolean internalCheckRelation(String itemname, IDependenceAssembly assembly,
			Set<String> itembuffer)
	{	//����itemname,��ȡ��Ӧ�ķ���.
		IDependence tmpIDependence = assembly.getItem(itemname);
		//itemname �������,��ͨΪnull
		if (tmpIDependence == null)
			return false;
		//��һ���ǿյ�.������ .
		if (itembuffer.contains(itemname))
			return false;
		else
			itembuffer.add(itemname);//���ӷ�����A

		boolean r = true;
		//������������ϵ.
		String[] tmpDependences = tmpIDependence.getDependence();
		//���û������.ֱ�ӷ���true;
		if (tmpDependences != null && tmpDependences.length > 0)
		{
			for (int i = 0; r && i < tmpDependences.length; i++)
			{	//ѭ��ÿһ�������ķ���Id;
				//itembuffer��������,ÿ�ζ��������Լ�.
				if (itembuffer.contains(tmpDependences[i]))
					r = false;
				else
					//�������.A������������A, ���A������B,��ôB������������AB.ͬʱA��������C,C������������ABC; A
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
	 * @return false ������ϵ���Ϸ�. 
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