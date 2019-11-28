package com.oracle.notebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class PythonScriptEngine extends ScriptEngine {

	@Override
	public Evaluation eval(File file) throws EvaluationException {
		try {
			Process process = new ProcessBuilder("python", file.getAbsolutePath()).start();
			process.waitFor(5, TimeUnit.SECONDS);
			process.destroy();
			if (process.exitValue() == 0) {
				return new Evaluation(getLastLine(process.getInputStream()), false);
			} else {
				String error = getLastLine(process.getErrorStream());
				error = !error.equals("") ? error : "process aborted";
				return new Evaluation(error, true);
			}
		} catch (Exception e) {
			throw new EvaluationException(e.getMessage());
		}
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