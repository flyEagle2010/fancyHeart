/**
 * 
 */
package com.doteplay.editor.common.commonobject;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import com.doteplay.editor.common.EditorPanel;
import com.doteplay.editor.common.InnerEditorPanel;

public class CommonObjectListPanel<DATA extends Object, DATAPANEL extends CommonDataPanel<DATA>>
		extends InnerEditorPanel
{

	private static final long serialVersionUID = 1L;

	protected List<DATA> dataList; 
	private DefaultListModel jListCardGroupModel = null;

	protected Class<DATA> dataClass;
	protected Class<DATAPANEL> dataPanelClass;

	private JPanel jPanel1 = null;
	private JToolBar jToolBar = null;
	private JButton jButtonAddCardGroupData = null;
	private JButton jButtonRemoveCardGroupData = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane = null;
	private JList jListCardGroup = null;
	private DATAPANEL jPanelDataEditPanel = null;

	/**
	 * This is the default constructor
	 */
	public CommonObjectListPanel()
	{
		super();
		initialize();
	}

	public CommonObjectListPanel(Class<DATA> dataClass,
			Class<DATAPANEL> dataPanelClass)
	{
		super();
		this.dataClass = dataClass;
		this.dataPanelClass = dataPanelClass;

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(512, 328);
		this.setLayout(new BorderLayout());
		this.add(getJPanel1(), BorderLayout.CENTER);
	}

	protected DATA newData()
	{
		try
		{
			if (dataClass != null)
				return (DATA) dataClass.newInstance();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	protected DATAPANEL newDataEditPanel()
	{
		try
		{
			if (dataPanelClass != null)
				return (DATAPANEL) dataPanelClass.newInstance();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void init(EditorPanel editorPanel)
	{
		super.init(editorPanel);

		jPanelDataEditPanel.init(editorPanel);
	}

	public void init(List<DATA> dataList)
	{
		this.dataList = dataList;

		for (DATA data : dataList)
		{
			jListCardGroupModel.addElement(data);
		}

		jPanelDataEditPanel.init((DATA) null);

		inited = true;
	}

	protected void setSelectedData(DATA data)
	{
		jPanelDataEditPanel.init(data);
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jToolBar.add(getJButtonAddCardGroupData());
			jToolBar.add(getJButtonRemoveCardGroupData());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButtonAddCardGroupData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAddCardGroupData()
	{
		if (jButtonAddCardGroupData == null)
		{
			jButtonAddCardGroupData = new JButton();
			jButtonAddCardGroupData.setIcon(new ImageIcon(getClass()
					.getResource("/img/icon/new.png")));
			jButtonAddCardGroupData
					.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							if (!inited)
								return;

							DATA data = newData();
							if (data == null)
								return;

							dataList.add(data);
							jListCardGroupModel.addElement(data);

							setModified(true);
						}
					});
		}
		return jButtonAddCardGroupData;
	}

	/**
	 * This method initializes jButtonRemoveCardGroupData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonRemoveCardGroupData()
	{
		if (jButtonRemoveCardGroupData == null)
		{
			jButtonRemoveCardGroupData = new JButton();
			jButtonRemoveCardGroupData.setIcon(new ImageIcon(getClass()
					.getResource("/img/icon/delete.png")));
			jButtonRemoveCardGroupData
					.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed(java.awt.event.ActionEvent e)
						{
							if (!inited)
								return;

							DATA data = (DATA) jListCardGroup
									.getSelectedValue();
							if (data == null)
								return;

							dataList.remove(data);
							jListCardGroupModel.removeElement(data);

							setModified(true);
						}
					});
		}
		return jButtonRemoveCardGroupData;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane()
	{
		if (jSplitPane == null)
		{
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(160);
			jSplitPane.setRightComponent(getJPanelDataEditPanel());
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
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
			jScrollPane.setViewportView(getJListCardGroup());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jListCardGroup
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJListCardGroup()
	{
		if (jListCardGroup == null)
		{
			jListCardGroup = new JList();

			jListCardGroupModel = new DefaultListModel();
			jListCardGroup.setModel(jListCardGroupModel);
			jListCardGroup
					.addListSelectionListener(new javax.swing.event.ListSelectionListener()
					{
						public void valueChanged(
								javax.swing.event.ListSelectionEvent e)
						{
							if (!inited)
								return;
							if (e.getValueIsAdjusting())
								return;

							setSelectedData((DATA) jListCardGroup
									.getSelectedValue());
						}
					});
		}
		return jListCardGroup;
	}

	/**
	 * This method initializes jPanelDataEditPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private DATAPANEL getJPanelDataEditPanel()
	{
		if (jPanelDataEditPanel == null)
		{
			jPanelDataEditPanel = newDataEditPanel();
		}
		return jPanelDataEditPanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
