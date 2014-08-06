/**
 * @package com.doteplay.editor.ui.property
 * @file StringProperty.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BoundsProperty implements IProperty<Rectangle,BoundsPropertyEditor> {

	private String name;
	private Rectangle value;
	private boolean editable;

	private BoundsPropertyEditor editor;

	public BoundsProperty(String name, Rectangle value, boolean editable) {
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
	public void setValue(Rectangle value) {
		this.value = value;
	}

	@Override
	public Rectangle getValue() {
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
	public BoundsPropertyEditor getPropertyEditor() {
		if(editor == null){
			editor = new BoundsPropertyEditor(null);
			editor.setEditProperty(this);
		}
		return editor;
	}

	@Override
	public String getDisplayValue() {
		return "x=" + value.x + ",y=" + value.y + ",width=" + value.width + ",height=" + value.height;
	}

	@Override
	public void init(DataInputStream in) throws Exception {
		name = in.readUTF();
		editable = in.readBoolean();

		int x = in.readInt();
		int y = in.readInt();
		int w = in.readInt();
		int h = in.readInt();
		value = new Rectangle(x, y, w, h);
	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		out.writeUTF(name);
		out.writeBoolean(editable);
		out.writeInt(value.x);
		out.writeInt(value.y);
		out.writeInt(value.width);
		out.writeInt(value.height);
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {}

}
