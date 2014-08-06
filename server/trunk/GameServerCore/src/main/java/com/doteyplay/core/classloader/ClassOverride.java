package com.doteyplay.core.classloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类内存byte复写（动态编译）
 * 
 * @author 注意Premain-Class定义
 * 
 * @deprecated.
 */
public class ClassOverride
{

	public ClassOverride()
	{
	}

	private static Instrumentation inst = null;

	public static void premain(String agentArgs, Instrumentation ins)
	{
		inst = ins;
	}

	public static void reload(Class<?> cls, File file) throws IOException,
			ClassNotFoundException, UnmodifiableClassException
	{
		byte code[] = loadBytes(cls, file);
		if (code == null)
		{
			throw new IOException("Unknown File");
		} else
		{
			ClassDefinition def = new ClassDefinition(cls, code);
			inst.redefineClasses(new ClassDefinition[]
			{ def });
			System.err
					.println((new StringBuilder(String.valueOf(cls.getName())))
							.append(" reloaded").toString());
			return;
		}
	}

	private static byte[] loadBytes(Class<?> cls, File file)
			throws IOException, ClassNotFoundException
	{
		String name = file.getName();
		if (name.endsWith(".jar"))
			return loadBytesFromJarFile(cls, file);
		if (name.endsWith(".class"))
			return loadBytesFromClassFile(file);
		else
			return null;
	}

	private static byte[] loadBytesFromClassFile(File classFile)
			throws IOException
	{
		byte buffer[];
		BufferedInputStream bis;
		buffer = new byte[(int) classFile.length()];
		FileInputStream fis = new FileInputStream(classFile);
		bis = new BufferedInputStream(fis);
		try
		{
			bis.read(buffer);
		} catch (IOException e)
		{
			throw e;
		} finally
		{
			try
			{
				bis.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		return buffer;
	}

	private static byte[] loadBytesFromJarFile(Class<?> cls, File file)
			throws IOException, ClassNotFoundException
	{
		byte buffer[];
		BufferedInputStream bis;
		JarFile jarFile = new JarFile(file);
		String name = cls.getName();
		name = (new StringBuilder(String.valueOf(name.replaceAll("\\.", "/"))))
				.append(".class").toString();
		JarEntry en = jarFile.getJarEntry(name);
		if (en == null)
			throw new ClassNotFoundException(name);
		buffer = new byte[(int) en.getSize()];
		bis = new BufferedInputStream(jarFile.getInputStream(en));
		try
		{
			bis.read(buffer);
		} catch (IOException e)
		{
			throw e;
		} finally
		{
			try
			{
				bis.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		return buffer;
	}
}
