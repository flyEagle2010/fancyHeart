/**
 * @package com.doteplay.editor.ui.property
 * @file StringProperty.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 */
public class ColorProperty implements IProperty<Color, ColorPropertyEditor> {

	private String name;
	private Color value;
	private boolean editable;
	private ColorPropertyEditor editor;

	public ColorProperty(String name, Color value, boolean editable) {
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
	public void setValue(Color value) {
		this.value = value;
	}

	@Override
	public Color getValue() {
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
	public ColorPropertyEditor getPropertyEditor() {
		editor = new ColorPropertyEditor();
		editor.setEditProperty(this);
		return editor;
	}

	@Override
	public String getDisplayValue() {
		return "0x" + Integer.toHexString(value.getRGB());
	}

	@Override
	public void init(DataInputStream in) throws Exception {
		name = in.readUTF();
		editable = in.readBoolean();

		int rgb = in.readInt();
		value = new Color(rgb);
	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		out.writeUTF(name);
		out.writeBoolean(editable);
		out.writeInt(value.getRGB());
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {}

}
