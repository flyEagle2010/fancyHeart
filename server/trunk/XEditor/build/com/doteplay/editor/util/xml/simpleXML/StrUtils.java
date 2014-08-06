package com.doteplay.editor.util.xml.simpleXML;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StrUtils {

    private static final String EN = "ISO-8859-1";
    private static final String CN = "GBK";
    private static final String EMPTY = "";
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 字符串是否为空或null
     * 
     * @param param
     * @return
     */
    public static boolean empty(String param) {
        return param == null || param.trim().length() < 1;
    }

    /**
     * 返回空串
     * 
     * @param param
     * @return
     */
    public static String nvl(String param) {
        return param != null ? param : "";
    }

    public static Integer parseInteger(String param, int dftValue) {
        return new Integer(parseint(param, dftValue));
    }

    public static int parseint(String param) {
        return parseint(param, 0);
    }

    /**
     * 将一个字符串转换为int,提供一个失败时的缺省值
     * 
     * @param param
     * @param dftValue
     * @return
     */
    public static int parseint(String param, int dftValue) {
        int i = dftValue;
        try {
            if (!empty(param))
                i = Integer.parseInt(param);
        } catch (Exception exception) {
        }
        return i;
    }

    public static Byte parseByte(String param, byte dftValue) {
        return new Byte(parsebyte(param, dftValue));
    }

    public static byte parsebyte(String param) {
        return parsebyte(param, (byte) 0);
    }

    /**
     * 将一个字符串转换为byte,提供一个失败时的缺省值
     * 
     * @param param
     * @param dftValue
     * @return
     */
    public static byte parsebyte(String param, byte dftValue) {
        byte i = dftValue;
        try {
            if (!empty(param))
                i = Byte.parseByte(param);
        } catch (Exception exception) {
        }
        return i;
    }

    public static Short parseShort(String param, short dftValue) {
        return new Short(parseshort(param, dftValue));
    }

    public static short parseshort(String param) {
        return parseshort(param, (short) 0);
    }

    /**
     * 将一个字符串转换为byte,提供一个失败时的缺省值
     * 
     * @param param
     * @param dftValue
     * @return
     */
    public static short parseshort(String param, short dftValue) {
        short i = dftValue;
        try {
            if (!empty(param))
                i = Short.parseShort(param);
        } catch (Exception exception) {
        }
        return i;
    }

    public static Double parseDouble(String param, double dftvalue) {
        return new Double(parsedouble(param, dftvalue));
    }

    public static double parsedouble(String param) {
        return parsedouble(param, 0.0);
    }

    public static double parsedouble(String param, double dftValue) {
        double df = dftValue;
        try {
            if (!empty(param))
                df = Double.parseDouble(param);
        } catch (Exception exception) {
        }
        return df;
    }

    public static Float parseFloat(String param, float dftvalue) {
        return new Float(parsefloat(param, dftvalue));
    }

    public static float parsefloat(String param) {
        return parsefloat(param, (float) 0.0);
    }

    public static float parsefloat(String param, float dftValue) {
        float df = dftValue;
        try {
            if (!empty(param))
                df = Float.parseFloat(param);
        } catch (Exception exception) {
        }
        return df;
    }

    public static Long parseLong(String param, long dftValue) {
        return new Long(parselong(param, dftValue));
    }

    public static long parselong(String param, long dftValue) {
        long l = dftValue;
        try {
            if (!empty(param))
                l = Long.parseLong(param);
        } catch (Exception exception) {
        }
        return l;
    }

    public static long parselong(String param) {
        long l = 0L;
        try {
            if (!empty(param))
                l = Long.parseLong(param);
        } catch (Exception exception) {
        }
        return l;
    }

    public static Boolean parseBoolean(String param) {
        return new Boolean(parseboolean(param));
    }

    public static Boolean parseBoolean(String param, boolean dftvalue) {
        return new Boolean(parseboolean(param, dftvalue));
    }

    public static boolean parseboolean(String param) {
        return parseboolean(param, false);
    }

    public static boolean parseboolean(String param, boolean dftvalue) {
        if (empty(param))
            return dftvalue;
        switch (param.charAt(0)) {
            case 49: // '1'
            case 84: // 'T'
            case 89: // 'Y'
            case 116: // 't'
            case 121: // 'y'
                return true;
            default:
                return false;
        }
    }

    public static double doubleDefault(Double souvalue, double dftvalue) {
        return (souvalue != null) ? souvalue.doubleValue() : dftvalue;
    }

    public static float floatDefault(Float souvalue, float dftvalue) {
        return (souvalue != null) ? souvalue.floatValue() : dftvalue;
    }

    public static int intDefault(Integer souvalue, int dftvalue) {
        return (souvalue != null) ? souvalue.intValue() : dftvalue;
    }

    public static short intDefault(Short souvalue, short dftvalue) {
        return (souvalue != null) ? souvalue.shortValue() : dftvalue;
    }

    public static byte byteDefault(Byte souvalue, byte dftvalue) {
        return (souvalue != null) ? souvalue.byteValue() : dftvalue;
    }

    public static long longDefault(Long souvalue, long dftvalue) {
        return (souvalue != null) ? souvalue.longValue() : dftvalue;
    }

    public static boolean boolDefault(Boolean souvalue, boolean dftvalue) {
        return (souvalue != null) ? souvalue.booleanValue() : dftvalue;
    }

    public static String doubleToStr(double value, String format) {
        if (format == null || format.length() == 0)
            format = "#0.00";
        DecimalFormat sdf = new DecimalFormat(format);
        return sdf.format(value);
    }

    public static String doubleToStr(double value, int length) {
        if (length < 0)
            length = 2;
        if (length > 10)
            length = 10;

        if (length <= 0)
            return doubleToStr(value, "#0");
        else {
            StringBuffer tmpFormat = new StringBuffer("#0.");
            for (int i = 0; i < length; i++)
                tmpFormat.append("0");
            return doubleToStr(value, tmpFormat.toString());
        }
    }

    public static String doubleToStr(String value, int length) {
        return doubleToStr(StrUtils.parsedouble(value, 0.0), length);
    }

    public static String decimalToStr(double value, String format) {
        if (format == null || format.length() == 0)
            format = "###,##0.0000";
        DecimalFormat sdf = new DecimalFormat(format);
        return sdf.format(value);
    }

    public static String decimalToStr(double value, int length) {
        if (length < 0)
            length = 2;
        if (length > 10)
            length = 10;

        if (length <= 0)
            return doubleToStr(value, "###,##0");
        else {
            StringBuffer tmpFormat = new StringBuffer("###,##0.");
            for (int i = 0; i < length; i++)
                tmpFormat.append("0");
            return doubleToStr(value, tmpFormat.toString());
        }
    }

    public static String decimalToStr(String value, int length) {
        return doubleToStr(StrUtils.parsedouble(value, 0.0), length);
    }

    public static String dateToStr(Date datetime, String pattern) {
        if (datetime != null) {
            SimpleDateFormat sdf = null;
            if (pattern == null || pattern.length() == 0)
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            else
                sdf = new SimpleDateFormat(pattern);
            return sdf.format(datetime);
        } else
            return "";
    }

    public static Date parseDate(String dateStr, String pattern) {
        return parseDate(dateStr, pattern, null);
    }

    public static Date parseDate(String dateStr, String pattern, Date dftDate) {
        Date result = dftDate;
        if (dateStr == null)
            return result;

        SimpleDateFormat formatter = new SimpleDateFormat((pattern != null) ? pattern : "yyyy-MM-dd");
        try {
            result = formatter.parse(dateStr);
        } catch (Exception e) {
            result = dftDate;
        }
        return result;
    }

    public static Properties parseParamList(String paramlist, Properties refproperties) {
        if (paramlist != null && paramlist.length() > 0 && refproperties != null) {
            String[] tmpParams = paramlist.split("[&]");
            for (int i = 0; i < tmpParams.length; i++) {
                String tmpParamItem = tmpParams[i];
                int tmpPos = tmpParamItem.indexOf('=');
                if (tmpPos > 0)
                    refproperties.setProperty(tmpParamItem.substring(0, tmpPos), tmpParamItem.substring(tmpPos + 1));
            }
        }
        return refproperties;
    }

    public static Properties parseParamList(String paramlist) {
        if (paramlist != null && paramlist.length() > 0)
            return parseParamList(paramlist, new Properties());
        else
            return null;
    }

    /**
     * 字符串进行xml过滤，是否只过滤标识符
     * 
     * @param xmlString
     * @param buffer
     * @param onlytag
     */
    public static void xmlEncoded(String xmlString, StringBuffer buffer, boolean onlytag) {
        xmlEncoded(xmlString, buffer, onlytag, false);
    }

    /**
     * 字符串进行xml过滤，是否只过滤标识符
     * 
     * @param xmlString
     * @param buffer
     * @param onlytag
     * @param encodebr
     */
    public static void xmlEncoded(String xmlString, StringBuffer buffer, boolean onlytag, boolean encodebr) {
        if (xmlString == null || buffer == null)
            return;

        char[] charData = xmlString.toCharArray();
        if (charData == null)
            return;

        for (int i = 0; i < charData.length; i++) {
            char character = charData[i];
            switch (character) {
                case 38: // '&'
                    buffer.append("&amp;");
                    break;
                case 34: // '"'
                    buffer.append("&quot;");
                    break;
                case 60: // '<'
                    buffer.append("&lt;");
                    break;
                case 62: // '>'
                    buffer.append("&gt;");
                    break;
                case 10: // '\n'
                    if (encodebr)
                        buffer.append("&#x0A;");
                    else
                        buffer.append(character);
                    break;
                case 13: // '\r'
                    if (encodebr)
                        buffer.append("&#x0D;");
                    else
                        buffer.append(character);
                    break;
                case 9: // '\t'
                    if (encodebr)
                        buffer.append("&#x09;");
                    else
                        buffer.append(character);
                    break;
                default:
                    if (onlytag)
                        buffer.append(character);
                    else {
                        if (character < 32) {
                            buffer.append("?");
                        } else if (character > '\177') {
                            buffer.append("&#x");
                            buffer.append(Integer.toHexString(character).toUpperCase());
                            buffer.append(";");
                        } else {
                            buffer.append(character);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 字符串进行xml过滤
     * 
     * @param xmlString
     * @param buffer
     */
    public static void xmlEncoded(String xmlString, StringBuffer buffer) {
        xmlEncoded(xmlString, buffer, false);
    }

    /**
     * 字符串进行xml过滤
     * 
     * @param str
     * @return
     */
    public static String xmlEncoded(String str) {
        return xmlEncoded(str, false);
    }

    /**
     * 字符串进行xml过滤
     * 
     * @param str
     * @param onlytag
     * @return
     */
    public static String xmlEncoded(String str, boolean onlytag) {
        return xmlEncoded(str, onlytag, false);
    }

    /**
     * 字符串进行xml过滤
     * 
     * @param str
     * @param onlytag
     * @param encodebr
     * @return
     */
    public static String xmlEncoded(String str, boolean onlytag, boolean encodebr) {
        if (str == null)
            return "";
        else {
            StringBuffer tmpBuffer = new StringBuffer(str.length());
            xmlEncoded(str, tmpBuffer, onlytag, encodebr);
            return tmpBuffer.toString();
        }
    }

    public static String removeXml(String str) {
        if (str == null)
            return str;

        char[] charData = str.toCharArray();

        StringBuffer buffer = new StringBuffer(charData.length);
        boolean inTag = false;
        for (int i = 0; i < charData.length; i++) {
            char ch = charData[i];
            if (ch == '<')
                inTag = true;
            else if (ch == '>') {
                inTag = false;
                continue;
            }
            if (!inTag)
                buffer.append(ch);
        }
        return buffer.toString();
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

    public static String purifyNumeric(String str) {
        if (str == null)
            return str;

        int stepl = 0;
        boolean over = false;
        while (!over && stepl < str.length()) {
            if (Character.isDigit(str.charAt(stepl)))
                over = true;
            else
                stepl++;
        }
        int stepr = str.length() - 1;
        over = false;
        while (!over && stepr > stepl) {
            if (Character.isDigit(str.charAt(stepr)))
                over = true;
            else
                stepr--;
        }
        if (stepl > 0 || stepr < str.length() - 1) {
            return str.substring(stepl, stepr + 1);
        } else
            return str;
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null)
            return null;
        int pads = size - str.length();
        if (pads <= 0)
            return str;
        if (pads > 8192)
            return rightPad(str, size, String.valueOf(padChar));
        else
            return str.concat(padding(pads, padChar));
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null)
            return null;
        if (padStr == null || padStr.length() == 0)
            padStr = " ";
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0)
            return str;
        if (padLen == 1 && pads <= 8192)
            return rightPad(str, size, padStr.charAt(0));
        if (pads == padLen)
            return str.concat(padStr);
        if (pads < padLen)
            return str.concat(padStr.substring(0, pads));
        char padding[] = new char[pads];
        char padChars[] = padStr.toCharArray();
        for (int i = 0; i < pads; i++)
            padding[i] = padChars[i % padLen];

        return str.concat(new String(padding));
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null)
            return null;
        int pads = size - str.length();
        if (pads <= 0)
            return str;
        if (pads > 8192)
            return leftPad(str, size, String.valueOf(padChar));
        else
            return padding(pads, padChar).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null)
            return null;
        if (padStr == null || padStr.length() == 0)
            padStr = " ";
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0)
            return str;
        if (padLen == 1 && pads <= 8192)
            return leftPad(str, size, padStr.charAt(0));
        if (pads == padLen)
            return padStr.concat(str);
        if (pads < padLen)
            return padStr.substring(0, pads).concat(str);
        char padding[] = new char[pads];
        char padChars[] = padStr.toCharArray();
        for (int i = 0; i < pads; i++)
            padding[i] = padChars[i % padLen];

        return (new String(padding)).concat(str);
    }

    private static String padding(int repeat, char padChar) {
        if (repeat > 0) {
            String pad = String.valueOf(padChar);
            for (; pad.length() < repeat; pad = pad.concat(pad))
                ;
            return pad.substring(0, repeat);
        } else
            return "";
    }

    public static String truncateNicely(String str, int lower, int upper, String appendToEnd) {
        str = removeXml(str);
        if (upper < lower)
            upper = lower;
        if (str.length() > upper) {
            int loc = str.lastIndexOf(32, upper);
            if (loc >= lower)
                str = str.substring(0, loc);
            else
                str = str.substring(0, upper);
            str = str + appendToEnd;
        }
        return str;
    }

    public static String byteTrim(String soustr, int trimsize) {
        if (soustr == null || soustr.length() == 0 || trimsize < 1)
            return (soustr != null) ? soustr : "";

        int tmpPos = 0;
        int tmpCurByteSize = 0;
        char[] tmpChars = soustr.toCharArray();
        for (int i = 0; tmpCurByteSize < trimsize && i < tmpChars.length; i++) {
            tmpCurByteSize = tmpCurByteSize + (String.valueOf(tmpChars[i])).getBytes().length;
            tmpPos++;
        }
        if (tmpPos > 0 && tmpPos < soustr.length())
            return soustr.substring(0, tmpPos);
        else
            return soustr;
    }

    /**
     * 对字符串做base64编码
     * 
     * @param str
     * @return
     */
    public static String base64Encode(String str) {
        if (str != null && str.length() > 0)
            str = (new BASE64Encoder()).encode(str.getBytes());
        return str;
    }

    /**
     * 对byte[]做base64编码
     * 
     * @param str
     * @return
     */
    public static String base64Encode(byte str[]) {
        String encodeStr = "";
        if (str != null && str.length > 0)
            encodeStr = (new BASE64Encoder()).encode(str);
        return encodeStr;
    }

    public static String base64Decode(String str) {
        if (str != null && str.length() > 0)
            try {
                byte buf[] = (new BASE64Decoder()).decodeBuffer(str);
                str = new String(buf);
            } catch (IOException ioex) {
                String errorCode = "Error occurs when do the method of String base64Decode. Error Prompt: "
                        + ioex.toString() + "Input String: " + str;
                System.out.println(errorCode);
            }
        return str;
    }

    public static byte[] base64DecodeForByte(String str) {
        byte buf[] = (byte[]) null;
        if (str != null && str.length() > 0)
            try {
                buf = (new BASE64Decoder()).decodeBuffer(str);
            } catch (IOException ioex) {
                String errorCode = "Error occurs when do the method of byte[] base64Decode. Error Prompt: "
                        + ioex.toString() + "Input String: " + str;
                System.out.println(errorCode);
            }
        return buf;
    }

    /**
     * 对字符串做MD5
     * 
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        if (s != null) {
            try {
                byte[] strTemp = s.getBytes();
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(strTemp);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                return "";
            }
        } else
            return "";
    }

    /**
     * 对Byte[]做MD5
     * 
     * @param sbytes
     * @return
     */
    public final static String MD5(byte[] sbytes) {
        if (sbytes != null) {
            try {
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(sbytes);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                return "";
            }
        } else
            return "";
    }

    /**
     * 替换第一个匹配字符串
     * 
     * @param text
     * @param repl
     * @param with
     * @return
     */
    public static String replaceOnce(String text, String repl, String with) {
        return replace(text, repl, with, 1);
    }

    /**
     * 字符串替换
     * 
     * @param text
     * @param repl
     * @param with
     * @return
     */
    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    /**
     * 字符串替换
     * 
     * @param text
     * @param repl
     * @param with
     * @param max
     * @return
     */
    public static String replace(String text, String repl, String with, int max) {
        if (text == null || repl == null || with == null || repl.length() == 0 || max == 0)
            return text;
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = 0;
        do {
            if ((end = text.indexOf(repl, start)) == -1)
                break;
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();
        } while (--max != 0);
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 字符替换
     * 
     * @param str
     * @param searchChar
     * @param replaceChar
     * @return
     */
    public static String replaceChars(String str, char searchChar, char replaceChar) {
        if (str == null)
            return null;
        else
            return str.replace(searchChar, replaceChar);
    }

    /**
     * 字符串替换
     * 
     * @param str
     * @param searchChars
     * @param replaceChars
     * @return
     */
    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0)
            return str;
        char chars[] = str.toCharArray();
        int len = chars.length;
        boolean modified = false;
        int i = 0;
        for (int isize = searchChars.length(); i < isize; i++) {
            char searchChar = searchChars.charAt(i);
            if (replaceChars == null || i >= replaceChars.length()) {
                int pos = 0;
                for (int j = 0; j < len; j++)
                    if (chars[j] != searchChar)
                        chars[pos++] = chars[j];
                    else
                        modified = true;

                len = pos;
                continue;
            }
            for (int j = 0; j < len; j++)
                if (chars[j] == searchChar) {
                    chars[j] = replaceChars.charAt(i);
                    modified = true;
                }

        }

        if (!modified)
            return str;
        else
            return new String(chars, 0, len);
    }

    /**
     * 首字母大写
     * 
     * @param str
     * @return
     */
    public static String fUppercase(String str) {
        if (str != null && str.length() > 0) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        } else
            return str;
    }

    public static String[] simpleSplit(String str, String separatorChars) {
        if (str == null || str.length() == 0)
            return new String[0];
        if (separatorChars == null || separatorChars.length() == 0)
            return new String[] { str };

        List list = new ArrayList();
        char[] tmpSpChars = separatorChars.toCharArray();
        int len = str.length();
        int i = 0;
        int j = 0;
        int start = 0;
        char currentChar = 0;
        boolean match = false;
        boolean found = false;
        while (i < len) {
            currentChar = str.charAt(i);
            found = false;
            for (j = 0; (!found) && j < tmpSpChars.length; j++) {
                found = (currentChar == tmpSpChars[j]);
            }
            if (found) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
            } else {
                match = true;
                i++;
            }
        }
        if (match)
            list.add(str.substring(start, i));
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * Input format is [aaa];[bbb];[xxxcc]
     * 
     * @param input
     * @return
     */
    public static List<String> fromBracketsStr(String input) {
        return fromBracketsStr(input, ";");
    }

    /**
     * Input format is [aaa];[bbb];[xxxcc]
     * 
     * @param input
     * @return
     */
    public static List<String> fromBracketsStr(String input, String spchar) {
        String[] array = parseBracketsStr(input, spchar);
        if (array != null && array.length > 0) {
            List<String> list = new ArrayList<String>(array.length);
            for (String s : array) {
                list.add(s);
            }
            return list;
        } else
            return null;
    }

    /**
     * 解析[aa];[bb];[cc]格式的字符串为字符串数组
     * 
     * @param input
     * @return
     */
    public static String[] parseBracketsStr(String input) {
        return parseBracketsStr(input, ";");
    }

    /**
     * Input format is [aaa];[bbb];[xxxcc]
     * 
     * @param input
     * @return
     */
    public static String[] parseBracketsStr(String input, String spchar) {
        if (input == null || input.length() == 0)
            return null;

        if (spchar == null || spchar.length() == 0)
            spchar = ";";

//        assert input == null;
        input = input.trim().replaceAll("\\]" + spchar + "[\r\n\t]", "\\]" + spchar);
        if (input.startsWith("[") && input.length() > 2) {
            if (input.endsWith("]")) {
                input = input.substring(1, input.length() - 1);
            } else if (input.endsWith("]" + spchar)) {
                input = input.substring(1, input.length() - 2);
            }
        }
        String[] result = input.split("\\]" + spchar + "\\[");
        return result;
    }

    /**
     * Input format is aaa|bbb|ccc
     * 
     * @param input
     * @return
     */
    public static Set<String> fromSplitStr(String input) {
        if (input == null || input.length() == 0)
            return null;

        String[] array = input.split("[|]");
        Set<String> set = new HashSet<String>(array.length);
        for (String s : array) {
            set.add(s);
        }
        return set;
    }

    /**
     * D.E.Knuth V.R.Pratt & J.H.Morris matching algorithms
     * 
     * @param S Matching String
     * @param P Pattern String
     * @param fromIndex match begin index
     * @return index of matching pattern
     * @author Zenberg.D
     */
    public static int kmpIndexOf(String S, String P, int fromIndex) {

        if (S == null || S.trim().length() == 0)
            return -1;
        if (fromIndex < 0 || fromIndex > S.length())
            return -1;

        if (P == null || P.equals(""))
            return 0;
        // ---------------------------
        int lP = P.length();
        int lS = S.length();
        if (lP > lS)
            return -1;
        // ---------------------------
        // compute next[]
        int[] next = new int[lP];
        int i = 0, j = -1;

        next[0] = -1;
        while (i < lP - 1) {
            if (j == -1 || P.charAt(i) == P.charAt(j)) {
                i++;
                j++;
                if (P.charAt(i) != P.charAt(j))
                    next[i] = j;
                else
                    next[i] = next[j];
            } else
                j = next[j];
        }
        // -----------------------------
        // matching
        i = fromIndex;
        j = 0;
        while (i < lS && j < lP) {
            if (j == -1 || S.charAt(i) == P.charAt(j)) {
                i++;
                j++;
            } else
                j = next[j];
        }

        if (j == lP)
            return i - j;
        else
            return -1;
    }

    /**
     * 中文xml解码
     * 
     * @param souStr
     * @return
     */
    public static String xmlDecodedCN(String souStr) {
        return xmlDecoded(souStr, true);
    }

    /**
     * 中文xml解码
     * 
     * @param souStr
     * @return
     */
    public static String xmlDecoded(String souStr) {
        return xmlDecoded(souStr, false);
    }

    /**
     * 中文xml解码,可以选择只解析中文
     * 
     * @param souStr
     * @param onlycn
     * @return
     */
    public static String xmlDecoded(String souStr, boolean onlycn) {
        if (souStr != null && souStr.length() > 0) {
            int tmpCkPos = 0;
            int tmpFdPos = 0;
            String tmpCkStr = null;
            StringBuffer tmpBuffer = new StringBuffer(souStr.length());
            Pattern sourcePattern = Pattern.compile("(&)([#\\d\\w]{2,6})(;)");
            Matcher matcher = sourcePattern.matcher(souStr);
            while (matcher != null && matcher.find()) {
                tmpFdPos = matcher.start();
                if (tmpFdPos > tmpCkPos)
                    tmpBuffer.append(souStr.substring(tmpCkPos, tmpFdPos));
                tmpCkStr = matcher.group(2);
                if (tmpCkStr.startsWith("#")) {
                    try {
                        char tmpChar = 0;
                        if (tmpCkStr.startsWith("#x"))
                            tmpChar = (char) Integer.decode("0X" + tmpCkStr.substring(2)).intValue();
                        else
                            tmpChar = (char) StrUtils.parseint(tmpCkStr.substring(1), 0);

                        if (tmpChar > 0) {
                            if (tmpChar < 256 && onlycn) {
                                if (tmpChar == '&')
                                    tmpBuffer.append("&amp;");
                                else if (tmpChar == '<')
                                    tmpBuffer.append("&lt;");
                                else if (tmpChar == '>')
                                    tmpBuffer.append("&gt;");
                                else if (tmpChar == '"')
                                    tmpBuffer.append("&quot;");
                                else
                                    tmpBuffer.append(tmpChar);
                            } else
                                tmpBuffer.append(tmpChar);
                        } else
                            tmpBuffer.append("&" + tmpCkStr + ";");
                    } catch (Exception e) {
                        tmpBuffer.append("&" + tmpCkStr + ";");
                    }
                } else if (tmpCkStr.compareTo("nbsp") == 0)
                    tmpBuffer.append(' ');
                else if (!onlycn) {
                    if ("amp".compareTo(tmpCkStr) == 0)
                        tmpBuffer.append('&');
                    else if ("quot".compareTo(tmpCkStr) == 0)
                        tmpBuffer.append('"');
                    else if ("lt".compareTo(tmpCkStr) == 0)
                        tmpBuffer.append('<');
                    else if ("gt".compareTo(tmpCkStr) == 0)
                        tmpBuffer.append('>');
                    else
                        tmpBuffer.append("&" + tmpCkStr + ";");
                } else
                    tmpBuffer.append("&" + tmpCkStr + ";");
                tmpCkPos = matcher.end();
            }
            if (tmpCkPos < souStr.length())
                tmpBuffer.append(souStr.substring(tmpCkPos, souStr.length()));
            return tmpBuffer.toString();
        }
        return souStr;
    }

    /**
     * 中文xml解码,可以选择只解析中文
     * 
     * @param souStr
     * @param onlycn
     * @return
     */
    public static String cnUrlEncoded(String souStr, String charset) {
        if (souStr != null && souStr.length() > 0) {
            if (charset == null || charset.length() == 0)
                charset = "UTF-8";

            char[] chars = souStr.toCharArray();
            int lastCN = -1;
            StringBuffer retuBuf = new StringBuffer(souStr.length());
            for (int i = 0, Len = chars.length; i < Len; i++) {
                int byteSize = (String.valueOf(chars[i])).getBytes().length;
                if (byteSize == 2)// 中文字符
                {
                    if (lastCN < 0)
                        lastCN = i;
                } else {
                    if (lastCN >= 0 && lastCN < i) {
                        try {
                            retuBuf.append(URLEncoder.encode(souStr.substring(lastCN, i), charset));
                        } catch (Exception e) {
                            retuBuf.append(souStr.substring(lastCN, i));
                        }
                    }
                    if (chars[i] == ' ')
                        retuBuf.append("%20");
                    else
                        retuBuf.append(chars[i]);
                    lastCN = -1;
                }
            }
            if (lastCN >= 0) {
                try {
                    retuBuf.append(URLEncoder.encode(souStr.substring(lastCN), charset));
                } catch (Exception e) {
                    retuBuf.append(souStr.substring(lastCN));
                }
            }
            return retuBuf.toString();
        } else
            return souStr;
    }

    /**
     * 兼容javascript的escape
     * 
     * @param src
     * @return
     */
    public static String escape(String src) {
        if (src == null)
            return null;

        int i;
        char j;
        StringBuffer tmp = new StringBuffer(src.length());
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 兼容IE的unescape
     * 
     * @param src
     * @return
     */
    public static String unescape(String src) {
        if (src == null)
            return null;

        StringBuffer tmp = new StringBuffer(src.length());
        tmp.ensureCapacity(src.length());
        boolean error = false;
        int lastPos = 0, pos = 0;
        int tmpInt = 0;
        char ch;
        while ((lastPos < src.length()) && !error) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    try {
                        tmpInt = Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                        if (tmpInt > 256) {
                            ch = (char) tmpInt;
                            tmp.append(ch);
                        } else {
                            error = true;
                            tmp.append(src.substring(pos, pos + 6));
                        }
                    } catch (Exception e) {
                        tmp.append(src.substring(pos, pos + 6));
                    }
                    lastPos = pos + 6;
                } else {
                    try {
                        tmpInt = Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                        if (tmpInt < 128) {
                            ch = (char) tmpInt;
                            tmp.append(ch);
                        } else {
                            error = true;
                            tmp.append(src.substring(pos, pos + 3));
                        }
                    } catch (Exception e) {
                        tmp.append(src.substring(pos, pos + 3));
                    }
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        if (error)
            return src;
        else
            return tmp.toString();
    }

    public static void main(String[] args) {
        String tmpStr = "aab&fa";
        System.out.println(StrUtils.xmlEncoded(tmpStr, true));

    }

}
