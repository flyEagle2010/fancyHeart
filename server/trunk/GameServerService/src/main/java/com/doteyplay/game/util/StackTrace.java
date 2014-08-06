package com.doteyplay.game.util;

public class StackTrace
{
	public static String getStackTrace(Class cls)
	{
		StackTraceElement[] elements = (new Throwable()).getStackTrace();
		StringBuffer buf = new StringBuffer();
		buf.append("Stack for " + cls.getName() + ":");
		for (int i = 0; i < elements.length; i++)
		{
			buf.append("/n    " + elements[i].getClassName() + "."
					+ elements[i].getMethodName() + "("
					+ elements[i].getFileName() + ":"
					+ elements[i].getLineNumber() + ")");
		}
		return buf.toString();
	}
}
