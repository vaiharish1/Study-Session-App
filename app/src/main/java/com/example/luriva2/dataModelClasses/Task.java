package com.example.luriva2.dataModelClasses;

public class Task {
	private final String taskName;
	private final int estimatedTime;
	private final int difficulty;
	private final String taskType;

	private final Date dueDate;

	public Task(String taskName, int estimatedTime, int difficulty, String taskType, Date dueDate) {
		this.taskName = taskName;
		this.estimatedTime = estimatedTime + Constants.BUFFER_TIME * difficulty;
		this.difficulty = difficulty;
		this.taskType = taskType;
		this.dueDate = dueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public String getTaskType() {
		return taskType;
	}

	@Override
	public String toString() {
		return "Task{" +
				"taskName='" + taskName + '\'' +
				", estimatedTime=" + estimatedTime +
				", difficulty=" + difficulty +
				", taskType='" + taskType + '\'' +
				'}';
	}
}
