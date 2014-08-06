/**
 * 
 */
package com.doteplay.editor.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.doteplay.editor.EditorConfig;

/**
 * @author dell
 *
 */
public class UpdateVersionDataDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private int result=JOptionPane.CANCEL_OPTION;
	private int sourceVersionType;
	private int targetVersionType;
	private boolean changeVersionData;
	
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JComboBox sourceVersionComboBox = null;
	private JComboBox targetVersionComboBox = null;
	private JButton okButton = null;
	private JCheckBox isChangeCheckBox = null;
	private JButton cancelButton = null;

	/**
	 * @param owner
	 */
	public UpdateVersionDataDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(276, 132);
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("更新版本数据对话框");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(4, 28, 61, 18));
			jLabel1.setText("目标版本:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 4, 61, 18));
			jLabel.setText("源版本:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setPreferredSize(new Dimension(276, 132));
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getSourceVersionComboBox(), null);
			jContentPane.add(getTargetVersionComboBox(), null);
			jContentPane.add(getOkButton(), null);
			jContentPane.add(getIsChangeCheckBox(), null);
			jContentPane.add(getCancelButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes sourceVersionComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSourceVersionComboBox() {
		if (sourceVersionComboBox == null) {
			sourceVersionComboBox = new JComboBox();
			sourceVersionComboBox.setBounds(new Rectangle(68, 4, 197, 21));
			
			for(int i=0;i<EditorConfig.VERSION_NAMES.length;i++)
			{
				sourceVersionComboBox.addItem(EditorConfig.VERSION_NAMES[i]);
			}
		}
		return sourceVersionComboBox;
	}

	/**
	 * This method initializes targetVersionComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTargetVersionComboBox() {
		if (targetVersionComboBox == null) {
			targetVersionComboBox = new JComboBox();
			targetVersionComboBox.setBounds(new Rectangle(68, 28, 197, 21));
			
			for(int i=0;i<EditorConfig.VERSION_NAMES.length;i++)
			{
				targetVersionComboBox.addItem(EditorConfig.VERSION_NAMES[i]);
			}
		}
		return targetVersionComboBox;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setBounds(new Rectangle(108, 80, 65, 21));
			okButton.setText("确定");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					sourceVersionType=sourceVersionComboBox.getSelectedIndex();
					targetVersionType=targetVersionComboBox.getSelectedIndex();
					changeVersionData=isChangeCheckBox.isSelected();
					
					result=JOptionPane.OK_OPTION;
					dispose();
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes isChangeCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getIsChangeCheckBox() {
		if (isChangeCheckBox == null) {
			isChangeCheckBox = new JCheckBox();
			isChangeCheckBox.setBounds(new Rectangle(68, 52, 197, 21));
			isChangeCheckBox.setText("转换数据版本");
		}
		return isChangeCheckBox;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(180, 80, 65, 21));
			cancelButton.setText("取消");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return cancelButton;
	}

	public int getResult() {
		return result;
	}

	public int getSourceVersionType() {
		return sourceVersionType;
	}

	public int getTargetVersionType() {
		return targetVersionType;
	}

	public boolean isChangeVersionData() {
		return changeVersionData;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
