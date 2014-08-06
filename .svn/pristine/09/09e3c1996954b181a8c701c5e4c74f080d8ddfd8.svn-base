package com.doteyplay.core.classloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 新增jar包状态下加载
 * 
 */
public class JarPathLoader {

	/**
	 * 所有引用的元素对象
	 */
	Vector<File> _elements = new Vector<File>();

	ClassLoader loader = null;

	boolean needLoad = false;

	private JarPathLoader() {
		addClasspath(System.getProperty("java.class.path"));
	}

	private JarPathLoader(String initial) {
		addClasspath(initial);
	}

	/**
	 * 增加一个对象（String->File）
	 * 
	 * @param component
	 * @return
	 */
	private boolean addComponent(String component) {
		if ((component != null) && (component.length() > 0)) {
			try {
				File f = new File(component);
				if (f.exists()) {
					File key = f.getCanonicalFile();
					if (!_elements.contains(key)) {
						_elements.add(key);
						needLoad = true;
						return true;
					}
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	/**
	 * 增加一个对象，File
	 * 
	 * @param component
	 * @return
	 */
	private boolean addComponent(File component) {
		if (component != null) {
			try {
				if (component.exists()) {
					File key = component.getCanonicalFile();
					if (!_elements.contains(key)) {
						_elements.add(key);
						needLoad = true;
						return true;
					}
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	private boolean addClasspath(String s) {
		boolean added = false;
		if (s != null) {
			StringTokenizer t = new StringTokenizer(s, File.pathSeparator);
			while (t.hasMoreTokens()) {
				added |= addComponent(t.nextToken());
			}
		}
		return added;
	}

	public String toString() {
		StringBuffer cp = new StringBuffer(1024);
		int cnt = _elements.size();
		if (cnt >= 1) {
			cp.append(((File) (_elements.elementAt(0))).getPath());
		}
		for (int i = 1; i < cnt; i++) {
			cp.append(File.pathSeparatorChar);
			cp.append(((File) (_elements.elementAt(i))).getPath());
		}
		return cp.toString();
	}

	private URL[] getUrls() {
		int cnt = _elements.size();
		URL[] urls = new URL[cnt];
		for (int i = 0; i < cnt; i++) {
			try {
				urls[i] = ((File) (_elements.elementAt(i))).toURI().toURL();
			} catch (MalformedURLException e) {
			}
		}
		return urls;
	}

	/**
	 * 获取ClassLoader
	 * 
	 * @return
	 */
	private ClassLoader getClassLoader() {
		if (loader == null || needLoad) {
			URL[] urls = getUrls();
			ClassLoader parent = Thread.currentThread().getContextClassLoader();
			if (parent == null) {
				parent = JarPathLoader.class.getClassLoader();
			}
			if (parent == null) {
				parent = ClassLoader.getSystemClassLoader();
			}
			loader = new URLClassLoader(urls, parent);
			needLoad = false;
		}
		return loader;
	}

	private static JarPathLoader instance = new JarPathLoader();

	/**
	 * 唯一对外方法，获取classLoader
	 * 
	 * @param paths
	 * @return
	 */
	public static ClassLoader getNewJarLoader(String paths) {
		if (paths == null || paths.length() <= 0) {
			return instance.getClassLoader();
		}

		try {
			StringTokenizer t = new StringTokenizer(paths, File.pathSeparator);
			while (t.hasMoreTokens()) {
				final File f = new File(t.nextToken()).getCanonicalFile();
				instance.addComponent(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return instance.getClassLoader();
		}

		System.setProperty("java.class.path", instance.toString());
		ClassLoader classloader = instance.getClassLoader();
		Thread.currentThread().setContextClassLoader(classloader);
		return classloader;
	}

}