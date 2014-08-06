package com.doteyplay.core.resourcebundle;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 单一的Resource接口
 * 
 * @author 
 * 
 */
public class WordResource {

	/**
	 * 只获取唯一的bundle
	 */
	private ResourceBundle bundle;

	public WordResource() {
	}

	public void _init(String language, String bundleFile) {
		bundle = ResourceBundle.getBundle(bundleFile, new Locale(language
				.substring(0, language.indexOf("_")), language
				.substring(language.indexOf("_") + 1)));
	}

	public String _get(String key) {
		if (key == null) {
			return "";
		}

		return MultipleLanguage.getMessage(bundle, key, key);
	}

	public String _get(String key, String defvalue) {
		if (key == null) {
			return "";
		}

		return MultipleLanguage.getMessage(bundle, key, defvalue);
	}

	/**
	 * 单例，中文
	 */
	private static WordResource _instantce = new WordResource();

	private static WordResource getInstance() {
		return _instantce;
	}

	/**
	 * 默认初始化。工具里面用
	 */
	public static void init() {
		getInstance()._init("zh_CN", "resource");
	}

	// 初始化语言
	public static void init(String language, String bundleFile) {
		getInstance()._init(language, bundleFile);
	}

	/**
	 * 根据键值获取对应的字符串
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return getInstance()._get(key);
	}

	/**
	 * 根据键值获取对应的字符串,如果没有则返回默认值
	 * 
	 * @param key
	 * @param dftValue
	 * @return
	 */
	public static String get(String key, String dftValue) {
		return getInstance()._get(key, dftValue);
	}
}
