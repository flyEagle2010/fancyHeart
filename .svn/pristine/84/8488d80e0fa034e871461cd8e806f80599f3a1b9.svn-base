/**
 * @package com.doteplay.editor.common
 * @file ListValueEditPanel.java
 */
package com.doteplay.editor.component;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.doteplay.editor.common.InnerEditorPanel;

public abstract class ListValueEditPanel extends InnerEditorPanel {

	private static final long serialVersionUID = 1L;
	private JToolBar jToolBar = null;
	protected JButton addButton = null;
	protected JButton delButton = null;
	private JSplitPane jSplitPane = null;
	private JPanel leftPanel = null;
	private JPanel rightPanel = null;
	private JScrollPane jScrollPane = null;
	private JList jList = null;
	protected DefaultListModel listModel = new DefaultListModel();
	private ListValueEditPanel thisPanel;

	/**
	 * This is the default constructor
	 */
	public ListValueEditPanel() {
		super();
		thisPanel = this;
		initialize();
	}
	
	/**
	 * 当添加时触发
	 */
	public abstract void onAddItem();
	
	/**
	 * 当删除时触发
	 */
	public abstract void onDelItem();
	
	/**
	 * 当选择时触发
	 * @param e
	 */
	public abstract void onItemSelected(javax.swing.event.ListSelectionEvent e);
	
	/**
	 * 
	 * @return
	 */
	public int getSelectedItemindex(){
		return jList.getSelectedIndex();
	}
	
	/**
	 * 添加编辑窗口
	 * @param p
	 */
	public synchronized void addEditPanel(JPanel p){
		rightPanel.removeAll();
		rightPanel.add(p);
	}
	
	/**
	 * 移除编辑窗口
	 * @param locationObjectEditPanel
	 */
	public void removeEditPanel(){
		rightPanel.removeAll();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(480, 312);
		this.setLayout(new BorderLayout());
		this.add(getJSplitPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getAddButton());
			jToolBar.add(getDelButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/new.png")));
			addButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onAddItem();
					setModified(true);
					thisPanel.updateUI();
				}
			});
		}
		return addButton;
	}

	/**
	 * This method initializes delButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDelButton() {
		if (delButton == null) {
			delButton = new JButton();
			delButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/delete.png")));
			delButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					onDelItem();
					setModified(true);
					thisPanel.updateUI();
				}
			});
		}
		return delButton;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getLeftPanel());
			jSplitPane.setRightComponent(getRightPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes leftPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new JPanel();
			leftPanel.setLayout(new BorderLayout());
			leftPanel.setPreferredSize(new Dimension(150, 0));
			leftPanel.add(getJToolBar(), BorderLayout.NORTH);
			leftPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return leftPanel;
	}

	/**
	 * This method initializes rightPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getRightPanel() {
		if (rightPanel == null) {
			rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout());
		}
		return rightPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setModel(listModel);
			jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if(e.getValueIsAdjusting() == true){
						onItemSelected(e);
						thisPanel.updateUI();
					}
					
				}
			});
		}
		return jList;
	}

//	public DefaultListModel getListModel() {
//		return this.listModel;
//	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
