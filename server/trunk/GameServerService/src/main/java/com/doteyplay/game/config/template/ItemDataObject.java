package com.doteyplay.game.config.template;

import com.doteyplay.game.util.excel.ExcelRowBinding;

@ExcelRowBinding
public class ItemDataObject extends ItemDataTemplate
{
	private CreateDataTemplate create;

	public CreateDataTemplate getCreate()
	{
		return create;
	}

	public void setCreate(CreateDataTemplate create)
	{
		this.create = create;
	}
}
