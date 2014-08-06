package com.doteyplay.core.resourcebundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 获取内容
 * @author 
 *
 */
public class MultipleLanguage {
	private static final Logger logger = Logger.getLogger(MultipleLanguage.class);
	
    public static String getMessage(ResourceBundle resource, String strKey, String strDefault) {
        String strDefProp = "";
        String strTrimDefProp = "";
        try {
            String strName = trimQuote(resource.getString(strKey));
            return new String(strName.getBytes("ISO8859-1"), "UTF-8");
        }catch(NullPointerException e){
//        	logger.error("ResourceBundler文件不存在"+e.getMessage());
        	return strDefault;
        } catch (MissingResourceException e) {
            try {
                strDefProp = getDefaultMsgPropName(strKey);
                strTrimDefProp = trimQuote(resource.getString(strDefProp));
                return new String(strTrimDefProp.getBytes("ISO8859-1"), "UTF-8");
            } catch (MissingResourceException ee) {

                return strDefault;
            } catch (java.io.UnsupportedEncodingException eee) {
                return strDefault;
            }
        } catch (java.io.UnsupportedEncodingException eee) {
            return strDefault;
        }
    }

    public static String getMessage(ResourceBundle resource, String strKey, String strDefault, String[] strParams)
            throws MultipleLanguageException {

        String tmp = getMessage(resource, strKey, strDefault);
        int prevIndex = 0;
        int curIndex = tmp.indexOf("%");
        String buffer = "";
        while (curIndex >= 0) {
            buffer = buffer + tmp.substring(prevIndex, curIndex);
            char macroId = tmp.charAt(curIndex + 1);
            if (Character.isDigit(macroId) && macroId != '0') {
                try {
                    buffer = buffer + strParams[Character.digit(macroId, 10) - 1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new MultipleLanguageException(Character.digit(macroId, 10), strParams.length);
                }
            } else if (macroId == '%') {
                buffer = buffer + "%";
            } else {

                return "Unknown macro string: " + tmp.substring(curIndex, curIndex + 2);
            }
            prevIndex = curIndex + 2;
            curIndex = tmp.indexOf("%", prevIndex);
        }
        buffer = buffer + tmp.substring(prevIndex);
        return buffer;
    }

    public static String getMessage(ResourceBundle resource, String strKey, String strDefault, String strParam)
            throws MultipleLanguageException {
        String[] s = { strParam };
        return getMessage(resource, strKey, strDefault, s);
    }

    public static String getMessage(ResourceBundle resource, String strKey, String strDefault, String strParam1,
            String strParam2) throws MultipleLanguageException {
        String[] s = { strParam1, strParam2 };
        return getMessage(resource, strKey, strDefault, s);
    }

    public static String getMessage(ResourceBundle resource, String strKey, String strDefault, String strParam1,
            String strParam2, String strParam3) throws MultipleLanguageException {
        String[] s = { strParam1, strParam2, strParam3 };
        return getMessage(resource, strKey, strDefault, s);
    }

    public static boolean isEnContext(ResourceBundle resource) {
        String strLanguage = getLanguageFlag(resource);
        if (strLanguage.equals("en_US"))
            return true;
        else
            return false;

    }

    public static boolean isCnContext(ResourceBundle resource) {
        String strLanguage = getLanguageFlag(resource);
        if (strLanguage.equals("zh_CN") || strLanguage.equals(""))
            return true;
        else
            return false;

    }

    private static String trimQuote(String str) {
        str = str.trim();
        int nLen = str.length();

        if (nLen < 2) {
            return str;
        }

        if (str.charAt(0) == '"' && str.charAt(nLen - 1) == '"') {
            return str.substring(1, nLen - 1);
        }

        return str;
    }

    private static String getDefaultMsgPropName(String strPropName) {
        int nIndex = strPropName.indexOf('.');
        if (nIndex < 0) {
            return "";
        }
        return "default" + strPropName.substring(nIndex);
    }

    public static Locale getLocale(ResourceBundle resource) {
        Locale locale = Locale.CHINA;

        try {
            String strLocale = resource.getString("locale");

            if (strLocale == null || strLocale.length() != 5 || strLocale.equals("cn")) {
                return Locale.CHINA;
            }

            String strLang = strLocale.substring(0, 2);
            String strCountry = strLocale.substring(3, 5);

            locale = new Locale(strLang, strCountry);

        } catch (MissingResourceException e) {
            return Locale.CHINA;
        }

        return locale;
    }

    public static String getDateFormat(ResourceBundle resource, Locale locale, String strFormatKey,
            String strDefaultFormat, Date date) {
        String str = "";

        try {
            str = resource.getString(strFormatKey);

        } catch (MissingResourceException e) {
            str = strDefaultFormat;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(str, locale);
        String strDate = formatter.format(date);

        return strDate;
    }

    public static boolean getInternationalFlg(ResourceBundle res) {
        try {
            String temp = res.getString("international_mode");
            if (temp.equalsIgnoreCase("true")) {
                return true;
            } else {
                return false;
            }
        } catch (java.util.MissingResourceException e) {
            return false;
        }
    }

    private static String getLanguageFlag(ResourceBundle res) {
        try {
            String temp = res.getString("MESSAGE_LANGUAGE");
            return temp;
        } catch (java.util.MissingResourceException e) {
            return "";
        }
    }

    public static String getDateCheckRegularExp(ResourceBundle res) {
        try {
            String temp = res.getString("date_check_regular_expression");
            if (temp != null && temp.length() > 0)
                return temp;
            else
                return "/^\\d{1,4}-\\d{1,2}-\\d{1,2}$/g";

        } catch (java.util.MissingResourceException e) {
            return "/^\\d{1,4}-\\d{1,2}-\\d{1,2}$/g";
        }
    }
}
