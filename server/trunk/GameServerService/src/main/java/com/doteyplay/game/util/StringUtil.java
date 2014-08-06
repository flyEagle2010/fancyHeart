package com.doteyplay.game.util;


public class StringUtil {

	// 将枚举里面的name格式化成需要的格式.Example:LEVEL_UP->LevelUp
	public static String formatClazzName(String name) {
		String[] names = name.split("_");
		StringBuffer clazzName = new StringBuffer();
		for (int i = 0; i < names.length; i++) {
			clazzName.append(names[i].substring(0, 1).toUpperCase()).append(
					names[i].substring(1).toLowerCase());
		}
		return clazzName.toString();
	}
	
	/**
	 * 取字符串的长度；汉字为2，英文为1；
	 * 
	 * @param str
	 * @return
	 */
	public static int length(String str){
		int len = 0;
		
		if(str == null)
			return len;
		char[] c = str.toCharArray();
		for(int i = 0; i < str.length(); i++){
			len++;
			if(!isLetter(c[i])){
				len++;
			}
		}
		return len;
	}
	
	/**
	 * 判断一个字符是ASCILL字符还是其他字符（如汉字，等其他字符）
	 * @param c
	 * @return true
	 */
	public static boolean isLetter(char c){
		int k  = 0x90;
		return c / k == 0? true:false;
	}
	
	
	/**
	 * 
	 * 取指定字符串，的长度；
	 * 如果是截断位置是中文，则把这个字符返回；（返回长度+1）
	 */
	public static String subString(String origin, int len){
		if(origin == null || origin.equals("") || len < 1)
			return "";
		if(len > StringUtil.length(origin)){
			return origin;
		}
		//判断，len的位置是字符还是中文；是中文，返回长度+1
		if( isChinese(origin.substring(len-1, len)) ){
			len ++;
		}
		byte[] strByte = new byte[len];
		
		System.arraycopy(origin.getBytes(),0, strByte, 0, len);
		int count = 0;
		for(int i= 0; i < len; i++){
			int value = (int)strByte[i];
			if(value < 0){
				count++;
			}
		}
		if(count %2 != 0){
			len ++;
		}
		return new String(strByte, 0,len);
	}
	
	/**
	 * 判断字符是不是中文；
	 * @param chineseStr，
	 * @return 是：返回true、不是中文返回fasle
	 */
	private static boolean isChinese(String chineseStr){
		if(chineseStr.matches("[\\u4e00-\\u9fbb]+")){
			return true;
		}
		return false;
	}
}
