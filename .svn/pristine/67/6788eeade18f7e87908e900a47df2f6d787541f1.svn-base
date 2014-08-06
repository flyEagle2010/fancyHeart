/**
 * 
 */
package com.doteyplay.game.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 平均执行时间2.2ms一条
 */
public class WordFilter {

	private Map<String, Pattern> blackMap = new HashMap<String, Pattern>();
	private List<String> whiteList = new ArrayList<String>();
	private static Logger logger = Logger.getLogger(WordFilter.class);
	private String[] badnum = new String[] {  };//2011-04-21 要显示地图坐标;删除  "64", "722", "89"
	private static final String CODENAME = "EEEEEE";

	public static WordFilter newChatFilter() {
		WordFilter filter = new WordFilter();
		filter.loadWhiteFile("/filterword_white_chat.txt");
		filter.laodBlackFile("/filterword_chat.txt");
		return filter;
	}

	public static WordFilter newNameFilter() {
		WordFilter filter = new WordFilter();
		filter.laodBlackFile("/filterword_name.txt");
		return filter;
	}

	/**
	 * 转半角 : 全角空格为12288，半角空格为32 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 * 
	 * @param input
	 * @return 半角化的字符
	 */
	private static String toBanJiao(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	private void loadWhiteFile(String string) {
		ArrayList<String> list = readFile(string);
		
		if(list==null || list.isEmpty()){
			return;
		}
		
		List<String> newList = new ArrayList<String>(list.size());
		for (String line : list) {
			newList.add(line);
		}
		this.whiteList = newList;
	}

	private void laodBlackFile(String string) {
		ArrayList<String> list = readFile(string);
		
		if(list==null || list.isEmpty()){
			return;
		}
		
		HashMap<String, Pattern> newMap = new HashMap<String, Pattern>();
		for (String line : list) {
			newMap.put(line, Pattern.compile(line, Pattern.CASE_INSENSITIVE));
		}
		this.blackMap = newMap;
	}

	// private static Pattern
	// numPattern=Pattern.compile("EEEEEE([0-9][0-9][0-9][0-9])");

	private String whiteExcape(String orgStr) {
		
		if(whiteList==null || whiteList.isEmpty()){
			return orgStr;
		}
		
		DecimalFormat df = new DecimalFormat("0000");
		for (int i = 0; i < whiteList.size(); i++) {
			orgStr = orgStr.replace(whiteList.get(i), CODENAME + df.format(i));
		}
		return orgStr;
	}

	private String whiteBack(String newStr) {
		
		if(whiteList==null || whiteList.isEmpty()){
			return newStr;
		}
		
		DecimalFormat df = new DecimalFormat("0000");
		for (int i = 0; i < whiteList.size(); i++) {
			newStr = newStr.replace(CODENAME + df.format(i), whiteList.get(i));
		}
		return newStr;
	}

	/**
	 * 字符串是否包含过滤字符,不区分大小写版本
	 * 
	 * @author He Yizhou
	 * @param orgStr
	 *            原始字符串
	 * @return 是否包含
	 */
	public boolean isContainWord(String orgStr) {
		
		if(blackMap==null || blackMap.isEmpty()){
			return false;
		}
		
		String normalized = toBanJiao(orgStr);
		normalized = whiteExcape(normalized);
		Set<String> keys = blackMap.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Pattern pattern = blackMap.get(key);
			if (pattern.matcher(normalized).find())
				return true;
		}
		// check bad num
		for (int i = 0; i < badnum.length; i++) {
			if (normalized.contains(badnum[i]))
				return true;
		}
		return false;
	}

	public String replaceFilterWord(String orgStr, String mask) {
		
		if(blackMap==null || blackMap.isEmpty()){
			return orgStr;
		}
		
		Set<String> keys = blackMap.keySet();
		String replaced = toBanJiao(orgStr);
		replaced = whiteExcape(replaced);
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = iterator.next();
//			System.out.println(key);
			Pattern pattern = blackMap.get(key);
			Matcher matcher = pattern.matcher(replaced);
			if (matcher.find()) {
				// replaced = matcher.replaceAll(makeChar(mask,key.length()));
				replaced = matcher.replaceAll(mask);
			}
		}
		replaced = whiteBack(replaced);

		for (int i = 0; i < badnum.length; i++) {
			replaced = replaced.replace(badnum[i], "*");
		}
		return replaced;
	}

	private ArrayList<String> readFile(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader br = null;

		try {

			inputStream = WordFilter.class.getResourceAsStream(fileName);
			inputStreamReader = new InputStreamReader(inputStream, "GBK");
			br = new BufferedReader(inputStreamReader);
			String temp = null;
			while ((temp = br.readLine()) != null) {
				list.add(temp.trim());
			}

		} catch (Exception e) {
			logger.error("读取文件失败",e);

		} finally {
			try {

				if (br != null) {
					br.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				logger.error("关闭数据流失败",e);
			}
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		String cmd = "一次机缘际遇下，我「被逼」自己踏上旅途，但自此却爱上了自助游，尤其是自己一人成行那种 wap.aaa.com";
		WordFilter wf = WordFilter.newChatFilter();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			wf.replaceFilterWord(cmd, "*");
		}
		long cost = System.currentTimeMillis() - begin;
		System.out.println(cost);

	}
}

class StringEntry {
	public String orgStr;
	public String escapeStr;
	public HashMap<String, String> cache = new HashMap<String, String>();
}