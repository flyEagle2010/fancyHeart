/**
 * @package com.doteplay.editor.component.PropertiesTable
 * @file StringPropertyEditor.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 */
public class StringPropertyEditor implements IPropertyEditor<StringProperty>, TableCellEditor {

	private StringProperty property;

	private JTextField textField;

	public StringPropertyEditor(){
		if(textField == null){
			textField = new JTextField();
		}
	}
	
	@Override
	public void setEditProperty(StringProperty p) {
		property = p;
		textField.setText(property.getValue());
	}

	@Override
	public void show(Component c) {
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return textField;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {

	}

	@Override
	public void cancelCellEditing() {
		textField.setText(property.getValue());
	}

	@Override
	public Object getCellEditorValue() {
		return textField.getText();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		property.setValue(textField.getText());
		return true;
	}

}
