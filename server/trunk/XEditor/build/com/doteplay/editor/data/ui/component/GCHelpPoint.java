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
public class GCHelpPoint extends GComponent {

	/** serialVersionUID */
	private static final long serialVersionUID = 2222152816184556235L;

	/**
	 * @param type
	 */
	public GCHelpPoint() {}

	@Override
	public void draw(Graphics2D g, int ox, int oy) {
		super.draw(g, ox, oy);
	}

	@Override
	public UIType getType() {
		return UIType.HelpPoint;
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
	public GCHelpPoint clone() {
		GCHelpPoint c = new GCHelpPoint();
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
