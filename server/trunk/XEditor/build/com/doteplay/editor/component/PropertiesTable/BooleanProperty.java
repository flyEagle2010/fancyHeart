/**
 * @package com.doteplay.editor.ui.property
 * @file StringProperty.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 */
public class BooleanProperty implements IProperty<Boolean,BooleanPropertyEditor> {

	private String name;
	protected Boolean value;
	private boolean editable;
	private BooleanPropertyEditor editor;
	
	public BooleanProperty(String name, Boolean value, boolean editable) {
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
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getValue() {
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
	public BooleanPropertyEditor getPropertyEditor() {
		if(editor == null){
			editor = new BooleanPropertyEditor();
			editor.setEditProperty(this);
		}
		return editor;
	}

	@Override
	public String getDisplayValue() {
		return value == true ? "ÊÇ" : "·ñ";
	}

	@Override
	public void init(DataInputStream in) throws Exception {
		name = in.readUTF();
		editable = in.readBoolean();
		value = in.readBoolean();
	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		out.writeUTF(name);
		out.writeBoolean(editable);
		out.writeBoolean(value);
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {}
}
