package com.doteyplay.game.util;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 调试工具之一,
 */
public final class DebugTools
{
	private DebugTools() {}

    public static String toString(Object obj)
    {
        StringBuilder sb = new StringBuilder(2048);
        show(obj, -1, sb);
        return sb.toString();
    }

    public static void show(Object obj)
    {
        StringBuilder sb = new StringBuilder(2048);
        show(obj, -1, sb);
        System.out.println(sb.toString());
    }

    public static void show(Object obj, StringBuilder sb)
    {
        show(obj, -1, sb);
    }

    public static void show(Object obj, PrintStream out)
    {
        StringBuilder sb = new StringBuilder(2048);
        show(obj, -1, sb);
        out.println(sb.toString());
        out.flush();
    }

    /*private static boolean isMultiItems(Object obj)
    {
        if(obj == null)
            return false;
        return (obj instanceof Map)
                || (obj.getClass().isArray())
                || (obj instanceof Collection);
    }

    private static void showValue(Object value, int indent, StringBuilder sb)
    {
        if (isMultiItems(value))
        {
            sb.append("\r\n");
            show(value, ++indent, sb);
        }
        else
            sb.append(value).append("\r\n");
    }*/

    private static void buildIndent(int indent, StringBuilder sb)
    {
        if(indent <= 0)
            return;
        for(int i=0; i<indent; i++)
        {
            sb.append("    ");
        }
    }

    private static void show(Object obj, int indent, StringBuilder sb)
    {
        if(obj == null)
        {
            sb.append("null").append("\r\n");
            return;
        }

        //String indentString = getIndent(indent);
        if(obj instanceof Map)
        {
            sb.append("\r\n");
            indent++;
            Map map = (Map)obj;
            Set<Map.Entry> all = map.entrySet();
            Iterator<Map.Entry> itr = all.iterator();
            while(itr.hasNext())
            {
                Map.Entry en = itr.next();
                Object key = en.getKey();
                Object value = en.getValue();
                buildIndent(indent, sb);
                sb.append(key).append("=");
                show(value, indent, sb);
            }
        }
        else if(obj.getClass().isArray())
        {
            sb.append("\r\n");
            indent++;
            Class cls = obj.getClass().getComponentType();
            if(cls.isPrimitive())
            {
            	showPrimitiveArray(obj, cls, indent, sb);
            }
            else
            {
	            Object[] objs = (Object[])obj;
	            int i = 0;
	            for(Object o : objs)
	            {
	                buildIndent(indent, sb);
	                sb.append("[").append(i).append("]=");
	                show(o, indent, sb);
	                i++;
	            }
            }
        }
        else if(obj instanceof Collection)
        {
            sb.append("\r\n");
            indent++;
            Collection objs = (Collection)obj;
            int i = 0;
            for(Object o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
        }
//        else if(obj instanceof RelatedData)
//        {
//            RelatedData rd = (RelatedData)obj;
//            show(rd.getValues(), indent, sb);
//        }
//        else if(obj instanceof KeyObject)
//        {
//            KeyObject ko = (KeyObject)obj;
//            sb.append("(").append(ko.getKey()).append(")").append(ko).append("\r\n");;
//        }
        else
        {
        	if(obj instanceof Boolean)
        	{
        		Boolean ok = (Boolean)obj;
        		sb.append(ok);
        	}
        	else
        		sb.append(obj);
        	
        	sb.append("\r\n");
        }
    }
    
    private static void showPrimitiveArray(Object obj, Class cls, int indent, StringBuilder sb)
    {
    	if(cls == Boolean.TYPE)
    	{
            boolean[] objs = (boolean[])obj;
            int i = 0;
            for(boolean o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Character.TYPE)
    	{
            char[] objs = (char[])obj;
            int i = 0;
            for(char o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Byte.TYPE)
    	{
            byte[] objs = (byte[])obj;
            int i = 0;
            for(byte o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Short.TYPE)
    	{
            short[] objs = (short[])obj;
            int i = 0;
            for(short o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Integer.TYPE)
    	{
            int[] objs = (int[])obj;
            int i = 0;
            for(int o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Long.TYPE)
    	{
            long[] objs = (long[])obj;
            int i = 0;
            for(long o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Float.TYPE)
    	{
            float[] objs = (float[])obj;
            int i = 0;
            for(float o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Double.TYPE)
    	{
            double[] objs = (double[])obj;
            int i = 0;
            for(double o : objs)
            {
                buildIndent(indent, sb);
                sb.append("[").append(i).append("]=");
                show(o, indent, sb);
                i++;
            }
    	}
    	else if(cls == Void.TYPE)
    	{
            
    	}
    }
    
    public static String toString(boolean ok)
    {
    	if(ok)
    		return "■";
    	else
    		return "□";
    }
    
    public static void fixLengthString(StringBuilder sb, float src, int size, char fill)
    {
    	fixLengthString(sb, String.valueOf(src), size, fill);
    }
    
    public static void fixLengthString(StringBuilder sb, long src, int size, char fill)
    {
    	fixLengthString(sb, String.valueOf(src), size, fill);
    }
    
    public static void fixLengthString(StringBuilder sb, int src, int size, char fill)
    {
    	fixLengthString(sb, String.valueOf(src), size, fill);
    }
    
    public static void fixLengthString(StringBuilder sb, String src, int size, char fill)
    {
        int len = src.length();
        int k = size - len;
        if (k < 0)
        {
            k = 3;
            len = len - k;
            fill = '.';
        }

        sb.append(src, 0, len);

        for (int i = 0; i < k; i++)
            sb.append(fill);
    }

}
