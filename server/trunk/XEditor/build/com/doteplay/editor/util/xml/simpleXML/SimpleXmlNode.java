package com.doteplay.editor.util.xml.simpleXML;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

// 一个简单的XML格式化输出工具
public class SimpleXmlNode {

	private String nodeName;
	private int level;
	private List<SimpleXmlNode> subNodes;
	
	private final static int MAX_ATTRIBUTE_COUNT = 32;
	private final static int ATTRIBUTE_NAME = 0;
	private final static int ATTRIBUTE_VALUE = 1;
	private String[][] attribute;
	private int attributeIndex;
	
	public SimpleXmlNode(String nodename, int level) {
		this.nodeName = (nodename != null) ? nodename : "unknown";
		attributeIndex = 0;
		this.level = level;
		subNodes = null;
	}
	
	public int getLevel() {
		return level;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodename) {
		this.nodeName = (nodename != null) ? nodename : "unknown";
	}

	public SimpleXmlNode addPrty(String prty, String value) {
		if (!StrUtils.empty(prty)) {
			if (attributeIndex < MAX_ATTRIBUTE_COUNT) {
				if (attribute == null) {
					attribute = new String[MAX_ATTRIBUTE_COUNT][2];
				}
				attribute[attributeIndex][ATTRIBUTE_NAME] = prty;
				attribute[attributeIndex][ATTRIBUTE_VALUE] = (value != null) ? value : "";
				attributeIndex++;
			}else{
				throw new IndexOutOfBoundsException();
			}
		}
		return this;
	}

	public SimpleXmlNode appendChild(String nodename) {
		if (subNodes == null)
			subNodes = new ArrayList<SimpleXmlNode>();
		SimpleXmlNode chnode = new SimpleXmlNode(nodename, this.level + 1);
		subNodes.add(chnode);
		return chnode;
	}

	public void clear() {
		if (subNodes != null) {
			SimpleXmlNode tmpSubNode;
			for (int i = 0; i < subNodes.size(); i++) {
				tmpSubNode = (SimpleXmlNode) subNodes.get(i);
				tmpSubNode.clear();
			}
			subNodes.clear();
			attributeIndex = 0;
			attribute = null;
		}
	}

	public void export(OutputStream outstream) {
		if (outstream == null)
			return;

		try {
			int i, len;
			byte[] preSpace = null;
			if (this.level > 0) {
				len = this.level * 2;
				preSpace = new byte[len];
				for (i = 0; i < len; i++)
					preSpace[i] = (byte) 32;
			}
			if (preSpace != null)
				outstream.write(preSpace, 0, preSpace.length);

			String tmpEncodeValue;
			outstream.write(60);
			outstream.write(nodeName.getBytes(SimpleXmlFormater.DEFAULT_CHARSET));
			if (attributeIndex > 0) {
				for (i = 0; i < attributeIndex; i++) {
					outstream.write(32);
					outstream.write(attribute[i][ATTRIBUTE_NAME].getBytes(SimpleXmlFormater.DEFAULT_CHARSET));
					outstream.write(61);
					outstream.write(34);
					tmpEncodeValue = StrUtils.xmlEncoded(attribute[i][ATTRIBUTE_VALUE], true);// 如果属性内部存在&,<,>等非法字符，需要转码
					outstream.write(tmpEncodeValue.getBytes(SimpleXmlFormater.DEFAULT_CHARSET));
					outstream.write(34);
				}
			}
			if (subNodes != null && subNodes.size() > 0) {
				outstream.write(62);
				outstream.write(SimpleXmlFormater.BR, 0, SimpleXmlFormater.BR.length);
				SimpleXmlNode tmpSubNode;
				for (i = 0; i < subNodes.size(); i++) {
					tmpSubNode = (SimpleXmlNode) subNodes.get(i);
					tmpSubNode.export(outstream);
				}
				if (preSpace != null)
					outstream.write(preSpace, 0, preSpace.length);
				outstream.write(60);
				outstream.write(47);
				outstream.write(nodeName.getBytes(SimpleXmlFormater.DEFAULT_CHARSET));
				outstream.write(62);
			}
			else {
				outstream.write(47);
				outstream.write(62);
			}
			outstream.write(SimpleXmlFormater.BR, 0, SimpleXmlFormater.BR.length);
			preSpace = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		export(outstream);
		String r = null;
		try {
			r = new String(outstream.toByteArray(), SimpleXmlFormater.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		outstream = null;
		return r;
	}

}
