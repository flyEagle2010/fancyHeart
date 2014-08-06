/**
 * @package com.doteplay.editor.tools
 * @file ToolsMenu.java
 */
package com.doteplay.editor.menu;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.doteplay.editor.XEditor;
import com.doteplay.editor.tools.tageditor.TagEditorDialog;

public class ToolsMenu extends JMenu {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected XEditor xEditor;
	private JMenuItem psdTestMenuItem = null;
	private JMenuItem databaseToolMenuItem = null;
	private JMenuItem jMenuItemDataSync = null;
	private JMenuItem tagEditorMenuItem = null;
	private JMenuItem jmi_HtmlTools = null;
	private JMenuItem jMenuItemFrameListTool = null;
	private JMenuItem jMenuItemSpriteSearchTool = null;
	
	public ToolsMenu(XEditor xEditor) {
		this.xEditor = xEditor;
		this.initialize();
	}

	private void initialize() {
		this.setText("¹¤¾ß");
		this.add(getTagEditorMenuItem());
	}


	/**
	 * This method initializes tagEditorMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getTagEditorMenuItem() {
		if (tagEditorMenuItem == null) {
			tagEditorMenuItem = new JMenuItem();
			tagEditorMenuItem.setText("Tag±à¼­Æ÷");
			tagEditorMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TagEditorDialog dialog = new TagEditorDialog(xEditor.getJFrame());

					Dimension d = dialog.getSize();
					Point loc = xEditor.getJFrame().getLocationOnScreen();
					loc.translate(212, 278 / 3);
					dialog.setLocation(loc);

					dialog.setModal(true);
					dialog.setVisible(true);
					if (dialog.resultFlag == JOptionPane.OK_OPTION) {

						System.out.println(dialog.getResult());

					}
				}
			});

		}
		return tagEditorMenuItem;
	}



}
