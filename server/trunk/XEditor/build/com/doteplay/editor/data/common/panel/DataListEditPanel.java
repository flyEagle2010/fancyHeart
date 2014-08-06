/**
 * 
 */
package com.doteplay.editor.data.common.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.InnerEditorPanel;
import com.doteplay.editor.util.Util;

public class DataListEditPanel<T extends BaseData> extends InnerEditorPanel {

	private static final long serialVersionUID = 3092712628556678677L;
	private String dataType;
	private List<T> dataList;  //  @jve:decl-index=0:
	private JPanel jPanel8 = null;
	private JToolBar jToolBar3 = null;
	private JButton addRewardGoodsButton = null;
	private JButton removeRewardGoodsButton = null;
	private JScrollPane jScrollPane6 = null;
	private JList skillDataList = null;
	private DefaultListModel skillDataListModel = null;
	/**
	 * This is the default constructor
	 */
	public DataListEditPanel(String dataType) {
		super();
		this.dataType = dataType;
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
		this.add(getJPanel8(), BorderLayout.CENTER);
	}

	/**
	 * 初始化技能数据列表
	 * @param dataList
	 */
	public void init(List<T> dataList)
	{
		this.dataList=dataList;
		
		inited=false;

		int i,size;
		
		List<T> v=dataList;
		size=v.size();
		T skillData;
		for(i=0;i<size;i++)
		{
			skillData=v.get(i);
			skillDataListModel.addElement(skillData);
		}
		inited=true;
	}

	private void addSkillObject()
	{
		inited=false;

		T skillData=(T)Util.selectData(this, dataType, null);
		if(skillData==null)
			return;
		
		dataList.add(skillData);
		skillDataListModel.addElement(skillData);
		
		inited=true;
		
		setModified(true);
	}
	
	private void removeSkillObject(int index)
	{
		T skillData = (T)skillDataListModel.getElementAt(index);
		if(skillData==null)
			return;
		
		int result=JOptionPane.showConfirmDialog(this, "是否删除技能:"+skillData);
		if(result!=JOptionPane.OK_OPTION)
			return;
		
		dataList.remove(skillData);
		skillDataListModel.removeElement(skillData);

		setModified(true);
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.setPreferredSize(new Dimension(0, 150));
			jPanel8.add(getJToolBar3(), java.awt.BorderLayout.NORTH);
			jPanel8.add(getJScrollPane6(), java.awt.BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jToolBar3	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jToolBar3 = new JToolBar();
			jToolBar3.add(getAddRewardGoodsButton());
			jToolBar3.add(getRemoveRewardGoodsButton());
		}
		return jToolBar3;
	}

	/**
	 * This method initializes addRewardGoodsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddRewardGoodsButton() {
		if (addRewardGoodsButton == null) {
			addRewardGoodsButton = new JButton();
			addRewardGoodsButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/new.png")));
			addRewardGoodsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addSkillObject();
				}
			});
		}
		return addRewardGoodsButton;
	}
	/**
	 * This method initializes removeRewardGoodsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveRewardGoodsButton() {
		if (removeRewardGoodsButton == null) {
			removeRewardGoodsButton = new JButton();
			removeRewardGoodsButton.setIcon(new ImageIcon(getClass().getResource("/img/icon/delete.png")));
			removeRewardGoodsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = skillDataList.getSelectedIndex();
					if(index==-1)
						return;
					
					removeSkillObject(index);
				}
			});
		}
		return removeRewardGoodsButton;
	}

	/**
	 * This method initializes jScrollPane6	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getSkillDataList());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes skillDataList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getSkillDataList() {
		if (skillDataList == null) {
			skillDataList = new JList();
			skillDataListModel = new DefaultListModel();
			
			skillDataList.setModel(skillDataListModel);
		}
		return skillDataList;
	}

}
