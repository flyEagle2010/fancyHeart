/**
 * @package com.doteplay.editor.ui.property
 * @file BoundsPropertyEditor.java
 */
package com.doteplay.editor.component.PropertiesTable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.doteplay.editor.util.Util;

/**
 */
public class BoundsPropertyEditor extends JDialog implements IPropertyEditor<BoundsProperty>{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel xLabel = null;
	private JLabel yLabel = null;
	protected JTextField xTextField = null;
	protected JTextField yTextField = null;
	private JLabel wLabel = null;
	private JLabel hLabel = null;
	protected JTextField wTextField = null;
	protected JTextField hTextField = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	
	protected BoundsProperty property;
	protected BoundsPropertyEditor thisDialog;
	/**
	 * @param owner
	 */
	public BoundsPropertyEditor(Frame owner) {
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
		this.setSize(247, 112);
		this.setModal(true);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(257, 112));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			hLabel = new JLabel();
			hLabel.setBounds(new Rectangle(124, 32, 34, 21));
			hLabel.setText("高度:");
			wLabel = new JLabel();
			wLabel.setBounds(new Rectangle(124, 4, 35, 21));
			wLabel.setText("宽度:");
			yLabel = new JLabel();
			yLabel.setBounds(new Rectangle(4, 32, 45, 21));
			yLabel.setText("Y 坐标:");
			xLabel = new JLabel();
			xLabel.setBounds(new Rectangle(4, 4, 45, 21));
			xLabel.setText("X坐标:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(xLabel, null);
			jContentPane.add(yLabel, null);
			jContentPane.add(getXTextField(), null);
			jContentPane.add(getYTextField(), null);
			jContentPane.add(wLabel, null);
			jContentPane.add(hLabel, null);
			jContentPane.add(getWTextField(), null);
			jContentPane.add(getHTextField(), null);
			jContentPane.add(getOkButton(), null);
			jContentPane.add(getCancelButton(), null);
		}
		return jContentPane;
	}

	@Override
	public void show(Component c) {
		Util.showCenteredDialog(this, c);
	}

	@Override
	public void setEditProperty(BoundsProperty p) {
		property = p;
		xTextField.setText(String.valueOf(p.getValue().x));
		yTextField.setText(String.valueOf(p.getValue().y));
		wTextField.setText(String.valueOf(p.getValue().width));
		hTextField.setText(String.valueOf(p.getValue().height));
	}

	/**
	 * This method initializes xTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getXTextField() {
		if (xTextField == null) {
			xTextField = new JTextField();
			xTextField.setText("0");
			xTextField.setBounds(new Rectangle(52, 4, 69, 21));
		}
		return xTextField;
	}

	/**
	 * This method initializes yTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getYTextField() {
		if (yTextField == null) {
			yTextField = new JTextField();
			yTextField.setText("0");
			yTextField.setBounds(new Rectangle(52, 32, 69, 21));
		}
		return yTextField;
	}

	/**
	 * This method initializes wTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getWTextField() {
		if (wTextField == null) {
			wTextField = new JTextField();
			wTextField.setText("0");
			wTextField.setBounds(new Rectangle(161, 4, 77, 21));
		}
		return wTextField;
	}

	/**
	 * This method initializes hTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHTextField() {
		if (hTextField == null) {
			hTextField = new JTextField();
			hTextField.setText("0");
			hTextField.setBounds(new Rectangle(161, 32, 77, 21));
		}
		return hTextField;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setBounds(new Rectangle(107, 56, 61, 25));
			okButton.setText("确定");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int x = Util.String2Int(xTextField.getText().trim());
					int y = Util.String2Int(yTextField.getText().trim());
					int w = Util.String2Int(wTextField.getText().trim());
					int h = Util.String2Int(hTextField.getText().trim());
					property.setValue(new Rectangle(x,y,w,h));
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
			cancelButton.setBounds(new Rectangle(170, 56, 65, 25));
			cancelButton.setText("取消");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					thisDialog.dispose();
				}
			});
		}
		return cancelButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
