package com.doteplay.editor.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.doteplay.editor.EditorConfig;
import com.doteplay.editor.XEditor;
import com.doteplay.editor.data.text.TextData;
import com.doteplay.editor.util.Util;

/**
 * 抽象数据编辑面板<br>
 * 主要实现了数据编辑的一些基础功能
 * 
 * @date 2011-7-25下午03:52:07
 */
public abstract class EditorPanel<T extends BaseData> extends JPanel
{

	private JDialog dataInfoDialog = null;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTextPane dataInfoTextPane = null;
	/**
	 * 数据组id
	 */
	public String groupId = "base";
	/**
	 * 编辑器引用
	 */
	public XEditor editor;
	/**
	 * 是否修改
	 */
	public boolean modified = false;
	/**
	 * 输入输出控制器
	 */
	protected InputMap windowIm;
	/**
	 * 数据是否初始化过
	 */
	public boolean inited = false;
	/**
	 * 当前面板引用
	 */
	protected EditorPanel thisPanel;
	/**
	 * 是否CTRL按下
	 */
	protected boolean keyFlag_CTRL = false;
	/**
	 * 是否SHIFT按下
	 */
	protected boolean keyFlag_SHIFT = false;

	/**
	 * 历史记录(100条)
	 */
	public List<byte[]> hisData = new ArrayList<byte[]>(); // @jve:decl-index=0:

	public EditorPanel()
	{
		thisPanel = this;

		windowIm = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);// WHEN_IN_FOCUSED_WINDOW
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "hotkey");
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				InputEvent.CTRL_DOWN_MASK), "hotkey");// KeyEvent.CTRL_DOWN_MASK
		addInputMapping(KeyEvent.VK_CONTROL, InputEvent.CTRL_DOWN_MASK);// block
																		// 碰撞
		addInputMapping(KeyEvent.VK_CONTROL, 0, true);// block 碰撞
		addInputMapping(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK);// block
																		// 碰撞
		addInputMapping(KeyEvent.VK_SHIFT, 0, true);// block 碰撞

		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
				InputEvent.CTRL_DOWN_MASK), "ctrl--");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,
				InputEvent.CTRL_DOWN_MASK), "ctrl--");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS,
				InputEvent.CTRL_DOWN_MASK), "ctrl-+");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,
				InputEvent.CTRL_DOWN_MASK), "ctrl-+");// KeyEvent.CTRL_DOWN_MASK
		windowIm.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,
				InputEvent.CTRL_DOWN_MASK), "ctrl-+");// KeyEvent.CTRL_DOWN_MASK
		ActionMap am = this.getActionMap();
		am.put("ctrl--", new AbstractAction()
		{
			/** serialVersionUID */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent p)
			{
				String b = p.getActionCommand(); // 得到行为的命令字符串
				// System.out.println("KeyPress : command="+b+","+p.getModifiers()+","+p);
				// onKeyPressed(p);
				onZoomKeyPressed(false);
			}
		});
		am.put("ctrl-+", new AbstractAction()
		{
			/** serialVersionUID */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent p)
			{
				String b = p.getActionCommand(); // 得到行为的命令字符串
				// System.out.println("KeyPress : command="+b+","+p.getModifiers()+","+p);
				// onKeyPressed(p);
				onZoomKeyPressed(true);
			}
		});
		am.put("hotkey", new AbstractAction()
		{

			/** serialVersionUID */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent p)
			{
				String b = p.getActionCommand(); // 得到行为的命令字符串
				// System.out.println("KeyPress : command="+b+","+p.getModifiers()+","+p);
				onKeyPressed(p);
			}
		});
		am.put("hotkeyRelease", new AbstractAction()
		{

			/** serialVersionUID */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent p)
			{
				String b = p.getActionCommand(); // 得到行为的命令字符串
				// System.out.println("KeyReleased : command="+b+","+p.getModifiers()+","+p);
				onKeyReleased(p);
			}
		});

		// windowIm.setParent(inputMap);

		this.setFocusable(true);
		this.addComponentListener(new java.awt.event.ComponentAdapter()
		{

			@Override
			public void componentHidden(java.awt.event.ComponentEvent e)
			{
				onComponentHidden();
			}

			@Override
			public void componentShown(java.awt.event.ComponentEvent e)
			{
				onComponentShown();
			}
		});
	}

	/**
	 * 控件显示事件，需重载实现
	 */
	protected void onComponentShown()
	{

	}

	/**
	 * 控件隐藏事件，需重载实现
	 */
	protected void onComponentHidden()
	{

	}

	/**
	 * 增加按键映射
	 * 
	 * @param code
	 * @param modifier
	 */
	protected final void addInputMapping(int code, int modifier)
	{
		windowIm.put(KeyStroke.getKeyStroke(code, modifier, false), "hotkey");// KeyEvent.CTRL_DOWN_MASK
	}

	/**
	 * 增加按键映射
	 * 
	 * @param code
	 * @param modifier
	 */
	protected final void addInputMapping(int code, int modifier,
			boolean keyRelease)
	{
		if (!keyRelease)
			windowIm.put(KeyStroke.getKeyStroke(code, modifier, false),
					"hotkey");// KeyEvent.CTRL_DOWN_MASK
		else
			windowIm.put(KeyStroke.getKeyStroke(code, modifier, true),
					"hotkeyRelease");// KeyEvent.CTRL_DOWN_MASK

	}

	/**
	 * 按键释放事件
	 * 
	 * @param p
	 */
	protected void onKeyReleased(ActionEvent p)
	{
		String b = p.getActionCommand();
		int modifier = p.getModifiers();
		if (b == null)
		{
			keyFlag_CTRL = false;
			keyFlag_SHIFT = false;
			return;
		}
	}

	/**
	 * 按键按下事件
	 * 
	 * @param p
	 */
	protected void onKeyPressed(ActionEvent p)
	{
		String b = p.getActionCommand();
		int modifier = p.getModifiers();
		// System.out.println("EditorPanel.onKeyPressed: b="+b +
		// ", modifier="+modifier+",paramString="+p.paramString()+", toString="+p.toString());
		if (b == null)
		{
			if (modifier == ActionEvent.CTRL_MASK)
				keyFlag_CTRL = true;
			if (modifier == ActionEvent.SHIFT_MASK)
				keyFlag_SHIFT = true;
			return;
		}
		int code = b.charAt(0);
		int i;
		if (code == KeyEvent.VK_DELETE)
		{
			// 删除按键[del]
			onDeletePressed();
		} else if (code == 0x01 && modifier == ActionEvent.CTRL_MASK)
		{
			// 全选按键[ctrl+a]
			onSelectAllPressed();
		} else if (code == 0x03 && modifier == ActionEvent.CTRL_MASK)
		{
			// 复制按键[ctrl+c]
			onCopyPressed();
		} else if (code == 0x16 && modifier == ActionEvent.CTRL_MASK)
		{
			// 粘贴按键[ctrl+v]
			onPastePressed();
		} else if (code == 0x18 && modifier == ActionEvent.CTRL_MASK)
		{
			// 剪切按键[ctrl+x]
			onCutPressed();
		} else if (code == 0x1a && modifier == ActionEvent.CTRL_MASK)
		{
			// 恢复按键[ctrl+z]
			onUndoPressed();
		} else if (code == 0x13 && modifier == ActionEvent.CTRL_MASK)
		{
			// 保存按键[ctrl+s]
			onSavePressed();
		} else if (code == 0x17 && modifier == ActionEvent.CTRL_MASK)
		{
			// 关闭按键[ctrl+w]
			onClosePressed();
		} else if (code == 0x19 && modifier == ActionEvent.CTRL_MASK)
		{
			// 重做按键[ctrl+y]
			onRedoPressed();
		}
	}

	/**
	 * 放大缩小按键响应，需重载实现
	 * 
	 * @param up
	 */
	protected void onZoomKeyPressed(boolean up)
	{
	}

	/**
	 * 删除按键[del]，需重载实现
	 * 
	 * @param up
	 */
	protected void onDeletePressed()
	{

	}

	/**
	 * 全选按键[ctrl+a]，需重载实现
	 */
	protected void onSelectAllPressed()
	{

	}

	/**
	 * 复制按键[ctrl+c]，需重载实现
	 */
	public void onCopyPressed()
	{

	}

	/**
	 * 粘贴按键[ctrl+v]，需重载实现
	 */
	public void onPastePressed()
	{

	}

	/**
	 * 剪切按键[ctrl+x]，需重载实现
	 */
	public void onCutPressed()
	{

	}

	/**
	 * 恢复按键[ctrl+z]
	 */
	protected final void onUndoPressed()
	{
		backHistory();
	}

	/**
	 * 重做按键[ctrl+y]，需重载实现
	 */
	protected void onRedoPressed()
	{

	}

	/**
	 * 保存按键[ctrl+s]，需重载实现
	 */
	protected void onSavePressed()
	{
		save();
	}

	/**
	 * 关闭按键[ctrl+w]
	 */
	protected final void onClosePressed()
	{
		if (close())
		{
			editor.closeDataEditor(this);
		}
	}

	/**
	 * 弹出窗口调用的回调函数，需重载实现
	 * 
	 * @param dialog
	 * @param type
	 * @param event
	 */
	public void notifyCallBack(JDialog dialog, String type, int event)
	{

	}

	/**
	 * 初始化面板
	 * 
	 * @param e
	 * @param gid
	 * @return
	 */
	public boolean init(XEditor e, String gid)
	{
		editor = e;
		groupId = gid;
		this.addComponentListener(new java.awt.event.ComponentAdapter()
		{

			public void componentShown(java.awt.event.ComponentEvent e)
			{
				requestFocus(true);
			}
		});
		return true;
	}

	/**
	 * 设置数据已修改
	 * 
	 * @param m
	 */
	public void setModified(boolean m)
	{
		modified = m;
		if (getData() != null)
			getData().modified = m;

		editor.updateDataEditorTitle(this);
	}

	/**
	 * 获取面板显示名称
	 * 
	 * @return
	 */
	public final String getTitle()
	{
		DataGroup dc = DataManager.getDataGroup(groupId);

		// 添加修改者和修改时间
		BaseData bd = getData();
		// String temp = "[" + dc.groupName + "]" + bd.name + " [" +
		// bd.lastEditUserName + "," + bd.lastEditorDateTime
		// + "]";
		String temp = bd.getBaseName();
		if (modified)
		{
			temp += " *";
		}
		return temp;
	}

	/**
	 * 设置数据
	 * 
	 * @param bd
	 */
	public final void setData(T bd)
	{
		bd.editor = this;
		modified = bd.isNew | bd.modified;
		bd.open();
		if (!bd.initInfo.equals(""))
		{
			JDialog aboutDialog = getDataInfoDialog();
			dataInfoTextPane.setText(bd.initInfo);
			Util.showCenteredDialog(aboutDialog, editor.getJFrame());

			aboutDialog.dispose();
			bd.initInfo = "";
			setModified(true);
		}
		initData(bd);
	}

	/**
	 * 初始化数据编辑面板显示
	 * 
	 * @param bd
	 */
	protected abstract void initData(T bd);

	/**
	 * 获取当前编辑的数据
	 * 
	 * @return
	 */
	public abstract T getData();

	/**
	 * 保存数据
	 * 
	 * @return
	 */
	protected abstract boolean saveData();

	//
	// /**
	// * 另存数据
	// *
	// * @return
	// */
	// public final boolean saveAs()
	// {
	// if (!save())
	// return false;
	//
	// BaseData bd = getData();
	// DataGroup dg = bd.dataGroup;
	//
	// String tid = DataManager.getNewDataId(bd.type);
	// tid = JOptionPane.showInputDialog(this, "请输入另存数据编号：", tid);
	//
	// if (tid == null || !Util.isInteger(tid))
	// {
	// return false;
	// }
	//
	// if (dg.isDataExist(tid))
	// {
	// int rel = JOptionPane.showConfirmDialog(this, "是否覆盖当前数据？");
	// if (rel == JOptionPane.OK_OPTION)
	// {
	// BaseData newBaseData = dg.getData(tid);
	// bd.copyDataTo(newBaseData);
	// newBaseData.loadData();
	// newBaseData.setId(tid);
	// newBaseData.save();
	// newBaseData.dataGroup.saveIndexFile();
	// newBaseData.release();
	// newBaseData.refNum = 0;
	//
	// return true;
	// }
	// } else
	// {
	//
	// BaseData newBaseData = dg.newData();
	// String name = JOptionPane.showInputDialog(this, "请输入另存数据名称：",
	// bd.nameId);
	// if (name != null && !name.equals(""))
	// {
	// newBaseData.nameId = name;
	// } else
	// {
	// return false;
	// }
	//
	// // newBaseData.isNew=true;
	// bd.copyDataTo(newBaseData);
	// newBaseData.loadData();
	//
	// newBaseData.id = tid;
	// newBaseData.fileName = bd.dataGroup.groupPath + tid + "."
	// + bd.dataGroup.fileSuffix;
	//
	// newBaseData.save();
	// newBaseData.dataGroup.saveIndexFile();
	// newBaseData.release();
	// newBaseData.refNum = 0;
	//
	// return true;
	// }
	// return false;
	//
	// }

	/**
	 * 保存数据
	 * 
	 * @return
	 */
	public final boolean save()
	{
		if (!modified)
			return true;

		BaseData bd = getData();
		if (EditorConfig.VERSION_TYPE != EditorConfig.VERSION_DEFAULT_TYPE
				&& !bd.versionDataEditable)
		{
			JOptionPane.showMessageDialog(this, "只能在缺省版本下保存数据!", "信息对话框",
					JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if (bd.isNew)
		{
			if (bd.id == null)
			{
				String tid = DataManager.getNewDataId(bd.type);// dataGroup.getMaxDataId();//
				tid = JOptionPane.showInputDialog(this, "请输入数据编号：", tid);
				if (tid == null || !Util.isInteger(tid)
						|| bd.dataGroup.isDataExist(tid))
				{
					JOptionPane.showMessageDialog(this, "数据编号错误,保存失败!",
							"信息对话框", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				bd.id = tid;
				bd.fileName = bd.dataGroup.groupPath + tid + "."
						+ bd.dataGroup.fileSuffix;
			}
			if (!bd.type.equals("text") && bd.nameId <= 0)
			{
				JOptionPane.showMessageDialog(this, "数据名字错误,保存失败!", "信息对话框",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		if (saveData())
		{
			if (bd.save())
			{
				// modified=false;
				this.setModified(false);
				DataGroup dc = DataManager.getDataGroup(groupId);
				dc.onDataSaved(getData());
				dc.saveIndexFile();
				// GroupPanel
				return true;
			} else
			{
				JOptionPane.showMessageDialog(this, "保存数据失败！");
			}
		}
		return false;
	}

	/**
	 * 释放编辑的数据
	 */
	public void release()
	{
		getData().editor = null;
		getData().close();
	}

	/**
	 * 关闭数据编辑器
	 * 
	 * @return
	 */
	public boolean close()
	{
		boolean ok = true;
		if (modified)
		{
			int i = JOptionPane.showConfirmDialog(this, "文件已更改，保存并退出？", "提示信息",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (i == JOptionPane.OK_OPTION)
			{
				if (isPanelLockedByOther())
				{
					ok = false;
				} else
				{
					ok = save();
				}

			} else if (i == JOptionPane.NO_OPTION)
			{
				ok = true;
			} else
				ok = false;
		}
		if (ok)
			release();

		return ok;
	}

	/**
	 * This method initializes dataInfoDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getDataInfoDialog()
	{
		if (dataInfoDialog == null)
		{
			dataInfoDialog = new JDialog();
			dataInfoDialog.setSize(new Dimension(408, 327));
			dataInfoDialog.setTitle("数据初始化信息");
			dataInfoDialog
					.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dataInfoDialog.setModal(true);
			dataInfoDialog.setContentPane(getJContentPane());
		}
		return dataInfoDialog;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new Dimension(400, 300));
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			jScrollPane.setViewportView(getDataInfoTextPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes dataInfoTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getDataInfoTextPane()
	{
		if (dataInfoTextPane == null)
		{
			dataInfoTextPane = new JTextPane();
			dataInfoTextPane.setEditable(false);
		}
		return dataInfoTextPane;
	}

	/**
	 * 判断当前数据编辑面板的数据是否被锁
	 * 
	 * @return
	 */
	public boolean isPanelLockedByOther()
	{
		if (getData() == null)
		{
			return false;
		}
		if (getData().isLockedByOther())
		{
			JOptionPane.showMessageDialog(editor.getJFrame(), "["
					+ getData().getName() + "]数据被["
					+ getData().getLockUserName() + "]锁定，请稍后再试！");
			return true;
		}
		return false;
	}

	/**
	 * 历史回退功能
	 */
	public void backHistory()
	{
		int size = hisData.size();
		if (size > 0)
		{
			T bd = getData();
			bd.editorData = hisData.remove(size - 1).clone();
			bd.releaseData();
			bd.loadData();
			initData(bd);
		}
	}

	/**
	 * 记录历史功能
	 */
	public void saveHistory()
	{
		BaseData bd = getData();
		bd.saveData();
		byte[] bs = bd.editorData.clone();
		int size = hisData.size();
		if (size > 100)
		{// 确保只能回退100步
			hisData.remove(0);
		}
		hisData.add(bs);
	}

	public int selectText(JButton button)
	{
		TextData data = (TextData) Util.selectData(thisPanel, "text",
				null);
		if (data == null)
			return -1;

		setModified(true);
		button.setText(data.gameData.name);

		return data.getIntId();
	}
}
