package com.doteyplay.core.classloader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.doteyplay.core.bhns.ISimpleService;

/**
 * 同名class直接加载器，不读父缓存，需要每次new
 * 
 */
public class CustomClassLoader extends ClassLoader
{

	private static Logger logger = Logger.getLogger(CustomClassLoader.class);
	private String[] jarPaths;

	public CustomClassLoader(String[] jarPaths)
	{
		super(null); // 指定父类加载器为 null
		this.jarPaths = jarPaths;
	}

	public void load() throws Exception
	{
		for (String jarPath : jarPaths)
			this.loadJarFile(jarPath);
	}

	@SuppressWarnings("unchecked")
	private void loadJarFile(String jarPath) throws Exception
	{
		JarFile jarFile = null;
		jarFile = new JarFile(jarPath);
		Enumeration<JarEntry> entrys = jarFile.entries();
		while (entrys.hasMoreElements())
		{
			try
			{
				JarEntry je = (JarEntry) entrys.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(".class"))
					continue;
				String className = je.getName().substring(0,
						je.getName().length() - ".class".length()).replace('/',
						'.');
				int readSize = (int) je.getSize();
				InputStream inputStream = jarFile.getInputStream(je);
				Class<?> clz = loadDirectly(inputStream, readSize,
						className);
				if (!clz.isAssignableFrom(ISimpleService.class))
				{
					Field tmpField = clz.getField("PORTAL_ID");
					if(tmpField == null)
						continue;
					ServiceJarReloadManager.notifyReplaceServiceImpl(tmpField.getInt(null),
							(Class<ISimpleService>)clz, this);
						
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
	}

	private Class<?> loadDirectly(InputStream iStream, int size, String pName)
			throws Exception
	{
		Class<?> cls = null;
		cls = instantiateClass(iStream, size, pName);
		return cls;
	}

	private Class<?> instantiateClass(InputStream fin, long len, String pName)
			throws Exception
	{
		byte[] raw = new byte[(int) len];
		fin.read(raw);
		fin.close();
		return defineClass(pName, raw, 0, raw.length);
	}

	protected Class<?> loadClass(String className, boolean resolve)
			throws ClassNotFoundException
	{
		if (className == null)
			throw new ClassNotFoundException("Class name is null");
		Class<?> cls = null;
		cls = findLoadedClass(className);
		if (cls == null)
			cls = getSystemClassLoader().loadClass(className);
		if (cls == null)
			throw new ClassNotFoundException(className);

		if (resolve)
			resolveClass(cls);
		return cls;
	}
	
	public static void main(String[] args)
	{
		 try
		 {
//		 JarClassLoader.getClassLoad("AA.jar", true);
//		 Object o =
//		 cc1.loadClass("com.t4game.service.ServiceA").newInstance();
//		 Method m = o.getClass().getMethod("call",new Class[]{});
//		 m.invoke(o, new Object[]{});
			
//		 CustomClassLoader cc2 = new CustomClassLoader(new String[]{"AService"}, new String[]{"AA.jar"});
//		 cc2.load();
//		 Object o2 =
//		 cc2.loadClass("com.t4game.service.AService").newInstance();
//		 Method m2 = o2.getClass().getMethod("call",new Class[]{});
//		 m2.invoke(o2, new Object[]{});
//		
//		 CustomClassLoader cc3 = new CustomClassLoader(new String[]{"sdfsdfs"}, new String[]{"B2.jar"});
//		 Object o3 =
//		 cc3.loadClass("com.t4game.domain.ObjectA").newInstance();
//		 Method m3 = o3.getClass().getMethod("speak",new Class[]{});
//		 m3.invoke(o3, new Object[]{});
//					
//		 m2.invoke(o2, new Object[]{});
//					
//		 Object o4 = cc3.loadClass("com.t4game.domain.ObjectA").newInstance();
//		 Method m4 = o4.getClass().getMethod("speak",new Class[]{});
//		 m4.invoke(o4, new Object[]{});
						
		 }catch (Exception e)
		 {
			 e.printStackTrace();
		 }
	}
}
