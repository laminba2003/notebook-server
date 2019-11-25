package com.oracle.notebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class PythonScriptEngine extends ScriptEngine {

	@Override
	public Evaluation eval(File file) {
		Evaluation evaluation = new Evaluation();
		try {
			Process process = Runtime.getRuntime().exec(new String[] { "python", file.getAbsolutePath() });
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
		} catch (Exception e) {
			evaluation.setResult(e.getMessage());
			e.printStackTrace();
		}
		return evaluation;
	}

}
