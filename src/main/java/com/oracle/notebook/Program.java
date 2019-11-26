package com.oracle.notebook;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Program {

	@NotNull
	@Pattern(regexp="^(%)\\w+\\s\\w+[^$]+$") 
	private String code;
	private List<Program> programs = new ArrayList<Program>();
	
	public Program() {
		
	}
	
	public Program(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code.substring(code.indexOf(" ")+1, code.length());
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getEngineName() {
		return code.substring(1, code.indexOf(" "));
	}

	public List<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}
	
}
