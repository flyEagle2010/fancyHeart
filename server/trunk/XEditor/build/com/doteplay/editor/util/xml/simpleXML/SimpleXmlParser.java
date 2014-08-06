package com.doteplay.editor.util.xml.simpleXML;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// 一个简单格式XML文件解析器，只支持两级文件结构
public class SimpleXmlParser extends DefaultHandler {

    private static class DtdExcludeResolver implements EntityResolver {

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            InputSource result = new InputSource(new StringReader(""));
            return result;
        }
    }

    private ISimpleXmlSupport _refSupport;

    public SimpleXmlParser(ISimpleXmlSupport support) {
        this._refSupport = support;
    }

    @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (_refSupport != null) {
            SimpleAttributes refAtt = new SimpleAttributes(attributes);
            _refSupport.readXmlData(qName, refAtt);
            refAtt.realse();
            refAtt = null;
        }
    }

    // ***********************************************************************************************
    // 从文件解析
    public static void parseXmlFramFile(String filename, ISimpleXmlSupport support) {
        File tmpFile = null;
        FileInputStream tmpIn = null;
        try {

            tmpFile = new File(filename);
            if (!tmpFile.exists())
                return;

            tmpIn = new FileInputStream(tmpFile);
            parseXmlData(tmpIn, support);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (tmpIn != null) {
                try {
                    tmpIn.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            tmpFile = null;
        }
    }

    // 从字符串解析
    public static void parseXmlData(String soustr, ISimpleXmlSupport support) {
        parseXmlData(new ByteArrayInputStream(soustr.getBytes()), support);
    }

    // 从流解析
    public static void parseXmlData(InputStream soustream, ISimpleXmlSupport support) {
        if (soustream != null && support != null) {
            SimpleXmlParser tmpHandler = null;
            SAXParser parser = null;
            SAXParserFactory factory = null;
            try {
                tmpHandler = new SimpleXmlParser(support);
                factory = SAXParserFactory.newInstance();
                factory.setNamespaceAware(false);
                factory.setValidating(false);

                try {

                    parser = factory.newSAXParser();
                    parser.getXMLReader().setEntityResolver(new DtdExcludeResolver());
                    parser.parse(new InputSource(soustream), tmpHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                factory = null;
                parser = null;
                tmpHandler = null;
            }
        }
    }
}
