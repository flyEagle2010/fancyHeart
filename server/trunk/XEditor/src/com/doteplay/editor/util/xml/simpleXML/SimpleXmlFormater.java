package com.doteplay.editor.util.xml.simpleXML;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

// 因为业务需求只是一个简单的格式化输出，因此未引用各种常用的DOM包，实现了一个简单的xml格式化输出
public class SimpleXmlFormater {

    public static String DEFAULT_CHARSET = "UTF-8";
    public final static byte[] BR = new byte[] { 13, 10 };

    private SimpleXmlNode _rootNode;

    public SimpleXmlFormater() {
        this("root");
    }

    public SimpleXmlFormater(String rootname) {
        _rootNode = new SimpleXmlNode((rootname != null) ? rootname : "root", 0);
    }

    public SimpleXmlNode appendNode(String nodename) {
        return _rootNode.appendChild(nodename);
    }

    public void clear() {
        _rootNode.clear();
    }

    public void export(OutputStream outstream) {
        if (outstream == null)
            return;

        try {
            outstream.write("<?xml version=\"1.0\" encoding=\"".getBytes());
            outstream.write(DEFAULT_CHARSET.getBytes());
            outstream.write("\"?>".getBytes());
            outstream.write(BR, 0, BR.length);
            _rootNode.export(outstream);
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

    public static void main(String[] args) {
        SimpleXmlFormater tmpXml = new SimpleXmlFormater();
        SimpleXmlNode tmpNode = tmpXml.appendNode("object");
        tmpNode.addPrty("name", "王<xiao&明").addPrty("sex", "fmale");
        for (int i = 0; i < 3; i++) {
            SimpleXmlNode tmpSubNode = tmpNode.appendChild("info");
            tmpSubNode.addPrty("weight", "56KG").addPrty("height", "156");
        }
        System.out.println(tmpXml.toString());
    }

}
