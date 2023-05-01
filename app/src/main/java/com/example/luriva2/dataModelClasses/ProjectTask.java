package com.example.luriva2.dataModelClasses;

public class ProjectTask extends Task {

	private Date dueDate;

	public ProjectTask(String taskName, int estimatedTime, int difficulty, Date dueDate) {
		super(taskName, estimatedTime, difficulty, "Project");
		this.dueDate = dueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
