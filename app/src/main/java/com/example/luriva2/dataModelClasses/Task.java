package com.example.luriva2.dataModelClasses;

import java.util.Objects;

public class Task {
	private final String taskName;
	private int estimatedTime;
	private final int difficulty;
	private final String taskType;
	private final Date dueDate;
	private int amtSessions;

	public Task(String taskName, int estimatedTime, int difficulty, String taskType, Date dueDate) {
		this.taskName = taskName;
		this.estimatedTime = estimatedTime + Constants.BUFFER_TIME * difficulty;
		this.difficulty = difficulty;
		this.taskType = taskType;
		this.dueDate = dueDate;
		this.amtSessions = 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Task)) return false;
		Task task = (Task) o;
		return getEstimatedTime() == task.getEstimatedTime() && getDifficulty() == task.getDifficulty() && getAmtSessions() == task.getAmtSessions() && getTaskName().equals(task.getTaskName()) && getTaskType().equals(task.getTaskType()) && Objects.equals(getDueDate(), task.getDueDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTaskName(), getEstimatedTime(), getDifficulty(), getTaskType(), getDueDate(), getAmtSessions());
	}

	public int getAmtSessions() {
		return amtSessions;
	}

	public void setAmtSessions(int amtSessions) {
		this.amtSessions = amtSessions;
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

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
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
				", dueDate=" + dueDate +
				", amtSessions=" + amtSessions +
				'}';
	}
}
