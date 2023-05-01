package com.example.luriva2.dataModelClasses;

public class OneTimeTask extends Task {
	
	private Date dueDate;

	public OneTimeTask(String taskName, int estimatedTime, int difficulty, Date dueDate) {
		super(taskName, estimatedTime, difficulty, "One-Time");
		this.dueDate = dueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
