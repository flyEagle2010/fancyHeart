package com.doteplay.editor.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class SelectMultipleDatasDialog extends JDialog{

	private JPanel contentPanel = null;
	private JScrollPane centerScrollPane = null;
	private JPanel topPanel = null;
	private JLabel tipsLabel = null;
	private JList centerList = null;
	private JPanel buttomPanel = null;  //  @jve:decl-index=0:visual-constraint="594,366"
	private JButton yesButton = null;
	private JButton noButton = null;
	private boolean inited =  false;
	private DefaultListModel centerListModel =  null;
	private boolean isOk =  false;
	
	
	public void initData(String message, Collection objList,
			Object initialSelectionValue){
		inited =  false;
		centerListModel.removeAllElements();
		Object obj = null;
		for(Iterator itr = objList.iterator(); itr.hasNext();) {
			obj = itr.next(); 
			centerListModel.addElement(obj);
		}
		centerList.repaint();
		if(initialSelectionValue != null){
			centerList.setSelectedValue(initialSelectionValue, true);
		}
		if(message != null && !message.isEmpty()){
			tipsLabel.setText(message);
		}
		
		inited =  true;
		
	}
	

	/**
	 * This method initializes 
	 * 
	 */
	public SelectMultipleDatasDialog(JFrame frame) {
		super(frame);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(335, 414));
        this.setLocation(new Point(600, 200));
        this.setContentPane(getContentPanel());
        this.setTitle("选择多个对象");
			
	}

	/**
	 * This method initializes contentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getCenterScrollPane(), BorderLayout.CENTER);
			contentPanel.add(getTopPanel(), BorderLayout.NORTH);
			contentPanel.add(getButtomPanel(), BorderLayout.SOUTH);
			
		}
		return contentPanel;
	}

	/**
	 * This method initializes centerScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getCenterScrollPane() {
		if (centerScrollPane == null) {
			centerScrollPane = new JScrollPane();
			centerScrollPane.setViewportView(getCenterList());
		}
		return centerScrollPane;
	}

	/**
	 * This method initializes topPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTopPanel() {
		if (topPanel == null) {
			tipsLabel = new JLabel();
			tipsLabel.setText("按住Shift或者Ctrl选择多条记录");
			tipsLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			tipsLabel.setHorizontalAlignment(SwingConstants.CENTER);
			tipsLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
			tipsLabel.setForeground(new Color(117, 170, 169));
			tipsLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			topPanel.setPreferredSize(new Dimension(100,28));
			topPanel.add(tipsLabel, BorderLayout.CENTER);
		}
		return topPanel;
	}

	/**
	 * This method initializes centerList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getCenterList() {
		if (centerList == null) {
			centerList = new JList();
			centerListModel = new DefaultListModel();
			centerList.setModel(centerListModel);
		}
		return centerList;
	}

	/**
	 * This method initializes buttomPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtomPanel() {
		if (buttomPanel == null) {
			buttomPanel = new JPanel();
			buttomPanel.setPreferredSize(new Dimension(100,40));
			buttomPanel.setLayout(new FlowLayout());
			buttomPanel.add(getYesButton(), null);
			buttomPanel.add(getNoButton(), null);
		}
		return buttomPanel;
	}

	/**
	 * This method initializes yesButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getYesButton() {
		if (yesButton == null) {
			yesButton = new JButton();
			yesButton.setHorizontalAlignment(SwingConstants.LEFT);
			yesButton.setText("确定");
			yesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!inited){
						return;
					}
					isOk =  true;
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
			noButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!inited){
						return;
					}
					isOk = false;
					dispose();
				}
			});
		}
		return noButton;
	}
	
	public int[] getDatas(){
		if(!isOk){
			return null;
		}
		return centerList.getSelectedIndices();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
