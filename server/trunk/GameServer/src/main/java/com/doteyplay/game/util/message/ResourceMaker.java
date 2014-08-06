package com.doteyplay.game.util.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.doteyplay.core.resourcebundle.LanguageResource;
import com.doteyplay.core.resourcebundle.MultipleLanguage;
import com.doteyplay.core.resourcebundle.WordResource;
import com.doteyplay.game.config.ServerConfig;
import com.doteyplay.messageconstants.constants.RoleMsgConstants;
import com.doteyplay.messageconstants.constants.SystemMsgConstants;

/**
 * 中文Resource_cn.Properties生成
 * 
 * @author 
 * 
 *  可能有问题了。 2012.03.15;
 */
public class ResourceMaker {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ResourceMaker.class);

	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			String param = args[0].trim();
			if (param.equalsIgnoreCase("-a")) {
//				System.out.print("追加新的内容到资源文件中\r\n");
				append();
			} else if (param.equals("-All")) {
//				System.out.print("重新创建资源文件\r\n");
				rebuild();
			} else {
				printHelp();
			}
		} else {
			printHelp();
		}
	}

	private static void printHelp() {
		StringBuffer buffer = new StringBuffer("Usage:\r\n");
		buffer.append("  -A,a: 追加新的键值到资源文件中\r\n");
		buffer.append("  -All: 重新创建资源文件\r\n");
//		System.out.print(buffer);
	}

	private static final String SERVER_CONFIG_FILE = "server-config.xml";
	private static String RESOURCE_BUNDLE_FILE = "";

	private static void append() {
		SAXReader saxReader = new SAXReader();
		try {
			Document paramsXMLDoc = saxReader.read(ResourceMaker.class
					.getClassLoader().getResourceAsStream(SERVER_CONFIG_FILE));
			Element root = paramsXMLDoc.getRootElement();
			RESOURCE_BUNDLE_FILE = root.elementText("resource_bundle_file");
			ServerConfig.RESOURCE_BUNDLE_FILE = RESOURCE_BUNDLE_FILE;
//			System.out.print("加载资源文件配置,资源文件为" + RESOURCE_BUNDLE_FILE + "\r\n");
			WordResource.init();
//			System.out.print("资源文件加载完毕\r\n");

			// 加载资源文件
			File file = loadFile(false);
			OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");

			pw.append("#=== "+Calendar.getInstance().getTime().toString()+" 追加===");
			checkAllConstants(pw, true);

			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.error("追加资源失败",e);
		}
	}

	private static void rebuild() {
		SAXReader saxReader = new SAXReader();
		try {
			Document paramsXMLDoc = saxReader.read(ResourceMaker.class
					.getClassLoader().getResourceAsStream(SERVER_CONFIG_FILE));
			Element root = paramsXMLDoc.getRootElement();
			RESOURCE_BUNDLE_FILE = root.elementText("resource_bundle_file");
//			System.out.print("资源文件配置为" + RESOURCE_BUNDLE_FILE + "\r\n");

			// 加载资源文件
			File file = loadFile(true);
			OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");

			pw.write("#Resource_cn\r\n");
			checkAllConstants(pw, false);

			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.error("重构资源失败",e);
		}
	}

	private static File loadFile(boolean remove) throws Exception {
		File tmpFile = new File(java.net.URLDecoder.decode(
				LanguageResource.class.getClassLoader().getResource(
						"").getPath(), "UTF-8")
				+ (RESOURCE_BUNDLE_FILE + "_zh_CN.properties"));

		if (tmpFile.exists() && remove) {
//			System.out.print("删除旧资源文件\r\n");
			boolean r = tmpFile.delete();
			if(!r){
				logger.error("删除旧资源文件失败,文件名"+tmpFile.getName());
			}
		}

		if (!tmpFile.exists()) {
			boolean r = tmpFile.createNewFile();
			if(!r){
				logger.error("创建文件失败");
			}
		}

		return tmpFile;
	}

	private static void checkAllConstants(OutputStreamWriter pw, boolean checkResource) throws Exception{
		String key = "";
		String value = "";

//		System.out.print("分析添加RoleMsgConstants文件\r\n");
		for (int i = 0; i < RoleMsgConstants.values().length; i++) {
			key = "RoleMsgConstants." + RoleMsgConstants.valueOf(i).name();
			if (checkResource) {
				value = MultipleLanguage.getMessage(LanguageResource.getBundle(
						LanguageResource.DEFAULT_LANGUAGE, false, LanguageResource.DEFAULT_BUNDLE_FILE), key, key);
			}
			if (!checkResource || key.equals(value)) {
				pw.append(key + "=" + RoleMsgConstants.valueOf(i).toString()
						+ "\r\n");
			}
		}
		pw.flush();

//		System.out.print("分析添加SystemMsgConstants文件\r\n");
		for (int i = 0; i < SystemMsgConstants.values().length; i++) {
			key = "SystemMsgConstants." + SystemMsgConstants.valueOf(i).name();
			if (checkResource) {
				value = MultipleLanguage.getMessage(LanguageResource.getBundle(
						LanguageResource.DEFAULT_LANGUAGE, false, LanguageResource.DEFAULT_BUNDLE_FILE), key, key);
			}
			if (!checkResource || key.equals(value)) {
				pw.append(key + "=" + SystemMsgConstants.valueOf(i).toString()
						+ "\r\n");
			}
		}
		pw.flush();
	}
}
