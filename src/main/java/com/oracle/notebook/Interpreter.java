package com.oracle.notebook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public Evaluation execute(@Valid @RequestBody Program program) {
		return eval(program);
	}

	@RequestMapping(value = "/execute/{sessionId}", method = RequestMethod.POST)
	public Evaluation execute(@PathVariable String sessionId, @Valid @RequestBody Program program) throws IOException {
		return eval(program);
	}

	private Evaluation eval(Program program) {
		Evaluation evaluation;
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName(program.getEngineName());
			if (engine != null) {
				File file = createTempFile(program);
				evaluation = engine.eval(file);
				file.delete();
			} else {
				evaluation = new Evaluation("engine not supported");
			}
		} catch (Exception e) {
			evaluation = new Evaluation("the code cannot be evaluated");
		}
		return evaluation;
	}

	public File createTempFile(Program program) throws IOException {
		File file = File.createTempFile("prog", ".tmp");
		Files.write(Paths.get(file.getAbsolutePath()), program.getCode().getBytes());
		return file;
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Evaluation handleException(MethodArgumentNotValidException exception) {
		return new Evaluation("the request format is invalid");
	}

}