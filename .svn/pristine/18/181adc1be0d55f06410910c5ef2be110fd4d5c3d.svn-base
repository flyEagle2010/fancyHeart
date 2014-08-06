package com.doteyplay.game.util.message;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.doteyplay.core.resourcebundle.IMultipleLanguageSetting;
import com.doteyplay.core.resourcebundle.WordResource;
import com.doteyplay.core.resourcebundle.util.fieldset.EnumField;
import com.doteyplay.core.resourcebundle.util.fieldset.FieldFactory;

/**
 * 常量加载器，用于将系统内的常量统一加载进来。
 * 
 * @author 
 * 
 */
public class ConstantLoader {
	private final static Logger logger = Logger.getLogger(ConstantLoader.class);
	/**
	 * 常量文件，可以定义多个.如果是多个，对应的常量要变更。 注意，在这里定义的常量文件中，所有final
	 * static的变量都要修改成static的，否则加载会失败。
	 */
	private static final String CONSTANT_FILE[] = {
			"com.doteyplay.messageconstants.constants.ArrayStringConstants",
			"com.doteyplay.game.constants.I18NMsgConstants",
	};

	private static final String RESOURCE_KEY[] = {"ArrayStringConstants.",
			"I18NMsgConstants.",
	/*
	 * "ArrayStringConstants.", "ArrayStringConstants."
	 */
	};

	// /**
	// * 加载资源常量同时赋值
	// */
	// public static void loadConstant() {
	// Class clazz = null;
	// String value = null;
	//
	// for (int i = 0; i < CONSTANT_FILE.length; i++) {
	// try {
	// clazz = ClassLoader.getSystemClassLoader().loadClass(CONSTANT_FILE[i]);
	// for (Field field : clazz.getFields()) {
	// value = getResource(RESOURCE_KEY[i], field.getName());
	// if ((value != null) && (value.length() > 0)) {
	// FieldFactory.setFieldValue(field, value);
	// }
	// }
	// } catch (Exception e) {
	// logger.error("加载常量错误，中断加载:" + e.getMessage());
	// }
	// }
	// }
	/**
	 * 加载资源常量同时赋值
	 */
	public static void loadConstant() {
		try {
			for (int i = 0; i < CONSTANT_FILE.length; i++) {
				Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(
						CONSTANT_FILE[i]);
				String value = null;
				if (IMultipleLanguageSetting.class.isAssignableFrom(clazz)
						&& clazz.isEnum()) {
					for (Object obj : clazz.getEnumConstants()) {
						value = getResource(
								RESOURCE_KEY[i],
								clazz.getSimpleName() + "."
										+ ((Enum<?>) obj).name());
						if (value != null && value.length() > 0) {
							new EnumField().setField(
									(IMultipleLanguageSetting) obj, value);
						}
					}
				} else {
					for (Field field : clazz.getFields()) {
						value = getResource(RESOURCE_KEY[i], field.getName());
						if (value != null && value.length() > 0) {
							FieldFactory.setFieldValue(field, value);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("加载常量错误，中断加载:" + e.getMessage());
		}
	}

	private static String getResource(String prefix, String key) {
		String value = WordResource.get(prefix + key);
		if (value == null || value.length() == 0 || value.equals(prefix + key)) {
			return null;
		}
		return value;
	}

//	public static void main(String[] args) {
//		// 输出常量
//		printTest();
//		// 加载resource文件
//		loadResource();
//		// 变更常量
//		// loadConstant();
//		// 输出变更结果
//		printTest();
//	}

//	private static final String SERVER_CONFIG_FILE = "debug_server_params.xml";
//	private static String RESOURCE_BUNDLE_FILE = "";
//
//	private static void loadResource() {
//		SAXReader saxReader = new SAXReader();
//		try {
//			Document paramsXMLDoc = saxReader.read(ResourceMaker.class
//					.getClassLoader().getResourceAsStream(SERVER_CONFIG_FILE));
//			Element root = paramsXMLDoc.getRootElement();
//			RESOURCE_BUNDLE_FILE = root.elementText("resource_bundle_file");
//			ServerConfig.RESOURCE_BUNDLE_FILE = RESOURCE_BUNDLE_FILE;
//			if (logger.isInfoEnabled())
//				logger.info("加载资源文件配置,资源文件为" + RESOURCE_BUNDLE_FILE + "\r\n");
//			WordResource.init();
//			if (logger.isInfoEnabled())
//				logger.info("资源文件加载完毕\r\n");
//		} catch (Exception e) {
//			
//		}
//
//	}

//	private static void printTest() {
//		// System.out.println("SIMPLE_STR=" + ConstantTest.SIMPLE_STR);
//		// System.out.println("==========");
//		for (int i = 0; i < ConstantTest.SIMPLE_ONE.length; i++) {
//			// System.out.println("ConstantTest.SIMPLE_ONE[" + i + "]"
//			// + ConstantTest.SIMPLE_ONE[i]);
//		}
//		// System.out.println("==========");
//		for (int i = 0; i < ConstantTest.SIMPLE_TWO.length; i++) {
//			for (int j = 0; j < ConstantTest.SIMPLE_TWO[i].length; j++) {
//				// System.out.println("ConstantTest.SIMPLE_MUL[" + i + "][" + j
//				// + "]" + ConstantTest.SIMPLE_TWO[i][j]);
//			}
//		}
//		// System.out.println("==========");
//		for (int i = 0; i < ConstantTest.SIMPLE_THREE.length; i++) {
//			for (int j = 0; j < ConstantTest.SIMPLE_THREE[i].length; j++) {
//				for (int k = 0; k < ConstantTest.SIMPLE_THREE[i][j].length; k++) {
//					// System.out.println("ConstantTest.SIMPLE_MUL[" + i + "]["
//					// + j + "][" + k + "]"
//					// + ConstantTest.SIMPLE_THREE[i][j][k]);
//				}
//			}
//		}
//		// System.out.println("---------------------");
//		// System.out.println("CONSUM_TYPE_INSURANCE=" +
//		// ArrayStringConstants.CONSUM_TYPE_INSURANCE);
//		for (int i = 0; i < ArrayStringConstants.CHARGING_SYSTEM_INFOS.length; i++) {
//			for (int j = 0; j < ArrayStringConstants.CHARGING_SYSTEM_INFOS[i].length; j++) {
//				// System.out.println("ArrayStringConstants.CHARGING_SYSTEM_INFOS["
//				// + i + "][" + j
//				// + "]" + ArrayStringConstants.CHARGING_SYSTEM_INFOS[i][j]);
//			}
//		}
//
//		for (int i = 0; i < ArrayStringConstants.GANG_BATTLE_TYPES.length; i++) {
//			// System.out.println("GANG_BATTLE_TYPES[" + i + "]" +
//			// ArrayStringConstants.GANG_BATTLE_TYPES[i]);
//		}
//	}
}
