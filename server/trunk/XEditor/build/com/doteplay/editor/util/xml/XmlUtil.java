/**
 * @package com.doteplay.editor.util.xml.data2xml
 * @file XmlUtil.java
 */
package com.doteplay.editor.util.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;

import com.doteplay.editor.common.IXmlElement;

/**
 */
public class XmlUtil {

	public final static String DEFAULT_CHARSET = "UTF-8";

	
	public static Element createCDATAElement(Element parent, String name,String s) {
		Element e = new DefaultElement(name);
		e.addCDATA(s);
		parent.add(e);
		return e;
	}
	
	public static String getStringValueByCDATAElement(Element e) {
		return getRealStrByCDATAStr(e.getText());
	}
	
	/**
	 * 向元素中添加子列表元素
	 * @param <T>
	 * @param parent
	 * @param elementList
	 */
	public static <T extends IXmlElement> void GetListElement(Element parent, List<T> elementList) {
		for (T data : elementList) {
			parent.add(data.getElement());
		}
	}
	
	/**
	 * 向元素中添加子列表元素
	 * @param <T>
	 * @param parent
	 * @param elementList
	 */
	public static <T extends IXmlElement> void GetListElement(Element parent, T[] elementList) {
		for (T data : elementList) {
			parent.add(data.getElement());
		}
	}
	
	/**
	 * 向数据中设置列表元素值
	 * @param <T>
	 * @param parent
	 * @param nodeName
	 * @param dataList
	 */
	public static <T extends IXmlElement> void SetListElement(Element parent, String nodeName, T[] dataList) {
		List<Element> elementList = parent.selectNodes(nodeName);
		for (int i = 0; i < elementList.size(); i++) {
			T data = dataList[i];
			data.setElement(elementList.get(i));
		}
	}
	
	/**
	 * 向数据中设置列表元素值
	 * @param <T>
	 * @param parent
	 * @param nodeName
	 * @param dataList
	 */
	public static <T extends IXmlElement> void SetListElement(Element parent, String nodeName, List<T> dataList) {
		List<Element> elementList = parent.selectNodes(nodeName);
		for (int i = 0; i < elementList.size(); i++) {
			T data = dataList.get(i);
			data.setElement(elementList.get(i));
		}
	}
	
	public static String getCDATAString(String value) {
		return "<![CDATA[" + value + "]]>";
	}
	
	public static String getRealStrByCDATAStr(String CDATAString) {
		if (!CDATAString.startsWith("<![CDATA[")) {
			return CDATAString;
		}
		
		int len = CDATAString.length();
		String s = CDATAString.substring(9, len - 3);
		return s;
	}
	
	/**
	 * 向元素中添加属性
	 * 
	 * @param e
	 * @param map
	 */
	public static void ElementAddAttributes(Element e, Map<String, Object> map){
		if(map == null){
			return;
		}
		for (String key : map.keySet()) {
			String value = String.valueOf(map.get(key));
			ElementAddAttributes(e, key, value);
		}
	}
	
	public static void ElementAddAttributes(Element e, String name,Object value){
		if(value == null){
			return;
		}
		Attribute a = new DefaultAttribute(name, String.valueOf(value));
		e.add(a);
	}

	/**
	 * 创造Element
	 * 
	 * @param e
	 * @return
	 */
	public static Element CreateElement(IXmlElement e) {
		return CreateElement(e, e.getClass().getSimpleName());
	}

	/**
	 * 创造Element
	 * 
	 * @param e
	 * @param name
	 * @return
	 */
	public static Element CreateElement(IXmlElement e, String name) {
		Element element = new DefaultElement(name);
		Map<String, Object> map = e.getAttributes();
		XmlUtil.ElementAddAttributes(element, map);
		return element;
	}

	/**
	 * 设置属性
	 * 
	 * @param xe
	 * @param e
	 */
	public static void PutAttributes(IXmlElement xe, Element e) {
		List<Attribute> attributes = e.attributes();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Attribute a : attributes) {
			map.put(a.getName(), a.getValue());
		}
		xe.setAttributes(map);
	}

	/**
	 * 写入XML文件
	 * 
	 * @param doc
	 * @param fileName
	 */
	public static boolean WriteXml(Document doc, String fileName) {
		return WriteXml(doc, new File(fileName));
	}

	/**
	 * 写入XML文件
	 * 
	 * @param doc
	 * @param file
	 */
	public static boolean WriteXml(Document doc, File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(DEFAULT_CHARSET);
			XMLWriter xmlWriter = new XMLWriter(out, format);
			xmlWriter.write(doc);
			xmlWriter.close();
			xmlWriter = null;
			out.close();
			out = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取XML文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static Document ReadXml(String fileName) {
		return ReadXml(new File(fileName));
	}

	/**
	 * 读取XML文件
	 * 
	 * @param file
	 * @return
	 */
	public static Document ReadXml(File file) {
		Document doc = null;
		try {
			SAXReader sr = new SAXReader();
			doc = sr.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 读取字符串
	 * @param s
	 * @return
	 */
	public static List<String> ReadStringList(String s) {
		int len = s.length();
		String ss = s.substring(1, len-1);
		String[] values = ss.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			list.add(values[i].trim());
		}
		return list;
	}
	
	public static List<Integer> ReadIntList(String s) {
		int len = s.length();
		String ss = s.substring(1, len-1);
		String[] values = ss.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < values.length; i++) {
			if (values[i].length()<=0)
			{
				continue;
			}
			list.add(Integer.parseInt(values[i].trim()));
		}
		return list;
	}
	
	public static int[] ReadIntArray(String s) {
		List<Integer> l = ReadIntList(s);
		int[] ary = new int[l.size()];
		for (int i = 0; i < ary.length; i++) {
			ary[i] = l.get(i);
		}
		return ary;
	}
	
}
