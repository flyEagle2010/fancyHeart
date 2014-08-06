/**
 * 
 */
package com.doteplay.editor.component.PropertiesTable;

import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * @author Yang
 */
public class PropertiesTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 6642324689642463255L;

	private IPropertyObject propertyObj;

	private final static String[] TITLE_NAME = { "属性", "值" };

	public PropertiesTableModel(IPropertyObject propertyObj) {
		this.propertyObj = propertyObj;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return TITLE_NAME[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return getPropertyList().get(rowIndex).getName();
		}
		else if (columnIndex == 1) {
			return getPropertyList().get(rowIndex).getDisplayValue();
		}
		return null;
	}

	/**
	 * @return
	 */
	public List<IProperty> getPropertyList() {
		if (propertyObj == null) {
			return null;
		}
		return propertyObj.getProperties();
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 1) {
			setPropertyValue(row, aValue);
		}
	}

	/**
	 * 获得属性值
	 * 
	 * @param propertyName 属性名
	 * @return
	 */
	public IProperty getProperty(int index) {
		return getPropertyList().get(index);
	}

	/**
	 * 设置属性值
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 */
	public <T> void setPropertyValue(int index, T value) {
		IProperty p = getPropertyList().get(index);
		p.setValue(value);
		propertyObj.setProperty(index, p);
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}

		if (!getPropertyList().get(rowIndex).isEditable()) {
			return false;
		}

		return true;
	}

	@Override
	public int getColumnCount() {
		return TITLE_NAME.length;
	}

	@Override
	public int getRowCount() {
		if (getPropertyList() == null) {
			return 0;
		}
		return getPropertyList().size();
	}
}