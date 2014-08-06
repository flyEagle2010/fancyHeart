package com.doteyplay.game.config.template;

import com.doteyplay.game.util.excel.ExcelCellBinding;
import com.doteyplay.game.util.excel.ExcelRowBinding;
import com.doteyplay.game.util.excel.TemplateConfigException;
import com.doteyplay.game.util.excel.TemplateObject;
import com.doteyplay.game.util.excel.TemplateService;

@ExcelRowBinding
public class CreateDataTemplate extends TemplateObject
{
	
	@ExcelCellBinding
	protected int type;

	@ExcelCellBinding
	protected int cost;

	@ExcelCellBinding
	protected int outItemId;

	@ExcelCellBinding
	protected int inItemId1;

	@ExcelCellBinding
	protected int itemNum1;

	@ExcelCellBinding
	protected int inItemId2;

	@ExcelCellBinding
	protected int itemNum2;

	@ExcelCellBinding
	protected int inItemId3;

	@ExcelCellBinding
	protected int itemNum3;

	@ExcelCellBinding
	protected int inItemId4;

	@ExcelCellBinding
	protected int itemNum4;

	@ExcelCellBinding
	protected int inItemId5;

	@ExcelCellBinding
	protected int itemNum5;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getCost()
	{
		return cost;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}

	public int getOutItemId()
	{
		return outItemId;
	}

	public void setOutItemId(int outItemId)
	{
		this.outItemId = outItemId;
	}

	public int getInItemId1()
	{
		return inItemId1;
	}

	public void setInItemId1(int inItemId1)
	{
		this.inItemId1 = inItemId1;
	}

	public int getItemNum1()
	{
		return itemNum1;
	}

	public void setItemNum1(int itemNum1)
	{
		this.itemNum1 = itemNum1;
	}

	public int getInItemId2()
	{
		return inItemId2;
	}

	public void setInItemId2(int inItemId2)
	{
		this.inItemId2 = inItemId2;
	}

	public int getItemNum2()
	{
		return itemNum2;
	}

	public void setItemNum2(int itemNum2)
	{
		this.itemNum2 = itemNum2;
	}

	public int getInItemId3()
	{
		return inItemId3;
	}

	public void setInItemId3(int inItemId3)
	{
		this.inItemId3 = inItemId3;
	}

	public int getItemNum3()
	{
		return itemNum3;
	}

	public void setItemNum3(int itemNum3)
	{
		this.itemNum3 = itemNum3;
	}

	public int getInItemId4()
	{
		return inItemId4;
	}

	public void setInItemId4(int inItemId4)
	{
		this.inItemId4 = inItemId4;
	}

	public int getItemNum4()
	{
		return itemNum4;
	}

	public void setItemNum4(int itemNum4)
	{
		this.itemNum4 = itemNum4;
	}

	public int getInItemId5()
	{
		return inItemId5;
	}

	public void setInItemId5(int inItemId5)
	{
		this.inItemId5 = inItemId5;
	}

	public int getItemNum5()
	{
		return itemNum5;
	}

	public void setItemNum5(int itemNum5)
	{
		this.itemNum5 = itemNum5;
	}

	@Override
	public void patchUp()
	{
		ItemDataObject item = TemplateService.getInstance().get(outItemId, ItemDataObject.class);
		item.setCreate(this);
	}
	
	@Override
	public String toString()
	{
		return "CreateDataTemplate [type=" + type + ", cost=" + cost
				+ ", outItemId=" + outItemId + ", inItemId1=" + inItemId1
				+ ", itemNum1=" + itemNum1 + ", inItemId2=" + inItemId2
				+ ", itemNum2=" + itemNum2 + ", inItemId3=" + inItemId3
				+ ", itemNum3=" + itemNum3 + ", inItemId4=" + inItemId4
				+ ", itemNum4=" + itemNum4 + ", inItemId5=" + inItemId5
				+ ", itemNum5=" + itemNum5 + "]";
	}

}
