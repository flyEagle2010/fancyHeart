/**
 * 
 */
package com.doteplay.editor.component;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableModel;

import com.doteplay.editor.common.InnerEditorPanel;

/**
 * @author Yang
 *
 */
public abstract class TableEditPanel extends InnerEditorPanel {

	private static final long serialVersionUID = 1L;
	private JToolBar jJToolBar = null;
	private JButton jButtonAdd = null;
	private JButton jButtonRemove = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton jButtonUp = null;
	private JButton jButtonDown = null;

	/**
	 * This is the default constructor
	 */
	public TableEditPanel() {
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
	protected JToolBar getJJToolBar() {
		if (jJToolBar == null) {
			jJToolBar = new JToolBar();
			jJToolBar.add(getJButtonAdd());
			jJToolBar.add(getJButtonRemove());
			jJToolBar.add(getJButtonUp());
			jJToolBar.add(getJButtonDown());
		}
		return jJToolBar;
	}

	public abstract void addItem();
	public abstract void removeItem();
	public abstract void upItem();
	public abstract void downItem();
	public abstract void clickedItem(java.awt.event.MouseEvent e);
	
	
	public void setTableModel(TableModel dataModel){
		jTable.setModel(dataModel);
	}
	
	public int getSelectTableRow(){
		return jTable.getSelectedRow();
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
					setModified(true);
					jTable.updateUI();
				}
			});
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonRemove	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRemove() {
		if (jButtonRemove == null) {
			jButtonRemove = new JButton();
			jButtonRemove.setIcon(new ImageIcon(getClass().getResource("/img/icon/delete.png")));
			jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					removeItem();
					setModified(true);
					jTable.updateUI();
				}
			});
		}
		return jButtonRemove;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	public JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					clickedItem(e);
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes jButtonUp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonUp() {
		if (jButtonUp == null) {
			jButtonUp = new JButton();
			jButtonUp.setIcon(new ImageIcon(getClass().getResource("/img/icon/moveup.png")));
			jButtonUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					upItem();
					setModified(true);
				}
			});
		}
		return jButtonUp;
	}

	/**
	 * This method initializes jButtonDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDown() {
		if (jButtonDown == null) {
			jButtonDown = new JButton();
			jButtonDown.setIcon(new ImageIcon(getClass().getResource("/img/icon/movedown.png")));
			jButtonDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					downItem();
					setModified(true);
				}
			});
		}
		return jButtonDown;
	}

}
