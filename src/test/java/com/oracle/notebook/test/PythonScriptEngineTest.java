package com.oracle.notebook.test;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import com.oracle.notebook.Evaluation;
import com.oracle.notebook.PythonScriptEngine;

public class PythonScriptEngineTest {

	@Test
	public void test() throws Exception {
		PythonScriptEngine engine = new PythonScriptEngine();
		File file = new File("src/test/resources/hello.py");
		assertTrue(file.exists());
		Evaluation evaluation = engine.eval(file);
		assertEquals("hello world", evaluation.getResult());
		assertEquals(false, evaluation.hasFailed());
	}

	
}
