/**
 * @package com.doteplay.editor.component
 * @file ShowImageDialog.java
 */
package com.doteplay.editor.component;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.doteplay.editor.common.EditorPanel;
import com.doteplay.editor.data.common.TimeData;

/**
 */
public class TimeEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private TimeEditPanel timePanel = null;
	/**
	 * @param owner
	 */
	public TimeEditDialog(Frame owner) {
		super(owner);
		initialize();
	}

	public void init(EditorPanel editorPanel, TimeData timeData) {
		timePanel.init(editorPanel);
		timePanel.initData(timeData);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(234, 90);
		this.setModal(true);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTimePanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes timePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private TimeEditPanel getTimePanel() {
		if (timePanel == null) {
			timePanel = new TimeEditPanel();
		}
		return timePanel;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
