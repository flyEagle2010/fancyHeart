/**
 * @package com.doteplay.editor.ui.property
 * @file StringProperty.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 */
public class StringProperty implements IProperty<String, StringPropertyEditor> {

	private String name;
	private String value;
	private boolean editable;

	public StringProperty(String name, String value, boolean editable) {
		this.setName(name);
		this.setValue(value);
		this.setEditable(editable);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public StringPropertyEditor getPropertyEditor() {
		return null;
	}

	@Override
	public String getDisplayValue() {
		return value;
	}

	@Override
	public void init(DataInputStream in) throws Exception {
		value = in.readUTF();
	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		out.writeUTF(value);
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {}

}
