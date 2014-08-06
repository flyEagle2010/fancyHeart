package com.doteplay.editor.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public  class CheckDataResultDialog extends JDialog {


	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTextPane jTextPane = null;
	private JButton jButton1 = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel = null;
	/**
	 * @param owner
	 */
	public CheckDataResultDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
//		this.setSize(300, 200);
		 Dimension   winSize   =   Toolkit.getDefaultToolkit().getScreenSize();   //屏幕分辨率   
			this.setBounds((winSize.width)/4,  (winSize.height)/4, 320, 350);
		this.setTitle("提示信息");
		this.setVisible(true);
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
			jContentPane.setLayout(new BoxLayout(getJContentPane(), BoxLayout.Y_AXIS));
			jContentPane.add(getJPanel1(), null);
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
			jScrollPane.setName("jScrollPane");
			jScrollPane.setViewportView(getJTextPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}

	/**
	 * 
	 * 插入文本
	 * 
	 */
	public void insertInfos(String msg){
		Document docs=jTextPane.getDocument();//利用getDocument()方法取得JTextPane的Document instance.0
		SimpleAttributeSet attrset=new SimpleAttributeSet();
		StyleConstants.setForeground(attrset,Color.black);
		StyleConstants.setBold(attrset,true);
		try{
		    docs.insertString(docs.getLength(),msg,attrset);     
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("确认");
			jButton1.addActionListener(new ButtonActionListener(this));
			jButton1.setBounds(60, 28, this.getX(), this.getY());
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel1.add(getJPanel(), BorderLayout.SOUTH);
			jPanel1.add(getJPanel2(), BorderLayout.NORTH);
		}
		return jPanel1;
	}
	/**
	 * 关闭按钮实现方法
	 */
	class ButtonActionListener implements ActionListener{
		private Dialog dialog;
		ButtonActionListener(JDialog dialog){
			this.dialog = dialog;
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			this.dialog.setVisible(false);
			this.dialog =  null;
		}
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton1(), new GridBagConstraints());
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJLabel(), new GridBagConstraints());
		}
		return jPanel2;
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("检测信息结果");
		}
		return jLabel;
	}


}
