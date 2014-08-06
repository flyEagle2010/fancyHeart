/**
 * @package com.doteplay.editor.component
 * @file BaseDataListEditPanel.java
 */
package com.doteplay.editor.component;

import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.util.Util;

public class BaseDataListEditPanel extends ListEditPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private DefaultListModel model = new DefaultListModel();
	private List<BaseData> dataList;
	private String groupId;

	public void initData(List<BaseData> dataList, String groupId) {
		this.groupId = groupId;
		this.dataList = dataList;
		for (int i = 0; i < dataList.size(); i++) {
			model.addElement(dataList.get(i).toString());
		}
		this.updateUI();
		inited = true;
	}

	@Override
	public synchronized void addItem() {
		Vector<BaseData> dataList = Util.selectDatas(editorPanel.editor.getJFrame(), groupId);
		if (dataList == null || dataList.isEmpty()) {
			return;
		}

		for (BaseData baseData : dataList) {
			if (dataList.contains(baseData.getIntId())) {
				return;
			}
			model.addElement(baseData.toString());
			dataList.add(baseData);
		}

		this.updateUI();
	}

	@Override
	public void onSelectItem(ListSelectionEvent e) {

	}

	@Override
	public synchronized void removeItem() {
		int[] selectIndexList = this.getJList().getSelectedIndices();

		Vector<Object> removeList = new Vector<Object>();
		Vector<BaseData> removeDataList = new Vector<BaseData>();

		for (int selIndex : selectIndexList) {
			removeList.add(model.get(selIndex));
			removeDataList.add(dataList.get(selIndex));
		}

		for (Object obj : removeList) {
			model.removeElement(obj);
		}
		dataList.removeAll(removeDataList);
		this.updateUI();
	}

}
