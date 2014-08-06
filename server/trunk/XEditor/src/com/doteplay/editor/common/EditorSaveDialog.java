package com.doteplay.editor.common;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditorSaveDialog extends JDialog {

	public int result=JOptionPane.CANCEL_OPTION;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel idLabel = null;
	private JLabel nameLabel = null;
	private JTextField idTextField = null;
	private JTextField nameTextField = null;
	private JButton okButton = null;
	private JButton cancelButton = null;

	public EditorSaveDialog(Frame arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(189, 124);
		this.setTitle("保存数据对话框");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(3, 2, 8, 48);
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.ipadx = 1;
			gridBagConstraints5.ipady = -3;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(3, 8, 8, 1);
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.ipadx = 1;
			gridBagConstraints4.ipady = -3;
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 97;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(3, 2, 3, 8);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 97;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(8, 2, 3, 8);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(3, 8, 7, 1);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.ipadx = 6;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(8, 8, 7, 1);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 6;
			gridBagConstraints.gridx = 0;
			nameLabel = new JLabel();
			nameLabel.setText("数据名称:");
			idLabel = new JLabel();
			idLabel.setText("数据编号:");
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(idLabel, gridBagConstraints);
			jContentPane.add(nameLabel, gridBagConstraints1);
			jContentPane.add(getIdTextField(), gridBagConstraints2);
			jContentPane.add(getNameTextField(), gridBagConstraints3);
			jContentPane.add(getOkButton(), gridBagConstraints4);
			jContentPane.add(getCancelButton(), gridBagConstraints5);
		}
		return jContentPane;
	}

	/**
	 * This method initializes idTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getIdTextField() {
		if (idTextField == null) {
			idTextField = new JTextField();
		}
		return idTextField;
	}

	/**
	 * This method initializes nameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
		}
		return nameTextField;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("确定");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					result=JOptionPane.OK_OPTION;
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
			cancelButton.setText("取消");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return cancelButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
