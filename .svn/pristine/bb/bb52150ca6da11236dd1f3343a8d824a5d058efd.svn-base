package com.doteyplay.core.classloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @author 
 * @deprecated
 */
public class NewClassLoader extends URLClassLoader {

	public NewClassLoader() {
		super(new URL[0], ClassLoader.getSystemClassLoader());
	}

	public void addPath(String paths) {
		if (paths == null || paths.length() <= 0) {
			return;
		}
		String separator = System.getProperty("path.separator");
		String[] pathToAdds = paths.split(separator);
		for (int i = 0; i < pathToAdds.length; i++) {
			if (pathToAdds[i] != null && pathToAdds[i].length() > 0) {
				try {					
					File pathToAdd = new File(pathToAdds[i]).getCanonicalFile();
					System.out.println(pathToAdd.toURI().toURL());
					addURL(pathToAdd.toURI().toURL());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}