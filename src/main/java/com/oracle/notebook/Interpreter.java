package com.oracle.notebook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Interpreter {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public Evaluation execute(HttpSession session, @Valid @RequestBody Program program) throws Exception {
		String key = program.getEngineName();
		List<Program> programs = (List<Program>) session.getAttribute(key);
		programs = programs != null ? programs : new ArrayList<Program>();
		program.setPrograms(programs);
		Evaluation evaluation = interpret(program);
		if (!evaluation.hasFailed()) {
			programs.add(program);
			session.setAttribute(key, programs);
		}
		return evaluation;
	}

	@RequestMapping(value = "/execute/{sessionId}", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public synchronized Evaluation execute(HttpServletRequest request, @PathVariable String sessionId,
			@Valid @RequestBody Program program) throws Exception {
		ServletContext context = request.getServletContext();
		String key = sessionId + "_" + program.getEngineName();
		List<Program> programs = (List<Program>) context.getAttribute(key);
		programs = programs != null ? programs : new ArrayList<Program>();
		program.setPrograms(programs);
		Evaluation evaluation = interpret(program);
		if (!evaluation.hasFailed()) {
			programs.add(program);
			context.setAttribute(key, programs);
		}
		return evaluation;
	}

	public Evaluation interpret(Program program) throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName(program.getEngineName());
		return engine != null ? eval(engine, program) : new Evaluation("engine not supported", true);
	}
	
	private Evaluation eval(ScriptEngine engine, Program program) throws Exception {
		File file = createTempFile(program);
		Evaluation evaluation = engine.eval(file);
		file.delete();
		return evaluation;
	}

	public File createTempFile(Program program) throws IOException {
		File file = File.createTempFile("prog", ".tmp");
		StringBuffer buffer = new StringBuffer();
		for (Program previous : program.getPrograms()) {
			buffer.append(previous.getCode() + "\r\n");
		}
		buffer.append(program.getCode());
		Files.write(Paths.get(file.getAbsolutePath()), buffer.toString().getBytes());
		return file;
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Evaluation handleException(MethodArgumentNotValidException exception) {
		return new Evaluation("the request payload is invalid", true);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Evaluation handleException(Exception exception) {
		return new Evaluation("the code cannot be evaluated", true);
	}

}