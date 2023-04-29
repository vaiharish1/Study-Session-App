package com.example.luriva2.dataModelClasses;

public class RepetitiveTask extends Task {

	private int howOften;

	public RepetitiveTask(String taskName, int estimatedTime, int difficulty, int howOften) {
		super(taskName, estimatedTime, difficulty, "Project");
		this.howOften = howOften;
	}

	public int getHowOften() {
		return howOften;
	}

	public void setHowOften(int howOften) {
		this.howOften = howOften;
	}
}
