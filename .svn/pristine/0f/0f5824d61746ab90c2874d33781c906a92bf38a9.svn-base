package com.doteyplay.core.resourcebundle.util.convert;

import java.util.ArrayList;

/**
 * 类json格式的处理。完成将诸如{{},{}}这样的内容解析为一个String数组
 * 
 * @author 
 * 
 */
public class Convert {

	/**
	 * 根据字符串返回多维数组
	 * 
	 * @return
	 */
	public static Object[] convert(String str) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		parse(list, str);

		Object[] array = new Object[list.size()];
		list.toArray(array);
		return array;
	}

	private static void parse(ArrayList<Object> list, String str) throws Exception {
		Tokener tokener = new Tokener(str);
		if (tokener.more()) {
			parseValue(list, tokener, true);
		}
	}

	private static void parseValue(ArrayList<Object> list, Tokener tokener, boolean top)
			throws Exception {
		char c;
		boolean finished = false; // 是否解析完当前数组对象
		while (tokener.more() && !finished) {
			c = tokener.next();
			if (c == ',')
				continue;
			if (c == '{') {
				if (!top) {
					ArrayList<Object> l = new ArrayList<Object>();
					parseValue(l, tokener, false);
					Object[] array = new Object[l.size()];
					l.toArray(array);
					list.add(array);
				} else {
					top = false;
				}
			} else {
				tokener.back(); // 退回到引号重新解析
				StringBuffer sb;
				boolean newStr = false; // 是否是一个新的String对象
				while (!finished && !tokener.end()) {
					c = tokener.next(); // 读取第一个
					sb = new StringBuffer();
					boolean next = false;
					while (c >= ' ' && !next && !finished) {
						if (c == '"') { // 第一个是引号的话表示新String的开始
							newStr = !newStr;
							if (newStr) // 是引号则读取下一个
								c = tokener.next();
							else
								next = true; //表示中断而且有数据
						}
						if (c == ',' && !newStr) { // 是逗号并且不是新的String，则读取下一个
							c = tokener.next();
						}

						if (",}\"".indexOf(c) < 0 // 当不是结束符并且在字符内 TODO \"的转义
								|| (c == ',' && newStr)) {
							sb.append(c);
							c = tokener.next();
						}
						if (c == '}') {
							finished = true;
						}
					}
					if (next) {
						list.add(sb.toString().trim()); // 增加到数组
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			// convert("国家聊天");
			//convert("{\"\",\"区域聊天\"}");
			//convert("{{\"多维测试11\",\"\"},{\"多维测试21\",\"多维测试22\"}}");
			//convert("{{{\"111\",\"112\"},{\"121\",\"122\"}},{{\"211\",\"212\"},{\"221\",\"222\"}}}");
//			Object[] arr = convert("{\"\", \"\", \"zz\", \"\", \"zzzz\", \"\"}");
//			System.out.println(Arrays.toString(arr));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
