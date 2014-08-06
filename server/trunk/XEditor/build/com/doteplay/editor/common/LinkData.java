package com.doteplay.editor.common;

import com.doteyplay.game.editor.ResourceDataConstants;

public class LinkData {

	public final static int LEVEL_0 = 0;
	public final static int LEVEL_1 = 1;
	public final static int LEVEL_2 = 2;
	public final static int LEVEL_3 = 3;
	public final static int LEVEL_4 = 4;
	public final static int LEVEL_5 = 5;
	public final static int LEVEL_6 = 6;
	public final static int LEVEL_7 = 7;
	public final static int LEVEL_8 = 8;
	public final static int LEVEL_9 = 9;
	public final static int LEVEL_COUNTS = 10;

	public int type;
	public int id;
	public int level = LEVEL_0;
	
	public boolean isRemove = false; 

	public LinkData(int type, int id, int level) {
		this.type = type;
		this.id = id;
		this.level = level;
	}

	public String getKey() {
		return type + "_" + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LinkData) {
			LinkData l = (LinkData) obj;
			if (type == l.type && id == l.id && level == l.level) {
				return true;
			}
		}
		return false;
	}

//	@Override
//	public String toString() {
//		BaseData bd = DataManager.getData(ResourceDataConstants.DATATYPE_ID[type], "" + id);
//		return "ÑÓ³Ù[ " + level  + " ] "+ ResourceDataConstants.DATATYPE_NAMES[type] + "  " +id + "£º" + bd.name;
//	}

	
//	public BaseData getBaseData(){
//		BaseData bd = DataManager.getData(ResourceDataConstants.DATATYPE_ID[type], "" + id);
//		return bd;
//	}
	
	
}
