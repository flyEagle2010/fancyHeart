/**
 * 
 */
package com.doteplay.editor.common.commonobject;

import com.doteplay.editor.common.InnerEditorPanel;

public class CommonDataPanel<DATA extends Object> extends InnerEditorPanel {
	
	protected DATA data;
	
	public void init(DATA data)
	{
		this.data = data;
	}
	
	@Override
	public void release() {
		super.release();
		
		this.data = null;
	}
}
