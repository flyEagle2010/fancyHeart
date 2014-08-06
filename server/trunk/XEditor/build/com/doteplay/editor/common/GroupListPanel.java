package com.doteplay.editor.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.doteyplay.game.editor.ResourceDataConstants;
import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.XEditor;
import com.doteplay.editor.database.BaseConnectionPool;
import com.doteplay.editor.dialog.CheckDataResultDialog;
import com.doteplay.editor.util.FileNameFilter;
import com.doteplay.editor.util.Util;

/**
 * 数据组管理面板
 */
public class GroupListPanel extends GroupPanel
{

	private static final long serialVersionUID = -1021598143153799993L;

	private boolean working = false;

	private DefaultListModel groupListModel = new DefaultListModel();

	private JButton downloadButton = null;
	private JButton uploadButton = null;
	private JToolBar toolsToolBar = null;
	private JButton resaveButton = null;
	private JToggleButton nameKeyWordToggleButton = null;
	private JToolBar jToolBar = null;
	private JLabel dataNumLabel = null;
	private JPanel jPanel = null;
	private JToolBar jToolBar2 = null;
	private JButton newDataButton = null;
	private JButton removeDataButton = null;
	private JScrollPane jScrollPane = null;
	private JList groupList = null;
	private JPanel jPanel1 = null;
	private JPopupMenu dataPopupMenu = null; // @jve:decl-index=0:visual-constraint="27,108"
	private JMenuItem preloadMenuItem = null;
	private JMenuItem publishMenuItem = null;
	private JButton jButtonHistoryList = null;
	private JButton jButtonClearLock = null;

	public GroupListPanel()
	{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(210, 500));
		this.add(getJToolBar2(), BorderLayout.NORTH);
		this.add(getToolsToolBar(), BorderLayout.SOUTH);
		this.add(getJToolBar(), BorderLayout.WEST);
		this.add(getJScrollPane(), BorderLayout.CENTER);

	}

	public void init(XEditor e, String gid)
	{
		// TODO GroupListPanel
		super.init(e, gid);
		// updateData();
		updateDataByThread();
		// if(!EditorConfig.useDataBase)
		// {
		//
		// }
	}

	public void importCSVData()
	{
		JFileChooser fc = new JFileChooser();
		String filterString = "csv";
		fc.setFileFilter(new FileNameFilter(filterString));

		fc.setCurrentDirectory(EditorConfig.CURRENT_DIR);
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			EditorConfig.CURRENT_DIR = f;

			Vector<String[]> v = Util.loadCSVFile(f);
		}
	}

	private void groupDatas(Object[] datas)
	{
		int size = datas.length;
		if (size == 0)
			return;

		JFileChooser fc = new JFileChooser();
		// fc.setFileFilter(new FileNameFilter("png"));

		fc.setCurrentDirectory(EditorConfig.CURRENT_DIR);
		int returnVal = fc.showSaveDialog(this);
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
		} else
			return;

		try
		{
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					file));
			BaseData bd;
			out.writeShort(size);
			for (int i = 0; i < size; i++)
			{
				bd = (BaseData) datas[i];
				bd.open();

				if (bd.saveClientData() && bd.clientData != null)
				{
					out.writeShort(bd.getIntId());
					// out.writeShort(bd.data1.length);
					out.write(bd.clientData);
				}

				bd.close();
			}
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void uploadDatas(Object[] datas)
	{

		// int result=JOptionPane.showConfirmDialog(editor.getJFrame(),
		// "请确认提交数据:"+datas.length, "确认对话框", JOptionPane.OK_CANCEL_OPTION);
		// if(result!=JOptionPane.OK_OPTION)
		// return;

		DataCheckOutDialog dialog = new DataCheckOutDialog(editor.getJFrame());
		Util.showCenteredDialog(dialog, editor.getJFrame());
		int rel = dialog.getResult();
		if (rel == 0)
		{
			return;
		}

		DataGroup dc = DataManager.getDataGroup(groupId);

		int size = datas.length;
		BaseData bd;
		for (int i = 0; i < size; i++)
		{
			bd = (BaseData) datas[i];
			bd.checkOutText = dialog.getText();
			uploadData(bd);
		}
		dc.saveIndexFile();
		groupList.repaint();
	}

	private void uploadData(BaseData bd)
	{
		if (EditorConfig.useDataBase)
		{
			// if(bd.geneType==1)
			// {
			// JOptionPane.showMessageDialog(editor.getJFrame(),
			// "内部生成数据，不允许上传！", "警告对话框", JOptionPane.ERROR_MESSAGE);
			// return;
			// }

			if (bd.isLockedByOther())
			{
				JOptionPane.showMessageDialog(editor.getJFrame(),
						"[" + bd.getName() + "]数据被[" + bd.getLockUserName()
								+ "]锁定，请稍后再试！");
				return;
			}

			if ((bd.editor != null && bd.editor.modified) || bd.modified)
			{
				JOptionPane.showMessageDialog(editor.getJFrame(),
						"[" + bd.toString() + "]数据正被修改，请保存数据后重试！", "确认对话框",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// int result=JOptionPane.showConfirmDialog(editor.getJFrame(),
			// "["+bd.toString()+"]-【数据库】确认保存数据？", "确认对话框",
			// JOptionPane.OK_CANCEL_OPTION);
			// if(result==JOptionPane.CANCEL_OPTION)
			// return;
			if (bd.db_save())
			{
				// JOptionPane.showMessageDialog(editor.getJFrame(),
				// "["+bd.toString()+"]-【数据库】数据保存成功！", "确认对话框",
				// JOptionPane.INFORMATION_MESSAGE);
			} else
			{
				JOptionPane.showMessageDialog(editor.getJFrame(),
						"[" + bd.getErrorStr() + "]-【数据库】数据保存失败！", "确认对话框",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void addData(BaseData bd)
	{
		groupListModel.addElement(bd);
		// groupList.repaint();
	}

	@Override
	public void removeData(BaseData bd)
	{
		DataGroup dg = DataManager.getDataGroup(groupId);
		if (bd.geneType == 1)
		{
			JOptionPane.showMessageDialog(editor.getJFrame(), "内部生成数据，不允许删除！",
					"警告对话框", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (bd.isInDB == 1)
		{
			JOptionPane.showMessageDialog(editor.getJFrame(), "数据库数据，不允许删除！",
					"警告对话框", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// int result=JOptionPane.showConfirmDialog(editor.getJFrame(),
		// "请确认删除数据", "确认对话框", JOptionPane.OK_CANCEL_OPTION);
		// if(result==JOptionPane.OK_OPTION)
		{
			groupListModel.removeElement(bd);
			dg.removeData(bd);
			// System.out.println("remove:"+dg.datas.get(bd.id));
			// dg.save();
			editor.closeDataEditor(bd.editor);
			// groupList.repaint();

			// 从数据库中删除
			if (bd.isInDB == 1)
			{
				if (bd.db_delete())
				{
					// JOptionPane.showMessageDialog(editor.getJFrame(),
					// "["+bd.toString()+"]-【数据库】数据删除成功！", "确认对话框",
					// JOptionPane.INFORMATION_MESSAGE);
				} else
				{
					JOptionPane.showMessageDialog(editor.getJFrame(),
							"[" + bd.toString() + "]-【数据库】数据删除失败！", "确认对话框",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void newData()
	{
		DataGroup dc = DataManager.getDataGroup(groupId);
		// if(dc.geneType==1)
		// {
		// JOptionPane.showMessageDialog(editor.getJFrame(), "内部生成数据，不允许新建！",
		// "警告对话框", JOptionPane.ERROR_MESSAGE);
		// return;
		// }

		// if(dc==null)
		// return;
		BaseData bd = DataManager.newData(groupId);
		// panel=
		// (JDialog)Util.loadClassByName("com.doteplay.editor.data."+dc.groupId+"."+dc.groupClassId+"NewDialog");
		// if(panel!=null)
		// {
		editor.openDataEditor(bd);
	}

	public void updateDataByThread()
	{
		DataGroup dc = DataManager.getDataGroup(groupId);
		updateData(null);
	}

	public void updateData(Thread thread)
	{
		DataGroup dg = DataManager.getDataGroup(groupId);
		dg.update(thread);
		updateDataList();
	}

	public List<BaseData> getFilterDatas()
	{

		DataGroup<BaseData> dg = DataManager.getDataGroup(groupId);
		IDataFilter<BaseData> filter = dg.getDataFilter();
		List<BaseData> v1 = new Vector<BaseData>();

		if (dg.customDataKey && filter != null)
		{
			String[] s = dg.getDataFilter().getFilterTypes();
			int sel = Util.selectIndex(this, "选择条件", s, null);
			v1 = dg.getDataFilter().getDataList(sel);
			return v1;
		}

		List<BaseData> v = dg.getDataList();
		int size = v.size();
		BaseData bd;
		for (int i = 0; i < size; i++)
		{
			bd = v.get(i);
			if (dg.localDataKey && bd.isInDB != 0)
				continue;
			if (dg.noGenDataKey && bd.geneType != 0)
				continue;
			if (dg.nameKey != null && bd.toString().indexOf(dg.nameKey) == -1)
				continue;
			if (dg.modifiedKey && !bd.isDeferrentVersion())
				continue;
			if (dg.dataTypeKey >= 0 && bd.dataType != dg.dataTypeKey)
				continue;
			if (dg.refDataKey && bd.refNum == 0)
				continue;
			if (dg.loadType > 0 && bd.preload != dg.loadType)
				continue;
			if (dg.loadType == 0 && bd.preload == 0)
				continue;
			v1.add(bd);
		}
		return v1;
	}

	private synchronized void updateDataList()
	{

		DataGroup dg = DataManager.getDataGroup(groupId);
		List<BaseData> v1 = this.getFilterDatas();
		if (v1 == null)
		{
			return;
		}
		groupListModel = new DefaultListModel();
		for (BaseData bd : v1)
		{
			groupListModel.addElement(bd);
		}
		groupList.setModel(groupListModel);

		dataNumLabel.setText(String.valueOf(v1.size()));
	}

	@Override
	public void updateView()
	{
		groupList.repaint();
		// System.out.println("GroupListPanel.updateView!!");
	}

	private void publishData()
	{
		DataGroup dc = DataManager.getDataGroup(groupId);
		Object[] objs = groupList.getSelectedValues();
		if (objs == null)
			return;

	}

	private void resaveData()
	{
		DataGroup dc = DataManager.getDataGroup(groupId);
		Object[] objs = groupList.getSelectedValues();
		if (objs == null)
			return;
		int result = JOptionPane.showConfirmDialog(editor.getJFrame(),
				"请确认更新数据格式:" + objs, "确认对话框", JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_OPTION)
			return;
		int size = objs.length;
		BaseData bd;
		for (int i = 0; i < size; i++)
		{
			bd = (BaseData) objs[i];
			if (bd.geneType == 1)
			{
				continue;
			}
			bd.open();
			bd.modified = true;
			bd.save();
			bd.close();
		}
		dc.saveIndexFile();
	}

	/**
	 * This method initializes downloadButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDownloadButton()
	{
		if (downloadButton == null)
		{
			downloadButton = new JButton();
			downloadButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/download1.png")));
			downloadButton.setToolTipText("【数据库】装载数据");
			downloadButton
					.addActionListener(new java.awt.event.ActionListener()
					{

						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							Object[] objs = groupList.getSelectedValues();
							if (objs == null)
								return;

							downloadDatas(objs);
						}
					});
		}
		return downloadButton;
	}

	private void downloadDatas(Object[] objs)
	{
		DataGroup dc = DataManager.getDataGroup(groupId);

		int result = JOptionPane.showConfirmDialog(editor.getJFrame(),
				"请确认下载数据:" + objs, "确认对话框", JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.OK_OPTION)
			return;
		for (int i = 0; i < objs.length; i++)
		{
			downloadData((BaseData) objs[i]);
		}
		dc.saveIndexFile();
		groupList.repaint();

	}

	public void downloadData(BaseData bd)
	{
		if (EditorConfig.useDataBase)
		{
			if (bd.geneType == 1)
			{
				JOptionPane.showMessageDialog(editor.getJFrame(),
						"内部生成数据，不允许下载！", "警告对话框", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (bd.refNum > 0)
			{
				JOptionPane.showMessageDialog(editor.getJFrame(),
						"请关闭此数据相关编辑器后重试！", "确认对话框",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			// int result=JOptionPane.showConfirmDialog(editor.getJFrame(),
			// "【数据库】确认装载数据？", "确认对话框", JOptionPane.OK_CANCEL_OPTION);
			// if(result==JOptionPane.CANCEL_OPTION)
			// return;

			Connection conn = BaseConnectionPool.getConnection();
			if (conn == null)
				return;

			try
			{
				boolean rel = bd.db_load(conn);

				if (rel)
				{
					// JOptionPane.showMessageDialog(editor.getJFrame(),
					// "【数据库】数据装载成功！", "确认对话框",
					// JOptionPane.INFORMATION_MESSAGE);
					
					if(bd.type.equals("text"))
						bd.openForText();
				} else
				{
					JOptionPane.showMessageDialog(editor.getJFrame(),
							"【数据库】数据装载失败！", "确认对话框", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			} finally
			{
				try
				{
					conn.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method initializes uploadButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getUploadButton()
	{
		if (uploadButton == null)
		{
			uploadButton = new JButton();
			uploadButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/upload1.png")));
			uploadButton.setToolTipText("【数据库】保存数据");
			uploadButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					// BaseData bd=(BaseData)groupList.getSelectedValue();
					// if(bd==null)
					// return;
					Object[] bds = groupList.getSelectedValues();
					for (Object obj : bds)
					{
						BaseData bd = (BaseData) obj;
						if (EditorConfig.useDataBase && !bd.isNew)
						{
							// 数据版本控制问题
							if (Util.indexOf(XEditor.user.id,
									EditorConfig.SPECIAL_RIGHT_OF_USER) < 0
									&& !bd.chechVersion())
							{
								JOptionPane.showMessageDialog(
										XEditor.xEditor.getJFrame(),
										"注意，提交项中有[ID：" + bd.getId() + " : "
												+ bd.getName()
												+ " ]不是最新数据，请先下载！");
								return;
							}
						}
					}
					uploadDatas(bds);

					updateDataByThread();

					if (bds.length <= 0)
					{
						JOptionPane.showMessageDialog(
								XEditor.xEditor.getJFrame(), "请先选择提交数据后再提交");
						return;

					}
					
					BaseData bd = (BaseData) bds[0];
					editor.updateDataEditorTitle(bd.editor);
					
					JOptionPane.showMessageDialog(
							XEditor.xEditor.getJFrame(), "提交完成！！");

				}
			});

		}
		return uploadButton;
	}

	/**
	 * This method initializes toolsToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getToolsToolBar()
	{
		if (toolsToolBar == null)
		{
			dataNumLabel = new JLabel();
			dataNumLabel.setText(" ");
			dataNumLabel.setSize(new Dimension(40, 18));
			dataNumLabel.setPreferredSize(new Dimension(40, 18));
			toolsToolBar = new JToolBar();
			toolsToolBar.setFloatable(false);
			toolsToolBar.add(getResaveButton());
			toolsToolBar.add(getJButtonClearLock());
			toolsToolBar.add(getUploadButton());
			toolsToolBar.add(dataNumLabel);
			toolsToolBar.add(getJPanel());
		}
		return toolsToolBar;
	}

	/**
	 * This method initializes resaveButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getResaveButton()
	{
		if (resaveButton == null)
		{
			resaveButton = new JButton();
			resaveButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/saveFile.png")));
			resaveButton.setToolTipText("更新数据格式");
			resaveButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					resaveData();
				}
			});
		}
		return resaveButton;
	}

	/**
	 * This method initializes nameKeyWordToggleButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getNameKeyWordToggleButton()
	{
		if (nameKeyWordToggleButton == null)
		{
			nameKeyWordToggleButton = new JToggleButton();
			nameKeyWordToggleButton.setIcon(new ImageIcon(GroupListPanel.class
					.getResource("/img/icon/zoomIn.png")));
			nameKeyWordToggleButton.setToolTipText("查询:名称关键字[无]");
			nameKeyWordToggleButton
					.addItemListener(new java.awt.event.ItemListener()
					{

						public void itemStateChanged(java.awt.event.ItemEvent e)
						{

							DataGroup dg = DataManager.getDataGroup(groupId);

							if (nameKeyWordToggleButton.isSelected())
							{
								dg.nameKey = JOptionPane.showInputDialog(
										thisPanel, "输入名称关键字:", dg.nameKey);
								nameKeyWordToggleButton.setToolTipText("名称关键字["
										+ dg.nameKey + "]");
							} else
							{
								dg.nameKey = null;
								nameKeyWordToggleButton
										.setToolTipText("名称关键字[无]");
							}

							updateDataList();
						}
					});
		}
		return nameKeyWordToggleButton;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar()
	{
		if (jToolBar == null)
		{
			jToolBar = new JToolBar();
			jToolBar.setOrientation(JToolBar.VERTICAL);
			jToolBar.setFloatable(false);
			jToolBar.add(getNameKeyWordToggleButton());
			jToolBar.add(getJPanel1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2()
	{
		if (jToolBar2 == null)
		{
			jToolBar2 = new JToolBar();
			jToolBar2.setFloatable(false);
			jToolBar2.add(getNewDataButton());
			jToolBar2.add(getRemoveDataButton());
			jToolBar2.add(getDownloadButton());
			jToolBar2.add(getCopyButton());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes newDataButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNewDataButton()
	{
		if (newDataButton == null)
		{
			newDataButton = new JButton();
			newDataButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/new.png")));
			newDataButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					newData();
				}
			});
		}
		return newDataButton;
	}

	/**
	 * This method initializes removeDataButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRemoveDataButton()
	{
		if (removeDataButton == null)
		{
			removeDataButton = new JButton();
			removeDataButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/delete.png")));
			removeDataButton
					.addActionListener(new java.awt.event.ActionListener()
					{

						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							Object[] objs = groupList.getSelectedValues();
							if (objs == null)
								return;
							int result = JOptionPane.showConfirmDialog(
									editor.getJFrame(), "请确认删除数据:" + objs,
									"确认对话框", JOptionPane.OK_CANCEL_OPTION);
							if (result != JOptionPane.OK_OPTION)
								return;
							int size = objs.length;
							BaseData bd;
							for (int i = 0; i < size; i++)
							{
								bd = (BaseData) objs[i];
								removeData(bd);
							}
							DataGroup dg = DataManager.getDataGroup(groupId);
							dg.saveIndexFile();
							groupList.repaint();

						}
					});
		}
		return removeDataButton;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(3, 3));
			jScrollPane.setViewportView(getGroupList());
		}
		return jScrollPane;
	}
	
	BaseData copyData = null;
	private JButton copyButton;

	/**
	 * This method initializes groupList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getGroupList()
	{
		if (groupList == null)
		{
			groupList = new JList();
			groupList.setSize(new Dimension(0, 0));
			groupList.setModel(groupListModel);

			groupList.addMouseListener(new java.awt.event.MouseAdapter()
			{

				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					if (e.getButton() == e.BUTTON1)
					{
						if (e.getClickCount() >= 2)
						{
							BaseData bd = (BaseData) groupList
									.getSelectedValue();
							if (bd == null)
								return;
							editor.openDataEditor(bd);
						}

						BaseData bd1 = (BaseData) groupList.getSelectedValue();

						if (bd1 == null)
						{
							return;
						}
						
						copyData = bd1;

						String s = "(ID:" + bd1.lastEditUserId + ",编辑者:"
								+ bd1.lastEditUserName + ",时间:"
								+ bd1.lastEditorDateTime + ",版本:"
								+ bd1.lastEditorVersion + ")";

						groupList.setToolTipText(s);
						groupList.repaint();
					} else
					{
						getDataPopupMenu().show(groupList, e.getX(), e.getY());
					}
				}
			});

			groupList.setCellRenderer(new BaseDataListRender());

		}
		return groupList;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1()
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
		}
		return jPanel1;
	}

	/**
	 * This method initializes dataPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getDataPopupMenu()
	{
		if (dataPopupMenu == null)
		{
			dataPopupMenu = new JPopupMenu();
			dataPopupMenu.add(getPreloadMenuItem());
			dataPopupMenu.add(getPublishMenuItem());
		}
		return dataPopupMenu;
	}

	/**
	 * This method initializes preloadMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPreloadMenuItem()
	{
		if (preloadMenuItem == null)
		{
			preloadMenuItem = new JMenuItem();
			preloadMenuItem.setText("客户端资源加载标志");
			preloadMenuItem
					.addActionListener(new java.awt.event.ActionListener()
					{

						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							DataGroup dataGroup = DataManager
									.getDataGroup(groupId);

//							if (dataGroup.getClientResourceType() == -1)
//							{
//								return;
//							}

							Object[] objs = groupList.getSelectedValues();
							if (objs == null)
								return;

							int result = Util.selectIndex(thisPanel,
									"更改客户端资源加载标志",
									ResourceDataConstants.PRELOAD_TYPE_STR,
									null);

							if (result == -1)
								return;

							int preload = result;

							String s = JOptionPane.showInputDialog("输入加载顺序");
							int preloadOrder = -1;
							if (s != null && !"".equals(s))
							{
								preloadOrder = Integer.parseInt(s);
							}

							BaseData baseDataTmp;
							int sizeTmp = objs.length;
							for (int i = 0; i < sizeTmp; i++)
							{
								baseDataTmp = (BaseData) objs[i];
								if (baseDataTmp.isInDB != 1)
								{
									JOptionPane.showMessageDialog(
											preloadMenuItem,
											"请先提交数据，然后再更改数据库加载标志！", "温馨提示！",
											JOptionPane.ERROR);
									return;
								}
							}

							Connection conn = BaseConnectionPool
									.getConnection();

							try
							{
								if (conn == null)
								{
									JOptionPane.showMessageDialog(
											preloadMenuItem, "获取不到数据库连接，更改失败！",
											"提示！", JOptionPane.ERROR);
									return;
								}
								String updateSql_Editer = "UPDATE "
										+ EditorConfig.RES_DATA_TABLE_EDITOR
										+ " SET preload=?,preloadOrder=?,gd_editorVersion=?,gd_editorUserId=?,gd_editorUserName=?,gd_version=?,gd_dateTime=CURRENT_TIMESTAMP WHERE  resourceId=? and resourceType=?";

								PreparedStatement pstat_Editer = conn
										.prepareStatement(updateSql_Editer);

								BaseData baseData;
								int size = objs.length;
								for (int i = 0; i < size; i++)
								{
									baseData = (BaseData) objs[i];
									baseData.preload = preload;
									if (preloadOrder != -1)
									{
										baseData.preloadOrder = preloadOrder;
									}
									int ver = 0;
									if (EditorConfig.isPublisher)
									{// 发布不用更改数据版本
										ver = baseData.version;
									} else
									{
										ver = baseData.db_version + 1;
									}

									pstat_Editer.setInt(1, baseData.preload);
									pstat_Editer.setInt(2,
											baseData.preloadOrder);
									pstat_Editer.setString(3,
											EditorConfig.VERSION_BUILD);
									pstat_Editer.setInt(4, XEditor.user.id);
									pstat_Editer
											.setString(5, XEditor.user.name);
									pstat_Editer.setInt(6, ver);
									pstat_Editer.setString(7, baseData.id);
									pstat_Editer.setInt(8,
											baseData.getResourceType());
									pstat_Editer.addBatch();
									if (!EditorConfig.isPublisher)
									{
										baseData.db_version++;
										baseData.version = baseData.db_version;
									} else
									{
										baseData.db_version = baseData.version;
									}
									baseData.lastEditUserId = XEditor.user.id;
									baseData.lastEditUserName = XEditor.user.name;
									baseData.lastEditorDateTime = new Timestamp(
											System.currentTimeMillis());
									// baseData.versionUp();
								}
								pstat_Editer.executeBatch();

							} catch (SQLException e1)
							{
								JOptionPane.showMessageDialog(preloadMenuItem,
										"数据库执行出错！", "提示！", JOptionPane.ERROR);
								e1.printStackTrace();
							} finally
							{
								if (conn != null)
								{
									try
									{
										conn.close();
									} catch (SQLException e1)
									{
										e1.printStackTrace();
									}
								}
							}

							dataGroup.saveIndexFile();
							groupList.repaint();
						}
					});
		}
		return preloadMenuItem;
	}

	private PreparedStatement createSql(PreparedStatement pstat, int preload,
			int preloadOrder)
	{

		return pstat;
	}

	/**
	 * This method initializes publishMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPublishMenuItem()
	{
		if (publishMenuItem == null)
		{
			publishMenuItem = new JMenuItem();
			publishMenuItem.setText("开放标志设置");
			publishMenuItem
					.addActionListener(new java.awt.event.ActionListener()
					{

						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							DataGroup dataGroup = DataManager
									.getDataGroup(groupId);
							Object[] objs = groupList.getSelectedValues();
							if (objs == null)
								return;

							int result = Util
									.selectIndex(
											thisPanel,
											"更改开关标志",
											ResourceDataConstants.SWITCH_TYPE_STR,
											null);

							if (result == -1)
								return;

							BaseData baseDataTmp;
							int sizeTmp = objs.length;
							for (int i = 0; i < sizeTmp; i++)
							{
								baseDataTmp = (BaseData) objs[i];
								if (baseDataTmp.isInDB != 1)
								{
									JOptionPane.showMessageDialog(
											preloadMenuItem,
											"请先提交数据，然后再更改数据库开放标志！", "温馨提示！",
											JOptionPane.ERROR);
									return;
								}
							}
							Connection conn = BaseConnectionPool
									.getConnection();

							try
							{
								if (conn == null)
								{
									JOptionPane.showMessageDialog(
											preloadMenuItem, "获取不到数据库连接，更改失败！",
											"提示！", JOptionPane.ERROR);
									return;
								}
								String updateSql_Editer = "UPDATE "
										+ EditorConfig.RES_DATA_TABLE_EDITOR
										+ " SET openFlag=?,gd_editorVersion=?,gd_editorUserId=?,gd_editorUserName=?,gd_version=?,gd_dateTime=CURRENT_TIMESTAMP WHERE  resourceId=? and resourceType=?";

								PreparedStatement pstat_Editer = conn
										.prepareStatement(updateSql_Editer);

								int openFlag = result;

								BaseData baseData;
								int size = objs.length;
								for (int i = 0; i < size; i++)
								{
									baseData = (BaseData) objs[i];
									baseData.openFlag = openFlag;
									int ver = 0;
									if (EditorConfig.isPublisher)
									{// 发布不用更改数据版本
										ver = baseData.version;
									} else
									{
										ver = baseData.db_version + 1;
									}

									pstat_Editer.setInt(1, baseData.openFlag);
									pstat_Editer.setString(2,
											EditorConfig.VERSION_BUILD);
									pstat_Editer.setInt(3, XEditor.user.id);
									pstat_Editer
											.setString(4, XEditor.user.name);
									pstat_Editer.setInt(5, ver);
									pstat_Editer.setString(6, baseData.id);
									pstat_Editer.setInt(7,
											baseData.getResourceType());
									pstat_Editer.addBatch();
									if (!EditorConfig.isPublisher)
									{
										baseData.db_version++;
										baseData.version = baseData.db_version;
									} else
									{
										baseData.db_version = baseData.version;
									}
									baseData.lastEditUserId = XEditor.user.id;
									baseData.lastEditUserName = XEditor.user.name;
									baseData.lastEditorDateTime = new Timestamp(
											System.currentTimeMillis());

									// baseData.versionUp();
								}
								pstat_Editer.executeBatch();

							} catch (SQLException e1)
							{
								JOptionPane.showMessageDialog(preloadMenuItem,
										"数据库执行出错！", "提示！", JOptionPane.ERROR);
								e1.printStackTrace();
							} finally
							{
								if (conn != null)
								{
									try
									{
										conn.close();
									} catch (SQLException e1)
									{
										e1.printStackTrace();
									}
								}
							}
							dataGroup.saveIndexFile();
							groupList.repaint();
						}
					});
		}
		return publishMenuItem;
	}

	/**
	 * This method initializes jButtonClearLock
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonClearLock()
	{
		if (jButtonClearLock == null)
		{
			jButtonClearLock = new JButton();
			jButtonClearLock.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/delete.png")));
			jButtonClearLock.setToolTipText("清除数据锁");
			jButtonClearLock
					.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed(java.awt.event.ActionEvent e)
						{

							if (!EditorConfig.useDataBase)
							{
								return;
							}

							// 清除该组锁标记
							Object[] selected_datas = groupList
									.getSelectedValues();

							int result = JOptionPane.showConfirmDialog(
									thisPanel, "是否清除数据锁?", "确认对话框",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.INFORMATION_MESSAGE);
							if (result == JOptionPane.CANCEL_OPTION)
								return;

							for (int i = 0; i < selected_datas.length; i++)
							{
								BaseData bd = (BaseData) selected_datas[i];
								bd.clearLock();
								System.out.println("clearLock:" + bd.getName());
							}

							// DataGroup dc = DataManager.getDataGroup(groupId);
							//
							// for (int i = 0; i < dc.getDataList().size(); i++)
							// {
							// BaseData bd = (BaseData)dc.getDataList().get(i);
							// bd.clearLock();
							// System.out.println("clearLock:"+bd.getName());
							// }
						}
					});
		}
		return jButtonClearLock;
	}

	public void gotoGroupList(int index)
	{
		JScrollBar sBar = jScrollPane.getVerticalScrollBar();
		int min = sBar.getMinimum();
		int max = sBar.getMaximum();
		int avg = (max - min) / groupListModel.getSize();
		groupList.setSelectedIndex(index);
		sBar.setValue(index * avg);
	}

	public Object[] getSelectObjects()
	{
		return this.groupList.getSelectedValues();
	}
	
	private JButton getCopyButton() {
		if (copyButton == null)
		{
			copyButton = new JButton();
			copyButton.setToolTipText("\u6570\u636E\u62F7\u8D1D");
			copyButton.setIcon(new ImageIcon(getClass().getResource(
					"/img/icon/copy.png")));
			copyButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if(copyData == null)
						return;
					
					if(!copyData.type.equals(groupId))
						return ;
					
					BaseData data = DataManager.newData(groupId);
					copyData.copyDataTo(data);
					data.loadData();
					data.setId(null);
					editor.openDataEditor(data);
				}
			});
		}
		return copyButton;
	}
} // @jve:decl-index=0:visual-constraint="162,11"
