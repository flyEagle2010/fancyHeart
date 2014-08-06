/**
 * 
 */
package com.doteplay.editor.common;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.doteplay.editor.XEditor;

public class InnerEditorPanel extends JPanel
{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected TitledBorder titledBorder;

	public boolean inited;
	protected BaseData baseData;
	/**
	 * 关联数据编辑器
	 */
	public EditorPanel editorPanel;

	/**
	 * This is the default constructor
	 */
	public InnerEditorPanel()
	{
		super();
	}

	public void setTitleName(String titleName)
	{
		if (titledBorder == null)
		{
			titledBorder = BorderFactory.createTitledBorder(null, titleName,
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51));
			this.setBorder(titledBorder);
		}
		titledBorder.setTitle(titleName);
	}

	public void setModified(boolean modified)
	{
		if (editorPanel != null)
			editorPanel.setModified(modified);
	}

	public void init(EditorPanel editorPanel)
	{
		this.editorPanel = editorPanel;
		if (editorPanel != null)
		{
			this.baseData = editorPanel.getData();
		}
	}

	public void release()
	{

	}

	protected JFrame getApplicationFrame()
	{
		return XEditor.xEditor.getJFrame();
	}

	public BaseData getBaseData()
	{
		return baseData;
	}
}
