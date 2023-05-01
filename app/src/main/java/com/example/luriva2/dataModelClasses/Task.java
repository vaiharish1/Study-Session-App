package com.example.luriva2.dataModelClasses;

public class Task {
	private String taskName;
	private int estimatedTime;
	private int difficulty;
	private String taskType;

	public Task(String taskName, int estimatedTime, int difficulty, String taskType) {
		this.taskName = taskName;
		this.estimatedTime = estimatedTime;
		this.difficulty = difficulty;
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
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
