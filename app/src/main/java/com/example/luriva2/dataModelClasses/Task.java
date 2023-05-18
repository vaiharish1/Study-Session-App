package com.example.luriva2.dataModelClasses;

import java.util.Objects;

public class Task {
	private final String taskName; // the task name
	private int estimatedTime; // the estimated time to complete this task
	private final int difficulty; // the task's difficulty
	private final String taskType; // the task's type (Project, Repetitive, One-Time)
	private final Date dueDate; // the due date of the task (can be null if Repetitive task)
	private int amtSessions; // the amount of session this task needs to be completed

	// the constructor
	public Task(String taskName, int estimatedTime, int difficulty, String taskType, Date dueDate) {
		this.taskName = taskName;
		this.estimatedTime = estimatedTime + Constants.BUFFER_TIME * difficulty;
		this.difficulty = difficulty;
		this.taskType = taskType;
		this.dueDate = dueDate;
		this.amtSessions = 0;
	}

	// the equals method
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Task)) return false;
		Task task = (Task) o;
		return getDifficulty() == task.getDifficulty() && getTaskName().equals(task.getTaskName()) && getTaskType().equals(task.getTaskType()) && Objects.equals(getDueDate(), task.getDueDate());
	}

	// the hashcode method
	@Override
	public int hashCode() {
		return Objects.hash(getTaskName(), getDifficulty(), getTaskType(), getDueDate());
	}

	// getting the total amount of sessions
	public int getAmtSessions() {
		return amtSessions;
	}

	// setting the total amount of sessions
	public void setAmtSessions(int amtSessions) {
		this.amtSessions = amtSessions;
	}

	// getting the due date
	public Date getDueDate() {
		return dueDate;
	}

	// getting the task name
	public String getTaskName() {
		return taskName;
	}

	// getting the difficulty of the task
	public int getDifficulty() {
		return difficulty;
	}

	// getting the estimated time of the task
	public int getEstimatedTime() {
		return estimatedTime;
	}

	// setting the estimated time of the task
	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	// getting the task type
	public String getTaskType() {
		return taskType;
	}

	// toString method
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
