package com.oracle.notebook;

public class Evaluation {

	private String result;
	private boolean failed;

	public Evaluation() {

	}

	public Evaluation(String result, boolean failed) {
		this.result = result;
		this.failed = failed;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean hasFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

}
