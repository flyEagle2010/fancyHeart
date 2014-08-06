/**
 * 
 */
package com.doteplay.editor.component.PropertiesTable;

import javax.swing.JTable;

import com.doteplay.editor.common.EditorPanel;

/**
 * @author Yang
 */
public class PropertiesTable extends JTable {

	/**
	 * 注意属性名不可重复
	 */
	private static final long serialVersionUID = 6568209628121590950L;

	private PropertiesTableModel tableModel;
	
	private EditorPanel editorPanel;

	/**
	 * This method initializes
	 */
	public PropertiesTable() {
		super();
		initialize();
	}

	public void setEditorPanel(EditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}
	
	private void setModified(boolean isModified) {
		editorPanel.setModified(isModified);
	}
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				onMouseDoubleClicked();
			}
		});

	}

	protected void onMouseDoubleClicked() {
		int selRow = this.getSelectedRow();
		if (selRow == -1) {
			return;
		}
		IProperty p = this.getProperty(selRow);
		IPropertyEditor editor = p.getPropertyEditor();
		if (editor != null) {
			editor.setEditProperty(p);
			editor.show(this);
			setProperty(selRow, p);
			setModified(true);
			this.updateUI();
		}
	}

	public void initData(IPropertyObject obj) {
		tableModel = new PropertiesTableModel(obj);
		this.setModel(tableModel);
	}

	public void release() {
		tableModel = null;
	}

	public PropertiesTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * 设置属性值
	 * 
	 * @param index 属性名
	 * @param value
	 */
	public void setProperty(int index, IProperty p) {
		tableModel.setPropertyValue(index, p.getValue());
	}

	/**
	 * 获取属性值
	 * 
	 * @param index
	 * @return
	 */
	public IProperty getProperty(int index) {
		return tableModel.getProperty(index);
	}

}
