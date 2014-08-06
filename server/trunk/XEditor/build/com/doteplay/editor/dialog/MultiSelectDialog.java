/**
 * 
 */
package com.doteplay.editor.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.doteplay.editor.util.Util;

public class MultiSelectDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private DefaultListModel objectListModel;
	private int result=JOptionPane.CANCEL_OPTION;
	private Object[] resultObjects=null;
	
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JList objectList = null;
	private JButton okButton = null;
	private JButton cancelButton = null;

	/**
	 * @param owner
	 */
	public MultiSelectDialog(Window owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(320, 341);
		this.setPreferredSize(new Dimension(320, 341));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setTitle("多项选择框");
		this.setContentPane(getJContentPane());
	}

	private void init(Vector objs,Vector initObjs)
	{
		int[] indices=null;
		if(initObjs!=null)
			indices=new int[initObjs.size()];
		int size=objs.size();
		int j=0;
		Object object;
		for(int i=0;i<size;i++)
		{
			object=objs.get(i);
			objectListModel.addElement(object);
			if(initObjs!=null && initObjs.contains(object))
			{
				indices[j++]=i;
			}
		}
		if(initObjs!=null)
			objectList.setSelectedIndices(indices);
	}
	
	public Object[] getResultObjectList()
	{
		return resultObjects;
	}
	/**
	 * 显示多项选择框
	 * @param owner
	 * @param objs
	 * @param initObjs
	 * @return
	 */
	public static Object[] showMultiSelectDialog(Window owner,Vector objs,Vector initObjs)
	{
		MultiSelectDialog dialog=new MultiSelectDialog(owner);
		
		dialog.init(objs, initObjs);
		
		Util.showCenteredDialog(dialog, owner);

		if(dialog.result==JOptionPane.CANCEL_OPTION)
		{
			return null;
		}
		return dialog.objectList.getSelectedValues();
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setPreferredSize(new Dimension(0, 36));
			jPanel.add(getOkButton(), null);
			jPanel.add(getCancelButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getObjectList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes objectList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getObjectList() {
		if (objectList == null) {
			objectList = new JList();
			objectListModel=new DefaultListModel();
			objectList.setModel(objectListModel);
		}
		return objectList;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resultObjects=objectList.getSelectedValues();
					result=JOptionPane.OK_OPTION;
					dispose();
				}
			});
			okButton.setText("确定");
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
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					result=JOptionPane.CANCEL_OPTION;
					dispose();
				}
			});
			cancelButton.setText("取消");
		}
		return cancelButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
