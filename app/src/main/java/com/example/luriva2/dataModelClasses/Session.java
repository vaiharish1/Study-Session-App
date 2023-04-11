package com.example.luriva2.dataModelClasses;

public class Session {
	private String sessionName;
	private int startTime;
	private int endTime;
	
	private int nextStartTime;

	public Session(String sessionName, int startTime, int endTime) {
		this.sessionName = sessionName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.nextStartTime = endTime + Constants.BUFFER_TIME;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public void setNextStartTime(int nextStartTime) {
		this.nextStartTime = nextStartTime;
	}

	@Override
	public String toString() {
		return "Session{" +
				"sessionName='" + sessionName + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", nextStartTime=" + nextStartTime +
				'}';
	}
}
