/**
 * @package com.doteplay.editor.script
 * @file JSScript.java
 */
package com.doteplay.editor.script;

import java.io.File;

import com.doteplay.editor.script.common.IScript;
import com.doteplay.editor.script.common.XScriptEngine;
import com.doteplay.editor.util.Util;

/**
 */
public class JSScript implements IScript {

	@Override
	public String getName() {
		return "JavaScript文件";
	}

	@Override
	public boolean eval() {
		File f = Util.openSelectFileDialog("JavaScript文件", "js");
		if (f == null) {
			return false;
		}
		try {
			XScriptEngine.getInstance().runScript(f);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
