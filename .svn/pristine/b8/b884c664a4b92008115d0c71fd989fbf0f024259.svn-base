package com.doteplay.editor.tools.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author wyc 诛神flash将xml导入数据库
 */
public class HelpDataXmlParser extends DefaultHandler {
	/**
	 * help的父节点id
	 */
	private int parentId;
	/**
	 * tip的父节点id
	 */
	private int tipPid;
	/**
	 * 生成的SQL语句
	 */
	private Map<Integer, HelpData> map = new Hashtable<Integer, HelpData>();
	/**
	 * 存放所有的层次父节点id
	 */
	private List<Integer> list = new ArrayList<Integer>();

	private File file = null;

	public HelpDataXmlParser() {}

	public HelpDataXmlParser(String fileName) {
		file = new File(fileName);
		init();
	}

	public HelpDataXmlParser(String filePath, String fileName) {
		file = new File(filePath, fileName);
		init();
	}

	public HelpDataXmlParser(File file) {
		this.file = file;
		init();
	}

	public void init() {
		XMLReader reader;
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(this);
			InputSource source = new InputSource(new FileInputStream(file));
			reader.parse(source);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) {
		if ("helps".equalsIgnoreCase(name)) {

		}
		if ("help".equalsIgnoreCase(name)) {

			HelpData cellData = new HelpData();
			Integer pid = new Integer(attributes.getValue("id"));
			String cellName = attributes.getValue("name");
			cellData.setPKId(pid);
			cellData.setName(cellName == null ? "" : cellName);
			map.put(pid, cellData);
			list.add(pid);

		}
		if ("tip".equalsIgnoreCase(name)) {
			tipPid = list.get(list.size() - 1);
			HelpData cellData = map.get(tipPid);

			String x = attributes.getValue("x");
			String y = attributes.getValue("y");
			String dir = attributes.getValue("dir");
			String content = attributes.getValue("content");
			String parameter = x + "," + y + "," + dir + "," + content;
			cellData.setDescription(cellData.getDescription() + parameter);
		}

	}

	@Override
	public void endElement(String uri, String localName, String name) {
		if ("helps".equalsIgnoreCase(name)) {

		}

		if ("help".equalsIgnoreCase(name)) {
			if (list.size() > 1) {// 说明是help下面的子节点
				parentId = list.get(list.size() - 2);// 父节点
				tipPid = list.get(list.size() - 1);// 本身节点
				HelpData cellData = map.get(tipPid);
				cellData.setParentId(parentId);

				if (!cellData.getDescription().trim().isEmpty()) {
					cellData.setDescription(cellData.getDescription().substring(0,
							cellData.getDescription().length() - 1));
				}
			}
			list.remove(list.size() - 1);// 从列表中移除该对象id

		}

		if ("tip".equalsIgnoreCase(name)) {
			tipPid = list.get(list.size() - 1);
			HelpData cellData = map.get(tipPid);
			cellData.setDescription(cellData.getDescription() + "#");

		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {

	}

	@Override
	public void startDocument() {

	}

	public boolean dealWithSql() {
		boolean flag = true;
		int index = 0;
		
		HelpImportDBManager manager = new HelpImportDBManager();
		
		for (Map.Entry<Integer, HelpData> me : map.entrySet()) {

			HelpData cellData = me.getValue();
			if (!manager.insertXmlIntoDB(cellData)) {
				flag = false;
			}
			System.out.println((index++)+"\t"+cellData.toString());
		}

		manager = null;
		
		return flag;
	}

	// public static void main(String[] args) {
	// File f = new File("c:/demo.xml");
	// HelpDataXmlParser parser = new HelpDataXmlParser(f);
	// parser.dealWithSql();
	// }

}
