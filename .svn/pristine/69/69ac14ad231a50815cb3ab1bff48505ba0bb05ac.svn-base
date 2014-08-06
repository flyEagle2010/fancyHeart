package com.doteplay.editor.tools.tageditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TagEditorDialog extends JDialog{
	private static final long serialVersionUID = -8881299666565547682L;
	private JPanel totalPanel = null;
	private JPanel downPanel = null;
	private JButton yesButton = null;
	private JButton noButton = null;
	public int resultFlag =  JOptionPane.CANCEL_OPTION;
	public TagEditor panel = null;
	private String result =  "";  //  @jve:decl-index=0:

	public String getResult() {
		return result;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public TagEditorDialog(JFrame jframe) {
		super(jframe);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(575, 508));
        this.setContentPane(getTotalPanel());
        this.setTitle("Tag编辑器");
			
	}

	/**
	 * This method initializes totalPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTotalPanel() {
		if (totalPanel == null) {
			totalPanel = new JPanel();
			totalPanel.setLayout(new BorderLayout());
			panel = new TagEditor("",true);
			totalPanel.add(panel,BorderLayout.CENTER);
			totalPanel.add(getDownPanel(), BorderLayout.SOUTH);
			
		}
		return totalPanel;
	}

	/**
	 * This method initializes downPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDownPanel() {
		if (downPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(3, 8, 5, 82);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 1;
			gridBagConstraints1.ipady = -3;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(3, 85, 5, 7);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 1;
			gridBagConstraints.ipady = -3;
			gridBagConstraints.gridx = 0;
			downPanel = new JPanel();
			downPanel.setLayout(new GridBagLayout());
			downPanel.setPreferredSize(new Dimension(5,33));
			downPanel.add(getYesButton(), gridBagConstraints);
			downPanel.add(getNoButton(), gridBagConstraints1);
		}
		return downPanel;
	}

	/**
	 * This method initializes yesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getYesButton() {
		if (yesButton == null) {
			yesButton = new JButton();
			yesButton.setText("确认");
			yesButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					resultFlag = JOptionPane.OK_OPTION;
					String content = panel.content;
					setResult(content);
					dispose();
				}
				
			});
		}
		return yesButton;
	}

	/**
	 * This method initializes noButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNoButton() {
		if (noButton == null) {
			noButton = new JButton();
			noButton.setText("取消");
			noButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
				
			});
		}
		return noButton;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
