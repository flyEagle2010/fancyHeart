/**
 * 
 */
package com.doteplay.editor.component.PropertiesTable;

import java.util.List;

/**
 * @author Yang
 */
public interface IPropertyObject {

	List<IProperty> getProperties();

	void setProperties(List<IProperty> propertylist);

	void setProperty(int index, IProperty p);

}
