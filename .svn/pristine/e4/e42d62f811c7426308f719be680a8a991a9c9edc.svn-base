package com.doteplay.editor.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.doteplay.editor.XEditor;
import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;

public class OpenDataEditorDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JComboBox groupTypeComboBox = null;
	private JLabel jLabel1 = null;
	private JTextField dataIdTextField = null;
	private JButton openButton = null;
	private OpenDataEditorDialog thisDialog;
	/**
	 * @param owner
	 */
	public OpenDataEditorDialog(Frame owner) {
		super(owner);
		thisDialog = this;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(293, 124);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setVgap(2);
			flowLayout.setHgap(2);
			jLabel1 = new JLabel();
			jLabel1.setText("     数据ID：");
			jLabel = new JLabel();
			jLabel.setText("选择类型：");
			jContentPane = new JPanel();
			jContentPane.setLayout(flowLayout);
			jContentPane.add(jLabel, null);
			jContentPane.add(getGroupTypeComboBox(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getDataIdTextField(), null);
			jContentPane.add(getOkButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes groupTypeComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getGroupTypeComboBox() {
		if (groupTypeComboBox == null) {
			groupTypeComboBox = new JComboBox();
			groupTypeComboBox.setPreferredSize(new Dimension(200, 25));
			
			int size=DataManager.dataGroupList.size();
			for(int i=0;i<size;i++){
				DataGroup dg=DataManager.dataGroupList.get(i);
				groupTypeComboBox.addItem(dg.groupName);
			}
			
		}
		return groupTypeComboBox;
	}

	/**
	 * This method initializes dataIdTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getDataIdTextField() {
		if (dataIdTextField == null) {
			dataIdTextField = new JTextField();
			dataIdTextField.setPreferredSize(new Dimension(200, 22));
		}
		return dataIdTextField;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setText("打开");
			openButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int selIndex = groupTypeComboBox.getSelectedIndex();
					DataGroup dg=DataManager.dataGroupList.get(selIndex);
					BaseData data = dg.getData(dataIdTextField.getText().trim());
					if(data == null){
						return;
					}else{
						XEditor.xEditor.openDataEditor(data);
					}
				}
			});
		}
		return openButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
