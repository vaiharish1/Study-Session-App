package com.example.luriva2.dataModelClasses;

public class Session implements Comparable<Session> {
	private final Task task; // the task this session is related to
	private final Date date; // the date of doing the session
	private Timeblock timeblock; // the timeblock
	private Time nextStartTime; // the next start time for this session

	private int sessionId; // the session id

	// constructing a session
	public Session(Task task, Date date, Timeblock timeblock, int sessionId) {
		this.task = task;
		this.date = date;
		this.timeblock = timeblock;
		this.nextStartTime = this.timeblock.getEndTime().add(0, Constants.IN_BETWEEN_TIME);
		this.sessionId = sessionId;
	}

	// get the session id (which session when doing the task)
	public int getSessionId() {
		return sessionId;
	}

	// setting session id
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	// getting the task
	public Task getTask() {
		return task;
	}

	// getting the date of doing the task
	public Date getDate() {
		return date;
	}

	// getting the timeblock
	public Timeblock getTimeblock() {
		return timeblock;
	}

	// setting the timeblock if changing time of session
	public void setTimeblock(Timeblock timeblock) {
		this.timeblock = timeblock;
		this.nextStartTime = this.timeblock.getEndTime().add(0, Constants.IN_BETWEEN_TIME);
	}

	// getting the next start time
	public Time getNextStartTime() {
		return nextStartTime;
	}

	// toString method
	@Override
	public String toString() {
		return "Session{" +
				"task=" + task +
				", date=" + date +
				", timeblock=" + timeblock +
				", nextStartTime=" + nextStartTime +
				", sessionId=" + sessionId +
				'}';
	}

	// comparing this session to other sessions
	@Override
	public int compareTo(Session session) {
		return this.getSessionId() - session.getSessionId();
	}
}
