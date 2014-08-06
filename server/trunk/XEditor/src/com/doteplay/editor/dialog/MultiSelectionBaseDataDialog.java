package com.doteplay.editor.dialog;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataManager;

public class MultiSelectionBaseDataDialog extends JDialog{

	private JScrollPane jScrollPane = null;
	private JList jList = null;
	private JPanel jContentPane = null;
	private JPanel jPanel = null; 
	private JButton jButton = null;
	private DefaultListModel objectistModel = new DefaultListModel();
	private String dataType = null;
	private JToolBar jToolBar = null;  //  @jve:decl-index=0:visual-constraint="390,61"
	private JButton filterButton = null;
	private JTextField filterTextField = null;
	private JLabel jLabel = null;
//	private BaseData[] baseDatas = null;
	private Vector<BaseData> v  = new Vector<BaseData>();  //  @jve:decl-index=0:
	private int result=JOptionPane.CANCEL_OPTION;
	private JButton escButton = null;
	
	public Vector<BaseData> getBaseDatas() {
		return v;
	}
	public MultiSelectionBaseDataDialog(String dataType){
		super();
		this.dataType = dataType;
		initialize();
	}
	public MultiSelectionBaseDataDialog(JFrame jframe,String dataType){
		super(jframe);
		this.dataType = dataType;
		initialize();
	}
	private void initialize() {
		this.setModal(true);
		this.setTitle("选择数据");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.add(getJToolBar(),BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.updateUI();
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
			jScrollPane.setSize(267,280);
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
			jList.setSize(0,0);
			addData();
			jList.setModel(objectistModel);
			jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		return jList;
	}
	/**
	 * 添加数据
	 */
	private void addData(){
		String condition = filterTextField.getText();
		if(objectistModel!=null){
			objectistModel.removeAllElements();
		}
		Vector<BaseData> v = DataManager.getDataGroup(dataType).getDataListByType(-1);
		for(int i = 0;i<v.size();i++){
			BaseData bd =  v.get(i);
			if(condition != null&&!condition.isEmpty()){
				if(bd.toString().indexOf(condition)>=0){
					objectistModel.addElement(bd);
				}
			} else {
				objectistModel.addElement(bd);
			}
		}
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton(), new GridBagConstraints());
			jPanel.add(getEscButton(), gridBagConstraints);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("\u786e\u8ba4");
			jButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
//					int[] values = jList.getSelectedIndices();
//					Vector<BaseData> tv = DataManager.getDataGroup(dataType).getDataListByType(-1);
//					for(int i = 0;i<values.length;i++){
//						v.add(tv.get(values[i]));
//					}
					
					for(Object o:jList.getSelectedValues()){
						v.add((BaseData)o);
					}
					result=JOptionPane.OK_OPTION;
					dispose();
				}
			});
			
		}
		return jButton;
	}
	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("查询 : ");
			jToolBar = new JToolBar();
			jToolBar.add(jLabel);
			jToolBar.add(getFilterTextField());
			jToolBar.add(getFilterButton());
		}
		return jToolBar;
	}
	/**
	 * This method initializes filterButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFilterButton() {
		if (filterButton == null) {
			filterButton = new JButton();
			filterButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/refresh.png")));
			filterButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					addData();
					jList.updateUI();
				}
				
			});
		}
		return filterButton;
	}
	/**
	 * This method initializes filterTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFilterTextField() {
		if (filterTextField == null) {
			filterTextField = new JTextField();
			filterTextField.setBounds(new Rectangle(44, 1, 113, 28));
		}
		return filterTextField;
	}
	/**
	 * This method initializes escButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getEscButton() {
		if (escButton == null) {
			escButton = new JButton();
			escButton.setText("取消");
			escButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					result=JOptionPane.CANCEL_OPTION;
					dispose();
				}
			});
		}
		return escButton;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
