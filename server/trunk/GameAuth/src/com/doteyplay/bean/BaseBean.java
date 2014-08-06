package com.doteyplay.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

public abstract class BaseBean implements Serializable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseBean.class);  
	    
	private static final long serialVersionUID = 1L;

	public  String toString()
    {
    	 return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
    public  boolean equals(Object o)
    {
    	return EqualsBuilder.reflectionEquals(this, o);
    }
    
    public Object clone() 
    {
    	Object ret = null;
        try 
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);

            out.writeObject(this);
            out.close();

            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bin);
            ret = in.readObject();
            in.close();
        } catch (Exception e) 
        {
        	logger.error("");
        }        
        return ret;
     }
}
