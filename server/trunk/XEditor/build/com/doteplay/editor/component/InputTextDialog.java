/**
 * @package com.doteplay.editor.component
 * @file ShowImageDialog.java
 */
package com.doteplay.editor.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.doteplay.editor.util.Util;

/**
 */
public class InputTextDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private InputTextDialog thisDialog;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;
	private JPanel jPanel = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private String inputText = "";
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;

	/**
	 * @param owner
	 */
	public InputTextDialog(Frame owner, String title, String text) {
		super(owner);
		thisDialog = this;
		initialize();
		initData(title, text);
	}

	private void initData(String title, String text) {
		this.inputText = text;
		jTextArea.setText(this.inputText);
		jLabel.setText(title);
	}

	public static String showDialog(Frame owner, String title, String inputText) {
		InputTextDialog dialog = new InputTextDialog(owner, title, inputText);
		dialog.setModal(true);
		Util.showCenteredDialog(dialog, owner);
		return dialog.getInputText();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(397, 303));
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setAutoscrolls(true);
		}
		return jTextArea;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(0, 30));
			jPanel.add(getOkButton(), null);
			jPanel.add(getCancelButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes okButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setBounds(new Rectangle(205, 5, 86, 21));
			okButton.setText("确定");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					inputText = jTextArea.getText();
					thisDialog.dispose();
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(295, 5, 81, 21));
			cancelButton.setText("取消");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thisDialog.dispose();
				}
			});
		}
		return cancelButton;
	}

	public String getInputText() {
		return this.inputText;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setText("");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setPreferredSize(new Dimension(0, 40));
			jPanel1.add(jLabel, BorderLayout.CENTER);
		}
		return jPanel1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
