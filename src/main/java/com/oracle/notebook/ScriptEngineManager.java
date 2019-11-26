package com.oracle.notebook;

public class ScriptEngineManager {

	public ScriptEngine getEngineByName(String name) {
		switch (name) {
		case "python":
			return new PythonScriptEngine();
		default:
			return null;
		}
	}

}
