/**
 * @package com.doteplay.editor.common
 * @file BaseDataListRender.java
 */
package com.doteplay.editor.common;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


/**
 */
public class BaseDataListRender extends DefaultListCellRenderer {

	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) 
	{
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		BaseData bd = (BaseData) value;
		setText(bd.getFullName());
		return this;
	}
	
}
