/**
 * 
 */
package com.doteplay.editor.component.PropertiesTable;

import com.doteplay.editor.common.ISavebleData;

/**
 * @author Yang
 */
public interface IProperty<T, E extends IPropertyEditor> extends ISavebleData {

	void setName(String name);

	String getName();

	T getValue();

	void setValue(T value);

	String getDisplayValue();

	boolean isEditable();

	void setEditable(boolean editable);

	E getPropertyEditor();
}
