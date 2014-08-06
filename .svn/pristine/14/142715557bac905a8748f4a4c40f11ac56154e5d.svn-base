package com.doteyplay.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class IOUtils
{
	/**
	 * ��ȡwebĿ¼
	 * 
	 * @return
	 */
	public static String getWebPath()
	{
		String tmpWebPath = IOUtils.class.getResource("/").getFile();
		int tmpPos = tmpWebPath.lastIndexOf("WEB-INF");
		if (tmpPos > 0)
			tmpWebPath = tmpWebPath.substring(0, tmpPos);
		return tmpWebPath;
	}

	public static String fixPath(String refPath, boolean needSeparator)
	{
		if (refPath != null)
		{
			if (needSeparator)
			{
				if (!refPath.endsWith("/") && !refPath.endsWith("\\"))
					return refPath + File.separator;
				else
					return refPath;
			}
			else
			{
				if (refPath.endsWith("/") && !refPath.endsWith("\\"))
					return refPath.substring(0, refPath.length() - 1);
				else
					return refPath;
			}
		}
		else
			return refPath;
	}

	/**
	 * ��ȡ��ǰ·��
	 * 
	 * @return
	 */
	public static String getCurrentPath()
	{
		return IOUtils.class.getResource("/").getFile();
	}

	public static boolean dirExists(String filepath)
	{
		return dirExists(filepath, false);
	}

	public static boolean dirExists(String filepath, boolean forceCreate)
	{
		boolean result = false;
		try
		{
			if (filepath != null && filepath.length() > 0)
			{
				File tmpDir = new java.io.File(filepath);
				result = tmpDir.exists();
				if (!result && forceCreate)
				{
					result = false;
					tmpDir.mkdirs();
					result = true;
				}
				tmpDir = null;
			}
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public static boolean delFile(String filePathAndName)
	{
		boolean result = true;
		try
		{
			if (filePathAndName != null && filePathAndName.length() > 0)
			{
				File myDelFile = new File(filePathAndName);
				myDelFile.delete();
			}
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public static void delFolder(String folderPath)
	{
		try
		{
			if (folderPath != null && folderPath.length() > 0)
			{
				delAllFile(folderPath); // ɾ����������������
				File myFilePath = new File(folderPath);
				myFilePath.delete(); // ɾ����ļ���
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void delAllFile(String path)
	{
		File file = new File(path);
		if (!file.exists())
		{
			return;
		}
		if (!file.isDirectory())
		{
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
			if (path.endsWith(File.separator))
			{
				temp = new File(path + tempList[i]);
			}
			else
			{
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile())
			{
				temp.delete();
			}
			if (temp.isDirectory())
			{
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ����ļ���
			}
		}
	}

	public static boolean fileExist(String filename)
	{
		File tmpfile = new File(filename);
		return (tmpfile.exists() && tmpfile.isFile());
	}

	public static void copyFile(String oldPath, String newPath)
	{
		InputStream inStream = null;
		FileOutputStream fs = null;
		try
		{
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists())
			{
				inStream = new FileInputStream(oldPath);
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1)
				{
					bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				inStream = null;
				fs.close();
				fs = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (inStream != null)
			{
				try
				{
					inStream.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (fs != null)
			{
				try
				{
					fs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void streamToFile(String filename, InputStream soustream)
	{
		FileOutputStream fs = null;
		try
		{
			int bytesum = 0;
			int byteread = 0;
			if (filename != null && filename.length() > 0 && soustream != null)
			{
				fs = new FileOutputStream(filename);
				byte[] buffer = new byte[256];
				while ((byteread = soustream.read(buffer)) != -1)
				{
					bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				fs = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fs != null)
			{
				try
				{
					fs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void fileToStream(String filename, OutputStream targetstream)
	{
		if (filename == null || targetstream == null)
			return;

		InputStream inStream = null;
		try
		{
			int bytesum = 0;
			int byteread = 0;
			File srcfile = new File(filename);
			if (srcfile.exists())
			{
				inStream = new FileInputStream(srcfile);
				byte[] buffer = new byte[256];
				while ((byteread = inStream.read(buffer)) != -1)
				{
					bytesum += byteread; // �ֽ��� �ļ���С
					targetstream.write(buffer, 0, byteread);
				}
				inStream.close();
				inStream = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (inStream != null)
			{
				try
				{
					inStream.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static String loadTextFile(String filename, String charset)
	{
		String fileText = null;
		ByteArrayOutputStream tmpLoadStream = new ByteArrayOutputStream();
		fileToStream(filename, tmpLoadStream);
		try
		{
			fileText = new String(tmpLoadStream.toByteArray(), charset);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			tmpLoadStream.close();
			tmpLoadStream = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fileText;
	}

	public static boolean saveTextFile(String text, String filename, String charset)
	{
		try
		{
			byte[] tmpTextData = text.getBytes(charset);
			ByteArrayInputStream tmpSaveStream = new ByteArrayInputStream(tmpTextData);
			streamToFile(filename, tmpSaveStream);
			tmpTextData = null;
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static void copyFolder(String oldPath, String newPath)
	{
		try
		{
			(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++)
			{
				if (oldPath.endsWith(File.separator))
				{
					temp = new File(oldPath + file[i]);
				}
				else
				{
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile())
				{
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/"
							+ (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1)
					{
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory())
				{// ��������ļ���
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void moveFile(String oldPath, String newPath)
	{
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath)
	{
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public static String[] viewFileList(String filepath, String fileFilter)
	{
		Vector filenamelist = new Vector(10);
		if (filepath != null && filepath.length() > 0)
		{
			try
			{
				String tmpfileFilter = "";
				if (fileFilter != null)
					tmpfileFilter = fileFilter.toLowerCase();
				fileFilter.toLowerCase();
				File tmpDir = new File(filepath);
				File[] tmpFiles = tmpDir.listFiles();
				if (tmpFiles != null)
				{
					for (int i = 0; i < tmpFiles.length; i++)
					{
						File tmpFile = tmpFiles[i];
						if (tmpFile.isFile() && tmpFile.getName().toLowerCase().endsWith(tmpfileFilter))
							filenamelist.add(tmpFile.getName());
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		String[] result = new String[filenamelist.size()];
		return (String[]) filenamelist.toArray(result);
	}

	public static String[] subDirs(String filepath)
	{
		Vector tmpDirList = new Vector(10);
		if (filepath != null && filepath.length() > 0)
		{
			try
			{
				File tmpDir = new File(filepath);
				File[] tmpFiles = tmpDir.listFiles();
				for (int i = 0; tmpFiles != null && i < tmpFiles.length; i++)
				{
					File tmpFile = tmpFiles[i];
					if (tmpFile.isDirectory())
						tmpDirList.add(tmpFile.getPath());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		String[] result = new String[tmpDirList.size()];
		return (String[]) tmpDirList.toArray(result);
	}

	public static String fileExt(String filename)
	{
		String result = "";
		if (filename != null)
		{
			int tmpPos = filename.lastIndexOf('.');
			if (tmpPos > 0)
				result = filename.substring(tmpPos + 1);
		}
		if (result != null && result.length() > 4)
			result = "";
		return result;
	}

	public static String urlFileExt(String filename)
	{
		String result = fileExt(filename);
		if (result == null || result.length() == 0 && filename != null)
		{
			int tmpPos = filename.lastIndexOf('?');
			if (tmpPos > 0)
				result = IOUtils.fileExt(filename.substring(0, tmpPos));
		}
		return result;
	}

	public static void main(String[] args)
	{
		System.out.println(getWebPath());
		// String[] tmpFileNames =
		// viewFileList("D:/9ib2/ydmall/src/com/ydwan/mall/bean/", ".hbm.xml");
		// for (int i = 0; tmpFileNames != null && i < tmpFileNames.length; i++)
		// {
		// System.out.println(tmpFileNames[i]);
		// }
	}
}
