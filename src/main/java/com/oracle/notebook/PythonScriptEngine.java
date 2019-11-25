package com.oracle.notebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PythonScriptEngine extends ScriptEngine {

	@Override
	public Evaluation eval(File file) throws Exception {
		Evaluation evaluation;
		Process process = new ProcessBuilder("python", file.getAbsolutePath()).start();
		String error = getLastLine(process.getErrorStream());
		if(error.equals("")) {
			evaluation = new Evaluation(getLastLine(process.getInputStream()), false);
		}else {
			evaluation = new Evaluation(error, true);
		}
		return evaluation;
	}
	
	protected String getLastLine(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String last = "", line = "";
	    while ((line = reader.readLine()) != null) { 
	        last = line;
	    }
		return last;
	}

}