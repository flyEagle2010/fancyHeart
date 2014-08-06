package com.doteyplay.core.configloader;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 默认的xml常量反射，只针对key,value方式. 用于将数值赋值到某个类的数值上。
 * 
 * @author 
 * 
 */
public class KeyValueXMLReflection {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(KeyValueXMLReflection.class);

	/**
	 * 将若干xml值反射到一个类上
	 * 
	 * @param c
	 *            需要反射的类
	 * @param xmlFileName
	 *            对应的xml文件
	 */
	public static void reflect(Class<?> c, String... xmlFileName) {
		SAXReader saxReader = new SAXReader();
		try {
			for (String xmlName : xmlFileName) {
				Document paramsXMLDoc = saxReader
						.read(KeyValueXMLReflection.class.getClassLoader()
								.getResourceAsStream(xmlName));
				Element root = paramsXMLDoc.getRootElement();
				Field fields[] = c.getDeclaredFields();
				String value;
				Element e;
				for (Field f : fields) {
					// 表示数组
					if (f.getType().isArray()) {
						e = root.element(f.getName().toLowerCase());
						if (e == null || e.elements().size() == 0) {
							logger.error(c.getName() + "." + f.getName()
									+ "对应数组项为空，采用默认值");
							continue;
						}
						invokeArray(f, e, c);

					} else {// 表示单一
						value = root.elementText(f.getName().toLowerCase());
						if (value == null) {
							logger.error(c.getName() + "." + f.getName()
									+ "对应项为空，采用默认值");
							continue;
						}
						invokeSingle(f, value, c);
					}

				}
			}
		} catch (Exception e) {
			logger.error("初始化服务器相关配置参数失败，错误信息：" + e.getMessage(), e);
		}
	}

	private static void invokeSingle(Field f, String value, Class<?> c)
			throws Exception {
		if (f.getType() == java.lang.Byte.class || f.getType() == byte.class) {
			setByte(f, value);
		} else if (f.getType() == java.lang.Boolean.class
				|| f.getType() == boolean.class) {
			setBoolean(f, value);
		} else if (f.getType() == java.lang.Short.class
				|| f.getType() == short.class) {
			setShort(f, value);
		} else if (f.getType() == java.lang.Integer.class
				|| f.getType() == int.class) {
			setInteger(f, value);
		} else if (f.getType() == java.lang.Long.class
				|| f.getType() == long.class) {
			setLong(f, value);
		} else if (f.getType() == java.lang.String.class) {
			setString(f, value);
		} else if (f.getType() == java.sql.Timestamp.class) {
			setTimeStamp(f, value);
		} else if (f.getType() == java.util.Date.class) {
			setDate(f, value);
		} else {
			logger.error("不支持的单一数据类型" + c.getName() + ":"
					+ f.getName().toLowerCase());
		}
	}

	public static void invokeArray(Field f, Element e, Class<?> c)
			throws Exception {
		invokeArray(f, e, c, null);
	}

	public static Object invokeArray(Field f, Element e, Class<?> c, Class<?> type)
			throws Exception {
		@SuppressWarnings("unchecked")
		List<Element> list = e.elements();
		int size = list.size();
		boolean needReturn = true;
		if (type == null) {
			type = f.getType();
			needReturn = false;

			if (list.get(0).elements().size() > 1) {
				invokeArray2D(f, e, c);
				return null;
			}
		}

		if (type == java.lang.Byte[].class || type == byte[].class) {
			byte[] array = new byte[size];
			for (int i = 0; i < size; i++) {
				array[i] = Byte.valueOf(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == java.lang.Boolean[].class || type == boolean[].class) {
			boolean[] array = new boolean[size];
			for (int i = 0; i < size; i++) {
				array[i] = Boolean.valueOf(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == java.lang.Short[].class || type == short[].class) {
			short[] array = new short[size];
			for (int i = 0; i < size; i++) {
				array[i] = Short.valueOf(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == java.lang.Integer[].class || type == int[].class) {
			int[] array = new int[size];
			for (int i = 0; i < size; i++) {
				array[i] = Integer.valueOf(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == java.lang.Long[].class || type == long[].class) {
			long[] array = new long[size];
			for (int i = 0; i < size; i++) {
				array[i] = Long.valueOf(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == String[].class) {
			String[] array = new String[size];
			for (int i = 0; i < size; i++) {
				array[i] = list.get(i).getText();
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == java.sql.Timestamp[].class) {
			Timestamp[] array = new Timestamp[size];
			for (int i = 0; i < size; i++) {
				array[i] = Timestamp.valueOf(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else if (type == Date[].class) {
			Date[] array = new Date[size];
			for (int i = 0; i < size; i++) {
				array[i] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(list.get(i).getText());
			}
			if (needReturn)
				return array;
			else
				f.set(null, array);
		} else {
			logger.error("不支持的数组数据类型" + c.getName() + ":"
					+ f.getName().toLowerCase());
		}
		return null;
	}

	private static void invokeArray2D(Field f, Element e, Class<?> c)
			throws Exception {
		@SuppressWarnings("unchecked")
		List<Element> list = e.elements();
		int size = list.size();
		if (f.getType() == java.lang.Byte[][].class
				|| f.getType() == byte[][].class) {
			byte[][] array = new byte[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (byte[]) invokeArray(f, list.get(i), c, byte[].class);
			}
			f.set(null, array);
		} else if (f.getType() == java.lang.Boolean[][].class
				|| f.getType() == boolean[][].class) {
			boolean[][] array = new boolean[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (boolean[]) invokeArray(f, list.get(i), c,
						boolean[].class);
			}
			f.set(null, array);
		} else if (f.getType() == java.lang.Short[][].class
				|| f.getType() == short[][].class) {
			short[][] array = new short[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (short[]) invokeArray(f, list.get(i), c,
						short[].class);
			}
			f.set(null, array);
		} else if (f.getType() == java.lang.Integer[][].class
				|| f.getType() == int[][].class) {
			int[][] array = new int[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (int[]) invokeArray(f, list.get(i), c, int[].class);
			}
			f.set(null, array);
		} else if (f.getType() == java.lang.Long[][].class
				|| f.getType() == long[][].class) {
			long[][] array = new long[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (long[]) invokeArray(f, list.get(i), c, long[].class);
			}
			f.set(null, array);
		} else if (f.getType() == String[][].class) {
			String[][] array = new String[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (String[]) invokeArray(f, list.get(i), c,
						String[].class);
			}
			f.set(null, array);
		} else if (f.getType() == java.sql.Timestamp[][].class) {
			Timestamp[][] array = new Timestamp[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (Timestamp[]) invokeArray(f, list.get(i), c,
						Timestamp[].class);
			}
			f.set(null, array);
		} else if (f.getType() == Date[][].class) {
			Date[][] array = new Date[size][];
			for (int i = 0; i < size; i++) {
				array[i] = (Date[]) invokeArray(f, list.get(i), c, Date[].class);
			}
			f.set(null, array);
		} else {
			logger.error("不支持的数组数据类型" + c.getName() + ":"
					+ f.getName().toLowerCase());
		}

	}

	private static void setByte(Field f, String value) throws Exception {
		f.setByte(null, Byte.valueOf(value));
	}

	private static void setBoolean(Field f, String value) throws Exception {
		f.setBoolean(f, Boolean.valueOf(value));
	}

	private static void setShort(Field f, String value) throws Exception {
		f.setShort(null, Short.valueOf(value));
	}

	private static void setInteger(Field f, String value) throws Exception {
		f.setInt(null, Integer.valueOf(value));
	}

	private static void setLong(Field f, String value) throws Exception {
		f.setLong(null, Long.valueOf(value));
	}

	private static void setString(Field f, String value) throws Exception {
		f.set(null, value);
	}

	private static void setTimeStamp(Field f, String value) throws Exception {
		f.set(null, Timestamp.valueOf(value));
	}

	private static void setDate(Field f, String value) throws Exception {
		f.set(null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
	}

//	public static int[][] TT;

	public static void main(String[] args) {
		reflect(KeyValueXMLReflection.class, "server-config.xml");
//		System.out.println(TT);
//		SAXReader saxReader = new SAXReader();
//		try {
//			Document paramsXMLDoc = saxReader.read(KeyValueXMLReflection.class
//					.getClassLoader().getResourceAsStream("tt.xml"));
//			Element root = paramsXMLDoc.getRootElement();
//			Class c = KeyValueXMLReflection.class;
//			Field fields[] = c.getDeclaredFields();
//			String value;
//			Element e;
//			for (Field f : fields) {
//				// 表示数组
//				if (f.getType().isArray()) {
//					e = root.element(f.getName().toLowerCase());
//					if (e == null || e.elements().size() == 0) {
//						logger.error(c.getName() + "." + f.getName()
//								+ "对应数组项为空，采用默认值");
//						continue;
//					}
//
//					invokeArray(f, e, c);
//					System.out.println(TT);
//				}
//			}
//
//		} catch (Exception e) {
//			logger.error("初始化服务器相关配置参数失败，错误信息：" + e.getMessage(), e);
//		}
	}

}
