/**
 * 
 */
package com.doteplay.editor.tools.update;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataGroup;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.database.BaseConnectionPool;



/**
 * @author Yang
 *
 */
public class DataUpdateToolDialog extends JDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8749311218114600776L;
	
	private Vector<ResUpdateData> resUpdateDataList=new Vector<ResUpdateData>();  //  @jve:decl-index=0:
	
//	public static final Map<String, Byte> DATATYPE_2_RESTYPE_MAP=new HashMap<String, Byte>();
//	{
//		DATATYPE_2_RESTYPE_MAP.put("image", ResourceDataConstants.DATATYPE_IMAGE);
//	}
	
	public static final String[][] DATA_TYPE = { 
		{ "图形" ,"image"}, 
		{ "物体" ,"object"},
		{ "地图" ,"scene"}, 
		{ "图形包" ,"pnggroup"}, 
		{ "世界地图" ,"worldmap"}
	};
	
	private String[] resUpdateDataListTitle = {"操作类型","是否常驻","名称" ,"编辑时间"};
	
	private final static String update_table_name="t_game_data_update";
	
	private DataUpdateToolDialog instance;
	private JPanel jPanel = null;
	private JLabel jLabelSelDataPackage = null;
	private JComboBox jComboBoxSelGroupData = null;
	private JToolBar jJToolBarBar = null;
	private JPanel jPanel1 = null;
	private JButton jButtonRefresh = null;
	
	private JScrollPane jScrollPaneLeft = null;
	private JLabel jLabelLeft = null;
	private JLabel jLabelRight = null;
	private JScrollPane jScrollPaneRight = null;
	
	private JButton jButtonAdd = null;
	private JButton jButtonDel = null;
	
	private JList jListBaseData = null;
	private JList jListPublishData = null;
	
	private DefaultListModel jListModelBaseData = new DefaultListModel();
	private DefaultListModel[] jListModelPublishData;
	private JButton jButtonSavePublishList = null;
	
	private int selectGroupId=0;
	
	public boolean isModified=false;
	
	private int selectDriver=0;
	private JButton jButtonClear = null;

	private JScrollPane jScrollPane = null;

	private JTable jTableSelectUpdateData = null;
	
	private DefaultTableModel updateDataTableModel = new DefaultTableModel(){
		
		private static final long serialVersionUID = 543543543531L;
		
		@Override
		public int getColumnCount() {
			return resUpdateDataListTitle.length;
		}

		@Override
		public int getRowCount() {
			return resUpdateDataList.size();
		}

		@Override
		public void addRow(Object[] rowData) {
			System.out.println("addRow:"+rowData.length);
			resUpdateDataList.add((ResUpdateData)rowData[0]);
			fireTableDataChanged();
		}

		@Override
		public void removeRow(int row) {
			resUpdateDataList.remove(row);
			fireTableRowsDeleted(row, row);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			ResUpdateData rd=resUpdateDataList.get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				return rd.getActionType();
			case 1:
				return rd.getIsResidence();
			case 2:
				return DataManager.getTextById(rd.getDataNameId());
			case 3:
				return rd.getVersion();
			}
			return null;
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			
			ResUpdateData rd=resUpdateDataList.get(rowIndex);
			
			switch (columnIndex) {
			case 0:
				String type=(String)value;
				if(type.equals("清除")){
					rd.setActionType((byte)0);
				}else if(type.equals("更新")){
					rd.setActionType((byte)1);
				}
				break;
			case 1:
				boolean isResidence=(Boolean)value;
				if(isResidence){
					rd.setIsResidence((byte)1);
				}else{
					rd.setIsResidence((byte)0);
				}
				break;
			case 2:
				break;
			case 3:
				break;
			}
			
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if(column==0 || column==1){
				return true;
			}
			return false;
		}
		
	};

	
	/**
	 * This method initializes 
	 * 
	 */
	public DataUpdateToolDialog(Frame owner) {
		super(owner);
		instance=this;
		initialize();
	}

	public void init() {
		
		//初始化list和ComboBox
		int size=DATA_TYPE.length;
		jListModelPublishData=new DefaultListModel[size];
		for(int i=0;i<size;i++){
			jComboBoxSelGroupData.addItem(DATA_TYPE[i][0]);
			jListModelPublishData[i]=new DefaultListModel();
		}
		
		//取得数据库中的数据
		getResUpdateDataListFromDB();
		
		setResUpdateDataListToListModel();
		
		if(jListModelPublishData[selectGroupId]!=null){
			jListPublishData.setModel(jListModelPublishData[selectGroupId]);
		}
		jListPublishData.updateUI();
		
		for(int i=0;i<resUpdateDataListTitle.length;i++){
			updateDataTableModel.addColumn(resUpdateDataListTitle[i]);
		}
		
//		System.out.println(typeColumn);
		JComboBox cb_0 = new JComboBox();
		TableColumn typeColumn_0 = jTableSelectUpdateData.getColumn(resUpdateDataListTitle[0]);
		typeColumn_0.setMaxWidth(100);
		typeColumn_0.setResizable(false);
		cb_0.addItem("清除");
		cb_0.addItem("更新");
		typeColumn_0.setCellEditor(new DefaultCellEditor(cb_0));
		
		JComboBox cb_1 = new JComboBox();
		TableColumn typeColumn_1 = jTableSelectUpdateData.getColumn(resUpdateDataListTitle[1]);
		typeColumn_1.setMaxWidth(100);
		typeColumn_1.setResizable(false);
		cb_1.addItem(true);
		cb_1.addItem(false);
		typeColumn_1.setCellEditor(new DefaultCellEditor(cb_1));
		
		jTableSelectUpdateData.updateUI();
	}

	/**
	 * 拷贝BaseData到ResUpdateData
	 * @param bd
	 * @param rd
	 */
	public void copyBaseData2ResUpdateData(BaseData bd,ResUpdateData rd){
		rd.setDataId("0");
		rd.setDataNameId(bd.getName());
		rd.setResId(bd.getIntId());
//		rd.setResType(DATATYPE_2_RESTYPE_MAP.get(bd.dataGroup.groupId));
		rd.setVersion(bd.lastEditorDateTime);
	}
	
	/**
	 * 设置ListModel到resUpdateDataList
	 */
	public void setListModelToResUpdateDataList(){
		resUpdateDataList.clear();
		for(int i=0;i<jListModelPublishData.length;i++){
			if(jListModelPublishData[i]==null || jListModelPublishData[i].isEmpty()){
				continue;
			}
			for(int j=0;j<jListModelPublishData[i].getSize();j++){
				BaseData bd=(BaseData)jListModelPublishData[i].get(j);
				ResUpdateData rd=new ResUpdateData();
				copyBaseData2ResUpdateData(bd,rd);
				resUpdateDataList.add(rd);
			}
		}
		
		jListPublishData.updateUI();
		System.out.println("setResUpdateDataList:"+resUpdateDataList);
	}
	
	/**
	 * 设置resUpdateDataList到ListModel
	 */
	public void setResUpdateDataListToListModel(){
		
		
		//初始化jListModelPublishData
		for(int i=0;i<jListModelPublishData.length;i++){
			jListModelPublishData[i].removeAllElements();
		}
		
		//添加dasedate到jListModelPublishData
		for(int i=0;i<resUpdateDataList.size();i++){
			ResUpdateData rd=resUpdateDataList.get(i);
			String dataGroupName=DATA_TYPE[rd.getResType()][1];
			DataGroup dg=DataManager.getDataGroup(dataGroupName);
			BaseData bd=(BaseData)dg.getData(""+rd.getResId());
			int resType=rd.getResType();
			jListModelPublishData[resType].addElement(bd);
		}
		
	}
	
	/**
	 * 删除数据库中的所有数据
	 * @return
	 */
	public boolean delAllResUpdateDataFromDB(){
		Connection conn=BaseConnectionPool.getConnection();
		if(conn==null){
			return false;
		}
		
		boolean ok=false;
		String sql;
    	PreparedStatement pstat;
    	sql="TRUNCATE TABLE " +update_table_name;
		
    	try {
    		
        	pstat=conn.prepareStatement(sql);
        	int result=pstat.executeUpdate();
        	if (result == 1) {
				ok = true;
			}
        	pstat.close();	    	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	finally
    	{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}    	
		return ok;
	}
	
	/**
	 * 取得数据库中的数据
	 */
	public void getResUpdateDataListFromDB(){
		
		Connection conn=BaseConnectionPool.getConnection();
		if(conn==null){
			return;
		}
		
		String sql;
    	PreparedStatement pstat;
    	ResultSet rs;
    	sql="select * from " +update_table_name;
    	
    	try {
			pstat=conn.prepareStatement(sql);
	    	rs=pstat.executeQuery();
	    	resUpdateDataList.clear();
	    	while (rs.next()) {
				ResUpdateData rd = new ResUpdateData();
				rd.setPKId(rs.getInt("PKId"));
				rd.setResType(rs.getByte("resType"));
				rd.setResId(rs.getInt("resId"));
				rd.setDataId(rs.getString("dataId"));
				rd.setDataNameId(rs.getInt("dataName"));
				rd.setVersion(rs.getTimestamp("version"));
				rd.setActionType(rs.getByte("actionType"));
				rd.setIsResidence(rs.getByte("isResidence"));
				resUpdateDataList.add(rd);
			}
	    	rs.close();
	    	pstat.close();
	    	
	    	System.out.println(resUpdateDataList);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	finally
    	{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}		
	}
	
	/**
	 * 保存数据到数据库
	 */
	public void saveResUpdateDataListToDB(){
		
		Connection conn=BaseConnectionPool.getConnection();
		if(conn==null){
			return;
		}
		
		try {
			for(ResUpdateData rd:resUpdateDataList){
				saveResUpdateDataToDB(conn,rd);
			}
		} catch (RuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * 保存数据某个数据到数据库
	 * @param conn
	 * @param rd
	 * @return
	 */
	public int saveResUpdateDataToDB(Connection conn,ResUpdateData rd){
		
		int result=0;
		String sql;
    	PreparedStatement pstat;
    	sql="insert into "+update_table_name+" (" +
    			"resType,resId,dataId,dataName,version,actionType,isResidence) " +
    			"values " +
    			"(?,?,?,?,?,?,?)";
    	
    	try {
    		pstat=conn.prepareStatement(sql);
    		pstat.setByte(1, rd.getResType());
    		pstat.setInt(2, rd.getResId());
    		pstat.setInt(3, Integer.parseInt(rd.getDataId()));
        	pstat.setInt(4, rd.getDataNameId());
        	pstat.setTimestamp(5, rd.getVersion());
        	pstat.setByte(6, rd.getActionType());
        	pstat.setByte(7, rd.getIsResidence());
        	result=pstat.executeUpdate();
        	pstat.close();
	    	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	
		return result;
		
	}
	
	
	/*private void setListModel(DefaultListModel lm,DataGroup dg) {
		for(int i=0;i<dg.dataList.size();i++){
			DataGroup dg=DataManager.dataGroupList.get(i);
			
		}
	}*/
	
	public void release() {
		jListModelBaseData=null;
		jListModelPublishData=null;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(590, 600));
		this.setResizable(false);
		this.setPreferredSize(new Dimension(590, 600));
		this.setContentPane(getJPanel());
		this.setTitle("资源更新设定");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(1);
			borderLayout.setVgap(1);
			jLabelSelDataPackage = new JLabel();
			jLabelSelDataPackage.setToolTipText("");
			jLabelSelDataPackage.setBounds(new Rectangle(10, 10, 68, 21));
			jLabelSelDataPackage.setText("选择资源包:");
			jPanel = new JPanel();
			jPanel.setLayout(borderLayout);
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	
	/**
	 * This method initializes jComboBoxSelDataPackage	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxSelDataPackage() {
		if (jComboBoxSelGroupData == null) {
			jComboBoxSelGroupData = new JComboBox();
			jComboBoxSelGroupData.setBounds(new Rectangle(80, 10, 141, 21));
			jComboBoxSelGroupData.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					
					jListModelBaseData.clear();
					
					selectGroupId=jComboBoxSelGroupData.getSelectedIndex();
					DataGroup dg=DataManager.getDataGroup(DATA_TYPE[selectGroupId][1]);
					Vector<BaseData> tmpDataList=dg.getDataList();
					for(int i=0;i<tmpDataList.size();i++){
						BaseData bd=(BaseData)tmpDataList.get(i);
						if(bd.geneType!=1){
							jListModelBaseData.addElement(bd);
						}
					}
					
					if(jListModelPublishData!=null && jListModelPublishData[selectGroupId]!=null){
						jListPublishData.setModel(jListModelPublishData[selectGroupId]);
					}
					jListPublishData.updateUI();
				}
			});
		}
		return jComboBoxSelGroupData;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJButtonRefresh());
			jJToolBarBar.add(getJButtonSavePublishList());
			jJToolBarBar.add(getJButtonClear());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabelRight = new JLabel();
			jLabelRight.setBounds(new Rectangle(420, 50, 61, 21));
			jLabelRight.setText("更新列表");
			jLabelLeft = new JLabel();
			jLabelLeft.setBounds(new Rectangle(90, 50, 61, 21));
			jLabelLeft.setText("源数据");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setSize(new Dimension(640, 480));
			jPanel1.add(jLabelSelDataPackage, null);
			jPanel1.add(getJComboBoxSelDataPackage(), null);
			jPanel1.add(getJScrollPaneLeft(), null);
			jPanel1.add(jLabelLeft, null);
			jPanel1.add(jLabelRight, null);
			jPanel1.add(getJScrollPaneRight(), null);
			jPanel1.add(getJButtonAdd(), null);
			jPanel1.add(getJButtonDel(), null);
			jPanel1.add(getJScrollPane(), null);
//			jPanel1.add(getJButtonSelAll(), null);
//			jPanel1.add(getJButtonSelNone(), null);
			
		}
		return jPanel1;
	}
	
	/**
	 * This method initializes jButtonPublish
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonRefresh() {
		if (jButtonRefresh == null) {
			jButtonRefresh = new JButton();
			jButtonRefresh.setText("刷新");
//			jButtonRefresh.setForeground(Color.red);
			jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
//					TableColumn typeColumn = jTableSelectUpdateData.getColumn(0);
					
//					System.out.println(typeColumn);
					
					//取得数据库中的数据
					getResUpdateDataListFromDB();
					setResUpdateDataListToListModel();
					
					jTableSelectUpdateData.updateUI();
				}
			});
		}
		return jButtonRefresh;
	}

	/**
	 * This method initializes jListBaseData
	 * 	
	 * @return javax.swing.JList
	 */
	private JList getJListBaseData() {
		
		if (jListBaseData == null) {
			jListBaseData = new JList();
			
			jListBaseData.setModel(jListModelBaseData);
		}
		return jListBaseData;
	}

	/**
	 * This method initializes jScrollPaneLeft	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneLeft() {
		if (jScrollPaneLeft == null) {
			jScrollPaneLeft = new JScrollPane();
			jScrollPaneLeft.setBounds(new Rectangle(10, 80, 231, 241));
			jScrollPaneLeft.setViewportView(getJListBaseData());
		}
		return jScrollPaneLeft;
	}

	/**
	 * This method initializes jScrollPaneRight	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneRight() {
		if (jScrollPaneRight == null) {
			jScrollPaneRight = new JScrollPane();
			jScrollPaneRight.setBounds(new Rectangle(340, 80, 236, 241));
			jScrollPaneRight.setViewportView(getJListPublishData());
		}
		return jScrollPaneRight;
	}

	/**
	 * This method initializes jListPublishData	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListPublishData() {
		if (jListPublishData == null) {
			jListPublishData = new JList();
		}
		return jListPublishData;
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setBounds(new Rectangle(250, 100, 81, 21));
			jButtonAdd.setText("添加 >>");
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int[] indeces=jListBaseData.getSelectedIndices();
					
					if(indeces!=null){
						for(int i=0;i<indeces.length;i++){
							
							Object obj=jListModelBaseData.get(indeces[i]);
							if(!jListModelPublishData[selectGroupId].contains(obj)){
								jListModelPublishData[selectGroupId].addElement(obj);
								
								BaseData bd=(BaseData)obj;
								
								int row=isUpdateDataLisContains(bd);
								
								if(row==-1){
									ResUpdateData rd=new ResUpdateData();
									copyBaseData2ResUpdateData(bd,rd);
									
									ResUpdateData[] rowData=new ResUpdateData[1];
									rowData[0]=rd;
									updateDataTableModel.addRow(rowData);
									
								}
							}
						}
					}
					jListPublishData.updateUI();
					
					isModified=true;
					
				}
			});
		}
		return jButtonAdd;
	}

	private int isUpdateDataLisContains(BaseData bd) {
		for(int row=0;row<resUpdateDataList.size();row++){
			ResUpdateData rd=resUpdateDataList.get(row);
			if(rd.getResId()==bd.getIntId()){
				updateDataTableModel.removeRow(row);
				return row;
			}
		}
		return -1;
	}
	
	/**
	 * This method initializes jButtonDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDel() {
		if (jButtonDel == null) {
			jButtonDel = new JButton();
			jButtonDel.setBounds(new Rectangle(250, 150, 81, 21));
			jButtonDel.setText("<< 删除");
			jButtonDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					Object[] selectedObj=jListPublishData.getSelectedValues();
					
					for(int i=0;i<selectedObj.length;i++){
						jListModelPublishData[selectGroupId].removeElement(selectedObj[i]);
						BaseData bd=(BaseData)selectedObj[i];
						int row=isUpdateDataLisContains(bd);
						if(row!=-1){
							updateDataTableModel.removeRow(row);
						}
					}
					jListPublishData.updateUI();
					isModified=true;
				}
			});
		}
		return jButtonDel;
	}

	
	/**
	 * This method initializes jButtonSavePublishList	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSavePublishList() {
		if (jButtonSavePublishList == null) {
			jButtonSavePublishList = new JButton();
			jButtonSavePublishList.setText("保存");
			jButtonSavePublishList.setForeground(Color.red);
			jButtonSavePublishList.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int rel=JOptionPane.showConfirmDialog(instance, "是否保存？", "保存", JOptionPane.OK_CANCEL_OPTION);
					
					if(rel==JOptionPane.CANCEL_OPTION){
						return;
					}
					
					delAllResUpdateDataFromDB();
					saveResUpdateDataListToDB();
					
					jTableSelectUpdateData.updateUI();
				}
			});
		}
		return jButtonSavePublishList;
	}

	
	/**
	 * This method initializes jButtonClear	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonClear() {
		if (jButtonClear == null) {
			jButtonClear = new JButton();
			jButtonClear.setText("清除");
			jButtonClear.setForeground(Color.red);
			jButtonClear.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int rel=JOptionPane.showConfirmDialog(instance, "是否清除数据？", "清除", JOptionPane.OK_CANCEL_OPTION);
					
					if(rel==JOptionPane.CANCEL_OPTION){
						return;
					}
					
					delAllResUpdateDataFromDB();
					
					resUpdateDataList.clear();
					
					for(int i=0;i<jListModelPublishData.length;i++){
						jListModelPublishData[i].removeAllElements();
					}
					jListPublishData.updateUI();
//					JOptionPane.showMessageDialog(instance, "清除成功！", "清除", JOptionPane.CLOSED_OPTION);
					jTableSelectUpdateData.updateUI();
				}
			});
		}
		return jButtonClear;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(10, 350, 566, 186));
			jScrollPane.setViewportView(getJTableSelectUpdateData());
		}
		return jScrollPane;
	}

	
	/**
	 * This method initializes jTableSelectUpdateData	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableSelectUpdateData() {
		if (jTableSelectUpdateData == null) {
			jTableSelectUpdateData = new JTable();
			jTableSelectUpdateData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableSelectUpdateData.setModel(updateDataTableModel);			
		}
		return jTableSelectUpdateData;
	}
	
	
//	private JButton jButtonSelAll = null;
//
//	private JButton jButtonSelNone = null;
//
//	private void selectAllPublish(byte type) {
//		for(ResUpdateData rd:resUpdateDataList){
//			rd.setActionType(type);
//		}
//		jTableSelectUpdateData.updateUI();
//	}
//	
//	/**
//	 * This method initializes jButtonSelAll	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getJButtonSelAll() {
//		if (jButtonSelAll == null) {
//			jButtonSelAll = new JButton();
//			jButtonSelAll.setBounds(new Rectangle(10, 325, 65, 21));
//			jButtonSelAll.setText("全选");
//			jButtonSelAll.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					selectAllPublish(true);
//				}
//			});
//		}
//		return jButtonSelAll;
//	}
//
//	/**
//	 * This method initializes jButtonSelNone	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getJButtonSelNone() {
//		if (jButtonSelNone == null) {
//			jButtonSelNone = new JButton();
//			jButtonSelNone.setBounds(new Rectangle(80, 325, 86, 21));
//			jButtonSelNone.setText("清除选项");
//			jButtonSelNone.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					selectAllPublish(false);
//				}
//			});
//		}
//		return jButtonSelNone;
//	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
