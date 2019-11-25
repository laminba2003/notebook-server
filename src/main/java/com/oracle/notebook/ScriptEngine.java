package com.oracle.notebook;

import java.io.File;

public abstract class ScriptEngine {

	public abstract Evaluation eval(File file);
	
}
