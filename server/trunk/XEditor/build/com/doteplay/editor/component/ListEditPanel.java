/**
 * 
 */
package com.doteplay.editor.component;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import com.doteplay.editor.common.InnerEditorPanel;

/**
 * @author Yang
 *
 */
public abstract class ListEditPanel extends InnerEditorPanel {

	private static final long serialVersionUID = 1L;
	private JToolBar jJToolBar = null;
	private JButton jButtonAdd = null;
	private JButton jButtonDel = null;
	private JScrollPane jScrollPane = null;
	private JList jList = null;
	/**
	 * This is the default constructor
	 */
	public ListEditPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getJJToolBar(), BorderLayout.NORTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jJToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBar() {
		if (jJToolBar == null) {
			jJToolBar = new JToolBar();
			jJToolBar.add(getJButtonAdd());
			jJToolBar.add(getJButtonDel());
		}
		return jJToolBar;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		jJToolBar.setEnabled(enabled);
		jButtonAdd.setEnabled(enabled);
		jButtonDel.setEnabled(enabled);
		jScrollPane.setEnabled(enabled);
		jList.setEnabled(enabled);
	}
	
	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setIcon(new ImageIcon(getClass().getResource("/img/icon/new.png")));
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addItem();
				}
			});
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDel() {
		if (jButtonDel == null) {
			jButtonDel = new JButton();
			jButtonDel.setIcon(new ImageIcon(getClass().getResource("/img/icon/delete.png")));
			jButtonDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					removeItem();
				}
			});
		}
		return jButtonDel;
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
	public JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					onSelectItem(e);
				}
			});
		}
		return jList;
	}

	public abstract void addItem();
	public abstract void removeItem();
	public abstract void onSelectItem(javax.swing.event.ListSelectionEvent e);
	
	public void setModel(ListModel model){
		jList.setModel(model);
	}
	
	public int getSelectIndex(){
		return jList.getSelectedIndex();
	}
	
	public int[] getSelectedIndices(){
		return jList.getSelectedIndices();
	}
}
