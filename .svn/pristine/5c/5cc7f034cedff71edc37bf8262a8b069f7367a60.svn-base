package com.doteyplay.messageconstants;


/**
 * Ìæ»»{}
 * 
 */
public class MessageUtil {

	public static String getMessage(String message, String... param) {
		if (param == null)
			return message;

		int i = 0;
		for (String str : param) {
			if (str == null)
				str="";
			i++;
			message = message.replaceAll("\\{" + i + "\\}", str);
		}
		return message;
	}
}
