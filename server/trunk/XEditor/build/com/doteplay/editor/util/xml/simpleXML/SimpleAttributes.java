package com.doteplay.editor.util.xml.simpleXML;

import org.xml.sax.Attributes;

public class SimpleAttributes {

    private Attributes refAttributes;

    public SimpleAttributes(Attributes refattributes) {
        this.refAttributes = refattributes;
    }

    public void realse() {
        this.refAttributes = null;
    }

    public int getLength() {
        return (refAttributes != null) ? refAttributes.getLength() : 0;
    }

    public String getPrtyName(int index) {
        return (refAttributes != null) ? refAttributes.getQName(index) : null;
    }

    public String getValue(int index) {
        return (refAttributes != null) ? refAttributes.getValue(index) : null;
    }

    public int getIndex(String prtyname) {
        return (refAttributes != null) ? refAttributes.getIndex(prtyname) : null;
    }

    public String getValue(String prtyname) {
        return (refAttributes != null) ? refAttributes.getValue(prtyname) : null;
    }
}
