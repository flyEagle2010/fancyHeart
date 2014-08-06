package com.doteplay.editor.data.sprite;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataManager;
import com.doteyplay.game.gamedata.data.sprite.SpriteGameData;

public class SpriteData extends BaseData<SpriteGameData>
{

	public SpriteData()
	{
		super(new SpriteGameData());
		type = "sprite";
		dataGroup = DataManager.getDataGroup(type);
	}


	@Override
	public String toString()
	{
		String s = super.toString();
		s += "(" + dataType + ")";
		return s;
	}

	// // TODO:=======Excel=====================================================
	// /**
	// * 获取Excel数据，需重载实现
	// */
	public String[] getExcelData()
	{
		String[] ss = new String[]
		{ id, String.valueOf(nameId) };
		return ss;
	}
	//
	// /**
	// * 设置Excel数据，需重载实现
	// */
	// public void setExcelData(String[] ss,int index) {
	// id = ss[0];
	// name = ss[1];
	// showName = ss[2];
	// }
	//
	// /**
	// * 获得Excel格式，需重载实现
	// */
	// public WritableSheet getWritableSheet(WritableSheet
	// sheet,WritableCellFormat wcf){
	// try {
	// sheet.addCell(new Label(0,0,"数据编号",wcf));
	// sheet.addCell(new Label(1,0,"名称",wcf));
	// sheet.addCell(new Label(1,0,"展现名称",wcf));
	// sheet.setColumnView(0, 10);
	// sheet.setColumnView(1, 30);
	// sheet.setColumnView(2, 30);
	// } catch (RowsExceededException e) {
	// e.printStackTrace();
	// } catch (WriteException e) {
	// e.printStackTrace();
	// }
	// return sheet;
	// }
	//
}
