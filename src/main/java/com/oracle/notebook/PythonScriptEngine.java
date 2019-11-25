package com.oracle.notebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class PythonScriptEngine extends ScriptEngine {

	@Override
	public Evaluation eval(File file) throws Exception {
		Evaluation evaluation = new Evaluation();
		Process process = new ProcessBuilder("python", file.getAbsolutePath()).start();
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String output = stdInput.readLine();
		if (output == null) {
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = stdError.readLine()) != null) {
				buffer.append(line + " ");
			}
			output = buffer.toString();
		}
		evaluation.setResult(output);
		return evaluation;
	}

}
