package com.doteyplay.core.resourcebundle;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.doteyplay.core.util.StrUtils;

/**
 * 读取resource
 * 
 * @author 
 * 
 */
public class LanguageResource {

	public static final String DEFAULT_LANGUAGE = "zh_CN";
	public static final String DEFAULT_BUNDLE_FILE = "resource";

	public static class ResClassLoader extends ClassLoader {
		ResClassLoader(ClassLoader parent) {
			super(parent);
		}
	}

//	private static Map<String, ResourceBundle> resBunder = new ConcurrentHashMap<String, ResourceBundle>();
	//因为只是初始化第一次读取，所以不做线程安全的，这样快
	private static Map<String, ResourceBundle> resBunder = new HashMap<String, ResourceBundle>();

	private static long lastModifiedTimeCn;

	private static void init(String language, String bundleFile) {
		clearCache();

		// String language = BaseConfig.RESOURCE_BUNDLE_LANGUAGE;

		if (StrUtils.empty(language)) {
			language = DEFAULT_LANGUAGE;
		}

		String[] lang = language.split(",");
		for (String l : lang) {
			Locale.setDefault(new Locale(l.substring(0, l.indexOf("_")), l
					.substring(l.indexOf("_") + 1)));
			ResourceBundle res = ResourceBundle.getBundle(bundleFile
			/** BaseConfig.RESOURCE_BUNDLE_FILE */
			, Locale.getDefault(), new ResClassLoader(LanguageResource.class.getClassLoader()));
			resBunder.put(l, res);
		}
	}

	private static void clearCache() {
		ResourceBundle.clearCache();
	}

	private static boolean needRefresh(String key,
			String bundleFile) {
		// String tmpFilename = DebugServer.RESOURCE_BUNDLE_FILE + "_" + key +
		// ".properties";
		String tmpFilename = new StringBuilder(bundleFile
		/** BaseConfig.RESOURCE_BUNDLE_FILE */
		).append("_").append(key).append(".properties").toString();
		return CheckFile(tmpFilename, key);
	}

	private static boolean CheckFile(String file, String key) {
		boolean modifyed = false;
		try {
			File tmpFile = new File(java.net.URLDecoder.decode(
					LanguageResource.class.getClassLoader()
							.getResource("").getPath(), "UTF-8")
					+ file);
			if (tmpFile != null && tmpFile.exists()) {
				modifyed = lastModifiedTimeCn < tmpFile.lastModified();
				lastModifiedTimeCn = tmpFile.lastModified();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modifyed;
	}

	public static ResourceBundle getBundle(String language,
			String bundleFile) {
		return getBundle(language, true, bundleFile);
	}

	public static ResourceBundle getBundle(String language,
			boolean check, String bundleFile) {
		if (check && needRefresh(language, bundleFile)) {
			init(language, bundleFile);
		}
		return resBunder.get(language);
	}
}
