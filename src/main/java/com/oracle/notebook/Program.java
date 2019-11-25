package com.oracle.notebook;

import javax.validation.constraints.NotNull;

public class Program {

	@NotNull
	private String code;
	
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
}
