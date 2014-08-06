package com.doteplay.editor.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
/**
 * 实现列表框的对话框可以传入【Object[]对象】,<br>
 * 可以【筛选】列表内容<br>
 * 静态调用showInputDialog(Object[] obj)方法<br>
 * @author Azrael_guan 2010-11-12
 * 
 *
 */
public class SelectObjectDialogList extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JPanel jPanelNull = null;
	private JPanel jPanelBack = null;
	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	private JScrollPane jScrollPaneList = null;
	private JList jListBaseData = null;
	private DefaultListModel modelBaseData;
	private Vector<Object> vectorData;
	private Object obj = null;  //  @jve:decl-index=0:
	private JTextField jTextFieldSift = null;
	private JLabel jLabel = null;
	private static SelectObjectDialogList mdl;
	/**
	 * This method initializes 
	 * 
	 */
	public SelectObjectDialogList(Object[] obj) {
		super();
		modelBaseData = new DefaultListModel();
		vectorData = new Vector<Object>();
		for (int i = 0; i < obj.length; i++) {
			vectorData.addElement(obj[i]);
			modelBaseData.addElement(obj[i]);
		}
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(387, 287));
        this.setContentPane(getJPanel());
        this.setTitle("列表对话框");
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJPanelBack(), BorderLayout.SOUTH);
			jPanel.add(getJScrollPaneList(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("筛选的内容:");
			jToolBar = new JToolBar();
			jToolBar.add(jLabel);
			jToolBar.add(getJTextFieldSift());
			jToolBar.add(getJPanelNull());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanelNull	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelNull() {
		if (jPanelNull == null) {
			jPanelNull = new JPanel();
			jPanelNull.setLayout(new GridBagLayout());
		}
		return jPanelNull;
	}

	/**
	 * This method initializes jPanelBack	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBack() {
		if (jPanelBack == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(10, 50, 10, 50);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 10;
			gridBagConstraints1.ipady = 2;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(10, 50, 10, 50);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 10;
			gridBagConstraints.ipady = 2;
			gridBagConstraints.gridx = 0;
			jPanelBack = new JPanel();
			jPanelBack.setLayout(new GridBagLayout());
			jPanelBack.add(getJButtonOk(), gridBagConstraints);
			jPanelBack.add(getJButtonCancel(), gridBagConstraints1);
		}
		return jPanelBack;
	}

	/**
	 * This method initializes jButtonOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton();
			jButtonOk.setText("确定");
			jButtonOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jListBaseData.getSelectedIndex() == -1)return;
					setObj(modelBaseData.elementAt(jListBaseData.getSelectedIndex()));
					setVisible(false);
				}
			});
		}
		return jButtonOk;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText("取消");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setObj(null);
					setVisible(false);
				}
			});
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jScrollPaneList	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneList() {
		if (jScrollPaneList == null) {
			jScrollPaneList = new JScrollPane();
			jScrollPaneList.setViewportView(getJListBaseData());
		}
		return jScrollPaneList;
	}

	/**
	 * This method initializes jListBaseData	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListBaseData() {
		if (jListBaseData == null) {
			jListBaseData = new JList();
			jListBaseData.setModel(modelBaseData);
			jListBaseData.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(e.getClickCount() == 2) {
						if(jListBaseData.getSelectedIndex() == -1)return;
						setObj(modelBaseData.elementAt(jListBaseData.getSelectedIndex()));
						jListBaseData.setSelectedIndex(-1);
						setVisible(false);
					}
				}
			});
		}
		return jListBaseData;
	}

	private Object getObj() {
		return obj;
	}

	private void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * This method initializes jTextFieldSift	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldSift() {
		if (jTextFieldSift == null) {
			jTextFieldSift = new JTextField();
			jTextFieldSift.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					siftList();
				}
			});
			jTextFieldSift.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					siftList();
				}
			});
		}
		return jTextFieldSift;
	}
	/**
	 * 筛选出来列表
	 */
	private void siftList() {
		String s = jTextFieldSift.getText();
		modelBaseData.removeAllElements();
		if(!s.equals("")) {
			for (int i = 0; i < vectorData.size(); i++) {
				if(vectorData.elementAt(i).toString().indexOf(s) != -1) {
					modelBaseData.addElement(vectorData.elementAt(i));
				}
			}
		}else{
			for (int i = 0; i < vectorData.size(); i++) {
				modelBaseData.addElement(vectorData.elementAt(i));
			}
		}
	}
	public static Object showInputDialog(Object[] obj){
		mdl = new SelectObjectDialogList(obj);
		return mdl.getObj();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
