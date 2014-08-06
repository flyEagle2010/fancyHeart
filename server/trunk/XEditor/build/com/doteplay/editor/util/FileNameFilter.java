package com.doteplay.editor.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FileNameFilter extends FileFilter
{
    String fileTypes;
    String[] ftlist;
    //Hashtable types=new Hashtable();
    public FileNameFilter(String p)
    {
        fileTypes=p;
        ftlist=p.split(";");
    }

    public boolean accept(File f)
    {
        if(f.isDirectory())
            return true;
        String s=f.getName();
        for(int i=0;i<ftlist.length;i++)
        {
            if (s.endsWith("."+ftlist[i]))
                return true;
        }
        return false;
    }
    public String getDescription()
    {
        return fileTypes;
    }
}
