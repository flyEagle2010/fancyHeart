///**
// * 
// */
//package com.doteplay.editor.dialog;
//
//import java.awt.Frame;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.util.Vector;
//
//import javax.swing.DefaultListModel;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.WindowConstants;
//
//import com.doteplay.editor.common.BaseData;
//import com.doteplay.editor.common.DataManager;
//import com.doteplay.editor.data.type.TypeData;
//import com.doteplay.editor.data.type.TypeItemData;
//
//public class SelectDataDialog extends JDialog {
//
//	public TypeItemData nowDataType=null;  //  @jve:decl-index=0:
//	public Object[] datas=null;
//	private TypeData typeData;  //  @jve:decl-index=0:
//	private DefaultListModel dataListModel;
//	public int result=JOptionPane.CANCEL_OPTION;
//	
//	private static final long serialVersionUID = 1L;
//	private JPanel jContentPane = null;
//	private JLabel dataTypeLabel = null;
//	private JComboBox dataTypeComboBox = null;
//	private JScrollPane jScrollPane = null;
//	private JList dataList = null;
//	private JButton okButton = null;
//	private JButton cancelButton = null;
//
//	/**
//	 * @param owner
//	 */
//	public SelectDataDialog(Frame owner) {
//		super(owner);
//		initialize();
//		init();
//	}
//
//	/**
//	 * This method initializes this
//	 * 
//	 * @return void
//	 */
//	private void initialize() {
//		this.setSize(212, 258);
//		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		this.setTitle("选择数据");
//		this.setContentPane(getJContentPane());
//	}
//
//	private void init()
//	{
//		typeData=(TypeData)DataManager.getDataByName("type", "数据类型");
//		typeData.open();
//		int size=typeData.typeItemDataList.size();
//		for(int i=0;i<size;i++)
//		{
//			dataTypeComboBox.addItem(typeData.typeItemDataList.get(i));
//		}
//	}
//	public void release()
//	{
//		typeData.close();
//	}
//	public void setDataType(int dt,boolean changable)
//	{
//		dataTypeComboBox.setEnabled(changable);
////		typeData.typeItemDataList.get(dt);
//		_setDataType(typeData.typeItemDataList.get(dt));
//	}
//	private void _setDataType(TypeItemData tid)
//	{
//		if(nowDataType==tid)
//			return;
//		nowDataType=tid;
//		updateDataList();
//		dataTypeComboBox.setSelectedItem(nowDataType);
//	}
//	private void updateDataList()
//	{
//		dataListModel.removeAllElements();
//		Vector<BaseData> v=DataManager.getDataList(nowDataType.name);
//		for(int i=0;i<v.size();i++)
//		{
//			dataListModel.addElement(v.get(i));
//		}
//	}
//	/**
//	 * This method initializes jContentPane
//	 * 
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJContentPane() {
//		if (jContentPane == null) {
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.insets = new Insets(3, 2, 6, 3);
//			gridBagConstraints4.gridy = 2;
//			gridBagConstraints4.ipadx = 1;
//			gridBagConstraints4.ipady = -3;
//			gridBagConstraints4.gridx = 2;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.insets = new Insets(3, 6, 6, 1);
//			gridBagConstraints3.gridy = 2;
//			gridBagConstraints3.ipadx = 1;
//			gridBagConstraints3.ipady = -3;
//			gridBagConstraints3.gridx = 1;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.fill = GridBagConstraints.BOTH;
//			gridBagConstraints2.gridwidth = 3;
//			gridBagConstraints2.gridx = 0;
//			gridBagConstraints2.gridy = 1;
//			gridBagConstraints2.ipadx = -62;
//			gridBagConstraints2.ipady = 31;
//			gridBagConstraints2.weightx = 1.0;
//			gridBagConstraints2.weighty = 1.0;
//			gridBagConstraints2.insets = new Insets(2, 4, 3, 3);
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints1.gridwidth = 2;
//			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.gridy = 0;
//			gridBagConstraints1.ipadx = 100;
//			gridBagConstraints1.weightx = 1.0;
//			gridBagConstraints1.insets = new Insets(4, 2, 1, 3);
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.insets = new Insets(4, 4, 8, 1);
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.gridx = 0;
//			dataTypeLabel = new JLabel();
//			dataTypeLabel.setText("数据类型：");
//			jContentPane = new JPanel();
//			jContentPane.setLayout(new GridBagLayout());
//			jContentPane.add(dataTypeLabel, gridBagConstraints);
//			jContentPane.add(getDataTypeComboBox(), gridBagConstraints1);
//			jContentPane.add(getJScrollPane(), gridBagConstraints2);
//			jContentPane.add(getOkButton(), gridBagConstraints3);
//			jContentPane.add(getCancelButton(), gridBagConstraints4);
//		}
//		return jContentPane;
//	}
//
//	/**
//	 * This method initializes dataTypeComboBox	
//	 * 	
//	 * @return javax.swing.JComboBox	
//	 */
//	private JComboBox getDataTypeComboBox() {
//		if (dataTypeComboBox == null) {
//			dataTypeComboBox = new JComboBox();
//			dataTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
//				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					TypeItemData tid=(TypeItemData)dataTypeComboBox.getSelectedItem();
//					if(nowDataType==tid)
//						return;
//					_setDataType(tid);
//				}
//			});
//		}
//		return dataTypeComboBox;
//	}
//
//	/**
//	 * This method initializes jScrollPane	
//	 * 	
//	 * @return javax.swing.JScrollPane	
//	 */
//	private JScrollPane getJScrollPane() {
//		if (jScrollPane == null) {
//			jScrollPane = new JScrollPane();
//			jScrollPane.setViewportView(getDataList());
//		}
//		return jScrollPane;
//	}
//
//	/**
//	 * This method initializes dataList	
//	 * 	
//	 * @return javax.swing.JList	
//	 */
//	private JList getDataList() {
//		if (dataList == null) {
//			dataList = new JList();
//			dataListModel=new DefaultListModel();
//			dataList.setModel(dataListModel);
//		}
//		return dataList;
//	}
//
//	/**
//	 * This method initializes okButton	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getOkButton() {
//		if (okButton == null) {
//			okButton = new JButton();
//			okButton.setText("确定");
//			okButton.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					
//					datas=dataList.getSelectedValues();
//					if(datas==null || datas.length==0)
//					{
//						JOptionPane.showMessageDialog(null, "数据不正确！");
//						return;
//					}
//					result=JOptionPane.OK_OPTION;
//					dispose();
//				}
//			});
//		}
//		return okButton;
//	}
//
//	/**
//	 * This method initializes cancelButton	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getCancelButton() {
//		if (cancelButton == null) {
//			cancelButton = new JButton();
//			cancelButton.setText("取消");
//			cancelButton.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					result=JOptionPane.CANCEL_OPTION;
//					dispose();
//				}
//			});
//		}
//		return cancelButton;
//	}
//
//}  //  @jve:decl-index=0:visual-constraint="10,11"
