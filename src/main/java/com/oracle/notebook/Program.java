package com.oracle.notebook;

public class Program {

	private String code;
	
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
