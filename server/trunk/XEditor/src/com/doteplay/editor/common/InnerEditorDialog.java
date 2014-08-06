/**
 * 
 */
package com.doteplay.editor.common;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.doteplay.editor.XEditor;

public class InnerEditorDialog extends JDialog {

	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;

	public boolean inited;
	/**
	 * 关联基础数据
	 */
	protected BaseData baseData;  //  @jve:decl-index=0:
	/**
	 * 关联数据编辑器
	 */
	public EditorPanel editorPanel;
	
	public JDialog thisDialog=this;

	public InnerEditorDialog() {
		super(XEditor.xEditor.getJFrame());
	}
	/**
	 * @param owner
	 */
	public InnerEditorDialog(Frame owner) {
		super(owner);
	}

	public void setModified(boolean modified)
	{
		if(editorPanel!=null)
			editorPanel.setModified(modified);
	}
	public void init(EditorPanel editorPanel) {
		this.editorPanel = editorPanel;
		if (editorPanel != null) {
			this.baseData = editorPanel.getData();
		}
		
//		System.out.println("this.editorPanel="+this.editorPanel);
	}

	public void release()
	{
		
	}

	protected JFrame getApplicationFrame()
	{
		return XEditor.xEditor.getJFrame();
	}

	public BaseData getBaseData() {
		return baseData;
	}
}
