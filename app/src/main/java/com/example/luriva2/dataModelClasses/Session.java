package com.example.luriva2.dataModelClasses;

public class Session {
	private final Task task;
	private final Date date;
	private Timeblock timeblock;
	private Time nextStartTime;

	private int sessionId;

	public Session(Task task, Date date, Timeblock timeblock, int sessionId) {
		this.task = task;
		this.date = date;
		this.timeblock = timeblock;
		this.nextStartTime = this.timeblock.getEndTime().add(0, Constants.IN_BETWEEN_TIME);
		this.sessionId = sessionId;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
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
		this.nextStartTime = this.timeblock.getEndTime().add(0, Constants.IN_BETWEEN_TIME);
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
