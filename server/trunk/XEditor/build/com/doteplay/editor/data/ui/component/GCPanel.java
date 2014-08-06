/**
 * @package com.doteplay.editor.ui.component
 * @file PanelComponent.java
 */
package com.doteplay.editor.data.ui.component;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.doteyplay.game.editor.UIType;

/**
 */
public class GCPanel extends GComponent{

	/**serialVersionUID*/
	private static final long serialVersionUID = -6934743915975295082L;

	public GCPanel() {
	}

	@Override
	public UIType getType() {
		return UIType.Panel;
	}

	@Override
	public void draw(Graphics2D g, int ox, int oy) {
		super.draw(g, ox, oy);
	}
	
	@Override
	public void init(DataInputStream in) throws Exception {
		super.init(in);
	}

	@Override
	public void save(DataOutputStream out) throws Exception {
		super.save(out);
	}

	@Override
	public void saveClient(DataOutputStream out) throws Exception {}

	@Override
	public void saveServer(DataOutputStream out) throws Exception {}

	@Override
	public GCPanel clone() {
		GCPanel c = new GCPanel();
		c.id = this.id;
		c.name = this.name;
		c.x = this.x;
		c.y = this.y;
		c.width = this.width;
		c.height = this.height;
		c.backGroundColor = this.backGroundColor;
		c.foreGroundColor = this.foreGroundColor;
		c.visible = this.visible;
		return c;
	}

}
