/**
 * @package com.doteplay.editor.component.PropertiesTable
 * @file BooleanPropertyEditor.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.doteplay.editor.util.Util;

/**
 */
public class BooleanPropertyEditor implements IPropertyEditor<BooleanProperty>,TableCellEditor{
	
	private BooleanProperty property;
	
	@Override
	public void show(Component c) {
		int rel = Util.selectIndex(c, "Ñ¡Ôñ", new String[] { "ÊÇ", "·ñ" }, (property.value == true ? "ÊÇ" : "·ñ"));
		if (rel == -1) {
			return;
		}
		property.setValue(rel == 0);
	}

	@Override
	public void setEditProperty(BooleanProperty p) {
		property = p;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return null;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {}

	@Override
	public void cancelCellEditing() {}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return false;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		return false;
	}
	
}
