/**
 * @package com.doteplay.editor.common
 * @file IXmlDocument.java
 */
package com.doteplay.editor.common;

import java.util.Map;

import org.dom4j.Element;

/**
 */
public interface IXmlElement {
	public Element getElement();
	public void setElement(Element e);
	public Map<String, Object> getAttributes();
	public void setAttributes(Map<String, Object> map);
}
