/**
 * @package com.doteplay.editor.tools.excel
 * @file ExcelDataTemplete.java
 */
package com.doteplay.editor.tools.excel2db.template;

import java.io.File;

import com.doteplay.editor.tools.excel.ExcelFieldType;
import com.doteplay.editor.tools.excel.ISheetDataFilter;

/**
 */
public interface ExcelDataTemplate {
	
	File getExcelFile();
	
	void setExcelFile(File f);
	
	String getTableName();
	/**
	 * @return String[数量][2] 0:名称 1:类型({@link ExcelFieldType})
	 */
	String[][] getFields();
	
	ISheetDataFilter getSheetDataFilter();
}
