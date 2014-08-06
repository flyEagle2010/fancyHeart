package com.doteplay.editor.common;

public class AbstractEditableData extends AbstractVersionData
{

	public EditorPanel editor;// µ±Ç°±à¼­Ãæ°å
	public boolean modified = false;
	public boolean isNew = true;

	public boolean isEdited()
	{
		return (this.editor != null && this.editor.modified);
	}

	public void bindEditor(EditorPanel editorpnl)
	{
		this.editor = null;
		this.editor = editorpnl;
	}

	public void releaseEditor()
	{
		this.editor = null;
	}

	public void markModified()
	{
		modified = true;
		if (editor != null)
		{
			editor.setModified(true);
		}
	}

	public void clearModifyFlag()
	{
		modified = false;
	}

	public boolean isModified()
	{
		return modified;
	}
}
