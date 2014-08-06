/**
 * @package com.doteplay.editor.tools.excel
 * @file SheetDataFilter.java
 */
package com.doteplay.editor.tools.excel;

/**
 */
public interface ISheetDataFilter {
	public abstract String[][] filter(String[][] sheetData);
}
