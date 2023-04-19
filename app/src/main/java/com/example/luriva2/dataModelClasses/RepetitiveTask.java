package com.example.luriva2.dataModelClasses;

public class RepetitiveTask extends Task {
	private String howOften;
	
	public RepetitiveTask(String n, int eT, String ho) {
		super(n, 1, eT);
		howOften = ho;
	}
	
	public String getHowOften() {
		return howOften;
	}
	
	public void setHowOften(String ho) {
		howOften = ho;
	}
}
