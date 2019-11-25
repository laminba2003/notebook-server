package com.oracle.notebook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Interpreter {

	@RequestMapping(value = "/execute", method = RequestMethod.POST)
    public Evaluation execute(@RequestBody Program program) throws IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		Evaluation evaluation;
		ScriptEngine engine = manager.getEngineByName(program.getEngineName());
		if(engine!=null) {
			File file = createTempFile(program);
			evaluation = engine.eval(file);
		} else {
			evaluation = new Evaluation();
			evaluation.setResult("engine not found");
		}
        return evaluation;
    }
	
	
	public File createTempFile(Program program) throws IOException {
		File file = File.createTempFile("prog", ".tmp");
		Files.write(Paths.get(file.getAbsolutePath()), program.getCode().getBytes());
		return file;
	}
	
	
}
