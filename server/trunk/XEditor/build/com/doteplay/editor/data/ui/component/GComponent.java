/**
 * @package com.doteplay.editor.ui.component
 * @file AbstractGameComponent.java
 */
package com.doteplay.editor.data.ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.doteyplay.game.editor.UIType;
import com.doteplay.editor.common.ISavebleData;
import com.doteplay.editor.component.PropertiesTable.BooleanProperty;
import com.doteplay.editor.component.PropertiesTable.BoundsProperty;
import com.doteplay.editor.component.PropertiesTable.ColorProperty;
import com.doteplay.editor.component.PropertiesTable.IProperty;
import com.doteplay.editor.component.PropertiesTable.IPropertyObject;
import com.doteplay.editor.component.PropertiesTable.StringProperty;

/**
 * 抽象游戏组件
 * 
 */
public abstract class GComponent implements IPropertyObject, Serializable, ISavebleData {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected int id;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 控件相对位置
	 */
	protected int x = 0, y = 0;

	/**
	 * 控件大小
	 */
	protected int width = 1, height = 1;

	/**
	 * 前景色,背景色
	 */
	protected int foreGroundColor, backGroundColor;

	/**
	 * 是否可见
	 */
	protected boolean visible = true;

	public GComponent() {

	}

	public abstract UIType getType();

	@Override
	public String toString() {
		// String s = this.getType() + "[" + x + "," + y + "|" + width + "," +
		// height + "]";
		String s = this.getName();
		return s;
	}

	public void draw(Graphics2D g, int sx, int sy) {
		g.setColor(new Color(backGroundColor));
		g.fillRect(sx, sy, width, height);
		g.setColor(new Color(foreGroundColor));
		g.setFont(new Font(null, Font.PLAIN, 9));
		g.drawString(this.getName(), sx, sy);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置位置、大小
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void setBounds(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public Point getPos() {
		return new Point(x, y);
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void setPos(Point p) {
		setPos(p.x, p.y);
	}

	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void init(DataInputStream in) throws Exception {
		id = in.readInt();
		name = in.readUTF();
		x = in.readInt();
		y = in.readInt();
		width = in.readInt();
		height = in.readInt();
		foreGroundColor = in.readInt();
		backGroundColor = in.readInt();
		visible = in.readBoolean();
	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		out.writeInt(id);
		out.writeUTF(name);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(width);
		out.writeInt(height);
		out.writeInt(foreGroundColor);
		out.writeInt(backGroundColor);
		out.writeBoolean(visible);
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {}

	@Override
	public List<IProperty> getProperties() {
		List<IProperty> list = new ArrayList<IProperty>();
		list.add(new StringProperty("ID", String.valueOf(id), false));
		list.add(new StringProperty("名称", name, true));
		list.add(new BoundsProperty("位置大小", new Rectangle(x, y, width, height), false));
		list.add(new ColorProperty("前景颜色", new Color(foreGroundColor), false));
		list.add(new ColorProperty("背景颜色", new Color(backGroundColor), false));
		list.add(new BooleanProperty("是否可见", visible, false));
		return list;
	}

	@Override
	public void setProperties(List<IProperty> propertylist) {

		name = (String) propertylist.get(1).getValue();

		Rectangle r = (Rectangle) propertylist.get(2).getValue();
		this.setBounds(r);

		Color c;
		c = (Color) propertylist.get(3).getValue();
		foreGroundColor = c.getRGB();

		c = (Color) propertylist.get(4).getValue();
		backGroundColor = c.getRGB();

		visible = (Boolean) propertylist.get(5).getValue();
	}

	@Override
	public void setProperty(int index, IProperty p) {
		String s;
		Color c;
		switch (index) {
		case 1:
			name = (String) p.getValue();
			break;
		case 2:
			Rectangle r = (Rectangle) p.getValue();
			this.setBounds(r);
			break;
		case 3:
			c = (Color) p.getValue();
			foreGroundColor = c.getRGB();
			break;
		case 4:
			c = (Color) p.getValue();
			backGroundColor = c.getRGB();
			break;
		case 5:
			visible = (Boolean) p.getValue();
			break;
		}
	}
	
	@Override
	public abstract GComponent clone();
}
