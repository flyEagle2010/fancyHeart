/**
 * 
 */
package com.doteplay.editor.script.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.doteplay.editor.common.DataManager;

/**
 * @author Yang
 */
public class XScriptEngine {

	private static XScriptEngine instance = null;

	/**
	 * 脚本引擎
	 */
	private ScriptEngine engine;

	private XScriptEngine() {

	}

	/**
	 * 初始化一次
	 * 
	 * @param engineName
	 * @return
	 */
	public static XScriptEngine getInstance() {
		if (instance == null) {
			instance = new XScriptEngine();
			instance.initEngine("js");
			instance.initBindings();
		}
		return instance;
	}

	public ScriptEngine getScriptEngine() {
		return engine;
	}

	private boolean initEngine(String engineName) {
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName(engineName);
		if (engine == null) {
			System.out.println("Engine is not found");
			return false;
		}
		return true;
	}

	/**
	 * 注册变量
	 * 
	 * @param name
	 * @param value
	 */
	public void registerBinding(String name, Object value) {
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.put(name, value);
	}

	/**
	 * 注册变量
	 * 
	 * @param map
	 */
	public void registerBinding(Map<String, Object> map) {
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.putAll(map);
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
	}

	/**
	 * 注销变量
	 * 
	 * @param name
	 */
	public void unregisterBinding(String name) {
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.remove(name);
		System.out.println("unregisterBindings:" + name);
	}

	private void initBindings() {

		Bindings bindings;

		bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.put("dataGroupList", DataManager.dataGroupList);
		bindings.put("datamanager", DataManager.class);

		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		System.out.println(engine.getBindings(ScriptContext.ENGINE_SCOPE).keySet());
	}

	public void runScript(String script) throws ScriptException {
		if (engine == null) {
			System.out.println("Engine is null");
			return;
		}
		engine.eval(script);
	}
	
	public void runScript(File file) throws Exception {
		if (engine == null) {
			System.out.println("Engine is null");
			return;
		}
		InputStream in = new FileInputStream(file);
		runScript(in);
	}

	public void runScript(InputStream script) throws Exception {
		if (engine == null) {
			System.out.println("Engine is null");
			return;
		}
		InputStreamReader reader = new InputStreamReader(script);
		engine.eval(reader);
		reader.close();
		reader = null;
	}

	public void test() {
		InputStream in = null;
		try {
//			in = new FileInputStream(new File("e:\\quest.js"));
			in = XScriptEngine.class.getResourceAsStream("/js/quest.js");
			XScriptEngine.getInstance().runScript(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (in != null) {
				in.close();
				in = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {

	}

}
