package com.example.luriva2.dataModelClasses;

public class Timeblock implements Comparable<Timeblock> {
    private Time startTime, endTime; // get start and end times of this block

    // the constructor
    public Timeblock(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getting the start time
    public Time getStartTime() {
        return startTime;
    }

    // getting the end time
    public Time getEndTime() {
        return endTime;
    }

    // if this timeblock contains a time
    public boolean contains(Time otherTime) {
        if (startTime.equals(otherTime)) return true;
        if (startTime.compareTo(otherTime) <= 0 && endTime.compareTo(otherTime) > 0) return true;
        return false;
    }

    // comparing this time block to another time block
    public int compareTo(Timeblock other) {
        return this.getStartTime().compareTo(other.getStartTime());
    }

    // toString method
    @Override
    public String toString() {
        return "Timeblock{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}