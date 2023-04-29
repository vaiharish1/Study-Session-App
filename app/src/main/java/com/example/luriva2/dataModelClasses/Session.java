package com.example.luriva2.dataModelClasses;

public class Session {
	private Task task;

	private Date date;
	private Timeblock timeblock;
	private Time nextStartTime;

	public Session(Task task, Date date, Timeblock timeblock) {
		this.task = task;
		this.date = date;
		this.timeblock = timeblock;
		this.nextStartTime = this.timeblock.getEndTime().add(0, Constants.BUFFER_TIME);
	}

	public Task getTask() {
		return task;
	}

	public Date getDate() {
		return date;
	}

	public Timeblock getTimeblock() {
		return timeblock;
	}

	public void setTimeblock(Timeblock timeblock) {
		this.timeblock = timeblock;
		this.nextStartTime = this.timeblock.getEndTime().add(0, Constants.BUFFER_TIME);
	}

	public Time getNextStartTime() {
		return nextStartTime;
	}

	@Override
	public String toString() {
		return "Session{" +
				"task=" + task +
				", date=" + date +
				", timeblock=" + timeblock +
				", nextStartTime=" + nextStartTime +
				'}';
	}
}
