package com.oracle.notebook.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.oracle.notebook.Evaluation;
import com.oracle.notebook.Interpreter;
import com.oracle.notebook.Program;

@RunWith(SpringRunner.class)
@WebMvcTest(Interpreter.class)
public class InterpreterTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testEvaluation() throws Exception {
		Interpreter interpreter = new Interpreter();
		Program program = new Program("%python print('hello world')");
		Evaluation evaluation = interpreter.interpret(program);
		assertNotNull(evaluation);
		assertEquals("hello world", evaluation.getResult());
		assertEquals(false, evaluation.hasFailed());
	}

	@Test
	public void testEndpoints() throws Exception {
		String code = "{\"code\" : \"%python print('hello world')\"}";
		mockMvc.perform(post("/execute").content(code).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is("hello world"))).andReturn();
		mockMvc.perform(post("/execute/12334555").content(code).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.result", is("hello world"))).andReturn();
	}

}
