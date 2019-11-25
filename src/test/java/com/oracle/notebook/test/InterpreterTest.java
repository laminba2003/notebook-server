package com.oracle.notebook.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oracle.notebook.Evaluation;
import com.oracle.notebook.Interpreter;
import com.oracle.notebook.Program;

public class InterpreterTest {

	@Test
	public void test() throws Exception {
		Interpreter interpreter = new Interpreter();
		Program program = new Program();
		program.setCode("%python print('hello world')");
		Evaluation evaluation = interpreter.eval(program);
		assertEquals("hello world", evaluation.getResult());
	}

}
