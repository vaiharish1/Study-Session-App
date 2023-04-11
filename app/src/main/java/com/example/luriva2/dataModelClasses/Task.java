package com.example.luriva2.dataModelClasses;

public class Task {
	private String name;
	private int difficulty;
	private int estimatedTime; // in minutes
	
	private int totalTime;
	
	public Task(String n, int d, int eT) {
		name = n;
		difficulty = d;
		estimatedTime = eT;
		
		totalTime = eT + Constants.BUFFER_TIME * difficulty;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public int getEstimatedTime() {
		return estimatedTime;
	}
 	
	public int getTotalTime() {
		return totalTime;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setDifficulty(int d) {
		difficulty = d;
	}
	
	public void setEstimatedTime(int eT) {
		estimatedTime = eT;
		totalTime = eT + Constants.BUFFER_TIME * difficulty;
	}
}
