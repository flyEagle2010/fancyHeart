package com.doteyplay.game.util;

import java.lang.reflect.Field;

public class SimpleReflectUtils {
	/**
	 * 拼装对象中,所有的属性的值.
	 * @param obj
	 * @return
	 */
	public static String reflect(Object obj) {
		if (obj == null)
			return "";
		StringBuffer sb = new StringBuffer();
		Field[] fields = obj.getClass().getDeclaredFields();
		sb.append("className:").append(obj.getClass().getSimpleName())
				.append(";");
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			// 字段名
			sb.append(fields[j].getName()).append("=").append("[");
			try {
				sb.append(fields[j].get(obj)).append("]");
				if (j != fields.length - 1) {
					sb.append(",");
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 其他类型。。。
		}
		return sb.toString();
	}

}
