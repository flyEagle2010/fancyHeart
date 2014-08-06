/**
 * @package com.doteplay.editor.common
 * @file IDataFilter.java
 */
package com.doteplay.editor.common;

import java.util.Vector;

/**
 */
public interface IDataFilter<T extends BaseData> {
	public Vector<T> getDataList(int selFilterType);
	public String[] getFilterTypes();
}
