package com.oracle.notebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class PythonScriptEngine extends ScriptEngine {

	@Override
	public Evaluation eval(File file) throws Exception {
		Process process = new ProcessBuilder("python", file.getAbsolutePath()).start();
		process.waitFor(5, TimeUnit.SECONDS);
		process.destroy();
		if (process.exitValue() == 0) {
			String error = getLastLine(process.getErrorStream());
			return error.equals("") ? new Evaluation(getLastLine(process.getInputStream()), false)
					: new Evaluation(error, true);
		} else {
			return new Evaluation("process aborted", true);
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