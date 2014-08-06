package com.doteyplay.game.config.template;

import com.doteyplay.game.util.SimpleReflectUtils;
import com.doteyplay.game.util.excel.ExcelCellBinding;
import com.doteyplay.game.util.excel.ExcelRowBinding;
import com.doteyplay.game.util.excel.TemplateConfigException;
import com.doteyplay.game.util.excel.TemplateObject;

@ExcelRowBinding
public class TollgateDataTemplate extends TemplateObject {

	/**
	 * 关卡名id
	 */
	@ExcelCellBinding
	protected String tollgateNameId;

	/**
	 * 关卡描述id
	 */
	@ExcelCellBinding
	protected String tollgateDescId;
	
	@ExcelCellBinding
	protected String tollgateUnOpenDesc;
	/**
	 * 显示分类
	 */
	@ExcelCellBinding
	protected int tollgateShowType;

	/**
	 * 关卡形象资源id
	 */
	@ExcelCellBinding
	protected int tollgateShowIconId;
	/**
	 * 关卡地图资源id
	 */
	@ExcelCellBinding
	protected String tollgateMapResourceId;
	/**
	 * 关卡父id.
	 */
	@ExcelCellBinding
	protected int tollgateParentId;

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public String getTollgateNameId() {
		return tollgateNameId;
	}

	public void setTollgateNameId(String tollgateNameId) {
		this.tollgateNameId = tollgateNameId;
	}

	public String getTollgateDescId() {
		return tollgateDescId;
	}

	public void setTollgateDescId(String tollgateDescId) {
		this.tollgateDescId = tollgateDescId;
	}

	public String getTollgateUnOpenDesc()
	{
		return tollgateUnOpenDesc;
	}

	public void setTollgateUnOpenDesc(String tollgateUnOpenDesc)
	{
		this.tollgateUnOpenDesc = tollgateUnOpenDesc;
	}

	public int getTollgateShowType() {
		return tollgateShowType;
	}

	public void setTollgateShowType(int tollgateShowType) {
		this.tollgateShowType = tollgateShowType;
	}

	public int getTollgateShowIconId() {
		return tollgateShowIconId;
	}

	public void setTollgateShowIconId(int tollgateShowIconId) {
		this.tollgateShowIconId = tollgateShowIconId;
	}

	public String getTollgateMapResourceId() {
		return tollgateMapResourceId;
	}

	public void setTollgateMapResourceId(String tollgateMapResourceId) {
		this.tollgateMapResourceId = tollgateMapResourceId;
	}
	
	public int getTollgateParentId() {
		return tollgateParentId;
	}

	public void setTollgateParentId(int tollgateParentId) {
		this.tollgateParentId = tollgateParentId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+SimpleReflectUtils.reflect(this);
	}

}
