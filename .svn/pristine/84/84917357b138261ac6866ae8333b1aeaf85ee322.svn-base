package com.doteyplay.game.util.excel;

import java.io.Serializable;

import com.doteyplay.game.util.SimpleReflectUtils;


@ExcelRowBinding
public abstract class TemplateObject implements Serializable
{

	/**
	 * offset从0开始。默认0的位置是id;
	 */
	@ExcelCellBinding
	public int id;
	
	public TemplateObject()
	{
	}

	public final int getId()
	{
		return id;
	}

	public final void setId(int id)
	{
		this.id = id;
	}

	public void check() throws TemplateConfigException
	{
		
	}
	
	public void onLoadFinished()
	{
		
	}

	public void patchUp() throws Exception
	{
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (id ^ id >>> 32);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemplateObject other = (TemplateObject) obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method
		return "Id=["+id+"],";
	}

}
