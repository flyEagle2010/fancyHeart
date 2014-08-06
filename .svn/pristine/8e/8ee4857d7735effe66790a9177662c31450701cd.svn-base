/**
 * @package com.doteplay.editor.ui.component
 * @file ComponentFactory.java
 */
package com.doteplay.editor.data.ui.component;

import com.doteyplay.game.editor.UIType;


/**
 */
public class ComponentFactory {

	public static GComponent create(UIType type) {
		if (type == UIType.Panel) {
			return new GCPanel();
		}
		else if (type == UIType.HelpPoint) {
			return new GCHelpPoint();
		}
		return null;
	}
}
