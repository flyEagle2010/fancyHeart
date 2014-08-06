package com.doteyplay.utils;

import java.util.regex.Pattern;

public class ValidateUtils {

    /**
     * �ַ��Ƿ�Ϊ�ջ�null
     * 
     * @param param
     * @return
     */
    public static boolean isEmpty(String param) {
        return param == null || param.trim().length() < 1;
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        char[] charData = str.toCharArray();
        for (int i = 0; i < charData.length; i++)
            if (!Character.isDigit(charData[i]))
                return false;

        return true;
    }

    public static boolean isNumericSpace(String str) {
        if (str == null)
            return false;
        char[] charData = str.toCharArray();
        for (int i = 0; i < charData.length; i++)
            if (!Character.isDigit(charData[i]) && charData[i] != ' ')
                return false;

        return true;
    }

    public static boolean isMobileNumber(String num) {
        return Pattern.matches("0?1[3|5|8]\\d{9}", num);
    }

    /**
     * ���email��ַ�Ƿ�Ϸ�
     * 
     * @param smail
     * @return boolean
     */
    public static boolean isMail(String smail) {
        if (smail == null || smail.length() < 5)
            return false;

        int point = smail.indexOf("@");
        if (point < 1)
            return false;

        boolean b = true;
        String temp1 = smail.substring(0, point);
        String temp2 = smail.substring(point + 1);
        if (temp2.indexOf(".") == -1) {
            b = false;
        } else if (temp2.length() - temp2.lastIndexOf(".") > 5) {
            b = false;
        }
        return b;
    }
}
