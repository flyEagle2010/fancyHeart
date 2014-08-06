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
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.util.Util;

/**
 */
public class BaseDataIdListEditPanel extends ListEditPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private DefaultListModel model = new DefaultListModel();
	private List<Integer> dataIdList;
	private String groupId;

	public void initData(List<Integer> dataIdList, String groupId) {
		this.groupId = groupId;
		this.dataIdList = dataIdList;
		for (int i = 0; i < dataIdList.size(); i++) {
			int dataId = dataIdList.get(i);
			BaseData data = DataManager.getBaseData(groupId, String.valueOf(dataId));
			model.addElement(data.toString());
		}

		this.setModel(model);
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
			if (dataIdList.contains(baseData.getIntId())) {
				return;
			}
			model.addElement(baseData.toString());
			dataIdList.add(baseData.getIntId());
		}

		this.setModified(true);
		this.updateUI();
	}

	@Override
	public void onSelectItem(ListSelectionEvent e) {

	}

	@Override
	public synchronized void removeItem() {
		int[] selectIndexList = this.getJList().getSelectedIndices();

		Vector<Object> removeDataList = new Vector<Object>();
		Vector<Integer> removeDataIdList = new Vector<Integer>();

		for (int selIndex : selectIndexList) {
			removeDataList.add(model.get(selIndex));
			removeDataIdList.add(dataIdList.get(selIndex));
		}

		for (Object obj : removeDataList) {
			model.removeElement(obj);
		}
		dataIdList.removeAll(removeDataIdList);
		
		this.setModified(true);
		this.updateUI();
	}

}
