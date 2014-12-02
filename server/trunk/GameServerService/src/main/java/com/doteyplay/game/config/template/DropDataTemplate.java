package com.doteyplay.game.config.template;

import com.doteyplay.game.util.SimpleReflectUtils;
import com.doteyplay.game.util.excel.ExcelCellBinding;
import com.doteyplay.game.util.excel.ExcelRowBinding;
import com.doteyplay.game.util.excel.TemplateConfigException;
import com.doteyplay.game.util.excel.TemplateObject;
/**
 * 掉落组
* @className:DropDataTemplate.java
* @classDescription:
* @author:Tom.Zheng
* @createTime:2014年10月14日 下午4:38:55
 */
@ExcelRowBinding
public class DropDataTemplate extends TemplateObject {

	/**
	 * 物品id
	 */
	@ExcelCellBinding
	protected int itemId;

	/**
	 * 物品数量
	 */
	@ExcelCellBinding
	protected int num;
	
	/**
	 * 掉率,成分之多少.
	 */
	@ExcelCellBinding
	protected int rate;
	

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}
	
	

	public int getItemId() {
		return itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}



	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}



	public int getRate() {
		return rate;
	}



	public void setRate(int rate) {
		this.rate = rate;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+SimpleReflectUtils.reflect(this);
	}

}
