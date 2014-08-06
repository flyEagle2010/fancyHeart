/**
 * @package com.doteplay.editor.component.PropertiesTable
 * @file ColorPropertyEditor.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;

import com.doteplay.editor.util.Util;

/**
 */
public class ColorPropertyEditor implements IPropertyEditor<ColorProperty>{
	
	private ColorProperty property;
	
	@Override
	public void show(Component c) {
		final JColorChooser panel = new JColorChooser(Color.white);
		ActionListener okListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				property.setValue(panel.getColor());
			}
		};
		
		JDialog dialog = JColorChooser.createDialog(null, "Ñ¡ÔñÑÕÉ«", true, panel, okListener, null);
		Util.showCenteredDialog(dialog, c);
	}

	@Override
	public void setEditProperty(ColorProperty p) {
		property = p;
	}
}
