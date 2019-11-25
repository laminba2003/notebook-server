package com.oracle.notebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PythonScriptEngine extends ScriptEngine {

	@Override
	public Evaluation eval(File file) throws Exception {
		Process process = new ProcessBuilder("python", file.getAbsolutePath()).start();
		String output = readAllLines(process.getInputStream());
		output = output!=null ? output : readAllLines(process.getErrorStream()); 
		Evaluation evaluation = new Evaluation(output);
		return evaluation;
	}
	
	protected String readAllLines(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line + " ");
		}
		return buffer.toString();
	}

}