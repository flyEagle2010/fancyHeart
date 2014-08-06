package com.doteyplay.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{
	public static boolean zipFile(String souFileName, String desFileName)
	{
		try
		{
			if (IOUtils.fileExist(souFileName))
			{
				FileOutputStream fileOut = new FileOutputStream(desFileName);
				ZipOutputStream outputStream = new ZipOutputStream(fileOut);
				File rootFile = new File(souFileName);
				FileInputStream fileIn = new FileInputStream(rootFile);
				outputStream.putNextEntry(new ZipEntry(souFileName));
				byte[] buffer = new byte[1024];
				while (fileIn.read(buffer) != -1)
				{
					outputStream.write(buffer);
				}
				outputStream.closeEntry();
				fileIn.close();
				outputStream.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public static boolean unzipFile(String souFileName, String desFileName)
	{
		return true;
	}
}
